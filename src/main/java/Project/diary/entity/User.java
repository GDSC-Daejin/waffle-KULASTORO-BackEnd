package Project.diary.entity;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Entity
@Table(name = "User") // Table 이름 지정
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class User {

    @Id
    private String userid;

    @NotNull
    @Column(length=255)
    private String password;

    @Column(nullable = false)
    private String nickname;


    // 다대일 관계 설정 (사용자가 쓴 일기를 보여주려고)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Diary> diaries;

    public void modify(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }

    public User(String userid, String password, String nickname) {
        this.userid = userid;
        this.password = password;
        this.nickname = nickname;
    }

}
