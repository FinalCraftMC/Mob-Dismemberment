package br.com.finalcraft.bettermobdismemberment.client.particle;

import br.com.finalcraft.bettermobdismemberment.common.BetterMobDismemberment;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

public class ParticleBlood extends EntityFX {

    public ParticleBlood(World world, double d, double d1, double d2, double d3, double d4, double d5, boolean isPlayer) {
        super(world, d, d1, d2, d3, d4, d5);
        this.particleGravity = 1.2F;
        this.particleRed = 1.2F;
        this.particleGreen = this.particleBlue = 0.0F;
        this.particleGreen = BetterMobDismemberment.config.greenBlood == 1 && !isPlayer ? 5.0F : 0.0F;
        this.particleScale *= 1.5F;
        this.multiplyVelocity(1.2F);
        this.motionY += (double)(this.rand.nextFloat() * 0.15F);
        this.motionZ *= (double)(0.4F / (this.rand.nextFloat() * 0.9F + 0.1F));
        this.motionX *= (double)(0.4F / (this.rand.nextFloat() * 0.9F + 0.1F));
        this.particleMaxAge = (int)(200.0F + 20.0F / (this.rand.nextFloat() * 0.9F + 0.1F));
        this.setParticleTextureIndex(19 + this.rand.nextInt(4));
        this.renderDistanceWeight = 10.0D;
    }

    public void renderParticle(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5) {
        super.renderParticle(tessellator, f, f1, f2, f3, f4, f5);
    }

    public int getBrightnessForRender(float f) {
        int i = super.getBrightnessForRender(f);
        float f1 = (float)this.particleAge / (float)this.particleMaxAge;
        f1 *= f1;
        f1 *= f1;
        int j = i & 255;
        int k = i >> 16 & 255;
        k += (int)(f1 * 15.0F * 16.0F);
        if (k > 240) {
            k = 240;
        }

        return j | k << 16;
    }

    public float getBrightness(float f) {
        float f1 = super.getBrightness(f);
        float f2 = (float)this.particleAge / (float)this.particleMaxAge;
        f2 *= f2;
        f2 *= f2;
        return f1 * (1.0F - f2) + f2;
    }

    public void onUpdate() {
        ++this.ticksExisted;
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }

        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (!this.isCollided) {
            this.motionY -= 0.04D * (double)this.particleGravity;
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.9800000190734863D;
            this.motionY *= 0.9800000190734863D;
            this.motionZ *= 0.9800000190734863D;
            if (this.onGround) {
                this.motionX *= 0.699999988079071D;
                this.motionZ *= 0.699999988079071D;
                this.posY += 0.1D;
            }
        }

    }

    @Override
    public int getFXLayer() {
        return 0;
    }
}