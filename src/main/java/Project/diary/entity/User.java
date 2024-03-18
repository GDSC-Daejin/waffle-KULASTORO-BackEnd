package Project.diary.entity;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    @Column(length=12) // 최대길이 12
    private String password;

    @Column(nullable = false)
    private String nickname;

    protected User() {}

    public static User createUser(String userid, String password,  String nickname) {
        return new User(null, userid, password, nickname);
    }


}
