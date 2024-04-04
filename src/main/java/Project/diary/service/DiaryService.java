package Project.diary.service;

import Project.diary.DAO.DiaryDAO;
import Project.diary.dto.DiaryRegisterDTO;
import Project.diary.dto.DiaryResponseDTO;
import Project.diary.entity.CustomUser;
import Project.diary.entity.Diary;
import Project.diary.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Locale.filter;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryDAO diaryDAO;

    private final UserService userService;

    @Transactional
    public List<Long> getDiaryListById(@AuthenticationPrincipal CustomUser customUser) {

        if (customUser == null) {
            throw new RuntimeException("로그인한 사용자 정보를 가져올 수 없습니다.");
        }

        String loggedId = customUser.getUsername();

        List<Diary> diariesByLoggedInUser = diaryDAO.findByUser_Userid(loggedId);

        return diariesByLoggedInUser.stream().map(Diary::getId).collect(Collectors.toList());

    }


    @Transactional
    public DiaryResponseDTO getDiaryById(Long id) {
        Diary diary = diaryDAO.findById(id).orElseThrow(() ->
                new IllegalArgumentException("잘못된 아이디입니다."));
        return new DiaryResponseDTO(diary);
    }

    @Transactional
    public Diary createDiary(DiaryRegisterDTO dto, String loggedId) {

        User user = userService.findOne(loggedId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Diary diary = Diary.builder()
                .context(dto.getContext())
                .title(dto.getTitle())
                .emotion(dto.getEmotion())
                .diarydate(dto.getDiarydate())
                .user(user) // 사용자 정보 할당
                .build();

        return diaryDAO.save(diary);

    }

    @Transactional
    public void  updateDiary(DiaryRegisterDTO updateDTO) {
        Diary diary = diaryDAO.findById(updateDTO.toEntity().getId()).
                orElseThrow(() -> new IllegalArgumentException("잘못된 일기 id 입니다."));
        diary.updateDiary(updateDTO.getTitle(), updateDTO.getContext(), new Date());
    }

    @Transactional
    public void deleteDiary(Long id) {
        diaryDAO.deleteById(id);
    }
}
