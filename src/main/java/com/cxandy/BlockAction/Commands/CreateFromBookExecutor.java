package com.cxandy.BlockAction.Commands;

import com.cxandy.BlockAction.BlockAction;
import com.cxandy.BlockAction.Data.CommandScript.CommandScript;
import com.cxandy.BlockAction.Data.CommandScript.ScriptData;
import com.cxandy.BlockAction.Data.Pair;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class CreateFromBookExecutor implements CommandExecutor {
    private BlockAction instance;
    public CreateFromBookExecutor(BlockAction instance){
        this.instance = instance;
    }
    @Override
    public CommandResult execute(CommandSource source, CommandContext args) throws CommandException {
        String modestr = args.<String>getOne(Text.of("mode")).get();
        if(source instanceof Player){
            Player player = (Player)source;
            Optional<ItemStack> itemOpt = player.getItemInHand(HandTypes.MAIN_HAND);
            if(itemOpt.isPresent() &&
                    (itemOpt.get().getItem() == ItemTypes.WRITABLE_BOOK || itemOpt.get().getItem() == ItemTypes.WRITTEN_BOOK)){
                ItemStack nowbook = itemOpt.get();
                StringBuilder builder = new StringBuilder();
                Optional<List<Text>> listOptional = nowbook.get(Keys.BOOK_PAGES);
                if(!listOptional.isPresent()){
                    player.sendMessage(Text.builder("Your book page is empty!").color(TextColors.YELLOW).style(TextStyles.BOLD).build());
                    return CommandResult.success();
                }
                for(Text page: listOptional.get()){
                    builder.append(" " + page.toPlain());
                }
                boolean isAttachMode = modestr.equals("create");
                Pair<String,Optional<CommandScript>> result = CommandScript.ConvertStringFromSender(builder.toString(),isAttachMode?CommandScript.AttachModeToken:CommandScript.ChangeModeToken,player);
                if(result.getValue().isPresent()){
                    ScriptData data = player.get(ScriptData.class).get();
                    List<CommandScript> scripts = data.getScripts();
                    Iterator<CommandScript> iterator = scripts.iterator();
                    while(iterator.hasNext()){
                        CommandScript cur = iterator.next();
                        if(cur.ScriptMode != CommandScript.PostExcutingModeToken &&
                                cur.ScriptMode != CommandScript.ItemDataStorageToken){
                            iterator.remove();
                        }
                    }
                    scripts.add(result.getValue().get());
                    player.sendMessage(Text.builder("Successfully change mode to ")
                            .color(TextColors.YELLOW)
                            .append(Text.builder(isAttachMode?"AttachMode\n":"ChangeMode\n")
                                    .color(TextColors.BLUE)
                                    .style(TextStyles.BOLD)
                                    .build())
                            .append(Text.builder("Script Content:\n")
                                    .color(TextColors.GREEN)
                                    .build())
                            .append(Text.builder(result.getValue().get().toDescription()+"\n").style(TextStyles.NONE).color(TextColors.BLUE).build())
                            .append(Text.builder(isAttachMode?"Right click a block to attach this command to it!":
                                    "Left click a block to override/add its last script's contents!")
                                    .build())
                            .build());
                } else {
                    player.sendMessage(Text.builder("Invaild script pattern: \n")
                            .style(TextStyles.BOLD)
                            .color(TextColors.RED)
                            .append(Text.builder(result.getKey())
                                    .color(TextColors.YELLOW)
                                    .build())
                            .build());
                }
            } else {
                player.sendMessage(Text.builder("Your are not holding a writable/written book!").color(TextColors.YELLOW).style(TextStyles.BOLD).build());
            }
        }
        else {
            source.sendMessage(Text.builder("CreateFromBook command can only be used by players!").color(TextColors.RED).style(TextStyles.BOLD).build());
        }
        return CommandResult.success();
    }
}
