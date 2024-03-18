/*
package Project.diary.config;

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

        // 여기서 차차 확장해나가야함
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserid())
                .password(user.getPassword())
                .build();
    }
}
*/
