package com.lielamar.armsrace.hook.hooks;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.hook.ArmsRaceHook;
import com.lielamar.armsrace.hook.hooks.papi.ArmsRaceExpansion;

public class ArmsRaceHookPlaceholderAPIHook extends ArmsRaceHook {

    public ArmsRaceHookPlaceholderAPIHook() {
        super("PlaceholderAPI");
    }

    @Override
    protected void runHookAction() {
        new ArmsRaceExpansion(Main.getPlugin(Main.class)).register();
    }
}