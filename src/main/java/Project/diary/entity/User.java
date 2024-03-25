package Project.diary.entity;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Entity
@Table(name = "Profile") // Table 이름 지정
@Data
@Builder
@AllArgsConstructor

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // pk 값

    @NotNull
    @Column(unique = true, length=10) //유일하고 최대 길이가 10.
    private String userid;

    @NotNull
    @Column(length=255)
    private String password;

    @Column(nullable = false)
    private String nickname;
//
//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY) // User 클래스의 user 필드를 매핑하고, 지연 로딩 방식 사용
//    private List<Diary> diaries;  // 사용자와 연관된 일기 목록 필드 추가


    protected User() {}

    public void modify(String nickname, String password) { // nickname 변경도 추가해야함
        this.nickname = nickname;
        this.password = password;
    }

}
