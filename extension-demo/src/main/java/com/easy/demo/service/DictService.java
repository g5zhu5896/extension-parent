package com.easy.demo.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.easy.demo.entity.Dict;

import java.util.List;
import java.util.Map;

public interface DictService extends IService<Dict> {

    List<Dict> list(String dictKey);

    List<Map<String,Object>> selectBySql(String sql);
}
