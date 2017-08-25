package com.wangbin.project.base.business.mapper;

import com.wangbin.project.base.business.entity.Permission;

import java.util.List;
import java.util.Map;

public interface PermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Permission record);

    Permission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Permission record);

    int getPermissionSumByParam(Map<String,Object> param);

    List<Permission> getPermissionListByParam(Map<String,Object> param);

    List<Permission> getPermissionListByPage(Map<String,Object> param);

    Permission getPermissionByName(String permissionName);

    int deletePermissionByIds(int[] ids);

    Permission getPermissionByUrl(String url);

    List<Permission> getPermissionByIds(List<Integer> ids);
}