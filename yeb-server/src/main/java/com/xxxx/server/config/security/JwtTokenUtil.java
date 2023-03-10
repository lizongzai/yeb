package com.xxxx.server.config.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * JWT工具类
 *
 * @author lizongzai
 * @since 1.0.0
 */

@Component
public class JwtTokenUtil {

  private static final String CLAIM_KEY_USERNAME = "sub";
  private static final String CLAIM_KEY_CREATED = "created";
  @Value("${jwt.secret}")
  private String secret;
  @Value("${jwt.expiration}")
  private long expiration;

  /**
   * 根据用户信息生成token
   *
   * @param userDetails
   * @return
   */
  public String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
    claims.put(CLAIM_KEY_CREATED, new Date());
    return generateToken(claims);
  }

  /**
   * 根据荷载生成token
   *
   * @param claims
   * @return
   */
  private String generateToken(Map<String, Object> claims) {
    return Jwts.builder().setClaims(claims).setExpiration(generateExpirationDate())
        .signWith(SignatureAlgorithm.HS512, secret).compact();
  }

  /**
   * 生成token过期时间
   *
   * @return
   */
  private Date generateExpirationDate() {
    return new Date(System.currentTimeMillis() + expiration * 1000);
  }

  /**
   * 从token中获取用户
   *
   * @param token
   * @return
   */
  public String getUserNameFromToken(String token) {
    String username = null;
    try {
      Claims claims = getClaimsFromToken(token);
      username = claims.getSubject();
    } catch (Exception e) {
      username = null;
    }
    return username;
  }

  /**
   * 从token中获取荷载
   *
   * @param token
   * @return
   */
  private Claims getClaimsFromToken(String token) {
    Claims claims = null;
    try {
      claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    } catch (Exception e) {
      claims = null;
    }
    return claims;
  }

  /**
   * 验证token是否有效
   *
   * @param token
   * @param userDetails
   * @return
   */
  public boolean validateToken(String token, UserDetails userDetails) {
    String username = getUserNameFromToken(token);
    return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
  }

  /**
   * 判断token是否有效
   *
   * @param token
   * @return
   */
  private boolean isTokenExpired(String token) {
    Date expiredDate = getExpiredDateFromToken(token);
    return expiredDate.before(new Date());
  }

  /**
   * 从token中获取过期时间
   *
   * @param token
   * @return
   */
  private Date getExpiredDateFromToken(String token) {
    Claims claims = getClaimsFromToken(token);
    return claims.getExpiration();
  }

  /**
   * 判断token是否可以刷新
   *
   * @param token
   * @return
   */
  public boolean canRefresh(String token) {
    return !isTokenExpired(token);
  }

  /**
   * 重新刷新token
   *
   * @param userDetails
   * @return
   */
  public String refreshToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    claims.put(CLAIM_KEY_CREATED, new Date());
    return generateToken(claims);
  }
}