package com.easy.core.dict.bean;

import lombok.Getter;

/**
 * @author zzz
 * @date 2021/6/4 17:53
 */
@Getter
public class TableDictBean<T> extends DictBean<T> {

    private String sql;

    public TableDictBean(String dictKey, String sql) {
        super(dictKey);
        this.sql = sql;
    }

}
