package com.easy.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.demo.entity.Dict;
import com.easy.demo.mapper.DictMapper;
import com.easy.demo.service.DictService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author zzz
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    @Override
    public List<Dict> list(String dictKey) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("dict_key", dictKey);
        List<Dict> list = list(wrapper);
        return list;
    }

    @Override
    public List<Map<String, Object>> selectBySql(String sql) {
        return baseMapper.selectBySql(sql);
    }
}
