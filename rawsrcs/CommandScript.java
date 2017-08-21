package com.cxandy.BlockAction.Data.CommandScript;

import com.cxandy.BlockAction.Data.CommandScript.Builders.CommandScriptBuilder;
import com.cxandy.BlockAction.Data.Pair;
import org.spongepowered.api.data.*;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyle;
import org.spongepowered.api.text.format.TextStyles;
import java.lang.reflect.Field;
import java.util.*;


public class CommandScript implements DataSerializable {
    private static final String UnitSpliter = ";";
    private static final String CommandBeginToken = "@Command";
    private static final String MessageSendToken = "@Message";
    private static final String PunishCommandBeginToken = "@Punish";
    private static final String CommandBypassToken = "@Bypass";
    private static final String CommandisWalkToken = "@Walk";
    private static final String CommandisClickToken = "@Click";
    private static final String CommandisFailStopToken = "@FailStop";
    private static final String CommandRequriedPlayerToken = "@Player";
    private static final String CommandRequriedItemToken = "@Item";
    private static final String CommandExcuteAmountLimitToken = "@Amount";
    private static final String CommandCooldownSecondsToken = "@Cooldown";
    private static final String CommandRequriedValueToken = "@Value";
    private static final String CommandRequriedPermToken = "@Perm";
    private static final String CommandRequriedGroupToken = "@Group";
    private static final String CommandDelaySecondsToken = "@Delay";
    private static final String ServerMessageToken = "@Server";
    private static final String CommandHandItemToken = "@Hand";
    private static final String CommandisSuccessStopToken = "@SuccessStop";
    private static final String CommandCloseInfoToken = "@CloseInfo";

    private static final String StoredItemToken = "@ItemStore";
    private static final String FormatColorToken = "@Color";
    private static final String FormatStyleToken = "@Style";
    private static final String FormatAdderToken = "@Add";

    public static final String NullCommand = "@NullCommand";
    public static final String DeleteModeStr = "DeleteMode";
    public static final String DisabledModeStr = "Disabled";
    public static final String ViewModestr = "ViewMode";
    public static final String ClearModestr = "ClearMode";
    public static final String NullMessage = "@NullMessage";
    public static final String NullItem = "@NullItem";

    public static final int ExcludeToken = 0;
    public static final int HasallToken = 1;
    public static final int HasoneToken = 2;
    public static final String[] TypeTokenStrs = new String[]{"exclude", "hasall", "hasone"};

    public static final int DisabledModeToken = 0;
    public static final int ViewModeToken = 1;
    public static final int DeleteModeToken = 2;
    public static final int ChangeModeToken = 3;
    public static final int AttachModeToken = 4;
    public static final int PostExcutingModeToken = 5;
    public static final int BlockDataModeToken = 6;
    public static final int ClearModeToken = 7;
    public static final int ItemDataStorageToken = 8;

    public static final int Undefined = -1;

    public static final CommandScript ViewMode = ConvertStringFromSender(NullCommand, ViewModeToken).getValue().get();
    public static final CommandScript DeleteMode = ConvertStringFromSender(NullCommand, DeleteModeToken).getValue().get();
    public static final CommandScript DisableMode = ConvertStringFromSender(NullCommand, DisabledModeToken).getValue().get();
    public static final CommandScript ClearMode = ConvertStringFromSender(NullCommand, ClearModeToken).getValue().get();

    public static final DataQuery COMMAND_STR_QUERY = DataQuery.of("COMMAND_STR");
    public static final DataQuery MESSAGE_STR_QUERY = DataQuery.of("MESSAGE_STR");
    public static final DataQuery SERVER_MESSAGE_STR_QUERY = DataQuery.of("SERVER_MESSAGE_STR");
    public static final DataQuery PUNISH_STR_QUERY = DataQuery.of("PUNISH_STR");
    public static final DataQuery SCRIPT_MODE_QUERY = DataQuery.of("SCRIPT_MODE");
    public static final DataQuery COMMAND_REQHANDITEMTYPE_QUERY = DataQuery.of("SCRIPT_REQ_HANDITEM_TYPE");
    public static final DataQuery COMMAND_REQHANDITEMNAME_QUERY = DataQuery.of("SCRIPT_REQ_HANDITEM_NAME");
    public static final DataQuery COMMAND_REQHANDITEMAMT_QUERY = DataQuery.of("SCRIPT_REQ_HANDITEM_AMT");
    public static final DataQuery COMMAND_REQHANDITEMBS_QUERY = DataQuery.of("SCRIPT_REQ_HANDITEM_BS");
    public static final DataQuery COMMAND_ISBYPASS_QUERY = DataQuery.of("SCRIPT_ISBYPASS");
    public static final DataQuery COMMAND_ISWALK_QUERY = DataQuery.of("SCRIPT_ISWALK");
    public static final DataQuery COMMAND_ISCLICK_QUERY = DataQuery.of("SCRIPT_ISCLICK");
    public static final DataQuery COMMAND_FSTOP_QUERY = DataQuery.of("SCRIPT_FSTOP");
    public static final DataQuery COMMAND_EXTAMTLIMIT_QUERY = DataQuery.of("SCRIPT_EXT_AMT_LIMIT");
    public static final DataQuery COMMAND_CLDSEC_QUERY = DataQuery.of("SCRIPT_CLDSEC");
    public static final DataQuery COMMAND_DELSEC_QUERY = DataQuery.of("SCRIPT_DELSEC");
    public static final DataQuery COMMAND_REQITEMAMT_QUERY = DataQuery.of("SCRIPT_REQ_ITEM_AMT");
    public static final DataQuery COMMAND_REQITEMBS_QUERY = DataQuery.of("SCRIPT_REQ_ITEM_BS");
    public static final DataQuery COMMAND_REQITEMISC_QUERY = DataQuery.of("SCRIPT_REQ_ITEM_ISC");
    public static final DataQuery COMMAND_REQITEMNAME_QUERY = DataQuery.of("SCRIPT_REQ_ITEM_NAME");
    public static final DataQuery COMMAND_REQPLAYERNAME_QUERY = DataQuery.of("SCRIPT_REQ_PLAYER_NAME");
    public static final DataQuery COMMAND_REQPREM_QUERY = DataQuery.of("SCRIPT_REQ_PERM_NAME");
    public static final DataQuery COMMAND_REQGRUOP_QUERY = DataQuery.of("SCRIPT_REQ_GROUP_NAME");
    public static final DataQuery COMMAND_REQVALUE_QUERY = DataQuery.of("SCRIPT_VALUE");
    public static final DataQuery COMMAND_TIMERBEGIN_QUERY = DataQuery.of("SCRIPT_TIMERBEGIN");
    public static final DataQuery COMMAND_TIMEREND_QUERY = DataQuery.of("SCRIPT_TIMEREND");
    public static final DataQuery COMMAND_REQITEMTYPE_QUERY = DataQuery.of("SCRIPT_REQ_ITEM_TYPE");
    public static final DataQuery COMMAND_REQPLAYERTYPE_QUERY = DataQuery.of("SCRIPT_REQ_PLAYER_TYPE");
    public static final DataQuery COMMAND_REQPERMTYPE_QUERY = DataQuery.of("SCRIPT_REQ_PERM_TYPE");
    public static final DataQuery COMMAND_REQGROUPTYPE_QUERY = DataQuery.of("SCRIPT_REQ_GROUP_TYPE");
    public static final DataQuery COMMAND_SSTOP_QUERY = DataQuery.of("SCRIPT_SSTOP");
    public static final DataQuery COMMAND_CLSINFO_QUERY = DataQuery.of("SCRIPT_CLSINFO");

