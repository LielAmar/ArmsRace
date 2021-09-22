package com.lielamar.armsrace.hook;

import org.bukkit.Bukkit;

import java.util.HashMap;

public abstract class ArmsRaceHook {

    private static final HashMap<String, ArmsRaceHook> hooks = new HashMap<>();

    static {
        hooks.put("PlaceholderAPI", new ArmsRaceHookPlaceholderAPI());
    }

    public static void attemptHooks() {
        hooks.values().forEach(hook -> {
            hook.hook();
        });
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