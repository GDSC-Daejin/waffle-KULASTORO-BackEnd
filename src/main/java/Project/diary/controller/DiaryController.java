package Project.diary.controller;

import Project.diary.dto.DiaryRegisterDTO;
import Project.diary.dto.DiaryResponseDTO;
import Project.diary.dto.UserRequestDTO;
import Project.diary.entity.CustomUser;
import Project.diary.entity.Diary;
import Project.diary.entity.DiaryCustomUser;
import Project.diary.entity.User;
import Project.diary.service.DiaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Positive;
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
    @GetMapping("/diary/list")
    @Operation(summary = "로그인한 사용자가 자신이 쓴 일기만 볼 수 있도록 일기들의 id 들을 반환")
    public String getUserDiaries(@AuthenticationPrincipal CustomUser customUser, Model model) {
        if (customUser == null) {
            throw new RuntimeException("로그인한 사용자 정보를 가져올 수 없습니다.");
        }

        List<Long> diaryIds = diaryService.getDiaryListById(customUser); // 이전에 작성한 getDiaryListById 메서드 호출

        model.addAttribute("diaryIds", diaryIds);

        return "diary_list";

    }

    @GetMapping("/diary/{id}")
    @Operation(summary = "일기 상세 페이지로 id를 통하여 값으로 이동")
    public String getDiaryById(@PathVariable Long id, Model model) {
        DiaryResponseDTO dto = diaryService.getDiaryById(id);
        model.addAttribute("diary", dto);
        return "diary_detail";
    }


    @GetMapping("/diary/diary_update/{id}")
    @Operation(summary = "일기 수정 id를 통하여 수정")
    public String diaryUpdate(@PathVariable Long id ,Model model) {
        DiaryResponseDTO dto = diaryService.getDiaryById(id);
        model.addAttribute("diary", dto); // 모델에 담아서 보여줄라고
        return "diary_update";
    }

    @Operation(summary = "일기 생성 페이지로 이동")
    @GetMapping("/diary/create_diary")
    public String userChange() {
        return "diary_create";
    }


    @Operation(summary = "일기 생성 로직")
    @Parameter(name = "title", description = "제목)")
    @Parameter(name = "context", description = "본문")
    @Parameter(name = "emotion", description = "감정 값 인데 일단은 null ")
    @Parameter(name = "diarydate", description = "현재 날짜 시간 ")
    @Parameter(name = "loggedid", description = "닉네임도 표시하게 할수는 있음")
    @PostMapping("/diary/create")
    @ApiResponse(responseCode = "201", description = "응답확인 ")
    public ResponseEntity<Diary> createDiary(@AuthenticationPrincipal CustomUser customUser, @RequestBody DiaryRegisterDTO dto) {
        String loggedId = customUser.getUsername();
        Diary createdDiary = diaryService.createDiary(dto, loggedId);

        // 성공적으로 생성된 경우 201(Created) 상태 코드와 함께 생성된 일기 객체를 반환합니다.
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDiary);
    }


    @Operation(summary = "일기 수정 로직")
    @Parameter(name = "title", description = "제목)")
    @Parameter(name = "context", description = "본문")
    @Parameter(name = "emotion", description = "감정 값 인데 일단은 null ")
    @Parameter(name = "diarydate", description = "수정된 날짜시간 값")
        @PutMapping("/diary/update")
    @ApiResponse(responseCode = "200", description = "성공 코드200")
        public ResponseEntity<String> updateDiary(@RequestBody DiaryRegisterDTO dto) {
        diaryService.updateDiary(dto);

        return new ResponseEntity<>("성공~", HttpStatus.OK);
    }

    @Operation(summary = "일기 삭제 로직 ")
    @DeleteMapping("/diary/{id}")
    @ApiResponse(responseCode = "200", description = "삭제 성공 코드 200")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        diaryService.deleteDiary(id);

        return ResponseEntity.status(HttpStatus.OK).body("일기가 성공적으로 삭제되었습니다.");

    }
}
