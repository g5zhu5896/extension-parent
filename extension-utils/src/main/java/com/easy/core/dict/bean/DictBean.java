package com.easy.core.dict.bean;

import com.easy.utils.ReflectUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zzz
 * @date 2021/6/4 17:53
 */
@Getter
@Setter
@EqualsAndHashCode(of = "value")
@ApiModel
public class DictBean<T> implements IDictBean<T> {


    @ApiModelProperty("字典值")
    private T value;

    @ApiModelProperty(value = "字典文本内容", hidden = true)
    private String label;

    @ApiModelProperty(value = "字典key", hidden = true)
    private String dictKey;

    public final static <U extends DictBean<T>, T> U create(T value, Class<U> type) {
        U dictBean = ReflectUtils.newInstance(type);
        dictBean.setValue(value);
        return dictBean;
    }
}
