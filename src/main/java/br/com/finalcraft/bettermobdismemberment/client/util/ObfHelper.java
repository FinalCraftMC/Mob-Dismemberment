package br.com.finalcraft.bettermobdismemberment.client.util;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

//Source https://github.com/iChun/iChunUtil/blob/1.7.10_legacy/src/main/java/us/ichun/mods/ichunutil/common/core/util/ObfHelper.java
public class ObfHelper {

    public static ResourceLocation invokeGetEntityTexture(Render rend, Class clz, EntityLivingBase ent) {
        ResourceLocation loc = getEntTexture(rend, clz, ent);
        return loc != null ? loc : AbstractClientPlayer.locationStevePng;
    }

    private static Map<Class<? extends Entity>, Method> mapOfMethhodGetEntityTexture = new HashMap<>();
    private static ResourceLocation getEntTexture(Render rend, Class<? extends Entity> entityClass, EntityLivingBase ent) {
        try {
            Method method = mapOfMethhodGetEntityTexture.get(entityClass);
            if (method == null){
                method = entityClass.getDeclaredMethod("func_110775_a", Entity.class);
                method.setAccessible(true);
                mapOfMethhodGetEntityTexture.put(entityClass,method);
            }
            return (ResourceLocation) method.invoke(rend, ent);
        }catch (Exception e){
            e.printStackTrace();
        }
        return AbstractClientPlayer.locationStevePng;
    }
}
