package com.cooldie.jesustotem.renderer;

import com.cooldie.jesustotem.controllers.ControllerJesus;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class JesusRenderer {
    private static final ResourceLocation[] JESUS_TEXTURES;

    static {
        JESUS_TEXTURES = new ResourceLocation[3];
        JESUS_TEXTURES[0] = new ResourceLocation("jesustotem", "textures/misc/jesus0.png");
        JESUS_TEXTURES[1] = new ResourceLocation("jesustotem", "textures/misc/jesus1.png");
        JESUS_TEXTURES[2] = new ResourceLocation("jesustotem", "textures/misc/jesus2.png");
    }

    private final ControllerJesus jesusController = new ControllerJesus();
    private boolean paused = false;

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

    public void render(GuiGraphics graphics, float tickDelta) {
        ControllerJesus.Frame frame = jesusController.getFrame();
        if (frame.alpha() == 0f) {
            return;
        }

        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.defaultBlendFunc();
        graphics.setColor(1f, 1f, 1f, frame.alpha());

        int guiWidth = graphics.guiWidth();
        int guiHeight = graphics.guiHeight();
        graphics.blit(
            JESUS_TEXTURES[frame.index()],
            0, 0,
            0f, 0f,
            guiWidth, guiHeight,
            guiWidth, guiHeight
        );

        graphics.setColor(1f, 1f, 1f, 1f);
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
    }
}
