package com.cxandy.BlockAction.EventLoaders;

import com.cxandy.BlockAction.BlockAction;
import com.cxandy.BlockAction.DataHandler.DataSeter;
import com.cxandy.BlockAction.Data.CommandScript.CommandScript;
import com.cxandy.BlockAction.Data.CommandScript.ScriptData;
import com.flowpowered.math.vector.Vector3i;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("ALL")
public class EventLoader {
    private BlockAction instance;
    public EventLoader(BlockAction instance){
        this.instance = instance;
        Sponge.getEventManager().registerListeners(instance,this);
    }
    @Listener
    public void onClientConnectionJoin(ClientConnectionEvent.Join event) {
        Player player = event.getTargetEntity();
        Optional<ScriptData> scriptDataOptional = player.get(ScriptData.class);
        if (!scriptDataOptional.isPresent()) player.offer(new ScriptData());
    }
    @SuppressWarnings("OptionalIsPresent")
    @Listener
    public void onBlockBreak(ChangeBlockEvent.Break event){
        for(Transaction<BlockSnapshot> cur : event.getTransactions()){
            World world = cur.getOriginal().getLocation().get().getExtent();
            Optional<List<CommandScript>> scriptsOptional = instance.WorldDataSeter.GetPosData(world,cur.getOriginal().getPosition());
            //noinspection OptionalIsPresent
            if(scriptsOptional.isPresent()){
                if(scriptsOptional.get().isEmpty()) {
                    instance.WorldDataSeter.RemoveData(world,cur.getOriginal().getPosition());
                }
                else{
                    cur.setValid(false);
                }
            }
        }
    }
    @SuppressWarnings("OptionalIsPresent")
    @Listener
    public void onPrimaryCLickBlock(InteractBlockEvent.Primary.MainHand event){
        Optional<Location<World>> LocationOptional = event.getTargetBlock().getLocation();
        //noinspection OptionalIsPresent
        if(LocationOptional.isPresent()){
            World world = LocationOptional.get().getExtent();
            List<Player> sc = event.getCause().allOf(Player.class);
            Vector3i blockpos = LocationOptional.get().getBlockPosition();
            Optional<List<CommandScript>> scriptsOptional = instance.WorldDataSeter.GetPosData(world,blockpos);
            if(scriptsOptional.isPresent() && !scriptsOptional.get().isEmpty()){
                for(Player cur: sc){
                    Iterator<CommandScript> it = scriptsOptional.get().iterator();
                    while(it.hasNext()){
                        CommandScript script = it.next();
                        if(!script.isClick) continue;
                        if(instance.PluginConfig.ExecuteRequriesPerms){
                            if(!cur.hasPermission("blockaction.excute.click")){
                                cur.sendMessage(Text.builder("You do not have the permission to excute the script!")
                                        .color(TextColors.RED)
                                        .style(TextStyles.BOLD)
                                        .build());
                            }
                        }
                        Long nowtime = System.currentTimeMillis();
                        if(script.CooldownSeconds != CommandScript.Undefined){
                            if(nowtime > script.TimerEnd){
                                script.TimerBegin = nowtime;
                                script.TimerEnd = nowtime + script.CooldownSeconds * 1000;
                                if(script.ExcuteAmountLimit != CommandScript.Undefined) --script.ExcuteAmountLimit;
                                boolean isfailed = instance.ScriptExectuor.PostScriptExecute(cur,script);
                                if(script.ExcuteAmountLimit == 0){
                                    it.remove();
                                }
                                if(isfailed && script.isFailStop) break;
                                if(!isfailed && script.isSuccessStop) break;
                            }
                        }
                        else{
                            if(script.ExcuteAmountLimit != CommandScript.Undefined) --script.ExcuteAmountLimit;
                            boolean isfailed = instance.ScriptExectuor.PostScriptExecute(cur,script);
                            if(script.ExcuteAmountLimit == 0){
                                it.remove();
                            }
                            if(isfailed && script.isFailStop) break;
                        }
                    }
                }
            }
        }
    }
    @SuppressWarnings("OptionalIsPresent")
    @Listener
    public void onSecondaryClickBlock(InteractBlockEvent.Secondary.MainHand event){
        Optional<Location<World>> LocationOptional = event.getTargetBlock().getLocation();
        //noinspection OptionalIsPresent
        if(LocationOptional.isPresent()){
            List<Player> sc = event.getCause().allOf(Player.class);
            Vector3i blockpos = LocationOptional.get().getBlockPosition();
            for(Player cur: sc){
                ScriptData data = cur.get(ScriptData.class).get();
                List<CommandScript> scripts = data.getScripts();
                for(int i = 0;i < scripts.size();++i){
                    int curmode = scripts.get(i).ScriptMode;
                    if(curmode != CommandScript.PostExcutingModeToken
                            && curmode != CommandScript.ItemDataStorageToken){
                        DataSeter seter = instance.WorldDataSeter;
                        if(curmode == CommandScript.ViewModeToken){
                            Optional<List<CommandScript>> blockscriptsOptional = seter.GetPosData(cur.getWorld(),blockpos);
                            if(blockscriptsOptional.isPresent()){
                                List<CommandScript> blockscripts = blockscriptsOptional.get();
                                if(blockscripts.isEmpty()){
                                    cur.sendMessage(Text.builder("No scripts on this block.").color(TextColors.YELLOW).build());
                                    seter.RemoveData(cur.getWorld(),blockpos);
                                }
                                else{
                                    cur.sendMessage(Text.builder(blockscripts.size()+" ")
                                                    .color(TextColors.GREEN)
                                                    .style(TextStyles.BOLD)
                                                    .append(Text.builder("scripts in total")
                                                            .color(TextColors.YELLOW)
                                                            .style(TextStyles.NONE)
                                                            .build())
                                                    .build());
                                    for(CommandScript now: blockscripts){
                                        cur.sendMessage(Text.builder(now.toDescription()).color(TextColors.BLUE).build());
                                    }
                                }
                            }
                            else cur.sendMessage(Text.builder("No scripts on this block.").color(TextColors.YELLOW).build());
                        }
                        else if(curmode == CommandScript.DeleteModeToken){
                            Optional<List<CommandScript>> blockscriptsOptional = seter.GetPosData(cur.getWorld(),blockpos);
                            if(blockscriptsOptional.isPresent()){
                                List<CommandScript> blockscripts = blockscriptsOptional.get();
                                if(blockscripts.isEmpty()){
                                    cur.sendMessage(Text.builder("No scripts on this block.").color(TextColors.YELLOW).build());
                                    seter.RemoveData(cur.getWorld(),blockpos);
                                }
                                else{
                                    blockscripts.remove(blockscripts.get(blockscripts.size() - 1));
                                    cur.sendMessage(Text.builder("Successfully removed the last script on this block.\n")
                                            .color(TextColors.YELLOW)
                                            .append(Text.builder(blockscripts.size()+" ").color(TextColors.GREEN).style(TextStyles.BOLD).build())
                                            .append(Text.builder("scripts remained on this block.").build())
                                            .build());
                                    if(blockscripts.isEmpty()){
                                        seter.RemoveData(cur.getWorld(),blockpos);
                                    }
                                }
                            }
                            else cur.sendMessage(Text.builder("No scripts on this block.").color(TextColors.YELLOW).build());
                        }
                        else if(curmode == CommandScript.ClearModeToken){
                            Optional<List<CommandScript>> blockscriptsOptional = seter.GetPosData(cur.getWorld(),blockpos);
                            if(blockscriptsOptional.isPresent()){
                                List<CommandScript> blockscripts = blockscriptsOptional.get();
                                if(blockscripts.isEmpty()){
                                    cur.sendMessage(Text.builder("No scripts on this block.").color(TextColors.YELLOW).build());
                                    seter.RemoveData(cur.getWorld(),blockpos);
                                }
                                else{
                                    cur.sendMessage(Text.builder("Successfully removes all scripts on this block").color(TextColors.YELLOW).build());
                                    seter.RemoveData(cur.getWorld(),blockpos);
                                }
                            }
                            else cur.sendMessage(Text.builder("No scripts on this block.").color(TextColors.YELLOW).build());
                        }
                        else if(curmode == CommandScript.AttachModeToken){
                            seter.AddPosScript(cur.getWorld(),blockpos,scripts.get(i));
                            List<CommandScript> blockscripts = seter.GetPosData(cur.getWorld(),blockpos).get();
                            cur.sendMessage(Text.builder("Successfully attached a script to this block.\n")
                                    .color(TextColors.YELLOW)
                                    .append(Text.builder(blockscripts.size()+" ").color(TextColors.GREEN).style(TextStyles.BOLD).build())
                                    .append(Text.builder("scripts in total on this block.").build())
                                    .build());
                        }
                        else if(curmode == CommandScript.ChangeModeToken){
                            Optional<List<CommandScript>> blockscriptsOptional = seter.GetPosData(cur.getWorld(),blockpos);
                            if(blockscriptsOptional.isPresent()){
                                List<CommandScript> blockscripts = blockscriptsOptional.get();
                                if(blockscripts.isEmpty()){
                                    cur.sendMessage(Text.builder("No scripts on this block.").color(TextColors.YELLOW).build());
                                    seter.RemoveData(cur.getWorld(),blockpos);
                                }
                                else{
                                    blockscripts.get(blockscripts.size() - 1).MergeFromChangingMode(scripts.get(i));
                                    cur.sendMessage(Text.builder("Successfully changed the last script on this block.\n")
                                            .color(TextColors.YELLOW)
                                            .append(Text.builder("Now Script Content:\n")
                                                    .color(TextColors.GREEN)
                                                    .build())
                                            .append(Text.builder(blockscripts.get(blockscripts.size() - 1).toDescription())
                                                    .color(TextColors.BLUE)
                                                    .build())
                                            .build());
                                }
                            }
                            else cur.sendMessage(Text.builder("No scripts on this block.").color(TextColors.YELLOW).build());
                        }
                        break;
                    }
                }
            }
        }
    }
}