package com.easy.core.serializer;

import com.easy.bean.BaseEnum;
import com.easy.bean.EasyProperties;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * BaseEnum序列化
 *
 * @author zzz
 * @date 2020/7/13 18:03
 */
public class BaseEnumSerializer extends JsonSerializer<BaseEnum> {
    @Override
    public void serialize(BaseEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeObject(value.getValue());
        gen.writeStringField(EasyProperties.buildEnumFieldName(gen.getOutputContext().getCurrentName()), value.getLabel());
    }
}
