package br.com.finalcraft.bettermobdismemberment.client.entity;

import br.com.finalcraft.bettermobdismemberment.client.render.gibmodels.GibModelTemplate;
import br.com.finalcraft.bettermobdismemberment.client.render.gibmodels.customnpcs.INPCGibModel;
import br.com.finalcraft.bettermobdismemberment.client.util.CustomNPCsUtil;
import br.com.finalcraft.bettermobdismemberment.common.BetterMobDismemberment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityMinecartTNT;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class EntityGib extends Entity
{
    public EntityLivingBase parent;
    public int type;
    public float pitchSpin;
    public float yawSpin;

    public int groundTime;
    public int liveTime;

    public boolean explosion;

    public GibModelTemplate gibModelTemplate = null;

    public GibModelTemplate getGibModelTemplate() {
        return gibModelTemplate;
    }

    public EntityGib(World world){
        super(world);
        type = 0; //0 == head; 1 == left arm; 2 == right arm; 3 == head; 4 == left leg; 5 == right leg; 6+ creeperfeet.
        groundTime = 0;
        liveTime = (int)world.getWorldTime();
        ignoreFrustumCheck = true;
    }

    public EntityGib(World world, EntityLivingBase gibParent, int gibType, Entity explo, GibModelTemplate gibModelTemplate){
        this(world);
        this.gibModelTemplate = gibModelTemplate;
        parent = gibParent;
        type = gibType;
        liveTime = (int)worldObj.getWorldTime();

        setLocationAndAngles(parent.posX, parent.boundingBox.minY, parent.posZ, parent.rotationYaw, parent.rotationPitch);
        rotationYaw = parent.prevRenderYawOffset;
        prevRotationYaw = parent.rotationYaw;
        prevRotationPitch = parent.rotationPitch;

        motionX = parent.motionX + (rand.nextDouble() - rand.nextDouble()) * 0.25D;
        motionY = parent.motionY;
        motionZ = parent.motionZ + (rand.nextDouble() - rand.nextDouble()) * 0.25D;

        this.gibModelTemplate.entityCreationSizeDeffinition(this);

        float i = rand.nextInt(45) + 5F + rand.nextFloat();
        float j = rand.nextInt(45) + 5F + rand.nextFloat();
        if(rand.nextInt(2) == 0)
        {
            i *= -1;
        }
        if(rand.nextInt(2) == 0)
        {
            j *= -1;
        }
        pitchSpin = i * (float)(motionY + 0.3D);
        yawSpin = j * (float)(Math.sqrt(motionX * motionZ) + 0.3D);

        setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);

        if(explo != null)
        {
            double mag = 1.0D;
            double mag2 = 1.0D;
            double dist = explo.getDistanceToEntity(parent);
            dist = Math.pow(dist / 2D, 2);
            if(dist < 0.1D)
            {
                dist = 0.1D;
            }
            if(explo instanceof EntityTNTPrimed || explo instanceof EntityMinecartTNT)
            {
                mag = 1.0D * (4.0 / dist);
            }
            else if(explo instanceof EntityCreeper)
            {
                EntityCreeper creep = (EntityCreeper)explo;
                if(creep.getPowered())
                {
                    mag = 1.0D * (6.0D / dist);
                }
                else
                {
                    mag = 1.0D * (3.0D / dist);
                }
            }
            mag = Math.pow(mag, 2) * 0.2D;
            mag2 = ((posY - explo.posY));
            motionX *= mag;
            motionY = mag2 * 0.4D + 0.22D;
            motionZ *= mag;

            explosion = true;
        }
    }

    @Override
    public void onUpdate()
    {
        if(parent == null)
        {
            setDead();
            return;
        }
        if(explosion)
        {
            motionX *= 1D / 0.92D;
            motionY *= 1D / 0.95D;
            motionZ *= 1D / 0.92D;
        }
        super.onUpdate();
        moveEntity(motionX, motionY, motionZ);

        this.motionY -= 0.08D;

        this.motionY *= 0.98D;
        this.motionX *= 0.91D;
        this.motionZ *= 0.91D;

        if(inWater)
        {
            motionY = 0.3D;
            pitchSpin = 0.0F;
            yawSpin = 0.0F;
        }
        if(onGround || handleWaterMovement())
        {
            rotationPitch += (-90F - (rotationPitch % 360F)) / 2;

            this.motionY *= 0.8D;
            this.motionX *= 0.8D;
            this.motionZ *= 0.8D;
        }
        else
        {
            rotationPitch += pitchSpin;
            rotationYaw += yawSpin;
            pitchSpin *= 0.98F;
            yawSpin *= 0.98F;
        }

        //If pushing is enabled and the entity is not a CustomNPCs with size lower than 5
        if(BetterMobDismemberment.config.gibPushing == 1 && !(this.gibModelTemplate instanceof INPCGibModel && CustomNPCsUtil.getModelRenderSize(this.parent) <= 4) )
        {
            List var2 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(0.15D, 0.0D, 0.15D));
            if(var2 != null && !var2.isEmpty())
            {
                Iterator var10 = var2.iterator();

                while(var10.hasNext())
                {
                    Entity var4 = (Entity)var10.next();

                    if(var4 instanceof EntityGib && !var4.onGround)
                    {
                        continue;
                    }

                    if(var4.canBePushed())
                    {
                        var4.applyEntityCollision(this);
                    }
                }
            }
        }

        if(onGround || handleWaterMovement())
        {
            groundTime++;
            if(groundTime > BetterMobDismemberment.config.gibGroundTime + 20)
            {
                setDead();
            }
        }
        else if(groundTime > BetterMobDismemberment.config.gibGroundTime)
        {
            groundTime--;
        }
        else
        {
            groundTime = 0;
        }
        if(liveTime + BetterMobDismemberment.config.gibTime < (int)worldObj.getWorldTime())
        {
            setDead();
        }
    }

    @Override
    protected void fall(float f) {
    }

    @Override
    public boolean isEntityAlive()
    {
        return !this.isDead;
    }

    @Override
    protected void entityInit()
    {
    }

    @Override
    public boolean writeToNBTOptional(NBTTagCompound par1NBTTagCompound)
    {
        return false;
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {}

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {}

    public ResourceLocation getParentTexture(){
        return this.gibModelTemplate.getParentTexture(this);
    }

    public void setSize(float width, float height){
        super.setSize(width,height);
    }

    public float getRenderHeigh(){
        return this.gibModelTemplate.getRenderHeigh(this);
    }

    public float getRenderWidth(){
        return this.gibModelTemplate.getRenderWidth(this);
    }
}
