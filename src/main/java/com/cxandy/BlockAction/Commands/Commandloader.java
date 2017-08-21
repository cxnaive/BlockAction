package com.cxandy.BlockAction.Commands;

import com.cxandy.BlockAction.BlockAction;
import com.google.common.collect.ImmutableMap;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class Commandloader {
    public Commandloader(BlockAction instance){
        CommandSpec Reload = CommandSpec.builder()
                .description(Text.of("reload data and configs"))
                .permission("blockaction.commands.reload")
                .arguments(
                        GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.choices(Text.of("specific"),ImmutableMap.of("config","config","data","data")))),
                        GenericArguments.none()
                )
                .executor(new ReloadExecutor(instance))
                .build();
        CommandSpec CreateFromBook = CommandSpec.builder()
                .description(Text.of("create script from book"))
                .permission("blockaction.commands.createfrombook")
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.choices(Text.of("mode"), ImmutableMap.of("create","create","change","change"))),
                        GenericArguments.none()
                )
                .executor(new CreateFromBookExecutor(instance))
                .build();
        CommandSpec RangeRemoveScript = CommandSpec.builder()
                .description(Text.of("remove all scripts in a range"))
                .permission("blockaction.commands.rangeremove")
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.integer(Text.of("x"))),
                        GenericArguments.onlyOne(GenericArguments.integer(Text.of("y"))),
                        GenericArguments.onlyOne(GenericArguments.integer(Text.of("z"))),
                        GenericArguments.onlyOne(GenericArguments.integer(Text.of("range"))),
                        GenericArguments.onlyOne(GenericArguments.optional(GenericArguments.world(Text.of("world")))),
                        GenericArguments.none()
                )
                .executor(new ScriptRangeRemoverExecutor(instance))
                .build();
        CommandSpec FindScript = CommandSpec.builder()
                .description(Text.of("find all scripts's pos in a range."))
                .permission("blockaction.commands.find")
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.integer(Text.of("x"))),
                        GenericArguments.onlyOne(GenericArguments.integer(Text.of("y"))),
                        GenericArguments.onlyOne(GenericArguments.integer(Text.of("z"))),
                        GenericArguments.onlyOne(GenericArguments.integer(Text.of("range"))),
                        GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.world(Text.of("world")))),
                        GenericArguments.none()
                )
                .executor(new ScriptFinderExecutor(instance))
                .build();
        CommandSpec ClearScript = CommandSpec.builder()
                .description(Text.of("clear all scripts in this world."))
                .permission("blockaction.commands.clear")
                .arguments(
                        GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.world(Text.of("world")))),
                        GenericArguments.none()
                )
                .executor(new ScriptClearerExecutor(instance))
                .build();
        CommandSpec ClearScriptAll = CommandSpec.builder()
                .description(Text.of("clear all scripts in all worlds."))
                .permission("blockaction.commands.clearall")
                .executor(new ScriptClearallExecutor(instance))
                .build();
        CommandSpec CreateScript = CommandSpec.builder()
                .description(Text.of("create a script and set player's mode to AttachMode."))
                .permission("blockaction.commands.create")
                .arguments(
                        GenericArguments.remainingJoinedStrings(Text.of("content"))
                )
                .executor(new ScriptCreatorExecutor())
                .build();
        CommandSpec ChangeScript = CommandSpec.builder()
                .description(Text.of("override/add the last script's content on a block."))
                .permission("blockaction.commands.change")
                .arguments(
                        GenericArguments.remainingJoinedStrings(Text.of("content"))
                )
                .executor(new ScriptChangerExecutor())
                .build();
        CommandSpec SetUserMode = CommandSpec.builder()
                .description(Text.of("set player's mode of scriptseter."))
                .permission("blockaction.commands.setmode")
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.choices(Text.of("modename"),ImmutableMap.of("disabled","disabled","viewmode","viewmode","deletemode","deletemode","clearmode","clearmode"))),
                        GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))),
                        GenericArguments.none()
                )
                .executor(new ModeSetterExecutor())
                .build();
        CommandSpec GetUserMode = CommandSpec.builder()
                .description(Text.of("get player's mode of scriptseter."))
                .arguments(
                        GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))),
                        GenericArguments.none()
                )
                .permission("blockaction.commands.getmode")
                .executor(new ModeGetterExecutor())
                .build();
        CommandSpec GetItemNameInHand = CommandSpec.builder()
                .description(Text.of("get the Item's name in player's primary hand."))
                .permission("blockaction.commands.getitemname")
                .arguments(
                        GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("token")))),
                        GenericArguments.none()
                )
                .executor(new GetItemNameInHandExecutor(instance))
                .build();
        CommandSpec ItemStorageList = CommandSpec.builder()
                .description(Text.of("list a player's Item-Storage."))
                .permission("blockaction.commands.itemstorage.list")
                .executor(new ItemStorageListExecutor())
                .build();
        CommandSpec ItemStorageClear = CommandSpec.builder()
                .description(Text.of("clear a player's Item-Storage."))
                .permission("blockaction.commands.itemstorage.clear")
                .executor(new ItemStorageClearExecutor())
                .build();
        CommandSpec ItemStorageRemove = CommandSpec.builder()
                .description(Text.of("remove a player's Item-Storage."))
                .permission("blockaction.commands.itemstorage.remove")
                .arguments(
                        GenericArguments.allOf(GenericArguments.string(Text.of("token")))
                )
                .executor(new ItemStorageRemoveExecutor())
                .build();
        CommandSpec ItemStorageRoot = CommandSpec.builder()
                .description(Text.of("Item storage commands"))
                .permission("blockaction.commands.itemstorage")
                .executor(new ItemStorageRootExecutor())
                .child(ItemStorageList,"list")
                .child(ItemStorageClear,"clear")
                .child(ItemStorageRemove,"remove")
                .build();
        CommandSpec AutoSaveSpec = CommandSpec.builder()
                .description(Text.of("Change Auto Save States"))
                .permission("blockaction.commands.autosave")
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.choices(Text.of("mode"),ImmutableMap.of("state","state","stop","stop","start","start"))),
                        GenericArguments.none()
                )
                .executor(new AutoSaveExecutor(instance))
                .build();
        CommandSpec RootCommand = CommandSpec.builder()
                .description(Text.of("BlockAction Commands."))
                .permission("blockaction.commands.info")
                .executor(new RootCommandExecutor())
                .child(GetUserMode,"getmode")
                .child(SetUserMode,"setmode")
                .child(CreateScript,"create")
                .child(ChangeScript,"change")
                .child(ClearScript,"clear")
                .child(FindScript,"find")
                .child(RangeRemoveScript,"remove")
                .child(ClearScriptAll,"clearall")
                .child(GetItemNameInHand,"getitemname")
                .child(CreateFromBook,"createfrombook")
                .child(Reload,"reload")
                .child(ItemStorageRoot,"itemstorage")
                .child(AutoSaveSpec,"autosave")
                .build();
        Sponge.getCommandManager().register(instance,RootCommand,"blockaction","ba");
    }
}
