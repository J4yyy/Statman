package de.j4yyy.statman.tasks;

import de.j4yyy.statman.manager.ThreadMonitorManager;
import org.bukkit.scheduler.BukkitRunnable;

public class MonitorTask extends BukkitRunnable {

    private final ThreadMonitorManager threadMonitorManager;

    public MonitorTask(ThreadMonitorManager threadMonitorManager) {
        this.threadMonitorManager = threadMonitorManager;
    }

    @Override
    public void run() {
        this.threadMonitorManager.collectThreads();
    }
}