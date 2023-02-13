package com.vetpetmon.wyrmsofnyrus.entity.wyrms;

import com.vetpetmon.wyrmsofnyrus.SoundRegistry;
import com.vetpetmon.wyrmsofnyrus.config.Evo;
import com.vetpetmon.wyrmsofnyrus.config.wyrmStats;
import com.vetpetmon.wyrmsofnyrus.entity.EntityWyrm;
import com.vetpetmon.wyrmsofnyrus.evo.evoPoints;
import com.vetpetmon.wyrmsofnyrus.item.AllItems;
import com.vetpetmon.wyrmsofnyrus.synapselib.difficultyStats;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntityWyrmSoldierfrost extends EntityWyrm implements IAnimatable, IAnimationTickable {
    private final AnimationFactory factory = new AnimationFactory(this);
    public EntityWyrmSoldierfrost(World world) {
        super(world);
        this.casteType = 3;
        setSize(2.2f, 1.5f);
        experienceValue = 20;
        enablePersistence();
        setNoAI(false);
        setPotency(15);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(3, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.0D, false));
        afterPlayers();
        hivemindFollow();
        if (getAttackVillagers()) afterVillagers();
        afterMobs();
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        if (Evo.evoEnabled && (evoPoints.getLevel() >= Evo.minEvoSoldierFrost)) this.setStatsEvo(wyrmStats.frostSoldierHP,wyrmStats.frostSoldierDEF,wyrmStats.frostSoldierATK,wyrmStats.frostSoldierSPD,wyrmStats.frostSoldierKBR,Evo.minEvoSoldierFrost);
        else this.setStats(wyrmStats.frostSoldierHP,wyrmStats.frostSoldierDEF,wyrmStats.frostSoldierATK,wyrmStats.frostSoldierSPD,wyrmStats.frostSoldierKBR);
    }

    @Override
    protected Item getDropItem() {
        return new ItemStack(AllItems.wyrmarmorfrag, 3).getItem();
    }

    @Override
    public SoundEvent getAmbientSound() {
        return SoundRegistry.deepwyrmclicks;
    }
    @Override
    public SoundEvent getHurtSound(DamageSource ds)  {
        return SoundRegistry.wyrmHissTwo;
    }
    /*@Override
    public SoundEvent getDeathSound() {
        return SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.enderdragon_fireball.explode"));
    }*/

    protected void playStepSound(BlockPos pos, Block blockIn)
    {
        this.playSound(SoundRegistry.slowwyrmsteps, 1.0F, 1.0F);
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source.isMagicDamage()) amount *= 0.5F;
        return super.attackEntityFrom(source, amount);
    }

    public boolean attackEntityAsMob(Entity entityIn) {
        boolean result = super.attackEntityAsMob(entityIn);
        if (result) {
            difficultyStats.applyPotionEffect(entityIn, MobEffects.SLOWNESS, 40, 4);
            difficultyStats.applyPotionEffect(entityIn, MobEffects.WEAKNESS, 60, 1);
        }
        return result;
    }
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 2F, this::predicate));
    }
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.soldierwyrm.moving"));
        }
        else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.soldierwyrm.idle"));
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
