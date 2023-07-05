package jobGPT.test.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jobGPT.test.domain.security.repository.UserRepository;
import jobGPT.test.global.config.auth.PrincipalDetailsService;
import jobGPT.test.global.jwt.filter.JwtAuthenticationProcessingFilter;
import jobGPT.test.global.jwt.service.JwtService;
import jobGPT.test.global.login.filter.CustomJsonUsernamePasswordAuthenticationFilter;
import jobGPT.test.global.login.handler.LoginFailHandler;
import jobGPT.test.global.login.handler.LoginSuccessHandler;
import jobGPT.test.global.logout.handler.LogoutSuccessHandler;
import jobGPT.test.global.oauth2.PrincipalOauth2UserService;
import jobGPT.test.global.oauth2.handler.OAuth2LoginFailHandler;
import jobGPT.test.global.oauth2.handler.OAuth2LoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


// 구글 로그인이 완료된 뒤의 후처리가 필요함  1.코드받기, 2.엑세스 토큰(권한)
// 3.사용자프로필 정보를 가져오고 4.정보를 토대로 회원가입을 자동으로 진행시킴
// 4-2 (이메일,전화번호,이름,아이디)쇼핑몰 -> (집주소),백화점몰 -> (vip등급, 일반등급)


@Configuration // IoC 빈(bean)을 등록
@EnableWebSecurity // 필터 체인 관리 시작 어노테이션
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) // @PreAuthorize("hasRole("~~") or hasRole("~~")") 활성화, 특정 주소 접근시 권한 및 인증을 위한 어노테이션 활성화 -> @Secured("ROLE_MANAGER") 이 롤 아니면 못들어감
public class SecurityConfig extends WebSecurityConfigurerAdapter{


    private final PrincipalOauth2UserService principalOauth2UserService;
    private final JwtService jwtService;
    private final PrincipalDetailsService principalDetailsService;
    private final UserRepository userRepository;
    private final OAuth2LoginFailHandler oAuth2LoginFailHandler;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final ObjectMapper objectMapper;
    private final LogoutSuccessHandler logoutSuccessHandler;



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 기능 및 세션 비활성화
        http.csrf().disable();
                http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable()

                        // 필터 관리

                .addFilterAfter(customJsonUsernamePasswordAuthenticationFilter(), LogoutFilter.class)
                .addFilterBefore(jwtAuthenticationProcessingFilter(), CustomJsonUsernamePasswordAuthenticationFilter.class)

                        // 권한 관리
                .authorizeRequests()
                        .antMatchers("/user/**").authenticated() // user ~~로 들어오면 인증 필요
                //.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
                //.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') and hasRole('ROLE_USER')")
//                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')") // role_admin 권한 있어야 들어올 수 있음
                        .anyRequest().permitAll() // 다른 url -> 모두 들어갈 수 있음

                        // auth
                .and()
                .formLogin()
                .loginPage("/login") // 권한이 없을 시 /login 페이지로 이동하게 함
                .loginProcessingUrl("/api/login") // loginProc주소 호출되면 시큐리티가 낚아채서 대신 로그인 진행
                .defaultSuccessUrl("/")

                        // oAuth2
                .and()
                .oauth2Login()
                .loginPage("/login")
                        .successHandler(oAuth2LoginSuccessHandler) // 동의하고 계속하기를 눌렀을 때 Handler 설정
                        .failureHandler(oAuth2LoginFailHandler)
                .userInfoEndpoint()
                .userService(principalOauth2UserService);// 구글 로그인이 완료된 뒤의 후처리가 필요함 -> 팁. 코드x, (엑세스 토큰 + 사용자 프로필 정보O)

        http.logout()
                .logoutUrl("/api/logout")
                .logoutSuccessHandler(logoutSuccessHandler)
                .deleteCookies("JSESSIONID","remember-me");
        http.cors();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD","POST","GET","DELETE","PUT","OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.addExposedHeader("AccessToken");
        configuration.addExposedHeader("RefreshToken");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/login", configuration);
        source.registerCorsConfiguration("/api/logout", configuration);
        source.registerCorsConfiguration("/api/create/chatbox", configuration);
        source.registerCorsConfiguration("/api/create/chat", configuration);
        source.registerCorsConfiguration("/api/searchbox", configuration);
        source.registerCorsConfiguration("/api/deletebox", configuration);
        source.registerCorsConfiguration("/oauth2/authorization/naver", configuration);
        source.registerCorsConfiguration("/oauth2/authorization/google", configuration);
        source.registerCorsConfiguration("/api/logout", configuration);
        source.registerCorsConfiguration("/api/create/chatbox", configuration);
        source.registerCorsConfiguration("/api/create/chat", configuration);
        source.registerCorsConfiguration("/api/searchbox", configuration);
        source.registerCorsConfiguration("/api/deletebox", configuration);
        return source;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(principalDetailsService);
        return new ProviderManager(provider);
    }

    /**
     * 로그인 성공 시 호출되는 LoginSuccessJWTProviderHandler 빈 등록
     */
    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler(jwtService, userRepository);
    }

    /**
     * 로그인 실패 시 호출되는 LoginFailureHandler 빈 등록
     */
    @Bean
    public LoginFailHandler loginFailureHandler() {
        return new LoginFailHandler();
    }

    @Bean
    public CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordAuthenticationFilter() {
        CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordLoginFilter
                = new CustomJsonUsernamePasswordAuthenticationFilter(objectMapper);
        customJsonUsernamePasswordLoginFilter.setAuthenticationManager(authenticationManager());
        customJsonUsernamePasswordLoginFilter.setAuthenticationSuccessHandler(loginSuccessHandler());
        customJsonUsernamePasswordLoginFilter.setAuthenticationFailureHandler(loginFailureHandler());
        return customJsonUsernamePasswordLoginFilter;
    }

    @Bean
    public JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter() {
        JwtAuthenticationProcessingFilter jwtAuthenticationFilter = new JwtAuthenticationProcessingFilter(jwtService, userRepository);
        return jwtAuthenticationFilter;
    }

}