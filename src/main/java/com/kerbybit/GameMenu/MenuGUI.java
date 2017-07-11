package com.kerbybit.GameMenu;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kerbybit.GameMenu.command.CommandMenu;
import com.kerbybit.GameMenu.util.Animation;
import com.kerbybit.GameMenu.util.FileHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.*;
import java.util.List;

import static net.minecraft.realms.RealmsMth.floor;

public class MenuGUI extends GuiScreen {
    private static Minecraft MC = Minecraft.getMinecraft();
    private static FontRenderer ren = MC.fontRendererObj;
    private static long sysTime = Minecraft.getSystemTime();

    private static int menu = 0;

    private static float animationSpeed = 5;

    private static int offset_menu_left = 0;
    private static int offset_menu_right = 0;

    private static String text_left;
    private static int offset_left = 0;
    private static int underline_left = 0;
    
    private static String text_right;
    private static int offset_right = 0;
    private static int underline_right = 0;

    private static List<String> left_texts = new ArrayList<String>();
    private static List<String> left_commands = new ArrayList<String>();
    private static List<Integer> left_offsets = new ArrayList<Integer>();
    private static List<Item> left_icons = new ArrayList<Item>();
    private static List<String> left_icons_extra = new ArrayList<String>();
    
    
    private static List<String> right_texts = new ArrayList<String>();
    private static List<String> right_commands = new ArrayList<String>();
    private static List<Integer> right_offsets = new ArrayList<Integer>();
    private static List<Item> right_icons = new ArrayList<Item>();
    private static List<String> right_icons_extra = new ArrayList<String>();

    private static String text_bottom;
    private static int offset_bottom = -5;
    private static String command_bottom;
    private static float color_left;

    private static String text_top;
    private static int offset_top = 5;
    private static String command_top;
    private static float color_right;

    private static float color_top;
    private static float color_bottom;

