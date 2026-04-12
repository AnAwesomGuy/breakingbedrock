package net.anawesomguy.breakingbedrock;

import dev.architectury.injectables.annotations.ExpectPlatform;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class BreakingBedrock {
    public static final String MOD_ID = "breakingbedrock";
    public static final Logger LOGGER = LogManager.getLogger("Name");

    public static final float DESTROY_TIME;
    public static final float EXPLOSION_RESIST;
    public static final int MINING_TIER;
    public static final boolean DROP_BEDROCK;

    static {
        Logger log = LOGGER;
        log.info("Initializing Breaking Bedrock. This mod overwrites bedrock, and may break with other mods that do the same!");
        Properties properties = new Properties();
        float destroyTime, explosionResist;
        int miningLevel;
        boolean dropBedrock;
        Path configFile = configDir().resolve(BreakingBedrock.MOD_ID + ".properties");

        configRead:
        {
            if (Files.exists(configFile)) // fall out if not exist
                try (InputStream in = Files.newInputStream(configFile)) {
                    Properties temp = new Properties();
                    temp.load(in);
                    String time = temp.getProperty("destroy_time"),
                        resist = temp.getProperty("explosion_resist"),
                        level = temp.getProperty("mining_level"),
                        drop = temp.getProperty("drop_bedrock");

                    if (time != null && ((destroyTime = Float.parseFloat(time)) >= 0 || destroyTime == -1F))
                        properties.setProperty("destroy_time", time);
                    else {
                        properties.setProperty("destroy_time", "100");
                        destroyTime = 100F;
                        log.debug("Correcting invalid config value {} for destroy_time!", time);
                    }

                    if (resist != null && (explosionResist = Float.parseFloat(resist)) >= 0)
                        properties.setProperty("explosion_resist", resist);
                    else {
                        properties.setProperty("explosion_resist", "3600000");
                        explosionResist = 3600000F;
                        log.debug("Correcting invalid config value {} for explosion_resist!", resist);
                    }

                    if (level != null && (miningLevel = Integer.parseInt(level)) >= 0) {
                        properties.setProperty("mining_level", level);
                    } else {
                        properties.setProperty("mining_level", "4");
                        miningLevel = 4;
                        log.debug("Correcting invalid config value {} for mining_level!", level);
                    }

                    boolean isFalse = "false".equalsIgnoreCase(drop);
                    boolean isTrue = "true".equalsIgnoreCase(drop);
                    if (drop == null || (!isFalse && !isTrue)) { // if value is not set or is neither "false" nor "true"
                        properties.setProperty("drop_bedrock", "false");
                        dropBedrock = false;
                        log.debug("Correcting invalid config value {} for drop_bedrock!", drop);
                    } else
                        properties.setProperty("drop_bedrock", String.valueOf(dropBedrock = isTrue));
                    break configRead; // break out
                } catch (IOException ignored) {
                    // fall out
                }
            log.info("Couldn't read config file (likely corrupted or missing)! Attempting to (re)create it.");
            properties.setProperty("destroy_time", "100");
            properties.setProperty("explosion_resist", "3600000");
            properties.setProperty("mining_level", "4");
            properties.setProperty("drop_bedrock", "false");
            destroyTime = 100F;
            explosionResist = 3600000F;
            miningLevel = 4;
            dropBedrock = false;
        }

        EXPLOSION_RESIST = explosionResist;
        DESTROY_TIME = destroyTime;
        MINING_TIER = miningLevel;
        DROP_BEDROCK = dropBedrock;

        try (OutputStream out = Files.newOutputStream(configFile)) {
            properties.store(out,
                             " destroy_time: The destroy time for bedrock. (obsidian is 50, stone is 1.5, -1 is indestructible)\n" +
                                 " explosion_resist: The explosion resistance for bedrock. (stone is 6, glass is 0.3)\n" +
                                 " mining_level: The mining level required to mine bedrock. (netherite is 4)\n" +
                                 " drop_bedrock: Whether bedrock should drop as a block when broken.\n");
        } catch (IOException e) {
            log.error("Unable to create/modify config file!", e);
        }

        log.debug("Config initialized with values: destroyTime={}, explosionResist={}, miningLevel={}, drop_bedrock={}",
                     destroyTime, explosionResist, miningLevel, dropBedrock);
    }

    @ExpectPlatform
    public static Path configDir() {
        throw new AssertionError();
    }

    // return null for no refmap
    @ExpectPlatform
    public static Boolean useOldRefmap() {
        throw new AssertionError();
    }
}
