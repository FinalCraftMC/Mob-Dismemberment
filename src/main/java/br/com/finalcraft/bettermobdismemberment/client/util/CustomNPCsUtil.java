package br.com.finalcraft.bettermobdismemberment.client.util;

import br.com.finalcraft.bettermobdismemberment.common.BetterMobDismemberment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import noppes.npcs.entity.EntityCustomNpc;

public class CustomNPCsUtil {

    public static boolean isPresent(){
        return BetterMobDismemberment.hasCustomNPCs;
    }

    public static boolean isEntityCustomNpc(Entity entity) {
        if (entity instanceof EntityCustomNpc) {
            EntityCustomNpc entityCustomNpc = (EntityCustomNpc) entity;
            if (entityCustomNpc.modelData != null && entityCustomNpc.modelData.entityClass != null){
                System.out.println(entityCustomNpc.modelData.entityClass.getName());
            }
            return true;
        }
        return false;
    }

    public static boolean isEntityNPCHuman(Entity entity) {
        if (entity instanceof EntityCustomNpc) {
            EntityCustomNpc entityCustomNpc = (EntityCustomNpc) entity;
            return entityCustomNpc.modelData == null || entityCustomNpc.modelData.entityClass == null;
        }
        return false;
    }

    public static boolean isEntityNPCZombie(Entity entity) {
        if (entity instanceof EntityCustomNpc) {
            EntityCustomNpc entityCustomNpc = (EntityCustomNpc) entity;
            if (entityCustomNpc.modelData != null && entityCustomNpc.modelData.entityClass != null){
                return entityCustomNpc.modelData.entityClass == EntityZombie.class;
            }
            return true;
        }
        return false;
    }


    public static int getModelRenderSize(EntityLivingBase entityCustomNpc){
        return ((EntityCustomNpc)entityCustomNpc).display.modelSize;
    }
}
