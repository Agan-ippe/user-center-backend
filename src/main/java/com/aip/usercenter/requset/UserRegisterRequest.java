package com.aip.usercenter.requset;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Aip
 * @version 1.0
 * @date 2023/10/18  16:35
 * @description 用户注册请求体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = -3007618639582610164L;

    private String userAccount;

    private String userPassword;

    private String checkPassword;
}
