package com.aip.usercenter.common;

import lombok.Getter;

/**
 * @author Aip
 * @version 1.0
 * @date 2023/11/8  18:07
 * @description 全局错误码
 */
@Getter
public enum ErrorCode {
    /**
     * 成功
     */
    SUCCESS(20000,"success",""),
    /**
     * 请求参数错误
     */
    PARAMS_ERROR(40000, "请求参数错误", ""),
    /**
     * 请求错误
     */
    NULL_ERROR(40001, "请求数据为空", ""),
    /**
     * 登录错误
     */
    NOT_LOGIN(40002, "未登录", ""),
    /**
     * 权限错误
     */
    NO_AUTH(40101, "无权限", ""),
    /**
     * 非法请求
     */
    ILLEGAL_REQUEST(40300, "非法请求", ""),
    /**
     * 系统异常
     */
    SYSTEM_ERROR(50000,"系统内部异常","");

    /**
     * 状态码
     */
    private final int code;

    /**
     * 状态码信息
     */
    private final String message;

    /**
     * 状态码详情描述
     */
    private final String description;

    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }
}
