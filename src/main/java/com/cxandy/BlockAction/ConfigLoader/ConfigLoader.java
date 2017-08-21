package com.cxandy.BlockAction.ConfigLoader;

import com.cxandy.BlockAction.BlockAction;
import com.cxandy.BlockAction.DataHandler.DataHandler;
import com.cxandy.BlockAction.Data.CommandScript.ScriptData;
import com.cxandy.BlockAction.Data.CommandScript.TypeSerializers.ScriptDataTypeSerializer;
import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ConfigLoader {
    private Path ConfigDir;
    private Path ConfigWorldData;
    private Path CommonConfig;
    private ConfigurationLoader<CommentedConfigurationNode> WorldDataLoader;
    private ConfigurationLoader<CommentedConfigurationNode> ConfigDataLoader;
    private ConfigurationNode WorldDataRootNode;
    private ConfigurationNode ConfigDataRootNode;
    BlockAction instance;
    public void LoadConfig(){
        try{
            ConfigDataRootNode = ConfigDataLoader.load();
        } catch (IOException e){
            ConfigDataRootNode = ConfigDataLoader.createEmptyNode();
        }
        instance.PluginConfig.AutoSaveEnable = ConfigDataRootNode.getNode("EnableAutoSave").getBoolean(true);
        instance.PluginConfig.AutoSaveInterval = ConfigDataRootNode.getNode("AutoSaveIntervalSec").getInt(60);
        instance.PluginConfig.ExecuteRequriesPerms = ConfigDataRootNode.getNode("EnableExecutePermCheck").getBoolean(false);
        instance.PluginConfig.SendExecuteInfoMessage = ConfigDataRootNode.getNode("EnableExecuteInfo").getBoolean(true);
        if(instance.SchedulerLoader != null){
            instance.SchedulerLoader.ConfigAutoSaver.cancel();
            instance.SchedulerLoader.ConfigAutoSaverBuilder.interval(instance.PluginConfig.AutoSaveInterval, TimeUnit.SECONDS);
            instance.SchedulerLoader.ConfigAutoSaver = instance.SchedulerLoader.ConfigAutoSaverBuilder.submit(instance);
        }
    }
    public void LoadData(){
        try{
            WorldDataRootNode = WorldDataLoader.load();
        } catch (IOException e){
            WorldDataRootNode = WorldDataLoader.createEmptyNode();
        }
        int SavedWolrds = WorldDataRootNode.getNode("WorldConut").getInt(0);
        for(int i = 0;i < SavedWolrds;++i){
            try {
                UUID nowuuid = WorldDataRootNode.getNode("WorldUUID_"+i).getValue(TypeToken.of(UUID.class));
                if(nowuuid == null){
                    instance.getLogger().warn("World " + i + " UUID Data Invaild");
                    continue;
                }
                ConfigurationNode curnode = WorldDataRootNode.getNode("WorldBlockData_"+i);
                if(curnode == null){
                    instance.getLogger().warn("World " + i + "Block Data Invaild");
                    continue;
                }
                int SavedNotes = curnode.getNode("NodeCounts").getInt(0);
                if(SavedNotes == 0) continue;
                DataHandler curhandler = new DataHandler(instance);
                instance.WorldBlockDataHandlers.put(nowuuid,curhandler);
                List<ScriptData> curdatas = new ArrayList<>();
                for(int j = 0;j < SavedNotes;++j){
                    ScriptData now = curnode.getNode("Node"+j).getValue(TypeToken.of(ScriptData.class));
                    if(now != null) curdatas.add(now);
                    else instance.getLogger().warn("World " + i + ": Node " + j + "Data Invaild");
                }
                curhandler.ReadConfig(curdatas);
            } catch (ObjectMappingException e) {
                instance.getLogger().warn("UUID Data Read Failed.");
            }
        }
    }
    public void LoadDataAndConfig(){
        LoadData();
        LoadConfig();
    }
    public ConfigLoader(Path ConfigDir,BlockAction instance){
        this.ConfigDir = ConfigDir;
        File dir = ConfigDir.toFile();
        if(!dir.exists()){
            if(dir.mkdirs()){
                instance.getLogger().info("Successfully created config dir.");
            }
            else{
                instance.getLogger().error("Cannot create config dir! Please do it mannually!");
            }
        }
        this.ConfigWorldData = ConfigDir.resolve("WorldData.conf");
        this.CommonConfig = ConfigDir.resolve("Config.conf");
        this.instance = instance;
        TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(ScriptData.class),new ScriptDataTypeSerializer());
        WorldDataLoader = HoconConfigurationLoader.builder().setPath(ConfigWorldData).build();
        ConfigDataLoader = HoconConfigurationLoader.builder().setPath(CommonConfig).build();
        LoadDataAndConfig();
    }
    public void SaveDataAndConfig(){
        int WorldCount = instance.WorldBlockDataHandlers.size();
        WorldDataRootNode = WorldDataLoader.createEmptyNode();
        WorldDataRootNode.getNode("WorldConut").setValue(WorldCount);
        int Worldidx = 0;
        for(Map.Entry<UUID,DataHandler> entry: instance.WorldBlockDataHandlers.entrySet()){
            try {
                WorldDataRootNode.getNode("WorldUUID_"+Worldidx).setValue(TypeToken.of(UUID.class),entry.getKey());
                List<ScriptData> curdatas = entry.getValue().BuildConfig();
                WorldDataRootNode.getNode("WorldBlockData_"+Worldidx,"NodeCounts").setValue(curdatas.size());
                for(int i = 0;i < curdatas.size();++i){
                    WorldDataRootNode.getNode("WorldBlockData_"+Worldidx,"Node"+i).setValue(TypeToken.of(ScriptData.class),curdatas.get(i));
                }
                Worldidx++;
            } catch (ObjectMappingException e) {
                instance.getLogger().error("World " + Worldidx+" UUID Data sava failed!");
            }
        }
        try {
            WorldDataLoader.save(WorldDataRootNode);
        } catch (IOException e){
            instance.getLogger().error("WorldData save faild, please check the permission of the config dir!");
            instance.getLogger().error(e.getLocalizedMessage());
        }
        //Config
        ConfigDataRootNode = ConfigDataLoader.createEmptyNode();
        ConfigDataRootNode.getNode("EnableAutoSave").setValue(instance.PluginConfig.AutoSaveEnable);
        ConfigDataRootNode.getNode("EnableExecutePermCheck").setValue(instance.PluginConfig.ExecuteRequriesPerms);
        ConfigDataRootNode.getNode("EnableExecuteInfo").setValue(instance.PluginConfig.SendExecuteInfoMessage);
        ConfigDataRootNode.getNode("AutoSaveIntervalSec").setValue(instance.PluginConfig.AutoSaveInterval);
        try {
            ConfigDataLoader.save(ConfigDataRootNode);
        } catch (IOException e){
            instance.getLogger().error("ConfigData save faild, please check the permission of the config dir!");
            instance.getLogger().error(e.getLocalizedMessage());
        }
    }
}
