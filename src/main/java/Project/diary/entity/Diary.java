package Project.diary.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "Diary") // Table 이름 지정
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 일기 고유번호

    @Column
    private String context;

    @Column
    private String title;

    @Column
    private String emotion;

    @Column
    private Date diarydate;

    @ManyToOne(fetch = FetchType.LAZY) // 다대일 관계 한 사용자가 일기를 여러개 쓸수 있으니까
    @JoinColumn(name = "userid")

    private User user; // 사용자와 연결


    public void updateDiary(String title, String context, Date diarydate) {
        this.title = title;
        this.context = context;
        this.diarydate = diarydate;
    }


}
