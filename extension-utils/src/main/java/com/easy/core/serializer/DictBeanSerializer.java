package com.easy.core.serializer;

import com.easy.bean.EasyProperties;
import com.easy.core.dict.bean.IDictBean;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * DictBean序列化
 *
 * @author zzz
 * @date 2021年6月9日15:28:01
 */
public class DictBeanSerializer extends JsonSerializer<IDictBean> {
    @Override
    public void serialize(IDictBean value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeObject(value.getValue());
        gen.writeStringField(EasyProperties.buildDictFieldName(gen.getOutputContext().getCurrentName()), value.getLabel());
    }
}
