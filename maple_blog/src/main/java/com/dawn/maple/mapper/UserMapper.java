package com.dawn.maple.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.dawn.maple.bean.Role;
import com.dawn.maple.bean.User;

import java.util.List;

/**
 *  @author dawnyu
 */
@Mapper
public interface UserMapper {

    User loadUserByUsername(@Param("username") String username);

    long reg(User user);

    int updateUserEmail(@Param("email") String email, @Param("id") Long id);

    List<User> getUserByNickname(@Param("nickname") String nickname);

    List<Role> getAllRole();

    int updateUserEnabled(@Param("enabled") Boolean enabled, @Param("uid") Long uid);

    int deleteUserById(Long uid);

    int deleteUserRolesByUid(Long id);

    int setUserRoles(@Param("rids") Long[] rids, @Param("id") Long id);

    User getUserById(@Param("id") Long id);
}
