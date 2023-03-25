package com.easy.demo.service.impl;

import com.easy.core.dict.bean.DictBean;
import com.easy.core.dict.bean.IDictBean;
import com.easy.core.dict.service.AbstractDictService;
import com.easy.demo.entity.Dict;
import com.easy.demo.service.DictService;
import com.easy.utils.ReflectUtils;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictBeanServiceImpl extends AbstractDictService {

    @Autowired
    private DictService dictService;

    @Override
    public List<IDictBean> queryBeanListByDictKey(String dictKey, Class<? extends IDictBean> clazz) {
        List<Dict> dictList = dictService.list(dictKey);
        Class<?> valueType = ReflectUtils.getSuperClassGenericType(clazz, 0);
        List<IDictBean> dictBeanList = Lists.newArrayListWithCapacity(dictList.size());
        dictList.forEach(dict -> {
            DictBean dictBean = new DictBean();
            if (valueType.isAssignableFrom(Integer.class)) {
                dictBean.setValue(Integer.parseInt(dict.getValue()));
            } else {
                dictBean.setValue(dict.getValue());
            }
            dictBean.setLabel(dict.getLabel());
            dictBean.setDictKey(dictKey);
            dictBeanList.add(dictBean);
        });
        return dictBeanList;
    }
}
