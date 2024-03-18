//package Project.diary.config;
//
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//import static org.springframework.transaction.TransactionDefinition.withDefaults;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//public class SpringSecurityConfig {
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//    @Bean
//    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                .csrf(AbstractHttpConfigurer::disable) // csrf 잠시 비활성화
//                .authorizeHttpRequests((authorize) -> authorize
//                        .requestMatchers("/", "/auth/**", "/user/**").permitAll() // 일단 모든 권한 허가 설정
//                        .anyRequest().permitAll()
//                )
//                .formLogin((formLogin) -> formLogin
//                        .loginPage("/auth/login") // 로그인페이지
//                        .loginProcessingUrl("/login-proc") // 실제 로그인이 요청되는 로직(컨트롤러 대신 security 가 인터셉트)
//                        .defaultSuccessUrl("/")
//                        .permitAll()); // 성공시 페이지
//        httpSecurity
//                .logout((logout) -> logout.logoutSuccessUrl("/auth/login")
//                        .invalidateHttpSession(true)); // http 세션 초기화
//        return httpSecurity.build();
//    }
//}
