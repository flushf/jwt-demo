package dec.jwt.demo.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Decimon
 * @Date: 2020-02-25 09:55
 * 统一异常处理
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 统一处理AuthenticationException异常
     *
     * @param response
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity globalException(HttpServletResponse response, AuthenticationException ex) {
        //返回未认证状态码
        return new ResponseEntity("please login",HttpStatus.UNAUTHORIZED);
    }
}
