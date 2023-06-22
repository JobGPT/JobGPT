package security.demo.config;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import security.demo.config.auth.PrincipalDetailsService;
import security.demo.config.jwt.JwtAuthenticationFilter;
import security.demo.config.jwt.JwtAuthorizationFilter;
import security.demo.config.oauth.PrincipalOauth2UserService;
import security.demo.repository.UserRepository;

// 구글 로그인이 완료된 뒤의 후처리가 필요함  1.코드받기, 2.엑세스 토큰(권한)
// 3.사용자프로필 정보를 가져오고 4.정보를 토대로 회원가입을 자동으로 진행시킴
// 4-2 (이메일,전화번호,이름,아이디)쇼핑몰 -> (집주소),백화점몰 -> (vip등급, 일반등급)


@Configuration // IoC 빈(bean)을 등록
@EnableWebSecurity // 필터 체인 관리 시작 어노테이션
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) // @PreAuthorize("hasRole("~~") or hasRole("~~")") 활성화, 특정 주소 접근시 권한 및 인증을 위한 어노테이션 활성화 -> @Secured("ROLE_MANAGER") 이 롤 아니면 못들어감
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    PrincipalOauth2UserService principalOauth2UserService;
    @Autowired
    private CorsConfig corsConfig;
    private final UserRepository userRepository;


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable(); // scrf 비활성화
                http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .addFilter(corsConfig.corsFilter())
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(),userRepository))
                .authorizeRequests()
                .antMatchers("/user/**").authenticated() // user ~~로 들어오면 인증 필요
                //.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
                //.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') and hasRole('ROLE_USER')")
//                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')") // role_admin 권한 있어야 들어올 수 있음
                .anyRequest().permitAll() // 다른 url -> 모두 들어갈 수 있음
                .and()
                .formLogin()
                .loginPage("/login") // 권한이 없을 시 /login 페이지로 이동하게 함
                .loginProcessingUrl("/loginProc") // loginProc주소 호출되면 시큐리티가 낚아채서 대신 로그인 진행
                .defaultSuccessUrl("/")
                .and()
                .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint()
                .userService(principalOauth2UserService); // 구글 로그인이 완료된 뒤의 후처리가 필요함 -> 팁. 코드x, (엑세스 토큰 + 사용자 프로필 정보O)
    }
}
