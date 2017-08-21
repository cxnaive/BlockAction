package com.cxandy.BlockAction.Schedulers;

import com.cxandy.BlockAction.BlockAction;
import org.spongepowered.api.scheduler.Task;

import java.util.concurrent.TimeUnit;

public class SchedulerLoader {
    private BlockAction instance;
    public Task.Builder PlayerPosChangeBuilder;
    public Task.Builder PlayerScriptPostBuilder;
    public Task.Builder ConfigAutoSaverBuilder;
    public Task PlayerPosChange;
    public Task PlayerScriptPost;
    public Task ConfigAutoSaver;
    public SchedulerLoader(BlockAction instance) {
        this.instance = instance;
        PlayerPosChangeBuilder = Task.builder();
        PlayerPosChangeBuilder = PlayerPosChangeBuilder.execute(new PlayerPosChangeRunnable(instance))
                .intervalTicks(1)
                .name("Check Player Pos Datas.");
        PlayerPosChange = PlayerPosChangeBuilder.submit(instance);
        PlayerScriptPostBuilder = Task.builder();
        PlayerScriptPostBuilder = PlayerScriptPostBuilder.execute(new PlayerScriptPostRunnable(instance))
                .interval(100, TimeUnit.MILLISECONDS)
                .name("Check Player Posted Script.");
        PlayerScriptPost = PlayerScriptPostBuilder.submit(instance);
        ConfigAutoSaverBuilder = Task.builder();
        ConfigAutoSaverBuilder = ConfigAutoSaverBuilder.execute(new ConfigAutoSaverRunnabe(instance))
                .interval(instance.PluginConfig.AutoSaveInterval, TimeUnit.SECONDS)
                .async()
                .name("Config/BlockData Auto Save.");
        ConfigAutoSaver = ConfigAutoSaverBuilder.submit(instance);
    }
}
