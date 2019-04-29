package com.bupt.dc.object.constant;

import org.apache.commons.lang.StringUtils;

public enum CategoryEnum {

    EAT(2),
    FUN(2),
    TRAFFIC(2),
    SHOPPING(2),

    SALARY(1),
    TRANSFER(1);

    private CategoryEnum(Integer type) {
        this.type = type;
    }

    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public static Integer getTypeByCategory(String category) {
        for (CategoryEnum categoryEnum : CategoryEnum.values()) {
            if (StringUtils.equals(category, categoryEnum.name())) {
                return categoryEnum.getType();
            }
        }
        return null;
    }

}
