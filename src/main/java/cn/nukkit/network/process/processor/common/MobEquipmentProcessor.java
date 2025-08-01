package cn.nukkit.network.process.processor.common;

import cn.nukkit.Player;
import cn.nukkit.PlayerHandle;
import cn.nukkit.event.player.PlayerKickEvent;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.item.Item;
import cn.nukkit.network.process.DataPacketProcessor;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.MobEquipmentPacket;
import cn.nukkit.network.protocol.ProtocolInfo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;

/**
 * @author LT_Name
 */
@Log4j2
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MobEquipmentProcessor extends DataPacketProcessor<MobEquipmentPacket> {

    public static final MobEquipmentProcessor INSTANCE = new MobEquipmentProcessor();

    private static final int MAX_FAILED = 5;

    @Override
    public void handle(@NotNull PlayerHandle playerHandle, @NotNull MobEquipmentPacket pk) {
        Player player = playerHandle.player;
        
        if (!player.spawned || !player.isAlive()) {
            return;
        }

        Inventory inv = player.getWindowById(pk.windowId);

        if (inv == null) {
            player.getServer().getLogger().debug(player.getName() + " has no open container with window ID " + pk.windowId);
            playerHandle.setFailedMobEquipmentPacket(playerHandle.getFailedMobEquipmentPacket() + 1);
            if (playerHandle.getFailedMobEquipmentPacket() > MAX_FAILED) {
                log.warn("{} Too many failed MobEquipmentPacket", player.getName());
                player.kick(PlayerKickEvent.Reason.INVALID_PACKET, "Too many failed packets", true, "type=MobEquipmentPacket");
            }
            return;
        }

        Item item = inv.getItem(pk.hotbarSlot);
        
        if (!item.equals(pk.item, false, true)) {
            Item fixItem = Item.get(item.getId(), item.getDamage(), item.getCount(), item.getCompoundTag());
            if (fixItem.equals(pk.item, false, true)) {
                inv.setItem(pk.hotbarSlot, fixItem);
            } else {
                player.getServer().getLogger().debug("Tried to equip "+pk.item+" but have {} in target slot "+fixItem);
                inv.sendContents(player);
            }
        }
        
        if (inv instanceof PlayerInventory) {
            ((PlayerInventory) inv).equipItem(pk.hotbarSlot);
        }

        player.setDataFlag(Player.DATA_FLAGS, Player.DATA_FLAG_ACTION, false);
    }

    @Override
    public int getPacketId() {
        return ProtocolInfo.toNewProtocolID(ProtocolInfo.MOB_EQUIPMENT_PACKET);
    }

    @Override
    public Class<? extends DataPacket> getPacketClass() {
        return MobEquipmentPacket.class;
    }

    @Override
    public boolean isSupported(int protocol) {
        return protocol >= ProtocolInfo.v1_1_0;
    }
}
