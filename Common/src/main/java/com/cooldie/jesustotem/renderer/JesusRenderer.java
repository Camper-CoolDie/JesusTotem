package com.cooldie.jesustotem.renderer;

import com.cooldie.jesustotem.controllers.ControllerJesus;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class JesusRenderer {
    private static final ResourceLocation[] JESUS_TEXTURES;

    static {
        JESUS_TEXTURES = new ResourceLocation[3];
        JESUS_TEXTURES[0] = ResourceLocation.fromNamespaceAndPath("jesustotem", "textures/misc/jesus0.png");
        JESUS_TEXTURES[1] = ResourceLocation.fromNamespaceAndPath("jesustotem", "textures/misc/jesus1.png");
        JESUS_TEXTURES[2] = ResourceLocation.fromNamespaceAndPath("jesustotem", "textures/misc/jesus2.png");
    }

    private final ControllerJesus jesusController = new ControllerJesus();
    private boolean paused = false;
    private float alpha = 0f;

    public void spawn() {
        jesusController.spawn(paused);
    }

    public void despawn() {
        jesusController.despawn();
    }

    public void pause() {
        if (!paused) {
            jesusController.pause();
            paused = true;
        }
    }

    public void resume() {
        if (paused) {
            jesusController.resume();
            paused = false;
        }
    }

    public void render(GuiGraphics graphics, DeltaTracker tickDelta) {
        ControllerJesus.Frame frame = jesusController.getFrame();
        if (frame.alpha() == 0f) {
            return;
        }

        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.defaultBlendFunc();
        alpha = frame.alpha();

        int guiWidth = graphics.guiWidth();
        int guiHeight = graphics.guiHeight();
        graphics.blit(
            JESUS_TEXTURES[frame.index()],
            0, 0,
            0f, 0f,
            guiWidth, guiHeight,
            guiWidth, guiHeight
        );

        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
    }

    public boolean isJesusResource(ResourceLocation location) {
        return location.getNamespace() == "jesustotem";
    }

    public float getAlpha() {
        return alpha;
    }
}
