package Project.diary.config;


import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2B);
    }


    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }



    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable).cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
//                        .requestMatchers( "/swagger-ui/**" , "view/join", "/status", "/auth/join", "/images/**", "auth/update", "auth/delete", "diary/**", "diary/list").permitAll() // 예외처리(인증없이도 들어가는)
                        .anyRequest().permitAll()// 어떤 요청이든 로그인해야함

                )
                .formLogin((formLogin) -> formLogin
                        .loginPage("/view/login") // 로그인페이지
                        .loginProcessingUrl("/login-proc") // 실제 로그인이 요청되는 로직(컨트롤러 대신 security 가 인터셉트)
                        .usernameParameter("userid")
                        .passwordParameter("pw")
                        .defaultSuccessUrl("/diary/list", true)
                        .permitAll()); // 대시보드 이용하기 위함
        httpSecurity
                .logout((logout) -> logout.logoutSuccessUrl("/auth/login")
                        .invalidateHttpSession(true)); // http 세션 초기화

        return httpSecurity.build();
    }
}


