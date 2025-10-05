package net.asura.incarnate;

public class CursedEnergyClient {
    // client-side cached values (updated by packet receiver)
    private static volatile int energy = 100;
    private static volatile int maxEnergy = 100;

    public static void setEnergy(int e, int m) {
        energy = e;
        maxEnergy = m;
    }

    public static int getEnergy() {
        return energy;
    }

    public static int getMaxEnergy() {
        return maxEnergy;
    }
}
