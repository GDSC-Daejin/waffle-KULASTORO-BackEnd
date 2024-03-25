package Project.diary.dto;

import Project.diary.entity.Diary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class DiaryResponseDTO {

    private String title;

    private String context;

    private Date diarydate;

    // 감정은 나중에 생성되므로, 초기값으로 null 할당.
    private String emotion = null;

    // 감정 생성 이후에 일기에 감정을 할당하는 메서드.
    // public void assignEmotion(String emotion) {
    //     this.emotion = emotion;


    public DiaryResponseDTO(Diary diary) {
        this.title = diary.getTitle();
        this.context = diary.getContext();
        this.diarydate = diary.getDiarydate();
        this.emotion = diary.getEmotion();
    }


}
