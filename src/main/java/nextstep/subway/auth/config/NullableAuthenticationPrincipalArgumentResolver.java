package nextstep.subway.auth.config;

import nextstep.subway.auth.application.JwtTokenProvider;
import nextstep.subway.auth.application.dto.LoginMember;
import nextstep.subway.auth.application.dto.NonLoginMember;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class NullableAuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {
    private final JwtTokenProvider jwtTokenProvider;

    public NullableAuthenticationPrincipalArgumentResolver(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(NullableAuthenticationPrincipal.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String authorization = webRequest.getHeader("Authorization");

        if (authorization == null
                || authorization.split(" ").length < 2
                || !"bearer".equalsIgnoreCase(authorization.split(" ")[0])) {
            return new NonLoginMember();
        }

        String token = authorization.split(" ")[1];
        String email = jwtTokenProvider.getPrincipal(token);

        return new LoginMember(email);
    }
}
