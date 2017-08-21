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

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ItemStorageRemoveExecutor implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource source, CommandContext args) throws CommandException{
        if(!(source instanceof Player)){
            source.sendMessage(Text.builder("ItemStorage remove command can only be used by players.!")
                    .color(TextColors.RED)
                    .style(TextStyles.BOLD)
                    .build());
            return CommandResult.success();
        }
        Collection<String> tokens = args.getAll(Text.of("token"));
        Player player = (Player) source;
        ScriptData data = player.get(ScriptData.class).get();
        List<CommandScript> scripts = data.getScripts();
        int count = 0;
        Iterator<CommandScript> iterator = scripts.iterator();
        while (iterator.hasNext()) {
            CommandScript cur = iterator.next();
            if (cur.ScriptMode == CommandScript.ItemDataStorageToken) {
                if(tokens.contains(cur.servermessagestr)){
                    ++count;
                    iterator.remove();
                    player.sendMessage(Text.builder("Successfully removed item-storage ")
                            .color(TextColors.YELLOW)
                            .style(TextStyles.BOLD)
                            .append(Text.builder(cur.servermessagestr)
                                    .color(TextColors.GREEN)
                                    .build())
                            .build());
                }
            }
        }
        player.sendMessage(Text.builder("Removed")
                .color(TextColors.YELLOW)
                .style(TextStyles.BOLD)
                .append(Text.builder(" " + count)
                        .color(TextColors.GREEN)
                        .build())
                .append(Text.of(" item-storages in total."))
                .build());
        return CommandResult.success();
    }
}
