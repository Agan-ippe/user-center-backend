package com.aip.usercenter.utils;

import com.aip.usercenter.common.BaseResponse;
import com.aip.usercenter.common.ErrorCode;

/**
 * @author Aip
 * @version 1.0
 * @date 2023/11/7  13:27
 * @description 返回工具类
 */
public class ResultUtils {

    private static final Object DATA_NULL = null;

    /**
     * 成功状态码
     * @return com.aip.usercenter.common.BaseResponse<T>
     * @author Aganippe
     * @version v1.0
     * @date 2024/1/4
     * @name success
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(200, data, "success");
    }

    /**
     * 成功状态码
     * @return com.aip.usercenter.common.BaseResponse<T>
     * @author Aganippe
     * @version v1.0
     * @date 2024/1/4
     * @name success
     */
    public static <T> BaseResponse<T> success() {
        return new BaseResponse<>(200, "success");
    }


    /**
     * 失败
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
        return new BaseResponse(code, DATA_NULL, message, description);
    }

    public static BaseResponse error(ErrorCode errorCode, String message, String description) {
        return new BaseResponse(errorCode.getCode(), DATA_NULL, message, description);
    }

    public static BaseResponse error(ErrorCode errorCode, String description) {
        return new BaseResponse(errorCode.getCode(), DATA_NULL, errorCode.getMessage(), description);
    }
}
