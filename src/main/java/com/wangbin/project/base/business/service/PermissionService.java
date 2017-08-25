package com.wangbin.project.base.business.service;

import com.wangbin.project.base.business.entity.Permission;
import com.wangbin.project.base.business.pager.Pager;
import com.wangbin.project.web.SearchInfo;

import java.util.List;

/**
 *
 *
 * @author wangbin
 **/
public interface PermissionService {

    int getPermissionSumByParam(SearchInfo searchInfo);

    List<Permission> getPermissionListByParam(SearchInfo searchInfo);

    Permission getPermissionById(Integer id);

    Permission getPermissionByName(String PermissionName);

    int insertPermission(Permission Permission);

    int updatePermission(Permission Permission);

    int deletePermission(int id);

    int deletePermissionByIds(int[] ids);

    Permission getPermissionByUrl(String url);

    Pager getPermissionListByPager(Pager pager);

    List<Permission> getPermissionByIds(List<Integer> ids);

}
