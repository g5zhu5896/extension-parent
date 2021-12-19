package com.easy.demo.service.impl;

import com.easy.core.dict.service.AbstractDictService;
import com.easy.core.dict.bean.DictBean;
import com.easy.core.dict.strategy.HttpRequestDictCacheStrategy;
import com.easy.core.dict.bean.IDictBean;
import com.easy.demo.entity.Dict;
import com.easy.demo.service.DictService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictBeanServiceImpl extends AbstractDictService {

    @Autowired
    private DictService dictService;

    @Override
    public List<IDictBean> queryBeanListByDictKey(String dictKey) {
        List<Dict> dictList = dictService.list(dictKey);
        List<IDictBean> dictBeanList = Lists.newArrayListWithCapacity(dictList.size());
        dictList.forEach(dict -> {
            DictBean dictBean = new DictBean();
            dictBean.setValue(dict.getValue());
            dictBean.setLabel(dict.getLabel());
            dictBean.setDictKey(dictKey);
            dictBeanList.add(dictBean);
        });
        return dictBeanList;
    }
}
