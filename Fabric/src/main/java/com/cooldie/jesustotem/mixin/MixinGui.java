package com.cooldie.jesustotem.mixin;

import com.cooldie.jesustotem.controllers.ControllerClient;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class MixinGui {
    @Inject(
        method = "renderCameraOverlays(Lnet/minecraft/client/gui/GuiGraphics;F)V",
        at = @At("TAIL")
    )
    private void renderCameraOverlays(GuiGraphics graphics, float tickDelta, CallbackInfo info) {
        ControllerClient.jesusRenderer.render(graphics, tickDelta);
    }

    @Inject(method = "tick(Z)V", at = @At("TAIL"))
    private void tick(boolean pause, CallbackInfo info) {
        if (pause) {
            ControllerClient.jesusRenderer.pause();
        } else {
            ControllerClient.jesusRenderer.resume();
        }
    }
}
