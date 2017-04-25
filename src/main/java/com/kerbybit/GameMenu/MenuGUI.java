package com.kerbybit.GameMenu;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.*;

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

    private static List<String> buttons_names_pvp = new ArrayList<String>();
    private static List<String> buttons_commands_pvp = new ArrayList<String>();
    private static List<Integer> buttonsOffset_pvp = new ArrayList<Integer>();
    private static List<Item> buttons_icons_pvp = new ArrayList<Item>();
    private static List<String> buttons_names_arcade = new ArrayList<String>();
    private static List<String> buttons_commands_arcade = new ArrayList<String>();
    private static List<Integer> buttonsOffset_arcade = new ArrayList<Integer>();
    private static List<Item> buttons_icons_arcade = new ArrayList<Item>();

    private static String buttonText_exit = EnumChatFormatting.RED + "Exit";
    private static int buttonOffset_exit = -5;

    private static String buttonText_classic = EnumChatFormatting.GOLD + "Hypixel Classics";
    private static int buttonOffset_classic = 5;

    static void init() {

        buttons_names_pvp.add("Blitz");
        buttons_commands_pvp.add("blitz");
        buttonsOffset_pvp.add(0);
        buttons_icons_pvp.add(Item.getByNameOrId("diamond_sword"));
        buttons_names_pvp.add("SkyWars");
        buttons_commands_pvp.add("skywars");
        buttonsOffset_pvp.add(0);
        buttons_icons_pvp.add(Item.getByNameOrId("ender_eye"));
        buttons_names_pvp.add("SkyClash");
        buttons_commands_pvp.add("skyclash");
        buttonsOffset_pvp.add(0);
        buttons_icons_pvp.add(Item.getByNameOrId("fire_charge"));
        buttons_names_pvp.add("Mega Walls");
        buttons_commands_pvp.add("mw");
        buttonsOffset_pvp.add(0);
        buttons_icons_pvp.add(Item.getByNameOrId("soul_sand"));
        buttons_names_pvp.add("Crazy Walls");
        buttons_commands_pvp.add("crazywalls");
        buttonsOffset_pvp.add(0);
        buttons_icons_pvp.add(Item.getByNameOrId("skull"));
        buttons_names_pvp.add("UHC Champions");
        buttons_commands_pvp.add("uhc");
        buttonsOffset_pvp.add(0);
        buttons_icons_pvp.add(Item.getByNameOrId("golden_apple"));
        buttons_names_pvp.add("Speed UHC");
        buttons_commands_pvp.add("speeduhc");
        buttonsOffset_pvp.add(0);
        buttons_icons_pvp.add(Item.getByNameOrId("golden_carrot"));

        buttons_names_arcade.add("Arcade Games");
        buttons_commands_arcade.add("arcade");
        buttonsOffset_arcade.add(0);
        buttons_icons_arcade.add(Item.getByNameOrId("slime_ball"));
        buttons_names_arcade.add("Housing");
        buttons_commands_arcade.add("main");
        buttonsOffset_arcade.add(0);
        buttons_icons_arcade.add(Item.getByNameOrId("dark_oak_door"));
        buttons_names_arcade.add("Smash Heroes");
        buttons_commands_arcade.add("smash");
        buttonsOffset_arcade.add(0);
        buttons_icons_arcade.add(Item.getByNameOrId("skull"));
        buttons_names_arcade.add("TnT Games");
        buttons_commands_arcade.add("tnt");
        buttonsOffset_arcade.add(0);
        buttons_icons_arcade.add(Item.getByNameOrId("tnt"));
        buttons_names_arcade.add("Warlords");
        buttons_commands_arcade.add("warlords");
        buttonsOffset_arcade.add(0);
        buttons_icons_arcade.add(Item.getByNameOrId("stone_axe"));
        buttons_names_arcade.add("Cops v Crims");
        buttons_commands_arcade.add("cvc");
        buttonsOffset_arcade.add(0);
        buttons_icons_arcade.add(Item.getByNameOrId("iron_bars"));
        buttons_names_arcade.add("Prototype");
        buttons_commands_arcade.add("ptl");
        buttonsOffset_arcade.add(0);
        buttons_icons_arcade.add(Item.getByNameOrId("anvil"));
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
                    menu = 1;
                } else if (y < height/7) {
                    MC.thePlayer.closeScreen();
                    MC.thePlayer.sendChatMessage("/lobby classic");
                } else {
                    if (x < (width / 2) - 5) {
                        if (x > buttonTextOffset_pvp-100 && x < buttonTextOffset_pvp+100) {
                            int i = 0;
                            for (String value : buttons_commands_pvp) {
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
                            for (String value : buttons_commands_arcade) {
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
        if (menu == 0 || menu == -1) {
            if (y > height - (height/7)) {
                buttonOffset_pvp = floor(Animation.interp(buttonOffset_pvp, -100, 5, 1));
                buttonTextOffset_pvp = floor(Animation.interp(buttonTextOffset_pvp, width/4, 5, 1));
                buttonUnderlineOffset_pvp = floor(Animation.interp(buttonUnderlineOffset_pvp, 0, 5, 1));
                buttonOffset_arcade = floor(Animation.interp(buttonOffset_arcade, -100, 5, 1));
                buttonTextOffset_arcade = floor(Animation.interp(buttonTextOffset_arcade, width - width/4, 5, 1));
                buttonUnderlineOffset_arcade = floor(Animation.interp(buttonUnderlineOffset_arcade, 0, 5, 1));
                for (int i=0; i<buttons_names_arcade.size(); i++) {
                    buttonsOffset_arcade.set(i, floor(Animation.interp(buttonsOffset_arcade.get(i), 0, 5, 1)));
                }
                for (int i=0; i<buttons_names_pvp.size(); i++) {
                    buttonsOffset_pvp.set(i, floor(Animation.interp(buttonsOffset_pvp.get(i), 0, 5, 1)));
                }
                buttonOffset_exit = floor(Animation.interp(buttonOffset_exit,  -height/7, 5, 1));
                buttonOffset_classic = floor(Animation.interp(buttonOffset_classic, 5, 5, 1));
            } else if (y < height/7) {
                buttonOffset_pvp = floor(Animation.interp(buttonOffset_pvp, -100, 5, 1));
                buttonTextOffset_pvp = floor(Animation.interp(buttonTextOffset_pvp, width/4, 5, 1));
                buttonUnderlineOffset_pvp = floor(Animation.interp(buttonUnderlineOffset_pvp, 0, 5, 1));
                buttonOffset_arcade = floor(Animation.interp(buttonOffset_arcade, -100, 5, 1));
                buttonTextOffset_arcade = floor(Animation.interp(buttonTextOffset_arcade, width - width/4, 5, 1));
                buttonUnderlineOffset_arcade = floor(Animation.interp(buttonUnderlineOffset_arcade, 0, 5, 1));
                for (int i=0; i<buttons_names_arcade.size(); i++) {
                    buttonsOffset_arcade.set(i, floor(Animation.interp(buttonsOffset_arcade.get(i), 0, 5, 1)));
                }
                for (int i=0; i<buttons_names_pvp.size(); i++) {
                    buttonsOffset_pvp.set(i, floor(Animation.interp(buttonsOffset_pvp.get(i), 0, 5, 1)));
                }
                buttonOffset_exit = floor(Animation.interp(buttonOffset_exit,  0, 5, 1));
                buttonOffset_classic = floor(Animation.interp(buttonOffset_classic, height/7, 5, 1));
            } else {
                buttonOffset_classic = floor(Animation.interp(buttonOffset_classic, 5, 5, 1));
                buttonOffset_exit = floor(Animation.interp(buttonOffset_exit, 0, 5, 1));
                if (x < width/2) {
                    for (int i=0; i<buttons_names_arcade.size(); i++) {
                        buttonsOffset_arcade.set(i, floor(Animation.interp(buttonsOffset_arcade.get(i), 0, 5, 1)));
                    }
                    buttonOffset_pvp = floor(Animation.interp(buttonOffset_pvp, 50, 5, 1));
                    buttonTextOffset_pvp = floor(Animation.interp(buttonTextOffset_pvp, width/3, 5, 1));
                    buttonUnderlineOffset_pvp = floor(Animation.interp(buttonUnderlineOffset_pvp, ren.getStringWidth(buttonText_pvp)/2, 5, 1));
                    buttonOffset_arcade = floor(Animation.interp(buttonOffset_arcade, -40, 5, 1));
                    buttonTextOffset_arcade = floor(Animation.interp(buttonTextOffset_arcade, width - width/4, 5, 1));
                    buttonUnderlineOffset_arcade = floor(Animation.interp(buttonUnderlineOffset_arcade, 0, 5, 1));

                    if (x > buttonTextOffset_pvp-100 && x < buttonTextOffset_pvp+100) {
                        for (int i=0; i<buttons_names_pvp.size(); i++) {
                            if (y > floor(height/4)+ 17 + (i*17) && y < floor(height/4)+ 31 + (i*17)) {
                                buttonsOffset_pvp.set(i, floor(Animation.interp(buttonsOffset_pvp.get(i), 25, 5, 1)));
                            } else {
                                buttonsOffset_pvp.set(i, floor(Animation.interp(buttonsOffset_pvp.get(i), 0, 5, 1)));
                            }
                        }
                    } else {
                        for (int i=0; i<buttons_names_pvp.size(); i++) {
                            buttonsOffset_pvp.set(i, floor(Animation.interp(buttonsOffset_pvp.get(i), 0, 5, 1)));
                        }
                    }
                } else {
                    for (int i=0; i<buttons_names_pvp.size(); i++) {
                        buttonsOffset_pvp.set(i, floor(Animation.interp(buttonsOffset_pvp.get(i), 0, 5, 1)));
                    }
                    buttonOffset_pvp = floor(Animation.interp(buttonOffset_pvp, -40, 5, 1));
                    buttonTextOffset_pvp = floor(Animation.interp(buttonTextOffset_pvp, width/4, 5, 1));
                    buttonUnderlineOffset_pvp = floor(Animation.interp(buttonUnderlineOffset_pvp, 0, 5, 1));
                    buttonOffset_arcade = floor(Animation.interp(buttonOffset_arcade, 50, 5, 1));
                    buttonTextOffset_arcade = floor(Animation.interp(buttonTextOffset_arcade, width - width/3, 5, 1));
                    buttonUnderlineOffset_arcade = floor(Animation.interp(buttonUnderlineOffset_arcade, ren.getStringWidth(buttonText_arcade)/2, 5, 1));

                    if (x > buttonTextOffset_arcade-100 && x < buttonTextOffset_arcade+100) {
                        for (int i=0; i<buttons_names_arcade.size(); i++) {
                            if (y > floor(height/4)+ 17 + (i*17) && y < floor(height/4)+ 31 + (i*17)) {
                                buttonsOffset_arcade.set(i, floor(Animation.interp(buttonsOffset_arcade.get(i), 25, 5, 1)));
                            } else {
                                buttonsOffset_arcade.set(i, floor(Animation.interp(buttonsOffset_arcade.get(i), 0, 5, 1)));
                            }
                        }
                    } else {
                        for (int i=0; i<buttons_names_arcade.size(); i++) {
                            buttonsOffset_arcade.set(i, floor(Animation.interp(buttonsOffset_arcade.get(i), 0, 5, 1)));
                        }
                    }
                }
            }
        } else {
            buttonOffset_pvp = floor(Animation.interp(buttonOffset_pvp, -width/2-20, 10, 1));
            buttonTextOffset_pvp = floor(Animation.interp(buttonTextOffset_pvp, -width/2-20 + width/4, 10, 1));
            buttonUnderlineOffset_pvp = floor(Animation.interp(buttonUnderlineOffset_pvp, 0, 5, 1));

            buttonOffset_arcade = floor(Animation.interp(buttonOffset_arcade, -width/2-20, 10, 1));
            buttonTextOffset_arcade = floor(Animation.interp(buttonTextOffset_arcade, width/2+20 + width - width/4, 10, 1));
            buttonUnderlineOffset_arcade = floor(Animation.interp(buttonUnderlineOffset_arcade, 0, 6, 1));

            for (int i=0; i<buttons_names_arcade.size(); i++) {
                buttonsOffset_arcade.set(i, floor(Animation.interp(buttonsOffset_arcade.get(i), 0, 5, 1)));
            }
            for (int i=0; i<buttons_names_pvp.size(); i++) {
                buttonsOffset_pvp.set(i, floor(Animation.interp(buttonsOffset_pvp.get(i), 0, 5, 1)));
            }

            buttonOffset_exit = floor(Animation.interp(buttonOffset_exit,  21, 10, 1));
            buttonOffset_classic = floor(Animation.interp(buttonOffset_classic, -1, 10, 1));

            if (buttonOffset_exit == 12) {
                MC.thePlayer.closeScreen();
            }
        }
    }

    @Override
    public void drawScreen(int x, int y, float ticks) {
        GL11.glColor4f(1, 1, 1, 1);
        //drawDefaultBackground();

        ScaledResolution var5 = new ScaledResolution(MC);
        float width = var5.getScaledWidth();
        float height = var5.getScaledHeight();

        while (Minecraft.getSystemTime() >= sysTime + 12L) {
            sysTime += 12L;
            mouseHover(x, y);
        }


        if (menu == -1) {
            buttonTextOffset_pvp = floor(-width/2-20);
            buttonTextOffset_arcade = floor(width/2+20 + width - width/4);
            buttonOffset_pvp = floor(-width/2-20);
            buttonOffset_arcade = floor(-width/2-20);
            buttonUnderlineOffset_pvp = 0;
            buttonUnderlineOffset_arcade = 0;
            for (int i=0; i<buttonsOffset_pvp.size(); i++) {
                buttonsOffset_pvp.set(i, 0);
            }
            for (int i=0; i<buttonsOffset_arcade.size(); i++) {
                buttonsOffset_arcade.set(i, 0);
            }
            buttonOffset_exit = -5;
            buttonOffset_classic = 5;
            menu = 0;
        }

        if (menu == 0 || menu == 1) {
            drawRect(0, 0, floor(width/2) - 5 + buttonOffset_pvp, floor(height), 0x50000000);
            drawCenteredString(ren, buttonText_pvp, buttonTextOffset_pvp, floor(height/4), 0xffffffff);
            if (buttonUnderlineOffset_pvp > 0) {
                drawHorizontalLine(buttonTextOffset_pvp - buttonUnderlineOffset_pvp, buttonTextOffset_pvp + buttonUnderlineOffset_pvp, floor(height/4)+10, 0xffffffff);
            }

            int i = 0;
            for (String button : buttons_names_pvp) {
                int drawX = buttonTextOffset_pvp;
                int drawW = 100;
                int drawO = buttonsOffset_pvp.get(i);
                ItemStack itemStack = new ItemStack(buttons_icons_pvp.get(i), 1);
                int drawY = floor(height/4)+20 + (i*17);

                drawRect(drawX-(drawW/2)-3-drawO, drawY-3, drawX+(drawW/2)+3+drawO, drawY+11, 0xff00102b);
                drawCenteredString(ren, button, drawX, drawY, 0xffffffff);
                drawItemIcon(drawX-(drawW/2)-3-(drawO/2), drawY-4, itemStack);
                drawItemIcon(drawX+(drawW/2)-13+(drawO/2), drawY-4, itemStack);
                i++;
            }

            drawRect(floor(width/2) + 5 - buttonOffset_arcade, 0, floor(width), floor(height), 0x50000000);
            drawCenteredString(ren, buttonText_arcade, buttonTextOffset_arcade, floor(height/4), 0xffffffff);
            if (buttonUnderlineOffset_arcade > 0) {
                drawHorizontalLine(buttonTextOffset_arcade - buttonUnderlineOffset_arcade, buttonTextOffset_arcade + buttonUnderlineOffset_arcade, floor(height/4)+10, 0xffffffff);
            }

            i = 0;
            for (String button : buttons_names_arcade) {
                int drawX = buttonTextOffset_arcade;
                int drawW = 100;
                int drawO = buttonsOffset_arcade.get(i);
                ItemStack itemStack = new ItemStack(buttons_icons_arcade.get(i), 1);
                int drawY = floor(height/4)+20 + (i*17);

                drawRect(drawX-(drawW/2)-3-drawO, drawY-3, drawX+(drawW/2)+3+drawO, drawY+11, 0xff001c03);
                drawCenteredString(ren, button, drawX, drawY, 0xffffffff);
                drawItemIcon(drawX-(drawW/2)-3-(drawO/2), drawY-4, itemStack);
                drawItemIcon(drawX+(drawW/2)-13+(drawO/2), drawY-4, itemStack);
                i++;
            }

            drawRect(0, floor(height + buttonOffset_exit), floor(width), floor(height), 0xff000000);
            drawCenteredString(ren, buttonText_exit, floor(width / 2), floor(height + buttonOffset_exit/2), 0xffffffff);

            drawRect(0, floor(buttonOffset_classic), floor(width), 0, 0xff000000);
            drawCenteredString(ren, buttonText_classic, floor(width/2), floor(buttonOffset_classic/2 - 10), 0xffffffff);
        }

        super.drawScreen(x, y, ticks);
    }

    private static void drawItemIcon(int x, int y, ItemStack itemStack) {
        RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();

        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderHelper.enableStandardItemLighting();
        RenderHelper.enableGUIStandardItemLighting();
        itemRenderer.zLevel = 200.0F;

        itemRenderer.renderItemIntoGUI(itemStack, x, y);
    }
}
