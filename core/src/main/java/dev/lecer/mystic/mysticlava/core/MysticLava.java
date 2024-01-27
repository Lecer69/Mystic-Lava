package dev.lecer.mystic.mysticlava.core;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.PacketEventsAPI;
import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import dev.lecer.mystic.mysticlava.core.enums.Version;
import dev.lecer.mystic.mysticlava.core.hook.PlaceholderAPIHook;
import dev.lecer.mystic.mysticlava.core.utils.ServerUtils;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import lombok.Getter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

@Getter
public final class MysticLava extends JavaPlugin {

    private static MysticLava INSTANCE;

    private PlaceholderAPIHook placeholderAPI;
    private PacketEventsAPI<?> packetEventsAPI;

    @Override
    public void onLoad() {
        INSTANCE = this;
        Version.bootstrap();
    }

    @Override
    public void onEnable() {
        var pluginManager = this.getServer().getPluginManager();
        this.listeners().forEach(listener -> pluginManager.registerEvents(listener, this));

        ServerUtils.ifPluginEnabled("PlaceholderAPI", () -> {
            this.placeholderAPI = new PlaceholderAPIHook();
            this.placeholderAPI.register();
        });

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
        }
    }

    @Override
    public void onDisable() {
        ServerUtils.ifPluginEnabled("PlaceholderAPI", () -> this.placeholderAPI.unregister());
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
