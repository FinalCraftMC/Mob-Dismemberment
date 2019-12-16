package me.ichun.mods.mobdismemberment.client.render;

import me.ichun.mods.mobdismemberment.client.entity.EntityGib;
import me.ichun.mods.mobdismemberment.client.render.gibmodels.GMTCreeper;
import me.ichun.mods.mobdismemberment.client.render.gibmodels.GMTSkeleton;
import me.ichun.mods.mobdismemberment.client.render.gibmodels.GMTZombie;
import me.ichun.mods.mobdismemberment.client.render.gibmodels.GibModelTemplate;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;

import java.util.ArrayList;
import java.util.List;

public class ModelGibManager extends ModelBase{

    public static final List<GibModelTemplate> modelTemplateList = new ArrayList<>();
    public static void addNewTemplate(GibModelTemplate gibModelTemplate){
        modelTemplateList.add(gibModelTemplate);
    }

    public static GibModelTemplate getApplicableModel(EntityLivingBase entityLiving){
        for (GibModelTemplate modelTemplate : modelTemplateList) {
            if (modelTemplate.isApplicableFor(entityLiving)){
                return modelTemplate;
            }
        }
        return null;
    }

    public ModelGibManager(){
        textureWidth = 64;
        textureHeight = 32;

        addNewTemplate(new GMTCreeper(this));
        addNewTemplate(new GMTZombie(this));
        addNewTemplate(new GMTSkeleton(this));
    }

    @Override
    public void render(Entity ent, float f, float f1, float f2, float f3, float f4, float f5){
        if (ent instanceof EntityGib){
            EntityGib theGib = (EntityGib) ent;
            theGib.getGibModelTemplate().render(theGib,f,f1,f2,f3,f4,f5);
        }
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity ent){
        if (ent instanceof EntityGib){
            EntityGib theGib = (EntityGib) ent;
            theGib.getGibModelTemplate().setRotationAngles(f,f1,f2,f3,f4,f5,theGib);
        }
    }

}
