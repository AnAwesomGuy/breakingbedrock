package net.anawesomguy.breakingbedrock;

import net.minecraft.references.BlockItemId;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public final class BreakingBedrockMixinPlugin implements IMixinConfigPlugin {
    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetName, String mixinName) {
        return switch(mixinName) {
            case "net.anawesomguy.breakingbedrock.mixin.BlocksMixin_LootTable" -> {
                BreakingBedrock.LOGGER.info("Loading mixin for giving bedrock a loot table");
                yield BreakingBedrock.DROP_BEDROCK;
            }
            case "net.anawesomguy.breakingbedrock.mixin.BlocksMixin_ReplaceBedrock_NEW_26_2" -> {
                try {
                    @SuppressWarnings("unused")
                    Class<?> c = BlockItemId.class;
                    BreakingBedrock.LOGGER.info("Loading mixin for 26.2-snapshot-3 and above");
                    yield true; // BlockItemId exists, use new mixin
                } catch (NoClassDefFoundError e) {
                    yield false; // BlockItemId doesn't exist, don't use new mixin
                }
            }
            case "net.anawesomguy.breakingbedrock.mixin.BlocksMixin_ReplaceBedrock_OLD" -> {
                try {
                    @SuppressWarnings("unused")
                    Class<?> c = BlockItemId.class;
                    yield false; // BlockItemId exists, don't use old mixin
                } catch (NoClassDefFoundError e) {
                    BreakingBedrock.LOGGER.info("Loading mixin for older 26.1.x versions");
                    yield true; // BlockItemId doesn't exist, use old mixin
                }
            }
            default -> true;
        };
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetName, ClassNode targetClass, String mixinName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetName, ClassNode targetClass, String mixinName, IMixinInfo mixinInfo) {
    }
}
