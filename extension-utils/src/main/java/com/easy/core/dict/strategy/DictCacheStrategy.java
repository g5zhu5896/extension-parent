package com.easy.core.dict.strategy;

import com.easy.core.dict.bean.IDictBean;

/**
 * 字典缓存策略接口
 */
public interface DictCacheStrategy {

    /**
     * 缓存dictBean
     *
     * @param dictBean
     * @param cacheKey
     * @return 返回需要映射到实体对应的Bean
     */
    IDictBean cacheDictBean(IDictBean dictBean, String cacheKey);

    /**
     * 从缓存中获取dictBean
     *
     * @param cacheKey
     * @return IDictBean
     */
    IDictBean getFromCache(String cacheKey);
}
