package com.wangbin.project.base.business.service.impl;/**
 * create by wangbin
 **/

import com.wangbin.project.base.business.entity.Permission;
import com.wangbin.project.base.business.entity.RolePerReleation;
import com.wangbin.project.base.business.mapper.RolePerReleationMapper;
import com.wangbin.project.base.business.service.PermissionService;
import com.wangbin.project.base.business.service.RolePerReleationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author wangbin
 **/
@Service
public class RolePerReleationServiceImpl implements RolePerReleationService {

    @Resource
    private RolePerReleationMapper rolePerReleationMapper;

    @Override
    public List<Integer> getPerIdsByRoleId(int roleId) {
        return rolePerReleationMapper.getPerIdsByRoleId(roleId);
    }

    @Override
    public List<Permission> getPermissionByRoleId(List<Permission> permissionList, List<Integer> perIds, boolean add) {
        List<Permission> result = new ArrayList<>();
        //说明是新增-代表不存在
        if (add) {
            for (Permission permission : permissionList) {
                if (!perIds.contains(permission.getId())) {
                    result.add(permission);
                }
            }
        } else {
            //代表存在的数据
            for (Permission permission : permissionList) {
                if (perIds.contains(permission.getId())) {
                    result.add(permission);
                }
            }
        }


        return result;
    }

    @Override
    public int addRolePerReleations(List<RolePerReleation> list) {
        int res = 0;
        if (!list.isEmpty()) {
            res = rolePerReleationMapper.insertInfos(list);
        }
        return res;
    }

    @Override
    public int deleteRolePerReleation(List<RolePerReleation> list) {
        int res = 0;
        if (!list.isEmpty()) {
            for (RolePerReleation item : list) {
                res = rolePerReleationMapper.deleteByInfo(item);
            }
        }
        return res;
    }


}
