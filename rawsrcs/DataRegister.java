package com.cxandy.BlockAction.Data;

import com.cxandy.BlockAction.Data.CommandScript.Builders.CommandScriptBuilder;
import com.cxandy.BlockAction.Data.CommandScript.Builders.ScriptDataBuilder;
import com.cxandy.BlockAction.Data.CommandScript.CommandScript;
import com.cxandy.BlockAction.Data.CommandScript.ImmutableScriptData;
import com.cxandy.BlockAction.Data.CommandScript.ScriptData;
import com.cxandy.BlockAction.Data.CommandScript.Translators.Vector3iTranslator;
import com.flowpowered.math.vector.Vector3i;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataManager;
import org.spongepowered.api.data.DataRegistration;
import org.spongepowered.api.data.persistence.DataTranslator;
import org.spongepowered.api.plugin.PluginContainer;

import java.util.Optional;

public class DataRegister {
    public DataRegister(PluginContainer container){
        DataManager dataManager = Sponge.getDataManager();
        //CommandScript
        dataManager.registerBuilder(CommandScript.class, new CommandScriptBuilder());
        //Vector3i translater
        Optional<DataTranslator<Vector3i>> cur = dataManager.getTranslator(Vector3i.class);

        if(!cur.isPresent()) dataManager.registerTranslator(Vector3i.class,new Vector3iTranslator());
        //Script Data
        DataRegistration.<ScriptData,ImmutableScriptData>builder()
                .dataClass(ScriptData.class)
                .immutableClass(ImmutableScriptData.class)
                .builder(new ScriptDataBuilder())
                .manipulatorId("script_data")
                .dataName("Script Data")
                .buildAndRegister(container);
    }
}
