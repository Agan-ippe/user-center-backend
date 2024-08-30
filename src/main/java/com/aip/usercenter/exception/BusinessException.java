package com.aip.usercenter.exception;

import com.aip.usercenter.common.BaseResponse;
import com.aip.usercenter.common.ErrorCode;
import lombok.Data;

/**
 * @author Aip
 * @version 1.0
 * @date 2023/11/9  18:09
 * @description 自定义业务异常类
 */
@Data
public class BusinessException extends RuntimeException {
    private final int code;
    private final String description;

    public BusinessException(String message, int code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = errorCode.getDescription();
    }

    public BusinessException(ErrorCode errorCode, String description) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

}
