package com.kerbybit.GameMenu;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static net.minecraft.realms.RealmsMth.floor;

public class MenuGUI extends GuiScreen {
    private static Minecraft MC = Minecraft.getMinecraft();
    private static FontRenderer ren = MC.fontRendererObj;
    private static long sysTime = Minecraft.getSystemTime();

    private static int menu = 0;

    private static int buttonOffset_pvp = 0;
    private static int buttonOffset_arcade = 0;

    private static String buttonText_pvp = EnumChatFormatting.BLUE + "PvP Minigames";
    private static int buttonTextOffset_pvp = 0;
    private static int buttonUnderlineOffset_pvp = 0;
    private static String buttonText_arcade = EnumChatFormatting.DARK_GREEN + "Arcade Minigames";
    private static int buttonTextOffset_arcade = 0;
    private static int buttonUnderlineOffset_arcade = 0;

    private static HashMap<String, String> buttons_pvp = new HashMap<String, String>();
    private static List<Integer> buttonsOffset_pvp = new ArrayList<Integer>();
    private static HashMap<String, String> buttons_arcade = new HashMap<String, String>();
    private static List<Integer> buttonsOffset_arcade = new ArrayList<Integer>();

    private static String buttonText_exit = EnumChatFormatting.RED + "Exit";
    private static int buttonOffset_exit = -5;

    static void init() {
        buttons_pvp.put("Mega Walls", "mw");
        buttonsOffset_pvp.add(0);
        buttons_pvp.put("Blitz", "blitz");
        buttonsOffset_pvp.add(0);
        buttons_pvp.put("UHC Champions", "uhc");
        buttonsOffset_pvp.add(0);
        buttons_pvp.put("SkyWars", "skywars");
        buttonsOffset_pvp.add(0);
        buttons_pvp.put("SkyClash", "skyclash");
        buttonsOffset_pvp.add(0);
        buttons_pvp.put("Crazy Walls", "crazywalls");
        buttonsOffset_pvp.add(0);
        buttons_pvp.put("Speed UHC", "speeduhc");
        buttonsOffset_pvp.add(0);

        buttons_arcade.put("Arena Brawl", "arena");
        buttonsOffset_arcade.add(0);
        buttons_arcade.put("TKR", "tkr");
        buttonsOffset_arcade.add(0);
        buttons_arcade.put("Smash Heroes", "smash");
        buttonsOffset_arcade.add(0);
        buttons_arcade.put("Arcade Games", "arcade");
        buttonsOffset_arcade.add(0);
        buttons_arcade.put("TnT Games", "tnt");
        buttonsOffset_arcade.add(0);
        buttons_arcade.put("Warlords", "warlords");
        buttonsOffset_arcade.add(0);
        buttons_arcade.put("Cops and Crims", "cvc");
        buttonsOffset_arcade.add(0);
    }

    static void openGui() {
        if (Main.openMenu) {
            MC.displayGuiScreen(new MenuGUI());
            menu = -1;
            Main.openMenu = false;
        }
    }

    @Override
    public void mouseClicked(int x, int y, int button) throws IOException {
        if (button==0) {
            if (menu == 0) {
                if (y > height - (height/7)) {
                    MC.thePlayer.closeScreen();
                } else {
                    if (x < (width / 2) - 5) {
                        if (x > buttonTextOffset_pvp-100 && x < buttonTextOffset_pvp+100) {
                            int i = 0;
                            for (String value : buttons_pvp.values()) {
                                if (y > floor(height/4)+ 17 + (i*17) && y < floor(height/4)+ 31 + (i*17)) {
                                    MC.thePlayer.closeScreen();
                                    MC.thePlayer.sendChatMessage("/lobby " + value);
                                }
                                i++;
                            }
                        }
                    } else if (x > (width/2)+5) {
                        if (x > buttonTextOffset_arcade-100 && x < buttonTextOffset_arcade+100) {
                            int i = 0;
                            for (String value : buttons_arcade.values()) {
                                if (y > floor(height / 4) + 17 + (i * 17) && y < floor(height / 4) + 31 + (i * 17)) {
                                    MC.thePlayer.closeScreen();
                                    MC.thePlayer.sendChatMessage("/lobby " + value);
                                }
                                i++;
                            }
                        }
                    }
                }
            }
        }

        super.mouseClicked(x, y, button);
    }

