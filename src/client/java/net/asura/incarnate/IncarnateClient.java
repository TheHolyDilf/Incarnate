package net.asura.incarnate;

import net.asura.incarnate.client.CursedEnergyClient;
import net.asura.incarnate.networking.ModPackets;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.Text;

public class IncarnateClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Register the S2C packet receiver
        ModPackets.registerS2CPackets();

        // Register HUD renderer (called every frame)
        HudRenderCallback.EVENT.register(this::renderCursedEnergy);
    }

    private void renderCursedEnergy(DrawContext context, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player == null) {
            return; // Don't render if not in-game
        }

        int width = context.getScaledWindowWidth();
        int height = context.getScaledWindowHeight();

        // Prepare the text to display
        String energyText = CursedEnergyClient.getEnergy() + " / " + CursedEnergyClient.getMaxEnergy() + " CE";
        Text text = Text.of(energyText);

        // Calculate text position (bottom-center of the screen)
        int textWidth = client.textRenderer.getWidth(text);
        int x = (width - textWidth) / 2;
        int y = height - 35; // A bit above the hotbar

        // Draw the text with a shadow
        context.drawTextWithShadow(client.textRenderer, text, x, y, 0xFFFFFF); // White color
    }
}
