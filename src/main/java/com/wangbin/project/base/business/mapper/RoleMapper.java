package com.wangbin.project.base.business.mapper;

import com.wangbin.project.base.business.entity.Role;

import java.util.List;
import java.util.Map;

public interface RoleMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    List<Role> getRoleListByParam(Map<String,Object> param);

    int getRoleSumByParam(Map<String,Object> param);

    Role getRoleByName(String roleName);

    int deleteByRoleIds(int[] ids);

}