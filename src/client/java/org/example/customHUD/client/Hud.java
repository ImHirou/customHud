package org.example.customHUD.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.joml.Matrix4f;

public class Hud {


    public Hud() {
        HudRenderCallback.EVENT.register(((drawContext, tickDeltaManager) -> {
            Matrix4f transformation = drawContext.getMatrices().peek().getPositionMatrix();
            Tessellator tessellator = Tessellator.getInstance();

            BufferBuilder buffer = tessellator.begin(VertexFormat.DrawMode.TRIANGLE_STRIP, VertexFormats.POSITION_COLOR);

            MatrixStack matrices = drawContext.getMatrices();

            MinecraftClient client = MinecraftClient.getInstance();
            if(client != null) {
                PlayerEntity player = client.player;
                float currentHealth = player.getHealth();
                float maxHealth = player.getMaxHealth();
                float hpRatio = currentHealth / maxHealth;

                buffer.vertex(transformation, 40, 20, 5).color(setRed(0xFF000000, (int) (960 * (hpRatio - 0.75f))));
                buffer.vertex(transformation, 20, 40, 5).color(setRed(0xFF000000, (int) (255 * (hpRatio + 0.15f))));
                buffer.vertex(transformation, 60, 40, 5).color(setRed(0xFF000000, (int) (255 * (hpRatio + 0.15f))));
                buffer.vertex(transformation, 40, 60, 5).color( setRed(0xFF000000, (int) (960 * (hpRatio - 0.75f))));

                drawEntity(drawContext, 20, -25, player);

                RenderSystem.setShader(GameRenderer::getPositionColorProgram);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

                BufferRenderer.drawWithGlobalProgram(buffer.end());
            }
        }));
    }

    private void drawEntity(DrawContext drawContext, int x, int y, LivingEntity entity) {
        InventoryScreen.drawEntity(drawContext,
                x, y, //first pos
                x+40, y+105, //last pos
                13,1F, //size
                (float)(x+50), (float)(y+55), //look pos
                entity); //model
    }

    public static int setRed(int originalColor, int red) {
        // Extract the alpha, green, and blue components
        int alpha = (originalColor >> 24) & 0xFF; // Get alpha
        int green = (originalColor >> 16) & 0xFF; // Get green
        int blue = (originalColor >> 8) & 0xFF;   // Get blue

        // Clamp red value between 0 and 255
        red = Math.min(255, Math.max(0, red));

        // Combine components back into a single ARGB color
        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    }

}
