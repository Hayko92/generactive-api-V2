package com.example.generative_api_v2.util;

public final class ItemIdGenerator {
    private static int CURRENT = 1;

    public static int getNextId() {
        return CURRENT++;
    }

    private ItemIdGenerator() {
    }
}
