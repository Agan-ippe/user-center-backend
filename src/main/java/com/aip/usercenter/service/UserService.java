package com.aip.usercenter.service;

import com.aip.usercenter.bean.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

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

    /**
     * 用于用户登录服务
     * @author Aganippe
     * @version v1.0
     * @date 2023/10/16
     * @name doLogin
     * @param
     * @param userAccount 用户账号
     * @param userPassword 用户密码
     * @param request r
     * @return com.aip.usercenter.bean.User
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用于用户脱敏
     * @author Aganippe
     * @version v1.0
     * @date 2023/10/23
     * @name getEncryptedUser
     * @param
     * @param originUser 源用户
     * @return com.aip.usercenter.bean.User
     */
    User getEncryptedUser(User originUser);

    /**
     * 退出登录
     * @author Aganippe
     * @version v1.0
     * @date 2023/11/6
     * @name userLogout
     * @return int
     */
    Integer userLogout(HttpServletRequest request);
}
