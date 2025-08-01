package cn.nukkit.level.format.generic;

import cn.nukkit.GameVersion;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.blockentity.PersistentDataContainerBlockEntity;
import cn.nukkit.entity.Entity;
import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.Level;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.level.format.LevelProvider;
import cn.nukkit.level.persistence.PersistentDataContainer;
import cn.nukkit.math.NukkitMath;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.nbt.tag.NumberTag;
import cn.nukkit.nbt.tag.Tag;
import cn.nukkit.network.protocol.BatchPacket;
import cn.nukkit.utils.collection.nb.Long2ObjectNonBlockingMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author MagicDroidX
 * Nukkit Project
 */
public abstract class BaseFullChunk implements FullChunk, ChunkManager {

    protected Long2ObjectNonBlockingMap<Entity> entities;

    protected Long2ObjectNonBlockingMap<BlockEntity> tiles;

    protected Long2ObjectNonBlockingMap<BlockEntity> tileList;

    /**
     * encoded as:
     *
     * (x &lt;&lt; 4) | z
     */
    protected byte[] biomes;

    protected byte[] blocks;

    protected byte[] data;

    protected byte[] skyLight;

    protected byte[] blockLight;

    protected byte[] heightMap;

    protected List<CompoundTag> NBTtiles;

    protected List<CompoundTag> NBTentities;

    protected Map<Integer, Integer> extraData;

    protected LevelProvider provider;
    protected Class<? extends LevelProvider> providerClass;

    private int x;
    private int z;
    private long hash;

    protected long changes;

    protected boolean isInit;

    protected Map<GameVersion, BatchPacket> chunkPackets;

