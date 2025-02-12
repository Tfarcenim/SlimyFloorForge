package tfar.slimyfloor;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(SlimyFloorForge.MOD_ID)
public class SlimyFloorForge {

    public static final String MOD_ID = "slimyfloor";

    public SlimyFloorForge() {
    }

    public static Boolean isInSlimeChunk(final ServerLevel level, final Player player) {
        if (player.getY() < 40 && level.dimension() == Level.OVERWORLD) {
            ChunkPos cpos = new ChunkPos(player.blockPosition());
            return WorldgenRandom.seedSlimeChunk(cpos.x, cpos.z, level.getSeed(), 987234911L).nextInt(10) == 0;
        }
        return false;
    }

    public static void displaySlimeParticle(final ServerLevel level, final ServerPlayer player, final Random random) {
        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();
        level.sendParticles(ParticleTypes.ITEM_SLIME, x, y, z, 10, 0.2d, 0.0d, 0.2d, 0.0d);
        level.playSound(null, x, y, z, SoundEvents.SLIME_SQUISH, SoundSource.PLAYERS, 0.2f,
                0.1f /* random.nextFloat() * 0.2f */);
    }
}
