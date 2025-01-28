package com.scm20.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm20.entities.User;
import com.scm20.forms.UserForm;
import com.scm20.helper.Message;
import com.scm20.helper.MessageType;
import com.scm20.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class pageController {

    @Autowired
    private UserService userService;

    // redirect index page
    @RequestMapping("/")
    public String index() {
        return "redirect:/home";
    }

    // home
    @RequestMapping("/home")
    public String home(Model model) {
        System.out.println("Home page handler");
        // sending data to view
        model.addAttribute("name", "Substring Technologies");
        return "home";
    }

    // about route
    @RequestMapping("/about")
    public String aboutPage() {
        System.out.println("About page loading");
        return "about";
    }

    // services route
    @RequestMapping("/services")
    public String servicesPage() {
        System.out.println("Services page loading");
        return "services";
    }

    // contact
    @GetMapping("/contact")
    public String contactPage() {
        return new String("contact");

    }

    // This is showing login page
    @GetMapping("/login")
    public String loginPage() {
        return new String("login");

    }

    // This is register page
    @GetMapping("/register")
    public String registerPage(Model model) {

        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        return "register";
    }

    // processing register

    @RequestMapping(value = "/do-register", method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForm userForm, BindingResult rBindingResult,
            HttpSession session) {
        System.out.println("Processing registration");
        // fetch the data
        // Userform
        System.out.println(userForm);

        // todo
        // validate form data
        if (rBindingResult.hasErrors()) {
            return "register";
        }

        // save to database
        // userForm get the data to user
        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setProfilePic(null);

        User saveUser = userService.saveUser(user);
        System.out.println("saved user");

        // message sucessfully register
        // Add message
        Message message = Message.builder().content("Registration Successful").type(MessageType.blue).build();
        session.setAttribute("message", message);

        // redirect to login page
        return "redirect:/register";
    }

}
