package Project.diary.controller;

import Project.diary.dto.UserJoinDTO;
import Project.diary.dto.UserRequestDTO;
import Project.diary.entity.CustomUser;
import Project.diary.entity.User;
import Project.diary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Controller
public class UserController {

    @Autowired
    private UserService userService;


    private final AuthenticationManager authenticationManager;


    @GetMapping("/view/join")
    public String joinPage() {
        return "join";
    }

    @GetMapping("/view/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/view/user_change")
    public String userChange(@AuthenticationPrincipal CustomUser customUser, Model model) {
        model.addAttribute("login_nickname", customUser.getNickname());
        model.addAttribute("login_userid" , customUser.getUsername());
        return "user_change";
    }

    @PutMapping("/auth/update")
    public ResponseEntity<String> modifyUser(@RequestBody UserRequestDTO dto) {
        userService.UserModify(dto);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUserid(), dto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseEntity<>("성공~", HttpStatus.OK);
    }



}
