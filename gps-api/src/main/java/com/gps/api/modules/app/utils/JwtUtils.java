
package com.gps.api.modules.app.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * jwt工具类
 *
 * @author Mark sunlightcs@gmail.com
 */
@ConfigurationProperties(prefix = "gps180.jwt")
@Component
@Data
@Slf4j
public class JwtUtils {

    private String secret;
    private long expirePhone;
    private long expireWeb;
    private String header;

    public String generateUserToken(long userId, long tokenId, String fromType) {
        return generateToken(userId + "", tokenId, "User", fromType);
    }

    public String generateDeviceToken(String imei, long tokenId, String fromType) {
        return generateToken(imei, tokenId, "Device", fromType);
    }

    /**
     * 生成jwt token
     */
    public String generateToken(String sub, long tokenId, String loginType, String fromType) {
        Date nowDate = new Date();
        long expire = fromType.equals("phone") ? expirePhone : expireWeb;
        //过期时间
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("logTyp", loginType)
                .setHeaderParam("fromTyp", fromType)
                .setHeaderParam("tokenId", tokenId)
                .setSubject(sub)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Jws<Claims> getJwsByToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token);
        } catch (Exception e) {
            log.debug("validate is token error ", e);
            return null;
        }
    }

    public Claims getClaimByToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.debug("validate is token error ", e);
            return null;
        }
    }

    /**
     * token是否过期
     *
     * @return true：过期
     */
    public boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }

    /**
     * 获取用户名从token中
     */
    public String getUsernameFromToken(String token) {
        return getClaimByToken(token).getSubject();
    }

    /**
     * 获取jwt发布时间
     */
    public Date getIssuedAtDateFromToken(String token) {
        return getClaimByToken(token).getIssuedAt();
    }

    /**
     * 获取jwt失效时间
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimByToken(token).getExpiration();
    }

    /**
     * 获取jwt接收者
     */
    public String getAudienceFromToken(String token) {
        return getClaimByToken(token).getAudience();
    }

    /**
     * 获取私有的jwt claim
     */
    public String getPrivateClaimFromToken(String token, String key) {
        return getClaimByToken(token).get(key).toString();
    }

}
