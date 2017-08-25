package com.wangbin.project.base.business.service.impl;/**
 * create by wangbin
 **/

import com.wangbin.project.base.business.entity.AdminUser;
import com.wangbin.project.base.business.entity.Permission;
import com.wangbin.project.base.business.entity.Role;
import com.wangbin.project.base.business.mapper.AdminUserMapper;
import com.wangbin.project.base.business.mapper.RoleMapper;
import com.wangbin.project.base.business.pager.Pager;
import com.wangbin.project.base.business.service.AdminUserService;
import com.wangbin.project.web.SearchInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author wangbin
 **/
@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private AdminUserMapper adminUserMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Pager getAdminUserByPager(Pager pager) {
        if (pager!=null) {
            SearchInfo searchInfo = pager.getSearchInfo();
            Map<String, Object> param = new HashMap<>();
            param.put("userName", searchInfo.getStr_param_0());
            param.put("phone", searchInfo.getStr_param_1());
            param.put("email", searchInfo.getStr_param_2());
            param.put("roleId", searchInfo.getInt_param_0());
            param.put("pageBegin", (pager.getPageIndex()-1) * pager.getPageSize());
            param.put("pageSize", pager.getPageSize());
            int itemSize = adminUserMapper.getAdminUserSumByParam(param);
            List<AdminUser> list = adminUserMapper.getAdminUserListByPage(param);
            addRoleName(list);
            pager.setItems(list);
            pager.setItemSize(itemSize);
            pager.setPageNum(itemSize,pager.getPageSize());
        }
        return pager;
    }

    @Override
    public int insertAdminUser(AdminUser adminUser) {
        return adminUserMapper.insert(adminUser);
    }

    @Override
    public int updateAdminUser(AdminUser adminUser) {
        return adminUserMapper.updateByPrimaryKeySelective(adminUser);
    }

    @Override
    public int deleteAdminUser(int id) {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("id",id);
        param.put("state",AdminUser.STATUS_DELETE);
        return adminUserMapper.updateStatus(param);
    }

    @Override
    public int deleteAdminUserByIds(int[] ids) {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("ids",ids);
        param.put("state",AdminUser.STATUS_DELETE);
        return adminUserMapper.updateStatusByIds(param);
    }

    @Override
    public AdminUser getAdminUserByName(String userName) {
        return adminUserMapper.getAdminUserByName(userName);
    }

    @Override
    public AdminUser getAdminUserByPhone(String phone) {
        return adminUserMapper.getAdminUserByPhone(phone);
    }

    @Override
    public AdminUser getAdminUserById(int id) {
        return adminUserMapper.selectByPrimaryKey(id);
    }

    private void addRoleName(List<AdminUser> list){
        Map<Integer, String> roleMap = getRoleMap();
        for (AdminUser adminUser : list) {
            String roleName = roleMap.get(adminUser.getRoleId());
            adminUser.setRoleName(roleName);
        }
    }

    private Map<Integer,String> getRoleMap(){
        Map<Integer,String> roleMap = new HashMap<>();
        Map<String,Object> param = new HashMap<>();
        List<Role> list = roleMapper.getRoleListByParam(param);
        for (Role role : list) {
            roleMap.put(role.getId(),role.getRoleName());
        }
        return roleMap;
    }
}
