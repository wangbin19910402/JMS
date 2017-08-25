package com.wangbin.project.base.business.service.impl;

import com.wangbin.project.base.business.entity.Role;
import com.wangbin.project.base.business.mapper.RoleMapper;
import com.wangbin.project.base.business.pager.Pager;
import com.wangbin.project.base.business.service.RoleService;
import com.wangbin.project.web.SearchInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author wangbin
 **/
@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Override
    public int getRoleSumByParam(SearchInfo searchInfo) {
        Map<String,Object> map = new HashMap<>();
        map.put("roleName",searchInfo.getStr_param_0());
        int result = roleMapper.getRoleSumByParam(map);
        return result;
    }

    @Override
    public List<Role> getRoleListByParam(SearchInfo searchInfo) {
        Map<String,Object> map = new HashMap<>();
        map.put("roleName",searchInfo.getStr_param_0());
        List<Role> result = roleMapper.getRoleListByParam(map);
        return result;
    }

    @Override
    public Role getRoleById(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public Role getRoleByName(String roleName) {
        return roleMapper.getRoleByName(roleName);
    }

    @Override
    public int insertRole(Role role) {
        return roleMapper.insert(role);
    }

    @Override
    public int updateRole(Role role) {
        return roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public int deleteRole(int id) {
        return roleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteRoleByIds(int[] ids) {
        return roleMapper.deleteByRoleIds(ids);
    }
}
