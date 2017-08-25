package com.wangbin.project.base.business.mapper;

import com.wangbin.project.base.business.entity.AdminUser;

import java.util.List;
import java.util.Map;

public interface AdminUserMapper {

    int insert(AdminUser record);

    AdminUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdminUser record);

    List<AdminUser> getAdminUserListByPage(Map<String,Object> map);

    int getAdminUserSumByParam(Map<String,Object> map);

    int updateStatus(Map<String, Object> map);

    int updateStatusByIds(Map<String, Object> map);

    AdminUser getAdminUserByName(String userName);

    AdminUser getAdminUserByPhone(String phone);
}