package net.anawesomguy.breakingbedrock.fabric;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.VersionParsingException;

import java.nio.file.Path;
import java.util.NoSuchElementException;

public final class BreakingBedrockImpl {
    public static Path configDir() {
        return FabricLoader.getInstance().getConfigDir();
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public static Boolean useOldRefmap() {
        if (FabricLoader.getInstance().isDevelopmentEnvironment())
            return null;
        try {
            return FabricLoader.getInstance().getModContainer("minecraft")
                               .get()
                               .getMetadata()
                               .getVersion()
                               .compareTo(Version.parse("1.18.2")) <= 0;
        } catch (VersionParsingException | NoSuchElementException e) {
            return false;
        }
    }
}
