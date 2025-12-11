package com.springcloud.backend.mapper;

import com.springcloud.backend.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM users WHERE username = #{username}")
    User findByUsername(String username);

    @Insert("INSERT INTO users(username,password,role,phone,status,created_at) VALUES(#{username},#{password},#{role},#{phone},#{status},NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    @Select("SELECT * FROM users WHERE id=#{id}")
    User findById(Long id);

    @Select("SELECT * FROM users WHERE role = 'MERCHANT' ORDER BY created_at DESC")
    List<User> listMerchants();

    @Select("SELECT * FROM users WHERE role = 'CUSTOMER' ORDER BY created_at DESC")
    List<User> listCustomers();

    @Update("UPDATE users SET status=#{status} WHERE id=#{id}")
    void updateStatus(@Param("id") Long id, @Param("status") Integer status);
}
