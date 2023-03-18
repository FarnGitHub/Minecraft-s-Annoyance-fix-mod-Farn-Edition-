package FarnUtil.boat;

import net.minecraft.src.*;

import java.util.List;

public class EntityBoatProxy extends Entity {
	public int boatCurrentDamage;
	public int boatTimeSinceHit;
	public int boatRockDirection;
	private int field_9394_d;
	private double field_9393_e;
	private double field_9392_f;
	private double field_9391_g;
	private double field_9390_h;
	private double field_9389_i;
	private double field_9388_j;
	private double field_9387_k;
	private double field_9386_l;

	public EntityBoatProxy(World world1) {
		super(world1);
		this.boatCurrentDamage = 0;
		this.boatTimeSinceHit = 0;
		this.boatRockDirection = 1;
		this.preventEntitySpawning = true;
		this.setSize(1.5F, 0.6F);
		this.yOffset = this.height / 2.0F;
	}

	protected boolean canTriggerWalking() {
		return false;
	}

	protected void entityInit() {
	}

	public AxisAlignedBB getCollisionBox(Entity entity1) {
		return entity1.boundingBox;
	}

	public AxisAlignedBB getBoundingBox() {
		return this.boundingBox;
	}

	public boolean canBePushed() {
		return true;
	}

	public EntityBoatProxy(World world1, double d2, double d4, double d6) {
		this(world1);
		this.setPosition(d2, d4 + (double)this.yOffset, d6);
		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;
		this.prevPosX = d2;
		this.prevPosY = d4;
		this.prevPosZ = d6;
	}

	public double getMountedYOffset() {
		return (double)this.height * 0.0D - (double)0.3F;
	}

	public boolean attackEntityFrom(Entity entity1, int i2) {
		if(!this.worldObj.multiplayerWorld && !this.isDead) {
			this.boatRockDirection = -this.boatRockDirection;
			this.boatTimeSinceHit = 10;
			this.boatCurrentDamage += i2 * 10;
			this.setBeenAttacked();
			if(this.boatCurrentDamage > 40) {
				if(this.riddenByEntity != null) {
					this.riddenByEntity.mountEntity(this);
				}
				this.dropItemWithOffset(Item.boat.shiftedIndex, 1, 0.0F);
				this.setEntityDead();
			}

			return true;
		} else {
			return true;
		}
	}

	public void performHurtAnimation() {
		this.boatRockDirection = -this.boatRockDirection;
		this.boatTimeSinceHit = 10;
		this.boatCurrentDamage += this.boatCurrentDamage * 10;
	}

	public boolean canBeCollidedWith() {
		return !this.isDead;
	}

	public void setPositionAndRotation2(double d1, double d3, double d5, float f7, float f8, int i9) {
		this.field_9393_e = d1;
		this.field_9392_f = d3;
		this.field_9391_g = d5;
		this.field_9390_h = (double)f7;
		this.field_9389_i = (double)f8;
		this.field_9394_d = i9 + 4;
		this.motionX = this.field_9388_j;
		this.motionY = this.field_9387_k;
		this.motionZ = this.field_9386_l;
	}

	public void setVelocity(double d1, double d3, double d5) {
		this.field_9388_j = this.motionX = d1;
		this.field_9387_k = this.motionY = d3;
		this.field_9386_l = this.motionZ = d5;
	}

