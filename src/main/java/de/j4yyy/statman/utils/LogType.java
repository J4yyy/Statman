package de.j4yyy.statman.utils;

public enum LogType {
    INFO("INFO"),
    WARN("WARNING"),
    ERROR("ERROR");

    public final String label;

    LogType(String label) {
        this.label = label;
    }
}