    private void mouseHover(int x, int y) {
        if (menu == 0) {
            if (y > height - (height/7)) {
                buttonOffset_pvp = floor(Animation.interp(buttonOffset_pvp, -40, 5, 1));
                buttonTextOffset_pvp = floor(Animation.interp(buttonTextOffset_pvp, width/4, 5, 1));
                buttonUnderlineOffset_pvp = floor(Animation.interp(buttonUnderlineOffset_pvp, 0, 5, 1));
                buttonOffset_arcade = floor(Animation.interp(buttonOffset_arcade, -40, 5, 1));
                buttonTextOffset_arcade = floor(Animation.interp(buttonTextOffset_arcade, width - width/4, 5, 1));
                buttonUnderlineOffset_arcade = floor(Animation.interp(buttonUnderlineOffset_arcade, 0, 5, 1));
                for (int i=0; i<buttons_arcade.size(); i++) {
                    buttonsOffset_arcade.set(i, floor(Animation.interp(buttonsOffset_arcade.get(i), 0, 5, 1)));
                }
                for (int i=0; i<buttons_pvp.size(); i++) {
                    buttonsOffset_pvp.set(i, floor(Animation.interp(buttonsOffset_pvp.get(i), 0, 5, 1)));
                }
                buttonOffset_exit = floor(Animation.interp(buttonOffset_exit,  -height/7, 5, 1));
            } else {
                buttonOffset_exit = floor(Animation.interp(buttonOffset_exit, 0, 5, 1));
                if (x < (width/2)-5) {
                    for (int i=0; i<buttons_arcade.size(); i++) {
                        buttonsOffset_arcade.set(i, floor(Animation.interp(buttonsOffset_arcade.get(i), 0, 5, 1)));
                    }
                    buttonOffset_pvp = floor(Animation.interp(buttonOffset_pvp, 50, 5, 1));
                    buttonTextOffset_pvp = floor(Animation.interp(buttonTextOffset_pvp, width/3, 5, 1));
                    buttonUnderlineOffset_pvp = floor(Animation.interp(buttonUnderlineOffset_pvp, ren.getStringWidth(buttonText_pvp)/2, 5, 1));
                    buttonOffset_arcade = floor(Animation.interp(buttonOffset_arcade, -40, 5, 1));
                    buttonTextOffset_arcade = floor(Animation.interp(buttonTextOffset_arcade, width - width/4, 5, 1));
                    buttonUnderlineOffset_arcade = floor(Animation.interp(buttonUnderlineOffset_arcade, 0, 5, 1));

                    if (x > buttonTextOffset_pvp-100 && x < buttonTextOffset_pvp+100) {
                        for (int i=0; i<buttons_pvp.size(); i++) {
                            if (y > floor(height/4)+ 17 + (i*17) && y < floor(height/4)+ 31 + (i*17)) {
                                buttonsOffset_pvp.set(i, floor(Animation.interp(buttonsOffset_pvp.get(i), 25, 5, 1)));
                            } else {
                                buttonsOffset_pvp.set(i, floor(Animation.interp(buttonsOffset_pvp.get(i), 0, 5, 1)));
                            }
                        }
                    } else {
                        for (int i=0; i<buttons_pvp.size(); i++) {
                            buttonsOffset_pvp.set(i, floor(Animation.interp(buttonsOffset_pvp.get(i), 0, 5, 1)));
                        }
                    }
                } else if (x > (width/2)+5) {
                    for (int i=0; i<buttons_pvp.size(); i++) {
                        buttonsOffset_pvp.set(i, floor(Animation.interp(buttonsOffset_pvp.get(i), 0, 5, 1)));
                    }
                    buttonOffset_pvp = floor(Animation.interp(buttonOffset_pvp, -40, 5, 1));
                    buttonTextOffset_pvp = floor(Animation.interp(buttonTextOffset_pvp, width/4, 5, 1));
                    buttonUnderlineOffset_pvp = floor(Animation.interp(buttonUnderlineOffset_pvp, 0, 5, 1));
                    buttonOffset_arcade = floor(Animation.interp(buttonOffset_arcade, 50, 5, 1));
                    buttonTextOffset_arcade = floor(Animation.interp(buttonTextOffset_arcade, width - width/3, 5, 1));
                    buttonUnderlineOffset_arcade = floor(Animation.interp(buttonUnderlineOffset_arcade, ren.getStringWidth(buttonText_arcade)/2, 5, 1));

                    if (x > buttonTextOffset_arcade-100 && x < buttonTextOffset_arcade+100) {
                        for (int i=0; i<buttons_arcade.size(); i++) {
                            if (y > floor(height/4)+ 17 + (i*17) && y < floor(height/4)+ 31 + (i*17)) {
                                buttonsOffset_arcade.set(i, floor(Animation.interp(buttonsOffset_arcade.get(i), 25, 5, 1)));
                            } else {
                                buttonsOffset_arcade.set(i, floor(Animation.interp(buttonsOffset_arcade.get(i), 0, 5, 1)));
                            }
                        }
                    } else {
                        for (int i=0; i<buttons_arcade.size(); i++) {
                            buttonsOffset_arcade.set(i, floor(Animation.interp(buttonsOffset_arcade.get(i), 0, 5, 1)));
                        }
                    }
                }
            }
        }
    }

