package Project.diary.service;


import Project.diary.dto.UserRequestDTO;
import Project.diary.entity.User;
import Project.diary.DAO.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
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

    // 회원가입시 조건 체크
    @Transactional
    public Map<String, String> validateHandler(Errors errors) {
        Map<String, String> validResult = new HashMap<>();

        // 조건 실패한 필드 목록r
        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField()); // valid 접두사가 붙은 필드 이름 지정
            validResult.put(validKeyName, error.getDefaultMessage());
            log.debug("검증 오류 - 필드: {}, 메시지: {}", validKeyName, error.getDefaultMessage());
        }
        return validResult;
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

    @Transactional
    public void join(@RequestBody UserRequestDTO dto) {

        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(dto.toEntity());
    }

}

