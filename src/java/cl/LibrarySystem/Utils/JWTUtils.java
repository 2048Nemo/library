package cl.LibrarySystem.Utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

public class JWTUtils {
    // 签名密钥
    private static final String SECRET = "!DAR$";

    /**
     * 生成token
     * @param  id,limit token携带的信息
     * @return token字符串
     */
    public static String getToken(String id,String password){
        // 指定token过期时间为7天
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 3);

        JWTCreator.Builder builder = JWT.create();
        String token = builder.withAudience(id)  // 作为token载荷
                .withExpiresAt(calendar.getTime())
                .sign(Algorithm.HMAC256(password));// 用户密码作为密钥
        return token;
    }


    /**
     * 解析token
     * @param token token字符串
     * @return 解析后的token
     */
    public static DecodedJWT decode(String token){
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        return decodedJWT;
    }
}

