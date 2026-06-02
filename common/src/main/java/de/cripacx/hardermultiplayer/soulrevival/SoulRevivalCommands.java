package de.cripacx.hardermultiplayer.soulrevival;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.commands.BalmCommands;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.permissions.Permissions;

public final class SoulRevivalCommands {

    private static final Identifier PERMISSION_STAGE_SET = Identifier.fromNamespaceAndPath("hardermultiplayer", "command.soulrevival.stage.set");

    private SoulRevivalCommands() {
    }

    public static void register() {
        BalmCommands.registerPermission(PERMISSION_STAGE_SET, Permissions.COMMANDS_OWNER);
        Balm.commands().register(SoulRevivalCommands::registerRoot);
    }

    private static void registerRoot(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("soulrevival")
                .then(Commands.literal("stage")
                        .then(Commands.literal("get")
                                .executes(context -> {
                                    SoulRevivalStage stage = SoulRevivalPersistence.getState().stage();
                                    context.getSource().sendSuccess(() -> Component.literal("Soul Revival stage: " + stage.value()), false);
                                    return stage.value();
                                }))
                        .then(Commands.literal("set")
                            .requires(BalmCommands.requirePermission(PERMISSION_STAGE_SET))
                                .then(Commands.argument("value", IntegerArgumentType.integer(1, 3))
                                        .executes(context -> {
                                            int value = IntegerArgumentType.getInteger(context, "value");
                                            SoulRevivalStage stage = SoulRevivalStage.fromValue(value);
                                            boolean changed = SoulRevivalPersistence.setStage(stage);

                                            if (changed) {
                                                SoulRevivalPersistence.save(context.getSource().getServer());
                                            }

                                            context.getSource().sendSuccess(() -> Component.literal("Soul Revival stage set to " + stage.value()), true);
                                            return 1;
                                        })))));
    }
}
