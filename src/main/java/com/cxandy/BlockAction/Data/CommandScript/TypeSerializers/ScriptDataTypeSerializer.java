package com.cxandy.BlockAction.Data.CommandScript.TypeSerializers;

import com.cxandy.BlockAction.Data.CommandScript.CommandScript;
import com.cxandy.BlockAction.Data.CommandScript.ScriptData;
import com.flowpowered.math.vector.Vector3i;
import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ScriptDataTypeSerializer implements TypeSerializer<ScriptData> {
    @Override
    public ScriptData deserialize(TypeToken<?> type, ConfigurationNode value) throws ObjectMappingException {
        List<String> scriptstrs = value.getNode("scripts").getList(TypeToken.of(String.class));
        if(scriptstrs == null) return null;
        List<CommandScript> scriptList = new ArrayList<>();
        for(int i = 0;i < scriptstrs.size();++i){
            Optional<CommandScript> commandScriptOptional = CommandScript.ConvertStringFromSender(scriptstrs.get(i),CommandScript.BlockDataModeToken).getValue();
            //noinspection OptionalIsPresent
            if(commandScriptOptional.isPresent()) {
                scriptList.add(commandScriptOptional.get());
            }
        }
        int vecx = value.getNode("scriptpos","x").getInt(0);
        int vecy = value.getNode("scriptpos","y").getInt(0);
        int vecz = value.getNode("scriptpos","z").getInt(0);
        return new ScriptData(scriptList,new Vector3i(vecx,vecy,vecz));
    }

    @Override
    public void serialize(TypeToken<?> type,ScriptData data,ConfigurationNode value) throws ObjectMappingException{
        List<String> scriptstrs = new ArrayList<>();
        for(int i = 0;i < data.getScripts().size();++i){
            scriptstrs.add(data.getScripts().get(i).toDescription());
        }
        value.getNode("scripts").setValue(new TypeToken<List<String>>(){}, scriptstrs);
        value.getNode("scriptpos","x").setValue(data.getPos().getX());
        value.getNode("scriptpos","y").setValue(data.getPos().getY());
        value.getNode("scriptpos","z").setValue(data.getPos().getZ());
    }
}