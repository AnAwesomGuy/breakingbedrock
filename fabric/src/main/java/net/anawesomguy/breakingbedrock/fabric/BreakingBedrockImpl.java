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
        if ("named".equals(FabricLoader.getInstance().getMappingResolver().getCurrentRuntimeNamespace()))
            return null; // we are using named mappings (yarn/mojmap)?
        try {
            // old refmap for <22w12a (2nd snapshot for 1.19) (so technically this wouldn't work on 22w11a and deep dark experiments but i doubt anyone is playing with this mod on those versions)
            return FabricLoader.getInstance()
                               .getModContainer("minecraft")
                               .get()
                               .getMetadata()
                               .getVersion()
                               .compareTo(Version.parse("1.19")) < 0; // return <1.19
        } catch (VersionParsingException | NoSuchElementException e) {
            // this should never ever happen
            throw new RuntimeException(e);
        }
    }
}
