package net.anawesomguy.breakingbedrock.mixin;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Blocks.class)
public abstract class BlocksMixin_LootTable {
    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "net/minecraft/world/level/block/state/BlockBehaviour$Properties.noLootTable()Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;", ordinal = 0), slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=bedrock")))
    private static Properties breakingbedrock$addLootTable(Properties properties) {
        return properties;
    }
}
