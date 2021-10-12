package com.example.generative_api_v2.util;

import com.example.generative_api_v2.dto.GroupDTO;

import java.util.List;

public final class SubGroupCheckUtil {
    public static boolean checkSetability(GroupDTO parent, GroupDTO main) {
        List<GroupDTO> subGroups = main.getGroups();

        for (GroupDTO sub : subGroups) {
            if (sub.equals(parent)) {
                return false;
            }

            if (!checkSetability(sub, parent)) {
                return false;
            }
        }
        return true;
    }

    private SubGroupCheckUtil() {
    }
}
