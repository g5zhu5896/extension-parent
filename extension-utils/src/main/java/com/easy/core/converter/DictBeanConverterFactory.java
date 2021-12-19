package com.easy.core.converter;


import com.easy.core.dict.bean.IDictBean;
import com.easy.core.dict.service.IDictService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * 表单IDictBean枚举转换
 *
 * @author zzz
 * @date 2020/7/10 14:56
 */
public class DictBeanConverterFactory implements ConverterFactory<String, IDictBean> {

    @Override
    public <T extends IDictBean> Converter<String, T> getConverter(Class<T> targetType) {
        return new DictBeanConverter(targetType);
    }

    public class DictBeanConverter<T extends IDictBean> implements Converter<String, T> {

        private Class<? extends IDictBean> type;

        public DictBeanConverter(Class<? extends IDictBean> type) {
            this.type = type;
        }

        @Override
        public T convert(String source) {
            return (T) IDictService.getInstance().getDictBean(source, type);
        }
    }
}
