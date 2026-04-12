package net.anawesomguy.breakingbedrock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BedrockBlock extends Block {
    public BedrockBlock(Properties properties) {
        super(properties);
    }

    @Override
    @SuppressWarnings("deprecation")
    public float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos) {
        Item item = player.getMainHandItem().getItem();
        return item instanceof DiggerItem &&
               ((DiggerItem)item).getTier().getLevel() >= BreakingBedrock.MINING_TIER &&
               item.isCorrectToolForDrops(state) ?
            player.getDestroySpeed(state) / state.getDestroySpeed(level, pos) / 30 :
            0F;
    }
}
