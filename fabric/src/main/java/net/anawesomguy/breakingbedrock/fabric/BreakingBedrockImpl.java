package net.anawesomguy.breakingbedrock.fabric;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import java.nio.file.Path;

public final class BreakingBedrockImpl {
    public static Path configDir() {
        return FabricLoader.getInstance().getConfigDir();
    }

    public static ItemStack getSelected(Inventory inv) {
        return inv.getSelectedItem();
    }
}
