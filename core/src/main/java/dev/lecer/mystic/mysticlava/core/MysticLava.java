package dev.lecer.mystic.mysticlava.core;

import dev.lecer.mystic.mysticlava.core.enums.Version;
import dev.lecer.mystic.mysticlava.core.hook.PlaceholderAPIHook;
import dev.lecer.mystic.mysticlava.core.utils.ServerUtils;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class MysticLava extends JavaPlugin {

    private static MysticLava INSTANCE;

    private PlaceholderAPIHook placeholderAPI;

    @Override
    public void onLoad() {
        INSTANCE = this;
        Version.bootstrap();
    }

    @Override
    public void onEnable() {
        ServerUtils.ifPluginEnabled("PlaceholderAPI", () -> {
            this.placeholderAPI = new PlaceholderAPIHook();
            this.placeholderAPI.register();
        });
    }

    @Override
    public void onDisable() {
        ServerUtils.ifPluginEnabled("PlaceholderAPI", () -> this.placeholderAPI.unregister());
    }

    public static MysticLava getInstance() {
        return INSTANCE;
    }
}
