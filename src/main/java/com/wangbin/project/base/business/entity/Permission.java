package com.wangbin.project.base.business.entity;

public class Permission {
    private Integer id;

    private String permissionName;

    private String url;

    private Integer role_per_check = CHECK_FALSE;//角色是否选中该权限

    private static final Integer CHECK_TRUE = 1;//角色已经选中权限

    private static final Integer CHECK_FALSE = 0;//角色为选中权限

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName == null ? null : permissionName.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getRole_per_check() {
        return role_per_check;
    }

    public void setRole_per_check(Integer role_per_check) {
        this.role_per_check = role_per_check;
    }
}