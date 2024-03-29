package Project.diary.controller;
import Project.diary.dto.UserRequestDTO;
import Project.diary.entity.CustomUser;
import Project.diary.DAO.UserRepository;
import Project.diary.service.UserService;
import Project.diary.validate.CheckUserNameValidate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
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
    @Operation(summary = "회원가입 페이지로 이동")
    public String joinPage() {
        return "join";
    }

    @GetMapping("/view/login")
    @Operation(summary = "로그인 페이지로 이동")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/view/user_change")
    @Operation(summary = "회원 수정가입 수정 페이지로 이동하는 로직")

    public String userChange(@AuthenticationPrincipal CustomUser customUser, Model model) {
                model.addAttribute("login_nickname", customUser.getNickname());
        model.addAttribute("login_userid" , customUser.getUsername());
        return "user_change";
    }



    @PutMapping("/auth/update")
    @Operation(summary = "회원 수정가입 로직을 진행")
    @Positive(message = "유저 ID는 변경 안됩니다!.")
    @Parameter(name = "login_userid", description = "로그인 유저 id 값(변경불가)")
    @Parameter(name = "login_nickname", description = "로그인 유저 닉네임 값")
    @Parameter(name = "password", description = "비밀번호 값")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = UserRequestDTO.class))}),
            @ApiResponse(responseCode = "404", description = "해당 ID의 유저가 존재하지 않습니다."),
            @ApiResponse(responseCode = "404", description = "해당 ID의 유저가 존재하지 않습니다.")
    })
    public ResponseEntity<String> modifyUser(@RequestBody UserRequestDTO dto) {
        userService.UserModify(dto);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUserid(), dto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseEntity<>("성공~", HttpStatus.OK);
    }

    @Operation(summary = "회원탈퇴 진행")
    @DeleteMapping("/auth/delete")
    public ResponseEntity<String> deleteUser(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();

        // 사용자 정보로 사용자를 찾아서 삭제
        userRepository.findByUserid(username).ifPresent(userRepository::delete);

        return ResponseEntity.ok("회원이 탈퇴되었습니다.");
    }



    @PostMapping("/auth/join")
    @Operation(summary = "회원가입 로직 진행")
    @Parameter(name = "userid", description = "아이디는 중복불가!", example = "fbgmltn12")
    @Parameter(name = "password", description = "비밀번호는 영어 소문자, 대문자, 특수문자 8자리 이상!" , example = "1q2w3e4R!")
    @Parameter(name = "nickname", description = "자유롭게 닉네임", example = "새싹이")
    @Parameter(name = "valid ~", description = "에러 메시지는 valid로 시작됩니다. Map형식으로 되어 있어서 valid로 시작하는거 키 : 그에 따른 메시지를 벨류로 하면 될거같아요")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = UserRequestDTO.class))}),
            @ApiResponse(responseCode = "404", description = "해당 ID의 유저가 존재하지 않습니다."),
    })


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


