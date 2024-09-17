package com.example.Terriffic;

import io.github.cdimascio.dotenv.Dotenv;

public class DotenvSingleton {
    private static Dotenv dotenv;

    private DotenvSingleton() {}

    public static Dotenv getInstance() {
        if (dotenv == null) {
            dotenv = Dotenv.configure().load();
        }
        return dotenv;
    }
}