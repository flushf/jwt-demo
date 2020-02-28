package dec.jwt.demo.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import dec.jwt.demo.constant.SecretConstant;

import java.util.Date;

/**
 * @Author: Decimon
 * @Date: 2020-02-25 09:23
 */
public class TokenUtils {
    //过期时间，可以自己灵活配置
    private final static long EX_TIME = 3_600_000L;
    public final static String IP_KEY = "ip";
    private final static String SECRET = "hello";

    public static String getToken(String id) {
        String token = "";
        token = JWT.create().withAudience(AESSecretUtil.encryptToStr(id, SecretConstant.DATA_KEY))
                .withExpiresAt(new Date(System.currentTimeMillis() +EX_TIME))
                .sign(Algorithm.HMAC256(getSecret(id)));
        return token;
    }

    public static String getToken(String host,String id) {
        String token = "";
        token = JWT.create().withAudience(id)
                .withExpiresAt(new Date(System.currentTimeMillis() +EX_TIME)).withClaim(IP_KEY,host)
                .sign(Algorithm.HMAC256(getSecret(id)));
        return token;
    }

    public static String getSecret(String id) {
        return SECRET;
    }
}
