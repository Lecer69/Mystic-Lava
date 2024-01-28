package dev.lecer.mystic.mysticlava.core.utils;

import dev.lecer.mystic.mysticlava.core.MysticLava;
import io.papermc.paper.threadedregions.scheduler.AsyncScheduler;
import io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler;
import io.papermc.paper.threadedregions.scheduler.RegionScheduler;
import lombok.experimental.UtilityClass;
import org.bukkit.Server;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

@UtilityClass
public class TaskUtils {

    public BukkitTask run(Runnable runnable) {
        return scheduler().runTask(plugin(), runnable);
    }

    public BukkitTask runLater(Runnable runnable, long delay) {
        return scheduler().runTaskLater(plugin(), runnable, delay);
    }

    public BukkitTask runTaskTimer(Runnable runnable, long delay, long period) {
        return scheduler().runTaskTimer(plugin(), runnable, delay, period);
    }

    public BukkitTask runAsync(Runnable runnable) {
        return scheduler().runTaskAsynchronously(plugin(), runnable);
    }

    public BukkitTask runLaterAsync(Runnable runnable, long delay) {
        return scheduler().runTaskLaterAsynchronously(plugin(), runnable, delay);
    }

    public BukkitTask runTaskTimerAsync(Runnable runnable, long delay, long period) {
        return scheduler().runTaskTimerAsynchronously(plugin(), runnable, delay, period);
    }

    public AsyncScheduler getAsyncScheduler() {
        return server().getAsyncScheduler();
    }

    public RegionScheduler getRegionScheduler() {
        return server().getRegionScheduler();
    }

    public GlobalRegionScheduler getGlobalRegionScheduler() {
        return server().getGlobalRegionScheduler();
    }

    private BukkitScheduler scheduler() {
        if (plugin().isFolia())
            throw new RuntimeException("Server is running on Folia, and you cannot use BukkitScheduler.");

        return server().getScheduler();
    }

    private Server server() {
        return plugin().getServer();
    }

    private MysticLava plugin() {
        return MysticLava.getInstance();
    }
}
