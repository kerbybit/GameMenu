package com.kerbybit.GameMenu;

import com.kerbybit.GameMenu.command.CommandMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

@Mod(modid = "gm", name = "GameMenu", version = "0.9")
public class Main {
    static int ticksElapsed = 0;
    public static Boolean openMenu = false;
    public static String dir = "./mods/GameMenu/";
    private static KeyBinding openMenuKey;
    public static Boolean worldLoaded = false;

    static Boolean openWithCompass = true;
    static Boolean checkFast = true;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);

        ClientCommandHandler.instance.registerCommand(new CommandMenu());

        openMenuKey = new KeyBinding("Open Hypixel game menu", Keyboard.KEY_G, "GameMenu");

        ClientRegistry.registerKeyBinding(openMenuKey);

        MenuGUI.init();
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (openMenuKey.isPressed()) {
            openMenu = true;
        }
    }

    @SubscribeEvent
    public void render(RenderGameOverlayEvent event) {
        MenuGUI.openGui();
        worldLoaded = true;

        if (openWithCompass && checkFast) {
            if (Minecraft.getMinecraft().thePlayer.openContainer instanceof ContainerChest) {
                ContainerChest chest = (ContainerChest) Minecraft.getMinecraft().thePlayer.openContainer;
                IInventory inv = chest.getLowerChestInventory();
                if (inv.getName().equals("Game Menu")) {
                    Minecraft.getMinecraft().thePlayer.closeScreen();
                    openMenu = true;
                }
            }
        }
    }

    @SubscribeEvent
    public void tick(TickEvent.ClientTickEvent event) {
        ticksElapsed++;

        if (openWithCompass && !checkFast) {
            if (Minecraft.getMinecraft().thePlayer.openContainer instanceof ContainerChest) {
                ContainerChest chest = (ContainerChest) Minecraft.getMinecraft().thePlayer.openContainer;
                IInventory inv = chest.getLowerChestInventory();
                if (inv.getName().equals("Game Menu")) {
                    Minecraft.getMinecraft().thePlayer.closeScreen();
                    openMenu = true;
                }
            }
        }
    }

    @SubscribeEvent
    public void worldUnload(WorldEvent.Unload event) {
        worldLoaded = false;
    }
}
