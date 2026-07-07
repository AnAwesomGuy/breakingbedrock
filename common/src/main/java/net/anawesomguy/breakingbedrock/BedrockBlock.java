package net.anawesomguy.breakingbedrock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BedrockBlock extends Block {
    public BedrockBlock(Properties properties) {
        super(properties);
    }

    @Override
    public float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos) {
        return player.getInventory().getSelectedItem().isCorrectToolForDrops(state) ?
               player.getDestroySpeed(state) / state.getDestroySpeed(level, pos) / 30 :
               0F;
    }
}
