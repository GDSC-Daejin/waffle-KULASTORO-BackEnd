package Project.diary.dto;


import Project.diary.entity.Diary;
import Project.diary.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class DiaryRegisterDTO {
    private Long id; // 일기 고유번호

    private String title;

    private String context;

    // 감정은 나중에 생성되므로, 초기값으로 null 할당.
    private String emotion = null;

    // 나중에 감정이 생성되면 해당 일기의 감정을 업데이트하는 메서드를 추가
    // public void updateEmotion(String emotion) {
    //     this.emotion = emotion;
    // }

    private Date diarydate;



    // 감정 생성 이후에 일기에 감정을 할당하는 메서드.
    // public void assignEmotion(String emotion) {
    //     this.emotion = emotion;
    // }

    public Diary toEntity() {
        Diary diary = Diary.builder()
                .id(this.id)
                .context(this.context)
                .title(this.title)
                .emotion(this.emotion)
                .diarydate(this.diarydate)
                .build();

        return diary;
    }
}
