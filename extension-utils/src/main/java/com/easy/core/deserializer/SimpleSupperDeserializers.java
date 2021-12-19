package com.easy.core.deserializer;

import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import com.fasterxml.jackson.databind.type.*;

/**
 * 让Type可以根据自己的父类链或父接口链获取Deserializers
 *
 * @author zzz
 * @date 2021年6月21日11:42:13
 */
public class SimpleSupperDeserializers extends SimpleDeserializers {
    private static final long serialVersionUID = 1L;

    @Override
    public JsonDeserializer<?> findArrayDeserializer(ArrayType type,
                                                     DeserializationConfig config, BeanDescription beanDesc,
                                                     TypeDeserializer elementTypeDeserializer, JsonDeserializer<?> elementDeserializer)
            throws JsonMappingException {
        return _find(type);
    }

    @Override
    public JsonDeserializer<?> findBeanDeserializer(JavaType type,
                                                    DeserializationConfig config, BeanDescription beanDesc)
            throws JsonMappingException {
        return _find(type);
    }

    @Override
    public JsonDeserializer<?> findCollectionDeserializer(CollectionType type,
                                                          DeserializationConfig config, BeanDescription beanDesc,
                                                          TypeDeserializer elementTypeDeserializer,
                                                          JsonDeserializer<?> elementDeserializer)
            throws JsonMappingException {
        return _find(type);
    }

    @Override
    public JsonDeserializer<?> findCollectionLikeDeserializer(CollectionLikeType type,
                                                              DeserializationConfig config, BeanDescription beanDesc,
                                                              TypeDeserializer elementTypeDeserializer,
                                                              JsonDeserializer<?> elementDeserializer)
            throws JsonMappingException {
        return _find(type);
    }

    @Override
    public JsonDeserializer<?> findEnumDeserializer(Class<?> type,
                                                    DeserializationConfig config, BeanDescription beanDesc)
            throws JsonMappingException {
        if (_classMappings == null) {
            return null;
        }
        JsonDeserializer<?> deser = _classMappings.get(new ClassKey(type));
        if (deser == null) {
            deser = _findSupper(type);
        }
        return deser;
    }

    @Override
    public JsonDeserializer<?> findTreeNodeDeserializer(Class<? extends JsonNode> nodeType,
                                                        DeserializationConfig config, BeanDescription beanDesc)
            throws JsonMappingException {
        if (_classMappings == null) {
            return null;
        }
        return _classMappings.get(new ClassKey(nodeType));
    }

    @Override
    public JsonDeserializer<?> findReferenceDeserializer(ReferenceType refType,
                                                         DeserializationConfig config, BeanDescription beanDesc,
                                                         TypeDeserializer contentTypeDeserializer, JsonDeserializer<?> contentDeserializer)
            throws JsonMappingException {
        // 21-Oct-2015, tatu: Unlikely this will really get used (reference types need more
        //    work, simple registration probably not sufficient). But whatever.
        return _find(refType);
    }

    @Override
    public JsonDeserializer<?> findMapDeserializer(MapType type,
                                                   DeserializationConfig config, BeanDescription beanDesc,
                                                   KeyDeserializer keyDeserializer,
                                                   TypeDeserializer elementTypeDeserializer,
                                                   JsonDeserializer<?> elementDeserializer)
            throws JsonMappingException {
        return _find(type);
    }

    @Override
    public JsonDeserializer<?> findMapLikeDeserializer(MapLikeType type,
                                                       DeserializationConfig config, BeanDescription beanDesc,
                                                       KeyDeserializer keyDeserializer,
                                                       TypeDeserializer elementTypeDeserializer,
                                                       JsonDeserializer<?> elementDeserializer)
            throws JsonMappingException {
        return _find(type);
    }

    private final JsonDeserializer<?> _find(JavaType type) {
        try {
            if (_classMappings == null) {
                return null;
            }
            JsonDeserializer<?> jsonDeserializer = _classMappings.get(new ClassKey(type.getRawClass()));
            if (jsonDeserializer == null) {
                jsonDeserializer = _findSupper(type.getRawClass());
            }
            return jsonDeserializer;
        } catch (Exception e) {
            RuntimeException customException = new RuntimeException("由于如果此处异常往上抛可能会被spring捕获且不输出,然后spring" +
                    "不会继续绑定实体参数,导致实体后面的参数都是null且看不到错误,所以主动打印异常日志方便排查,并异常时返回null,方便后面参数的绑定", e);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 递归寻找父类的 JsonDeserializer
     *
     * @param childType
     * @return
     */
    private final JsonDeserializer<?> _findSupper(Class<?> childType) {
        JsonDeserializer<?> jsonDeserializer = _findInterface(childType);
        if (jsonDeserializer != null) {
            return jsonDeserializer;
        }

        Class<?> type = childType.getSuperclass();
        if (type == null || type.equals(Object.class)) {
            return null;
        }
        jsonDeserializer = _classMappings.get(new ClassKey(type));
        if (jsonDeserializer == null) {
            /*递归父类中找*/
            jsonDeserializer = _findSupper(type);
        }
        return jsonDeserializer;
    }

    /**
     * 递归寻找父接口的 JsonDeserializer
     *
     * @param type
     * @return
     */
    private final JsonDeserializer<?> _findInterface(Class<?> type) {

        /*从接口中找JsonDeserializer*/
        Class<?>[] interfaces = type.getInterfaces();
        if (ArrayUtils.isNotEmpty(interfaces)) {
            for (Class<?> anInterface : interfaces) {
                JsonDeserializer<?> jsonDeserializer = _classMappings.get(new ClassKey(anInterface));
                if (jsonDeserializer != null) {
                    return jsonDeserializer;
                }
                jsonDeserializer = _findInterface(anInterface);
                if (jsonDeserializer != null) {
                    return jsonDeserializer;
                }
            }
        }
        return null;
    }
}
