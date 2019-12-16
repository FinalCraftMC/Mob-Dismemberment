package me.ichun.mods.mobdismemberment.client.render.gibmodels;

import me.ichun.mods.mobdismemberment.client.entity.EntityGib;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.world.World;

public class GMTCreeper implements GibModelTemplate{

    public ModelRenderer head32;
    public ModelRenderer body32;
    public ModelRenderer creeperFoot;

    public GMTCreeper(ModelBase modelBase) {
        creeperFoot = new ModelRenderer(modelBase, 0, 16);
        creeperFoot.setTextureSize(64, 32);
        creeperFoot.addBox(-2F, -3F, -2F, 4, 6, 4);
        creeperFoot.setRotationPoint(0F, 24F, 0F);
        creeperFoot.rotateAngleX = 0F;
        creeperFoot.rotateAngleY = 0F;
        creeperFoot.rotateAngleZ = 0F;
        creeperFoot.mirror = false;

        head32 = new ModelRenderer(modelBase, 0, 0);
        head32.setTextureSize(64, 32);
        head32.addBox(-4F, -4F, -4F, 8, 8, 8);
        head32.setRotationPoint(0F, 20F, 0F);
        head32.rotateAngleX = 0F;
        head32.rotateAngleY = 0F;
        head32.rotateAngleZ = 0F;
        head32.mirror = false;

        body32 = new ModelRenderer(modelBase, 16, 16);
        body32.setTextureSize(64, 32);
        body32.addBox(-4F, -6F, -2F, 8, 12, 4);
        body32.setRotationPoint(0F, 22F, 0F);
        body32.rotateAngleX = 0F;
        body32.rotateAngleY = 0F;
        body32.rotateAngleZ = 0F;
        body32.mirror = false;
    }

    // ===============================================================================================
    // Selection Section
    // ===============================================================================================
    
    @Override
    public void spawnEntityGibInWorld(World world, EntityLivingBase living, Entity explo){
        world.spawnEntityInWorld(new EntityGib(world, living, 0, explo, this));
        world.spawnEntityInWorld(new EntityGib(world, living, 3, explo, this));
        world.spawnEntityInWorld(new EntityGib(world, living, 6, explo, this));
        world.spawnEntityInWorld(new EntityGib(world, living, 7, explo, this));
        world.spawnEntityInWorld(new EntityGib(world, living, 8, explo, this));
        world.spawnEntityInWorld(new EntityGib(world, living, 9, explo, this));
    }

    @Override
    public boolean isApplicableFor(EntityLivingBase living){
        if (living instanceof EntityCreeper) return true;  //EntityPigZombie already extends EntityZombie
        return false;
    }
    
    // ===============================================================================================
    // Render Section
    // ===============================================================================================
    
    @Override
    public ModelRenderer setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, EntityGib gib){
        switch ( gib.type ){
            case 0:         //Head
                head32.rotateAngleY = f3 / 57.29578F;
                head32.rotateAngleX = f4 / 57.29578F;
                return head32;
            case 3:         //Body
                body32.rotateAngleY = f3 / 57.29578F;
                body32.rotateAngleX = f4 / 57.29578F;
                return body32;
            case 6:         //CreeperFoot
            case 7:         //CreeperFoot
            case 8:         //CreeperFoot
            case 9:         //CreeperFoot
                creeperFoot.rotateAngleY = f3 / 57.29578F;
                creeperFoot.rotateAngleX = f4 / 57.29578F;
                return creeperFoot;
        }
        return null;
    }

    @Override
    public void render(EntityGib ent, float f, float f1, float f2, float f3, float f4, float f5) {
        setRotationAngles(f, f1, f2, f3, f4, f5, ent)
                .render(f5);
    }
}
