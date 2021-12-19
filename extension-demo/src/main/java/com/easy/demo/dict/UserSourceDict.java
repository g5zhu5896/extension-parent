package com.easy.demo.dict;


import com.easy.core.dict.bean.DictBean;

public class UserSourceDict extends DictBean<String> {
    /**
     * 如果代码需要使用到可以这样创建枚举字典提供使用,尽量不要代码直接字典值
     * 字典表是有 jd tx bd 三个字典项的。
     */
    public static final UserSourceDict JD = create("tx");

    public UserSourceDict() {
        setDictKey("USER_SOURCE");
    }

    private static UserSourceDict create(String value) {
        return create(value, UserSourceDict.class);
    }
}
