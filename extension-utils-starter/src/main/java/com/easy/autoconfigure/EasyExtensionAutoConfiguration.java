package com.easy.autoconfigure;


import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.easy.bean.BaseEnum;
import com.easy.bean.EasyProperties;
import com.easy.core.SpringUtils;
import com.easy.core.converter.BaseEnumConverterFactory;
import com.easy.core.converter.DictBeanConverterFactory;
import com.easy.core.deserializer.BaseEnumDeserializer;
import com.easy.core.deserializer.DictBeanDeserializer;
import com.easy.core.deserializer.SimpleSupperDeserializers;
import com.easy.core.dict.bean.IDictBean;
import com.easy.core.serializer.BaseEnumSerializer;
import com.easy.core.serializer.DictBeanSerializer;
import com.easy.core.swagger.plugin.ExtensionSwaggerPlugin;
import com.easy.core.typehandler.DictBeanHandler;
import com.easy.core.typehandler.EnumTypeHandler;
import com.easy.utils.StringUtils;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.swagger2.web.Swagger2Controller;

import java.util.Set;
import java.util.TimeZone;

/**
 *
 */
@Configuration
@EnableConfigurationProperties(EasyProperties.class)
@Import(SpringUtils.class)
@Slf4j
public class EasyExtensionAutoConfiguration {

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addFormatters(FormatterRegistry registry) {
                registry.addConverterFactory(new BaseEnumConverterFactory());
                registry.addConverterFactory(new DictBeanConverterFactory());
            }
        };
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonObjectMapperCustomization() {
        BaseEnumSerializer baseEnumSerializer = new BaseEnumSerializer();
        DictBeanSerializer dictBeanSerializer = new DictBeanSerializer();
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder.timeZone(TimeZone.getDefault()).
                serializerByType(BaseEnum.class, baseEnumSerializer).
                serializerByType(IDictBean.class, dictBeanSerializer).
                serializerByType(Long.class, ToStringSerializer.instance).build();
    }

    @Bean
    public Module module() {
        SimpleModule simpleModule = new SimpleModule();
        /*添加把map key 的序列化器(首字母大写的驼峰值),目前map put是什么就是什么 不强转大写*/
//        simpleModule.addKeySerializer(String.class, new MapStringKeySerialize());

        //让字典Bean可以找到父类的Deserializers,这样就可以不用为了每个DictBean子类都注册Deserializers
        SimpleSupperDeserializers deserializers = new SimpleSupperDeserializers();
//        SimpleDeserializers deserializers = new SimpleDeserializers();
        DictBeanDeserializer dictBeanDeserializer = new DictBeanDeserializer();
        deserializers.addDeserializer(IDictBean.class, dictBeanDeserializer);

        BaseEnumDeserializer baseEnumDeserializer = new BaseEnumDeserializer();
        deserializers.addDeserializer(BaseEnum.class, baseEnumDeserializer);

        simpleModule.setDeserializers(deserializers);
        return simpleModule;

    }

    @ConditionalOnClass(Swagger2Controller.class)
    @Configuration
    public class SwaggerExtensionConfig {
        @Bean
        @ConditionalOnMissingBean(ExtensionSwaggerPlugin.class)
        public ExtensionSwaggerPlugin baseEnumPropertyPlugin() {
            ExtensionSwaggerPlugin baseEnumPlugin = new ExtensionSwaggerPlugin();
            return baseEnumPlugin;
        }
    }

    @Value("${mybatis-plus.type-enums-package:}")
    private String typeEnumsPackage;

    /**
     * 为字典Bean注册TypeHandler
     *
     * @param event
     */
    @EventListener
    public void dictBeanTypeHandlerRegister(ContextRefreshedEvent event) {

        TypeHandlerRegistry typeHandlerRegistry = SqlHelper.FACTORY.getConfiguration().getTypeHandlerRegistry();

        //如果没有配置mybatis plus的枚举转换则注册自己的枚举TypeHandler
        if (StringUtils.isBlank(typeEnumsPackage)) {
            typeHandlerRegistry.register(BaseEnum.class, EnumTypeHandler.class);
        }

        if (StringUtils.isBlank(EasyProperties.DICT_BEAN_PACKAGE)) {
            log.warn("yml没有配置easy.dictBeanPackage,如需mybatis字典的转换,请自己主动注册TypeHandler");
            return;
        }
        String[] dictBeanPackageArr = StringUtils.split(EasyProperties.DICT_BEAN_PACKAGE, ",");
        for (String dictBeanPackage : dictBeanPackageArr) {
            ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
            scanner.addIncludeFilter(new AssignableTypeFilter(IDictBean.class));
            Set<BeanDefinition> beanDefinitionSet = scanner.findCandidateComponents(dictBeanPackage);

            for (BeanDefinition beanDefinition : beanDefinitionSet) {
                String beanClassName = beanDefinition.getBeanClassName();
                Class<?> clazz = null;
                try {
                    clazz = Class.forName(beanClassName);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(beanClassName + "找不到", e);
                }
                typeHandlerRegistry.register(clazz, DictBeanHandler.class);
            }
        }


    }

}
