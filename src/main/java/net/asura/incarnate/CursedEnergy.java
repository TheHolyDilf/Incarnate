package net.asura.incarnate;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.WeakHashMap;

public class CursedEnergy {
    public static final Identifier SYNC_PACKET_ID = new Identifier("incarnate", "sync_cursed_energy");
    private static final int MAX_ENERGY = 100;

    // Simple server-side map (WeakHashMap so entries get GC'd when players disconnect)
    private static final Map<ServerPlayerEntity, Integer> energyMap = new WeakHashMap<>();
    private static int tickCounter = 0;

    public static void init() {
        // Send initial sync when player joins
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.getPlayer();
            energyMap.putIfAbsent(player, MAX_ENERGY);
            syncEnergy(player);
        });

        // Server tick: count ticks and every 20 ticks (≈1 second) regenerate and sync
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            tickCounter++;
            if (tickCounter >= 20) {
                for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                    tickPlayer(player);
                    syncEnergy(player); // update client every second
                }
                tickCounter = 0;
            }
        });
    }

    private static void tickPlayer(ServerPlayerEntity player) {
        int current = getEnergy(player);
        if (current < MAX_ENERGY) {
            setEnergy(player, current + 1);
        }
    }

    public static int getEnergy(ServerPlayerEntity player) {
        return energyMap.getOrDefault(player, MAX_ENERGY);
    }

    public static void setEnergy(ServerPlayerEntity player, int value) {
        energyMap.put(player, Math.min(value, MAX_ENERGY));
    }

    public static void syncEnergy(ServerPlayerEntity player) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(getEnergy(player));
        buf.writeInt(MAX_ENERGY);
        ServerPlayNetworking.send(player, SYNC_PACKET_ID, buf);
    }

    // Optional NBT helpers if you later want to save/load
    public static void saveToNbt(ServerPlayerEntity player, NbtCompound nbt) {
        nbt.putInt("CursedEnergy", getEnergy(player));
    }

    public static void loadFromNbt(ServerPlayerEntity player, NbtCompound nbt) {
        if (nbt != null && nbt.contains("CursedEnergy")) {
            setEnergy(player, nbt.getInt("CursedEnergy"));
        } else {
            energyMap.putIfAbsent(player, MAX_ENERGY);
        }
    }
}
