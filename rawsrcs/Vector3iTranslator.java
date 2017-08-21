package com.cxandy.BlockAction.Data.CommandScript.Translators;

import com.flowpowered.math.vector.Vector3i;
import com.google.common.reflect.TypeToken;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.Queries;
import org.spongepowered.api.data.persistence.DataTranslator;
import org.spongepowered.api.data.persistence.InvalidDataException;

public class Vector3iTranslator implements DataTranslator<Vector3i> {
    public static final int CONTENT_VERSION = 1;
    private static final DataQuery VECTOR3I_X = DataQuery.of("VECTOR3I_X");
    private static final DataQuery VECTOR3I_Y = DataQuery.of("VECTOR3I_Y");
    private static final DataQuery VECTOR3I_Z = DataQuery.of("VECTOR3I_Z");
    @Override

    public TypeToken<Vector3i> getToken(){
        return TypeToken.of(Vector3i.class);
    }

    @Override
    public Vector3i translate(final DataView content) throws InvalidDataException{
        content.getInt(Queries.CONTENT_VERSION).ifPresent(version -> {
            if (version != CONTENT_VERSION) {
                throw new InvalidDataException("Version incompatible: " + version);
            }
        });

        if(!content.contains(VECTOR3I_X,VECTOR3I_Y,VECTOR3I_Z)){
            throw new InvalidDataException("Incomplete Vector3i Data.");
        }
        int pX = content.getInt(VECTOR3I_X).get();
        int pY = content.getInt(VECTOR3I_Y).get();
        int pZ = content.getInt(VECTOR3I_Z).get();
        return new Vector3i(pX,pY,pZ);
    }

    @Override
    public DataContainer translate(Vector3i vec){
        return DataContainer.createNew()
                .set(VECTOR3I_X,vec.getX())
                .set(VECTOR3I_Y,vec.getY())
                .set(VECTOR3I_Z,vec.getZ());
    }

    @Override
    public String getId(){
        return "blockscripts:vector3i_translator";
    }

    @Override
    public String getName(){
        return "Vector3i Translator";
    }
}
