package com.cooldie.jesustotem.mixin;

import com.cooldie.jesustotem.controllers.ControllerClient;
import net.minecraft.client.DeltaTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DeltaTracker.Timer.class)
public class MixinTimer {
    @Inject(method = "updatePauseState(Z)V", at = @At("TAIL"))
    private void updatePauseState(boolean paused, CallbackInfo info) {
        if (paused) {
            ControllerClient.jesusRenderer.pause();
        } else {
            ControllerClient.jesusRenderer.resume();
        }
    }
}
