package com.cxandy.BlockAction.ScriptExecutor;

import com.cxandy.BlockAction.BlockAction;
import com.cxandy.BlockAction.Data.CommandScript.CommandScript;
import com.cxandy.BlockAction.Data.CommandScript.ScriptData;
import com.cxandy.BlockAction.Data.Pair;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.Slot;
import org.spongepowered.api.service.economy.account.UniqueAccount;
import org.spongepowered.api.service.economy.transaction.ResultType;
import org.spongepowered.api.service.economy.transaction.TransactionResult;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import java.math.BigDecimal;
import java.util.*;

public class ExecuteHelper {
    private BlockAction instance;
    private String chu(String str){
        if(str.startsWith("minecraft:")) return str.substring(10).trim();
        if(str.charAt(0) == '/') return str.substring(1);
        return str;
    }
    public ExecuteHelper(BlockAction instance){
        this.instance = instance;
    }
    public boolean PostScriptExecute(Player player, CommandScript script){
        boolean isfailed = false;
        //@Player
        if(!script.RequiredPlayerName.isEmpty()){
            boolean cons = script.RequiredPlayerName.contains(player.getName());
            if(cons && script.RequiredPlayerType == CommandScript.ExcludeToken) {
                isfailed = true;
                if(instance.PluginConfig.SendExecuteInfoMessage && !script.isCloseInfo){
                    player.sendMessage(Text.builder("You are marked Excluded in this script.")
                            .color(TextColors.YELLOW)
                            .style(TextStyles.BOLD)
                            .build());
                }
            }
            if(!cons && script.RequiredPlayerType == CommandScript.HasoneToken) {
                isfailed = true;
                if(instance.PluginConfig.SendExecuteInfoMessage && !script.isCloseInfo){
                    player.sendMessage(Text.builder("You are not marked as a vaild excutor in this script.")
                            .color(TextColors.YELLOW)
                            .style(TextStyles.BOLD)
                            .build());
                }
            }
        }
        //@Item
        Iterable<Slot> slotIter = player.getInventory().slots();
        final List<Integer> itemcounts = new ArrayList<>();
        boolean itemhasone = false;
        boolean itemhasall = true;
        int itemhasidx = -1;
        if(!isfailed && !script.RequiredItemName.isEmpty()){
            itemcounts.clear();
            for(int i = 0;i < script.RequiredItemAmount.size();++i){
                itemcounts.add(0);
            }
            for(Slot nowslot: slotIter){
                Optional<ItemStack> stackOptional = nowslot.peek();
                if(stackOptional.isPresent()){
                    ItemStack stack = stackOptional.get();
                    Optional<BlockState> state = stack.get(Keys.ITEM_BLOCKSTATE);
                    int idx = script.RequiredItemName.indexOf(chu(stack.getItem().getName()));
                    String bsid = "NULL";
                    if(state.isPresent()){
                        bsid = chu(state.get().getId());
                    }
                    if(idx != -1 &&(script.RequiredItemBlockState.get(idx).equalsIgnoreCase("NULL")
                    || script.RequiredItemBlockState.get(idx).equalsIgnoreCase(bsid))){
                        itemcounts.set(idx,itemcounts.get(idx) + stack.getQuantity());
                    }
                }
            }

            for(int i = 0;i < script.RequiredItemAmount.size();++i){
                if(itemcounts.get(i) < script.RequiredItemAmount.get(i)){
                    itemhasall = false;
                }
                else {
                    itemhasone = true;
                    if(itemhasidx == -1) itemhasidx = i;
                    itemcounts.set(i, script.RequiredItemAmount.get(i));
                }
            }
            if(!itemhasall && script.RequiredItemType == CommandScript.HasallToken){
                isfailed = true;
                if(instance.PluginConfig.SendExecuteInfoMessage && !script.isCloseInfo){
                    player.sendMessage(Text.builder("Hasall Type:")
                            .color(TextColors.YELLOW)
                            .style(TextStyles.BOLD)
                            .build());
                    for(int i = 0;i < script.RequiredItemAmount.size();++i){
                        if(itemcounts.get(i) < script.RequiredItemAmount.get(i)){
                            player.sendMessage(Text.builder("Item ")
                                    .color(TextColors.YELLOW)
                                    .style(TextStyles.BOLD)
                                    .append(Text.builder(chu(script.RequiredItemBlockState.get(i)))
                                            .color(TextColors.GREEN)
                                            .build())
                                    .append(Text.of(" is not enough!"))
                                    .build());
                        }
                    }
                }
            }
            if(!itemhasone && script.RequiredItemType == CommandScript.HasoneToken){
                isfailed = true;
                if(instance.PluginConfig.SendExecuteInfoMessage && !script.isCloseInfo){
                    player.sendMessage(Text.builder("Hasone Type:\n")
                            .color(TextColors.YELLOW)
                            .style(TextStyles.BOLD)
                            .append(Text.of("You need to have at least one item satisfies the amount requirement."))
                            .build());
                }
            }
            if(itemhasone && script.RequiredItemType == CommandScript.ExcludeToken){
                isfailed = true;
                if(instance.PluginConfig.SendExecuteInfoMessage && !script.isCloseInfo){
                    player.sendMessage(Text.builder("Exclude Type:\n")
                            .color(TextColors.YELLOW)
                            .style(TextStyles.BOLD)
                            .append(Text.of("You can not have the items which are marked in this script."))
                            .build());
                }
            }
        }
        //@Hand
        if(!isfailed && !script.RequiredHandItemName.equals(CommandScript.NullItem)){
            Optional<ItemStack> stackOpt = player.getItemInHand(HandTypes.MAIN_HAND);
            if(stackOpt.isPresent()){
                ItemStack stack = stackOpt.get();
                Optional<BlockState> state = stack.get(Keys.ITEM_BLOCKSTATE);
                String bsid = "NULL";
                if(state.isPresent()){
                    bsid = chu(state.get().getId());
                }
                if(chu(stack.getItem().getName()).equalsIgnoreCase(chu(script.RequiredHandItemName)) && (script.RequiredHandItemBlockState.equalsIgnoreCase("NULL") ||
                script.RequiredHandItemBlockState.equalsIgnoreCase(bsid))){
                    if(stack.getQuantity() < script.RequiredHandItemAmount){
                        isfailed = true;
                    }
                }
                else isfailed = true;
            }
            else isfailed = true;
            if(isfailed && instance.PluginConfig.SendExecuteInfoMessage && !script.isCloseInfo){
                player.sendMessage(Text.builder("You do not satisfy the hand-item requirements").build());
            }
        }
        //@Perm
        if(!isfailed && !script.RequiredPerm.isEmpty()){
            boolean hasone = false;
            boolean hasall = true;
            for(String cur:script.RequiredPerm){
                if(!player.hasPermission(cur)){
                    hasall = false;
                }
                else hasone = true;
            }
            if(!hasall && script.RequiredPermType == CommandScript.HasallToken) isfailed = true;
            if(!hasone && script.RequiredPermType == CommandScript.HasoneToken) isfailed = true;
            if(hasone && script.RequiredPermType == CommandScript.ExcludeToken) isfailed = true;
            if(isfailed && instance.PluginConfig.SendExecuteInfoMessage && !script.isCloseInfo){
                player.sendMessage(Text.builder("You do not satisfy the permission requirements.")
                        .color(TextColors.YELLOW)
                        .style(TextStyles.BOLD)
                        .build());
            }
        }
        //@Group
        if(!isfailed && !script.RequiredGroup.isEmpty()){
            boolean hasone = false;
            boolean hasall = true;
            final List<Subject> parents = player.getParents();
            final List<String> namelist = new ArrayList<>();
            namelist.clear();
            for(Subject cur : parents) namelist.add(cur.getIdentifier());
            for(String cur: script.RequiredGroup){
                if(!namelist.contains(cur)){
                    hasall = false;
                }
                else hasone = true;
            }
            if(!hasall && script.RequiredGroupType == CommandScript.HasallToken) isfailed = true;
            if(!hasone && script.RequiredGroupType == CommandScript.HasoneToken) isfailed = true;
            if(hasone && script.RequiredGroupType == CommandScript.ExcludeToken) isfailed = true;
            if(isfailed && instance.PluginConfig.SendExecuteInfoMessage && !script.isCloseInfo)
            player.sendMessage(Text.builder("You do not satisfy the group requirements.")
                    .color(TextColors.YELLOW)
                    .style(TextStyles.BOLD)
                    .build());
        }
        //@Value
        if(!isfailed && script.RequiredValue != CommandScript.Undefined && instance.HasEconomyService){
            Optional<UniqueAccount> uOpt = instance.EconomyService.getOrCreateAccount(player.getUniqueId());
            if(uOpt.isPresent()){
                UniqueAccount acc = uOpt.get();
                TransactionResult result = acc.withdraw(instance.EconomyService.getDefaultCurrency(), BigDecimal.valueOf(script.RequiredValue), Cause.source(player).build());
                if(result.getResult() == ResultType.SUCCESS){
                    if(instance.PluginConfig.SendExecuteInfoMessage && !script.isCloseInfo){
                        player.sendMessage(Text.builder("Your balance has reduced by "+script.RequiredValue)
                                .style(TextStyles.BOLD)
                                .color(TextColors.YELLOW)
                                .build());
                    }
                }
                else if(result.getResult() == ResultType.FAILED || result.getResult() == ResultType.ACCOUNT_NO_FUNDS){
                    isfailed = true;
                    if(instance.PluginConfig.SendExecuteInfoMessage && !script.isCloseInfo){
                        player.sendMessage(Text.builder("Your balance is not enough.")
                                .color(TextColors.YELLOW)
                                .style(TextStyles.BOLD)
                                .build());
                    }
                } else {
                    isfailed = true;
                    if(instance.PluginConfig.SendExecuteInfoMessage && !script.isCloseInfo){
                        player.sendMessage(Text.builder("An error occurred when reducing economy value.")
                                .color(TextColors.RED)
                                .style(TextStyles.BOLD)
                                .build());
                    }
                }
            }
        }
        //@Item-consume
        if(!isfailed && !script.RequiredItemName.isEmpty()){
            if(script.RequiredItemType == CommandScript.HasallToken){
                for(Slot nowslot: slotIter){
                    Optional<ItemStack> stackOptional = nowslot.peek();
                    //noinspection OptionalIsPresent
                    if(stackOptional.isPresent()){
                        ItemStack stack = stackOptional.get();
                        int idx = script.RequiredItemName.indexOf(chu(stack.getItem().getName()));
                        if(idx == -1) continue;
                        if(!script.RequiredItemIsConsume.get(idx)) continue;
                        Optional<BlockState> state = stack.get(Keys.ITEM_BLOCKSTATE);
                        String bsid = "NULL";
                        if(state.isPresent()){
                            bsid = chu(state.get().getId());
                        }
                        if(script.RequiredItemBlockState.get(idx).equalsIgnoreCase("NULL")
                                || script.RequiredItemBlockState.get(idx).equalsIgnoreCase(bsid)){
                            if(itemcounts.get(idx) != 0){
                                int nowreduce = Math.min(itemcounts.get(idx),stack.getQuantity());
                                stack.setQuantity(stack.getQuantity() - nowreduce);
                                nowslot.poll();
                                nowslot.offer(stack);
                                itemcounts.set(idx,itemcounts.get(idx) - nowreduce);
                            }
                        }
                    }
                }
            }
            else if(script.RequiredItemType == CommandScript.HasoneToken){
                for(Slot nowslot: slotIter){
                    Optional<ItemStack> stackOptional = nowslot.peek();
                    if(stackOptional.isPresent()){
                        ItemStack stack = stackOptional.get();
                        int idx = script.RequiredItemName.indexOf(chu(stack.getItem().getName()));
                        if(idx != itemhasidx) continue;
                        if(!script.RequiredItemIsConsume.get(itemhasidx)) break;
                        Optional<BlockState> state = stack.get(Keys.ITEM_BLOCKSTATE);
                        String bsid = "NULL";
                        if(state.isPresent()){
                            bsid = chu(state.get().getId());
                        }
                        if(script.RequiredItemBlockState.get(itemhasidx).equalsIgnoreCase("NULL")
                                || script.RequiredItemBlockState.get(itemhasidx).equalsIgnoreCase(bsid)){
                            if(itemcounts.get(idx) != 0){
                                int nowreduce = Math.min(itemcounts.get(idx),stack.getQuantity());
                                stack.setQuantity(stack.getQuantity() - nowreduce);
                                nowslot.poll();
                                nowslot.offer(stack);
                                itemcounts.set(idx,itemcounts.get(idx) - nowreduce);
                                if(itemcounts.get(idx) == 0) break;
                            }
                        }
                    }
                }
            }
        }
        if(script.DelaySeconds != CommandScript.Undefined){
            CommandScript now = script.Copy();
            now.isfailed = isfailed;
            now.ScriptMode = CommandScript.PostExcutingModeToken;
            ScriptData data = player.get(ScriptData.class).orElse(null);
            now.TimerBegin = System.currentTimeMillis();
            now.TimerEnd = now.TimerBegin + script.DelaySeconds * 1000;
            data.getScripts().add(now);
        }
        else ExcuteScript(player,script,isfailed);
        if(!script.messagestr.equals(CommandScript.NullMessage)){
            Pair<String,Optional<Text>> res = CommandScript.ParseMessage(script.messagestr);
            script.messagestr = res.getKey();
        }
        if(!script.servermessagestr.equals(CommandScript.NullMessage)){
            Pair<String,Optional<Text>> res = CommandScript.ParseMessage(script.servermessagestr);
            script.servermessagestr = res.getKey();
        }
        if(isfailed){
            for(int i = 0;i < script.punishstr.size();++i){
                Pair<String,Optional<Text>> res = CommandScript.ParseMessage(script.punishstr.get(i));
                script.punishstr.set(i,res.getKey());
            }
        } else {
            for(int i = 0;i < script.comandstr.size();++i){
                Pair<String,Optional<Text>> res = CommandScript.ParseMessage(script.comandstr.get(i));
                script.comandstr.set(i,res.getKey());
            }
        }
        return isfailed;
    }
    public void ExcuteScript(Player player,CommandScript script,boolean isfailed){
        if(!script.messagestr.equals(CommandScript.NullMessage)){
            Pair<String,Optional<Text>> res = CommandScript.ParseMessage(script.messagestr);
            player.sendMessage(res.getValue().get());
        }
        if(!script.servermessagestr.equals(CommandScript.NullMessage)){
            Pair<String,Optional<Text>> res = CommandScript.ParseMessage(script.servermessagestr);
            MessageChannel.TO_ALL.send(res.getValue().get());
        }
        CommandSource nowsource = player;
        if(script.isBypass) {
            nowsource = Sponge.getServer().getConsole();
        }
        if(isfailed){
            for(int i = 0;i < script.punishstr.size();++i){
                Pair<String,Optional<Text>> res = CommandScript.ParseMessage(script.punishstr.get(i));
                String cmdnow = chu(res.getValue().get().toPlain());
                if(script.isBypass) cmdnow = cmdnow.replace("@a",player.getName());
                Sponge.getCommandManager().process(nowsource,cmdnow);
            }
        } else {
            for(int i = 0;i < script.comandstr.size();++i){
                Pair<String,Optional<Text>> res = CommandScript.ParseMessage(script.comandstr.get(i));
                String cmdnow = chu(res.getValue().get().toPlain());
                if(script.isBypass) cmdnow = cmdnow.replace("@a",player.getName());
                Sponge.getCommandManager().process(nowsource,cmdnow);
            }
        }
    }
}