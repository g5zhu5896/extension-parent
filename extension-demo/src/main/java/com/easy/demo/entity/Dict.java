package com.easy.demo.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 *
 */
@Data
@ApiModel
public class Dict {
    private Long id;
    private String label;
    private String value;
    private String dictKey;

}
