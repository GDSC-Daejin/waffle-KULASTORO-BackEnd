package Project.diary.controller;

import Project.diary.entity.CustomUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
@CrossOrigin(originPatterns = {"http://localhost:3000"})

public class ViewController {

    @GetMapping("/dashboard")
    @Operation(summary = "혹시 메인페이지 만드실까 해서 만든것 + 로그인했을때 닉네임으로 보이게 테스트 완료")

    @Parameter(name = "login_nickname", description = "로그인 유저 닉네임 값", example = "닉네임123", required = true)
    public String dashboardPage(@AuthenticationPrincipal CustomUser customUser,  Model model) {


                model.addAttribute("login_nickname", customUser.getNickname());
        // nickname 으로 보이게 표시

        return "dashboard";
    }


}