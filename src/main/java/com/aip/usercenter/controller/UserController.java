package com.aip.usercenter.controller;

import com.aip.usercenter.bean.User;
import com.aip.usercenter.bean.requset.UserLoginRequest;
import com.aip.usercenter.bean.requset.UserRegisterRequest;
import com.aip.usercenter.common.BaseResponse;
import com.aip.usercenter.common.ErrorCode;
import com.aip.usercenter.utils.ResultUtils;
import com.aip.usercenter.contant.UserConstant;
import com.aip.usercenter.exception.BusinessException;
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
    //推的上去吗

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
    public BaseResponse<User> getCurrentUser(HttpServletRequest request){
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null) {
            return null;
        }
        Long userId = currentUser.getId();
        //todo 校验用户是否合法
        User user = userService.getById(userId);
        User encryptedUser = userService.getEncryptedUser(user);
        return ResultUtils.success(encryptedUser);
    }


    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
//            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        Long result = userService.userRegister(userAccount, userPassword, checkPassword);
        return ResultUtils.success(result);
    }


    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            return null;
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();

        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }
        User user = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(user);
    }

    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        Integer result = userService.userLogout(request);
        return ResultUtils.success(result);
    }

    @GetMapping("/search")
    public BaseResponse<List<User>> searchUsers(String username, HttpServletRequest request) {
        //鉴权,仅管理员查询
        if (!isAdmin(request)) {
           throw new BusinessException(ErrorCode.NO_AUTH, "非管理员禁止访问");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("username", username);
        }
        List<User> userList = userService.list(queryWrapper);
        List<User> list = userList.stream().map(user -> userService.getEncryptedUser(user)).collect(Collectors.toList());
        return ResultUtils.success(list);
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody long id, HttpServletRequest request) {
        if (!isAdmin(request)) {
            return null;
        }
        if (id <= 0) {
            return null;
        }
        boolean result = userService.removeById(id);
        return ResultUtils.success(result);
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
