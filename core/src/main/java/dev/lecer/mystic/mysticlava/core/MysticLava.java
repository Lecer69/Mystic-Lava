package dev.lecer.mystic.mysticlava.core;

import dev.lecer.mystic.mysticlava.core.enums.Version;
import org.bukkit.plugin.java.JavaPlugin;

public final class MysticLava extends JavaPlugin {

    private static MysticLava INSTANCE;

    @Override
    public void onLoad() {
        INSTANCE = this;
        Version.bootstrap();
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    public static MysticLava getInstance() {
        return INSTANCE;
    }
}
