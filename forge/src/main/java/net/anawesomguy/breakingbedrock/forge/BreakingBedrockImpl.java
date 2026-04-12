package net.anawesomguy.breakingbedrock.forge;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public final class BreakingBedrockImpl {
    public static Path configDir() {
        return FMLPaths.CONFIGDIR.get();
    }

    public static Boolean useOldRefmap() {
        try {
            if (!(Boolean)Class.forName("net.minecraftforge.fml.loading.FMLLoader")
                               .getMethod("isProduction")
                               .invoke(null))
                return null;
            BlockBehaviour.Properties.class.getMethod("m_60993_");
            return true;
        } catch (ClassNotFoundException e) {
            return null;
        } catch (ReflectiveOperationException e) {
            return false;
        }
    }
}
