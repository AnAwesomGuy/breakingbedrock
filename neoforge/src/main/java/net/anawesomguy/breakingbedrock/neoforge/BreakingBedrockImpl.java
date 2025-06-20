package net.anawesomguy.breakingbedrock.neoforge;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.loading.FMLPaths;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.nio.file.Path;
import java.util.function.Function;

public final class BreakingBedrockImpl {
    private static final Function<Inventory, ItemStack> SELECTED;

    static {
        Function<Inventory, ItemStack> selected;
        try {
            //noinspection JavaLangInvokeHandleSignature
            MethodHandle m = MethodHandles.lookup().findVirtual(Inventory.class, "getSelected", MethodType.methodType(ItemStack.class));
            selected = inv -> {
                try {
                    return (ItemStack) m.invoke(inv);
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            };
        } catch (ReflectiveOperationException e) {
            selected = Inventory::getSelectedItem;
        }
        SELECTED = selected;
    }

    public static Path configDir() {
        try {
            return FMLPaths.CONFIGDIR.get();
        } catch (LinkageError e) {
            return net.minecraftforge.fml.loading.FMLPaths.CONFIGDIR.get();
        }
    }

    public static ItemStack getSelected(Inventory inv) {
        return SELECTED.apply(inv);
    }
}
