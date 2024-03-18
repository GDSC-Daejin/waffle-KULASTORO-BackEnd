package Project.diary.service;


import Project.diary.entity.User;
import Project.diary.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;


    public Optional<User> findOne(String userid) {
        return userRepository.findByUserid(userid);
    }
}

