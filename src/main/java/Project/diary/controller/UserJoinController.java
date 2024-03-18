package Project.diary.controller;

import Project.diary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class UserJoinController {

    private final UserService userService;


    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/auth/signup")
    public String join() {
        return "join.jsp";
    }


    @GetMapping("/auth/test")
    public String test() {
        return "posts/posts-read";
    }



    @GetMapping("/auth/login")
    public String login() {
        return "/user/user-login";
    }



}


