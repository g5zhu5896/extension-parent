package com.easy.core.swagger.plugin;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.easy.bean.BaseEnum;
import com.easy.bean.EasyProperties;
import com.easy.core.dict.bean.IDictBean;
import com.easy.utils.ReflectUtils;
import com.easy.utils.StringUtils;
import com.fasterxml.classmate.types.ResolvedObjectType;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.service.AllowableListValues;
import springfox.documentation.service.AllowableValues;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
import springfox.documentation.spi.service.ExpandedParameterBuilderPlugin;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.ParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.spi.service.contexts.ParameterContext;
import springfox.documentation.spi.service.contexts.ParameterExpansionContext;
import springfox.documentation.spring.web.readers.parameter.ModelAttributeParameterMetadataAccessor;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * swagger参数返回相关配置
 * 主要是配置文档参数和result BaseEnum允许值列表
 */
public class ExtensionSwaggerPlugin implements ExpandedParameterBuilderPlugin, ModelPropertyBuilderPlugin, ParameterBuilderPlugin, OperationBuilderPlugin {

    /**
     * 返回ApiModel参数
     *
     * @param context
     */
    @Override
    public void apply(ModelPropertyContext context) {
        if (context.getBeanPropertyDefinition().isPresent()) {
//            Optional<ApiModelProperty> annotation = Annotations.findPropertyAnnotation(
//                    context.getBeanPropertyDefinition().get(),
//                    ApiModelProperty.class);
//            if (annotation.isPresent()) {
            final Class<?> rawPrimaryType = context.getBeanPropertyDefinition().get().getRawPrimaryType();
            if (BaseEnum.class.isAssignableFrom(rawPrimaryType)) {
                AllowableListValues allowablValues = allowableValues(rawPrimaryType);
//                final ResolvedType resolvedType = context.getResolver().resolve(String.class);
                context.getBuilder().allowableValues(allowablValues);
            }

            if (EasyProperties.DICT_SWAGGER_SUPPORT) {
                //@RequestBodey 字典字段格式为 {dict:""} 而不是 {"dict":{"value":""}
                if (IDictBean.class.isAssignableFrom(rawPrimaryType)) {
                    context.getBuilder().type(ResolvedObjectType.create(String.class, null, null, null));
                }
            }
//            }
        }


    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return true;
    }

    /**
     * 接口查询参数
     * 接口方法直接定义参数
     *
     * @param context
     */
    @Override
    public void apply(ParameterContext context) {
        Class<?> type = context.resolvedMethodParameter().getParameterType().getErasedType();
        if (BaseEnum.class.isAssignableFrom(type)) {
            ParameterBuilder parameterBuilder = context.parameterBuilder();
            parameterBuilder.allowableValues(allowableValues(type));
        }
    }


    @Override
    public void apply(OperationContext context) {
//        Map<String, List<String>> map = new HashMap<>();
//        List<ResolvedMethodParameter> parameters = context.getParameters();
    }

    private boolean isSupportGetField = true;

    /**
     * 接口查询参数为ApiModel类
     *
     * @param context
     */
    @Override
    public void apply(ParameterExpansionContext context) {
        Class<?> type = null;
        //2.9.2版本 getField方法过时,不支持调用
        if (isSupportGetField) {
            try {
                if (EasyProperties.DICT_SWAGGER_SUPPORT) {
                    // get和表单等查询时如果是一个 ApiModel 则字典字段展示的是 dict 而不是 dict.value
                    if (IDictBean.class.isAssignableFrom(context.getField().getDeclaringType().getErasedType())) {
                        if (StringUtils.isNotBlank(context.getParentName())) {
                            context.getParameterBuilder().name(context.getParentName());
                            return;
                        }
                    }
                }
                type = context.getField().getType().getErasedType();


            } catch (Exception e) {
                isSupportGetField = false;
            }
        }
        //getField过时后的处理方式
        if (type == null) {
            if (EasyProperties.DICT_SWAGGER_SUPPORT) {
                ModelAttributeParameterMetadataAccessor metadataAccessor = ReflectUtils.getFieldValue(context, "metadataAccessor");
                List<AnnotatedElement> annotatedElements = ReflectUtils.getFieldValue(metadataAccessor, "annotatedElements");
                if (CollectionUtils.isNotEmpty(annotatedElements)) {
                    Class<?> declaringClass = ((Method) annotatedElements.get(0)).getDeclaringClass();
                    if (IDictBean.class.isAssignableFrom(declaringClass)) {
                        if (StringUtils.isNotBlank(context.getParentName())) {
                            context.getParameterBuilder().name(context.getParentName());
                            return;
                        }
                    }
                }
            }

            type = context.getFieldType().getErasedType();
        }

        if (BaseEnum.class.isAssignableFrom(type)) {
            AllowableValues allowableValues = allowableValues(type);
            context.getParameterBuilder().allowableValues(allowableValues);
        }
    }

    /**
     * 获取BaseEnum 枚举的可能值
     *
     * @param type
     * @return AllowableListValues
     */
    private AllowableListValues allowableValues(Class<?> type) {
        BaseEnum[] values = (BaseEnum[]) type.getEnumConstants();
        List<String> displayValues = Arrays.stream(values).filter(Objects::nonNull).map(baseEnum -> {
            return baseEnum.getValue().toString();
        }).collect(Collectors.toList());
        return new AllowableListValues(displayValues, "LIST");
    }

}