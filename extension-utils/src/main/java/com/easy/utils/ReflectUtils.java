package com.easy.utils;

import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 反射工具类
 *
 * @author zzz
 * @date 2020/9/9 11:17
 */
@Slf4j
public class ReflectUtils {
    /**
     * <p>
     * 反射对象获取父类泛型
     * </p>
     *
     * @param clazz 对象
     * @param index 泛型所在位置
     * @return Class
     */
    public static Class<?> getSuperClassGenericType(final Class<?> clazz, final int index) {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            log.warn(String.format("Warn: %s's superclass not ParameterizedType", clazz.getSimpleName()));
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            log.warn(String.format("Warn: Index: %s, Size of %s's Parameterized Type: %s .", index,
                    clazz.getSimpleName(), params.length));
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            log.warn(String.format("Warn: %s not set the actual class on superclass generic parameter",
                    clazz.getSimpleName()));
            return Object.class;
        }
        return (Class<?>) params[index];
    }

    /**
     * <p>
     * 反射对象获取接口泛型
     * </p>
     *
     * @param clazz          对象
     * @param interfaceClass 想获取的接口的class
     * @param index          泛型所在位置
     * @return Class
     */
    public static Class<?> getInterfaceClassGenericType(final Class<?> clazz, Class<?> interfaceClass, final int index) {
        Type[] genericInterfaces = clazz.getGenericInterfaces();
        for (Type genType : genericInterfaces) {
            if (!(genType instanceof ParameterizedType)) {
                log.warn(String.format("Warn: %s's superclass not ParameterizedType", clazz.getSimpleName()));
                return Object.class;
            }
            ParameterizedType parameterGenType = (ParameterizedType) genType;
            if (parameterGenType.getRawType().equals(interfaceClass)) {
                Type[] params = parameterGenType.getActualTypeArguments();
                if (index >= params.length || index < 0) {
                    log.warn(String.format("Warn: Index: %s, Size of %s's Parameterized Type: %s .", index,
                            clazz.getSimpleName(), params.length));
                    return Object.class;
                }
                if (!(params[index] instanceof Class)) {
                    log.warn(String.format("Warn: %s not set the actual class on superclass generic parameter",
                            clazz.getSimpleName()));
                    return Object.class;
                }
                return (Class<?>) params[index];
            }
        }
        log.warn(String.format("Warn: %s without interface", clazz.getSimpleName()));
        return Object.class;
    }

    /**
     * <p>
     * 反射对象修改私有字段
     * </p>
     *
     * @param obj   对象
     * @param field 字段名称
     * @param value 字段值
     */
    public static void setPrivateField(final Object obj, final String field, Object value) {
        try {
            Field declaredField = obj.getClass().getDeclaredField(field);
            declaredField.setAccessible(true);
            declaredField.set(obj, value);
        } catch (Exception e) {
            throw new RuntimeException("反射赋值失败", e);
        }
    }

    /**
     * <p>
     * 创建对象
     * </p>
     *
     * @param clazz  对象
     * @param params 构造方法参数
     * @return Class
     */
    public static <T> T newInstance(final Class<T> clazz, Object... params) {
        try {
            Class[] parameterTypes = {};
            if (ArrayUtils.isNotEmpty(params)) {
                parameterTypes = new Class[params.length];
                for (int i = 0; i < params.length; i++) {
                    parameterTypes[i] = params[i].getClass();
                }
            }
            Constructor<?> constructor = clazz.getDeclaredConstructor(parameterTypes);
            constructor.setAccessible(true);
            T instance = (T) constructor.newInstance(params);
            return instance;
        } catch (Throwable ex) {
            throw new IllegalArgumentException("Cannot instantiate " + clazz + " : " + params, ex);
        }
    }

    public static <T> T getFieldValue(Object context, String metadataAccessor) {
        try {
            Field field = context.getClass().getDeclaredField(metadataAccessor);
            field.setAccessible(true);
            T o = (T) field.get(context);
            return o;
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException("找不到字段", e);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("没有权限", e);
        }

    }
}
