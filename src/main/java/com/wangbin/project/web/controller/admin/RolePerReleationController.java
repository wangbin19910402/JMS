package com.wangbin.project.web.controller.admin;

import com.wangbin.project.base.business.entity.Permission;
import com.wangbin.project.base.business.entity.RolePerReleation;
import com.wangbin.project.base.business.service.PermissionService;
import com.wangbin.project.base.business.service.RolePerReleationService;
import com.wangbin.project.web.Response;
import com.wangbin.project.web.SearchInfo;
import com.wangbin.project.web.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 角色权限关系controller
 *
 * @author wangbin
 **/
@RequestMapping("/role-per-releation")
@Controller
public class RolePerReleationController extends AbstractController {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RolePerReleationService rolePerReleationService;

    @RequestMapping(value = "/add-list", method = RequestMethod.GET)
    public String addList(HttpServletRequest request,
                       @RequestParam("roleId")int roleId,
                       @RequestParam(value = "permissionName",required = false) String permissionName,
                       @RequestParam(value = "url",required = false) String url,
                       Model model) {

        SearchInfo searchInfo = new SearchInfo();
        if (!StringUtils.isEmpty(permissionName)) {
            searchInfo.setStr_param_0(permissionName);
        }
        if (!StringUtils.isEmpty(url)) {
            searchInfo.setStr_param_1(url);
        }
        List<Permission> perlist = permissionService.getPermissionListByParam(searchInfo);
        List<Integer> perIds = rolePerReleationService.getPerIdsByRoleId(roleId);
        List<Permission> result = rolePerReleationService.getPermissionByRoleId(perlist,perIds,true);
        model.addAttribute("allPer", result);
        model.addAttribute("roleId",roleId);
        model.addAttribute("searchInfo",searchInfo);
        return "role-permission/role-per-releation-add";
    }

    @RequestMapping(value = "/del-list", method = RequestMethod.GET)
    public String delList(HttpServletRequest request,
                       @RequestParam("roleId")int roleId,
                       @RequestParam(value = "permissionName",required = false) String permissionName,
                       @RequestParam(value = "url",required = false) String url,
                       Model model) {

        SearchInfo searchInfo = new SearchInfo();
        if (!StringUtils.isEmpty(permissionName)) {
            searchInfo.setStr_param_0(permissionName);
        }
        if (!StringUtils.isEmpty(url)) {
            searchInfo.setStr_param_1(url);
        }
        List<Permission> perlist = permissionService.getPermissionListByParam(searchInfo);
        List<Integer> perIds = rolePerReleationService.getPerIdsByRoleId(roleId);
        List<Permission> result = rolePerReleationService.getPermissionByRoleId(perlist,perIds,false);
        model.addAttribute("allPer", result);
        model.addAttribute("roleId",roleId);
        model.addAttribute("searchInfo",searchInfo);
        return "role-permission/role-per-releation-delete";
    }

    /**
     * 新增获取删除角色对应权限
     * @param roleId    角色id
     * @param perIds    权限id
     * @param isSave    是否新增
     * @return
     */
    @RequestMapping(value = "/save-or-delete", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String ajaxAdd(@RequestParam("roleId") int roleId,
                          @RequestParam(value = "perIds",defaultValue = "") String perIds,
                          @RequestParam("isSave") boolean isSave) {
        Response<Map<String, Object>> response = new Response<>();

        if (!StringUtils.isEmpty(perIds)) {
            perIds = perIds.substring(0,perIds.length());
        }

        String[] per = perIds.split(",");
        List<RolePerReleation> list = new ArrayList<>();
        for (String item : per) {
            //如果老数据中不包含新数据
            RolePerReleation releation = new RolePerReleation();
            releation.setPerId(Integer.parseInt(item));
            releation.setRoleId(roleId);
            list.add(releation);
        }

        int res = 0;
        if (isSave) {
            res = rolePerReleationService.addRolePerReleations(list);
        } else{
            res = rolePerReleationService.deleteRolePerReleation(list);
        }

        if (res > 0) {
            response.setCode(0);
            response.setMessage("更新成功");
        } else {
            response.setCode(1001);
            response.setMessage("更新失败");
        }
        return toJsonResponse(response);

    }



}
