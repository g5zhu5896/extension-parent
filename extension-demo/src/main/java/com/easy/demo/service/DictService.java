package com.easy.demo.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.easy.demo.entity.Dict;

import java.util.List;

public interface DictService extends IService<Dict> {

    List<Dict> list(String dictKey);
}
