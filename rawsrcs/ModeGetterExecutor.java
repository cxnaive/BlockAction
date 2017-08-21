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
import java.util.Optional;

public class ModeGetterExecutor implements CommandExecutor {
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
            if (spplayer.isPresent()) player = spplayer.get();
            else player = (Player) source;
        }
        ScriptData data = player.get(ScriptData.class).get();
        List<CommandScript> scripts = data.getScripts();
        if (scripts.isEmpty()) scripts.add(CommandScript.DisableMode);
        for (int i = 0; i < scripts.size(); ++i) {
            CommandScript cur = scripts.get(i);
            if ((cur.ScriptMode != CommandScript.PostExcutingModeToken)
                    && (cur.ScriptMode != CommandScript.ItemDataStorageToken)) {
                if (cur.ScriptMode == CommandScript.ChangeModeToken || cur.ScriptMode == CommandScript.AttachModeToken) {
                    source.sendMessage(Text.builder("Player current Mode: ")
                            .color(TextColors.YELLOW)
                            .style(TextStyles.BOLD)
                            .append(Text.builder(cur.ScriptMode == CommandScript.ChangeModeToken ? "ChangeMode\n" : "AttachMode\n")
                                    .color(TextColors.GREEN)
                                    .build())
                            .append(Text.builder("Script Content:\n")
                                    .color(TextColors.GREEN)
                                    .build())
                            .append(Text.builder(cur.toDescription()).style(TextStyles.NONE).color(TextColors.BLUE).build())
                            .build());
                } else if (cur.ScriptMode == CommandScript.DisabledModeToken) {
                    source.sendMessage(Text.builder("Player current Mode: ")
                            .color(TextColors.YELLOW)
                            .style(TextStyles.BOLD)
                            .append(Text.builder("Disabled")
                                    .color(TextColors.RED)
                                    .build())
                            .build());
                } else if (cur.ScriptMode == CommandScript.ViewModeToken) {
                    source.sendMessage(Text.builder("Player current Mode: ")
                            .color(TextColors.YELLOW)
                            .style(TextStyles.BOLD)
                            .append(Text.builder("ViewMode")
                                    .color(TextColors.GRAY)
                                    .build())
                            .build());
                } else if (cur.ScriptMode == CommandScript.DeleteModeToken) {
                    source.sendMessage(Text.builder("Player current Mode: ")
                            .color(TextColors.YELLOW)
                            .style(TextStyles.BOLD)
                            .append(Text.builder("DeleteMode")
                                    .color(TextColors.BLUE)
                                    .build())
                            .build());
                } else {
                    source.sendMessage(Text.builder("Player current Mode: ")
                            .color(TextColors.YELLOW)
                            .style(TextStyles.BOLD)
                            .append(Text.builder("ClearMode")
                                    .color(TextColors.BLUE)
                                    .build())
                            .build());
                }
            }
        }
        return CommandResult.success();
    }
}
