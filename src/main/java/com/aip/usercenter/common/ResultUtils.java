package com.aip.usercenter.common;

import com.sun.istack.internal.NotNull;

/**
 * @author Aip
 * @version 1.0
 * @date 2023/11/7  13:27
 * @description 返回工具类
 */
public class ResultUtils {

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(200, data, "success");
    }

    /**
     * 失败
     *
     * @param errorCode 失败状态码
     * @return com.aip.usercenter.common.BaseResponse
     * @author Aganippe
     * @date 2023/11/9
     * @name error 错误
     */
    public static BaseResponse error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }

    public static BaseResponse error(int code, String message, String description) {
        return new BaseResponse<>(code, message, description);
    }

    public static BaseResponse error(ErrorCode errorCode, String description) {
        return new BaseResponse<>(errorCode.getCode(), description);
    }

    public static BaseResponse error(ErrorCode errorCode, String message, String description) {
        return new BaseResponse<>(errorCode.getCode(), message, description);
    }

}
