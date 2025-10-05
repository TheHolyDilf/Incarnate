package net.asura.incarnate;

import net.asura.incarnate.event.PlayerTickHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Incarnate implements ModInitializer {
    public static final String MOD_ID = "incarnate";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        // Register the server-side tick handler to send energy updates
        ServerTickEvents.START_SERVER_TICK.register(new PlayerTickHandler());
    }
}
