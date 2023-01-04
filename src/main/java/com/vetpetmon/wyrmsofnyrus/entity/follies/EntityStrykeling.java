package com.vetpetmon.wyrmsofnyrus.entity.follies;

import com.vetpetmon.wyrmsofnyrus.config.wyrmStats;
import com.vetpetmon.wyrmsofnyrus.entity.ability.painandsuffering.wyrmBreakDoors;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

public class EntityStrykeling extends EntityWyrmfolly implements IAnimatable, IAnimationTickable {
    protected int age; // currently unused
    public EntityStrykeling(World worldIn) {
        super(worldIn);
        this.age = 0;
    }

    public void StatMap() {
        this.setStats(
                wyrmStats.strykelingfollyHP,
                wyrmStats.strykelingfollyDEF,
                wyrmStats.strykelingfollyATK,
                wyrmStats.strykelingfollySPD,
                wyrmStats.strykelingfollyKBR
        );
    }

    @Override
    public void updateLevel(){
        super.updateLevel();
        this.StatMap();
    }
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.updateLevel();
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        if (compound.hasKey("age")) this.age = compound.getInteger("age");
    }

    @Override
    protected void initEntityAI() { //AYO YOUR LEAPING IS GOOFY!!! override the whole thing
        makeAllTargets();
        this.tasks.addTask(2, new wyrmBreakDoors(this, 200));
        this.tasks.addTask(1, new EntityAIWander(this, 0.45));
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.0D, true));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("age", this.age);
    }

    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 2F, this::predicate));
    }
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.strykling.moving"));
        }
        else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.strykling.idle"));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public int tickTimer() {return ticksExisted;}

    @Override
    public void tick() {super.onUpdate();}
}
