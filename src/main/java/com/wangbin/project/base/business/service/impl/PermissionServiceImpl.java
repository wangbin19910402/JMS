package com.wangbin.project.base.business.service.impl;/**
 * create by wangbin
 **/

import com.wangbin.project.base.business.entity.Permission;
import com.wangbin.project.base.business.mapper.PermissionMapper;
import com.wangbin.project.base.business.pager.Pager;
import com.wangbin.project.base.business.service.PermissionService;
import com.wangbin.project.web.SearchInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangbin
 **/
@Service
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private PermissionMapper permissionMapper;

    @Override
    public int getPermissionSumByParam(SearchInfo searchInfo) {
        Map<String, Object> param = new HashMap<>();
        param.put("permissionName", searchInfo.getStr_param_0());
        param.put("url", searchInfo.getStr_param_1());
        int res = permissionMapper.getPermissionSumByParam(param);
        return res;
    }

    @Override
    public List<Permission> getPermissionListByParam(SearchInfo searchInfo) {
        Map<String, Object> param = new HashMap<>();
        param.put("permissionName", searchInfo.getStr_param_0());
        param.put("url", searchInfo.getStr_param_1());
        List<Permission> res = permissionMapper.getPermissionListByParam(param);
        return res;
    }

    @Override
    public Permission getPermissionById(Integer id) {
        return permissionMapper.selectByPrimaryKey(id);
    }

    @Override
    public Permission getPermissionByName(String PermissionName) {
        return permissionMapper.getPermissionByName(PermissionName);
    }

    @Override
    public int insertPermission(Permission Permission) {
        return permissionMapper.insert(Permission);
    }

    @Override
    public int updatePermission(Permission Permission) {
        return permissionMapper.updateByPrimaryKeySelective(Permission);
    }

    @Override
    public int deletePermission(int id) {
        return permissionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deletePermissionByIds(int[] ids) {
        return permissionMapper.deletePermissionByIds(ids);
    }

    @Override
    public Permission getPermissionByUrl(String url) {
        return permissionMapper.getPermissionByUrl(url);
    }

    @Override
    public Pager<Permission> getPermissionListByPager(Pager pager) {
        if (pager!=null) {
            SearchInfo searchInfo = pager.getSearchInfo();
            Map<String, Object> param = new HashMap<>();
            param.put("permissionName", searchInfo.getStr_param_0());
            param.put("url", searchInfo.getStr_param_1());
            param.put("pageBegin", (pager.getPageIndex()-1) * pager.getPageSize());
            param.put("pageSize", pager.getPageSize());
            int itemSize = permissionMapper.getPermissionSumByParam(param);
            List<Permission> list = permissionMapper.getPermissionListByPage(param);
            pager.setItems(list);
            pager.setItemSize(itemSize);
            pager.setPageNum(itemSize,pager.getPageSize());
        }
        return pager;
    }

    @Override
    public List<Permission> getPermissionByIds(List<Integer> ids) {
        return permissionMapper.getPermissionByIds(ids);
    }


}
