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

public class DiaryRegisterDTO {
    private Long id; // 일기 고유번호

    private String title;

    private String context;

    // 감정은 나중에 생성되므로, 초기값으로 null 할당.
    private String emotion = null;

    private Date diarydate;

    private String userId; // 사용자 ID



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
