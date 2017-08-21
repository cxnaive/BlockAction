package com.cxandy.BlockAction.Schedulers;

import com.cxandy.BlockAction.BlockAction;
import com.cxandy.BlockAction.Data.CommandScript.CommandScript;
import com.cxandy.BlockAction.Data.CommandScript.ScriptData;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class PlayerScriptPostRunnable implements Runnable {
    private BlockAction instance;
    public PlayerScriptPostRunnable(BlockAction instance){
        this.instance = instance;
    }
    @Override
    public void run(){
        Collection<Player> players = Sponge.getServer().getOnlinePlayers();
        for(Player cur : players){
            ScriptData data = cur.get(ScriptData.class).get();
            List<CommandScript> scripts = data.getScripts();
            Iterator<CommandScript> it = scripts.iterator();
            while(it.hasNext()){
                CommandScript now = it.next();
                if(now.ScriptMode == CommandScript.PostExcutingModeToken && now.TimerEnd < System.currentTimeMillis()) {
                    instance.ScriptExectuor.ExcuteScript(cur, now, now.isfailed);
                    it.remove();
                }
            }
        }
    }
}
