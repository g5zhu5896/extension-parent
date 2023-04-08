package com.easy.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easy.demo.entity.Dict;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface DictMapper extends BaseMapper<Dict> {
    @Select("${sql}")
    List<Map<String, Object>> selectBySql(String sql);
}
