package com.aip.usercenter.controller;

import com.aip.usercenter.bean.User;
import com.aip.usercenter.bean.requset.UserLoginRequest;
import com.aip.usercenter.bean.requset.UserRegisterRequest;
import com.aip.usercenter.contant.UserConstant;
import com.aip.usercenter.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Aip
 * @version 1.0
 * @date 2023/10/18  15:57
 * @description 用户接口
 */
@RestController
@RequestMapping("/user")
public class UserController implements UserConstant {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            return null;
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return null;
        }
        return userService.userRegister(userAccount, userPassword, checkPassword);
    }

    /**
     * 获取当前的登录用户信息
     * @author Aganippe
     * @version v1.0
     * @date 2023/11/1
     * @name getCurrentUser
     * @param
     * @param request request
     * @return com.aip.usercenter.bean.User
     */
    @GetMapping("/current")
    public User getCurrentUser(HttpServletRequest request){
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null) {
            return null;
        }
        Long userId = currentUser.getId();
        //todo 校验用户是否合法
        User user = userService.getById(userId);
        return userService.getEncryptedUser(user);
    }

    @PostMapping("/login")
    public User userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            return null;
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();

        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }
        return userService.userLogin(userAccount, userPassword, request);
    }

    @GetMapping("/search")
    public List<User> queryUsers(String username, HttpServletRequest request) {
        //鉴权,仅管理员查询
        if (!isAdmin(request)) {
            return new ArrayList<>();
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isBlank(username)) {
            queryWrapper.like("username", username);
        }
        List<User> userList = userService.list(queryWrapper);
        return userList.stream().map(user -> userService.getEncryptedUser(user)).collect(Collectors.toList());
    }

    @PostMapping("/delete")
    public boolean deleteUser(@RequestBody long id, HttpServletRequest request) {
        if (!isAdmin(request)) {
            return false;
        }
        if (id <= 0) {
            return false;
        }
        return userService.removeById(id);
    }

    /**
     * 鉴权,判断用户资质是否为管理员
     * @author Aganippe
     * @version v1.0
     * @date 2023/10/22
     * @name isAdmin
     * @param
     * @param request request
     * @return boolean
     */
    public boolean isAdmin(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return user != null && user.getUserRole() == ADMIN_ROLE;

    }
}
