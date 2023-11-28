package de.j4yyy.statman.utils;

import java.io.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LogFile {

    private final File file;

    public LogFile(String name, File path) {
        this.file = new File(path, name);

        if(!this.file.exists()) {
            path.mkdirs();
            try {
                this.file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void log(LogType type, String plugin, int priority, Thread.State state, boolean isAlive, boolean isInterrupted, boolean isDaemon) {
        try {
            Writer output = new BufferedWriter(new FileWriter(this.file, true));

            //Zone Setup
            Instant instant = Instant.now();
            ZoneId z = ZoneId.of("Europe/Berlin");
            ZonedDateTime zdt = instant.atZone(z);
            Locale l = Locale.GERMANY;
            DateTimeFormatter f = DateTimeFormatter.ofPattern("dd.MM.yyyy | HH:mm:ss");

            output
                    .append("[").append(zdt.format(f)).append(" ").append(type.label).append(" ] > ")
                    .append("[ ").append(plugin).append(" ] ")
                    .append("Priority: ").append(priority+"").append(" | ")
                    .append("State: ").append(state.name()).append(" | ")
                    .append("isAlive: ").append(isAlive+"").append(" | ")
                    .append("isInterrupted: ").append(isInterrupted+"").append(" | ")
                    .append("isDaemon: ").append(isDaemon+"").append(" | ")
                    .append("\n");
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void log(LogType type, String plugin, String message) {
        try {
            Writer output = new BufferedWriter(new FileWriter(this.file, true));
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy | HH:mm:ss");
            output
                    .append("[").append(dtf.format(LocalDateTime.now())).append(" ").append(type.label).append(" ] > ")
                    .append("[ ").append(plugin).append(" ] ")
                    .append(message)
                    .append("\n");
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}