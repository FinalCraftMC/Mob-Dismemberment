package br.com.finalcraft.bettermobdismemberment.common;

import br.com.finalcraft.bettermobdismemberment.client.render.gibmodels.customnpcs.GMTHumanNPC;
import br.com.finalcraft.bettermobdismemberment.client.render.gibmodels.customnpcs.GMTZombieNPC;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import br.com.finalcraft.bettermobdismemberment.client.core.EventHandlerClient;
import br.com.finalcraft.bettermobdismemberment.client.entity.EntityGib;
import br.com.finalcraft.bettermobdismemberment.client.render.ModelGibManager;
import br.com.finalcraft.bettermobdismemberment.client.render.RenderGib;
import br.com.finalcraft.bettermobdismemberment.client.render.gibmodels.GMTCreeper;
import br.com.finalcraft.bettermobdismemberment.client.render.gibmodels.GMTSkeleton;
import br.com.finalcraft.bettermobdismemberment.client.render.gibmodels.GMTZombie;
import br.com.finalcraft.bettermobdismemberment.common.core.MDConfig;
import net.minecraftforge.common.MinecraftForge;

import java.io.File;

@Mod(
        modid = BetterMobDismemberment.MOD_ID,
        name = BetterMobDismemberment.MOD_NAME,
        version = BetterMobDismemberment.VERSION
)
public class BetterMobDismemberment {
    public static final String MOD_ID = "bettermobdismemberment";
    public static final String MOD_NAME = "BetterMobDismemberment";
    public static final String VERSION = "4.2.0";

    public static MDConfig config;

    public static EventHandlerClient eventHandlerClient;

    public static boolean hasCustomNPCs = false;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        if (event.getSide().isServer()) {
            event.getModLog().warn("You're loading BetterMobDismemberment on a server! This is a client-only mod!");
        }else {
            config = new MDConfig( new File("config/BetterMobDismemberment.cfg"));
            eventHandlerClient = new EventHandlerClient();

            FMLCommonHandler.instance().bus().register(eventHandlerClient);
            MinecraftForge.EVENT_BUS.register(eventHandlerClient);

            RenderingRegistry.registerEntityRenderingHandler(EntityGib.class, new RenderGib());

            ModelGibManager.addNewTemplate(new GMTCreeper(ModelGibManager.instance));
            ModelGibManager.addNewTemplate(new GMTZombie(ModelGibManager.instance));
            ModelGibManager.addNewTemplate(new GMTSkeleton(ModelGibManager.instance));
        }
    }

    @Mod.EventHandler
    public void preInit(FMLPostInitializationEvent event){
        if (event.getSide().isClient()) {
            if (hasCustomNPCs = Loader.isModLoaded("customnpcs")){
                ModelGibManager.addNewTemplate(new GMTHumanNPC(ModelGibManager.instance));
                ModelGibManager.addNewTemplate(new GMTZombieNPC(ModelGibManager.instance));
            }
        }
    }

}
