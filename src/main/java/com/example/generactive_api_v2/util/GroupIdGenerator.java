package com.example.generactive_api_v2.util;

public class GroupIdGenerator {
    private static int CURRENT = 1;

    public static int getNextId() {
        return CURRENT++;
    }

    private GroupIdGenerator() {
    }
}
