package com.kerbybit.GameMenu;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ParticleHandler {
    private static List<Double> partsX = new ArrayList<Double>();
    private static List<Double> partsY = new ArrayList<Double>();
    private static List<Double> partsXspeed = new ArrayList<Double>();
    private static List<Double> partsYspeed = new ArrayList<Double>();

    public static Boolean enabled = true;
    static float partSize = 1f;
    static int partRed = 255;
    static int partGreen = 255;
    static int partBlue = 255;

    static float lineSize = 0.5f;
    static float lineMaxLength = 50;
    static int lineRed = 255;
    static int lineGreen = 255;
    static int lineBlue = 255;

    private static float width;
    private static float height;

    private static int alpha = 0;

    static void init() {
        partsX.clear();
        partsY.clear();
        partsXspeed.clear();
        partsYspeed.clear();

        ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
        width = res.getScaledWidth();
        height = res.getScaledHeight();

        if (enabled) {
            for (int i = 0; i < (width + height) / 5; i++) {
                partsX.add(Math.random() * width);
                partsY.add(Math.random() * height);
                partsXspeed.add((Math.random() * 2 - 1) / 4);
                partsYspeed.add((Math.random() / 2));
            }
        }
    }

    private static void step(int i) {
        ScaledResolution newRes = new ScaledResolution(Minecraft.getMinecraft());
        if (width != newRes.getScaledWidth() || height != newRes.getScaledHeight())
            init();

        partsX.set(i, partsX.get(i) + partsXspeed.get(i));
        partsY.set(i, partsY.get(i) + partsYspeed.get(i));

        ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
        float width = res.getScaledWidth();
        float height = res.getScaledHeight();

        if (partsX.get(i) > width && partsXspeed.get(i) > 0)
            partsX.set(i, 0d);
        if (partsX.get(i) < 0 && partsXspeed.get(i) < 0)
            partsX.set(i, (double) width);
        if (partsY.get(i) > height && partsYspeed.get(i) > 0)
            partsY.set(i, 0d);
        if (partsY.get(i) < 0 && partsYspeed.get(i) < 0)
            partsY.set(i, (double) height);
    }

    static void draw(int menu, int x, int y) {
        if (menu == 0) {
            alpha = 255;
        } else {
            if (alpha > 0)
                alpha -= 15;
            if (alpha < 0)
                alpha = 0;
        }

        for (int i=0; i<partsX.size(); i++) {
            step(i);

            drawLine(partsX.get(i), partsY.get(i), (double) x, (double) y, new Color(lineRed, lineGreen, lineBlue, alpha).getRGB());
            drawRect(partsX.get(i) - partSize, partsY.get(i) - partSize, partsX.get(i) + partSize, partsY.get(i) + partSize, new Color(partRed, partGreen, partBlue, alpha).getRGB());
        }
    }

    private static void drawLine(Double x1, Double y1, Double x2, Double y2, int color) {
        double distance = Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2));
        if (distance < lineMaxLength) {
            double theta = -Math.atan2(y2 - y1, x2 - x1);
            double i = Math.sin(theta) * lineSize;
            double j = Math.cos(theta) * lineSize;

            double ax = x1 + i;
            double ay = y1 + j;
            double dx = x1 - i;
            double dy = y1 - j;

            double bx = x2 + i;
            double by = y2 + j;
            double cx = x2 - i;
            double cy = y2 - j;

            float f3 = (float) (color >> 24 & 255) / 255.0F;
            float f = (float) (color >> 16 & 255) / 255.0F;
            float f1 = (float) (color >> 8 & 255) / 255.0F;
            float f2 = (float) (color & 255) / 255.0F;

            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldrenderer = tessellator.getWorldRenderer();

            GlStateManager.enableBlend();
            GlStateManager.disableTexture2D();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.color(f, f1, f2, f3);

            worldrenderer.begin(7, DefaultVertexFormats.POSITION);
            worldrenderer.pos(ax, ay, 0.0D).endVertex();
            worldrenderer.pos(bx, by, 0.0D).endVertex();
            worldrenderer.pos(cx, cy, 0.0D).endVertex();
            worldrenderer.pos(dx, dy, 0.0D).endVertex();

            tessellator.draw();

            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
        }
    }

    private static void drawRect(Double left, Double top, Double right, Double bottom, int color) {
        if (left < right) {
            Double i = left;
            left = right;
            right = i;
        }

        if (top < bottom) {
            Double j = top;
            top = bottom;
            bottom = j;
        }

        float f3 = (float)(color >> 24 & 255) / 255.0F;
        float f = (float)(color >> 16 & 255) / 255.0F;
        float f1 = (float)(color >> 8 & 255) / 255.0F;
        float f2 = (float)(color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f3);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(left, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, top, 0.0D).endVertex();
        worldrenderer.pos(left, top, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
}