    public String messagestr = NullMessage;
    public String servermessagestr = NullMessage;
    public String RequiredHandItemName = NullItem;
    public List<String> comandstr = new ArrayList<>();
    public List<String> punishstr = new ArrayList<>();
    public List<String> RequiredItemName = new ArrayList<>();
    public List<Integer> RequiredItemAmount = new ArrayList<>();
    public List<String> RequiredItemBlockState = new ArrayList<>();
    public List<Boolean> RequiredItemIsConsume = new ArrayList<>();
    public List<String> RequiredPlayerName = new ArrayList<>();
    public List<String> RequiredPerm = new ArrayList<>();
    public List<String> RequiredGroup = new ArrayList<>();
    public int RequiredHandItemType = Undefined;
    public int RequiredItemType = Undefined;
    public int RequiredPlayerType = Undefined;
    public int RequiredPermType = Undefined;
    public int RequiredGroupType = Undefined;
    public int ScriptMode = 0;
    public boolean isBypass = false;
    public boolean isWalk = false;
    public boolean isClick = false;
    public boolean isFailStop = false;
    public boolean isSuccessStop = false;
    public boolean isfailed = false;
    public boolean isCloseInfo = false;
    public String RequiredHandItemBlockState = NullItem;
    public int RequiredHandItemAmount = Undefined;
    public int ExcuteAmountLimit = Undefined;
    public int CooldownSeconds = Undefined;
    public int DelaySeconds = Undefined;
    public int RequiredValue = Undefined;
    public long TimerBegin = Undefined;
    public long TimerEnd = Undefined;

    public CommandScript Copy() {
        CommandScript now = new CommandScript();
        now.messagestr = this.messagestr;
        now.servermessagestr = this.servermessagestr;
        now.RequiredHandItemName = this.RequiredHandItemName;
        now.comandstr.addAll(this.comandstr);
        now.punishstr.addAll(this.punishstr);
        now.RequiredPlayerName.addAll(this.RequiredPlayerName);
        now.RequiredPerm.addAll(this.RequiredPerm);
        now.RequiredGroup.addAll(this.RequiredGroup);
        for (int i = 0; i < this.RequiredItemName.size(); ++i) {
            now.RequiredItemName.add(this.RequiredItemName.get(i));
            now.RequiredItemAmount.add(this.RequiredItemAmount.get(i));
            now.RequiredItemBlockState.add(this.RequiredItemBlockState.get(i));
            now.RequiredItemIsConsume.add(this.RequiredItemIsConsume.get(i));
        }
        now.isfailed = this.isfailed;
        now.RequiredHandItemType = this.RequiredHandItemType;
        now.RequiredItemType = this.RequiredItemType;
        now.RequiredPlayerType = this.RequiredPlayerType;
        now.RequiredPermType = this.RequiredPermType;
        now.RequiredGroupType = this.RequiredGroupType;
        now.ScriptMode = this.ScriptMode;
        now.isBypass = this.isBypass;
        now.isWalk = this.isWalk;
        now.isClick = this.isClick;
        now.isFailStop = this.isFailStop;
        now.isSuccessStop = this.isSuccessStop;
        now.isCloseInfo = this.isCloseInfo;
        now.RequiredHandItemBlockState = this.RequiredHandItemBlockState;
        now.RequiredHandItemAmount = this.RequiredHandItemAmount;
        now.ExcuteAmountLimit = this.ExcuteAmountLimit;
        now.CooldownSeconds = this.CooldownSeconds;
        now.DelaySeconds = this.DelaySeconds;
        now.RequiredValue = this.RequiredValue;
        now.TimerBegin = this.TimerBegin;
        now.TimerEnd = this.TimerEnd;
        return now;
    }

    private static String chu(String str) {
        if (str.startsWith("minecraft:")) return str.substring(10).trim();
        if (str.charAt(0) == '/') return str.substring(1);
        return str;
    }

    private void addtoBuilder(StringBuilder builder, String Token, List<String> vas) {
        if (vas.isEmpty()) return;
        builder.append(Token).append(":");
        for (int i = 0; i < vas.size(); ++i) {
            builder.append(vas.get(i));
            if (i < vas.size() - 1) builder.append(UnitSpliter);
            else builder.append(" ");
        }
    }

    private void addtoBuilder(StringBuilder builder, boolean vas, String Token) {
        if (vas) {
            builder.append(Token).append(" ");
        }
    }

    private void addtoBuilder(StringBuilder builder, int vas, String Token) {
        if (vas != Undefined) {
            builder.append(Token)
                    .append(":")
                    .append(vas)
                    .append(" ");
        }
    }