    public static void init() {
        left_texts.clear();
        left_commands.clear();
        left_offsets.clear();
        left_icons.clear();
        left_icons_extra.clear();
        right_texts.clear();
        right_commands.clear();
        right_offsets.clear();
        right_icons.clear();
        right_icons_extra.clear();

        JsonObject menuJson = new JsonObject();

        FileHandler.checkDir();
        try {
            menuJson = FileHandler.loadFile();
        } catch (IOException e) {
            try {
                FileHandler.generateFile();
                menuJson = FileHandler.loadFile();
            } catch (IOException exception) {
                e.printStackTrace();
                CommandMenu.showMessage(EnumChatFormatting.RED + "Unable to load menu.json! IOException");
            }
        } catch (Exception e) {
            e.printStackTrace();
            CommandMenu.showMessage(EnumChatFormatting.RED + "Unable to load menu.json! Unknown Exception");
        }

        String tempSettingString = FileHandler.getValue(menuJson, "settings.open with hypixel compass");
        Main.openWithCompass = tempSettingString.equals("null") || tempSettingString.equals("true");
        tempSettingString = FileHandler.getValue(menuJson, "settings.check extra fast (may cause lag)");
        Main.checkFast = tempSettingString.equals("null") || tempSettingString.equals("true");

        animationSpeed = getFloat(menuJson, "settings.animation speed", 5);

        tempSettingString = FileHandler.getValue(menuJson, "settings.particles.enabled");
        ParticleHandler.enabled = tempSettingString.equals("null") || tempSettingString.equals("true");

        ParticleHandler.partSize = getFloat(menuJson, "settings.particles.particle size", 1, true);
        ParticleHandler.partPack = getFloat(menuJson, "settings.particles.particle packing", 5);
        ParticleHandler.partXSpeed = getFloat(menuJson, "settings.particles.particle x speed", 4);
        ParticleHandler.partYSpeed = getFloat(menuJson, "settings.particles.particle y speed", 3);
        ParticleHandler.lineSize = getFloat(menuJson, "settings.particles.line thickness", 0.25f, true);
        ParticleHandler.lineMaxLength = getFloat(menuJson, "settings.particles.line max length", 50);
        ParticleHandler.lineType = getInt(menuJson, "settings.particles.line type", 0);

        ParticleHandler.partRainbow = FileHandler.getValue(menuJson, "settings.particles.particle color.rainbow").equals("true");
        ParticleHandler.partRainbowSpeed = getFloat(menuJson, "settings.particles.particle color.rainbow speed", 50);
        ParticleHandler.partRainbowStripes = getFloat(menuJson, "settings.particles.particle color.rainbow stripes", 10);
        ParticleHandler.partRed = getInt(menuJson, "settings.particles.particle color.red");
        ParticleHandler.partGreen = getInt(menuJson, "settings.particles.particle color.green");
        ParticleHandler.partBlue = getInt(menuJson, "settings.particles.particle color.blue");

        ParticleHandler.lineRainbow = FileHandler.getValue(menuJson, "settings.particles.line color.rainbow").equals("true");
        ParticleHandler.lineRainbowSpeed = getFloat(menuJson, "settings.particles.line color.rainbow speed", 50);
        ParticleHandler.lineRainbowStripes = getFloat(menuJson, "settings.particles.line color.rainbow stripes", 10);
        ParticleHandler.lineRed = getInt(menuJson, "settings.particles.line color.red");
        ParticleHandler.lineGreen = getInt(menuJson, "settings.particles.line color.green");
        ParticleHandler.lineBlue = getInt(menuJson, "settings.particles.line color.blue");

        if ((text_bottom = addFormatting(FileHandler.getValue(menuJson, "bottom.name"))).equals("null")) {
            text_bottom = EnumChatFormatting.RED + "Exit";
        }
        if ((command_bottom = FileHandler.getValue(menuJson, "bottom.command")).equals("null")) {
            command_bottom = "*exit*";
        }

        if ((text_top = addFormatting(FileHandler.getValue(menuJson, "top.name"))).equals("null")) {
            text_top = EnumChatFormatting.GOLD + "Hypixel Classics";
        }
        if ((command_top = FileHandler.getValue(menuJson, "top.command")).equals("null")) {
            command_top = "classic";
        }


        if ((text_left = addFormatting(FileHandler.getValue(menuJson, "left.name"))).equals("null")) {
            text_left = EnumChatFormatting.BLUE + "PvP Minigames";
        }
        String temp_color_left;
        if ((temp_color_left = FileHandler.getValue(menuJson, "left.color")).equals("null")) {
            color_left = 0x00102b;
        } else {
            try {
                color_left = Integer.parseInt(temp_color_left.replaceFirst("#", ""), 16);
            } catch (Exception e) {
                e.printStackTrace();
                color_left = 0x00102b;
            }
        }

        if ((text_right = addFormatting(FileHandler.getValue(menuJson, "right.name"))).equals("null")) {
            text_right = EnumChatFormatting.DARK_GREEN + "Arcade Minigames";
        }
        String temp_color_right;
        if ((temp_color_right = FileHandler.getValue(menuJson, "right.color")).equals("null")) {
            color_right = 0x001c03;
        } else {
            try {
                color_right = Integer.parseInt(temp_color_right.replaceFirst("#", ""), 16);
            } catch (Exception e) {
                color_right = 0x001c03;
            }
        }

        String temp_color_top;
        if ((temp_color_top = FileHandler.getValue(menuJson, "top.color")).equals("null")) {
            color_top = 0x000000;
        } else {
            try {
               color_top = Integer.parseInt(temp_color_top.replaceFirst("#", ""), 16) ;
            } catch (Exception e) {
                color_top = 0x000000;
            }
        }

        String temp_color_bottom;
        if ((temp_color_bottom = FileHandler.getValue(menuJson, "bottom.color")).equals("null")) {
            color_bottom = 0x000000;
        } else {
            try {
                color_bottom = Integer.parseInt(temp_color_bottom.replaceFirst("#", ""), 16) ;
            } catch (Exception e) {
                color_bottom = 0x000000;
            }
        }

        JsonArray leftButtons = new JsonParser().parse(FileHandler.getValue(menuJson, "left.buttons")).getAsJsonArray();
        for (JsonElement leftButton : leftButtons) {
            left_texts.add(FileHandler.getValue(leftButton.getAsJsonObject(), "name"));
            left_commands.add(FileHandler.getValue(leftButton.getAsJsonObject(), "command"));
            left_offsets.add(0);
            Item item = Item.getByNameOrId(FileHandler.getValue(leftButton.getAsJsonObject(), "icon.item"));
            if (item == null) {
                item = Block.getBlockFromName(FileHandler.getValue(leftButton.getAsJsonObject(), "icon.item")).getItem(null, null);
                if (item == null) {
                    left_icons.add(Blocks.barrier.getItem(null, null));
                } else {
                    left_icons.add(item);
                }
            } else {
                left_icons.add(item);
            }

            String extra = "";

            if (!FileHandler.getValue(leftButton.getAsJsonObject(), "icon.meta").equals("null")) {
                extra += "meta=" + FileHandler.getValue(leftButton.getAsJsonObject(), "icon.meta");
            }
            extra += ",";
            if (!FileHandler.getValue(leftButton.getAsJsonObject(), "icon.owner").equals("null")) {
                extra += "owner=" + FileHandler.getValue(leftButton.getAsJsonObject(), "icon.owner");
            }
            extra += ",";
            if (!FileHandler.getValue(leftButton.getAsJsonObject(), "icon.color").equals("null")) {
                extra += "color=" + FileHandler.getValue(leftButton.getAsJsonObject(), "icon.color");
            }

            if (extra.equals(",")) {
                extra = "";
            }
            left_icons_extra.add(extra);
        }

        JsonArray rightButtons = new JsonParser().parse(FileHandler.getValue(menuJson, "right.buttons")).getAsJsonArray();
        for (JsonElement rightButton : rightButtons) {
            right_texts.add(FileHandler.getValue(rightButton.getAsJsonObject(), "name"));
            right_commands.add(FileHandler.getValue(rightButton.getAsJsonObject(), "command"));
            right_offsets.add(0);
            Item item = Item.getByNameOrId(FileHandler.getValue(rightButton.getAsJsonObject(), "icon.item"));
            if (item == null) {
                item = Block.getBlockFromName(FileHandler.getValue(rightButton.getAsJsonObject(), "icon.item")).getItem(null, null);
                if (item == null) {
                    right_icons.add(Blocks.barrier.getItem(null, null));
                } else {
                    right_icons.add(item);
                }
            } else {
                right_icons.add(item);
            }

            String extra = "";

            if (!FileHandler.getValue(rightButton.getAsJsonObject(), "icon.meta").equals("null")) {
                extra += "meta=" + FileHandler.getValue(rightButton.getAsJsonObject(), "icon.meta");
            }
            extra += ",";
            if (!FileHandler.getValue(rightButton.getAsJsonObject(), "icon.owner").equals("null")) {
                extra += "owner=" + FileHandler.getValue(rightButton.getAsJsonObject(), "icon.owner");
            }
            extra += ",";
            if (!FileHandler.getValue(rightButton.getAsJsonObject(), "icon.color").equals("null")) {
                extra += "owner=" + FileHandler.getValue(rightButton.getAsJsonObject(), "icon.color");
            }

            if (extra.equals(",")) {
                extra = "";
            }
            right_icons_extra.add(extra);
        }
    }

