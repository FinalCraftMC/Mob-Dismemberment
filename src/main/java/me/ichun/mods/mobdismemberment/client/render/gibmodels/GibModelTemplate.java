package me.ichun.mods.mobdismemberment.client.render.gibmodels;

import me.ichun.mods.mobdismemberment.client.entity.EntityGib;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public interface GibModelTemplate {

    public void spawnEntityGibInWorld(World world, EntityLivingBase living, Entity explo);

    public ModelRenderer setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, EntityGib ent);

    public boolean isApplicableFor(EntityLivingBase living);

    public void render(EntityGib ent, float f, float f1, float f2, float f3, float f4, float f5);

}
