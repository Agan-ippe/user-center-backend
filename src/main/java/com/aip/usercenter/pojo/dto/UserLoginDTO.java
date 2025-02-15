package com.aip.usercenter.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Aip
 * @Date 2025/02/15   17:29
 * @Version 1.0
 * @Description 用户登录数据模型
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 用户密码
     */
    private String userPassword;
}
