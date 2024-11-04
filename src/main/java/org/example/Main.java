package org.example;

import org.example.config.ApplicationConfig;
import org.example.config.Populator;

public class Main {
    public static void main(String[] args) {
        ApplicationConfig.startServer(7070);
        Populator.main(null);
    }
}