    public String toDescription() {
        StringBuilder builder = new StringBuilder();
        if (ScriptMode == ClearModeToken) return ClearModestr;
        if (ScriptMode == DeleteModeToken) return DeleteModeStr;
        if (ScriptMode == DisabledModeToken) return DisabledModeStr;
        if (ScriptMode == ViewModeToken) return ViewModestr;
        addtoBuilder(builder, CommandBeginToken, comandstr);
        if (!messagestr.equals(NullMessage)) {
            builder.append(MessageSendToken)
                    .append(":")
                    .append(messagestr)
                    .append(" ");
        }
        if (!servermessagestr.equals(NullMessage)) {
            builder.append(ServerMessageToken)
                    .append(":")
                    .append(servermessagestr)
                    .append(" ");
        }
        if (!RequiredHandItemName.equals(NullItem)) {
            builder.append(CommandHandItemToken)
                    .append(":")
                    .append(TypeTokenStrs[RequiredHandItemType])
                    .append(":")
                    .append(RequiredHandItemName)
                    .append(":")
                    .append(RequiredHandItemBlockState)
                    .append(":")
                    .append(RequiredHandItemAmount)
                    .append(" ");
        }
        addtoBuilder(builder, PunishCommandBeginToken, punishstr);
        if (RequiredPlayerType != Undefined)
            addtoBuilder(builder, CommandRequriedPlayerToken + ":" + TypeTokenStrs[RequiredPlayerType], RequiredPlayerName);
        if (RequiredPermType != Undefined)
            addtoBuilder(builder, CommandRequriedPermToken + ":" + TypeTokenStrs[RequiredPermType], RequiredPerm);
        if (RequiredGroupType != Undefined)
            addtoBuilder(builder, CommandRequriedGroupToken + ":" + TypeTokenStrs[RequiredGroupType], RequiredGroup);
        if (!RequiredItemName.isEmpty()) {
            builder.append(CommandRequriedItemToken + ":" + TypeTokenStrs[RequiredItemType]);
            builder.append(":");
            for (int i = 0; i < RequiredItemName.size(); ++i) {
                builder.append(RequiredItemName.get(i))
                        .append(":")
                        .append(RequiredItemBlockState.get(i))
                        .append(":")
                        .append(RequiredItemAmount.get(i))
                        .append(":")
                        .append(RequiredItemIsConsume.get(i));
                if (i < RequiredItemName.size() - 1) builder.append(UnitSpliter);
                else builder.append(" ");
            }
        }
        addtoBuilder(builder, isWalk, CommandisWalkToken);
        addtoBuilder(builder, isClick, CommandisClickToken);
        addtoBuilder(builder, isBypass, CommandBypassToken);
        addtoBuilder(builder, isFailStop, CommandisFailStopToken);
        addtoBuilder(builder, isSuccessStop, CommandisSuccessStopToken);
        addtoBuilder(builder, isCloseInfo, CommandCloseInfoToken);
        addtoBuilder(builder, ExcuteAmountLimit, CommandExcuteAmountLimitToken);
        addtoBuilder(builder, CooldownSeconds, CommandCooldownSecondsToken);
        addtoBuilder(builder, DelaySeconds, CommandDelaySecondsToken);
        addtoBuilder(builder, RequiredValue, CommandRequriedValueToken);
        return builder.toString();
    }

    public void MergeFromChangingMode(CommandScript cur) {
        if (cur.ScriptMode != ChangeModeToken) return;
        for (int i = 0; i < cur.comandstr.size(); ++i) {
            this.comandstr.add(cur.comandstr.get(i));
        }
        for (int i = 0; i < cur.punishstr.size(); ++i) {
            this.punishstr.add(cur.punishstr.get(i));
        }
        if (!cur.RequiredHandItemName.equals(NullItem)) {
            this.RequiredHandItemType = cur.RequiredHandItemType;
            this.RequiredHandItemName = cur.RequiredHandItemName;
            this.RequiredHandItemBlockState = cur.RequiredHandItemBlockState;
            this.RequiredHandItemAmount = cur.RequiredHandItemAmount;
        }
        if (cur.RequiredItemType != Undefined && this.RequiredItemType != cur.RequiredItemType) {
            this.RequiredItemName.clear();
            this.RequiredItemAmount.clear();
            this.RequiredItemBlockState.clear();
            this.RequiredItemIsConsume.clear();
            this.RequiredItemType = cur.RequiredItemType;
        }
        for (int i = 0; i < cur.RequiredItemName.size(); ++i) {
            this.RequiredItemName.add(cur.RequiredItemName.get(i));
            this.RequiredItemBlockState.add(cur.RequiredItemBlockState.get(i));
            this.RequiredItemAmount.add(cur.RequiredItemAmount.get(i));
            this.RequiredItemIsConsume.add(cur.RequiredItemIsConsume.get(i));
        }
        if (cur.RequiredPermType != Undefined && this.RequiredPermType != cur.RequiredPermType) {
            this.RequiredPerm.clear();
            this.RequiredPermType = cur.RequiredPermType;
        }
        for (int i = 0; i < cur.RequiredPerm.size(); ++i) {
            this.RequiredPerm.add(cur.RequiredPerm.get(i));
        }
        if (cur.RequiredGroupType != Undefined && this.RequiredGroupType != cur.RequiredGroupType) {
            this.RequiredGroup.clear();
            this.RequiredGroupType = cur.RequiredGroupType;
        }
        for (int i = 0; i < cur.RequiredGroup.size(); ++i) {
            this.RequiredGroup.add(cur.RequiredGroup.get(i));
        }
        if (cur.RequiredPlayerType != Undefined && this.RequiredPlayerType != cur.RequiredPlayerType) {
            this.RequiredPlayerName.clear();
            this.RequiredPlayerType = cur.RequiredPlayerType;
        }
        for (int i = 0; i < cur.RequiredPlayerName.size(); ++i) {
            this.RequiredPlayerName.add(cur.RequiredPlayerName.get(i));
        }
        if (!cur.messagestr.equals(NullMessage)) this.messagestr = cur.messagestr;
        if (!cur.servermessagestr.equals(NullMessage)) this.servermessagestr = cur.servermessagestr;
        if (cur.isFailStop) {
            this.isFailStop = true;
            this.isSuccessStop = false;
        } else if (cur.isSuccessStop) {
            this.isSuccessStop = true;
            this.isFailStop = false;
        }
        if (cur.isBypass) this.isBypass = true;
        if (cur.isClick) this.isClick = true;
        if (cur.isWalk) this.isWalk = true;
        if (cur.isCloseInfo) this.isCloseInfo = true;
        if (cur.ExcuteAmountLimit != Undefined) this.ExcuteAmountLimit = cur.ExcuteAmountLimit;
        if (cur.CooldownSeconds != Undefined) this.CooldownSeconds = cur.CooldownSeconds;
        if (cur.DelaySeconds != Undefined) this.DelaySeconds = cur.DelaySeconds;
        if (cur.RequiredValue != Undefined) this.RequiredValue = cur.RequiredValue;
    }

