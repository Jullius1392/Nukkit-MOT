package cn.nukkit.level.format;

import cn.nukkit.GameVersion;
import cn.nukkit.Server;
import cn.nukkit.level.GameRules;
import cn.nukkit.level.Level;
import cn.nukkit.level.format.generic.BaseFullChunk;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.Utils;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;

import java.util.Map;

/**
 * @author MagicDroidX
 * Nukkit Project
 */
public interface LevelProvider {

    byte ORDER_YZX = 0;
    byte ORDER_ZXY = 1;

    @Deprecated
    default void requestChunkTask(IntSet protocols, int chunkX, int chunkZ) {
        Server.mvw("LevelProvider#requestChunkTask(IntSet, int, int) is deprecated, please use LevelProvider#requestChunkTask(ObjectSet<GameVersion>, int, int) instead.");
        requestChunkTask(Utils.intSet2GameVersionSet(protocols, false), chunkX, chunkZ);
    }

    void requestChunkTask(ObjectSet<GameVersion> protocols, int chunkX, int chunkZ);

    String getPath();

    String getGenerator();

    Map<String, Object> getGeneratorOptions();

    BaseFullChunk getLoadedChunk(int X, int Z);

    BaseFullChunk getLoadedChunk(long hash);

    BaseFullChunk getChunk(int X, int Z);

    BaseFullChunk getChunk(int X, int Z, boolean create);

    BaseFullChunk getEmptyChunk(int chunkX, int chunkZ);

    void saveChunks();

    void saveChunk(int X, int Z);

    void saveChunk(int X, int Z, FullChunk chunk);

    void unloadChunks();

    boolean loadChunk(int X, int Z);

    boolean loadChunk(int X, int Z, boolean create);

    boolean unloadChunk(int X, int Z);

    boolean unloadChunk(int X, int Z, boolean safe);

    boolean isChunkGenerated(int X, int Z);

    boolean isChunkPopulated(int X, int Z);

    boolean isChunkLoaded(int X, int Z);

    boolean isChunkLoaded(long hash);

    void setChunk(int chunkX, int chunkZ, FullChunk chunk);

    String getName();

    boolean isRaining();

    void setRaining(boolean raining);

    int getRainTime();

    void setRainTime(int rainTime);

    boolean isThundering();

    void setThundering(boolean thundering);

    int getThunderTime();

    void setThunderTime(int thunderTime);

    long getCurrentTick();

    void setCurrentTick(long currentTick);

    long getTime();

    void setTime(long value);

    long getSeed();

    void setSeed(long value);

    Vector3 getSpawn();

    void setSpawn(Vector3 pos);

    Map<Long, ? extends FullChunk> getLoadedChunks();

    void doGarbageCollection();

    default void doGarbageCollection(long time) {

    }

    Level getLevel();

    void close();

    void saveLevelData();

    void updateLevelName(String name);

    GameRules getGamerules();

    void setGameRules(GameRules rules);

    default int getMaximumLayer() {
        return 0;
    }

    default int getMinBlockY() {
        return Math.max(this.getLevel().getDimensionData().getMinHeight(), 0);
    }

    default int getMaxBlockY() {
        return Math.min(this.getLevel().getDimensionData().getMaxHeight(), 255);
    }
}