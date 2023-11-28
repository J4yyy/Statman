package de.j4yyy.statman;

import de.j4yyy.statman.commands.CreateLogCommand;
import de.j4yyy.statman.commands.tabcomp.CreateLogTabComp;
import de.j4yyy.statman.manager.ThreadMonitorManager;
import de.j4yyy.statman.utils.LogFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Statman extends JavaPlugin {

    private ThreadMonitorManager threadMonitorManager;

    public ThreadMonitorManager getThreadManager() {
        return this.threadMonitorManager;
    }

    @Override
    public void onEnable() {
        this.threadMonitorManager = new ThreadMonitorManager(this, new LogFile("log.txt", getDataFolder()));

        registerCommands();
    }

    @Override
    public void onDisable() {
        this.threadMonitorManager.collectThreads();
    }

    private void registerCommands() {
        Objects.requireNonNull(this.getCommand("tlog")).setExecutor(new CreateLogCommand(this, threadMonitorManager));
        Objects.requireNonNull(this.getCommand("tlog")).setTabCompleter(new CreateLogTabComp());
    }
}
