package com.easy.core.dict.service;


import com.easy.core.SpringUtils;
import com.easy.core.dict.bean.IDictBean;

import java.util.List;

/**
 * @author zzz
 * @date 2021年7月2日09:53:27
 */
public interface IDictService {

    /**
     * 用字典key获取DictBean
     *
     * @param value
     * @param type  字段类型,请传入当前字段的类型
     * @return
     */
    IDictBean getDictBean(Object value, Class<? extends IDictBean> type);

    /**
     * 根据字典类型查询对应的DictBean集合
     *
     * @param dictKey 字典key
     * @return 返回字典类型对应的所有DictBean
     */
    List<IDictBean> queryBeanListByDictKey(String dictKey);

    /**
     * 获取实例
     *
     * @return
     */
    public static IDictService getInstance() {
        //后面需考虑工厂类
        return SpringUtils.getBean(IDictService.class);
    }
}
