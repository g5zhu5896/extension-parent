package com.easy.core.dict.bean;

import com.easy.utils.ReflectUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zzz
 * @date 2021/6/4 17:53
 */
@Getter
@EqualsAndHashCode(of = "value")
public class DictBean<T> implements IDictBean<T> {

    /**
     * 字典值
     */
    @Setter
    private T value;

    /**
     * 字典文本内容
     */
    @Setter
    private String label;

    /**
     * 字典key
     */
    private String dictKey;

    public DictBean(String dictKey) {
        this.dictKey = dictKey;
    }

    public final static <U extends DictBean<T>, T> U create(T value, Class<U> type) {
        U dictBean = ReflectUtils.newInstance(type);
        dictBean.setValue(value);
        return dictBean;
    }

    public DictBean clone() {
        try {
            return (DictBean) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalArgumentException("克隆异常");
        }
    }
}
