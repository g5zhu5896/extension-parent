package com.easy.core.dict.service;


import com.easy.core.SpringUtils;
import com.easy.core.dict.bean.DictBean;
import com.easy.core.dict.bean.IDictBean;
import com.easy.core.dict.bean.TableDictBean;

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
     * @return 字典实体
     */
    IDictBean getDictBean(Object value, Class<? extends IDictBean> type);

    /**
     * 根据字典类型查询对应的DictBean集合
     *
     * @param dictKey 字典key
     * @return 返回字典类型对应的所有DictBean
     */
    List<IDictBean> queryBeanListByDictKey(DictBean baseDictBean);

    /**
     * 根据sql查询对应的DictBean集合
     *
     * @param baseDictBean 字典实例
     * @return 返回sql对应的所有DictBean
     */
    default List<IDictBean> queryBeanListBySql(TableDictBean baseDictBean) {
        throw new UnsupportedOperationException("不支持的操作");
    }

    /**
     * 获取实例
     *
     * @return IDictService
     */
    public static IDictService getInstance() {
        //后面需考虑工厂类
        return SpringUtils.getBean(IDictService.class);
    }
}
