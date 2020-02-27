package dec.jwt.demo.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import dec.jwt.demo.util.TokenUtils;

/**
 * @Author: Decimon
 * @Date: 2020-02-25 09:41
 */
public class TokenService {


    public String getToken(String id) {
        // TODO: 2020-02-25
        String token="";
        token= JWT.create().withAudience(id)
                .sign(Algorithm.HMAC256(TokenUtils.getSecret(id)));
        return token;
    }
}
