package com.cxandy.BlockAction.Commands;

import com.cxandy.BlockAction.BlockAction;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.Optional;

public class ReloadExecutor implements CommandExecutor{
    private BlockAction instance;
    public ReloadExecutor(BlockAction instance){
        this.instance = instance;
    }
    @Override
    public CommandResult execute(CommandSource source, CommandContext args) throws CommandException{
        Optional<String> spectrOpt = args.<String>getOne("specific");
        if(spectrOpt.isPresent()){
            if(spectrOpt.get().equals("data")){
                instance.BlockScriptsConfigLoader.LoadData();
                source.sendMessage(Text.builder("Successfully reloaded blockdatas.")
                        .color(TextColors.YELLOW)
                        .build());
            }
            else {
                instance.BlockScriptsConfigLoader.LoadConfig();
                source.sendMessage(Text.builder("Successfully reloaded configs.")
                        .color(TextColors.YELLOW)
                        .build());
            }
        }
        else{
            instance.BlockScriptsConfigLoader.LoadDataAndConfig();
            source.sendMessage(Text.builder("Successfully reloaded blockdatas and configs.")
                    .color(TextColors.YELLOW)
                    .build());
        }
        return CommandResult.success();
    }
}
