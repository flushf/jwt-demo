package dec.jwt.demo.constant;

/**
 * @Author: Decimon
 * @Date: 2020-02-28 09:10
 */
public class SecretConstant {
    //签名秘钥 自定义
    public static final String BASE64SECRET = "hello world";

    //超时毫秒数（默认30分钟）
    public static final int EXPIRESSECOND = 1800000;

    //用于JWT加密的密匙 自定义
    public static final String DATA_KEY = "hello world";

}