    public CommandScript() {

    }

    @Override
    public int getContentVersion() {
        return CommandScriptBuilder.CONTENT_VERSION;
    }

    @Override
    public DataContainer toContainer() {
        return DataContainer.createNew()
                .set(COMMAND_STR_QUERY, this.comandstr)
                .set(MESSAGE_STR_QUERY, this.messagestr)
                .set(SERVER_MESSAGE_STR_QUERY, this.servermessagestr)
                .set(PUNISH_STR_QUERY, this.punishstr)
                .set(SCRIPT_MODE_QUERY, this.ScriptMode)
                .set(COMMAND_ISBYPASS_QUERY, this.isBypass)
                .set(COMMAND_ISWALK_QUERY, this.isWalk)
                .set(COMMAND_ISCLICK_QUERY, this.isClick)
                .set(COMMAND_FSTOP_QUERY, this.isFailStop)
                .set(COMMAND_SSTOP_QUERY, this.isSuccessStop)
                .set(COMMAND_CLSINFO_QUERY, this.isCloseInfo)
                .set(COMMAND_EXTAMTLIMIT_QUERY, this.ExcuteAmountLimit)
                .set(COMMAND_CLDSEC_QUERY, this.CooldownSeconds)
                .set(COMMAND_DELSEC_QUERY, this.DelaySeconds)
                .set(COMMAND_REQITEMAMT_QUERY, this.RequiredItemAmount)
                .set(COMMAND_REQITEMNAME_QUERY, this.RequiredItemName)
                .set(COMMAND_REQITEMBS_QUERY, this.RequiredItemBlockState)
                .set(COMMAND_REQITEMISC_QUERY, this.RequiredItemIsConsume)
                .set(COMMAND_REQPLAYERNAME_QUERY, this.RequiredPlayerName)
                .set(COMMAND_REQPREM_QUERY, this.RequiredPerm)
                .set(COMMAND_REQGRUOP_QUERY, this.RequiredGroup)
                .set(COMMAND_REQVALUE_QUERY, this.RequiredValue)
                .set(COMMAND_TIMERBEGIN_QUERY, this.TimerBegin)
                .set(COMMAND_TIMEREND_QUERY, this.TimerEnd)
                .set(COMMAND_REQHANDITEMNAME_QUERY, this.RequiredHandItemName)
                .set(COMMAND_REQHANDITEMBS_QUERY, this.RequiredHandItemBlockState)
                .set(COMMAND_REQHANDITEMAMT_QUERY, this.RequiredHandItemAmount)
                .set(COMMAND_REQHANDITEMTYPE_QUERY, this.RequiredHandItemType)
                .set(COMMAND_REQITEMTYPE_QUERY, this.RequiredItemType)
                .set(COMMAND_REQPLAYERTYPE_QUERY, this.RequiredPlayerType)
                .set(COMMAND_REQPERMTYPE_QUERY, this.RequiredPermType)
                .set(COMMAND_REQGROUPTYPE_QUERY, this.RequiredGroupType)
                .set(Queries.CONTENT_VERSION, getContentVersion());
    }

    private static int GetNextIndex(int BeginIndex, int[] idxs) {
        for (int i = 0; i < idxs.length; ++i) {
            if (idxs[i] > BeginIndex) return idxs[i];
        }
        return Undefined;
    }

    private static int GetFromEnd(String str, char token, int stidx) {
        for (int i = stidx; i > 0; --i) {
            if (str.charAt(i) == token) return i;
        }
        return -1;
    }

    public static Optional<TextColor> GetColor(String name) {
        for (Field f : TextColors.class.getDeclaredFields()) {
            boolean b = f.isAccessible();
            f.setAccessible(true);
            if (f.getName().equalsIgnoreCase(name)) {
                try {
                    TextColor c = (TextColor) f.get(null);
                    f.setAccessible(b);
                    return Optional.of(c);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    return Optional.empty();
                }
            }
        }
        return Optional.empty();
    }

    public static Optional<TextStyle> GetStyle(String name) {
        for (Field f : TextStyles.class.getDeclaredFields()) {
            boolean b = f.isAccessible();
            f.setAccessible(true);
            if (f.getName().equalsIgnoreCase(name)) {
                try {
                    TextStyle c = (TextStyle) f.get(null);
                    f.setAccessible(b);
                    return Optional.of(c);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    return Optional.empty();
                }
            }
        }
        return Optional.empty();
    }

