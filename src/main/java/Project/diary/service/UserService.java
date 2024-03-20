package Project.diary.service;


import Project.diary.dto.UserRequestDTO;
import Project.diary.entity.User;
import Project.diary.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public Optional<User> findOne(String userid) {
        return userRepository.findByUserid(userid);
    }

    public boolean isValidMember(String userId, String password) {
        Optional<User> member = findOne(userId);
        if (member.isPresent()) {
            return member.get().getPassword().equals(password);
        }
        return false;
    }

    //  회원수정(더티 체킹)
    @Transactional
    public void UserModify(UserRequestDTO dto) {
        User user = userRepository.findByUserid(dto.toEntity().getUserid()).orElseThrow(() ->
        new IllegalArgumentException("해당 회원은 존재하지 않습니다"));
;

        String encRawPassword = passwordEncoder.encode(dto.getPassword());
        user.modify(dto.getNickname(), encRawPassword);
    }


}

