package com.easy.core.deserializer;

import com.easy.bean.BaseEnum;
import com.easy.utils.StringUtils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.BeanUtils;

import java.io.IOException;

/**
 * BaseEnum反序列化
 *
 * @author zzz
 * @date 2020/7/13 18:03
 */
public class BaseEnumDeserializer extends JsonDeserializer<BaseEnum> {

    @Override
    public BaseEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
        String currentName = p.currentName();
        Object currentValue = p.getCurrentValue();
        Class findPropertyType = null;
        findPropertyType = BeanUtils.findPropertyType(currentName, currentValue.getClass());
        if (findPropertyType == null) {
            throw new RuntimeException("在" + currentValue.getClass() + "实体类中找不到" + currentName + "字段");
        }
        String asText = null;
        asText = node.asText();
        if (StringUtils.isBlank(asText)) {
            return null;
        }

        if (BaseEnum.class.isAssignableFrom(findPropertyType)) {
            BaseEnum valueOf = null;
            if (StringUtils.isNotBlank(asText)) {
                valueOf = BaseEnum.getEnum(findPropertyType, asText);
            }
            if (valueOf != null) {
                return valueOf;
            }

        }

        return (BaseEnum) Enum.valueOf(findPropertyType, asText);
    }
}
