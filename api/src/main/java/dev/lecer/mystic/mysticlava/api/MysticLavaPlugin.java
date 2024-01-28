package dev.lecer.mystic.mysticlava.api;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class MysticLavaPlugin extends JavaPlugin {

    private static MysticLavaPlugin INSTANCE;

    public static MysticLavaPlugin getInstance() {
        return INSTANCE;
    }

    public static void setInstance(MysticLavaPlugin instance) {
        MysticLavaPlugin.INSTANCE = instance;
    }
}
