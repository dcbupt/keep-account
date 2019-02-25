package com.bupt.dc.object.constant;

import java.util.ArrayList;
import java.util.List;

public enum RoleEnum {
    ADMIN,
    USER;

    public static List<String> getRoles() {
        List<String> roles = new ArrayList<>();
        for (RoleEnum roleEnum : RoleEnum.values()) {
            roles.add(roleEnum.name());
        }
        return roles;
    }
}
