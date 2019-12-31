package br.com.finalcraft.bettermobdismemberment.client.render;

import br.com.finalcraft.bettermobdismemberment.client.entity.EntityGib;
import br.com.finalcraft.bettermobdismemberment.client.render.gibmodels.GibModelTemplate;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

import java.util.ArrayList;
import java.util.List;

public class ModelGibManager extends ModelBase{

    public static ModelGibManager instance;

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

    public static boolean isApplicableToAnyModel(EntityLivingBase entityLiving){
        return getApplicableModel(entityLiving) != null;
    }

    public ModelGibManager(){
        textureWidth = 64;
        textureHeight = 32;
        instance = this;
    }

    public void bindEntityTexture(Entity ent){
        EntityGib theGib = (EntityGib) ent;
        theGib.getGibModelTemplate().bindEntityTexture(theGib);
    }

    @Override
    public void render(Entity ent, float f, float f1, float f2, float f3, float f4, float f5){
        EntityGib theGib = (EntityGib) ent;
        theGib.getGibModelTemplate().render(theGib,f,f1,f2,f3,f4,f5);
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity ent){
        EntityGib theGib = (EntityGib) ent;
        theGib.getGibModelTemplate().setRotationAngles(f,f1,f2,f3,f4,f5,theGib);
    }

}
