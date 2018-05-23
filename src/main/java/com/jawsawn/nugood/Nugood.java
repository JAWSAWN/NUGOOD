package com.jawsawn.nugood;


import net.minecraftforge.fml.common.Mod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;


@Mod(modid = "nugood", name = "NUGOOD", version = Nugood.VERSION, clientSideOnly = true)

public class Nugood {

    public static final String VERSION = "1.0";

    public static boolean secret = false;
    public static boolean played = false;
    public static double pitch = 1.0D;

    @Instance("nugood")
    public static Nugood instance;
    private static Logger logger;


    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();

        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();

        secret = config.getBoolean("wolfGrowl", "nugood", false, "Changing this to true might result in bad wirings...");

        if (config.hasChanged()) {
            config.save();
        }

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onGuiOpen(GuiOpenEvent event) {
        if (event.getGui() instanceof GuiMainMenu && !played) {
            played = true;
            if (secret) {
                ResourceLocation location = new ResourceLocation("nugood", "secret_ding");
                SoundEvent sound = new SoundEvent(location);
                Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(sound, (float) pitch));
            }
        }
    }


}

