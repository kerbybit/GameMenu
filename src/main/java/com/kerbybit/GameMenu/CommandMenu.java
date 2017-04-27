package com.kerbybit.GameMenu;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class CommandMenu extends CommandBase {
    @Override
    public String getCommandName() {
        return "game";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/game <reload/files/reset>";
    }
    private void showCommandUsage(ICommandSender sender) {
        showMessage(EnumChatFormatting.RED + getCommandUsage(sender));
    }
    static void showMessage(String message) {
        if (Main.worldLoaded) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message));
        } else {
            System.out.println(message);
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 0) {
            Main.openMenu = true;
        } else {
            if (args.length >= 1) {
                if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("load")) {
                    MenuGUI.init();
                    showMessage(EnumChatFormatting.DARK_AQUA + "Reloaded GameMenu config");
                } else if (args[0].equalsIgnoreCase("files") || args[0].equalsIgnoreCase("file")) {
                    showMessage(EnumChatFormatting.DARK_AQUA + "Opening GameMenu file location...");
                    try {
                        Desktop.getDesktop().open(new File("./mods/GameMenu"));
                    } catch (Exception e) {
                        e.printStackTrace();
                        showMessage(EnumChatFormatting.RED + "Could not find GameMenu files!");
                    }
                } else if (args[0].equalsIgnoreCase("reset")) {
                    try {
                        FileHandler.generateFile();
                        MenuGUI.init();
                        showMessage(EnumChatFormatting.DARK_AQUA + "Reset GameMenu config");
                    } catch (IOException e) {
                        e.printStackTrace();
                        showMessage(EnumChatFormatting.RED + "Unable to reset GameMenu config file!");
                    }
                } else {
                   showCommandUsage(sender);
                }
            } else {
                showCommandUsage(sender);
            }
        }
    }
}
