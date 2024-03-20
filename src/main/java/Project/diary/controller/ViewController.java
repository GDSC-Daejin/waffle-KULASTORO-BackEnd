package Project.diary.controller;

import Project.diary.entity.CustomUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class ViewController {



    @GetMapping("/dashboard")
    public String dashboardPage(@AuthenticationPrincipal CustomUser customUser,  Model model) {
        model.addAttribute("login_nickname", customUser.getNickname());
        // nickname 으로 보이게 표시

        return "dashboard";
    }


}