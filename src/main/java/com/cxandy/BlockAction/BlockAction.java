package com.cxandy.BlockAction;

import com.cxandy.BlockAction.ConfigLoader.ConfigLoader;
import com.cxandy.BlockAction.ConfigLoader.PluginConfig;
import com.cxandy.BlockAction.DataHandler.DataHandler;
import com.cxandy.BlockAction.DataHandler.DataSeter;
import com.cxandy.BlockAction.EventLoaders.EventLoader;
import com.cxandy.BlockAction.Data.DataRegister;
import com.cxandy.BlockAction.Schedulers.SchedulerLoader;
import com.cxandy.BlockAction.ScriptExecutor.ExecuteHelper;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.game.state.*;
import com.cxandy.BlockAction.Commands.Commandloader;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.event.Listener;
import com.google.inject.Inject;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.service.permission.PermissionService;

import java.nio.file.Path;
import java.util.*;

@Plugin(
        id = BlockAction.PLUGINID,
        name = BlockAction.NAME,
        version = BlockAction.VERSION,
        description = "A Block Action-Script Plugin",
        authors = {"cxAndy"}
)
public class BlockAction {
    public static final String PLUGINID ="blockaction";
    public static final String NAME="Block Action";
    public static final String VERSION ="1.1";
    public SchedulerLoader SchedulerLoader;
    public DataSeter WorldDataSeter;
    public ConfigLoader BlockScriptsConfigLoader;
    public EconomyService EconomyService;
    public boolean HasEconomyService = false;
    public ExecuteHelper ScriptExectuor;
    public PluginConfig PluginConfig;
    public PermissionService PermissionService;
    public Map<UUID,DataHandler> WorldBlockDataHandlers;
    @Inject
    private Logger logger;

    @Inject
    @ConfigDir(sharedRoot = false)
    private Path privateConfigDir;

    @Inject
    private PluginContainer instance;

    public PluginContainer getInstance(){
        return this.instance;
    }
    public Logger getLogger() {
        return logger;
    }
    @Listener
    public void onGamePreInitialization(GamePreInitializationEvent event) {
        getLogger().info("Initialing BlockAction.");
        getLogger().info("Loading Script Datas.");
        PluginConfig = new PluginConfig();
        WorldBlockDataHandlers = new HashMap<>();
        BlockScriptsConfigLoader = new ConfigLoader(privateConfigDir,this);
        WorldDataSeter = new DataSeter(this);
    }
    @Listener
    public void onGameInitialization(GameInitializationEvent event) {
        getLogger().info("Registering Data Classes.");
        new DataRegister(getInstance());
        new EventLoader(this);
        this.ScriptExectuor = new ExecuteHelper(this);
    }
    @Listener
    public void onGameAboutToStart(GameAboutToStartServerEvent event){
        getLogger().info("Checking For Economy Plugin.");
        Optional<EconomyService> ecoserviceOptional = Sponge.getServiceManager().provide(org.spongepowered.api.service.economy.EconomyService.class);
        if(!ecoserviceOptional.isPresent()){
            getLogger().warn("Economy plugin not found. @Value will not be avalible!");
        }
        else{
            getLogger().info("Economy plugin found.");
            this.HasEconomyService = true;
            this.EconomyService = ecoserviceOptional.get();
        }
        this.PermissionService = Sponge.getServiceManager().provide(org.spongepowered.api.service.permission.PermissionService.class).get();
    }
    @Listener
    public void onGameStartingServer(GameStartingServerEvent event) {
        getLogger().info("Registering BlockAction Commands.");
        new Commandloader(this);
    }
    @Listener
    public void onGameStarted(GameStartedServerEvent event){
        getLogger().info("Registering Schedulers.");
        SchedulerLoader = new SchedulerLoader(this);
    }
    @Listener
    public void onGameStoppingServer(GameStoppingServerEvent event){
        getLogger().info("Saving BlockAction Data.");
        BlockScriptsConfigLoader.SaveDataAndConfig();
    }
}
