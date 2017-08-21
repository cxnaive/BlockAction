package com.cxandy.BlockAction.Data;
import com.cxandy.BlockAction.Data.CommandScript.CommandScript;
import com.flowpowered.math.vector.Vector3i;
import com.google.common.reflect.TypeToken;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.KeyFactory;
import org.spongepowered.api.data.value.mutable.ListValue;
import org.spongepowered.api.data.value.mutable.Value;

import java.util.List;

public class BlockScriptsKeys {
    public static final Key<Value<Vector3i>> SCRIPT_POS = KeyFactory.makeSingleKey(
            TypeToken.of(Vector3i.class),
            new TypeToken<Value<Vector3i>>() {},
            DataQuery.of("ScriptDataPos"),
            "blockaction:script_data_pos","Script Data Pos");
    public static final Key<ListValue<CommandScript>> SCRIPTS = KeyFactory.makeListKey(
            new TypeToken<List<CommandScript>>() {},
            new TypeToken<ListValue<CommandScript>>() {},
            DataQuery.of("ScriptDataScripts"),"blockaction:script_data_scripts","Script Data Scripts");
}
