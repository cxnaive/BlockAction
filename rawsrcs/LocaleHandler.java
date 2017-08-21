package com.cxandy.BlockAction.Localize;

import java.util.HashMap;
import java.util.Map;

public class LocaleHandler {
    public Map<String,String> strs = new HashMap<>();
    public String getString(String str){
        if(strs.containsKey(str)) return strs.get(str);
        return str;
    }
}
