package com.cxandy.BlockAction.Commands;

import com.cxandy.BlockAction.BlockAction;
import com.cxandy.BlockAction.Data.CommandScript.CommandScript;
import com.cxandy.BlockAction.Data.CommandScript.ScriptData;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class GetItemNameInHandExecutor implements CommandExecutor{
    private BlockAction instance;
    public GetItemNameInHandExecutor(BlockAction instance){
        this.instance = instance;
    }
    private String chu(String str){
        if(str.startsWith("minecraft:")) return str.substring(10).trim();
        if(str.charAt(0) == '/') return str.substring(1);
        return str;
    }
    @Override
    public CommandResult execute(CommandSource source, CommandContext args) throws CommandException{
        if(source instanceof Player){
            Player player = (Player)source;
            Optional<ItemStack> itemStackOptional = player.getItemInHand(HandTypes.MAIN_HAND);
            if(itemStackOptional.isPresent()){
                Optional<BlockState> state = itemStackOptional.get().get(Keys.ITEM_BLOCKSTATE);
                String childid = "NULL";
                if(state.isPresent()){
                    childid = chu(state.get().getId());
                }
                String nameid = chu(itemStackOptional.get().getItem().getName());
                player.sendMessage(Text.builder("The item's name is:\n")
                        .color(TextColors.YELLOW)
                        .style(TextStyles.BOLD)
                        .append(Text.builder(nameid + "\n")
                                .color(TextColors.GREEN)
                                .build())
                        .append(Text.of("BlockState is:\n"))
                        .append(Text.builder(childid)
                                .color(TextColors.GREEN)
                                .build())
                        .build());
                Optional<String> tokenopt = args.<String>getOne(Text.of("token"));
                if(tokenopt.isPresent()){
                    String token = tokenopt.get();
                    ScriptData data = player.get(ScriptData.class).get();
                    List<CommandScript> scripts = data.getScripts();
                    Iterator<CommandScript> iterator = scripts.iterator();
                    boolean isexist = false;
                    while (iterator.hasNext()) {
                        CommandScript cur = iterator.next();
                        if (cur.ScriptMode == CommandScript.ItemDataStorageToken) {
                            if(cur.servermessagestr.equals(token)){
                                cur.messagestr = nameid + ":" + childid;
                                isexist = true;
                            }
                        }
                    }
                    if(!isexist){
                        CommandScript now = CommandScript.ConvertToItemDataStorage(nameid,childid,token).getValue().get();
                        scripts.add(now);
                    }
                    player.sendMessage(Text.builder("Successfully set Item-Storage data:\n")
                            .color(TextColors.YELLOW)
                            .style(TextStyles.BOLD)
                            .append(Text.of("Token:"))
                            .append(Text.builder(token + "\n")
                                    .color(TextColors.GREEN)
                                    .build())
                            .append(Text.of("ItemContent:\n"))
                            .append(Text.builder(nameid+":"+childid)
                                    .color(TextColors.GREEN)
                                    .build())
                            .build());
                }
            }
            else{
                player.sendMessage(Text.builder("Your MainHand is empty! Where's the item?!").color(TextColors.YELLOW).style(TextStyles.BOLD).build());
            }
        } else {
            source.sendMessage(Text.builder("Getitemname command can only be used by players!").color(TextColors.RED).style(TextStyles.BOLD).build());
        }
        return CommandResult.success();
    }
}
