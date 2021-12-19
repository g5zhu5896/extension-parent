package com.easy.core.dict.bean;

/**
 * @author zzz
 * @date 2021/6/4 17:53
 */
public interface IDictBean<T> {

    /**
     * 获取字典Value
     *
     * @return
     */
    T getValue();

    /**
     * 设置字典Value
     *
     * @param value
     */
    void setValue(T value);

    /**
     * 获取字典文本内容
     *
     * @return
     */
    String getLabel();

    /**
     * 设置字典文本内容
     *
     * @param label
     */
    void setLabel(String label);


    /**
     * 获取字典类型,如果字典key唯一可以为空,
     * 否则得返回字典类型以便可以根据字典类型和字典key识别唯一
     *
     * @return
     */
    String getDictKey();

    /**
     * 获取字典类型,如果字典key唯一可以为空,
     * 否则得返回字典类型以便可以根据字典类型和字典key识别唯一
     *
     * @param dictKey
     * @return
     */
    void setDictKey(String dictKey);

}
