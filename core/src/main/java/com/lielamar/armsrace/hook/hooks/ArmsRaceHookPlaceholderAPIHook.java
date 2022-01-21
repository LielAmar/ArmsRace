package com.lielamar.armsrace.hook.hooks;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.hook.ArmsRaceHook;
import com.lielamar.armsrace.placeholder.ArmsRacePlaceholder;

public class ArmsRaceHookPlaceholderAPIHook extends ArmsRaceHook {

    public ArmsRaceHookPlaceholderAPIHook() {
        super("PlaceholderAPI");
    }

    @Override
    protected void runHookAction() {
        new ArmsRacePlaceholder(Main.getPlugin(Main.class)).register();
    }
}