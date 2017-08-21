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

public class RootCommandExecutor implements CommandExecutor{
    @Override
    public CommandResult execute(CommandSource source, CommandContext args) throws CommandException{
        source.sendMessage(Text.builder("BlockAction ").color(TextColors.GOLD).style(TextStyles.BOLD).append(Text.builder("V"+ BlockAction.VERSION).style(TextStyles.BOLD).color(TextColors.GREEN).build()).build());
        return CommandResult.success();
    }
}
