package com.cxandy.BlockAction.Data.CommandScript;

import com.cxandy.BlockAction.Data.CommandScript.Builders.ScriptDataBuilder;
import com.cxandy.BlockAction.Data.BlockScriptsKeys;
import com.flowpowered.math.vector.Vector3i;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.value.immutable.ImmutableListValue;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

import java.util.ArrayList;
import java.util.List;

public class ImmutableScriptData extends AbstractImmutableData<ImmutableScriptData,ScriptData> {
    private final List<CommandScript> scripts;
    private final Vector3i pos;
    public ImmutableScriptData(){
        this(new ArrayList<>(),Vector3i.ZERO);
        this.scripts.add(CommandScript.DisableMode);
    }
    public ImmutableScriptData(List<CommandScript> scripts,Vector3i pos){
        this.scripts = scripts;
        this.pos = pos;
    }
    protected ImmutableListValue<CommandScript> scripts(){
        return Sponge.getRegistry().getValueFactory().createListValue(BlockScriptsKeys.SCRIPTS,this.scripts,new ArrayList<>()).asImmutable();
    }
    protected ImmutableValue<Vector3i> pos(){
        return Sponge.getRegistry().getValueFactory().createValue(BlockScriptsKeys.SCRIPT_POS,pos,Vector3i.ZERO).asImmutable();
    }
    public Vector3i getPos(){
        return this.pos;
    }

    public List<CommandScript> getScripts(){
        return this.scripts;
    }

    @Override
    protected void registerGetters(){
        registerKeyValue(BlockScriptsKeys.SCRIPT_POS,this::pos);
        registerKeyValue(BlockScriptsKeys.SCRIPTS,this::scripts);

        registerFieldGetter(BlockScriptsKeys.SCRIPT_POS,this::getPos);
        registerFieldGetter(BlockScriptsKeys.SCRIPTS,this::getScripts);
    }
    @Override
    public int getContentVersion(){
        return ScriptDataBuilder.CONTENT_VERSION;
    }

    @Override
    public DataContainer toContainer(){
        DataContainer container = super.toContainer();
        container.set(BlockScriptsKeys.SCRIPT_POS,this.pos);
        container.set(BlockScriptsKeys.SCRIPTS,this.scripts);
        return container;
    }
    @Override
    public ScriptData asMutable(){
        return new ScriptData(this.scripts,this.pos);
    }
}
