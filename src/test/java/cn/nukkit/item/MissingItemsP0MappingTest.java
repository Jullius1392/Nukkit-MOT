package cn.nukkit.item;

import cn.nukkit.GameVersion;
import cn.nukkit.MockServer;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.*;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.level.GameRule;
import cn.nukkit.level.GameRules;
import cn.nukkit.level.Level;
import cn.nukkit.utils.DyeColor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MissingItemsP0MappingTest {

    private static final List<ExpectedItem> P0_ITEMS = List.of(
            new ExpectedItem("minecraft:torchflower_seeds", GameVersion.V1_19_70, true, ItemTorchflowerSeeds.class),
            new ExpectedItem("minecraft:pitcher_pod", GameVersion.V1_20_0, true, ItemPitcherPod.class),
            new ExpectedItem("minecraft:armadillo_scute", GameVersion.V1_20_60, true, ItemArmadilloScute.class),
            new ExpectedItem("minecraft:wolf_armor", GameVersion.V1_20_60, true, ItemWolfArmor.class),
            new ExpectedItem("minecraft:resin_brick", GameVersion.V1_21_50, true, ItemResinBrick.class),
            new ExpectedItem("minecraft:copper_horse_armor", GameVersion.V1_21_110, true, ItemHorseArmorCopper.class),
            new ExpectedItem("minecraft:netherite_horse_armor", GameVersion.V1_21_130, true, ItemHorseArmorNetherite.class),
            new ExpectedItem("minecraft:music_disc_tears", GameVersion.V1_21_90, true, ItemRecordTears.class),
            new ExpectedItem("minecraft:music_disc_lava_chicken", GameVersion.V1_21_93, true, ItemRecordLavaChicken.class),
            new ExpectedItem("minecraft:wooden_spear", GameVersion.V1_21_130, true, ItemSpearWood.class),
            new ExpectedItem("minecraft:stone_spear", GameVersion.V1_21_130, true, ItemSpearStone.class),
            new ExpectedItem("minecraft:iron_spear", GameVersion.V1_21_130, true, ItemSpearIron.class),
            new ExpectedItem("minecraft:golden_spear", GameVersion.V1_21_130, true, ItemSpearGold.class),
            new ExpectedItem("minecraft:diamond_spear", GameVersion.V1_21_130, true, ItemSpearDiamond.class),
            new ExpectedItem("minecraft:copper_spear", GameVersion.V1_21_130, true, ItemSpearCopper.class),
            new ExpectedItem("minecraft:netherite_spear", GameVersion.V1_21_130, true, ItemSpearNetherite.class),
            new ExpectedItem("minecraft:copper_nautilus_armor", GameVersion.V1_21_130, true, ItemNautilusArmorCopper.class),
            new ExpectedItem("minecraft:iron_nautilus_armor", GameVersion.V1_21_130, true, ItemNautilusArmorIron.class),
            new ExpectedItem("minecraft:golden_nautilus_armor", GameVersion.V1_21_130, true, ItemNautilusArmorGold.class),
            new ExpectedItem("minecraft:diamond_nautilus_armor", GameVersion.V1_21_130, true, ItemNautilusArmorDiamond.class),
            new ExpectedItem("minecraft:netherite_nautilus_armor", GameVersion.V1_21_130, true, ItemNautilusArmorNetherite.class),
            new ExpectedItem("minecraft:ender_dragon_spawn_egg", GameVersion.V1_19_60, false, ItemSpawnEgg.class),
            new ExpectedItem("minecraft:wither_spawn_egg", GameVersion.V1_19_60, false, ItemSpawnEgg.class)
    );

    private static final List<String> DEFERRED_SPAWN_EGGS = List.of(
            "minecraft:trader_llama_spawn_egg",
            "minecraft:camel_husk_spawn_egg",
            "minecraft:nautilus_spawn_egg",
            "minecraft:parched_spawn_egg",
            "minecraft:zombie_nautilus_spawn_egg"
    );

    @BeforeAll
    static void setup() {
        MockServer.init();
    }

    @Test
    public void testP0ItemsConstructAndEncodeOnFirstSupportedVersion() {
        for (ExpectedItem expected : P0_ITEMS) {
            Item item = Item.fromString(expected.identifier());
            assertNotEquals(Item.AIR, item.getId(), expected.identifier() + " should be constructible");
            assertEquals(expected.stringItem(), item instanceof StringItem, expected.identifier() + " StringItem expectation mismatch");
            assertEquals(expected.itemClass(), item.getClass(), expected.identifier() + " should use the expected item class");
            assertTrue(item.isSupportedOn(expected.firstVersion()), expected.identifier() + " should support " + expected.firstVersion());
            assertCanEncode(expected.firstVersion(), item, expected.identifier());
        }
    }

    @Test
    public void testP0ItemsEncodeOnLatestVersion() {
        GameVersion latest = GameVersion.getLastVersion();
        for (ExpectedItem expected : P0_ITEMS) {
            Item item = Item.fromString(expected.identifier());
            assertTrue(item.isSupportedOn(latest), expected.identifier() + " should support latest version");
            assertCanEncode(latest, item, expected.identifier());
        }
    }

    @Test
    public void testBossSpawnEggsUseEntityNetworkIds() {
        Item enderDragonEgg = Item.fromString("minecraft:ender_dragon_spawn_egg");
        Item witherEgg = Item.fromString("minecraft:wither_spawn_egg");

        assertSame(ItemSpawnEgg.class, enderDragonEgg.getClass());
        assertSame(ItemSpawnEgg.class, witherEgg.getClass());
        assertEquals(53, enderDragonEgg.getDamage());
        assertEquals(52, witherEgg.getDamage());
    }

    @Test
    public void testSpawnEggsWithoutRegisteredEntitiesStayUnavailable() {
        for (String identifier : DEFERRED_SPAWN_EGGS) {
            assertEquals(Item.AIR, Item.fromString(identifier).getId(),
                    identifier + " should stay unmapped until MOT has the corresponding entity");
        }
    }

    @Test
    public void testLungeCanTargetRegisteredSpearItems() {
        Enchantment lunge = Enchantment.getEnchantment(Enchantment.ID_LUNGE);
        assertNotNull(lunge);
        for (String identifier : List.of(
                "minecraft:wooden_spear",
                "minecraft:stone_spear",
                "minecraft:iron_spear",
                "minecraft:golden_spear",
                "minecraft:diamond_spear",
                "minecraft:copper_spear",
                "minecraft:netherite_spear"
        )) {
            assertTrue(lunge.canEnchant(Item.fromString(identifier)), identifier + " should accept Lunge");
        }
    }

    @Test
    public void testWolfArmorStoresCustomColor() {
        ItemWolfArmor armor = new ItemWolfArmor().setColor(DyeColor.BLUE);

        assertNotNull(armor.getColor());
        assertEquals(DyeColor.BLUE.getColor().getRGB(), armor.getColor().getRGB());
        assertEquals(11, armor.getArmorPoints());
        assertEquals(64, armor.getMaxDurability());
        assertEquals(1, armor.getMaxStackSize());
    }

    @Test
    public void testSeedItemsPointToCropBlocks() {
        Item torchflowerSeeds = Item.fromString("minecraft:torchflower_seeds");
        Item pitcherPod = Item.fromString("minecraft:pitcher_pod");

        assertEquals(Block.TORCHFLOWER_CROP, torchflowerSeeds.getBlock().getId());
        assertEquals(Block.PITCHER_CROP, pitcherPod.getBlock().getId());
        assertInstanceOf(BlockTorchflowerCrop.class, Block.get(Block.TORCHFLOWER_CROP));
        assertInstanceOf(BlockTorchflower.class, Block.get(Block.TORCHFLOWER));
        assertInstanceOf(BlockPitcherCrop.class, Block.get(Block.PITCHER_CROP));
        assertInstanceOf(BlockPitcherPlant.class, Block.get(Block.PITCHER_PLANT));
    }

    @Test
    public void testNewPlantBlockIdentifiersEncodeOnLatestVersion() {
        GameVersion latest = GameVersion.getLastVersion();
        assertBlockIdentifierEncodes(latest, Block.TORCHFLOWER_CROP, "minecraft:torchflower_crop");
        assertBlockIdentifierEncodes(latest, Block.TORCHFLOWER, "minecraft:torchflower");
        assertBlockIdentifierEncodes(latest, Block.PITCHER_CROP, "minecraft:pitcher_crop");
        assertBlockIdentifierEncodes(latest, Block.PITCHER_PLANT, "minecraft:pitcher_plant");
    }

    @Test
    public void testMaturePlantBlockItemsEncodeOnLatestVersion() {
        GameVersion latest = GameVersion.getLastVersion();
        assertBlockItemEncodes(latest, Block.TORCHFLOWER, "minecraft:torchflower");
        assertBlockItemEncodes(latest, Block.PITCHER_PLANT, "minecraft:pitcher_plant");
    }

    @Test
    public void testNewCropsAreRegisteredForRandomTicks() throws Exception {
        Field randomTickBlocksField = Level.class.getDeclaredField("randomTickBlocks");
        randomTickBlocksField.setAccessible(true);
        boolean[] randomTickBlocks = (boolean[]) randomTickBlocksField.get(null);

        assertTrue(randomTickBlocks[Block.TORCHFLOWER_CROP], "Torchflower crops should receive random ticks");
        assertTrue(randomTickBlocks[Block.PITCHER_CROP], "Pitcher crops should receive random ticks");
    }

    @Test
    public void testSpearStabRespectsPlayerPvpRules() {
        MockServer.reset();
        Server server = MockServer.get();
        Level level = Mockito.mock(Level.class);
        GameRules gameRules = GameRules.getDefault();
        Mockito.when(level.getGameRules()).thenReturn(gameRules);

        Player attacker = Mockito.mock(Player.class);
        Player target = Mockito.mock(Player.class);
        Mockito.when(attacker.getServer()).thenReturn(server);
        Mockito.when(attacker.getLevel()).thenReturn(level);
        target.gamemode = Player.SURVIVAL;

        ItemSpear spear = (ItemSpear) Item.fromString("minecraft:iron_spear");

        server.pvpEnabled = false;
        EntityDamageByEntityEvent serverPvpDisabled = new EntityDamageByEntityEvent(
                attacker, target, EntityDamageEvent.DamageCause.ENTITY_ATTACK, 1f);
        assertFalse(spear.prepareStabAttack(attacker, target, serverPvpDisabled));

        server.pvpEnabled = true;
        gameRules.setGameRule(GameRule.PVP, false);
        EntityDamageByEntityEvent worldPvpDisabled = new EntityDamageByEntityEvent(
                attacker, target, EntityDamageEvent.DamageCause.ENTITY_ATTACK, 1f);
        assertTrue(spear.prepareStabAttack(attacker, target, worldPvpDisabled));
        assertTrue(worldPvpDisabled.isCancelled());
    }

    @Test
    public void testHorseArmorItemsExposeProtection() {
        assertHorseArmor(Item.get(Item.LEATHER_HORSE_ARMOR), 3);
        assertHorseArmor(Item.get(Item.IRON_HORSE_ARMOR), 5);
        assertHorseArmor(Item.get(Item.GOLD_HORSE_ARMOR), 7);
        assertHorseArmor(Item.get(Item.DIAMOND_HORSE_ARMOR), 11);
        assertHorseArmor(Item.fromString("minecraft:copper_horse_armor"), 4);
        assertHorseArmor(Item.fromString("minecraft:netherite_horse_armor"), 19);
    }

    @Test
    public void testNautilusArmorItemsExposeProtectionWithoutDurability() {
        assertNautilusArmor(Item.fromString("minecraft:copper_nautilus_armor"), 4, ItemTool.TIER_COPPER);
        assertNautilusArmor(Item.fromString("minecraft:iron_nautilus_armor"), 5, ItemTool.TIER_IRON);
        assertNautilusArmor(Item.fromString("minecraft:golden_nautilus_armor"), 7, ItemTool.TIER_GOLD);
        assertNautilusArmor(Item.fromString("minecraft:diamond_nautilus_armor"), 11, ItemTool.TIER_DIAMOND);
        assertNautilusArmor(Item.fromString("minecraft:netherite_nautilus_armor"), 19, ItemTool.TIER_NETHERITE);
    }

    @Test
    public void testSpearsCanReleaseAndConsumeOneDurabilityOnUse() {
        ItemSpear spear = (ItemSpear) Item.fromString("minecraft:iron_spear");

        assertTrue(spear.canRelease());
        assertEquals(0, spear.getDamage());
        assertTrue(spear.useOn((Entity) null));
        assertEquals(1, spear.getDamage());
    }

    private static void assertCanEncode(GameVersion gameVersion, Item item, String source) {
        RuntimeItemMapping mapping = RuntimeItems.getMapping(gameVersion);
        if (item instanceof StringItem stringItem) {
            assertTrue(mapping.getNetworkIdByNamespaceId(stringItem.getNamespaceId()).isPresent(),
                    source + " should have runtime namespace mapping on " + gameVersion);
        } else {
            assertDoesNotThrow(() -> mapping.getNetworkId(item),
                    source + " should have legacy runtime mapping on " + gameVersion);
        }
    }

    private static void assertBlockItemEncodes(GameVersion gameVersion, int blockId, String identifier) {
        Item item = Block.get(blockId).toItem();
        assertLegacyBlockItemEncodes(gameVersion, blockId, identifier, item);
    }

    private static void assertBlockIdentifierEncodes(GameVersion gameVersion, int blockId, String identifier) {
        Item item = Item.fromString(identifier);
        assertLegacyBlockItemEncodes(gameVersion, blockId, identifier, item);
        assertEquals(blockId, item.getBlock().getId(), identifier + " should resolve to its block");
    }

    private static void assertLegacyBlockItemEncodes(GameVersion gameVersion, int blockId, String identifier, Item item) {
        assertEquals(255 - blockId, item.getId(), identifier + " should use its legacy block item id");
        assertDoesNotThrow(() -> RuntimeItems.getMapping(gameVersion).getNetworkId(item),
                identifier + " should have legacy runtime mapping on " + gameVersion);
    }

    private static void assertHorseArmor(Item item, int armorPoints) {
        assertTrue(item.isHorseArmor(), item + " should be horse armor");
        assertEquals(armorPoints, item.getArmorPoints());
        assertEquals(1, item.getMaxStackSize());
        assertEquals(1, item.getCount());
    }

    private static void assertNautilusArmor(Item item, int armorPoints, int tier) {
        assertInstanceOf(ItemNautilusArmor.class, item);
        assertEquals(armorPoints, item.getArmorPoints());
        assertEquals(tier, item.getTier());
        assertEquals(-1, item.getMaxDurability());
        assertEquals(1, item.getMaxStackSize());
    }

    private record ExpectedItem(String identifier, GameVersion firstVersion, boolean stringItem, Class<? extends Item> itemClass) {
    }
}
