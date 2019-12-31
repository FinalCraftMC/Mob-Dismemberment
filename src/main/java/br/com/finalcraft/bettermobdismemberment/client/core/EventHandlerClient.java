package br.com.finalcraft.bettermobdismemberment.client.core;

import br.com.finalcraft.bettermobdismemberment.client.render.gibmodels.GibModelTemplate;
import br.com.finalcraft.bettermobdismemberment.client.util.ASMUtil;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import br.com.finalcraft.bettermobdismemberment.client.render.ModelGibManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityMinecartTNT;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class EventHandlerClient {
    public long clock;

    public HashMap<EntityLivingBase, Integer> dismemberTimeout = new HashMap<>();
    public HashMap<Entity, Long> exploTime = new HashMap<>();
    public ArrayList<Entity> explosionSources = new ArrayList<>();

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event){
        if (event.entity.worldObj.isRemote && !event.entityLiving.isChild()){
            if (ModelGibManager.isApplicableToAnyModel(event.entityLiving)){
                dismemberTimeout.put(event.entityLiving, 2);
            }
        }
    }

    @SubscribeEvent
    public void onClientConnection(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        exploTime.clear();
        dismemberTimeout.clear();
        explosionSources.clear();
    }

    @SubscribeEvent
    public void worldTick(TickEvent.ClientTickEvent event) {
        if(event.phase == TickEvent.Phase.END && Minecraft.getMinecraft().theWorld != null){
            Minecraft mc = Minecraft.getMinecraft();
            WorldClient world = mc.theWorld;

            if(clock != world.getWorldTime() || !world.getGameRules().getGameRuleBooleanValue("doDaylightCycle")){
                clock = world.getWorldTime();

                for(int i = 0; i < world.loadedEntityList.size(); i++){
                    Entity ent = (Entity)world.loadedEntityList.get(i);
                    if(ent instanceof EntityCreeper || ent instanceof EntityTNTPrimed || ent instanceof EntityMinecartTNT){
                        if(!explosionSources.contains(ent)){
                            explosionSources.add(ent);
                        }
                    }
                    if (ent instanceof EntityLivingBase && !ent.isEntityAlive() && !dismemberTimeout.containsKey(ent) && ModelGibManager.isApplicableToAnyModel((EntityLivingBase)ent)){
                        dismemberTimeout.put((EntityLivingBase)ent, 2);
                    }
                }

                for(int i = explosionSources.size() - 1; i >= 0; i--){
                    Entity ent = explosionSources.get(i);
                    if(ent.isDead){
                        if(ent instanceof EntityCreeper){
                            int igniteTime = ASMUtil.getTimeSinceIgnited((EntityCreeper)ent);
                            int maxFuseTime = ASMUtil.getFuseTime((EntityCreeper)ent);
                            if(igniteTime >= maxFuseTime)
                            {
                                if(!exploTime.containsKey(ent))
                                {
                                    long time = ent.worldObj.getWorldTime() % 24000L;
                                    if(time > 23959L)
                                    {
                                        time -= 23999L;
                                    }
                                    exploTime.put(ent, time);
                                }

                                dismemberTimeout.put((EntityLivingBase)ent, 2);
                            }
                        }else if(ent instanceof EntityTNTPrimed || ent instanceof EntityMinecartTNT){
                            if(!exploTime.containsKey(ent))
                            {
                                long time = ent.worldObj.getWorldTime() % 24000L;
                                if(time > 23959L)
                                {
                                    time -= 23999L;
                                }
                                exploTime.put(ent, time);
                            }
                        }

                        explosionSources.remove(i);
                    }
                }

                Iterator<Entry<EntityLivingBase, Integer>> ite = dismemberTimeout.entrySet().iterator();
                if(ite.hasNext()){
                    Entry<EntityLivingBase, Integer> e = ite.next();

                    e.setValue(e.getValue() - 1);

                    e.getKey().hurtTime = 0;
                    e.getKey().deathTime = 0;

                    Entity explo = null;
                    double dist = 1000D;
                    for(Entry<Entity, Long> e1 : exploTime.entrySet()){
                        double mobDist = e1.getKey().getDistanceToEntity(e.getKey());
                        if(mobDist < 10D && mobDist < dist)
                        {
                            dist = mobDist;
                            explo = e1.getKey();
                            e.setValue(0);
                        }
                    }

                    if(e.getValue() <= 0){
                        if(dismember(e.getKey().worldObj, e.getKey(), explo))
                        {
                            e.getKey().setDead();
                        }
                        ite.remove();
                    }
                }

                Iterator<Entry<Entity, Long>> ite1 = exploTime.entrySet().iterator();
                long worldTime = world.getWorldTime() % 24000L;
                while(ite1.hasNext()){
                    Entry<Entity, Long> e = ite1.next();
                    if(e.getValue() + 40L < worldTime)
                    {
                        ite1.remove();
                    }
                }
            }
        }
    }

    public boolean dismember(World world, EntityLivingBase living, Entity explo){
        if (!living.isChild()){
            GibModelTemplate gibTemplate = ModelGibManager.getApplicableModel(living);
            if (gibTemplate != null){
                gibTemplate.spawnEntityGibInWorld(world, living, explo);
                return true;
            }
        }
        return false;
    }
}
