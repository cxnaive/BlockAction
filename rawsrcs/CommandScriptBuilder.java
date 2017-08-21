package com.cxandy.BlockAction.Data.CommandScript.Builders;

import com.cxandy.BlockAction.Data.CommandScript.CommandScript;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;

import java.util.ArrayList;
import java.util.Optional;

public class CommandScriptBuilder extends AbstractDataBuilder<CommandScript>{
    public static final int CONTENT_VERSION = 1;

    public CommandScriptBuilder(){
        super(CommandScript.class,CONTENT_VERSION);
    }

    @Override
    protected Optional<CommandScript> buildContent(DataView content) throws InvalidDataException {
        if(!content.contains(
                CommandScript.COMMAND_STR_QUERY,
                CommandScript.MESSAGE_STR_QUERY,
                CommandScript.PUNISH_STR_QUERY,
                CommandScript.SERVER_MESSAGE_STR_QUERY,
                CommandScript.COMMAND_CLDSEC_QUERY,
                CommandScript.COMMAND_DELSEC_QUERY,
                CommandScript.COMMAND_EXTAMTLIMIT_QUERY,
                CommandScript.COMMAND_FSTOP_QUERY,
                CommandScript.COMMAND_ISBYPASS_QUERY,
                CommandScript.COMMAND_ISCLICK_QUERY,
                CommandScript.COMMAND_ISWALK_QUERY,
                CommandScript.COMMAND_SSTOP_QUERY,
                CommandScript.SCRIPT_MODE_QUERY,
                CommandScript.COMMAND_REQGRUOP_QUERY,
                CommandScript.COMMAND_REQITEMAMT_QUERY,
                CommandScript.COMMAND_REQITEMNAME_QUERY,
                CommandScript.COMMAND_REQITEMBS_QUERY,
                CommandScript.COMMAND_REQITEMISC_QUERY,
                CommandScript.COMMAND_REQPLAYERNAME_QUERY,
                CommandScript.COMMAND_REQPREM_QUERY,
                CommandScript.COMMAND_REQVALUE_QUERY,
                CommandScript.COMMAND_TIMERBEGIN_QUERY,
                CommandScript.COMMAND_TIMEREND_QUERY,
                CommandScript.COMMAND_REQHANDITEMNAME_QUERY,
                CommandScript.COMMAND_REQHANDITEMBS_QUERY,
                CommandScript.COMMAND_REQHANDITEMAMT_QUERY,
                CommandScript.COMMAND_REQHANDITEMTYPE_QUERY,
                CommandScript.COMMAND_REQITEMTYPE_QUERY,
                CommandScript.COMMAND_REQPLAYERTYPE_QUERY,
                CommandScript.COMMAND_REQPERMTYPE_QUERY,
                CommandScript.COMMAND_REQGROUPTYPE_QUERY,
                CommandScript.COMMAND_CLSINFO_QUERY)){
            return Optional.empty();
        }
        CommandScript now = new CommandScript();
        now.comandstr = content.getStringList(CommandScript.COMMAND_STR_QUERY).orElse(new ArrayList<>());
        now.messagestr = content.getString(CommandScript.MESSAGE_STR_QUERY).orElse(CommandScript.NullMessage);
        now.servermessagestr = content.getString(CommandScript.SERVER_MESSAGE_STR_QUERY).orElse(CommandScript.NullMessage);
        now.punishstr = content.getStringList(CommandScript.PUNISH_STR_QUERY).orElse(new ArrayList<>());
        now.CooldownSeconds = content.getInt(CommandScript.COMMAND_CLDSEC_QUERY).orElse(CommandScript.Undefined);
        now.DelaySeconds = content.getInt(CommandScript.COMMAND_DELSEC_QUERY).orElse(CommandScript.Undefined);
        now.ExcuteAmountLimit = content.getInt(CommandScript.COMMAND_EXTAMTLIMIT_QUERY).orElse(CommandScript.Undefined);
        now.isFailStop = content.getBoolean(CommandScript.COMMAND_FSTOP_QUERY).orElse(false);
        now.isBypass = content.getBoolean(CommandScript.COMMAND_ISBYPASS_QUERY).orElse(false);
        now.isClick = content.getBoolean(CommandScript.COMMAND_ISCLICK_QUERY).orElse(false);
        now.isWalk = content.getBoolean(CommandScript.COMMAND_ISWALK_QUERY).orElse(false);
        now.isSuccessStop = content.getBoolean(CommandScript.COMMAND_SSTOP_QUERY).orElse(false);
        now.isCloseInfo = content.getBoolean(CommandScript.COMMAND_CLSINFO_QUERY).orElse(false);
        now.ScriptMode = content.getInt(CommandScript.SCRIPT_MODE_QUERY).orElse(CommandScript.DisabledModeToken);
        now.RequiredGroup = content.getStringList(CommandScript.COMMAND_REQGRUOP_QUERY).orElse(new ArrayList<>());
        now.RequiredItemAmount = content.getIntegerList(CommandScript.COMMAND_REQITEMAMT_QUERY).orElse(new ArrayList<>());
        now.RequiredItemName = content.getStringList(CommandScript.COMMAND_REQITEMNAME_QUERY).orElse(new ArrayList<>());
        now.RequiredItemBlockState = content.getStringList(CommandScript.COMMAND_REQITEMBS_QUERY).orElse(new ArrayList<>());
        now.RequiredItemIsConsume = content.getBooleanList(CommandScript.COMMAND_REQITEMISC_QUERY).orElse(new ArrayList<>());
        now.RequiredPlayerName = content.getStringList(CommandScript.COMMAND_REQPLAYERNAME_QUERY).orElse(new ArrayList<>());
        now.RequiredPerm = content.getStringList(CommandScript.COMMAND_REQPREM_QUERY).orElse(new ArrayList<>());
        now.RequiredValue = content.getInt(CommandScript.COMMAND_REQVALUE_QUERY).orElse(CommandScript.Undefined);
        now.RequiredHandItemName = content.getString(CommandScript.COMMAND_REQHANDITEMNAME_QUERY).orElse(CommandScript.NullItem);
        now.RequiredHandItemBlockState = content.getString(CommandScript.COMMAND_REQHANDITEMBS_QUERY).orElse(CommandScript.NullItem);
        now.RequiredHandItemAmount = content.getInt(CommandScript.COMMAND_REQHANDITEMAMT_QUERY).orElse(CommandScript.Undefined);
        now.RequiredHandItemType = content.getInt(CommandScript.COMMAND_REQHANDITEMTYPE_QUERY).orElse(CommandScript.Undefined);
        now.RequiredItemType = content.getInt(CommandScript.COMMAND_REQITEMTYPE_QUERY).orElse(CommandScript.Undefined);
        now.RequiredPlayerType = content.getInt(CommandScript.COMMAND_REQPLAYERTYPE_QUERY).orElse(CommandScript.Undefined);
        now.RequiredPermType = content.getInt(CommandScript.COMMAND_REQPERMTYPE_QUERY).orElse(CommandScript.Undefined);
        now.RequiredGroupType = content.getInt(CommandScript.COMMAND_REQGROUPTYPE_QUERY).orElse(CommandScript.Undefined);
        now.TimerBegin = content.getLong(CommandScript.COMMAND_TIMERBEGIN_QUERY).orElse((long)CommandScript.Undefined);
        now.TimerEnd = content.getLong(CommandScript.COMMAND_TIMEREND_QUERY).orElse((long) CommandScript.Undefined);
        if(now.isFailStop) now.isSuccessStop = false;
        return Optional.of(now);
    }
}
