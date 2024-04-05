package Project.diary.service;

import Project.diary.DAO.DiaryDAO;
import Project.diary.dto.DiaryRegisterDTO;
import Project.diary.dto.DiaryResponseDTO;
import Project.diary.entity.CustomUser;
import Project.diary.entity.Diary;
import Project.diary.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Locale.filter;

@Service
@RequiredArgsConstructor
@Slf4j
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
    public void updateDiary(DiaryRegisterDTO updateDTO) {
        Diary diary = diaryDAO.findById(updateDTO.toEntity().getId()).
                orElseThrow(() -> new IllegalArgumentException("잘못된 일기 id 입니다."));
        diary.updateDiary(updateDTO.getTitle(), updateDTO.getContext(), new Date());
    }

    @Transactional
    public void deleteDiary(Long id) {
        diaryDAO.deleteById(id);
    }


    public String extractEmotionFromDiary(String diaryContent) {
        StringBuilder responseContent = new StringBuilder();
        String emotion = ""; // 감정 변수 초기화

        try {
            URL url = new URL("https://ml.hsmarco.kr/sentence?text=" + URLEncoder.encode(diaryContent, "UTF-8"));
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    responseContent.append(inputLine);
                }

                in.close();

                // 감정을 추출하는 로직을 추가하고, emotion 변수에 할당
                emotion = extractEmotionFromResponse(responseContent.toString());

            } else {
                log.error("HTTP GET request failed with response code {}", responseCode);
            }
        } catch (IOException e) {
            log.error("Error occurred while sending HTTP request: {}", e.getMessage());
        }

        return emotion;
    }


    private String extractEmotionFromResponse(String responseContent) {
        // 이 예제에서는 단순히 분석 결과를 그대로 감정으로 반환합니다.
        // 분석 결과에서 감정을 추출하는 코드를 작성하세요.
        return responseContent;
    }


    @Transactional
    public void saveDiaryEmotion(Long diaryId, String emotion) {
        Diary diary = diaryDAO.findById(diaryId).orElseThrow(() -> new NotFoundException("Diary not found"));
        diary.setEmotion(emotion);
        diaryDAO.save(diary);
    }

}