    private static int getInt(JsonObject menuJson, String key) {
        return getInt(menuJson, key, 255);
    }
    private static int getInt(JsonObject menuJson, String key, int defaultValue) {
        String tempSettingString = FileHandler.getValue(menuJson, key);
        if (!tempSettingString.equals("null")) {
            try {
                return Integer.parseInt(tempSettingString);
            } catch (NumberFormatException exception) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    private static float getFloat(JsonObject menuJson, String key, float defaultValue, Boolean getHalf) {
        String tempSettingString = FileHandler.getValue(menuJson, key);
        if (!tempSettingString.equals("null")) {
            try {
                if (getHalf)
                    return Float.parseFloat(tempSettingString)/2;
                else
                    return Float.parseFloat(tempSettingString);
            } catch (NumberFormatException exception) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    private static float getFloat(JsonObject menuJson, String key, float defaultValue) {
        return getFloat(menuJson, key, defaultValue, false);
    }

    static void openGui() {
        if (Main.openMenu) {
            MC.displayGuiScreen(new MenuGUI());
            menu = -1;
            Main.openMenu = false;
        }
    }

    private void executeCommand(String command) {
        if (command.equalsIgnoreCase("*exit*")) {
            menu = 1;
        } else if (command.equalsIgnoreCase("home")) {
            MC.thePlayer.closeScreen();
            MC.thePlayer.sendChatMessage("/home");
        } else if (!command.equalsIgnoreCase("null")) {
            MC.thePlayer.closeScreen();
            MC.thePlayer.sendChatMessage("/lobby " + command);
        }
    }

    @Override
    public void mouseClicked(int x, int y, int button) throws IOException {
        if (button==0) {
            if (menu == 0) {
                if (y > height - (height/7)) {
                    executeCommand(command_bottom);
                } else if (y < height/7) {
                    executeCommand(command_top);
                } else {
                    if (x < (width / 2) - 5) {
                        if (x > offset_left-100 && x < offset_left+100) {
                            int i = 0;
                            for (String value : left_commands) {
                                if (y > floor(height/4)+ 17 + (i*17) && y < floor(height/4)+ 31 + (i*17)) {
                                    executeCommand(value);
                                }
                                i++;
                            }
                        }
                    } else if (x > (width/2)+5) {
                        if (x > offset_right-100 && x < offset_right+100) {
                            int i = 0;
                            for (String value : right_commands) {
                                if (y > floor(height / 4) + 17 + (i * 17) && y < floor(height / 4) + 31 + (i * 17)) {
                                    executeCommand(value);
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
                offset_menu_left = floor(Animation.interp(offset_menu_left, -100, animationSpeed));
                offset_left = floor(Animation.interp(offset_left, width/4, animationSpeed));
                underline_left = floor(Animation.interp(underline_left, 0, animationSpeed));
                offset_menu_right = floor(Animation.interp(offset_menu_right, -100, animationSpeed));
                offset_right = floor(Animation.interp(offset_right, width - width/4, animationSpeed));
                underline_right = floor(Animation.interp(underline_right, 0, animationSpeed));
                for (int i=0; i<right_texts.size(); i++) {
                    right_offsets.set(i, floor(Animation.interp(right_offsets.get(i), 0, animationSpeed)));
                }
                for (int i=0; i<left_texts.size(); i++) {
                    left_offsets.set(i, floor(Animation.interp(left_offsets.get(i), 0, animationSpeed)));
                }
                offset_bottom = floor(Animation.interp(offset_bottom,  -height/7, animationSpeed));
                offset_top = floor(Animation.interp(offset_top, 15, animationSpeed));
            } else if (y < height/7) {
                offset_menu_left = floor(Animation.interp(offset_menu_left, -100, animationSpeed));
                offset_left = floor(Animation.interp(offset_left, width/4, animationSpeed));
                underline_left = floor(Animation.interp(underline_left, 0, animationSpeed));
                offset_menu_right = floor(Animation.interp(offset_menu_right, -100, animationSpeed));
                offset_right = floor(Animation.interp(offset_right, width - width/4, animationSpeed));
                underline_right = floor(Animation.interp(underline_right, 0, 5));
                for (int i=0; i<right_texts.size(); i++) {
                    right_offsets.set(i, floor(Animation.interp(right_offsets.get(i), 0, animationSpeed)));
                }
                for (int i=0; i<left_texts.size(); i++) {
                    left_offsets.set(i, floor(Animation.interp(left_offsets.get(i), 0, animationSpeed)));
                }
                offset_bottom = floor(Animation.interp(offset_bottom,  -12, animationSpeed));
                offset_top = floor(Animation.interp(offset_top, height/7, animationSpeed));
            } else {
                offset_top = floor(Animation.interp(offset_top, 15, animationSpeed));
                offset_bottom = floor(Animation.interp(offset_bottom, -12, animationSpeed));
                if (x < width/2) {
                    for (int i=0; i<right_texts.size(); i++) {
                        right_offsets.set(i, floor(Animation.interp(right_offsets.get(i), 0, animationSpeed)));
                    }
                    offset_menu_left = floor(Animation.interp(offset_menu_left, 50, animationSpeed));
                    offset_left = floor(Animation.interp(offset_left, width/3, animationSpeed));
                    underline_left = floor(Animation.interp(underline_left, 50, animationSpeed));
                    offset_menu_right = floor(Animation.interp(offset_menu_right, -40, animationSpeed));
                    offset_right = floor(Animation.interp(offset_right, width - width/4, animationSpeed));
                    underline_right = floor(Animation.interp(underline_right, 0, animationSpeed));

                    if (x > offset_left-100 && x < offset_left+100) {
                        for (int i=0; i<left_texts.size(); i++) {
                            if (y > floor(height/4)+ 17 + (i*17) && y < floor(height/4)+ 31 + (i*17)) {
                                left_offsets.set(i, floor(Animation.interp(left_offsets.get(i), 25, animationSpeed)));
                            } else {
                                left_offsets.set(i, floor(Animation.interp(left_offsets.get(i), 0, animationSpeed)));
                            }
                        }
                    } else {
                        for (int i=0; i<left_texts.size(); i++) {
                            left_offsets.set(i, floor(Animation.interp(left_offsets.get(i), 0, animationSpeed)));
                        }
                    }
                } else {
                    offset_top = floor(Animation.interp(offset_top, 15, animationSpeed));
                    offset_bottom = floor(Animation.interp(offset_bottom, -12, animationSpeed));
                    for (int i=0; i<left_texts.size(); i++) {
                        left_offsets.set(i, floor(Animation.interp(left_offsets.get(i), 0, animationSpeed)));
                    }
                    offset_menu_left = floor(Animation.interp(offset_menu_left, -40, animationSpeed));
                    offset_left = floor(Animation.interp(offset_left, width/4, animationSpeed));
                    underline_left = floor(Animation.interp(underline_left, 0, animationSpeed));
                    offset_menu_right = floor(Animation.interp(offset_menu_right, 50, animationSpeed));
                    offset_right = floor(Animation.interp(offset_right, width - width/3, animationSpeed));
                    underline_right = floor(Animation.interp(underline_right, 50, 5));

                    if (x > offset_right-100 && x < offset_right+100) {
                        for (int i=0; i<right_texts.size(); i++) {
                            if (y > floor(height/4)+ 17 + (i*17) && y < floor(height/4)+ 31 + (i*17)) {
                                right_offsets.set(i, floor(Animation.interp(right_offsets.get(i), 25, animationSpeed)));
                            } else {
                                right_offsets.set(i, floor(Animation.interp(right_offsets.get(i), 0, animationSpeed)));
                            }
                        }
                    } else {
                        for (int i=0; i<right_texts.size(); i++) {
                            right_offsets.set(i, floor(Animation.interp(right_offsets.get(i), 0, animationSpeed)));
                        }
                    }
                }
            }
        } else {
            offset_menu_left = floor(Animation.interp(offset_menu_left, -width/2-20, animationSpeed*2));
            offset_left = floor(Animation.interp(offset_left, -width/2-20 + width/4, animationSpeed*2));
            underline_left = floor(Animation.interp(underline_left, 0, animationSpeed));

            offset_menu_right = floor(Animation.interp(offset_menu_right, -width/2-20, animationSpeed*2));
            offset_right = floor(Animation.interp(offset_right, width/2+20 + width - width/4, animationSpeed*2));
            underline_right = floor(Animation.interp(underline_right, 0, animationSpeed));

            for (int i=0; i<right_texts.size(); i++) {
                right_offsets.set(i, floor(Animation.interp(right_offsets.get(i), 0, animationSpeed)));
            }
            for (int i=0; i<left_texts.size(); i++) {
                left_offsets.set(i, floor(Animation.interp(left_offsets.get(i), 0, animationSpeed)));
            }

            offset_bottom = floor(Animation.interp(offset_bottom,  21, animationSpeed*2));
            offset_top = floor(Animation.interp(offset_top, -10, animationSpeed*2));

            if (offset_bottom == 12) {
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
            ParticleHandler.init();

            offset_left = floor(-width/2-20);
            offset_right = floor(width/2+20 + width - width/4);
            offset_menu_left = floor(-width/2-20);
            offset_menu_right = floor(-width/2-20);
            underline_left = 0;
            underline_right = 0;
            for (int i=0; i<left_offsets.size(); i++) {
                left_offsets.set(i, 0);
            }
            for (int i=0; i<right_offsets.size(); i++) {
                right_offsets.set(i, 0);
            }
            offset_bottom = 10;
            offset_top = -10;
            menu = 0;
        }

        if (menu == 0 || menu == 1) {
            ParticleHandler.draw(menu, x, y);

            drawRect(0, 0, floor(width/2) - 5 + offset_menu_left, floor(height), 0x50000000);
            drawRect(offset_left-ren.getStringWidth(text_left)/2-3, floor(height/4)-2, offset_left+ren.getStringWidth(text_left)/2+3, floor(height/4)+10, 0x50000000);
            drawCenteredString(ren, text_left, offset_left, floor(height/4), 0xffffffff);
            if (underline_left > 0) {
                drawHorizontalLine(offset_left - underline_left, offset_left + underline_left, floor(height/4)+13, 0xffffffff);
            }

            int i = 0;
            for (String button : left_texts) {
                int drawX = offset_left;
                int drawW = 100;
                int drawO = left_offsets.get(i);

                int meta = 0;
                String owner = "";
                String color = "";
                for (String value : left_icons_extra.get(i).split(",")) {
                    if (value.startsWith("meta=")) {
                        try {
                            meta = Integer.parseInt(value.substring(5));
                        } catch (NumberFormatException e) {
                            meta = 0;
                        }
                    } else if (value.startsWith("owner=")) {
                        owner = value.substring(6);
                    } else if (value.startsWith("color=")) {
                        color = value.substring(6);
                    }
                }
                ItemStack itemStack = new ItemStack(left_icons.get(i), 1, meta);
                itemStack.setTagCompound(new NBTTagCompound());
                if (!owner.equals("")) {
                    itemStack.getTagCompound().setTag("SkullOwner", new NBTTagString(owner));
                }
                if (!color.equals("")) {
                    try {
                        NBTTagCompound colorCompound = new NBTTagCompound();
                        colorCompound.setInteger("color", Integer.parseInt(color.replace("#", ""), 16));
                        itemStack.getTagCompound().setTag("display", colorCompound);
                    } catch (NumberFormatException e) { /* do nothing */ }
                }

                int drawY = floor(height/4)+20 + (i*17);

                drawRect(drawX-(drawW/2)-3-drawO, drawY-3, drawX+(drawW/2)+3+drawO, drawY+11, (int) (color_left+0xff000000));
                drawCenteredString(ren, button, drawX, drawY, 0xffffffff);
                drawItemIcon(drawX-(drawW/2)-3-(drawO/2), drawY-4, itemStack);
                drawItemIcon(drawX+(drawW/2)-13+(drawO/2), drawY-4, itemStack);
                i++;
            }

            drawRect(floor(width/2) + 5 - offset_menu_right, 0, floor(width), floor(height), 0x50000000);
            drawRect(offset_right-ren.getStringWidth(text_right)/2-3, floor(height/4)-2, offset_right+ren.getStringWidth(text_right)/2+3, floor(height/4)+10, 0x50000000);
            drawCenteredString(ren, text_right, offset_right, floor(height/4), 0xffffffff);
            if (underline_right > 0) {
                drawHorizontalLine(offset_right - underline_right, offset_right + underline_right, floor(height/4)+13, 0xffffffff);
            }

            i = 0;
            for (String button : right_texts) {
                int drawX = offset_right;
                int drawW = 100;
                int drawO = right_offsets.get(i);

                int meta = 0;
                String owner = "";
                String color = "";
                String extra = right_icons_extra.get(i);
                for (String value : extra.split(",")) {
                    if (value.startsWith("meta=")) {
                        try {
                            meta = Integer.parseInt(value.substring(5));
                        } catch (NumberFormatException e) {
                            meta = 0;
                        }
                    } else if (value.startsWith("owner=")) {
                        owner = value.substring(6);
                    } else if (value.startsWith("color=")) {
                        color = value.substring(6);
                    }
                }
                ItemStack itemStack = new ItemStack(right_icons.get(i), 1, meta);
                itemStack.setTagCompound(new NBTTagCompound());
                if (!owner.equals("")) {
                    itemStack.getTagCompound().setTag("SkullOwner", new NBTTagString(owner));
                }
                if (!color.equals("")) {
                    try {
                        NBTTagCompound colorCompound = new NBTTagCompound();
                        colorCompound.setInteger("color", Integer.parseInt(color.replace("#",""), 16));
                        itemStack.getTagCompound().setTag("display", colorCompound);
                    } catch (NumberFormatException e) { /* do nothing */ }
                }

                int drawY = floor(height/4)+20 + (i*17);

                drawRect(drawX-(drawW/2)-3-drawO, drawY-3, drawX+(drawW/2)+3+drawO, drawY+11, (int) (color_right+0xff000000));
                drawCenteredString(ren, button, drawX, drawY, 0xffffffff);
                drawItemIcon(drawX-(drawW/2)-3-(drawO/2), drawY-4, itemStack);
                drawItemIcon(drawX+(drawW/2)-13+(drawO/2), drawY-4, itemStack);
                i++;
            }

            drawRect(0, floor(height + offset_bottom), floor(width), floor(height), (int) (color_bottom+0xff000000));
            drawCenteredString(ren, text_bottom, floor(width / 2), floor(height + offset_bottom/2)-3, 0xffffffff);

            drawRect(0, floor(offset_top), floor(width), 0, (int) (color_top+0xff000000));
            drawCenteredString(ren, text_top, floor(width/2), floor(offset_top/2 - 10)+5, 0xffffffff);
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

    private static String addFormatting(String in) {
        return in.replace("&0", EnumChatFormatting.BLACK.toString())
                .replace("&1", EnumChatFormatting.DARK_BLUE.toString())
                .replace("&2", EnumChatFormatting.DARK_GREEN.toString())
                .replace("&3", EnumChatFormatting.DARK_AQUA.toString())
                .replace("&4", EnumChatFormatting.DARK_RED.toString())
                .replace("&5", EnumChatFormatting.DARK_PURPLE.toString())
                .replace("&6", EnumChatFormatting.GOLD.toString())
                .replace("&7", EnumChatFormatting.GRAY.toString())
                .replace("&8", EnumChatFormatting.DARK_GRAY.toString())
                .replace("&9", EnumChatFormatting.BLUE.toString())
                .replace("&a", EnumChatFormatting.GREEN.toString())
                .replace("&b", EnumChatFormatting.AQUA.toString())
                .replace("&c", EnumChatFormatting.RED.toString())
                .replace("&d", EnumChatFormatting.LIGHT_PURPLE.toString())
                .replace("&e", EnumChatFormatting.YELLOW.toString())
                .replace("&f", EnumChatFormatting.WHITE.toString())
                .replace("&k", EnumChatFormatting.OBFUSCATED.toString())
                .replace("&l", EnumChatFormatting.BOLD.toString())
                .replace("&m", EnumChatFormatting.STRIKETHROUGH.toString())
                .replace("&n", EnumChatFormatting.UNDERLINE.toString())
                .replace("&o", EnumChatFormatting.ITALIC.toString())
                .replace("&r", EnumChatFormatting.RESET.toString());
    }
}
