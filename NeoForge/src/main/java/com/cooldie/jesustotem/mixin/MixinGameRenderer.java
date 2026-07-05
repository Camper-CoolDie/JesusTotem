package com.cooldie.jesustotem.mixin;

import com.cooldie.jesustotem.controllers.ControllerClient;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class MixinGameRenderer {
    @Inject(
        method = "displayItemActivation(Lnet/minecraft/world/item/ItemStack;)V",
        at = @At("TAIL")
    )
    private void displayItemActivation(ItemStack stack, CallbackInfo info) {
        ControllerClient.jesusRenderer.spawn();
    }
}
