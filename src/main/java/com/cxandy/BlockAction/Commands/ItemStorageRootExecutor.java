package com.cxandy.BlockAction.Commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

public class ItemStorageRootExecutor implements CommandExecutor{
    @Override
    public CommandResult execute(CommandSource source, CommandContext args) throws CommandException{
        if(!(source instanceof Player)){
            source.sendMessage(Text.of("ItemStorage Commands can only be used by players.!"));
        }
        source.sendMessage(Text.builder("ItemStorage Sub Commands are:\n ")
                .color(TextColors.GOLD)
                .style(TextStyles.BOLD)
                .append(Text.builder("-> ")
                        .color(TextColors.BLUE)
                        .build())
                .append(Text.builder("clear\n")
                        .color(TextColors.GREEN)
                        .build())
                .append(Text.builder("-> ")
                        .color(TextColors.BLUE)
                        .build())
                .append(Text.builder("list\n")
                        .color(TextColors.GREEN)
                        .build())
                .append(Text.builder("-> ")
                        .color(TextColors.BLUE)
                        .build())
                .append(Text.builder("remove token1 token2 token3 ...\n")
                        .color(TextColors.GREEN)
                        .build())
                .build());
        return CommandResult.success();
    }
}
