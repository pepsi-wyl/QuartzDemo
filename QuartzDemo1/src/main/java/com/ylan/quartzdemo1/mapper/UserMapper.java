package com.ylan.quartzdemo1.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.ylan.quartzdemo1.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author by pepsi-wyl
 * @date 2022-03-05 9:46
 */

// 继承BaseMapper
@Repository(value = "userMapper")
public interface UserMapper extends BaseMapper<User> {

}