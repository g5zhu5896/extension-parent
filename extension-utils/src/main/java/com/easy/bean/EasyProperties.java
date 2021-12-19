package com.easy.bean;

import com.easy.utils.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 */
@ConfigurationProperties(prefix = "easy")
public class EasyProperties {
    /**
     * 文件路径url序列化相关配置
     */
    public static String FILE_FIELD_PREFIX = "full";
    public static String FILE_FIELD_SUFFIX = "";

    public static String FILE_URL_PREFIX = "";

    /**
     * 字典序列化相关配置
     */
    public static String DICT_FIELD_PREFIX = "";
    public static String DICT_FIELD_SUFFIX = "Label";

    /**
     * 枚举序列化相关配置
     */
    public static String ENUM_FIELD_PREFIX = "";
    public static String ENUM_FIELD_SUFFIX = "Label";
    /**
     * mybatis 字典类扫描包,配置后会寻找包里的IDictBean子类扫描注册DictBeanHandler
     * 注册后才可以在查询和存储的时候进行dictBean转换,多个用逗号分割
     */
    public static String DICT_BEAN_PACKAGE;

    /**
     * swagger文档是否启用支持字典类型展示处理
     */
    public static boolean DICT_SWAGGER_SUPPORT = false;

    public void setDictSwaggerSupport(boolean dictSwaggerSupport) {
        DICT_SWAGGER_SUPPORT = dictSwaggerSupport;
    }

    public void setFileFieldPrefix(String fileFieldPrefix) {
        FILE_FIELD_PREFIX = fileFieldPrefix;
    }

    public void setFileFieldSuffix(String fileFieldSuffix) {
        FILE_FIELD_SUFFIX = fileFieldSuffix;
    }

    public void setFileUrlPrefix(String fileUrlPrefix) {
        FILE_URL_PREFIX = fileUrlPrefix;
    }

    public void setDictBeanPackage(String dictBeanPackage) {
        DICT_BEAN_PACKAGE = dictBeanPackage;
    }

    public void setDictFieldPrefix(String dictFieldPrefix) {
        DICT_FIELD_PREFIX = dictFieldPrefix;
    }

    public void setDictFieldSuffix(String dictFieldSuffix) {
        DICT_FIELD_SUFFIX = dictFieldSuffix;
    }


    public void setEnumFieldPrefix(String enumFieldPrefix) {
        ENUM_FIELD_PREFIX = enumFieldPrefix;
    }

    public void setEnumFieldSuffix(String enumFieldSuffix) {
        ENUM_FIELD_SUFFIX = enumFieldSuffix;
    }

    public static String buildFileFieldName(String fieldName) {
        if (StringUtils.isNotBlank(FILE_FIELD_PREFIX)) {
            fieldName = StringUtils.upperCaseFirst(fieldName);
        }
        return FILE_FIELD_PREFIX + fieldName + FILE_FIELD_SUFFIX;
    }

    public static String buildDictFieldName(String fieldName) {
        if (StringUtils.isNotBlank(DICT_FIELD_PREFIX)) {
            fieldName = StringUtils.upperCaseFirst(fieldName);
        }
        return DICT_FIELD_PREFIX + fieldName + DICT_FIELD_SUFFIX;
    }

    public static String buildEnumFieldName(String fieldName) {
        if (StringUtils.isNotBlank(ENUM_FIELD_PREFIX)) {
            fieldName = StringUtils.upperCaseFirst(fieldName);
        }
        return ENUM_FIELD_PREFIX + fieldName + ENUM_FIELD_SUFFIX;
    }

    public static String buildUrl(String path) {
        return FILE_URL_PREFIX + path;
    }
}
