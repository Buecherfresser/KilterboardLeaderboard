package com.jonasdrechsel.kilterboardleaderboard.Controller;

import com.jonasdrechsel.kilterboardleaderboard.Data.KilterUser;
import com.jonasdrechsel.kilterboardleaderboard.Database.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@Controller
public class WebpageController {

    private final UserService userService;

    public WebpageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/Kilterleaderboard/user/{username}")
    public String getUserProfile(@PathVariable String username, Model model) {
        Optional<KilterUser> kilterUser = userService.getUserByUsername(username);
        if (kilterUser.isPresent()) {
            model.addAttribute("kilterUser", kilterUser.get());
            return "userTemplate";  // Name of the HTML template
        } else {
            return "error"; // Return an error page if user is not found
        }
    }

//    @GetMapping({"/kilterleaderboard", "/Kilterleaderboard"})
//    public String serveLeaderboard() {
//        // This will serve the index.html located in /static/Kilterleaderboard/
//        return "../static/Kilterleaderboard/index.html";
//    }

    @GetMapping({"/kilterleaderboard", "/Kilterleaderboard", "/Kilterleaderboard/", "/kilterleaderboard/"})
    public RedirectView redirectWithUsingRedirectView(
            RedirectAttributes attributes) {
//        attributes.addFlashAttribute("flashAttribute", "redirectWithRedirectView");
//        attributes.addAttribute("attribute", "redirectWithRedirectView");
        return new RedirectView("/Kilterleaderboard/index.html");
    }

}
