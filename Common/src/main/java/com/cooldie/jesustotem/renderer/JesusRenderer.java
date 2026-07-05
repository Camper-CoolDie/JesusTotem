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

    public void render(GuiGraphics graphics, DeltaTracker tickDelta) {
        ControllerJesus.Frame frame = jesusController.getFrame();
        if (frame.alpha() == 0f) {
            return;
        }

        int guiWidth = graphics.guiWidth();
        int guiHeight = graphics.guiHeight();
        graphics.blit(
            RenderType::guiTexturedOverlay,
            JESUS_TEXTURES[frame.index()],
            0, 0,
            0f, 0f,
            guiWidth, guiHeight,
            guiWidth, guiHeight,
            ARGB.white(frame.alpha())
        );
    }
}
