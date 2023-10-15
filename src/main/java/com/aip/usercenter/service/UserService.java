package com.aip.usercenter.service;

import com.aip.usercenter.bean.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author aip
 * @date 2023/10/14
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册功能
     * @author Aganippe
     * @version v1.0
     * @name userRegister 用户注册
     * @param
     * @param userAccount 用户账号
     * @param userPassword 用户密码
     * @param checkPassword 确认密码
     * @notice
     * @return long 新用户ID
     */
    Long userRegister(String userAccount, String userPassword, String checkPassword);

}
