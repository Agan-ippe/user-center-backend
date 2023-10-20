package com.aip.usercenter.contant;

/**
 * @author Aip
 * @version 1.0
 * @date 2023/10/20  17:16
 * @description 用户常量
 */
public interface UserConstant {

    /**
     * 用户登录状态键
     */
    String USER_LOGIN_STATE = "userLoginState";

    /**
     * 默认权限
     */
    int DEFAULT_ROLE = 0;

    /**
     * 管理员权限
     */
    int ADMIN_ROLE = 1;

    /**
     * 用户账号最小长度
     */
    int ACCOUNT_MIN_LENGTH = 6;

    /**
     * 用户账号最大长度
     */
    int ACCOUNT_MAX_LENGTH = 15;

    /**
     * 用户名最小长度
     */
    int USERNAME_MIN_LENGTH = 3;

    /**
     * 用户名最大长度
     */
    int USERNAME_MAX_LENGTH = 10;

    /**
     * 用户密码最小长度长度
     */
    int PASSWORD_MIN_LENGTH = 8;

    /**
     * 用户密码最大长度
     */
    int PASSWORD_MAX_LENGTH = 20;

}
