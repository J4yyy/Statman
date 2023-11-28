package de.j4yyy.statman.commands;

import de.j4yyy.statman.Statman;
import de.j4yyy.statman.manager.ThreadMonitorManager;
import de.j4yyy.statman.utils.HelpingFunctions;
import de.j4yyy.statman.utils.LogFile;
import de.j4yyy.statman.utils.LogType;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.logging.Level;

public class CreateLogCommand implements CommandExecutor {

    private final Statman plugin;
    private final ThreadMonitorManager threadMonitorManager;

    public CreateLogCommand(Statman plugin, ThreadMonitorManager threadMonitorManager) {
        this.plugin = plugin;
        this.threadMonitorManager = threadMonitorManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        switch (args[0].toLowerCase()) {
            case "create" -> {
                if(!sender.hasPermission("statman.log.create")) {
                    sender.sendMessage("[ThreadMonitor] > Fehlende Berechtigungen");
                    return false;
                }
                String name = "";
                if(args.length != 2) {
                    name = HelpingFunctions.generateName();
                } else {
                    name = args[1].replace("/", "-");
                }
                LogFile logFile = new LogFile(name+".txt", plugin.getDataFolder());
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
                sender.sendMessage("[ThreadMonitor] > Log created '" + name + "'");
            }
            case "start" -> {
                if(!sender.hasPermission("statman.monitor")) {
                    sender.sendMessage("[ThreadMonitor] > Fehlende Berechtigungen");
                    return false;
                }
                if(!threadMonitorManager.isRunning()) {
                    this.threadMonitorManager.startMonitoring();
                    sender.sendMessage("[ThreadMonitor] > now running");
                } else {
                    sender.sendMessage("[ThreadMonitor] > already running");
                }
            }
            case "stop" -> {
                if(!sender.hasPermission("statman.monitor")) {
                    sender.sendMessage("[ThreadMonitor] > Fehlende Berechtigungen");
                    return false;
                }
                if(threadMonitorManager.isRunning()) {
                    this.threadMonitorManager.stopMonitoring();
                    sender.sendMessage("[ThreadMonitor] > now stopped");
                } else {
                    sender.sendMessage("[ThreadMonitor] > already stopped");
                }
            }
        }
        return false;
    }
}

//tlog create <name>
//tlog start
//tlog stop