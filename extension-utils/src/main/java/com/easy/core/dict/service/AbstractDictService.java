package com.easy.core.dict.service;


import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.easy.core.dict.bean.DictBean;
import com.easy.core.dict.strategy.DictCacheStrategy;
import com.easy.core.dict.bean.IDictBean;
import com.easy.core.dict.strategy.HttpRequestDictCacheStrategy;
import com.easy.utils.StringUtils;

import java.util.List;

/**
 * @author zzz
 * @date 2021年7月2日09:53:19
 */
public abstract class AbstractDictService implements IDictService {

    DictCacheStrategy cache;

    public AbstractDictService() {
        this(new HttpRequestDictCacheStrategy());
    }

    public AbstractDictService(DictCacheStrategy cache) {
        this.cache = cache;
    }

    @Override
    public IDictBean getDictBean(Object value, Class<? extends IDictBean> type) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        IDictBean dictBean = null;
        try {
            dictBean = type.newInstance();
            dictBean.setValue(value);

            //缓存用的key
            String cacheKey = getCacheKey(dictBean);
            IDictBean cacheDictBean = cache.getFromCache(cacheKey);
            if (cacheDictBean == null) {
                if (StringUtils.isNotBlank(dictBean.getDictKey())) {
                    List<IDictBean> dictBeanList = queryBeanListByDictKey(dictBean.getDictKey(), type);
                    if (CollectionUtils.isNotEmpty(dictBeanList)) {
                        for (IDictBean item : dictBeanList) {
                            if (dictBean.getValue().equals(item.getValue().toString())) {
                                //如果是当前查询的dictKey则把查到的数据拷贝到dictBean
                                 dictBean = item;
                            } else {
                                //如果不是当前查询的key则缓存对应的DictBean
                                cache.cacheDictBean(item, getCacheKey(item));
                            }
                        }
                    }
                }

                //缓存当前dictKey对应的bean并返回实际对象
                cache.cacheDictBean(dictBean, cacheKey);
            } else {
                dictBean = cacheDictBean;
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("字典转换异常", e);
        }
        if (StringUtils.isBlank(dictBean.getLabel())) {
            throw new IllegalArgumentException("字典" + dictBean.getDictKey() + "找不到对应的value:" + value);
        }
        return dictBean;
    }

    /**
     * 获取缓存key
     *
     * @param dictBean
     */
    private String getCacheKey(IDictBean dictBean) {
        return dictBean.getDictKey() + "_" + dictBean.getValue();
    }

}