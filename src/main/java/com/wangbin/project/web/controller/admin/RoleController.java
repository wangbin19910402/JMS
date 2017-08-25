package com.wangbin.project.web.controller.admin;

import com.wangbin.project.base.business.entity.Role;
import com.wangbin.project.base.business.service.RoleService;
import com.wangbin.project.web.Response;
import com.wangbin.project.web.SearchInfo;
import com.wangbin.project.web.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 角色controller
 *
 * @author wangbin
 **/
@Controller
@RequestMapping("/role")
public class RoleController extends AbstractController {

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(HttpServletRequest request,
                       Model model){

        SearchInfo searchInfo = new SearchInfo();

        List<Role> rolePager = roleService.getRoleListByParam(searchInfo);
        int rolesum = roleService.getRoleSumByParam(searchInfo);
        model.addAttribute("roleList",rolePager);
        model.addAttribute("roleSum",rolesum);
        return "role/role-list";
    }

    @RequestMapping("/add")
    public String add(){
        return "role/role-add";
    }

    @RequestMapping(value = "/ajax-add", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String ajaxAdd(@RequestParam("roleName") String roleName,
                          @RequestParam("roleDesc") String roleDesc,
                          HttpServletRequest request){
        Response<Map<String,Object>> response = new Response<>();

        if (roleService.getRoleByName(roleName)!=null) {
            response.setCode(10001);
            response.setMessage("该角色已经存在");
            return toJsonResponse(response);
        }
        Role role = new Role();
        role.setRoleName(roleName);
        role.setRoleDesc(roleDesc);
        int res = roleService.insertRole(role);
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
                         Model model){
        Role role = roleService.getRoleById(id);
        model.addAttribute("role",role);
        return "role/role-update";
    }

    @RequestMapping(value = "/ajax-update", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String ajaxUpdate(@RequestParam("id") int id,
                         @RequestParam("roleName") String roleName,
                         @RequestParam("roleDesc") String roleDesc,
                         HttpServletRequest request){
        Response<Map<String,Object>> response = new Response<>();
        Role temp = roleService.getRoleByName(roleName);
        if (temp != null && temp.getId() != id) {
            response.setCode(10001);
            response.setMessage("该角色已经存在");
            return toJsonResponse(response);
        }

        Role role = roleService.getRoleById(id);
        if (role != null && role.getId() > 0) {
            role.setRoleName(roleName);
            role.setRoleDesc(roleDesc);
        }
        int res = roleService.updateRole(role);
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
    public String ajaxDelete(@RequestParam("id") int id){
        Response<Map<String,Object>> response = new Response<>();

        Role role = roleService.getRoleById(id);
        if (role == null || role.getId() <= 0) {
            response.setCode(404);
            response.setMessage("未找到对应的角色信息");
            return toJsonResponse(response);
        }
        int res = roleService.deleteRole(id);
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
    public String ajaxDeleteByIds(@RequestParam("ids") int[] ids){
        Response<Map<String,Object>> response = new Response<>();

        if (ids == null || ids.length <= 0) {
            response.setCode(404);
            response.setMessage("未找到对应的角色信息");
            return toJsonResponse(response);
        }

        int res = roleService.deleteRoleByIds(ids);
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
