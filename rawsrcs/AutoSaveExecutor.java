package com.cxandy.BlockAction.Commands;

import com.cxandy.BlockAction.BlockAction;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

public class AutoSaveExecutor implements CommandExecutor {
    private BlockAction instance;

    public AutoSaveExecutor(BlockAction instance) {
        this.instance = instance;
    }

    @Override
    public CommandResult execute(CommandSource source, CommandContext args) throws CommandException {
        String mode = args.<String>getOne(Text.of("mode")).get();
        if (mode.equals("start")) {
            instance.PluginConfig.AutoSaveEnable = true;
        }
        if (mode.equals("stop")) {
            instance.PluginConfig.AutoSaveEnable = false;
        }
        source.sendMessage(Text.builder("Now State: ")
                .style(TextStyles.BOLD)
                .color(TextColors.YELLOW)
                .append(Text.builder(instance.PluginConfig.AutoSaveEnable?"Started\n":"Stopped\n")
                        .color(instance.PluginConfig.AutoSaveEnable?TextColors.GREEN:TextColors.RED)
                        .build())
                .append(Text.of("Now Set Interval:"))
                .append(Text.builder(" " + instance.PluginConfig.AutoSaveInterval)
                        .color(TextColors.GREEN)
                        .build())
                .build());
        return CommandResult.success();
    }
}
