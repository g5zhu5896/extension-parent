package com.easy.demo.entity;

import com.easy.core.serializer.FilePathSerializer;
import com.easy.demo.dict.UserGenderDict;
import com.easy.demo.dict.UserSourceDict;
import com.easy.demo.enums.UserIsEnableEnum;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zzz
 */
@Data
@ApiModel
public class User {
    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("name")
    private String name;
    @ApiModelProperty("年龄")
    private Integer age;
    @ApiModelProperty(UserIsEnableEnum.description)
    private UserIsEnableEnum isEnable;
    @ApiModelProperty("来源")
    private UserSourceDict source;
    @ApiModelProperty("来源")
    private UserGenderDict gender;
    @JsonSerialize(using = FilePathSerializer.class)
    @ApiModelProperty("头像")
    private String headUrl;
}
