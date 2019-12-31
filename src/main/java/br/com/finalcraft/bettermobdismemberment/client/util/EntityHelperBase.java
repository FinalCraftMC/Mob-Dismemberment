package br.com.finalcraft.bettermobdismemberment.client.util;


//Source https://github.com/iChun/iChunUtil/blob/1.7.10_legacy/src/main/java/us/ichun/mods/ichunutil/common/core/EntityHelperBase.java
public class EntityHelperBase {

    public static float interpolateRotation(float prevRotation, float nextRotation, float partialTick){
        float f3;

        for (f3 = nextRotation - prevRotation; f3 < -180.0F; f3 += 360.0F)
        {
            ;
        }

        while (f3 >= 180.0F)
        {
            f3 -= 360.0F;
        }

        return prevRotation + partialTick * f3;
    }


}
