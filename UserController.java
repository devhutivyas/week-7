package com.busyqa.course.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.busyqa.course.bean.RoleBean;
import com.busyqa.course.bean.UserBean;
import com.busyqa.course.service.IRoleService;
import com.busyqa.course.service.IUserService;

@SuppressWarnings("unused")
@Controller
public class UserController {

    @Autowired
    IUserService userService;

    /*{1} Add wiring annotation here.*/
    IRoleService roleService;

    /*
     * This method is executed every time this Controller is called. It adds
     * the list of roles to the Model and the roles are always sent to the View.
     */
    @ModelAttribute("list_roles")
    public List<RoleBean> addListRoles() {

        /*{2} In the next statement, return the list of roles using the roleService.(replace the null)*/
        return null;
    }

    @GetMapping({"/", "/index"})
    public String index() {

        System.out.println("Showing the Index!!!");

        return "index";
    }

    @GetMapping("/list_users")
    public ModelAndView listUsers(){

        System.out.println("Listing the Users!!!");

        List<UserBean> users = this.userService.listUsers();

        users.forEach(System.out::println);

        return new ModelAndView("list_users","users",users);
    }

    @GetMapping("/input_user")
    public String inputUser(){

        System.out.println("Showing the Input User!!!");

        return "input_user";
    }

    @PostMapping("/create_user")
    public String createUser(HttpServletRequest request) throws ParseException {

        System.out.println("Creating User!!!");

        String first = request.getParameter("first");
        String last = request.getParameter("last");
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        String birth = request.getParameter("birth");

        /*{3} Retrieve the parameter "id_role" from the request and assign it to the idRole variable.*/
        String idRole = null;

        /*{4} In the next statement, replace the null that comes after the parameter birth by the idRole variable.*/
        UserBean user = this.createUserBean(null, first, last, userName, password, birth, null);

        this.userService.createUser(user);

        return "redirect:/list_users";
    }

    @GetMapping("/show_user")
    public ModelAndView showUser(@RequestParam("id_user") int idUser) {

        System.out.println("Showing the User!!!");

        UserBean user = this.userService.findUser(idUser);

        return new ModelAndView("show_user","user",user);
    }

    @PostMapping("/modify_user")
    public String modifyUser(HttpServletRequest request) throws ParseException {

        System.out.println("Updating the User!!!");

        String idUser = request.getParameter("id_user");
        String first = request.getParameter("first");
        String last = request.getParameter("last");
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        String birth = request.getParameter("birth");

        /*{5} Retrieve the parameter "id_role" from the request and assign it to the idRole variable.*/
        String idRole = null;

        /*{6} In the next statement, replace the null that comes after the parameter birth by the idRole variable.*/
        UserBean user = this.createUserBean(idUser, first, last, userName, password, birth, null);

        this.userService.modifyUser(user);

        return "redirect:/list_users";
    }

    @GetMapping("/delete_user")
    public String deleteUser(@RequestParam("id_user") int idUser){

        System.out.println("Deleting the User!!!");

        this.userService.deleteUser(idUser);

        return "redirect:/list_users";
    }

    /*
     * The following are helper methods that help to process the information received.
     */
    private UserBean createUserBean(String idUser, String first, String last,
                                     String userName, String password, String birth,
                                      String idRole) throws ParseException {

        /*
         * {7} In the following statement, return the RoleBean using the findRole()
         *     method of the roleService. Notice that the findRole() takes the idRole
         *     as a parameter.(Replace the null)
         */
        RoleBean role = null;

        /*
         * {8} In the following statement, replace the null by the role variable.
         */
        UserBean user = new UserBean(this.parseId(idUser),
                                     first.trim(),
                                     last.trim(),
                                     userName.trim(),
                                     password.trim(),
                                     this.parseDate(birth.trim()),
                                     null);
        return user;
    }

    private int parseId(String id) {
        return (id==null)?0:Integer.parseInt(id.trim());
    }

    private Date parseDate(String date) throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.parse(date);
    }
}
