package com.kerbybit.GameMenu;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class CommandMenu extends CommandBase {
    @Override
    public String getCommandName() {
        return "game";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/game";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        Main.openMenu = true;
    }
}
