package net.asura.incarnate.networking.packet;

import io.netty.buffer.Unpooled;
import net.asura.incarnate.client.CursedEnergyClient;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

/**
 * A custom server-to-client (S2C) packet to sync cursed energy data.
 * This packet is sent from the server to the client to update the player's energy values.
 */
public class CursedEnergySyncS2CPacket {

    /**
     * This method is called on the client thread when the packet is received.
     * It reads the data from the buffer and updates the client-side data.
     *
     * @param client       The Minecraft client instance.
     * @param handler      The client's network handler.
     * @param buf          The packet buffer containing the data.
     * @param responseSender A utility for sending response packets.
     */
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        // Read the energy and maxEnergy values from the buffer.
        // The order of reading must be the same as the order of writing on the server.
        int energy = buf.readInt();
        int maxEnergy = buf.readInt();

        // Ensure the update happens on the main client thread by calling the client-side handler.
        client.execute(() -> CursedEnergyClient.setEnergy(energy, maxEnergy));
    }

    /**
     * A helper method to create a buffer with the necessary data for this packet.
     * This would be called on the server side before sending the packet.
     *
     * @param energy     The current energy value to send.
     * @param maxEnergy  The maximum energy value to send.
     * @return A PacketByteBuf ready to be sent.
     */
    public static PacketByteBuf create(int energy, int maxEnergy) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeInt(energy);
        buf.writeInt(maxEnergy);
        return buf;
    }
}
