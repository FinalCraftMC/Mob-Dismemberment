package me.ichun.mods.mobdismemberment.client.util;

import net.minecraft.entity.monster.EntityCreeper;

import java.lang.reflect.Field;

public class ASMUtil {

    private static Field field_timeSinceIgnited = null;
    public static int getTimeSinceIgnited(EntityCreeper entityCreeper){
        try {
            if (field_timeSinceIgnited == null){
                try {
                    field_timeSinceIgnited = EntityCreeper.class.getDeclaredField("field_70833_d");
                    field_timeSinceIgnited.setAccessible(true);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            return field_timeSinceIgnited.getInt(entityCreeper);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    private static Field field_fuseTime = null;
    public static int getFuseTime(EntityCreeper entityCreeper){
        try {
            if (field_fuseTime == null){
                try {
                    field_fuseTime = EntityCreeper.class.getDeclaredField("field_82225_f");
                    field_fuseTime.setAccessible(true);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            return field_fuseTime.getInt(entityCreeper);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}
