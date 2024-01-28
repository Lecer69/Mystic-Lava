package dev.lecer.mystic.mysticlava.core;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.PacketEventsAPI;
import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import dev.lecer.mystic.mysticlava.api.MysticLavaPlugin;
import dev.lecer.mystic.mysticlava.core.enums.Version;
import dev.lecer.mystic.mysticlava.core.hook.PlaceholderAPIHook;
import dev.lecer.mystic.mysticlava.core.utils.LoggerUtils;
import dev.lecer.mystic.mysticlava.core.utils.ServerUtils;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import java.util.List;

@Getter
public final class MysticLava extends MysticLavaPlugin {

    private static MysticLava INSTANCE;

    private PlaceholderAPIHook placeholderAPI;
    private PacketEventsAPI<?> packetEventsAPI;

    private boolean folia;

    @Override
    public void onLoad() {
        INSTANCE = this;
        MysticLavaPlugin.setInstance(this);
        Version.bootstrap();
        this.folia = ServerUtils.isFolia();
    }

    @Override
    public void onEnable() {
        var pluginManager = this.getServer().getPluginManager();
        this.listeners().forEach(listener -> pluginManager.registerEvents(listener, this));

        ServerUtils.ifPluginEnabledOrElse("PlaceholderAPI", () -> {
            LoggerUtils.info("Registered placeholders to PlaceholderAPI.");

            this.placeholderAPI = new PlaceholderAPIHook();
            this.placeholderAPI.register();
        }, () -> LoggerUtils.warning("Unable to register placeholders to PlaceholderAPI."));

        if (ServerUtils.isClassExist("com.github.retrooper.packetevents.PacketEventsAPI")) {
            PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
            var packetAPI = PacketEvents.getAPI();

            packetAPI.getSettings()
                    .checkForUpdates(false)
                    .reEncodeByDefault(true) // TODO: check if it is buggy
                    .bStats(false)
                    .debug(false);

            packetAPI.load();

            this.packetEventsAPI = packetAPI;

            var eventManager = packetAPI.getEventManager();
            this.packetListeners().forEach(packetListener -> eventManager.registerListener(packetListener, PacketListenerPriority.NORMAL));

            LoggerUtils.info("Registered packetevents api.");
        } else {
            LoggerUtils.error("Unable to register packetevents api, plugin will not work without it.");
            this.disablePlugin();
        }
    }

    @Override
    public void onDisable() {
        ServerUtils.ifPluginEnabled("PlaceholderAPI", () -> {
            if (this.placeholderAPI != null) this.placeholderAPI.unregister();
        });
    }

    public void disablePlugin() {
        try {
            MysticLavaPlugin.setInstance(null);
            Bukkit.getPluginManager().disablePlugin(this);
        } catch (Throwable throwable) {
            Bukkit.getServer().shutdown();
        }
    }

    private List<PacketListener> packetListeners() {
        return List.of();
    }

    private List<Listener> listeners() {
        return List.of();
    }

    public static MysticLava getInstance() {
        return INSTANCE;
    }
}
