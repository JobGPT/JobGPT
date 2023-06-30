package jobGPT.test.global.jwt.annotation;

import jobGPT.test.domain.security.Entity.User;
import jobGPT.test.domain.security.repository.UserRepository;
import jobGPT.test.global.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.info("start supportsParameter");
        boolean hasAno = parameter.hasParameterAnnotation(JwtAuth.class);
        boolean hasUserType = User.class.isAssignableFrom(parameter.getParameterType());
        return hasAno && hasUserType;
    }

    // supportsParameter 가 true 면 실행됨
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        log.info("start resolveArgument");
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        if (request.getHeader("AccessToken") == null) {
            String authorizationHeader = request.getHeader("RefreshToken");
            String token = authorizationHeader.split(" ")[1];
            return userRepository.findByRefreshToken(token).get();
        } else {
            String authorizationHeader = request.getHeader("AccessToken");
            String token = authorizationHeader.split(" ")[1];
            Optional<String> username = jwtService.extractUsername(token);
            return userRepository.findByUsername(username.get()).get();
        }

    }
}
