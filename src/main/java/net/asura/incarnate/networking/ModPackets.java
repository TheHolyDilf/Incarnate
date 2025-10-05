package net.asura.incarnate.networking;

import net.asura.incarnate.Incarnate;
import net.asura.incarnate.networking.packet.CursedEnergySyncS2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;

/**
 * Central registry for all mod packets.
 * This class handles the registration of both client-bound (S2C) and server-bound (C2S) packets.
 */
public class ModPackets {
    // It's good practice to keep your packet identifiers in a central place.
    // Let's move the SYNC_PACKET_ID here.
    public static final Identifier CURSED_ENERGY_SYNC_ID = new Identifier(Incarnate.MOD_ID, "cursed_energy_sync");

    /**
     * Registers all Server-to-Client (S2C) packets.
     * This should be called from your client-side initializer.
     */
    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(CURSED_ENERGY_SYNC_ID, CursedEnergySyncS2CPacket::receive);
    }
}