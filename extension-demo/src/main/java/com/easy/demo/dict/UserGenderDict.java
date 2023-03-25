package com.easy.demo.dict;


import com.easy.core.dict.bean.DictBean;

public class UserGenderDict extends DictBean<Integer> {

    public UserGenderDict() {
        setDictKey("GENDER");
    }

    private static UserGenderDict create(Integer value) {
        return create(value, UserGenderDict.class);
    }
}
