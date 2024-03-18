package Project.diary.controller;

import Project.diary.dto.UserJoinDTO;
import Project.diary.service.RegisterMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthorizationController {

    private final RegisterMemberService registerMemberService;


    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody UserJoinDTO dto) {
        try {
            registerMemberService.join(dto.getUserid(), dto.getPw(), dto.getNickname());
            return ResponseEntity.ok("join success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}