package com.aip.usercenter.service;

import com.aip.usercenter.pojo.domain.User;
import com.aip.usercenter.pojo.dto.UserLoginDTO;
import com.aip.usercenter.pojo.dto.UserRegisterDTO;
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
     * @name userRegister 用户注册
     * @param registerDTO 用户注册信息
     * @return long 新用户ID
     */
    Long userRegister(UserRegisterDTO registerDTO);

    /**
     * 用于用户登录服务
     * @author Aganippe
     * @date 2023/10/16
     * @name doLogin
     * @param userLoginDTO 用户登录信息
     * @param request r
     * @return com.aip.usercenter.bean.User
     */
    User userLogin(UserLoginDTO userLoginDTO, HttpServletRequest request);

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
