package Project.diary.controller;

import Project.diary.dto.DiaryRegisterDTO;
import Project.diary.dto.DiaryResponseDTO;
import Project.diary.dto.UserRequestDTO;
import Project.diary.entity.CustomUser;
import Project.diary.entity.Diary;
import Project.diary.entity.DiaryCustomUser;
import Project.diary.entity.User;
import Project.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;



    /**
     * 일기는 캘린더형식으로 넣으니까 id만 가져오도록
     */


//    @GetMapping("/diary/list")
//    public String getDiaryById(Model model) {
//        List<Long> diaryId = diaryService.getDiaryById();
//        model.addAttribute("diary", diaryId);
//        return "diary_list";
//    }

    @GetMapping("/diary/{id}")
    public String getDiaryById(@PathVariable Long id, Model model) {
        DiaryResponseDTO dto = diaryService.getDiaryById(id);
        model.addAttribute("diary", dto);
        return "diary_detail";
    }

    @GetMapping("/diary/list")
    public String getUserDiaries(@AuthenticationPrincipal CustomUser customUser, Model model) {
        if (customUser == null) {
            throw new RuntimeException("로그인한 사용자 정보를 가져올 수 없습니다.");
        }

        List<Long> diaryIds = diaryService.getDiaryListById(customUser); // 이전에 작성한 getDiaryListById 메서드 호출

        model.addAttribute("diaryIds", diaryIds);

        return "diary_list";

    }

    @GetMapping("/diary/diary_update/{id}")
    public String diaryUpdate(@PathVariable Long id ,Model model) {
        DiaryResponseDTO dto = diaryService.getDiaryById(id);
        model.addAttribute("diary", dto); // 모델에 담아서 보여줄라고
        return "diary_update";
    }

    @GetMapping("/diary/create_diary")
    public String userChange() {
        return "diary_create";
    }


    @PostMapping("/diary/create")
    public Diary createDiary(@AuthenticationPrincipal CustomUser customUser, @RequestBody DiaryRegisterDTO dto) {

        String loggedId = customUser.getUsername();
        return diaryService.createDiary(dto, loggedId);

    }



    @PutMapping("/diary/update")
    public ResponseEntity<String> updateDiary(@RequestBody DiaryRegisterDTO dto) {
        diaryService.updateDiary(dto);

        return new ResponseEntity<>("성공~", HttpStatus.OK);
    }

    @DeleteMapping("/diary/{id}")
    public void deletePost(@PathVariable Long id) {
        diaryService.deleteDiary(id);
    }
}
