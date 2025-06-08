package com.cooldie.jesustotem;

import com.cooldie.jesustotem.controllers.ControllerClient;
import com.cooldie.jesustotem.screens.ConfigScreen;
import java.io.File;
import net.minecraft.client.gui.screens.Screen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.javafmlmod.FMLModContainer;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = "jesustotem", dist = Dist.CLIENT)
public class JesusTotemClient {
    private static ControllerClient client;

    private static final File CONFIG_FILE = FMLPaths.CONFIGDIR.get().resolve("jesustotem.json").toFile();

    public JesusTotemClient(FMLModContainer container, IEventBus eventBus, Dist dist) {
        client = new ControllerClient(CONFIG_FILE);
        container.registerExtensionPoint(IConfigScreenFactory.class, new IConfigScreenFactory() {
            public Screen createScreen(ModContainer container, Screen screen) {
                return new ConfigScreen(screen);
            }
        });
    }
}
