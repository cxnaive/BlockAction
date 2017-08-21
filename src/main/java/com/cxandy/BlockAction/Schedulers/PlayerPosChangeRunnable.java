package com.cxandy.BlockAction.Schedulers;

import com.cxandy.BlockAction.BlockAction;
import com.cxandy.BlockAction.Data.CommandScript.CommandScript;
import com.cxandy.BlockAction.Data.CommandScript.ScriptData;
import com.flowpowered.math.vector.Vector3i;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class PlayerPosChangeRunnable implements Runnable {
    private BlockAction instance;
    public PlayerPosChangeRunnable(BlockAction instance){
        this.instance = instance;
    }
    @Override
    public void run(){
        Collection<Player> players = Sponge.getServer().getOnlinePlayers();
        for(Player cur : players){
            ScriptData data = cur.get(ScriptData.class).get();
            Vector3i nowpos = cur.getLocation().getBlockPosition();
            if(!nowpos.equals(data.getPos())){
                Vector3i blockpos = new Vector3i(nowpos.getX(),nowpos.getY() - 1,nowpos.getZ());
                Optional<List<CommandScript>> scriptsOptional = instance.WorldDataSeter.GetPosData(cur.getWorld(),blockpos);
                //noinspection OptionalIsPresent
                if(scriptsOptional.isPresent()){
                    List<CommandScript> scripts = scriptsOptional.get();
                    if(!scripts.isEmpty()){
                        Iterator<CommandScript> it = scripts.iterator();
                        while(it.hasNext()){
                            CommandScript script = it.next();
                            if(!script.isWalk) continue;
                            if(instance.PluginConfig.ExecuteRequriesPerms){
                                if(!cur.hasPermission("blockaction.excute.walk")){
                                    cur.sendMessage(Text.builder("You do not have the permission to excute the script!")
                                            .color(TextColors.RED)
                                            .style(TextStyles.BOLD)
                                            .build());
                                    break;
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
                                boolean isfaild = instance.ScriptExectuor.PostScriptExecute(cur,script);
                                if(script.ExcuteAmountLimit == 0){
                                    it.remove();
                                }
                                if(isfaild && script.isFailStop) break;
                            }
                        }
                    }
                    else instance.WorldDataSeter.RemoveData(cur.getWorld(),nowpos);
                }
                data.setPos(nowpos);
                cur.offer(data);
            }
        }
    }
}
