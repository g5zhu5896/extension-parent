package com.easy.core.serializer;

import com.easy.utils.StringUtils;
import com.easy.bean.EasyProperties;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * 上传文件路径序列化
 *
 * @author zzz
 * @date 2020/7/13 18:03
 */
public class FilePathSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value);
        if (StringUtils.isNotBlank(value)) {
            if (value.startsWith("http")) {
                gen.writeStringField(EasyProperties.buildFileFieldName(gen.getOutputContext().getCurrentName()), value);
            } else {
                gen.writeStringField(EasyProperties.buildFileFieldName(gen.getOutputContext().getCurrentName()), EasyProperties.buildUrl(value));
            }
        }
    }

}
