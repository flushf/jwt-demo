package dec.jwt.demo.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import dec.jwt.demo.annotation.JwtLoginToken;
import dec.jwt.demo.annotation.PassToken;
import dec.jwt.demo.exception.AuthenticationException;
import dec.jwt.demo.util.TokenUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Objects;

/**
 * @Author: Decimon
 * @Date: 2020-02-25 09:27
 */
public class AuthenticationInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(JwtLoginToken.class)) {
            JwtLoginToken jwtLoginToken = method.getAnnotation(JwtLoginToken.class);
            if (jwtLoginToken.required()) {
                // 执行认证
                if (token == null) {
                    throw new AuthenticationException("无token，请重新登录");
                }
                // 获取 token 中的 user id
                String userId;
                try {
                    DecodedJWT decode = JWT.decode(token);
                    userId = decode.getAudience().get(0);
                    // 验证时间是否过期
                    Date expiresAt = decode.getExpiresAt();
                    if (expiresAt == null || expiresAt.before(new Date())) {
                        throw new AuthenticationException("token已过期");
                    }
                    if (!Objects.equals(decode.getClaim(TokenUtils.IP_KEY).asString(), httpServletRequest.getRemoteHost())) {
                        throw new AuthenticationException("ip与登录不一致");
                    }
                } catch (JWTDecodeException j) {
                    throw new AuthenticationException("401");
                }

                // 验证 token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(TokenUtils.getSecret(userId))).build();
                try {
                    jwtVerifier.verify(token);
                } catch (Exception e) {
                    throw new AuthenticationException("401");
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }
}
