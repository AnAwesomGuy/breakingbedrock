package net.anawesomguy.breakingbedrock.mixin;

import net.anawesomguy.breakingbedrock.BedrockBlock;
import net.anawesomguy.breakingbedrock.BreakingBedrock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

import java.util.function.Function;

@Mixin(Blocks.class)
public abstract class BlocksMixin_ReplaceBedrock_OLD {
    @Shadow
    private static Block register(String string, Function<Properties, Block> function, Properties properties) {
        throw new AssertionError();
    }

    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "net/minecraft/world/level/block/Blocks.register(Ljava/lang/String;Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/Block;", ordinal = 0), slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=bedrock")))
    private static Block breakingbedrock$replaceBedrock(String name, Properties properties) {
        return register(name, props -> new BedrockBlock(props.strength(BreakingBedrock.DESTROY_TIME, BreakingBedrock.EXPLOSION_RESIST).requiresCorrectToolForDrops()), properties);
    }
}