	public void onUpdate() {
		super.onUpdate();
		if(this.boatTimeSinceHit > 0) {
			--this.boatTimeSinceHit;
		}

		if(this.boatCurrentDamage > 0) {
			--this.boatCurrentDamage;
		}

		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		byte b1 = 5;
		double d2 = 0.0D;

		for(int i4 = 0; i4 < b1; ++i4) {
			double d5 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double)(i4 + 0) / (double)b1 - 0.125D;
			double d7 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double)(i4 + 1) / (double)b1 - 0.125D;
			AxisAlignedBB axisAlignedBB9 = AxisAlignedBB.getBoundingBoxFromPool(this.boundingBox.minX, d5, this.boundingBox.minZ, this.boundingBox.maxX, d7, this.boundingBox.maxZ);
			if(this.worldObj.isAABBInMaterial(axisAlignedBB9, Material.water)) {
				d2 += 1.0D / (double)b1;
			}
		}

		double d6;
		double d8;
		double d10;
		double d21;
		if(this.worldObj.multiplayerWorld) {
			if(this.field_9394_d > 0) {
				d21 = this.posX + (this.field_9393_e - this.posX) / (double)this.field_9394_d;
				d6 = this.posY + (this.field_9392_f - this.posY) / (double)this.field_9394_d;
				d8 = this.posZ + (this.field_9391_g - this.posZ) / (double)this.field_9394_d;

				for(d10 = this.field_9390_h - (double)this.rotationYaw; d10 < -180.0D; d10 += 360.0D) {
				}

				while(d10 >= 180.0D) {
					d10 -= 360.0D;
				}

				this.rotationYaw = (float)((double)this.rotationYaw + d10 / (double)this.field_9394_d);
				this.rotationPitch = (float)((double)this.rotationPitch + (this.field_9389_i - (double)this.rotationPitch) / (double)this.field_9394_d);
				--this.field_9394_d;
				this.setPosition(d21, d6, d8);
				this.setRotation(this.rotationYaw, this.rotationPitch);
			} else {
				d21 = this.posX + this.motionX;
				d6 = this.posY + this.motionY;
				d8 = this.posZ + this.motionZ;
				this.setPosition(d21, d6, d8);
				if(this.onGround) {
					this.motionX *= 0.5D;
					this.motionY *= 0.5D;
					this.motionZ *= 0.5D;
				}

				this.motionX *= (double)0.99F;
				this.motionY *= (double)0.95F;
				this.motionZ *= (double)0.99F;
			}

		} else {
			if(d2 < 1.0D) {
				d21 = d2 * 2.0D - 1.0D;
				this.motionY += (double)0.04F * d21;
			} else {
				if(this.motionY < 0.0D) {
					this.motionY /= 2.0D;
				}

				this.motionY += 0.007000000216066837D;
			}

			if(this.riddenByEntity != null) {
				this.motionX += this.riddenByEntity.motionX * 0.2D;
				this.motionZ += this.riddenByEntity.motionZ * 0.2D;
			}

			d21 = 0.4D;
			if(this.motionX < -d21) {
				this.motionX = -d21;
			}

			if(this.motionX > d21) {
				this.motionX = d21;
			}

			if(this.motionZ < -d21) {
				this.motionZ = -d21;
			}

			if(this.motionZ > d21) {
				this.motionZ = d21;
			}

			if(this.onGround) {
				this.motionX *= 0.5D;
				this.motionY *= 0.5D;
				this.motionZ *= 0.5D;
			} else {
				this.motionX *= (double)0.99F;
				this.motionY *= (double)0.95F;
				this.motionZ *= (double)0.99F;
			}

			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			d6 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
			if(d6 > 0.15D) {
				d8 = Math.cos((double)this.rotationYaw * Math.PI / 180.0D);
				d10 = Math.sin((double)this.rotationYaw * Math.PI / 180.0D);

				for(int i12 = 0; (double)i12 < 1.0D + d6 * 60.0D; ++i12) {
					double d13 = (double)(this.rand.nextFloat() * 2.0F - 1.0F);
					double d15 = (double)(this.rand.nextInt(2) * 2 - 1) * 0.7D;
					double d17;
					double d19;
					if(this.rand.nextBoolean()) {
						d17 = this.posX - d8 * d13 * 0.8D + d10 * d15;
						d19 = this.posZ - d10 * d13 * 0.8D - d8 * d15;
						this.worldObj.spawnParticle("splash", d17, this.posY - 0.125D, d19, this.motionX, this.motionY, this.motionZ);
					} else {
						d17 = this.posX + d8 + d10 * d13 * 0.7D;
						d19 = this.posZ + d10 - d8 * d13 * 0.7D;
						this.worldObj.spawnParticle("splash", d17, this.posY - 0.125D, d19, this.motionX, this.motionY, this.motionZ);
					}
				}
			}

			this.rotationPitch = 0.0F;
			d8 = (double)this.rotationYaw;
			d10 = this.prevPosX - this.posX;
			double d23 = this.prevPosZ - this.posZ;
			if(d10 * d10 + d23 * d23 > 0.001D) {
				d8 = (double)((float)(Math.atan2(d23, d10) * 180.0D / Math.PI));
			}

			double d14;
			for(d14 = d8 - (double)this.rotationYaw; d14 >= 180.0D; d14 -= 360.0D) {
			}

			while(d14 < -180.0D) {
				d14 += 360.0D;
			}

			if(d14 > 20.0D) {
				d14 = 60.0D;
			}

			if(d14 < -20.0D) {
				d14 = -60.0D;
			}

			this.rotationYaw = (float)((double)this.rotationYaw + d14);
			this.setRotation(this.rotationYaw, this.rotationPitch);
			List list16 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand((double)0.2F, 0.0D, (double)0.2F));
			int i24;
			if(list16 != null && list16.size() > 0) {
				for(i24 = 0; i24 < list16.size(); ++i24) {
					Entity entity18 = (Entity)list16.get(i24);
					if(entity18 != this.riddenByEntity && entity18.canBePushed() && entity18 instanceof EntityBoat) {
						entity18.applyEntityCollision(this);
					}
				}
			}

			for(i24 = 0; i24 < 4; ++i24) {
				int i25 = MathHelper.floor_double(this.posX + ((double)(i24 % 2) - 0.5D) * 0.8D);
				int i26 = MathHelper.floor_double(this.posY);
				int i20 = MathHelper.floor_double(this.posZ + ((double)(i24 / 2) - 0.5D) * 0.8D);
				if(this.worldObj.getBlockId(i25, i26, i20) == Block.snow.blockID) {
					this.worldObj.setBlockWithNotify(i25, i26, i20, 0);
				}
			}

			if(this.riddenByEntity != null && this.riddenByEntity.isDead) {
				this.riddenByEntity = null;
			}

		}
	}

	public void updateRiderPosition() {
		if(this.riddenByEntity != null) {
			double d1 = Math.cos((double)this.rotationYaw * Math.PI / 180.0D) * 0.4D;
			double d3 = Math.sin((double)this.rotationYaw * Math.PI / 180.0D) * 0.4D;
			this.riddenByEntity.setPosition(this.posX + d1, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ + d3);
		}
	}

	public float getShadowSize() {
		return 0.0F;
	}

	protected void readEntityFromNBT(NBTTagCompound nBTTagCompound1) {
	}

	protected void writeEntityToNBT(NBTTagCompound nBTTagCompound1) {
	}

	public boolean interact(EntityPlayer entityPlayer1) {
		if(this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer && this.riddenByEntity != entityPlayer1) {
			return true;
		} else {
			if(!this.worldObj.multiplayerWorld) {
				entityPlayer1.mountEntity(this);
				if(entityPlayer1.ridingEntity == null) {
					entityPlayer1.setPosition(entityPlayer1.posX, entityPlayer1.posY + 0.1f, entityPlayer1.posZ);
				}
			}

			return true;
		}
	}
}
