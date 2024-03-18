
package Project.diary.service;

import Project.diary.entity.User;
import Project.diary.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RegisterMemberService {
    private final UserRepository repository;


    public Long join(String userid, String password, String nickname) {
        User user = User.createUser(userid, password, nickname);
        validateDuplicateUser(user);
        repository.save(user);

        return user.getId();
    }

    private void validateDuplicateUser(User user) {
        repository.findByUserid(user.getUserid())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }
}

