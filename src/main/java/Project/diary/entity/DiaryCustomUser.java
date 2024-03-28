package Project.diary.entity;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class DiaryCustomUser implements UserDetails {

    private String userid;
    private String password;
    private String nickname;

    public DiaryCustomUser(String userid, String password,String nickname) {
        this.userid = userid;
        this.password = password;
        this.nickname = nickname;
    }

    public User toUser() {
        User user = new User(userid, password, nickname);
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}