package com.cxandy.BlockAction.DataHandler;

import com.cxandy.BlockAction.BlockAction;
import com.cxandy.BlockAction.Data.CommandScript.CommandScript;
import com.cxandy.BlockAction.Data.CommandScript.ScriptData;
import com.flowpowered.math.vector.Vector3i;
import org.spongepowered.api.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class DataSeter {
    private BlockAction instance;
    public DataSeter(BlockAction instance){
        this.instance = instance;
    }
    public Optional<DataHandler> GetDataHandler(World world){
        DataHandler now = instance.WorldBlockDataHandlers.get(world.getUniqueId());
        return Optional.ofNullable(now);
    }
    public Optional<DataHandler> GetDataHandler(UUID worldUUID){
        DataHandler now = instance.WorldBlockDataHandlers.get(worldUUID);
        return Optional.ofNullable(now);
    }
    public void SetDataHandler(World world,DataHandler handler){
        if(handler.isEmpty()) ClearData(world);
        instance.WorldBlockDataHandlers.put(world.getUniqueId(),handler);
    }
    public Optional<List<CommandScript>> GetPosData(World world, Vector3i pos){
        DataHandler now = instance.WorldBlockDataHandlers.get(world.getUniqueId());
        if(now == null) return Optional.empty();
        return now.getPosData(pos);
    }
    public void AddPosScript(World world,Vector3i pos,CommandScript script){
        DataHandler now = instance.WorldBlockDataHandlers.get(world.getUniqueId());
        if(now == null) {
            now = new DataHandler(instance);
            instance.WorldBlockDataHandlers.put(world.getUniqueId(),now);
        }
        Optional<List<CommandScript>> scriptListOptional = now.getPosData(pos);
        CommandScript blockscript = script.Copy();
        blockscript.ScriptMode = CommandScript.BlockDataModeToken;
        if(scriptListOptional.isPresent()){
            scriptListOptional.get().add(script);
        }
        else {
            List<CommandScript> nowlist = new ArrayList<>();
            nowlist.add(script);
            now.setScriptData(new ScriptData(nowlist,pos));
        }
    }
    public void SetPosData(World world, Vector3i pos, List<CommandScript> scripts){
        if(scripts == null) return;
        SetData(world,new ScriptData(scripts, pos));
    }
    public void SetData(World world, ScriptData scriptData){
        if(scriptData.getScripts().isEmpty()) RemoveData(world,scriptData);
        DataHandler now = instance.WorldBlockDataHandlers.get(world.getUniqueId());
        if(now == null) now = new DataHandler(instance);
        now.setScriptData(scriptData);
        if(!instance.WorldBlockDataHandlers.containsKey(world.getUniqueId())){
            instance.WorldBlockDataHandlers.put(world.getUniqueId(),now);
        }
    }
    public int RemoveData(World world, ScriptData scriptData){
        return RemoveData(world,scriptData.getPos());
    }
    public int RemoveData(World world, Vector3i vector3i){
        DataHandler now = instance.WorldBlockDataHandlers.get(world.getUniqueId());
        if(now == null) return -1;
        int result = now.removeScriptData(vector3i);
        if(now.isEmpty()) instance.WorldBlockDataHandlers.remove(world.getUniqueId());
        return result;
    }
    public void ClearData(World world){
        if(instance.WorldBlockDataHandlers.containsKey(world.getUniqueId())){
            instance.WorldBlockDataHandlers.remove(world.getUniqueId());
        }
    }
    public void ClearData(UUID worldUUID){
        if(instance.WorldBlockDataHandlers.containsKey(worldUUID)){
            instance.WorldBlockDataHandlers.remove(worldUUID);
        }
    }
    public void ClearAll(){
        instance.WorldBlockDataHandlers.clear();
    }
}
