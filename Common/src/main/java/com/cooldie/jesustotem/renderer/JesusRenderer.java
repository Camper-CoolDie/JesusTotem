package com.cooldie.jesustotem.renderer;

import com.cooldie.jesustotem.controllers.ControllerJesus;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.shaders.UniformType;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;

public class JesusRenderer {
    private static final ResourceLocation[] JESUS_TEXTURES;
    private static final RenderPipeline JESUS = RenderPipeline.builder()
        .withUniform("DynamicTransforms", UniformType.UNIFORM_BUFFER)
        .withUniform("Projection", UniformType.UNIFORM_BUFFER)
        .withVertexShader("core/position_tex_color")
        .withFragmentShader("core/position_tex_color")
        .withSampler("Sampler0")
        .withBlend(BlendFunction.TRANSLUCENT)
        .withVertexFormat(DefaultVertexFormat.POSITION_TEX_COLOR, VertexFormat.Mode.QUADS)
        .withLocation("pipeline/fire_screen_effect")
        .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
        .withDepthWrite(false)
        .build();

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
            JESUS,
            JESUS_TEXTURES[jesusIndex],
            0, 0,
            0f, 0f,
            guiWidth, guiHeight,
            guiWidth, guiHeight,
            ARGB.white(jesusAlpha)
        );
    }
}
