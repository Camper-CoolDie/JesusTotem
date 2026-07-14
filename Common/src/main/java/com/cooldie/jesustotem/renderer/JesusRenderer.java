package com.cooldie.jesustotem.renderer;

import com.cooldie.jesustotem.controllers.ControllerJesus;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.ColorTargetState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.shaders.UniformType;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;

public class JesusRenderer {
    private static final Identifier[] JESUS_TEXTURES;
    private static final RenderPipeline JESUS = RenderPipeline.builder()
        .withUniform("DynamicTransforms", UniformType.UNIFORM_BUFFER)
        .withUniform("Projection", UniformType.UNIFORM_BUFFER)
        .withVertexShader("core/position_tex_color")
        .withFragmentShader("core/position_tex_color")
        .withSampler("Sampler0")
        .withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
        .withVertexFormat(DefaultVertexFormat.POSITION_TEX_COLOR, VertexFormat.Mode.QUADS)
        .withLocation("pipeline/fire_screen_effect")
        .build();

    static {
        JESUS_TEXTURES = new Identifier[3];
        JESUS_TEXTURES[0] = Identifier.fromNamespaceAndPath("jesustotem", "textures/misc/jesus0.png");
        JESUS_TEXTURES[1] = Identifier.fromNamespaceAndPath("jesustotem", "textures/misc/jesus1.png");
        JESUS_TEXTURES[2] = Identifier.fromNamespaceAndPath("jesustotem", "textures/misc/jesus2.png");
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

    public void render(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
        ControllerJesus.Frame frame = jesusController.getFrame();
        if (frame.alpha() == 0f) {
            return;
        }

        int guiWidth = graphics.guiWidth();
        int guiHeight = graphics.guiHeight();
        graphics.blit(
            JESUS,
            JESUS_TEXTURES[frame.index()],
            0, 0,
            0f, 0f,
            guiWidth, guiHeight,
            guiWidth, guiHeight,
            ARGB.white(frame.alpha())
        );
    }
}
