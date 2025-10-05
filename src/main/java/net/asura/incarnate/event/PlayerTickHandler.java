package net.asura.incarnate.event;

import net.asura.incarnate.networking.ModPackets;
import net.asura.incarnate.networking.packet.CursedEnergySyncS2CPacket;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;

/**
 * Handles server-side player events, specifically for ticking logic.
 */
public class PlayerTickHandler implements ServerTickEvents.StartTick {
    // Simple counter to mock changing energy values
    private int tickCounter = 0;

    @Override
    public void onStartTick(net.minecraft.server.MinecraftServer server) {
        // This event fires 20 times per second.
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            // Let's sync the data every second (20 ticks)
            if (tickCounter % 20 == 0) {
                // MOCK DATA: Replace this with your actual energy logic later
                int currentEnergy = (tickCounter / 20) % 100; // Example: energy cycles 0-99
                int maxEnergy = 100;

                // Create the packet with the data and send it to the client
                ServerPlayNetworking.send(player, ModPackets.CURSED_ENERGY_SYNC_ID, CursedEnergySyncS2CPacket.create(currentEnergy, maxEnergy));
            }
        }
        tickCounter++;
    }
}
