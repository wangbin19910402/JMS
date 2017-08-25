package com.wangbin.project.base.business.service;

import com.wangbin.project.base.business.entity.Permission;
import com.wangbin.project.base.business.entity.RolePerReleation;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author wangbin
 **/
public interface RolePerReleationService {

    public List<Integer> getPerIdsByRoleId(int roleId);

    public List<Permission> getPermissionByRoleId(List<Permission> permissionList, List<Integer> perIds, boolean add);

    public int addRolePerReleations(List<RolePerReleation> list);

    public int deleteRolePerReleation(List<RolePerReleation> list);
}
