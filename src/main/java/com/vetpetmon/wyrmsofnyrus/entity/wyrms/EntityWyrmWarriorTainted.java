package com.vetpetmon.wyrmsofnyrus.entity.wyrms;

import com.hbm.interfaces.IRadiationImmune;
import com.hbm.lib.ModDamageSource;
import com.hbm.potion.HbmPotion;
import com.vetpetmon.wyrmsofnyrus.SoundRegistry;
import com.vetpetmon.wyrmsofnyrus.config.Client;
import com.vetpetmon.wyrmsofnyrus.config.Evo;
import com.vetpetmon.wyrmsofnyrus.config.WyrmStats;
import com.vetpetmon.wyrmsofnyrus.entity.EntityWyrmFlying;
import com.vetpetmon.wyrmsofnyrus.evo.EvoPoints;
import com.vetpetmon.wyrmsofnyrus.item.AllItems;
import com.vetpetmon.wyrmsofnyrus.locallib.ai.EntityAIFlierMob;
import com.vetpetmon.wyrmsofnyrus.locallib.ai.EntityAIFlierMoveRandom;
import com.vetpetmon.wyrmsofnyrus.locallib.ai.movehelpers.FlierMoveHelperGhastlike;
import com.vetpetmon.wyrmsofnyrus.locallib.DifficultyStats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;


public class EntityWyrmWarriorTainted extends EntityWyrmFlying implements IAnimatable, IAnimationTickable, IRadiationImmune {
    private final AnimationFactory factory = new AnimationFactory(this);
    //private boolean isCharging;
    public EntityWyrmWarriorTainted(World world) {
        super(world);
        this.casteType = 2;
        setSize(0.9f, 2.0f);
        experienceValue = 30;
        this.navigator = new PathNavigateFlying(this, this.world);
        this.moveHelper = new FlierMoveHelperGhastlike(this, 20, 1.0, 0.8);
        enablePersistence();
        setNoAI(false);
        setPotency(10);
        this.setAnimationNames(new String[]{"warriorwyrm.groundedIdle","warriorwyrm.groundedRun","warriorwyrm.idle","warriorwyrm.moving","warriorwyrm.inWater","warriorwyrm.swim"});
    }


    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        if (Evo.evoEnabled && (EvoPoints.getLevel() >= Evo.minEvoWarriorTainted)) this.setStatsEvo(WyrmStats.taintedWarriorHP, WyrmStats.taintedWarriorDEF, WyrmStats.taintedWarriorATK, WyrmStats.taintedWarriorSPD, WyrmStats.taintedWarriorKBR, Evo.minEvoWarriorTainted);
        else this.setStats(WyrmStats.taintedWarriorHP, WyrmStats.taintedWarriorDEF, WyrmStats.taintedWarriorATK, WyrmStats.taintedWarriorSPD, WyrmStats.taintedWarriorKBR);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        simpleAI();
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.05D, false));
        this.tasks.addTask(4, new EntityAIFlierMob(this, 3.05, 200));
        this.tasks.addTask(8, new EntityAIFlierMoveRandom(this));
        //this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
        this.afterPlayers();
        this.afterVillagers();
        this.afterAnimals();
        this.afterMobs();
        hivemindFollow();
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        boolean result = super.attackEntityAsMob(entityIn);
        if (result) {
            DifficultyStats.applyPotionEffect(entityIn, HbmPotion.taint, 240, 1);
        }
        return result;
    }
    @Override
    public void onLivingUpdate(){
        super.onLivingUpdate();
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.setNoGravity(true);
    }

    @Override
    protected Item getDropItem() {
        return new ItemStack(AllItems.wyrmarmorfrag, 4).getItem();
    }

    @Override
    public SoundEvent getAmbientSound() {
        return SoundRegistry.wyrmroars;
    }
    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.bat.takeoff"));
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source.isMagicDamage()) amount = 0.0F;
        if (source.isProjectile()) amount *= 0.5F;
        if (source.isExplosion()) amount *= 0.5F;
        if (source == ModDamageSource.radiation || source == ModDamageSource.mudPoisoning) {
            this.heal(amount * 1.25F);
            return false;
        }
        return super.attackEntityFrom(source, amount);
    }

    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 2F, this::predicate));
    }
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        if (event.isMoving()) {
            if (isInWater() && Client.fancyAnimations) event.getController().setAnimation(getAnimation(5));
            else if (isGrounded()) event.getController().setAnimation(getAnimation(1));
            else event.getController().setAnimation(getAnimation(3));
        }
        else {
            if (isInWater() && Client.fancyAnimations) event.getController().setAnimation(getAnimation(4));
            else if (isGrounded()) event.getController().setAnimation(getAnimation(0));
            else event.getController().setAnimation(getAnimation(2));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public int tickTimer() {
        return ticksExisted;
    }

    @Override
    public void tick() {super.onUpdate();}
}
