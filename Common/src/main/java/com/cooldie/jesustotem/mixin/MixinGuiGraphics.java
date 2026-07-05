package com.cooldie.jesustotem.mixin;

import com.cooldie.jesustotem.controllers.ControllerClient;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiGraphics.class)
public class MixinGuiGraphics {
    @Shadow
    private void innerBlit(
        ResourceLocation location,
        int x1, int x2,
        int y1, int y2,
        int blitOffset,
        float minU, float maxU,
        float minV, float maxV,
        float red, float green, float blue, float alpha
    ) {};

    @Inject(
        method = "blit(Lnet/minecraft/resources/ResourceLocation;IIFFIIII)V",
        at = @At("HEAD"),
        cancellable = true
    )
    private void blit(
        ResourceLocation location,
        int x, int y,
        float uOffset, float vOffset,
        int width, int height,
        int textureWidth, int textureHeight,
        CallbackInfo info
    ) {
        if (ControllerClient.jesusRenderer.isJesusResource(location)) {
            float alpha = ControllerClient.jesusRenderer.getAlpha();
            innerBlit(
                location,
                x, x + width,
                y, y + height,
                0,
                uOffset / textureWidth, (uOffset + width) / textureWidth,
                vOffset / textureHeight, (vOffset + height) / textureHeight,
                1f, 1f, 1f, alpha
            );
            info.cancel();
        }
    }
}
