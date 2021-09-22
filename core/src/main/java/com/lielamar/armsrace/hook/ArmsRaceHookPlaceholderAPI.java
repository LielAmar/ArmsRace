package com.lielamar.armsrace.hook;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.hook.papi.ArmsRacePlaceholder;

public class ArmsRaceHookPlaceholderAPI extends ArmsRaceHook {

    public ArmsRaceHookPlaceholderAPI() {
        super("PlaceholderAPI");
    }

    @Override
    protected void runHookAction() {
        new ArmsRacePlaceholder(Main.getPlugin(Main.class)).register();
    }
}