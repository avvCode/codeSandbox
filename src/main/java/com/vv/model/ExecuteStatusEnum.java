package com.vv.model;

import lombok.Getter;

/**
 * @author vv
 */
@Getter
public enum ExecuteStatusEnum {
    COMPILER("编译错误",2),
    SUCCESS("成功",1),
    SUBMIT_CODE_ERROR("提交代码错误",3);
    ;

    ExecuteStatusEnum(String msg, Integer code) {
        this.msg = msg;
        this.code = code;
    }

    private final String msg;
    private final Integer code;
}
