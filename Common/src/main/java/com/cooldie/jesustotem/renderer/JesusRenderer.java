package com.cooldie.jesustotem.renderer;

import com.cooldie.jesustotem.controllers.ControllerJesus;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;

public class JesusRenderer {
    private static final ResourceLocation[] JESUS_TEXTURES;

    static {
        JESUS_TEXTURES = new ResourceLocation[3];
        JESUS_TEXTURES[0] = ResourceLocation.fromNamespaceAndPath("jesustotem", "textures/misc/jesus0.png");
        JESUS_TEXTURES[1] = ResourceLocation.fromNamespaceAndPath("jesustotem", "textures/misc/jesus1.png");
        JESUS_TEXTURES[2] = ResourceLocation.fromNamespaceAndPath("jesustotem", "textures/misc/jesus2.png");
    }

    private final ControllerJesus jesusController = new ControllerJesus();

    public void spawn() {
        jesusController.spawn();
    }

    public void despawn() {
        jesusController.despawn();
    }

    public void render(GuiGraphics graphics, DeltaTracker tickDelta) {
        long time = System.currentTimeMillis();
        float jesusAlpha = jesusController.getAlpha(time);
        if (jesusAlpha == 0f) {
            return;
        }
        int jesusIndex = jesusController.getIndex(time);

        int guiWidth = graphics.guiWidth();
        int guiHeight = graphics.guiHeight();
        graphics.blit(
            RenderType::guiTexturedOverlay,
            JESUS_TEXTURES[jesusIndex],
            0, 0,
            0f, 0f,
            guiWidth, guiHeight,
            guiWidth, guiHeight,
            ARGB.white(jesusAlpha)
        );
    }
}
