package net.anawesomguy.breakingbedrock.neoforge;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.loading.FMLPaths;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.nio.file.Path;

public final class BreakingBedrockImpl {
    private static final MethodHandle SELECTED;

    static {
        MethodHandle m;
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType type = MethodType.methodType(ItemStack.class);
        try {
            //noinspection JavaLangInvokeHandleSignature
            m = lookup.findVirtual(Inventory.class, "getSelected", type); // <1.21.5
        } catch (ReflectiveOperationException e) {
            try {
                m = lookup.findVirtual(Inventory.class, "getSelectedItem", type); // >=1.21.5ec
            } catch (ReflectiveOperationException ex) {
                throw new RuntimeException(ex);
            }
        }
        SELECTED = m;
    }

    public static Path configDir() {
        try {
            return FMLPaths.CONFIGDIR.get();
        } catch (LinkageError e) {
            return net.minecraftforge.fml.loading.FMLPaths.CONFIGDIR.get();
        }
    }

    public static ItemStack getSelected(Inventory inv) {
        try {
            return (ItemStack) SELECTED.invoke(inv);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
