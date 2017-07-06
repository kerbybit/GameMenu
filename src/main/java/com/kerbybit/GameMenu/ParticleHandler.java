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
    static float partSize = 1;
    static float partPack = 5;
    static int partRed = 255;
    static int partGreen = 255;
    static int partBlue = 255;
    static Boolean partRainbow = false;
    static float partRainbowStripes = 10;
    static float partRainbowSpeed = 50;
    static float partXSpeed = 4;
    static float partYSpeed = 3;

    static float lineSize = 0.5f;
    static float lineMaxLength = 50;
    static int lineRed = 255;
    static int lineGreen = 255;
    static int lineBlue = 255;
    static Boolean lineRainbow = false;
    static float lineRainbowStripes = 10;
    static float lineRainbowSpeed = 50;

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
            for (int i = 0; i < (width + height) / partPack; i++) {
                partsX.add(Math.random() * width);
                partsY.add(Math.random() * height);
                partsXspeed.add((Math.random() * 2 - 1) / partXSpeed);
                partsYspeed.add((Math.random() / partYSpeed));
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

    private static float sysTime = Minecraft.getSystemTime();
    static void draw(int menu, int x, int y) {
        if (menu == 0) {
            alpha = 255;
        } else {
            if (alpha > 0)
                alpha -= 15;
            if (alpha < 0)
                alpha = 0;
        }

        Boolean doStep = false;
        if (Minecraft.getSystemTime() >= sysTime + 12L) {
            sysTime = Minecraft.getSystemTime();
            doStep = true;
        }

        for (int i=0; i<partsX.size(); i++) {
            if (doStep)
                step(i);

            drawLine(partsX.get(i), partsY.get(i), (double) x, (double) y, new Color(lineRed, lineGreen, lineBlue, alpha).getRGB(), lineRainbow);
            double size = partSize + partsYspeed.get(i);
            drawRect(partsX.get(i) - size, partsY.get(i) - size, partsX.get(i) + size, partsY.get(i) + size, new Color(partRed, partGreen, partBlue, alpha).getRGB(), partRainbow);
        }
    }

    private static void drawLine(Double x1, Double y1, Double x2, Double y2, int color, Boolean rainbow) {
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

            float a = (float) (1 - (distance / lineMaxLength));
            float r;
            float g;
            float b;

            if (rainbow) {
                double step = Main.ticksElapsed + (x1+y1)/lineRainbowStripes;
                r = (float) (((Math.sin(step / lineRainbowSpeed) + 0.75) * 170)/255);
                g = (float) (((Math.sin(step / lineRainbowSpeed + ((2 * Math.PI) / 3)) + 0.75) * 170)/255);
                b = (float) (((Math.sin(step / lineRainbowSpeed + ((4 * Math.PI) / 3)) + 0.75) * 170)/255);

                if (r < 0) r = 0;
                if (g < 0) g = 0;
                if (b < 0) b = 0;
                if (r > 1) r = 1;
                if (g > 1) g = 1;
                if (b > 1) b = 1;
            } else {
                r = (float) (color >> 16 & 255) / 255.0F;
                g = (float) (color >> 8 & 255) / 255.0F;
                b = (float) (color & 255) / 255.0F;
            }

            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldrenderer = tessellator.getWorldRenderer();

            GlStateManager.enableBlend();
            GlStateManager.disableTexture2D();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.color(r, g, b, a);

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

    private static void drawRect(Double left, Double top, Double right, Double bottom, int color, Boolean rainbow) {
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

        float a = (float) (color >> 24 & 255) / 255.0f;
        float r;
        float g;
        float b;
        if (rainbow) {
            double step = Main.ticksElapsed + (left+right)/partRainbowStripes;
            r = (float) (((Math.sin(step / partRainbowSpeed) + 0.75) * 170)/255);
            g = (float) (((Math.sin(step / partRainbowSpeed + ((2 * Math.PI) / 3)) + 0.75) * 170)/255);
            b = (float) (((Math.sin(step / partRainbowSpeed + ((4 * Math.PI) / 3)) + 0.75) * 170)/255);

            if (r < 0) r = 0;
            if (g < 0) g = 0;
            if (b < 0) b = 0;
            if (r > 1) r = 1;
            if (g > 1) g = 1;
            if (b > 1) b = 1;
        } else {
            r = (float) (color >> 16 & 255) / 255.0F;
            g = (float) (color >> 8 & 255) / 255.0F;
            b = (float) (color & 255) / 255.0F;
        }
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(r, g, b, a);
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
