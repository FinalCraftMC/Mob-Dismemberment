package me.ichun.mods.mobdismemberment.common;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import ichun.common.core.config.ConfigHandler;
import ichun.common.core.config.IConfigUser;
import me.ichun.mods.mobdismemberment.client.core.EventHandlerClient;
import me.ichun.mods.mobdismemberment.client.entity.EntityGib;
import me.ichun.mods.mobdismemberment.client.render.RenderGib;
import me.ichun.mods.mobdismemberment.common.core.MDConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Property;
import org.apache.logging.log4j.LogManager;

@Mod(
        modid = "mobdismemberment",
        name = "MobDismemberment",
        version = "4.1.0",
        dependencies = "required-after:iChunUtil"
)
public class MobDismemberment
{
    public static final String VERSION = "4.1.0";
    public static final String MOD_ID = "mobdismemberment";
    public static final String MOD_NAME = "MobDismemberment";

    @Mod.Instance(MOD_ID)
    public static MobDismemberment instance;

    public static MDConfig config;

    public static EventHandlerClient eventHandlerClient;

    private static boolean hasMobAmputation;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){

        config = new MDConfig();

        eventHandlerClient = new EventHandlerClient();

        FMLCommonHandler.instance().bus().register(eventHandlerClient);
        MinecraftForge.EVENT_BUS.register(eventHandlerClient);

        RenderingRegistry.registerEntityRenderingHandler(EntityGib.class, new RenderGib());
    }

    public static boolean hasMobAmputation()
    {
        return hasMobAmputation;
    }
}
