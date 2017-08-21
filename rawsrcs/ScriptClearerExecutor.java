package com.cxandy.BlockAction.Commands;

import com.cxandy.BlockAction.BlockAction;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;
import org.spongepowered.api.world.storage.WorldProperties;

import java.util.Optional;
import java.util.UUID;

public class ScriptClearerExecutor implements CommandExecutor{
    private BlockAction instance;
    public ScriptClearerExecutor(BlockAction instance){
        this.instance = instance;
    }
    @Override
    public CommandResult execute(CommandSource source, CommandContext args) throws CommandException {
        Optional<WorldProperties> worldOptional = args.<WorldProperties>getOne(Text.of("world"));
        UUID worldUUID;
        String worldName;
        if (!(source instanceof Player)) {
            if (!worldOptional.isPresent()) {
                source.sendMessage(Text.builder("You must specify which world you wish to perform this action on.!").color(TextColors.RED).style(TextStyles.BOLD).build());
                return CommandResult.success();
            }
            worldUUID = worldOptional.get().getUniqueId();
            worldName = worldOptional.get().getWorldName();
        } else {
            if (worldOptional.isPresent()) {
                worldUUID = worldOptional.get().getUniqueId();
                worldName = worldOptional.get().getWorldName();
            }
            else {
                worldUUID = ((Player) source).getWorld().getUniqueId();
                worldName = ((Player) source).getWorld().getName();
            }
        }
        instance.WorldDataSeter.ClearData(worldUUID);
        source.sendMessage(Text.builder("Successfully removed all scripts in world ")
                .color(TextColors.YELLOW)
                .append(Text.builder(worldName)
                        .style(TextStyles.BOLD)
                        .color(TextColors.GREEN)
                        .build())
                .build());
        return CommandResult.success();
    }
}