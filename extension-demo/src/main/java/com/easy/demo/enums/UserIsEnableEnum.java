package com.easy.demo.enums;

import com.easy.bean.BaseEnum;
import lombok.Getter;


@Getter
public enum UserIsEnableEnum implements BaseEnum<Integer> {
    /**
     *
     */
    YES(1, "启用"),
    NO(2, "未启用");

    private Integer value;
    private String label;

    public static final String description = "是否启用：1-启用 2-未启用";

    UserIsEnableEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
}
