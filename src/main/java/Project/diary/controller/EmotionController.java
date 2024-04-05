package Project.diary.controller;


import Project.diary.dto.DiaryRegisterDTO;
import Project.diary.dto.DiaryResponseDTO;
import Project.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

@RestController
@RequiredArgsConstructor
public class EmotionController {

    @Autowired
    private DiaryService diaryService;

    @PostMapping("/analyze-diary/{diaryId}")
    public ResponseEntity<String> analyzeDiary(@PathVariable Long diaryId) {
        DiaryResponseDTO diaryResponseDTO = diaryService.getDiaryById(diaryId);
        String diaryContent = diaryResponseDTO.getContext();
        String analysisResult = diaryService.extractEmotionFromDiary(diaryContent);

        diaryService.saveDiaryEmotion(diaryId, analysisResult);
        return new ResponseEntity<>(analysisResult, HttpStatus.OK);
    }


}




