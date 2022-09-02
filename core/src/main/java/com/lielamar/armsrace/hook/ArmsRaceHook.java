package com.lielamar.armsrace.hook;

import com.lielamar.armsrace.hook.hooks.ArmsRaceHookPlaceholderAPIHook;
import org.bukkit.Bukkit;

import java.util.LinkedHashMap;

public abstract class ArmsRaceHook {

    private static final LinkedHashMap<String, ArmsRaceHook> hooks = new LinkedHashMap<>();

    static {
        hooks.put("PlaceholderAPI", new ArmsRaceHookPlaceholderAPIHook());
    }

    public static void attemptHooks() {
        hooks.values().forEach(ArmsRaceHook::hook);
    }

    public static boolean getHook(String pluginName) {
        if(hooks.get(pluginName) == null) {
            return false;
        }

        return hooks.get(pluginName).isEnabled();
    }

    public static ArmsRaceHook getHookInstance(String pluginName) {
        return hooks.get(pluginName);
    }

    protected String pluginName;
    protected boolean enabled;

    public ArmsRaceHook(String pluginName) {
        this.pluginName = pluginName;
    }

    public void hook() {
        if (Bukkit.getPluginManager().isPluginEnabled(this.pluginName)) {
            String version = Bukkit.getPluginManager().getPlugin(this.pluginName).getDescription().getVersion();
            System.out.print("Successfully hooked into " + this.pluginName + " version §e" + version);
            this.enabled = true;
            this.runHookAction();
        }
    }

    protected abstract void runHookAction();

    public boolean isEnabled() {
        return enabled;
    }
}