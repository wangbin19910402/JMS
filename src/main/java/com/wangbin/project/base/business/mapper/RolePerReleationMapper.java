package com.wangbin.project.base.business.mapper;

import com.wangbin.project.base.business.entity.RolePerReleation;

import java.util.List;

public interface RolePerReleationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RolePerReleation record);

    int insertSelective(RolePerReleation record);

    RolePerReleation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RolePerReleation record);

    int updateByPrimaryKey(RolePerReleation record);

    List<Integer> getPerIdsByRoleId(Integer roleId);

    int deleteByInfo(RolePerReleation item);

    int insertInfos(List<RolePerReleation> list);
}