package mcjty.rftoolsutility.modules.teleporter.data;

import mcjty.lib.varia.LevelTools;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Objects;

public class TeleportDestination {
    private final BlockPos coordinate;
    private final RegistryKey<World> dimension;
    private String name = "";

    public TeleportDestination(PacketBuffer buf) {
        int cx = buf.readInt();
        int cy = buf.readInt();
        int cz = buf.readInt();
        if (cx == -1 && cy == -1 && cz == -1) {
            coordinate = null;
        } else {
            coordinate = new BlockPos(cx, cy, cz);
        }
        dimension = LevelTools.getId(buf.readResourceLocation());
        setName(buf.readUtf(32767));
    }

    public TeleportDestination(BlockPos coordinate, RegistryKey<World> dimension) {
        this.coordinate = coordinate;
        this.dimension = dimension;
    }

    public boolean isValid() {
        return coordinate != null;
    }

    public void toBytes(PacketBuffer buf) {
        if (coordinate == null) {
            buf.writeInt(-1);
            buf.writeInt(-1);
            buf.writeInt(-1);
        } else {
            buf.writeInt(coordinate.getX());
            buf.writeInt(coordinate.getY());
            buf.writeInt(coordinate.getZ());
        }
        buf.writeResourceLocation(dimension.location());
        buf.writeUtf(getName());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) {
            this.name = "";
        } else {
            this.name = name;
        }
    }

    public BlockPos getCoordinate() {
        return coordinate;
    }

    public RegistryKey<World> getDimension() {
        return dimension;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeleportDestination that = (TeleportDestination) o;
        return Objects.equals(coordinate, that.coordinate) &&
                Objects.equals(dimension, that.dimension) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinate, dimension, name);
    }
}