    @Override
    public BaseFullChunk clone() {
        BaseFullChunk chunk;
        try {
            chunk = (BaseFullChunk) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
        if (this.biomes != null) {
            chunk.biomes = this.biomes.clone();
        }

        if (this.blocks != null) {
            chunk.blocks = this.blocks.clone();
        }

        if (this.data != null) {
            chunk.data = this.data.clone();
        }

        if (this.skyLight != null) {
            chunk.skyLight = this.skyLight.clone();
        }

        if (this.blockLight != null) {
            chunk.blockLight = this.blockLight.clone();
        }

        if (this.heightMap != null) {
            chunk.heightMap = this.getHeightMapArray().clone();
        }
        return chunk;
    }

    protected BaseFullChunk cloneForChunkSending() {
        BaseFullChunk chunk;
        try {
            chunk = (BaseFullChunk)super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }

        if (this.tiles != null) {
            chunk.tiles = new Long2ObjectNonBlockingMap<>();
            chunk.tiles.putAll(this.tiles);
        }

        chunk.entities = null;
        chunk.tileList = null;
        chunk.NBTentities = null;
        chunk.NBTtiles = null;
        chunk.extraData = null;
        return chunk;
    }

    @Deprecated
    public void setChunkPacket(int protocol, BatchPacket packet) {
        this.setChunkPacket(GameVersion.byProtocol(protocol, Server.getInstance().onlyNetEaseMode), packet);
    }

    public void setChunkPacket(GameVersion protocol, BatchPacket packet) {
        if (packet != null) {
            packet.trim();
            if (this.chunkPackets == null) {
                this.chunkPackets = new Object2ObjectOpenHashMap<>();
            }
            this.chunkPackets.put(protocol, packet);
        }
    }

    @Deprecated
    public BatchPacket getChunkPacket(int protocol) {
        return getChunkPacket(GameVersion.byProtocol(protocol, Server.getInstance().onlyNetEaseMode));
    }

    public BatchPacket getChunkPacket(GameVersion protocol) {
        if (this.chunkPackets == null) {
            return null;
        }
        BatchPacket pk = this.chunkPackets.get(protocol);
        if (pk != null) {
            pk.trim();
        }
        return pk;
    }

    public void backwardCompatibilityUpdate(Level level) {
        // Does nothing here
    }

    @Override
    public void initChunk() {
        if (this.getProvider() != null && !this.isInit) {
            boolean changed = this.hasChanged();
            if (this.NBTentities != null) {
                for (CompoundTag nbt : NBTentities) {
                    if (!nbt.contains("id")) {
                        this.setChanged();
                        continue;
                    }
                    ListTag<? extends Tag> pos = nbt.getList("Pos");
                    if (NukkitMath.floorDouble(((NumberTag<?>) pos.get(0)).getData().doubleValue()) >> 4 != this.x || NukkitMath.floorDouble(((NumberTag<?>) pos.get(2)).getData().doubleValue()) >> 4 != this.z) {
                        changed = true;
                        continue;
                    }
                    Entity entity = Entity.createEntity(nbt.getString("id"), this, nbt);
                    if (entity != null) {
                        changed = true;
                    }
                }
                this.NBTentities = null;
            }

            if (this.NBTtiles != null) {
                for (CompoundTag nbt : NBTtiles) {
                    if (nbt != null) {
                        if (!nbt.contains("id")) {
                            changed = true;
                            continue;
                        }
                        if ((nbt.getInt("x") >> 4) != this.x || ((nbt.getInt("z") >> 4) != this.z)) {
                            changed = true;
                            continue;
                        }
                        BlockEntity blockEntity = BlockEntity.createBlockEntity(nbt.getString("id").replaceFirst("BlockEntity", ""), this, nbt);
                        if (blockEntity == null) {
                            changed = true;
                        }
                    }
                }
                this.NBTtiles = null;
            }

            this.setChanged(changed);

            this.isInit = true;
        }
    }

    @Override
    public final long getIndex() {
        return hash;
    }

    @Override
    public final int getX() {
        return x;
    }

    @Override
    public final int getZ() {
        return z;
    }

    @Override
    public void setPosition(int x, int z) {
        this.x = x;
        this.z = z;
        this.hash = Level.chunkHash(x, z);
    }

    @Override
    public final void setX(int x) {
        this.x = x;
        this.hash = Level.chunkHash(x, z);
    }

    @Override
    public final void setZ(int z) {
        this.z = z;
        this.hash = Level.chunkHash(x, z);
    }

    @Override
    public LevelProvider getProvider() {
        return provider;
    }

    @Override
    public void setProvider(LevelProvider provider) {
        this.provider = provider;

        if (provider != null) {
            this.providerClass = provider.getClass();
        }
    }

    @Override
    public int getBiomeId(int x, int z) {
        return this.biomes[(x << 4) | z] & 0xFF;
    }

    @Override
    public void setBiomeId(int x, int z, byte biomeId) {
        this.setChanged();
        this.biomes[(x << 4) | z] = biomeId;
    }

    @Override
    public void setBiomeId(int x, int z, int biomeId) {
        this.biomes[(x << 4) | z] = (byte) biomeId;
    }

    @Override
    public int getBiomeColor(int x, int z) {
        return 0;
    }

    @Override
    public void setBiomeColor(int x, int z, int r, int g, int b) {
    }

    @Override
    public int getHeightMap(int x, int z) {
        return this.heightMap[(z << 4) | x] & 0xFF;
    }

    @Override
    public void setHeightMap(int x, int z, int value) {
        this.heightMap[(z << 4) | x] = (byte) value;
    }

    @Override
    public void recalculateHeightMap() {
        for (int z = 0; z < 16; ++z) {
            for (int x = 0; x < 16; ++x) {
                recalculateHeightMapColumn(x, z);
            }
        }
    }

    @Override
    public int recalculateHeightMapColumn(int x, int z) {
        int max = getHighestBlockAt(x, z, false);
        int y;
        for (y = max; y >= 0; --y) {
            if (Block.lightFilter[getBlockIdAt(x, y, z)] > 1 || Block.diffusesSkyLight[getBlockIdAt(x, y, z)]) {
                break;
            }
        }

        setHeightMap(x, z, y + 1);
        return y + 1;
    }

    @Override
    public int getBlockExtraData(int x, int y, int z) {
        int index = Level.chunkBlockHash(x, y, z);
        if (this.extraData != null && this.extraData.containsKey(index)) {
            return this.extraData.get(index);
        }

        return 0;
    }

    @Override
    public void setBlockExtraData(int x, int y, int z, int data) {
        if (data == 0) {
            if (this.extraData != null) {
                this.extraData.remove(Level.chunkBlockHash(x, y, z));
            }
        } else {
            if (this.extraData == null) this.extraData = new Int2ObjectOpenHashMap<>();
            this.extraData.put(Level.chunkBlockHash(x, y, z), data);
        }

        this.setChanged(true);
    }

    @Override
    public void populateSkyLight() {
        // basic light calculation
        for (int z = 0; z < 16; ++z) {
            for (int x = 0; x < 16; ++x) { // iterating over all columns in chunk
                int top = this.getHeightMap(x, z) - 1; // top-most block

                int y;

                for (y = this.getProvider().getMaxBlockY(); y > top; --y) {
                    // all the blocks above & including the top-most block in a column are exposed to sun and
                    // thus have a skylight value of 15
                    this.setBlockSkyLight(x, y, z, 15);
                }

                int nextLight = 15; // light value that will be applied starting with the next block
                int nextDecrease = 0; // decrease that that will be applied starting with the next block

                // TODO: remove nextLight & nextDecrease, use only light & decrease variables
                for (y = top; y >= this.getProvider().getMinBlockY(); --y) { // going under the top-most block
                    nextLight -= nextDecrease;
                    int light = nextLight; // this light value will be applied for this block. The following checks are all about the next blocks

                    if (light < 0) {
                        light = 0;
                    }

                    this.setBlockSkyLight(x, y, z, light);

                    if (light == 0) { // skipping block checks, because everything under a block that has a skylight value
                                      // of 0 also has a skylight value of 0
                        continue;
                    }

                    // START of checks for the next block
                    int id = this.getBlockId(x, y, z);

                    if (!Block.transparent[id]) { // if we encounter an opaque block, all the blocks under it will
                                           // have a skylight value of 0 (the block itself has a value of 15, if it's a top-most block)
                        nextLight = 0;
                    } else if (Block.diffusesSkyLight[id]) {
                        nextDecrease += 1; // skylight value decreases by one for each block under a block
                                           // that diffuses skylight. The block itself has a value of 15 (if it's a top-most block)
                    } else {
                        nextDecrease -= Block.lightFilter[id]; // blocks under a light filtering block will have a skylight value
                                                            // decreased by the lightFilter value of that block. The block itself
                                                            // has a value of 15 (if it's a top-most block)
                    }
                    // END of checks for the next block
                }
            }
        }
    }

    @Override
    public int getHighestBlockAt(int x, int z) {
        return this.getHighestBlockAt(x, z, true);
    }

    @Override
    public int getHighestBlockAt(int x, int z, boolean cache) {
        if (cache) {
            int h = this.getHeightMap(x, z);
            if (h != this.getProvider().getMinBlockY() && h != this.getProvider().getMaxBlockY()) {
                return h;
            }
        }
        for (int y = this.getProvider().getMaxBlockY(); y >= this.getProvider().getMinBlockY(); --y) {
            if (getBlockId(x, y, z) != 0x00) {
                if (cache) {
                    this.setHeightMap(x, z, y);
                }
                return y;
            }
        }
        return 0;
    }

    @Override
    public void addEntity(Entity entity) {
        if (this.entities == null) {
            this.entities = new Long2ObjectNonBlockingMap<>();
        }
        this.entities.put(entity.getId(), entity);
        if (!(entity instanceof Player) && this.isInit) {
            this.setChanged();
        }
    }

    @Override
    public void removeEntity(Entity entity) {
        if (this.entities != null) {
            this.entities.remove(entity.getId());
            if (!(entity instanceof Player) && this.isInit) {
                this.setChanged();
            }
        }
    }

    @Override
    public void addBlockEntity(BlockEntity blockEntity) {
        if (this.tiles == null) {
            this.tiles = new Long2ObjectNonBlockingMap<>();
            this.tileList = new Long2ObjectNonBlockingMap<>();
        }
        this.tiles.put(blockEntity.getId(), blockEntity);
        int y = blockEntity.getFloorY() - this.getProvider().getMinBlockY();
        int index = ((blockEntity.getFloorZ() & 0x0f) << 16) | ((blockEntity.getFloorX() & 0x0f) << 12) | y;
        if (this.tileList.containsKey(index) && !this.tileList.get(index).equals(blockEntity)) {
            BlockEntity entity = this.tileList.get(index);
            this.tiles.remove(entity.getId());
            entity.onReplacedWith(blockEntity);
            entity.close();
        }
        this.tileList.put(index, blockEntity);
        if (this.isInit) {
            this.setChanged();
        }
    }

    @Override
    public void removeBlockEntity(BlockEntity blockEntity) {
        if (this.tiles != null) {
            this.tiles.remove(blockEntity.getId());
            int y = blockEntity.getFloorY() - this.getProvider().getMinBlockY();
            int index = ((blockEntity.getFloorZ() & 0x0f) << 16) | ((blockEntity.getFloorX() & 0x0f) << 12) | y;
            this.tileList.remove(index);

            if (!(blockEntity instanceof PersistentDataContainerBlockEntity) && blockEntity.hasPersistentDataContainer()) {
                this.createPersistentBlockContainer(blockEntity, blockEntity.getPersistentDataContainer().getStorage());
            }

            if (this.isInit) {
                this.setChanged();
            }
        }
    }

    private BlockEntity createPersistentBlockContainer(Vector3 pos, CompoundTag storage) {
        CompoundTag tag = BlockEntity.getDefaultCompound(pos, BlockEntity.PERSISTENT_CONTAINER);
        tag.putCompound(PersistentDataContainer.STORAGE_TAG, storage);
        return BlockEntity.createBlockEntity(BlockEntity.PERSISTENT_CONTAINER, this, tag);
    }

    @Override
    public Map<Long, Entity> getEntities() {
        return entities == null ? Collections.emptyMap() : entities;
    }

    @Override
    public Map<Long, BlockEntity> getBlockEntities() {
        return tiles == null ? Collections.emptyMap() : tiles;
    }

    @Override
    public Map<Integer, Integer> getBlockExtraDataArray() {
        return extraData == null ? Collections.emptyMap() : extraData;
    }

    @Override
    public BlockEntity getTile(int x, int y, int z) {
        if (this.tileList == null || this.getProvider() == null)  {
            return null;
        }
        int capY = y - this.getProvider().getMinBlockY();
        return this.tileList.get((z << 16) | (x << 12) | capY);

    }

    @Override
    public boolean isLoaded() {
        return this.getProvider() != null && this.getProvider().isChunkLoaded(this.x, this.z);
    }

    @Override
    public boolean load() throws IOException {
        return this.load(true);
    }

    @Override
    public boolean load(boolean generate) throws IOException {
        return this.getProvider() != null && this.getProvider().getChunk(this.x, this.z, true) != null;
    }

    @Override
    public boolean unload() throws Exception {
        return this.unload(provider.getLevel().getAutoSave(), true);
    }

    @Override
    public boolean unload(boolean save) throws Exception {
        return this.unload(save, true);
    }

    @Override
    public boolean unload(boolean save, boolean safe) {
        LevelProvider provider = this.getProvider();
        if (provider == null) {
            return true;
        }
        if (save && this.changes != 0) {
            provider.saveChunk(this.x, this.z);
        }
        if (safe) {
            for (Entity entity : this.getEntities().values()) {
                if (entity instanceof Player) {
                    return false;
                }
            }
        }
        for (Entity entity : new ArrayList<>(this.getEntities().values())) {
            if (entity instanceof Player) {
                continue;
            }
            entity.close();
        }

        for (BlockEntity blockEntity : new ArrayList<>(this.getBlockEntities().values())) {
            blockEntity.close();
        }
        this.provider = null;
        return true;
    }

    @Override
    public byte[] getBlockIdArray() {
        return this.blocks;
    }

    @Override
    public byte[] getBlockDataArray() {
        return this.data;
    }

    @Override
    public byte[] getBlockSkyLightArray() {
        return this.skyLight;
    }

    @Override
    public byte[] getBlockLightArray() {
        return this.blockLight;
    }

    @Override
    public byte[] getBiomeIdArray() {
        return this.biomes;
    }

    @Override
    public void setBiomeIdArray(byte[] biomeIdArray) {
        this.biomes = biomeIdArray;
    }

    @Override
    public byte[] getHeightMapArray() {
        return this.heightMap;
    }

    public long getChanges() {
        return changes;
    }

    @Override
    public boolean hasChanged() {
        return this.changes != 0;
    }

    @Override
    public void setChanged() {
        this.changes++;
        chunkPackets = null;
    }

    @Override
    public void setChanged(boolean changed) {
        if (changed) {
            setChanged();
        } else {
            changes = 0;
        }
    }

    @Override
    public byte[] toFastBinary() {
        return this.toBinary();
    }

    @Override
    public boolean isLightPopulated() {
        return true;
    }

    @Override
    public void setLightPopulated() {
        this.setLightPopulated(true);
    }

    @Override
    public void setLightPopulated(boolean value) {

    }

    @Override
    public int getBlockIdAt(int x, int y, int z) {
        return getBlockIdAt(x, y, z, 0);
    }

    @Override
    public int getBlockIdAt(int x, int y, int z, int layer) {
        if (x >> 4 == this.x && z >> 4 == this.z) {
            return getBlockId(x & 15, y, z & 15, layer);
        }
        return 0;
    }

    @Override
    public void setBlockFullIdAt(int x, int y, int z, int fullId) {
        setFullBlockId(x, y, z, 0, fullId);
    }

    @Override
    public void setBlockFullIdAt(int x, int y, int z, int layer, int fullId) {
        if (x >> 4 == getX() && z >> 4 == getZ()) {
            setFullBlockId(x & 15, y, z & 15, layer, fullId);
        }
    }

    @Override
    public boolean setBlockAtLayer(int x, int y, int z, int layer, int blockId) {
        return setBlockAtLayer(x, y, z, layer, blockId, 0);
    }

    @Override
    public boolean setBlockAtLayer(int x, int y, int z, int layer, int blockId, int meta) {
        int oldId = getBlockId(x, y, z, layer);
        int oldData = getBlockData(x, y, z, layer);
        if (oldId != blockId || oldData != meta) {
            setBlock(x, y, z, layer, blockId);
            setBlockData(x, y, z, layer, meta);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setBlockIdAt(int x, int y, int z, int id) {
        setBlockIdAt(x, y, z, 0, id);
    }

    @Override
    public void setBlockIdAt(int x, int y, int z, int layer, int id) {
        if (x >> 4 == this.x && z >> 4 == this.z) {
            setBlockId(x & 15, y, z & 15, layer, id);
        }
    }
    
    @Override
    public void setBlockAt(int x, int y, int z, int id, int data) {
        if (x >> 4 == getX() && z >> 4 == getZ()) {
            setBlock(x & 15, y, z & 15, id, data);
        }
    }

    @Override
    public int getBlockDataAt(int x, int y, int z) {
        return getBlockDataAt(x, y, z, 0);
    }

    @Override
    public int getBlockDataAt(int x, int y, int z, int layer) {
        if (x >> 4 == this.x && z >> 4 == this.z) {
            return getBlockIdAt(x & 15, y, z & 15, layer);
        }
        return 0;
    }

    @Override
    public void setBlockDataAt(int x, int y, int z, int data) {
        setBlockDataAt(x, y, z, 0, data);
    }

    @Override
    public void setBlockDataAt(int x, int y, int z, int layer, int data) {
        if (x >> 4 == this.x && z >> 4 == this.z) {
            setBlockData(x & 15, y, z & 15, layer, data);
        }
    }

    @Override
    public BaseFullChunk getChunk(int chunkX, int chunkZ) {
        if (chunkX == this.x && chunkZ == this.z) return this;
        return null;
    }

    @Override
    public void setChunk(int chunkX, int chunkZ) {
        setChunk(chunkX, chunkZ, null);
    }

    @Override
    public void setChunk(int chunkX, int chunkZ, BaseFullChunk chunk) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getSeed() {
        throw new UnsupportedOperationException("Chunk does not have a seed");
    }

    @Override
    public int getMinBlockY() {
        return this.getProvider().getMinBlockY();
    }

    @Override
    public int getMaxBlockY() {
        return this.getProvider().getMaxBlockY();
    }

    public boolean compress() {
        if (this.chunkPackets == null) {
            return false;
        }
        for (BatchPacket pk : this.chunkPackets.values()) {
            if (pk != null) {
                pk.trim();
            }
        }
        return !this.chunkPackets.isEmpty();
    }
}
