package cn.nukkit.level.particle;

import cn.nukkit.GameVersion;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.LevelEventPacket;

public class ScrapeParticle extends GenericParticle {
    public ScrapeParticle(Vector3 pos) {
        super(pos, Particle.TYPE_WAX);
    }

    @Override
    public DataPacket[] mvEncode(GameVersion protocol) {
        LevelEventPacket pk = new LevelEventPacket();
        pk.evid = LevelEventPacket.EVENT_PARTICLE_SCRAPE;
        pk.x = (float) this.x;
        pk.y = (float) this.y;
        pk.z = (float) this.z;
        pk.data = this.data;
        pk.protocol = protocol.getProtocol();
        pk.gameVersion = protocol;
        pk.tryEncode();
        return new DataPacket[]{pk};
    }
}