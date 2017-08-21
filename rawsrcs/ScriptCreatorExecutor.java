package com.cxandy.BlockAction.Commands;

import com.cxandy.BlockAction.Data.CommandScript.CommandScript;
import com.cxandy.BlockAction.Data.CommandScript.ScriptData;
import com.cxandy.BlockAction.Data.Pair;
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
import java.util.Optional;

public class ScriptCreatorExecutor implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource source, CommandContext args) throws CommandException {
        if(source instanceof Player){
            Player player = (Player) source;
            String content = args.<String>getOne(Text.of("content")).get();
            Pair<String,Optional<CommandScript>> result = CommandScript.ConvertStringFromSender(content,CommandScript.AttachModeToken,player);
            Optional<CommandScript> scriptOptional = result.getValue();
            if(scriptOptional.isPresent()){
                ScriptData data = player.get(ScriptData.class).get();
                List<CommandScript> scripts = data.getScripts();
                Iterator<CommandScript> iterator = scripts.iterator();
                while(iterator.hasNext()){
                    CommandScript cur = iterator.next();
                    if(cur.ScriptMode != CommandScript.PostExcutingModeToken
                            && cur.ScriptMode != CommandScript.ItemDataStorageToken){
                        iterator.remove();
                    }
                }
                scripts.add(scriptOptional.get());
                player.sendMessage(Text.builder("Successfully change mode to ")
                        .color(TextColors.YELLOW)
                        .append(Text.builder("AttachMode\n")
                                .color(TextColors.BLUE)
                                .style(TextStyles.BOLD)
                                .build())
                        .append(Text.builder("Script Content:\n")
                                .color(TextColors.GREEN)
                                .build())
                        .append(Text.builder(scriptOptional.get().toDescription()+"\n").style(TextStyles.NONE).color(TextColors.BLUE).build())
                        .append(Text.builder("Right click a block to attach this command to it!")
                                .build())
                        .build());
            }
            else{
                player.sendMessage(Text.builder("Invaild script pattern: \n")
                        .style(TextStyles.BOLD)
                        .color(TextColors.RED)
                        .append(Text.builder(result.getKey())
                                .color(TextColors.YELLOW)
                                .build())
                        .build());
            }
        }
        else {
            source.sendMessage(Text.builder("Create command can only be used by players!").color(TextColors.RED).style(TextStyles.BOLD).build());
        }
        return CommandResult.success();
    }
}
