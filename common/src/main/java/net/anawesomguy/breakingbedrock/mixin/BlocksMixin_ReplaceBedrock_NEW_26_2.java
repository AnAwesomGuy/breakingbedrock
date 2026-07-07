package net.anawesomguy.breakingbedrock.mixin;

import net.anawesomguy.breakingbedrock.BedrockBlock;
import net.anawesomguy.breakingbedrock.BreakingBedrock;
import net.minecraft.references.BlockItemId;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

import java.util.function.Function;

@Mixin(Blocks.class)
public abstract class BlocksMixin_ReplaceBedrock_NEW_26_2 {
    @Shadow
    private static Block register(BlockItemId id, Function<Properties, Block> factory, Properties properties) {
        throw new AssertionError();
    }

    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Blocks;register(Lnet/minecraft/references/BlockItemId;Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/Block;", ordinal = 0), slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/references/BlockItemIds;BEDROCK:Lnet/minecraft/references/BlockItemId;", opcode = Opcodes.GETSTATIC)))
    private static Block breakingbedrock$replaceBedrock(BlockItemId id, Properties properties) {
        return register(id, props -> new BedrockBlock(props.strength(BreakingBedrock.DESTROY_TIME, BreakingBedrock.EXPLOSION_RESIST).requiresCorrectToolForDrops()), properties);
    }
}
