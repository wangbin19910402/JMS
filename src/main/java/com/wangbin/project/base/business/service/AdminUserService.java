package com.wangbin.project.base.business.service;

import com.wangbin.project.base.business.entity.AdminUser;
import com.wangbin.project.base.business.pager.Pager;

/**
 *
 *
 * @author wangbin
 **/
public interface AdminUserService {

    public Pager getAdminUserByPager(Pager pager);

    public int insertAdminUser(AdminUser adminUser);

    public int updateAdminUser(AdminUser adminUser);

    public int deleteAdminUser(int id);

    public int deleteAdminUserByIds(int[] ids);

    public AdminUser getAdminUserByName(String userName);

    public AdminUser getAdminUserByPhone(String phone);

    public AdminUser getAdminUserById(int id);

}
