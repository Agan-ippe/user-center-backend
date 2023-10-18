package com.aip.usercenter.service.impl;

import com.aip.usercenter.dao.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aip.usercenter.bean.User;
import com.aip.usercenter.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;


/**
 * 用户服务功能实现类
 *
 * @author Aip
 * @version v1.0
 * @date 2023/10/14
 */

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Autowired
    UserMapper userMapper;

    /**
     * 盐值
     */
    private static final String SALT = "Aip";
    /**
     * 用户登录状态键
     */
    private static final String USER_LOGIN_STATE = "userLoginState";

    @Override
    public Long userRegister(String userAccount, String userPassword, String checkPassword) {
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            // TODO 修改为自定义异常
            return -1L;
        }
        if (userAccount.length() < 6) {
            return -1L;
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            return -1L;
        }
        if (!userPassword.equals(checkPassword)) {
            return -1L;
        }

        //使用正则表达式校验用户账号
        //至少包含一个字母和数字
        String regExPassword = "^(?=.*[\\d])(?=.*[a-zA-Z])[a-zA-Z\\d!@#$%^&*_.]{8,20}$";
        String regExAccount = "^[a-zA-Z\\d]{6,18}$";
        if (!userAccount.matches(regExAccount)) {
            return -1L;
        }
        if (!userPassword.matches(regExPassword)) {
            return -1L;
        }

        //判断用户账号是否在数据库中重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            return -1L;
        }

        //加密密码
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        //插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setPwd(encryptPassword);
        //提交用户注册数据
        userMapper.insert(user);
        return user.getId();
    }

    @Override
    public User doLogin(String userAccount, String userPassword, HttpServletRequest request) {
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }
        if (userAccount.length() < 6) {
            return null;
        }
        if (userPassword.length() < 8) {
            return null;
        }
        //使用正则表达式校验用户账号
        //至少包含一个字母和数字
        String regExPassword = "^(?=.*[\\d])(?=.*[a-zA-Z])[a-zA-Z\\d!@#$%^&*_.]{8,20}$";
        String regExAccount = "^[a-zA-Z\\d]{6,18}$";
        if (!userAccount.matches(regExAccount)) {
            return null;
        }
        if (!userPassword.matches(regExPassword)) {
            return null;
        }

        //查询用户登录信息是否符合数据库信息
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        //用户不存在
        if (user == null) {
            log.info("user login failed,user-account can't match user-password");
            return null;
        }
        //脱敏
        User encryptedUser = new User();
        encryptedUser.setId(user.getId());
        encryptedUser.setUsername(user.getUsername());
        encryptedUser.setUserAccount(user.getUserAccount());
        encryptedUser.setGender(user.getGender());
        encryptedUser.setAvatarUrl(user.getAvatarUrl());
        encryptedUser.setEmail(user.getEmail());
        encryptedUser.setUserStatus(user.getUserStatus());
        encryptedUser.setCreateTime(user.getCreateTime());

        //记录用户的登录状态
        request.getSession().setAttribute(USER_LOGIN_STATE,encryptedUser);
        return encryptedUser;
    }
}




