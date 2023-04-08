package com.easy.core.dict.service;


import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.easy.core.dict.bean.IDictBean;
import com.easy.core.dict.bean.TableDictBean;
import com.easy.core.dict.strategy.DictCacheStrategy;
import com.easy.core.dict.strategy.HttpRequestDictCacheStrategy;
import com.easy.utils.ReflectUtils;
import com.easy.utils.StringUtils;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * @author zzz
 * @date 2021年7月2日09:53:19
 */
public abstract class AbstractDictService implements IDictService {

    private final Map<Class<? extends IDictBean>, IDictBean> DICT_KEY_MAP = Maps.newHashMap();

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
        IDictBean baseDictBean = null;
        try {
            baseDictBean = getTempDictBean(type);
            //缓存用的key
            String cacheKey = getCacheKey(baseDictBean.getDictKey(), value);
            IDictBean cacheDictBean = cache.getFromCache(cacheKey);
            if (cacheDictBean == null) {
                List<IDictBean> dictBeanList = queryBeanList(baseDictBean);
                if (CollectionUtils.isNotEmpty(dictBeanList)) {
                    for (IDictBean item : dictBeanList) {
                        if (value.equals(item.getValue().toString())) {
                            //如果是当前查询的dictKey则把查到的数据拷贝到dictBean
                            baseDictBean = item;
                        } else {
                            //如果不是当前查询的key则缓存对应的DictBean
                            cache.cacheDictBean(item, getCacheKey(item.getDictKey(), item.getValue()));
                        }
                    }
                }

                //缓存当前dictKey对应的bean并返回实际对象
                cache.cacheDictBean(baseDictBean, cacheKey);
            } else {
                baseDictBean = cacheDictBean;
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("字典转换异常", e);
        }
        if (StringUtils.isBlank(baseDictBean.getLabel())) {
            throw new IllegalArgumentException("字典" + baseDictBean.getDictKey() + "找不到对应的value:" + value);
        }
        return baseDictBean;
    }

    private List<IDictBean> queryBeanList(IDictBean dictBean) {
        if (TableDictBean.class.isInstance(dictBean)) {
            TableDictBean tableDict = (TableDictBean) dictBean;
            return queryBeanListBySql(tableDict);
        } else {
            return queryBeanListByDictKey(dictBean);
        }
    }

    /**
     * 获取缓存key
     *
     * @param dictKey
     * @param value
     */
    private String getCacheKey(String dictKey, Object value) {
        return dictKey + "_" + value;
    }

    /**
     * 获取字典实体
     *
     * @param type
     */
    private IDictBean getTempDictBean(Class<? extends IDictBean> type) {
        IDictBean dict = DICT_KEY_MAP.get(type);
        if (dict == null) {
            dict = ReflectUtils.newInstance(type);
            DICT_KEY_MAP.put(type, dict);
        }
        return dict;
    }
}