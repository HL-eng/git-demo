package com.fdmtek.utils;

public enum LoginReturnCode {

    USERNAME_IS_EMPTY(1,"用户名不能为空"),
    PASSWORD_IS_EMPTY(2,"密码不能为空"),
    LOGIN_SUCCESS(3,"登录成功"),
    LOGIN_FAIL(4,"登录失败");

    private Integer code;
    private String message;
    LoginReturnCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
