package de.cripacx.hardermultiplayer.soulrevival;

import de.cripacx.hardermultiplayer.item.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public final class SoulRevivalRecipeRules {

    private SoulRevivalRecipeRules() {
    }

    public static ItemStack filterSoulCharmCraftResult(ItemStack result, Container craftMatrix) {
        return isSoulCharmCraftAllowed(result, craftMatrix) ? result : ItemStack.EMPTY;
    }

    public static boolean shouldBlockSoulCharmCraft(Player player, ItemStack result, Container craftMatrix) {
        if (player.level().isClientSide()) {
            return false;
        }

        if (isSoulCharmCraftAllowed(result, craftMatrix)) {
            return false;
        }

        player.sendSystemMessage(Component.literal("This Soul Charm recipe is not available in the current stage."));
        return true;
    }

    private static boolean isSoulCharmCraftAllowed(ItemStack result, Container craftMatrix) {
        if (!result.is(ModItems.soulCharm)) {
            return true;
        }

        SoulRevivalStage stage = SoulRevivalPersistence.getState().stage();
        return switch (stage) {
            case STAGE_1 -> matchesStage1(craftMatrix);
            case STAGE_2 -> matchesStage2(craftMatrix);
            case STAGE_3 -> matchesStage3(craftMatrix);
        };
    }

    private static boolean matchesStage1(Container matrix) {
        return matrix.getContainerSize() >= 9
                && is(matrix.getItem(0), Items.DIAMOND_BLOCK)
                && is(matrix.getItem(1), Items.GOLD_BLOCK)
                && is(matrix.getItem(2), Items.DIAMOND_BLOCK)
                && is(matrix.getItem(3), Items.GOLD_BLOCK)
                && is(matrix.getItem(4), Items.GOLDEN_APPLE)
                && is(matrix.getItem(5), Items.GOLD_BLOCK)
                && is(matrix.getItem(6), Items.DIAMOND_BLOCK)
                && is(matrix.getItem(7), Items.GOLD_BLOCK)
                && is(matrix.getItem(8), Items.DIAMOND_BLOCK);
    }

    private static boolean matchesStage2(Container matrix) {
        return matrix.getContainerSize() >= 9
                && is(matrix.getItem(0), Items.BLAZE_ROD)
                && is(matrix.getItem(1), Items.NETHERITE_SCRAP)
                && is(matrix.getItem(2), Items.BLAZE_ROD)
                && is(matrix.getItem(3), Items.NETHERITE_SCRAP)
                && is(matrix.getItem(4), Items.GHAST_TEAR)
                && is(matrix.getItem(5), Items.NETHERITE_SCRAP)
                && is(matrix.getItem(6), Items.BLAZE_ROD)
                && is(matrix.getItem(7), Items.NETHERITE_SCRAP)
                && is(matrix.getItem(8), Items.BLAZE_ROD);
    }

    private static boolean matchesStage3(Container matrix) {
        return matrix.getContainerSize() >= 9
                && is(matrix.getItem(0), Items.NETHER_STAR)
                && is(matrix.getItem(1), Items.ECHO_SHARD)
                && is(matrix.getItem(2), Items.NETHER_STAR)
                && is(matrix.getItem(3), Items.NETHERITE_INGOT)
                && is(matrix.getItem(4), Items.ENCHANTED_GOLDEN_APPLE)
                && is(matrix.getItem(5), Items.NETHERITE_INGOT)
                && is(matrix.getItem(6), Items.END_CRYSTAL)
                && is(matrix.getItem(7), Items.ECHO_SHARD)
                && is(matrix.getItem(8), Items.END_CRYSTAL);
    }

    private static boolean is(ItemStack stack, net.minecraft.world.level.ItemLike itemLike) {
        return !stack.isEmpty() && stack.is(itemLike.asItem());
    }
}
