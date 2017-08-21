package com.cxandy.BlockAction.Schedulers;

import com.cxandy.BlockAction.BlockAction;

public class ConfigAutoSaverRunnabe implements Runnable {
    private BlockAction instance;
    public ConfigAutoSaverRunnabe(BlockAction instance){
        this.instance = instance;
    }
    @Override
    public void run(){
        if(instance.PluginConfig.AutoSaveEnable){
            instance.BlockScriptsConfigLoader.SaveDataAndConfig();
        }
    }
}
