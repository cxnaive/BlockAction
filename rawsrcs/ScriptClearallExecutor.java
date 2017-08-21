package com.cxandy.BlockAction.Commands;

import com.cxandy.BlockAction.BlockAction;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class ScriptClearallExecutor implements CommandExecutor{
    private BlockAction instance;
    public ScriptClearallExecutor(BlockAction instance){
        this.instance = instance;
    }
    @Override
    public CommandResult execute(CommandSource source, CommandContext args) throws CommandException {
        instance.WorldDataSeter.ClearAll();
        source.sendMessage(Text.builder("Successfully removed all scripts in all worlds.").color(TextColors.YELLOW).build());
        return CommandResult.success();
    }
}
