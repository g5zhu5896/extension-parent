package com.easy.core.dict.strategy;

import com.easy.core.dict.bean.IDictBean;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.HashMap;
import java.util.Map;

/**
 * 基于单次接口请求进行缓存
 *
 * @author zzz
 */
public class HttpRequestDictCacheStrategy implements DictCacheStrategy {

    @Override
    public IDictBean cacheDictBean(IDictBean dictBean, String cacheKey) {
        //将dictBean放入缓存
        Map<String, IDictBean> cache = getCache();
        if (cache != null) {
            cache.put(cacheKey, dictBean);
        }
        return dictBean;
    }

    @Override
    public IDictBean getFromCache(String cacheKey) {
        Map<String, IDictBean> cache = getCache();
        if (cache != null) {
            return cache.get(cacheKey);
        } else {
            return null;
        }
    }

    private Map<String, IDictBean> getCache() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            Object dictCache = requestAttributes.getAttribute("dictCache", RequestAttributes.SCOPE_REQUEST);
            if (dictCache == null) {
                dictCache = new HashMap<String, IDictBean>();
                requestAttributes.setAttribute("dictCache", dictCache, RequestAttributes.SCOPE_REQUEST);
            }
            return (Map<String, IDictBean>) dictCache;
        } else {
            return null;
        }
    }
}
