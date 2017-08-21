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
import java.util.Optional;

public class ModeSetterExecutor implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource source, CommandContext args) throws CommandException {
        Optional<Player> spplayer = args.<Player>getOne(Text.of("player"));
        Player player;
        if (!(source instanceof Player)) {
            if (!spplayer.isPresent()) {
                source.sendMessage(Text.builder("You must specify which player you wish to perform this action on.!").color(TextColors.RED).style(TextStyles.BOLD).build());
                return CommandResult.success();
            }
            player = spplayer.get();
        } else {
            //noinspection OptionalIsPresent
            if (spplayer.isPresent()) player = spplayer.get();
            else player = (Player) source;
        }
        ScriptData data = player.get(ScriptData.class).get();
        List<CommandScript> scripts = data.getScripts();
        Iterator<CommandScript> iterator = scripts.iterator();
        while (iterator.hasNext()) {
            CommandScript cur = iterator.next();
            if (cur.ScriptMode != CommandScript.PostExcutingModeToken
                    && cur.ScriptMode != CommandScript.ItemDataStorageToken) {
                iterator.remove();
            }
        }
        String str = args.<String>getOne("modename").get().toLowerCase();
        if (str.equals("disabled")) {
            scripts.add(CommandScript.DisableMode);
            source.sendMessage(Text.builder("Successfully changed player mode to ").color(TextColors.YELLOW).append(Text.builder("Disabled").color(TextColors.RED).style(TextStyles.BOLD).build()).build());
        } else if (str.equals("viewmode")) {
            scripts.add(CommandScript.ViewMode);
            source.sendMessage(Text.builder("Successfully changed player mode to ").color(TextColors.YELLOW).append(Text.builder("ViewMode").color(TextColors.GRAY).style(TextStyles.BOLD).build()).build());
        } else if (str.equals("deletemode")) {
            scripts.add(CommandScript.DeleteMode);
            source.sendMessage(Text.builder("Successfully changed player mode to ").color(TextColors.YELLOW).append(Text.builder("DeleteMode").color(TextColors.BLUE).style(TextStyles.BOLD).build()).build());
        } else if (str.equals("clearmode")) {
            scripts.add(CommandScript.ClearMode);
            source.sendMessage(Text.builder("Successfully changed player mode to ").color(TextColors.YELLOW).append(Text.builder("ClearMode").color(TextColors.BLUE).style(TextStyles.BOLD).build()).build());
        } else {
            source.sendMessage(Text.builder("An error occured when excuting this command!\n")
                    .color(TextColors.RED)
                    .build());
        }
        return CommandResult.success();
    }
}
