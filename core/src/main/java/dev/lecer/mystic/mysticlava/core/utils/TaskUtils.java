package dev.lecer.mystic.mysticlava.core.utils;

import dev.lecer.mystic.mysticlava.core.MysticLava;
import lombok.experimental.UtilityClass;
import org.bukkit.plugin.Plugin;
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

    private BukkitScheduler scheduler() {
        return plugin().getServer().getScheduler();
    }

    private Plugin plugin() {
        return MysticLava.getInstance();
    }
}
