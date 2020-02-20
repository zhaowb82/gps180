package com.gps.db.enums;

import lombok.Getter;

/**
 * 总部枚举
 * @author haibin.tang
 * @create 2019-05-16 3:14 PM
 **/
@Getter
public enum Repository {

    HQ("1", "总部");

    private String code;

    private String desc;

    Repository(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
