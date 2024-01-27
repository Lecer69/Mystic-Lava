package dev.lecer.mystic.mysticlava.core.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

@UtilityClass
public class ServerUtils {

    public boolean isPluginEnabled(String pluginName) {
        return Bukkit.getServer().getPluginManager().isPluginEnabled(pluginName);
    }

    public void ifPluginEnabled(String pluginName, Runnable runnable) {
        if (isPluginEnabled(pluginName)) runnable.run();
    }
}