    public static Pair<String, Optional<Text>> ParseSub(String str,Player player) {
        int ridx = str.indexOf(':');
        if(ridx == Undefined){
            return new Pair<>("Invaild pattern, correct is [content:@Color...(sub args)]",Optional.empty());
        }
        String content = str.substring(0, ridx);
        Text.Builder builder = Text.builder(content);
        String argContent = str.substring(ridx + 1);
        int idxs[] = new int[4];
        int coloridx = idxs[0] = str.indexOf(FormatColorToken);
        int styleidx = idxs[1] = str.indexOf(FormatStyleToken);
        int adderidx = idxs[2] = str.indexOf(FormatAdderToken);
        idxs[3] = str.length();
        int number = 0;
        int addvalue = 0;
        int limit = 0;
        boolean haslimit = false;
        String colorstr = "";
        String stylestr = "";
        Arrays.sort(idxs);
        if (coloridx != Undefined) {
            String info = "Invaild @Color pattern, correct pattern is @Color:color";
            coloridx += FormatColorToken.length() - 1;
            if (coloridx + 2 >= str.length() || (str.charAt(coloridx + 1) != ':'))
                return new Pair<>(info, Optional.empty());
            int nextidx = GetNextIndex(coloridx, idxs);
            colorstr = str.substring(coloridx + 2, nextidx).trim();
            Optional<TextColor> colorOpt = GetColor(colorstr);
            if (!colorOpt.isPresent()) {
                return new Pair<>("Invaild color type " + colorstr.toUpperCase(), Optional.empty());
            }
            builder.color(colorOpt.get());
        }
        if (styleidx != Undefined) {
            String info = "Invaild @Style pattern, correct pattern is @Style:style1:style2....";
            styleidx += FormatStyleToken.length() - 1;
            if (styleidx + 2 >= str.length() || (str.charAt(styleidx + 1) != ':')) {
                return new Pair<>(info, Optional.empty());
            }
            int nextidx = GetNextIndex(styleidx, idxs);
            stylestr = str.substring(styleidx + 2, nextidx).trim();
            String contents[] = stylestr.split(UnitSpliter);
            if (contents.length == 0) return new Pair<>(info, Optional.empty());
            TextStyle styles[] = new TextStyle[contents.length];
            for (int i = 0; i < contents.length; ++i) {
                Optional<TextStyle> styleOpt = GetStyle(contents[i].trim());
                if (!styleOpt.isPresent()) {
                    return new Pair<>("Invaild style type " + contents[i].toUpperCase(), Optional.empty());
                }
                styles[i] = styleOpt.get();
            }
            builder.style(styles);
        }
        if (adderidx != Undefined) {
            adderidx += FormatAdderToken.length() - 1;
            String info = "Invaild @Add pattern, correct is @Add:add-value:limit\n" +
                    "or\n" +
                    "@Add:add-value\n";
            if (adderidx + 2 >= str.length() || (str.charAt(adderidx + 1) != ':'))
                return new Pair<>(info, Optional.empty());
            int nextidx = GetNextIndex(adderidx, idxs);
            String addercontent[] = str.substring(adderidx + 2, nextidx).trim().split(":");
            if (addercontent.length > 2) return new Pair<>(info, Optional.empty());
            try{
                number = Integer.parseInt(content);
            } catch (Exception e){
                return new Pair<>("@Add requires that content must be an Integer.", Optional.empty());
            }
            if (addercontent.length == 1){
                try {
                    addvalue = Integer.parseInt(addercontent[0]);
                    number += addvalue;
                } catch (Exception e){
                    return new Pair<>(info, Optional.empty());
                }
            }
            else {
                try {
                    addvalue = Integer.parseInt(addercontent[0]);
                    limit = Integer.parseInt(addercontent[1]);
                    if (addvalue < 0) {
                        if (number < limit) number = limit;
                        if (number + addvalue >= limit) number += addvalue;
                    } else {
                        if (number > limit) number = limit;
                        if (number + addvalue <= limit) number += addvalue;
                    }
                    haslimit = true;
                } catch (Exception e) {
                    return new Pair<>(info, Optional.empty());
                }
            }

        }
        StringBuilder replacebuilder = new StringBuilder();
        if (adderidx != Undefined) {
            if(haslimit){
                replacebuilder.append("[" + number + ": ")
                        .append(FormatAdderToken)
                        .append(":")
                        .append(addvalue)
                        .append(":")
                        .append(limit)
                        .append(" ");
            } else {
                replacebuilder.append("[" + number + ": ")
                        .append(FormatAdderToken)
                        .append(":")
                        .append(addvalue)
                        .append(" ");
            }
        } else replacebuilder.append("[" + content + ": ");
        if (coloridx != Undefined) {
            replacebuilder.append(FormatColorToken).append(":").append(colorstr).append(" ");
        }
        if (styleidx != Undefined) {
            replacebuilder.append(FormatStyleToken).append(":").append(stylestr);
        }
        replacebuilder.append("]");
        return new Pair<>(replacebuilder.toString(), Optional.of(builder.build()));
    }
    public static Pair<String, Optional<Text>> ParseMessage(String str) {
        return ParseMessage(str,null);
    }
    public static Pair<String, Optional<Text>> ParseMessage(String str,Player player) {
        Text.Builder builder = Text.builder("");
        StringBuilder replacebuilder = new StringBuilder();
        for (int i = 0; i < str.length(); ++i) {
            if (str.charAt(i) == '[') {
                int k = str.indexOf(']', i + 1);
                if (k == -1) return new Pair<>("Uncomplete '['", Optional.empty());
                Pair<String, Optional<Text>> res = ParseSub(str.substring(i + 1, k),player);
                if (res.getValue().isPresent()) {
                    builder.append(res.getValue().get());
                    replacebuilder.append(res.getKey());
                    i = k;
                } else return new Pair<>("Invaild Sub Pattern " + res.getKey(), Optional.empty());
            } else {
                replacebuilder.append(str.charAt(i));
                builder.append(Text.of(str.charAt(i)));
            }
        }
        return new Pair<>(replacebuilder.toString(), Optional.of(builder.build()));
    }

    private static Pair<Integer, Integer> GetType(String str, int beginidx) {
        int idx = str.indexOf(":", beginidx);
        if (idx == Undefined) return null;
        String cur = str.substring(beginidx, idx);
        for (int i = 0; i < 3; ++i) {
            if (cur.equals(TypeTokenStrs[i])) {
                return new Pair<>(i, idx);
            }
        }
        return null;
    }

    public static Pair<String, Optional<CommandScript>> ConvertToItemDataStorage(String id, String blockstate, String Token) {
        CommandScript now = new CommandScript();
        now.messagestr = id + ":" + blockstate;
        now.servermessagestr = Token;
        now.ScriptMode = ItemDataStorageToken;
        return new Pair<>("Success", Optional.of(now));
    }

