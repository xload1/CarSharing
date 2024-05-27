package com.example.carsharing;

import com.example.carsharing.entities.Cars;
import com.example.carsharing.entities.Users;
import com.example.carsharing.servicies.CarSharingService;
import com.example.carsharing.servicies.LoginService;
import com.example.carsharing.servicies.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;

@Controller
public class MainController {
    String reservationError = "";
    int loggedInUserId = 1;
    boolean isAdmin = false;
    String loginError = "";

    LoginService loginService;

    CarSharingService carSharingService;
    ReservationService reservationService;

    @Autowired
    public MainController(LoginService loginService, CarSharingService carSharingService, ReservationService reservationService) {
        this.loginService = loginService;
        this.carSharingService = carSharingService;
        this.reservationService = reservationService;
    }

    @GetMapping("/")
    public String start(Model model) {
        model.addAttribute("loginError", loginError);
        return "start page";
    }

    @PostMapping("/enter")
    public String enter(@RequestParam(required = false) String user_id, @RequestParam(required = false) boolean newUser,
                        @RequestParam(required = false) String user_name, @RequestParam(required = false) String contact) {
        if (!newUser) {
            Users user = loginService.getUser(Integer.parseInt(user_id));
            if (user != null) {
                loggedInUserId = user.getUserid();
                isAdmin = user.isAdmin();
                return "redirect:/main";
            } else {
                loginError = "User not found";
                return "redirect:/";
            }
        } else {
            Users user = new Users();
            user.setName(user_name);
            user.setContact(contact);
            loginService.saveUser(user);
            loggedInUserId = user.getUserid();
            return "redirect:/main";
        }
    }

    @GetMapping("/main")
    public String main(Model model, @RequestParam(required = false) String show) {
        model.addAttribute("isAdmin", isAdmin);
        boolean showBookings = show != null && show.equals("true");
        model.addAttribute("show", showBookings);
        if (showBookings) {
            model.addAttribute("cars", carSharingService.getAllCars().stream().sorted(Comparator.comparing(Cars::isAvailable).reversed()).toList());
        } else {
            model.addAttribute("cars", carSharingService.getAllAvailableCars());
        }
        model.addAttribute("id", loggedInUserId);
        return "main page";
    }

    @GetMapping("/main/{car_id}")
    public String reserveCar(@PathVariable int car_id, Model model) {
        model.addAttribute("car", carSharingService.getCar(car_id));
        model.addAttribute("id", loggedInUserId);
        model.addAttribute("isAdmin", isAdmin);
        return "reserve";
    }

    @PostMapping("/reserve")
    public String reserve(@RequestParam int car_id, @RequestParam int hours) {
        reservationError =  reservationService.reserveCar(car_id, loggedInUserId, hours);

        return "redirect:/reservations";
    }
    @GetMapping("/reservations")
    public String reservations(Model model) {
        model.addAttribute("bookings", reservationService.getBookings(loggedInUserId));
        model.addAttribute("css", carSharingService);
        model.addAttribute("error", reservationError);
        model.addAttribute("isAdmin", isAdmin);
        return "reservations";
    }
    @PostMapping("/cancel")
    public String cancel(@RequestParam int booking_id) {
        reservationService.cancelBooking(booking_id);
        reservationError = "Booking cancelled";
        return "redirect:/reservations";
    }
}
