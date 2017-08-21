package com.cxandy.BlockAction.Localize;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public class LocaleMapper {
    public Map<String,LocaleHandler> AvaliableLocales = new HashMap<>();
    public Optional<LocaleHandler> Gethandler(Locale locale){
        String now = locale.getLanguage()+"_"+locale.getCountry();
        if(AvaliableLocales.containsKey(now)){
            return Optional.ofNullable(AvaliableLocales.get(now));
        }
        else return Optional.ofNullable(AvaliableLocales.get("en_US"));
    }
    public String getTraslation(Locale locale,String str){
        Optional<LocaleHandler> handlerOptional = Gethandler(locale);
        if(handlerOptional.isPresent()){
            return handlerOptional.get().getString(str);
        }
        else return str;
    }
}
