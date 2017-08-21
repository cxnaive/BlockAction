package com.cxandy.BlockAction.DataHandler;

import com.cxandy.BlockAction.BlockAction;
import com.cxandy.BlockAction.Data.CommandScript.CommandScript;
import com.cxandy.BlockAction.Data.CommandScript.ScriptData;
import com.flowpowered.math.vector.Vector3i;

import java.util.*;

public class DataHandler {
    private BlockAction instance;
    private HashMap<Vector3i,List<CommandScript>> BlockDatas = new HashMap<>();
    public Set<Map.Entry<Vector3i,List<CommandScript>>> getEntryset(){
        return BlockDatas.entrySet();
    }
    public DataHandler(BlockAction instance){
        this.instance = instance;
    }
    public Optional<List<CommandScript>> getCommandScripts(Vector3i pos){
        return Optional.ofNullable(BlockDatas.get(pos));
    }
    public boolean isEmpty(){
        return BlockDatas.isEmpty();
    }
    public void setScriptData(ScriptData data){
        if(data.getScripts().isEmpty()) removeScriptData(data);
        if(BlockDatas.containsKey(data.getPos())){
         BlockDatas.replace(data.getPos(),data.getScripts());
        }
        else BlockDatas.put(data.getPos(),data.getScripts());
    }
    public int removeScriptData(ScriptData data){
        return removeScriptData(data.getPos());
    }
    public int removeScriptData(Vector3i pos){
        if(BlockDatas.containsKey(pos)) {
            BlockDatas.remove(pos);
            return 0;
        }
        return -1;
    }
    public Optional<List<CommandScript>> getPosData(Vector3i vector3i){
        return Optional.ofNullable(BlockDatas.get(vector3i));
    }
    public void ReadConfig(List<ScriptData> dataList){
        BlockDatas.clear();
        for(int i = 0;i < dataList.size();++i){
            setScriptData(dataList.get(i));
        }
    }
    public List<ScriptData> BuildConfig(){
        List<ScriptData> cur = new ArrayList<>();
        for(Map.Entry<Vector3i,List<CommandScript>> entry: BlockDatas.entrySet()){
            ScriptData now = new ScriptData(entry.getValue(),entry.getKey());
            cur.add(now);
        }
        return cur;
    }
}
