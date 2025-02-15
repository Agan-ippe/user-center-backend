package com.aip.usercenter.pojo.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 用户密码
     */
    private String userPassword;

    /**
     * 头像
     */
    private String avatarUrl;

    /**
     * 性别，0：女、1：男
     */
    private Integer gender;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 所在城市
     */
    private String city;

    /**
     * 标签列表
     */
    private String tags;

    /**
     * 默认为0，0：正常、1：封禁
     */
    private Integer userStatus;

    /**
     * 默认为0，0：普通用户、1：管理员
     */
    private Integer userRole;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 默认为0，0：否、1：是
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}