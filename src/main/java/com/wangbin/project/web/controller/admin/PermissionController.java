package com.wangbin.project.web.controller.admin;

import com.wangbin.project.base.business.entity.Permission;
import com.wangbin.project.base.business.pager.Pager;
import com.wangbin.project.base.business.service.PermissionService;
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
import java.util.Map;

/**
 * 权限controller
 *
 * @author wangbin
 **/
@Controller
@RequestMapping("/permission")
public class PermissionController extends AbstractController {

    @Autowired
    private PermissionService permissionService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(HttpServletRequest request,
                       @RequestParam(value = "permissionName",required = false) String permissionName,
                       @RequestParam(value = "url",required = false) String url,
                       @RequestParam(value = "pageIndex", required = false, defaultValue = "1")int pageIndex,
                       @RequestParam(value = "pageSize", required = false, defaultValue = "10")int pageSize,
                       Model model) {

        SearchInfo searchInfo = new SearchInfo();
        if (!StringUtils.isEmpty(permissionName)) {
            searchInfo.setStr_param_0(permissionName);
        }
        if (!StringUtils.isEmpty(url)) {
            searchInfo.setStr_param_1(url);
        }
        Pager pager = new Pager();
        pager.setPageSize(pageSize);
        pager.setPageIndex(pageIndex);
        pager.setSearchInfo(searchInfo);
        Pager<Permission> permissionPager = permissionService.getPermissionListByPager(pager);
        model.addAttribute("pager", permissionPager);
        return "permission/permission-list";
    }

    @RequestMapping("/add")
    public String add() {
        return "permission/permission-add";
    }

    @RequestMapping(value = "/ajax-add", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String ajaxAdd(@RequestParam("permissionName") String permissionName,
                          @RequestParam("url") String url,
                          HttpServletRequest request) {
        Response<Map<String, Object>> response = new Response<>();

        if (permissionService.getPermissionByName(permissionName)!=null) {
            response.setCode(10001);
            response.setMessage("权限名称已经存在");
            return toJsonResponse(response);
        }

        if (permissionService.getPermissionByUrl(url)!=null) {
            response.setCode(10002);
            response.setMessage("权限地址已经存在");
            return toJsonResponse(response);
        }

        Permission permission = new Permission();
        permission.setPermissionName(permissionName);
        permission.setUrl(url);
        int res = permissionService.insertPermission(permission);
        if (res > 0) {
            response.setCode(0);
            response.setMessage("新增成功");
        } else {
            response.setCode(1000);
            response.setMessage("新增失败");
        }
        return toJsonResponse(response);

    }

    @RequestMapping("/update")
    public String update(@RequestParam("id") int id,
                         HttpServletRequest request,
                         Model model) {
        Permission permission = permissionService.getPermissionById(id);
        model.addAttribute("permission", permission);
        return "permission/permission-update";
    }

    @RequestMapping(value = "/ajax-update", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String ajaxUpdate(@RequestParam("id") int id,
                             @RequestParam("permissionName") String permissionName,
                             @RequestParam("url") String url,
                             HttpServletRequest request) {
        Response<Map<String, Object>> response = new Response<>();
        Permission temp = permissionService.getPermissionByName(permissionName);
        if (temp != null && temp.getId() != id) {
            response.setCode(10001);
            response.setMessage("权限名称已经存在");
            return toJsonResponse(response);
        }
        Permission temp2 = permissionService.getPermissionByUrl(url);
        if (temp2 != null && temp2.getId() != id) {
            response.setCode(10002);
            response.setMessage("权限地址已经存在");
            return toJsonResponse(response);
        }

        Permission permission = permissionService.getPermissionById(id);
        if (permission != null && permission.getId() > 0) {
            permission.setPermissionName(permissionName);
            permission.setUrl(url);
        }
        int res = permissionService.updatePermission(permission);
        if (res > 0) {
            response.setCode(0);
            response.setMessage("修改成功");
        } else {
            response.setCode(1000);
            response.setMessage("修改失败");
        }

        return toJsonResponse(response);
    }

    @RequestMapping(value = "/ajax-delete", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String ajaxDelete(@RequestParam("id") int id) {
        Response<Map<String, Object>> response = new Response<>();

        Permission permission = permissionService.getPermissionById(id);
        if (permission == null || permission.getId() <= 0) {
            response.setCode(404);
            response.setMessage("未找到对应的权限信息");
            return toJsonResponse(response);
        }
        int res = permissionService.deletePermission(id);
        if (res > 0) {
            response.setCode(0);
            response.setMessage("删除成功");
        } else {
            response.setCode(1000);
            response.setMessage("删除失败");
        }

        return toJsonResponse(response);
    }

    @RequestMapping(value = "/ajax-delete-by-ids", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String ajaxDeleteByIds(@RequestParam("ids") int[] ids) {
        Response<Map<String, Object>> response = new Response<>();

        if (ids == null || ids.length <= 0) {
            response.setCode(404);
            response.setMessage("未找到对应的权限信息");
            return toJsonResponse(response);
        }

        int res = permissionService.deletePermissionByIds(ids);
        if (res > 0) {
            response.setCode(0);
            response.setMessage("删除成功");
        } else {
            response.setCode(1000);
            response.setMessage("删除失败");
        }

        return toJsonResponse(response);
    }
}