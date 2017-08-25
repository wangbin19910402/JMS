package com.wangbin.project.web.controller.admin;

import com.wangbin.project.base.business.entity.AdminUser;
import com.wangbin.project.base.business.entity.Role;
import com.wangbin.project.base.business.pager.Pager;
import com.wangbin.project.base.business.service.AdminUserService;
import com.wangbin.project.base.business.service.RoleService;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author wangbin
 **/
@Controller
@RequestMapping("/adminUser")
public class AdminUserController extends AbstractController{

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private RoleService  roleService;

    private final int ROLE_ID_DEFAULT = -1;

    @RequestMapping("/list")
    public String list(HttpServletRequest request,
                       @RequestParam(value = "userName",required = false) String userName,
                       @RequestParam(value = "phone",required = false) String phone,
                       @RequestParam(value = "email", required = false)String email,
                       @RequestParam(value = "roleId", required = false, defaultValue = "-1")int roleId,
                       @RequestParam(value = "pageIndex", required = false, defaultValue = "1")int pageIndex,
                       @RequestParam(value = "pageSize", required = false, defaultValue = "10")int pageSize,
                       Model model) {

        SearchInfo searchInfo = new SearchInfo();
        if (!StringUtils.isEmpty(userName)) {
            searchInfo.setStr_param_0(userName);
        }
        if (!StringUtils.isEmpty(phone)) {
            searchInfo.setStr_param_1(phone);
        }
        if (!StringUtils.isEmpty(email)) {
            searchInfo.setStr_param_2(email);
        }
        if (roleId != ROLE_ID_DEFAULT) {
            searchInfo.setInt_param_0(roleId);
        }
        Pager pager = new Pager();
        pager.setPageSize(pageSize);
        pager.setPageIndex(pageIndex);
        pager.setSearchInfo(searchInfo);
        Pager<AdminUser> adminUserPager = adminUserService.getAdminUserByPager(pager);
        model.addAttribute("pager", adminUserPager);
        model.addAttribute("roleList",roleService.getRoleListByParam(new SearchInfo()));
        return "admin-user/admin-user-list";
    }


    @RequestMapping("/add")
    public String add(Model model){
        List<Role> roleList = roleService.getRoleListByParam(new SearchInfo());
        model.addAttribute("roleList",roleList);
        return "admin-user/admin-user-add";
    }

    @RequestMapping(value = "/ajax-add", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String ajaxAdd(@RequestParam("userName") String userName,
                          @RequestParam("phone") String phone,
                          @RequestParam("email") String email,
                          @RequestParam("roleId") Integer roleId,
                          HttpServletRequest request) {
        Response<Map<String, Object>> response = new Response<>();

        if (adminUserService.getAdminUserByName(userName)!=null) {
            response.setCode(10001);
            response.setMessage("用户名已经存在");
            return toJsonResponse(response);
        }

        if (adminUserService.getAdminUserByPhone(phone)!=null) {
            response.setCode(10002);
            response.setMessage("手机已经注册");
            return toJsonResponse(response);
        }

        if (roleService.getRoleById(roleId)==null) {
            response.setCode(10003);
            response.setMessage("角色查询失败");
            return toJsonResponse(response);
        }

        AdminUser adminUser = new AdminUser();
        adminUser.setUserName(userName);
        adminUser.setPassword(AdminUser.PASSWORD_INIT);
        adminUser.setPhone(phone);
        adminUser.setEmail(email);
        adminUser.setState(AdminUser.STATUS_NORMAL);
        adminUser.setRoleId(roleId);
        adminUser.setCreatedTime(new Date());
        adminUser.setUpdatedTime(new Date());
        int res = adminUserService.insertAdminUser(adminUser);
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
            Model model){
        AdminUser adminUser = adminUserService.getAdminUserById(id);
        List<Role> roleList = roleService.getRoleListByParam(new SearchInfo());
        model.addAttribute("adminUser",adminUser);
        model.addAttribute("roleList",roleList);
        return "admin-user/admin-user-update";
    }

    @RequestMapping(value = "/ajax-update", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String ajaxUpdate(@RequestParam("id") int id,
                             @RequestParam("userName") String userName,
                             @RequestParam("phone") String phone,
                             @RequestParam("email") String email,
                             @RequestParam("roleId") Integer roleId,
                             HttpServletRequest request) {
        Response<Map<String, Object>> response = new Response<>();
        AdminUser temp = adminUserService.getAdminUserByName(userName);
        if (temp != null && temp.getId() != id) {
            response.setCode(10001);
            response.setMessage("用户名已经存在");
            return toJsonResponse(response);
        }
        AdminUser temp2 = adminUserService.getAdminUserByPhone(phone);
        if (temp2 != null && temp2.getId() != id) {
            response.setCode(10002);
            response.setMessage("联系电话已经存在");
            return toJsonResponse(response);
        }

        AdminUser adminUser = adminUserService.getAdminUserById(id);
        if (adminUser != null && adminUser.getId() > 0) {
            adminUser.setUserName(userName);
            adminUser.setPhone(phone);
            adminUser.setEmail(email);
            adminUser.setRoleId(roleId);
            adminUser.setUpdatedTime(new Date());
        }
        int res = adminUserService.updateAdminUser(adminUser);
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

        AdminUser adminUser = adminUserService.getAdminUserById(id);
        if (adminUser == null || adminUser.getId() <= 0) {
            response.setCode(404);
            response.setMessage("未找到对应的用户信息");
            return toJsonResponse(response);
        }
        int res = adminUserService.deleteAdminUser(id);
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

        int res = adminUserService.deleteAdminUserByIds(ids);
        if (res > 0) {
            response.setCode(0);
            response.setMessage("删除成功");
        } else {
            response.setCode(1000);
            response.setMessage("删除失败");
        }

        return toJsonResponse(response);
    }


    @RequestMapping(value = "/ajax-password-reset", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String ajaxPasswordReset(@RequestParam("id") int id) {
        Response<Map<String, Object>> response = new Response<>();

        AdminUser adminUser = adminUserService.getAdminUserById(id);
        if (adminUser == null || adminUser.getId() <= 0) {
            response.setCode(404);
            response.setMessage("未找到对应的用户信息");
            return toJsonResponse(response);
        }
        adminUser.setPassword(AdminUser.PASSWORD_INIT);
        adminUser.setUpdatedTime(new Date());
        int res = adminUserService.updateAdminUser(adminUser);
        if (res > 0) {
            response.setCode(0);
            response.setMessage("成功");
        } else {
            response.setCode(1000);
            response.setMessage("失败");
        }
        return toJsonResponse(response);
    }

}
