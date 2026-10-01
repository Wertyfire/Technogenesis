package ru.wertyfiregames.technogenesis.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import ru.wertyfiregames.technogenesis.init.TechBlockEntities;

public class PressBlockEntity extends BlockEntity {
    public PressBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(TechBlockEntities.PRESS.get(), blockPos, blockState);
    }
}