    public static Pair<String, Optional<CommandScript>> ConvertStringFromSender(String str, int ModeToken) {
        return ConvertStringFromSender(str, ModeToken, null);
    }

    public static Pair<String, Optional<CommandScript>> ConvertStringFromSender(String str, int ModeToken, Player player) {
        if (str.equals(DisabledModeStr)) return new Pair<>("Success", Optional.of(DisableMode));
        if (str.equals(DeleteModeStr)) return new Pair<>("Success", Optional.of(DeleteMode));
        if (str.equals(ViewModestr)) return new Pair<>("Success", Optional.of(ViewMode));
        if (str.equals(ClearModestr)) return new Pair<>("Success", Optional.of(ClearMode));
        CommandScript now = new CommandScript();
        now.ScriptMode = ModeToken;
        try {
            int[] idxs = new int[20];
            int commandbeginidx = idxs[0] = str.indexOf(CommandBeginToken);
            int messagebeginidx = idxs[1] = str.indexOf(MessageSendToken);
            int servermessageidx = idxs[2] = str.indexOf(ServerMessageToken);
            int punishbeginidx = idxs[3] = str.indexOf(PunishCommandBeginToken);
            int bypassidx = idxs[4] = str.indexOf(CommandBypassToken);
            int iswalkidx = idxs[5] = str.indexOf(CommandisWalkToken);
            int isClickidx = idxs[6] = str.indexOf(CommandisClickToken);
            int isStopidx = idxs[7] = str.indexOf(CommandisFailStopToken);
            int amountidx = idxs[8] = str.indexOf(CommandExcuteAmountLimitToken);
            int cooldownidx = idxs[9] = str.indexOf(CommandCooldownSecondsToken);
            int valueidx = idxs[10] = str.indexOf(CommandRequriedValueToken);
            int permidx = idxs[11] = str.indexOf(CommandRequriedPermToken);
            int groupidx = idxs[12] = str.indexOf(CommandRequriedGroupToken);
            int delayidx = idxs[13] = str.indexOf(CommandDelaySecondsToken);
            int itemidx = idxs[14] = str.indexOf(CommandRequriedItemToken);
            int handitemidx = idxs[15] = str.indexOf(CommandHandItemToken);
            int playeridx = idxs[16] = str.indexOf(CommandRequriedPlayerToken);
            int isContinueidx = idxs[17] = str.indexOf(CommandisSuccessStopToken);
            int isCloseInfoidx = idxs[18] = str.indexOf(CommandCloseInfoToken);
            idxs[19] = str.length();
            Arrays.sort(idxs);
            if (commandbeginidx != Undefined) {
                commandbeginidx += CommandBeginToken.length() - 1;
                if (commandbeginidx + 2 >= str.length() || (str.charAt(commandbeginidx + 1) != ':'))
                    return new Pair<>("Invaild @Command Pattern.", Optional.empty());
                int nextidx = GetNextIndex(commandbeginidx, idxs);
                String[] contents = str.substring(commandbeginidx + 2, nextidx).trim().split(UnitSpliter);
                for(String cur:contents){
                    Pair<String, Optional<Text>> res = ParseMessage(cur);
                    if (!res.getValue().isPresent())
                        return new Pair<>("Invaild @Command Pattern:\n" + res.getKey(), Optional.empty());
                }
                Collections.addAll(now.comandstr, contents);
            }
            if (messagebeginidx != Undefined) {
                messagebeginidx += MessageSendToken.length() - 1;
                if (messagebeginidx + 2 >= str.length() || (str.charAt(messagebeginidx + 1) != ':'))
                    return new Pair<>("Invaild @Message Pattern.", Optional.empty());
                int nextidx = GetNextIndex(messagebeginidx, idxs);
                now.messagestr = str.substring(messagebeginidx + 2, nextidx).trim();
                Pair<String, Optional<Text>> res = ParseMessage(now.messagestr);
                if (!res.getValue().isPresent())
                    return new Pair<>("Invaild @Message Pattern:\n" + res.getKey(), Optional.empty());
            }
            if (servermessageidx != Undefined) {
                servermessageidx += ServerMessageToken.length() - 1;
                if (servermessageidx + 2 >= str.length() || (str.charAt(servermessageidx + 1) != ':'))
                    return new Pair<>("Invaild @Server Pattern.", Optional.empty());
                int nextidx = GetNextIndex(servermessageidx, idxs);
                now.servermessagestr = str.substring(servermessageidx + 2, nextidx).trim();
                Pair<String, Optional<Text>> res = ParseMessage(now.servermessagestr);
                if (!res.getValue().isPresent())
                    return new Pair<>("Invaild @Server Pattern:\n" + res.getKey(), Optional.empty());
            }
            if (punishbeginidx != Undefined) {
                punishbeginidx += PunishCommandBeginToken.length() - 1;
                if (punishbeginidx + 2 >= str.length() || (str.charAt(punishbeginidx + 1) != ':'))
                    return new Pair<>("Invaild @Punish Pattern.", Optional.empty());
                int nextidx = GetNextIndex(punishbeginidx, idxs);
                String[] contents = str.substring(punishbeginidx + 2, nextidx).trim().split(UnitSpliter);
                for(String cur:contents){
                    Pair<String, Optional<Text>> res = ParseMessage(cur);
                    if (!res.getValue().isPresent())
                        return new Pair<>("Invaild @Punish Pattern:\n" + res.getKey(), Optional.empty());
                }
                Collections.addAll(now.punishstr, contents);
            }
            if (amountidx != Undefined) {
                amountidx += CommandExcuteAmountLimitToken.length() - 1;
                if (amountidx + 2 >= str.length() || (str.charAt(amountidx + 1) != ':'))
                    return new Pair<>("Invaild @Amount Pattern.", Optional.empty());
                int nextidx = GetNextIndex(amountidx, idxs);
                String nstr = str.substring(amountidx + 2, nextidx).trim();
                now.ExcuteAmountLimit = Integer.parseInt(nstr);
                if (now.ExcuteAmountLimit == 0)
                    return new Pair<>("@Amount can not be 0.", Optional.empty());
            }
            if (cooldownidx != Undefined) {
                cooldownidx += CommandCooldownSecondsToken.length() - 1;
                if (cooldownidx + 2 >= str.length() || (str.charAt(cooldownidx + 1) != ':'))
                    return new Pair<>("Invaild @Cooldown Pattern.", Optional.empty());
                int nextidx = GetNextIndex(cooldownidx, idxs);
                String nstr = str.substring(cooldownidx + 2, nextidx).trim();
                now.CooldownSeconds = Integer.parseInt(nstr);
                if (now.CooldownSeconds == 0) return new Pair<>("@Amount can not be 0.", Optional.empty());
            }
            if (valueidx != Undefined) {
                valueidx += CommandRequriedValueToken.length() - 1;
                if (valueidx + 2 >= str.length() || (str.charAt(valueidx + 1) != ':'))
                    return new Pair<>("Invaild @Amount Pattern.", Optional.empty());
                int nextidx = GetNextIndex(valueidx, idxs);
                String nstr = str.substring(valueidx + 2, nextidx).trim();
                now.RequiredValue = Integer.parseInt(nstr);
                if (now.RequiredValue == 0) return new Pair<>("@Value can not be 0.", Optional.empty());
            }
            if (handitemidx != Undefined) {
                handitemidx += CommandHandItemToken.length() - 1;
                String iteminfo = "Invaild hand-item unit pattern, correct pattern is:\n" +
                        "Itemname:ItemChildID:ItemAmount\n" +
                        "or\n" +
                        "@ItemStore:StoredToken:ItemAmount";
                if (handitemidx + 2 >= str.length() || (str.charAt(handitemidx + 1) != ':'))
                    return new Pair<>("Invaild @Hand Pattern.", Optional.empty());
                int nextidx = GetNextIndex(handitemidx, idxs);

                Pair<Integer, Integer> type = GetType(str, handitemidx + 2);
                if (type == null)
                    return new Pair<>("@Hand must have an operation type[exclude,hasone].", Optional.empty());
                now.RequiredHandItemType = type.getKey();
                handitemidx = type.getValue() - 1;
                if (handitemidx + 2 >= str.length())
                    return new Pair<>("Invaild @Hand Pattern.", Optional.empty());

                String cur = str.substring(handitemidx + 2, nextidx).trim();
                if (cur.isEmpty()) {
                    return new Pair<>(iteminfo, Optional.empty());
                }
                int ridx = GetFromEnd(cur, ':', cur.length() - 1);
                if (ridx == Undefined)
                    return new Pair<>(iteminfo, Optional.empty());
                int ridx1 = GetFromEnd(cur, ':', ridx - 1);
                if (ridx1 == Undefined)
                    return new Pair<>(iteminfo, Optional.empty());
                if (ridx1 == 0) {
                    return new Pair<>(iteminfo, Optional.empty());
                }
                String nowname = chu(cur.substring(0, ridx1).trim());
                String nowbsid = chu(cur.substring(ridx1 + 1, ridx).trim());
                if (nowname.equals(StoredItemToken)) {
                    if (player == null) {
                        return new Pair<>("You need to specify a player when using @StoredItem.", Optional.empty());
                    }
                    ScriptData data = player.get(ScriptData.class).get();
                    List<CommandScript> scripts = data.getScripts();
                    boolean isfound = false;
                    for (CommandScript nowc : scripts) {
                        if (nowc.ScriptMode == CommandScript.ItemDataStorageToken) {
                            if (nowc.servermessagestr.equals(nowbsid)) {
                                isfound = true;
                                String cont = nowc.messagestr;
                                int tidx = GetFromEnd(cont, ':', cont.length() - 1);
                                nowname = cont.substring(0, tidx);
                                nowbsid = cont.substring(tidx + 1);
                                break;
                            }
                        }
                    }
                    if (!isfound) {
                        return new Pair<>("The player doesn't have the item-storage token named " + nowbsid + ".", Optional.empty());
                    }
                }
                now.RequiredHandItemName = nowname;
                now.RequiredHandItemBlockState = nowbsid;
                now.RequiredHandItemAmount = Integer.parseInt(cur.substring(ridx + 1).trim());
                if (now.RequiredHandItemAmount == 0)
                    return new Pair<>("@Hand: item's limit amount can not be 0.", Optional.empty());
            }
            if (delayidx != Undefined) {
                delayidx += CommandDelaySecondsToken.length() - 1;
                if (delayidx + 2 >= str.length() || (str.charAt(delayidx + 1) != ':'))
                    return new Pair<>("Invaild @Delay Pattern.", Optional.empty());
                int nextidx = GetNextIndex(delayidx, idxs);
                String nstr = str.substring(delayidx + 2, nextidx).trim();
                now.DelaySeconds = Integer.parseInt(nstr);
                if (now.DelaySeconds == 0) return new Pair<>("@Delay can not be 0.", Optional.empty());
            }
            if (itemidx != Undefined) {
                itemidx += CommandRequriedItemToken.length() - 1;
                String iteminfo = "Invaild item unit pattern, correct pattern is:\n" +
                        "Itemname:ItemChildID:ItemAmount:Ifconsume(true/false)\n" +
                        "or\n" +
                        "@ItemStore:StoredToken:ItemAmount:Ifconsume(true/false)";
                if (itemidx + 2 >= str.length() || (str.charAt(itemidx + 1) != ':'))
                    return new Pair<>("Invaild @Item Pattern", Optional.empty());
                Pair<Integer, Integer> type = GetType(str, itemidx + 2);
                if (type == null)
                    return new Pair<>("@Item must have an operation type[exclude,hasall,hasone].", Optional.empty());
                now.RequiredItemType = type.getKey();
                itemidx = type.getValue() - 1;
                if (itemidx + 2 >= str.length())
                    return new Pair<>("Invaild @Item Pattern.", Optional.empty());
                int nextidx = GetNextIndex(itemidx, idxs);
                String[] contents = str.substring(itemidx + 2, nextidx).trim().split(UnitSpliter);
                for (String cur : contents) {
                    if (cur.isEmpty()) {
                        return new Pair<>(iteminfo, Optional.empty());
                    }
                    int ridx = GetFromEnd(cur, ':', cur.length() - 1);
                    if (ridx == Undefined)
                        return new Pair<>(iteminfo, Optional.empty());
                    int ridx1 = GetFromEnd(cur, ':', ridx - 1);
                    if (ridx1 == Undefined)
                        return new Pair<>(iteminfo, Optional.empty());
                    int ridx2 = GetFromEnd(cur, ':', ridx1 - 1);
                    if (ridx2 == Undefined)
                        return new Pair<>(iteminfo, Optional.empty());
                    if (ridx2 == 0) {
                        return new Pair<>(iteminfo, Optional.empty());
                    }
                    String nowname = chu(cur.substring(0, ridx2).trim());
                    String nowbsid = chu(cur.substring(ridx2 + 1, ridx1).trim());
                    if (nowname.equals(StoredItemToken)) {
                        if (player == null) {
                            return new Pair<>("You need to specify a player when using @StoredItem.", Optional.empty());
                        }
                        ScriptData data = player.get(ScriptData.class).get();
                        List<CommandScript> scripts = data.getScripts();
                        boolean isfound = false;
                        for (CommandScript nowc : scripts) {
                            if (nowc.ScriptMode == CommandScript.ItemDataStorageToken) {
                                if (nowc.servermessagestr.equals(nowbsid)) {
                                    isfound = true;
                                    String cont = nowc.messagestr;
                                    int tidx = GetFromEnd(cont, ':', cont.length() - 1);
                                    nowname = cont.substring(0, tidx);
                                    nowbsid = cont.substring(tidx + 1);
                                    break;
                                }
                            }
                        }
                        if (!isfound) {
                            return new Pair<>("The player doesn't have the item-storage token named " + nowbsid + ".", Optional.empty());
                        }
                    }
                    now.RequiredItemName.add(nowname);
                    int nowli = Integer.parseInt(cur.substring(ridx1 + 1, ridx).trim());
                    if (nowli == 0)
                        return new Pair<>("@Item: item's limit amount can not be 0.", Optional.empty());
                    boolean iscons = Boolean.parseBoolean(cur.substring(ridx + 1).trim());
                    now.RequiredItemBlockState.add(nowbsid);
                    now.RequiredItemAmount.add(nowli);
                    now.RequiredItemIsConsume.add(iscons);
                }
            }
            if (playeridx != Undefined) {
                playeridx += CommandRequriedPlayerToken.length() - 1;
                if (playeridx + 2 >= str.length() || (str.charAt(playeridx + 1) != ':'))
                    return new Pair<>("Invaild @Player Pattern.", Optional.empty());
                Pair<Integer, Integer> type = GetType(str, playeridx + 2);
                if (type == null || type.getKey() == HasallToken)
                    return new Pair<>("@Player must have an operation type[exclude,hasone].", Optional.empty());
                now.RequiredPlayerType = type.getKey();
                playeridx = type.getValue() - 1;
                if (playeridx + 2 >= str.length())
                    return new Pair<>("Invaild @Player Pattern.", Optional.empty());
                int nextidx = GetNextIndex(playeridx, idxs);
                String[] contents = str.substring(playeridx + 2, nextidx).trim().split(UnitSpliter);
                Collections.addAll(now.RequiredPlayerName, contents);
            }
            if (permidx != Undefined) {
                permidx += CommandRequriedPermToken.length() - 1;
                if (permidx + 2 >= str.length() || (str.charAt(permidx + 1) != ':'))
                    return new Pair<>("Invaild @Perm Pattern.", Optional.empty());
                Pair<Integer, Integer> type = GetType(str, permidx + 2);
                if (type == null)
                    return new Pair<>("@Player must have an operation type[exclude,hasall,hasone].", Optional.empty());
                now.RequiredPermType = type.getKey();
                permidx = type.getValue() - 1;
                if (permidx + 2 >= str.length())
                    return new Pair<>("Invaild @Perm Pattern.", Optional.empty());
                int nextidx = GetNextIndex(permidx, idxs);
                String[] contents = str.substring(permidx + 2, nextidx).trim().split(UnitSpliter);
                Collections.addAll(now.RequiredPerm, contents);
            }
            if (groupidx != Undefined) {
                groupidx += CommandRequriedGroupToken.length() - 1;
                if (groupidx + 2 >= str.length() || (str.charAt(groupidx + 1) != ':'))
                    return new Pair<>("Invaild @Group Pattern.", Optional.empty());
                Pair<Integer, Integer> type = GetType(str, groupidx + 2);
                if (type == null)
                    return new Pair<>("@Player must have an operation type[exclude,hasall,hasone].", Optional.empty());
                now.RequiredGroupType = type.getKey();
                groupidx = type.getValue() - 1;
                if (groupidx + 2 >= str.length())
                    return new Pair<>("Invaild @Group Pattern.", Optional.empty());
                int nextidx = GetNextIndex(groupidx, idxs);
                String[] contents = str.substring(groupidx + 2, nextidx).trim().split(UnitSpliter);
                Collections.addAll(now.RequiredGroup, contents);
            }
            now.isBypass = bypassidx != Undefined;
            now.isWalk = iswalkidx != Undefined;
            now.isClick = isClickidx != Undefined;
            now.isFailStop = isStopidx != Undefined;
            now.isSuccessStop = isContinueidx != Undefined;
            now.isCloseInfo = isCloseInfoidx != Undefined;
            if (now.isFailStop && now.isSuccessStop) {
                return new Pair<>("@FailStop and @SuccessStop can't both exist in a script!", Optional.empty());
            }
            if (now.toDescription().isEmpty()) return new Pair<>("Empty Script.", Optional.empty());
        } catch (Exception ex) {
            if (ex instanceof NumberFormatException) {
                new Pair<>("Invaild Number Format!", Optional.empty());
            }
            return new Pair<>("Unknown Exception", Optional.empty());
        }
        return new Pair<>("Success", Optional.of(now));
    }
}