package com.easy.core.deserializer;

import com.easy.utils.StringUtils;
import com.easy.core.dict.bean.IDictBean;
import com.easy.core.dict.service.IDictService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.BeanUtils;

import java.io.IOException;

/**
 * DictBean反序列化
 *
 * @author zzz
 * @date 2021年6月9日15:26:44
 */
public class DictBeanDeserializer extends JsonDeserializer<IDictBean> {

    @Override
    public IDictBean deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
        //当前绑定实体里的json字段名称
        String currentName = p.currentName();
        //当前绑定的实体
        Object currentValue = p.getCurrentValue();
        //当前绑定实体里的json字段类型Class
        Class findPropertyType = null;
        findPropertyType = BeanUtils.findPropertyType(currentName, currentValue.getClass());
        if (findPropertyType == null) {
            throw new RuntimeException("在" + currentValue.getClass() + "实体类中找不到" + currentName + "字段");
        }
        String asText = null;
        asText = node.asText();
        if (StringUtils.isBlank(asText)) {
            asText = node.get("value").asText();
        }
        if (StringUtils.isBlank(asText)) {
            return null;
        }

        IDictBean valueOf = null;
        if (StringUtils.isNotBlank(asText)) {
            valueOf = IDictService.getInstance().getDictBean(asText, findPropertyType);
        }
        if (valueOf == null) {
            throw new RuntimeException(currentName + "值不正确");
        }
        return valueOf;
    }
}
