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

    public void spawn() {
        jesusController.spawn();
    }

    public void despawn() {
        jesusController.despawn();
    }

    public void render(GuiGraphics graphics, float tickDelta) {
        long time = System.currentTimeMillis();
        float jesusAlpha = jesusController.getAlpha(time);
        if (jesusAlpha == 0f) {
            return;
        }
        int jesusIndex = jesusController.getIndex(time);

        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.defaultBlendFunc();

        int guiWidth = graphics.guiWidth();
        int guiHeight = graphics.guiHeight();
        graphics.blit(
            JESUS_TEXTURES[jesusIndex],
            0, 0,
            0f, 0f,
            guiWidth, guiHeight,
            guiWidth, guiHeight
        );

        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
    }

    public boolean isJesusLocation(ResourceLocation location) {
        return location.getNamespace() == "jesustotem";
    }

    public float getAlpha() {
        return jesusController.getAlpha(System.currentTimeMillis());
    }
}
