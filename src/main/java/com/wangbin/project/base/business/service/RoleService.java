package com.wangbin.project.base.business.service;

import com.wangbin.project.base.business.entity.Role;
import com.wangbin.project.web.SearchInfo;

import java.util.List;

/**
 *
 *
 * @author wangbin
 **/
public interface RoleService {

    int getRoleSumByParam(SearchInfo searchInfo);

    List<Role> getRoleListByParam(SearchInfo searchInfo);

    Role getRoleById(Integer id);

    Role getRoleByName(String roleName);

    int insertRole(Role role);

    int updateRole(Role role);

    int deleteRole(int id);

    int deleteRoleByIds(int[] ids);
}
