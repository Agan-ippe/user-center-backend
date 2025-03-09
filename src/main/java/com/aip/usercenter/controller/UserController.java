package com.aip.usercenter.controller;

import com.aip.usercenter.pojo.domain.User;
import com.aip.usercenter.pojo.dto.UserLoginDTO;
import com.aip.usercenter.pojo.dto.UserRegisterDTO;
import com.aip.usercenter.requset.UserLoginRequest;
import com.aip.usercenter.requset.UserRegisterRequest;
import com.aip.usercenter.common.BaseResponse;
import com.aip.usercenter.common.ErrorCode;
import com.aip.usercenter.utils.ResultUtils;
import com.aip.usercenter.contant.UserConstant;
import com.aip.usercenter.exception.BusinessException;
import com.aip.usercenter.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    /**
     * 获取当前的登录用户信息
     * @author Aganippe
     * @date 2023/11/1
     * @name getCurrentUser
     * @param request request
     * @return com.aip.usercenter.pojo.domain.User
     */
    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request){
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
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
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long result = userService.userRegister(new UserRegisterDTO(userAccount, userPassword, checkPassword));
        log.info("id:{}",result);
        return ResultUtils.success(result);
    }


    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.ILLEGAL_REQUEST, "非法请求");
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();

        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "用户账号或密码不能为空");
        }

        User user = userService.userLogin(new UserLoginDTO(userAccount,userPassword), request);
        return ResultUtils.success(user);
    }

    @PostMapping("/logout")
    public BaseResponse userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN, "未登录");
        }
        userService.userLogout(request);
        return ResultUtils.success(USER_LOGOUT);
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
            throw new BusinessException(ErrorCode.NO_AUTH, "非管理员禁止访问");
        }
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数错误");
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
