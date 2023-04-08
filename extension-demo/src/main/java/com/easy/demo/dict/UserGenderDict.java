package com.easy.demo.dict;


import com.easy.core.dict.bean.TableDictBean;

public class UserGenderDict extends TableDictBean<Integer> {

    public UserGenderDict() {
        super("GENDER", "select label,value from dict where dict_key = 'GENDER'");
    }

    private static UserGenderDict create(Integer value) {
        return create(value, UserGenderDict.class);
    }
}
