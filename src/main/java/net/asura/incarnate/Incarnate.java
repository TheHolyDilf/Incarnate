package net.asura.incarnate;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class Incarnate implements ModInitializer {
    public static final String MOD_ID = "incarnate";

    @Override
    public void onInitialize() {
        System.out.println("Incarnate mod loaded!");
        CursedEnergy.init();
    }

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }
}
