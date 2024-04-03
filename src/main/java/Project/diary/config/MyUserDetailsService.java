package Project.diary.config;

import Project.diary.entity.CustomUser;
import Project.diary.entity.User;
import Project.diary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String insertedUserName) throws UsernameNotFoundException {
        User user = userService.findOne(insertedUserName)
                .orElseThrow(() -> new UsernameNotFoundException("회원이 존재하지 않습니다!"));

        String nickname = user.getNickname();

        CustomUser customUser = new CustomUser(
                user.getUserid(),
                user.getPassword(),
                user.getNickname()
        );

        return customUser;
    }
}

