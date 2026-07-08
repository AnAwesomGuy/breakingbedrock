package net.anawesomguy.breakingbedrock.fabric;

import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public final class BreakingBedrockImpl {
    public static Path configDir() {
        return FabricLoader.getInstance().getConfigDir();
    }
}
