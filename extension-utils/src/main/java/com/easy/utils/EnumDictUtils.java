package com.easy.utils;

import com.easy.bean.BaseEnum;
import com.easy.core.dict.bean.IDictBean;
import com.easy.core.dict.service.IDictService;

/**
 * @author zzz
 * @date 2021/12/19 10:56
 */
public class EnumDictUtils {

    /**
     * 把值转换成对于的枚举或字典
     * 如果需要自己手动转换时用这个工具,这样万一哪天突然Enum突然想改成dict的时候,需要改动的地方就可以少很多
     *
     * @param value 枚举值或字典值
     * @param clazz 可以是BaseEnum的子类 也可以是 IDictBean的子类
     * @param <T>
     * @return 字典或美剧
     */
    public static <T> T convert(Object value, Class<T> clazz) {
        if (BaseEnum.class.isAssignableFrom(clazz)) {
            Class<? extends BaseEnum> type = (Class<? extends BaseEnum>) clazz;
            return (T) BaseEnum.getEnum(type, value);
        } else {
            Class<? extends IDictBean> type = (Class<? extends IDictBean>) clazz;
            return (T) IDictService.getInstance().getDictBean(value, type);
        }
    }
}
