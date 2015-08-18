package uk.co.qmunity.lib.network;

import net.minecraft.world.World;
import uk.co.qmunity.lib.vec.IWorldLocation;
import uk.co.qmunity.lib.vec.WorldPos;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

public abstract class LocatedPacket<T extends LocatedPacket<T>> extends Packet<T> {

    protected int x, y, z;

    public LocatedPacket(IWorldLocation location) {

        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
    }

    public LocatedPacket(int x, int y, int z) {

        this.x = x;
        this.y = y;
        this.z = z;
    }

    public LocatedPacket() {

    }

    @Override
    public void fromBytes(MCByteBuf buf) {

        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
    }

    @Override
    public void toBytes(MCByteBuf buf) {

        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    public TargetPoint getTargetPoint(World world, double range) {

        return new NetworkRegistry.TargetPoint(world.provider.dimensionId, x, y, z, range);
    }

    protected WorldPos getWorldPos(World world) {

        return new WorldPos(world, x, y, z);
    }

}
