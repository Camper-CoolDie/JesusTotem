package com.cooldie.jesustotem.controllers;

import com.cooldie.jesustotem.renderer.JesusRenderer;
import java.io.File;

public class ControllerClient {
    public static File configFile;
    public static JesusRenderer jesusRenderer;

    public ControllerClient(File configFile) {
        this.configFile = configFile;
        ControllerConfig.load();
        jesusRenderer = new JesusRenderer();
    }
}
