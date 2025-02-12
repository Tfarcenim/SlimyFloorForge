package tfar.slimyfloor.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


import java.util.Random;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import tfar.slimyfloor.SlimyFloorForge;

@Mixin(Entity.class)
public class EntityMixin {

	@Shadow
	@Final
	protected Random random;

	@Inject(method = "playStepSound",
			at = @At(value = "INVOKE",
					target = "Lnet/minecraft/world/entity/Entity;playSound(Lnet/minecraft/sounds/SoundEvent;FF)V"))
	protected final void playStepSoundMixin(final CallbackInfo info) {
		// Only 10% of footsteps might produce particles
		// TODO Make this a configurable property
		if (random.nextFloat() >= 0.10f)
			return;
		if ((Object) this instanceof ServerPlayer player) {
            if (player.level instanceof ServerLevel serverLevel) {
				if (Boolean.TRUE.equals(SlimyFloorForge.isInSlimeChunk(serverLevel, player))) {
					SlimyFloorForge.displaySlimeParticle(serverLevel, player, random);
				}
			}
		}
	}
}
