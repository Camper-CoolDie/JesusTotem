package com.cooldie.jesustotem.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileReader;
import java.io.FileWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControllerConfig {
    private static final String JESUS_FADE_IN_KEY = "jesus_fade_in";
    private static final String JESUS_HOLD_KEY = "jesus_hold";
    private static final String JESUS_FADE_OUT_KEY = "jesus_fade_out";

    private static final Logger LOGGER = LoggerFactory.getLogger("JesusTotem config");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void load() {
        if (ControllerClient.configFile.exists()) {
            try {
                FileReader reader = new FileReader(ControllerClient.configFile);
                JsonElement json = JsonParser.parseReader(reader);
                JsonObject object = json.getAsJsonObject();

                long jesusFadeIn = object.get(JESUS_FADE_IN_KEY).getAsLong();
                long jesusHold = object.get(JESUS_HOLD_KEY).getAsLong();
                long jesusFadeOut = object.get(JESUS_FADE_OUT_KEY).getAsLong();

                ControllerJesus.fadeIn = jesusFadeIn;
                ControllerJesus.hold = jesusHold;
                ControllerJesus.fadeOut = jesusFadeOut;
                reader.close();
            } catch (Exception e) {
                LOGGER.error("Could not load config from '" + ControllerClient.configFile.getAbsolutePath() + "'", e);
            }
        }
    }

    public static void save() {
        try {
            JsonObject object = new JsonObject();
            object.addProperty(JESUS_FADE_IN_KEY, ControllerJesus.fadeIn);
            object.addProperty(JESUS_HOLD_KEY, ControllerJesus.hold);
            object.addProperty(JESUS_FADE_OUT_KEY, ControllerJesus.fadeOut);

            FileWriter writer = new FileWriter(ControllerClient.configFile);
            GSON.toJson(object, writer);
            writer.close();
        } catch (Exception e) {
            LOGGER.error("Could not save config to '" + ControllerClient.configFile.getAbsolutePath() + "'", e);
        }
    }
}
