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

import java.util.Iterator;
import java.util.List;

public class ItemStorageClearExecutor implements CommandExecutor{
    @Override
    public CommandResult execute(CommandSource source, CommandContext args) throws CommandException{
        if(!(source instanceof Player)){
            source.sendMessage(Text.builder("ItemStorage clear command can only be used by players.!")
                    .color(TextColors.RED)
                    .style(TextStyles.BOLD)
                    .build());
            return CommandResult.success();
        }
        Player player = (Player) source;
        ScriptData data = player.get(ScriptData.class).get();
        List<CommandScript> scripts = data.getScripts();
        Iterator<CommandScript> iterator = scripts.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            CommandScript cur = iterator.next();
            if (cur.ScriptMode == CommandScript.ItemDataStorageToken) {
                ++count;
                iterator.remove();
            }
        }
        player.sendMessage(Text.builder("Successfully removed")
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