package net.asura.incarnate.client;

/**
 * Stores client-side cursed energy data.
 * This data is synced from the server.
 */
public class CursedEnergyClient {
    private static int energy;
    private static int maxEnergy;

    public static void setEnergy(int energy, int maxEnergy) {
        CursedEnergyClient.energy = energy;
        CursedEnergyClient.maxEnergy = maxEnergy;
    }

    public static int getEnergy() {
        return energy;
    }

    public static int getMaxEnergy() {
        return maxEnergy;
    }
}
