package com.wangbin.project.web.shiro;

import com.wangbin.project.base.business.entity.AdminUser;
import com.wangbin.project.base.business.entity.Permission;
import com.wangbin.project.base.business.service.AdminUserService;
import com.wangbin.project.base.business.service.PermissionService;
import com.wangbin.project.base.business.service.RolePerReleationService;
import com.wangbin.project.base.business.service.RoleService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 *
 * @author wangbin
 **/
public class ShiroRemal extends AuthorizingRealm {

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private RolePerReleationService rolePerReleationService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal(); //获取用户名
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        AdminUser currentUser = adminUserService.getAdminUserByName(username);
        if (currentUser == null) {
            return null;
        }
        List<Integer> perIds = rolePerReleationService.getPerIdsByRoleId(currentUser.getRoleId());
        List<Permission> permissionsList = permissionService.getPermissionByIds(perIds);
        Set<String> roles = new HashSet<>();
        roles.add(roleService.getRoleById(currentUser.getRoleId()).getRoleName());
        Set<String> permissions = new HashSet<>();
        for (Permission permission : permissionsList) {
            permissions.add(permission.getUrl());
        }
        authorizationInfo.setRoles(roles);
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken.getPrincipal(); // 获取用户名
        String accountName = usernamePasswordToken.getUsername();
        if(accountName != null) {
            System.out.println(11111111);
            AdminUser user = adminUserService.getAdminUserByName(accountName);
            AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user.getUserName(), user.getPassword(), getName());
            return authcInfo;
        } else {
            System.out.println(22222222);
            return new SimpleAuthenticationInfo();
        }
    }

}
