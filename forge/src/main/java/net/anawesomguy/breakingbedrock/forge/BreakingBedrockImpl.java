package net.anawesomguy.breakingbedrock.forge;

import net.anawesomguy.breakingbedrock.BreakingBedrock;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public final class BreakingBedrockImpl {
    public static Path configDir() {
        return FMLPaths.CONFIGDIR.get();
    }

    public static Boolean useOldRefmap() {
        try {
            // i tell shadowJar to ignore strings so this is used to detect neoforge
            Class.forName("net.minecraftforge.fml.loading.FMLLoader");
        } catch (ClassNotFoundException e) {
            // we on neoforge (no refmap)
            return null;
        }

        if (!FMLLoader.isProduction()) // only load refmap in prod
            return null; // no refmap, not in prod

        // BlockBehaviour.Properties.class.getMethod("m_60993_"); // CANNOT LOAD THIS CLASS EARLY (cuz of mixins) (this method only exists in old)
        String mcVersion = FMLLoader.versionInfo().mcVersion();
        if (mcVersion != null && mcVersion.startsWith("1."))
            try {
                return Integer.parseInt(mcVersion.split("\\.")[1]) < 19;
            } catch (NumberFormatException e) {
                // log the error below
            }
        BreakingBedrock.LOGGER.fatal("Unknown Minecraft version '{}'. Your game will likely crash soon!", mcVersion);
        return false; // the game is probably screwed at this point
    }
}
