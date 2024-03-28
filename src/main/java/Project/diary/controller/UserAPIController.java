package Project.diary.controller;
import Project.diary.dto.UserRequestDTO;
import Project.diary.entity.CustomUser;
import Project.diary.DAO.UserRepository;
import Project.diary.service.UserService;
import Project.diary.validate.CheckUserNameValidate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RequiredArgsConstructor
@Controller
public class UserAPIController {

    @Autowired
    private UserService userService;

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final CheckUserNameValidate checkUserNameValidate;

    @InitBinder // 커스텀 유효성 검증을 위해 추가
    public void validatorBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(checkUserNameValidate);
    }


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


    @DeleteMapping("/auth/delete")
    public ResponseEntity<String> deleteUser(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();

        // 사용자 정보로 사용자를 찾아서 삭제
        userRepository.findByUserid(username).ifPresent(userRepository::delete);

        return ResponseEntity.ok("회원이 탈퇴되었습니다.");
    }



    @PostMapping("/auth/join")
    public String join(@Valid @RequestBody UserRequestDTO dto, Errors errors, Model model) {

        /*  회원가입 실패시 날라가는 거 방지 */
        if (errors.hasErrors()) {
            model.addAttribute("dto", dto);


       //   가입 조건 로직 통과 못한것들 핸들링

            Map<String, String> validatorResult = userService.validateHandler(errors);

            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }

            return "view/join"; // 회원가입 페이지로 다시 리턴
        }
        userService.join(dto);
        return "redirect:/auth/login";
    }


    }


