package com.aip.usercenter.service.impl;

import com.aip.usercenter.dao.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aip.usercenter.bean.User;
import com.aip.usercenter.service.UserService;
import com.aip.usercenter.dao.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;


/**
 * 用户服务功能实现类
 *
 * @author Aip
 * @version v1.0
 * @date 2023/10/14
 */

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public Long userRegister(String userAccount, String userPassword, String checkPassword) {
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
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
        if (!userPassword.matches(regExPassword)){
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
        final String SALT = "Aip";
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        //插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setPwd(encryptPassword);
        userMapper.insert(user);
        return user.getId();
        //提交用户注册数据

    }
}




