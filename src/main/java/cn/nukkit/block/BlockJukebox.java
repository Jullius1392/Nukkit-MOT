package cn.nukkit.block;

import cn.nukkit.Player;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.blockentity.BlockEntityJukebox;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBlock;
import cn.nukkit.item.ItemRecord;
import cn.nukkit.item.ItemTool;
import cn.nukkit.math.BlockFace;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.utils.BlockColor;
import org.jetbrains.annotations.NotNull;

/**
 * Created by CreeperFace on 7.8.2017.
 */
public class BlockJukebox extends BlockSolid implements BlockEntityHolder<BlockEntityJukebox> {

    @Override
    public String getName() {
        return "Jukebox";
    }

    @Override
    public int getId() {
        return JUKEBOX;
    }

    @NotNull
    @Override
    public Class<? extends BlockEntityJukebox> getBlockEntityClass() {
        return BlockEntityJukebox.class;
    }

    @NotNull
    @Override
    public String getBlockEntityType() {
        return BlockEntity.JUKEBOX;
    }

    @Override
    public double getHardness() {
        return 0.8;
    }

    @Override
    public double getResistance() {
        return 30;
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_AXE;
    }

    @Override
    public boolean canBeActivated() {
        return true;
    }

    @Override
    public Item toItem() {
        return new ItemBlock(this, 0);
    }

    @Override
    public boolean onActivate(Item item, Player player) {
        BlockEntity blockEntity = this.getLevel().getBlockEntity(this);
        if (!(blockEntity instanceof BlockEntityJukebox)) {
            blockEntity = this.createBlockEntity();
        }

        BlockEntityJukebox jukebox = (BlockEntityJukebox) blockEntity;
        if (jukebox.getRecordItem().getId() != 0) {
            jukebox.dropItem();
        } else if (item instanceof ItemRecord) {
            jukebox.setRecordItem(item);
            jukebox.play();
            player.getInventory().decreaseCount(player.getInventory().getHeldItemIndex());
        }

        return false;
    }

    @Override
    public boolean place(Item item, Block block, Block target, BlockFace face, double fx, double fy, double fz, Player player) {
        if (super.place(item, block, target, face, fx, fy, fz, player)) {
            createBlockEntity();
            return true;
        }

        return false;
    }

    /*@Override // Replaced with BlockEntityJukebox#onBreak
    public boolean onBreak(Item item) {
        if (super.onBreak(item)) {
            BlockEntity blockEntity = this.level.getBlockEntity(this);

            if (blockEntity instanceof BlockEntityJukebox) {
                ((BlockEntityJukebox) blockEntity).dropItem();
            }
            return true;
        }

        return false;
    }*/

    @Override
    public BlockEntityJukebox createBlockEntity() {
        CompoundTag nbt = new CompoundTag()
                .putList(new ListTag<>("Items"))
                .putString("id", BlockEntity.JUKEBOX)
                .putInt("x", getFloorX())
                .putInt("y", getFloorY())
                .putInt("z", getFloorZ());

        return (BlockEntityJukebox) BlockEntity.createBlockEntity(BlockEntity.JUKEBOX, this.level.getChunk(getFloorX() >> 4, getFloorZ() >> 4), nbt);
    }

    @Override
    public BlockColor getColor() {
        return BlockColor.DIRT_BLOCK_COLOR;
    }

    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }

    @Override
    public int getComparatorInputOverride() {
        BlockEntity blockEntity = this.getLevel().getBlockEntityIfLoaded(this);
        return blockEntity instanceof BlockEntityJukebox ? ((BlockEntityJukebox) blockEntity).getComparatorSignal() : 0;
    }
}