    @Override
    public void drawScreen(int x, int y, float ticks) {
        GL11.glColor4f(1, 1, 1, 1);
        drawDefaultBackground();

        ScaledResolution var5 = new ScaledResolution(MC);
        float width = var5.getScaledWidth();
        float height = var5.getScaledHeight();

        while (Minecraft.getSystemTime() >= sysTime + 12L) {
            sysTime += 12L;
            mouseHover(x, y);
        }


        if (menu == -1) {
            buttonTextOffset_pvp = floor(width/4);
            buttonTextOffset_arcade = floor(width - width/4);
            buttonOffset_pvp = 0;
            buttonOffset_arcade = 0;
            buttonUnderlineOffset_pvp = 0;
            buttonUnderlineOffset_arcade = 0;
            for (int i=0; i<buttonsOffset_pvp.size(); i++) {
                buttonsOffset_pvp.set(i, 0);
            }
            for (int i=0; i<buttonsOffset_arcade.size(); i++) {
                buttonsOffset_arcade.set(i, 0);
            }
            buttonOffset_exit = -5;
            menu = 0;
        }

        if (menu == 0) {
            drawRect(0, 0, floor(width/2) - 5 + buttonOffset_pvp, floor(height), 0x50000000);
            drawCenteredString(ren, buttonText_pvp, buttonTextOffset_pvp, floor(height/4), 0xffffffff);
            if (buttonUnderlineOffset_pvp > 0) {
                drawHorizontalLine(buttonTextOffset_pvp - buttonUnderlineOffset_pvp, buttonTextOffset_pvp + buttonUnderlineOffset_pvp, floor(height/4)+10, 0xffffffff);
            }

            int i = 0;
            for (String button : buttons_pvp.keySet()) {
                int drawX = buttonTextOffset_pvp;
                int drawW = 100;
                int drawO = buttonsOffset_pvp.get(i);
                int drawY = floor(height/4)+20 + (i*17);

                drawRect(drawX-(drawW/2)-3-drawO, drawY-3, drawX+(drawW/2)+3+drawO, drawY+11, 0xff000000);
                drawCenteredString(ren, button, drawX, drawY, 0xffffffff);
                i++;
            }

            drawRect(floor(width/2) + 5 - buttonOffset_arcade, 0, floor(width), floor(height), 0x50000000);
            drawCenteredString(ren, buttonText_arcade, buttonTextOffset_arcade, floor(height/4), 0xffffffff);
            if (buttonUnderlineOffset_arcade > 0) {
                drawHorizontalLine(buttonTextOffset_arcade - buttonUnderlineOffset_arcade, buttonTextOffset_arcade + buttonUnderlineOffset_arcade, floor(height/4)+10, 0xffffffff);
            }

            i = 0;
            for (String button : buttons_arcade.keySet()) {
                int drawX = buttonTextOffset_arcade;
                int drawW = 100;
                int drawO = buttonsOffset_arcade.get(i);
                int drawY = floor(height/4)+20 + (i*17);

                drawRect(drawX-(drawW/2)-3-drawO, drawY-3, drawX+(drawW/2)+3+drawO, drawY+11, 0xff000000);
                drawCenteredString(ren, button, drawX, drawY, 0xffffffff);
                i++;
            }

            drawRect(0, floor(height + buttonOffset_exit), floor(width), floor(height), 0xff000000);
            drawCenteredString(ren, buttonText_exit, floor(width / 2), floor(height + buttonOffset_exit + 12), 0xffffffff);
        }

        super.drawScreen(x, y, ticks);
    }
}
