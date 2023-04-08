package com.easy.demo.service.impl;

import com.easy.core.dict.bean.DictBean;
import com.easy.core.dict.bean.IDictBean;
import com.easy.core.dict.bean.TableDictBean;
import com.easy.core.dict.service.AbstractDictService;
import com.easy.demo.entity.Dict;
import com.easy.demo.service.DictService;
import com.easy.utils.ReflectUtils;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DictBeanServiceImpl extends AbstractDictService {

    @Autowired
    private DictService dictService;

    @Override
    public List<IDictBean> queryBeanListByDictKey(DictBean baseDictBean) {

        Class<? extends IDictBean> clazz = baseDictBean.getClass();

        List<Dict> dictList = dictService.list(baseDictBean.getDictKey());
        Class<?> valueType = ReflectUtils.getSuperClassGenericType(clazz, 0);
        List<IDictBean> dictBeanList = Lists.newArrayListWithCapacity(dictList.size());
        dictList.forEach(dict -> {
            IDictBean dictBean = baseDictBean.clone();
            if (valueType.isAssignableFrom(Integer.class)) {
                dictBean.setValue(Integer.parseInt(dict.getValue()));
            } else {
                dictBean.setValue(dict.getValue());
            }
            dictBean.setLabel(dict.getLabel());
            dictBeanList.add(dictBean);
        });
        return dictBeanList;
    }

    @Override
    public List<IDictBean> queryBeanListBySql(TableDictBean baseDictBean) {
        Class<? extends TableDictBean> clazz = baseDictBean.getClass();
        List<Map<String, Object>> dictList = dictService.selectBySql(baseDictBean.getSql());
        Class<?> valueType = ReflectUtils.getSuperClassGenericType(clazz, 0);
        List<IDictBean> dictBeanList = Lists.newArrayListWithCapacity(dictList.size());
        dictList.forEach(dict -> {
            IDictBean dictBean = baseDictBean.clone();
            Object value = dict.get("VALUE");
            if (valueType.isAssignableFrom(Integer.class)) {
                dictBean.setValue(Integer.parseInt(value.toString()));
            } else {
                dictBean.setValue(value.toString());
            }
            dictBean.setLabel(dict.get("LABEL").toString());
            dictBeanList.add(dictBean);
        });
        return dictBeanList;
    }
}
