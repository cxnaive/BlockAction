package com.cxandy.BlockAction.Commands;

import com.cxandy.BlockAction.Data.CommandScript.CommandScript;
import com.cxandy.BlockAction.Data.CommandScript.ScriptData;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import java.util.List;

public class ItemStorageListExecutor implements CommandExecutor{
    @Override
    public CommandResult execute(CommandSource source, CommandContext args) throws CommandException{
        if(!(source instanceof Player)){
            source.sendMessage(Text.builder("ItemStorage list command can only be used by players.!")
                    .color(TextColors.RED)
                    .style(TextStyles.BOLD)
                    .build());
            return CommandResult.success();
        }
        Player player = (Player) source;
        ScriptData data = player.get(ScriptData.class).get();
        List<CommandScript> scripts = data.getScripts();
        int count = 0;
        for(int i = 0;i < scripts.size();++i){
            CommandScript cur = scripts.get(i);
            if(cur.ScriptMode == CommandScript.ItemDataStorageToken){
                ++count;
            }
        }
        player.sendMessage(Text.builder("Stored")
                .color(TextColors.YELLOW)
                .style(TextStyles.BOLD)
                .append(Text.builder(" "+count)
                        .color(TextColors.GREEN)
                        .build())
                .append(Text.of(" items in total."))
                .build());
        for(int i = 0;i < scripts.size();++i){
            CommandScript cur = scripts.get(i);
            if(cur.ScriptMode == CommandScript.ItemDataStorageToken){
                player.sendMessage(Text.builder("Token: ")
                        .color(TextColors.YELLOW)
                        .style(TextStyles.BOLD)
                        .append(Text.builder(cur.servermessagestr)
                                .color(TextColors.GREEN)
                                .build())
                        .append(Text.of(" ItemContent: "))
                        .append(Text.builder(cur.messagestr)
                                .color(TextColors.GREEN)
                                .build())
                        .build());
            }
        }
        return CommandResult.success();
    }
}