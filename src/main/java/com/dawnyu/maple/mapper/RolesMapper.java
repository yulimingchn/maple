package com.dawnyu.maple.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.dawnyu.maple.bean.Role;

import java.util.List;

/**
 *  @author dawnyu
 */
@Mapper
public interface RolesMapper {
    int addRoles(@Param("roles") String[] roles, @Param("uid") Long uid);

    List<Role> getRolesByUid(Long uid);
}
