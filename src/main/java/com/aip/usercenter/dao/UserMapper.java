package com.aip.usercenter.dao;

import com.aip.usercenter.bean.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


/**
 * @author Aip
 * @version 1.0
 * @date 2023/10/8  17:20
 * @description 对User实体类进行增删改查
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
}
