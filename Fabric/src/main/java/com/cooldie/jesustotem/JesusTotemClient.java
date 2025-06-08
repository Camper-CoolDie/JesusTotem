package com.cooldie.jesustotem;

import com.cooldie.jesustotem.controllers.ControllerClient;
import java.io.File;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class JesusTotemClient implements ClientModInitializer {
    private static ControllerClient client;

    private static final File CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve("jesustotem.json").toFile();

    @Override
    public void onInitializeClient() {
        client = new ControllerClient(CONFIG_FILE);
    }
}
