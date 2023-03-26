package com.easy.bean;


import com.baomidou.mybatisplus.annotation.IEnum;

import java.io.Serializable;

/**
 * 基本枚举
 *
 * @author zzz
 * @date 2020/7/13 16:53
 */
public interface BaseEnum<T extends Serializable> extends IEnum<T> {

    /**
     * 获取枚举文本
     *
     * @return 举文本
     */
    String getLabel();

    /**
     * 获取枚举名称
     *
     * @return 枚举名称
     */
    String name();

    /**
     * @param type  枚举类型
     * @param value 枚举值
     * @param <M> BaseEnum的子类
     * @return 枚举值
     */
    public static <M extends BaseEnum> M getEnum(Class<M> type, Object value) {
        M[] objs = type.getEnumConstants();
        for (M em : objs) {
            if (em.getValue().toString().equals(value.toString()) || em.name().equals(value)) {
                return em;
            }
        }
        return null;
    }
}
