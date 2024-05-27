package com.example.carsharing;

import com.example.carsharing.entities.Bookings;
import com.example.carsharing.entities.Cars;
import com.example.carsharing.entities.Users;
import com.example.carsharing.servicies.CarSharingService;
import com.example.carsharing.servicies.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;

@Controller
public class MainController {
    int loggedInUserId = 0;
    boolean isAdmin = false;
    String loginError = "";
    @Autowired
    LoginService loginService;
    @Autowired
    CarSharingService carSharingService;
    @GetMapping("/")
    public String start(Model model) {
        model.addAttribute("loginError", loginError);
        return "start page";
    }
    @PostMapping("/enter")
    public String enter(@RequestParam(required = false) String user_id, @RequestParam(required = false) boolean newUser,
                        @RequestParam(required = false) String user_name, @RequestParam(required = false) String contact) {
        if(!newUser) {
            Users user = loginService.getUser(Integer.parseInt(user_id));
            if (user != null) {
                loggedInUserId = user.getUser_id();
                isAdmin = user.isAdmin();
                return "redirect:/main";
            } else {
                loginError = "User not found";
                return "redirect:/";
            }
        }else {
            Users user = new Users();
            user.setName(user_name);
            user.setContact(contact);
            loginService.saveUser(user);
            loggedInUserId = user.getUser_id();
            return "redirect:/main";
        }
    }
    @GetMapping("/main")
    public String main(Model model, @RequestParam(required = false) String show) {
        model.addAttribute("isAdmin", isAdmin);
        boolean showBookings = show != null && show.equals("true");
        model.addAttribute("show", showBookings);
        if(showBookings) {
            model.addAttribute("cars", carSharingService.getAllCars().stream().sorted(Comparator.comparing(Cars::isAvailable).reversed()).toList());
        } else {
            model.addAttribute("cars", carSharingService);
        }
        model.addAttribute("id", loggedInUserId);
        return "main page";
    }
}
