package net.littleaura.differentworlds.block.custom;

import com.mojang.serialization.MapCodec;
import net.littleaura.differentworlds.block.entity.ModBlockEntities;
import net.littleaura.differentworlds.block.entity.custom.EnergyVesselBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

public class EnergyVesselBlock extends BaseEntityBlock {

    public static final BooleanProperty ACTIVATED = BooleanProperty.create("activated");

    public EnergyVesselBlock(Properties settings) {
        super(settings);

        registerDefaultState(defaultBlockState().setValue(ACTIVATED, false));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (!player.getAbilities().mayBuild) {
            // Skip if the player isn't allowed to modify the level.
            return InteractionResult.PASS;
        } else if (!player.isShiftKeyDown()) {
            if (level.getBlockEntity(pos) instanceof EnergyVesselBlockEntity energyVesselBlockEntity) {
                boolean activated = state.getValue(ACTIVATED);

                level.setBlockAndUpdate(pos, state.setValue(ACTIVATED, !activated));

                energyVesselBlockEntity.resetTSL();

                level.playSound(player, pos, SoundEvents.COMPARATOR_CLICK, SoundSource.BLOCKS, 1.0F, 1.0F);

                return InteractionResult.SUCCESS;
            } else {
                //show message in toolbar

                return InteractionResult.PASS;
            }
        } else if (player.getInventory().getSelectedItem().isEmpty()) {
            ItemStack blockstack = new ItemStack((state.getBlock().asItem()));
            blockstack.applyComponents(Objects.requireNonNull(level.getBlockEntity(pos)).collectComponents());

            player.getInventory().setSelectedItem(blockstack);

            level.removeBlock(pos, false);

            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.PASS;
        }
    }

    public static int getLuminance(BlockState currentBlockState) {
        boolean activated = currentBlockState.getValue(EnergyVesselBlock.ACTIVATED);

        return activated ? 15:0;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ACTIVATED);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(EnergyVesselBlock::new);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new EnergyVesselBlockEntity(blockPos, blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, ModBlockEntities.ENERGY_VESSEL_BLOCK_ENTITY, EnergyVesselBlockEntity::tick);
    }
}