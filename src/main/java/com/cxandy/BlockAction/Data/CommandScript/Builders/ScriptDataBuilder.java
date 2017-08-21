package com.cxandy.BlockAction.Data.CommandScript.Builders;

import com.cxandy.BlockAction.Data.CommandScript.CommandScript;
import com.cxandy.BlockAction.Data.CommandScript.ImmutableScriptData;
import com.cxandy.BlockAction.Data.CommandScript.ScriptData;
import com.cxandy.BlockAction.Data.BlockScriptsKeys;
import com.flowpowered.math.vector.Vector3i;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;

import java.util.List;
import java.util.Optional;

public class ScriptDataBuilder extends AbstractDataBuilder<ScriptData> implements DataManipulatorBuilder<ScriptData,ImmutableScriptData>{
    public static final int CONTENT_VERSION = 1;

    public ScriptDataBuilder(){
        super(ScriptData.class,CONTENT_VERSION);
    }

    @Override
    public ScriptData create(){
        return new ScriptData();
    }

    @Override
    public Optional<ScriptData> createFrom(DataHolder dataHolder){
        return create().fill(dataHolder);
    }

    @Override
    protected Optional<ScriptData> buildContent(DataView container){
        if(!container.contains(BlockScriptsKeys.SCRIPTS, BlockScriptsKeys.SCRIPT_POS)){
            return Optional.empty();
        }
        final List<CommandScript> scripts = container.getSerializableList(BlockScriptsKeys.SCRIPTS.getQuery(),CommandScript.class).get();
        final Vector3i pos = container.getObject(BlockScriptsKeys.SCRIPT_POS.getQuery(),Vector3i.class).get();
        return Optional.of(new ScriptData(scripts,pos));
    }
}