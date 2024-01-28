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

    public void ifPluginEnabledOrElse(String pluginName, Runnable runnable, Runnable orElse) {
        if (isPluginEnabled(pluginName)) runnable.run();
        else orElse.run();
    }

    public boolean isFolia() {
        var classExist = isClassExist("io.papermc.paper.threadedregions.RegionizedServer");
        if (classExist) LoggerUtils.info("Detected server is running on Folia.");

        return classExist;
    }

    public boolean isClassExist(String clazz) {
        try {
            Class.forName(clazz);
            return true;
        } catch (ClassNotFoundException exception) {
            return false;
        }
    }
}
