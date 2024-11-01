package org.example.customHUD.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.network.ClientPlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainClient implements ClientModInitializer {

    public static final String MOD_ID = "custom-hud";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static MainClient instance;

    private Hud hud;

    @Override
    public void onInitializeClient() {
        instance = this;
        hud = new Hud();
        LOGGER.info("Hello Fabric Client World!");
    }

    public static MainClient getInstance() {
        return instance;
    }

}
