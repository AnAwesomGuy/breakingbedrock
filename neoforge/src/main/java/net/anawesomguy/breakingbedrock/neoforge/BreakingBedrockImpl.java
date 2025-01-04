package net.anawesomguy.breakingbedrock.neoforge;

import net.neoforged.fml.loading.FMLPaths;

import java.nio.file.Path;

public final class BreakingBedrockImpl {
    public static Path configDir() {
        try {
            return FMLPaths.CONFIGDIR.get();
        } catch (LinkageError e) {
            return net.minecraftforge.fml.loading.FMLPaths.CONFIGDIR.get();
        }
    }
}
