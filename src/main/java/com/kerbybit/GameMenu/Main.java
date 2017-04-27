package com.kerbybit.GameMenu;

import net.minecraft.client.settings.KeyBinding;
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
import org.lwjgl.input.Keyboard;

@Mod(modid = "gm", name = "GameMenu", version = "0.8")
public class Main {
    static Boolean openMenu = false;
    static String dir = "./mods/GameMenu/";
    private KeyBinding openMenuKey;
    static Boolean worldLoaded = false;

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
    }

    @SubscribeEvent
    public void worldUnload(WorldEvent.Unload event) {
        worldLoaded = false;
    }
}
