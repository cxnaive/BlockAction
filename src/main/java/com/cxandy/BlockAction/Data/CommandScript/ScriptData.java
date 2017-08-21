package com.cxandy.BlockAction.Data.CommandScript;

import com.cxandy.BlockAction.Data.CommandScript.Builders.ScriptDataBuilder;
import com.cxandy.BlockAction.Data.BlockScriptsKeys;
import com.flowpowered.math.vector.Vector3i;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.ListValue;
import org.spongepowered.api.data.value.mutable.Value;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ScriptData extends AbstractData<ScriptData,ImmutableScriptData>{
    private List<CommandScript> scripts;
    private Vector3i pos;
    public ScriptData(){
        this(new ArrayList<>(),Vector3i.ZERO);
        this.scripts.add(CommandScript.DisableMode);
    }
    public ScriptData(List<CommandScript> scripts,Vector3i pos){
        this.scripts = scripts;
        this.pos = pos;
    }
    protected ListValue<CommandScript> scripts(){
        return Sponge.getRegistry().getValueFactory().createListValue(BlockScriptsKeys.SCRIPTS,this.scripts,new ArrayList<>());
    }
    protected Value<Vector3i> pos(){
        return Sponge.getRegistry().getValueFactory().createValue(BlockScriptsKeys.SCRIPT_POS,pos,Vector3i.ZERO);
    }
    public Vector3i getPos(){
        return this.pos;
    }
    public void setPos(@Nullable Vector3i pos){
        this.pos = pos;
    }
    public List<CommandScript> getScripts(){
        return this.scripts;
    }
    public void setScripts(List<CommandScript> scripts){
        this.scripts = scripts;
    }
    @Override
    protected void registerGettersAndSetters(){
        registerKeyValue(BlockScriptsKeys.SCRIPT_POS,this::pos);
        registerKeyValue(BlockScriptsKeys.SCRIPTS,this::scripts);

        registerFieldGetter(BlockScriptsKeys.SCRIPT_POS,this::getPos);
        registerFieldGetter(BlockScriptsKeys.SCRIPTS,this::getScripts);

        registerFieldSetter(BlockScriptsKeys.SCRIPT_POS,this::setPos);
        registerFieldSetter(BlockScriptsKeys.SCRIPTS,this::setScripts);
    }
    @Override
    public int getContentVersion(){
        return ScriptDataBuilder.CONTENT_VERSION;
    }
    @Override
    public Optional<ScriptData> fill(DataHolder dataHolder, MergeFunction overlap){
        ScriptData scriptData = overlap.merge(this,dataHolder.get(ScriptData.class).orElse(null));
        setPos(scriptData.getPos());
        setScripts(scriptData.getScripts());
        return Optional.of(this);
    }
    @Override
    public Optional<ScriptData> from(DataContainer container){
        if(!container.contains(BlockScriptsKeys.SCRIPT_POS, BlockScriptsKeys.SCRIPTS)){
            return Optional.empty();
        }
        setPos(container.getObject(BlockScriptsKeys.SCRIPT_POS.getQuery(),Vector3i.class).get());
        setScripts(container.getSerializableList(BlockScriptsKeys.SCRIPTS.getQuery(),CommandScript.class).get());
        return Optional.of(this);
    }
    @Override
    public ScriptData copy(){
        return new ScriptData(this.scripts,this.pos);
    }
    @Override
    public DataContainer toContainer(){
        DataContainer container = super.toContainer();
        container.set(BlockScriptsKeys.SCRIPT_POS,this.pos);
        container.set(BlockScriptsKeys.SCRIPTS,this.scripts);
        return container;
    }
    @Override
    public ImmutableScriptData asImmutable(){
        return new ImmutableScriptData(this.scripts,this.pos);
    }
}
