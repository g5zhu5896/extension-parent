package com.easy.core.converter;

import com.easy.bean.BaseEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * 表单BaseEnum枚举转换
 *
 * @author zzz
 * @date 2020/7/10 14:56
 */
public class BaseEnumConverterFactory implements ConverterFactory<String, BaseEnum> {

    @Override
    public <T extends BaseEnum> Converter<String, T> getConverter(Class<T> targetType) {
        return new BaseEnumConverter(targetType);
    }

    public class BaseEnumConverter<String, T extends BaseEnum> implements Converter<String, BaseEnum> {

        private Class<T> type;

        public BaseEnumConverter(Class<T> type) {
            this.type = type;
        }

        @Override
        public BaseEnum convert(String source) {
            return BaseEnum.getEnum(type, source);
        }
    }
}
