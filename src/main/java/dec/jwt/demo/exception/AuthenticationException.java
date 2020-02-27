package dec.jwt.demo.exception;

/**
 * @Author: Decimon
 * @Date: 2020-02-25 09:59
 */
public class AuthenticationException extends Exception {

    public AuthenticationException(String message) {
        super(message);
    }
}
