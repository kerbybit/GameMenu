package com.kerbybit.GameMenu;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ChatComponentText;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

import static net.minecraft.realms.RealmsMth.floor;

public class MenuGUI extends GuiScreen {
    private static Minecraft MC = Minecraft.getMinecraft();

    private static int menu = 0;

    public static void openGui() {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Opening menu"));
        MC.displayGuiScreen(new MenuGUI());
    }

    @Override
    public void mouseClicked(int x, int y, int button) throws IOException {
        System.out.println("mouse clicked");
        super.mouseClicked(x, y, button);
    }

    private void mouseHover(int x, int y) {

    }

    @Override
    public void drawScreen(int x, int y, float ticks) {
        GL11.glColor4f(1, 1, 1, 1);
        drawDefaultBackground();
        System.out.println("drawing screen");

        ScaledResolution var5 = new ScaledResolution(MC);
        float width = var5.getScaledWidth();
        float height = var5.getScaledHeight();

        mouseHover(x, y);

        if (menu == 0) {
            drawVerticalLine(floor(width/2), 0, floor(height), 0xffffffff);
        }

        super.drawScreen(x, y, ticks);
    }
}
