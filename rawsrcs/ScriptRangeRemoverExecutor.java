package com.cxandy.BlockAction.Commands;

import com.cxandy.BlockAction.BlockAction;
import com.cxandy.BlockAction.Data.CommandScript.CommandScript;
import com.cxandy.BlockAction.DataHandler.DataHandler;
import com.flowpowered.math.vector.Vector3i;
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

import java.util.*;

public class ScriptRangeRemoverExecutor implements CommandExecutor{
    private BlockAction instance;
    public ScriptRangeRemoverExecutor(BlockAction instance){
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
        Optional<DataHandler> handlerOptional = instance.WorldDataSeter.GetDataHandler(worldUUID);
        final List<Vector3i> blockposes = new ArrayList<>();
        blockposes.clear();
        //noinspection OptionalIsPresent
        if (handlerOptional.isPresent()) {
            DataHandler handler = handlerOptional.get();
            int vecx = args.<Integer>getOne(Text.of("x")).get();
            int vecy = args.<Integer>getOne(Text.of("y")).get();
            int vecz = args.<Integer>getOne(Text.of("z")).get();
            int range = args.<Integer>getOne(Text.of("range")).get();
            Vector3i now = new Vector3i(vecx, vecy, vecz);
            for (Map.Entry<Vector3i, List<CommandScript>> entry : handler.getEntryset()) {
                if (entry.getValue().isEmpty()) blockposes.add(entry.getKey());
                else if (entry.getKey().distanceSquared(now) <= range * range) {
                    blockposes.add(entry.getKey());
                }
            }
            for (int i = 0; i < blockposes.size(); ++i) {
                handler.removeScriptData(blockposes.get(i));
            }
        }
        Text.Builder builder = Text.builder(blockposes.size() + " ")
                .style(TextStyles.BOLD)
                .color(TextColors.GREEN)
                .append(Text.builder("scripts removed in total in world ")
                        .style(TextStyles.NONE)
                        .color(TextColors.YELLOW)
                        .build())
                .append(Text.builder(worldName)
                        .color(TextColors.GREEN)
                        .build());
        source.sendMessage(builder.build());
        return CommandResult.success();
    }
}
