package de.j4yyy.statman.manager;

import de.j4yyy.statman.Statman;
import de.j4yyy.statman.tasks.MonitorTask;
import de.j4yyy.statman.utils.LogFile;
import de.j4yyy.statman.utils.LogType;
import org.bukkit.Bukkit;

import java.util.Set;
import java.util.logging.Level;

public class ThreadMonitorManager {

    private final Statman plugin;
    private final MonitorTask monitorTask;
    private final LogFile logFile;

    private boolean isRunning = false;

    public ThreadMonitorManager(Statman plugin, LogFile logFile) {
        this.plugin = plugin;
        this.logFile = logFile;

        this.monitorTask = new MonitorTask(this);
    }

    public void collectThreads() {
        int deadCount = 0;
        int interruptedCount = 0;
        Set<Thread> threads = Thread.getAllStackTraces().keySet();
        for(Thread t : threads) {
            LogType type = LogType.INFO;
            if(!t.isAlive()) {
                type = LogType.WARN;
                deadCount += 1;
            }
            if(t.isInterrupted()) {
                type = LogType.WARN;
                interruptedCount += 1;
            }
            logFile.log(type, t.getName(), t.getPriority(), t.getState(), t.isAlive(), t.isInterrupted(), t.isDaemon());
        }

        if(deadCount > (threads.size()/8)) {
            Bukkit.getLogger().log(Level.WARNING, deadCount + ". Threads are currently Dead");
            logFile.log(LogType.ERROR, "Statman", deadCount + ". Threads are currently Dead");
        }

        if(interruptedCount > (threads.size()/4)) {
            Bukkit.getLogger().log(Level.WARNING, interruptedCount + ". Threads are currently interrupted");
            logFile.log(LogType.ERROR, "Statman", interruptedCount + ". Threads are currently interrupted");
        }
    }

    public void startMonitoring() {
        this.monitorTask.runTaskTimer(plugin, 0L, 20L*60*5);
        isRunning = true;
    }

    public void stopMonitoring() {
        this.monitorTask.cancel();
        isRunning = false;
    }

    public boolean isRunning() {
        return this.isRunning;
    }
}