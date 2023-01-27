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
//
//@Component
//public class JwtTokenUtil {
//
//  private static final String CLAIM_KEY_USERNAME = "sub";
//  private static final String CLAIM_KEY_CREATED = "created";
//  @Value("${jwt.secret}")
//  private String secret;
//  @Value("${jwt.expiration}")
//  private long expiration;
//
//  /**
//   * 根据用户信息生成token
//   *
//   * @param userDetails
//   * @return
//   */
//  public String generateToken(UserDetails userDetails) {
//    Map<String, Object> claims = new HashMap<>();
//    claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
//    claims.put(CLAIM_KEY_CREATED, new Date());
//    return generateToken(claims);
//  }
//
//  /**
//   * 根据荷载生成token
//   *
//   * @param claims
//   * @return
//   */
//  private String generateToken(Map<String, Object> claims) {
//    return Jwts.builder().setClaims(claims).setExpiration(generateExpirationDate())
//        .signWith(SignatureAlgorithm.HS512, secret).compact();
//  }
//
//  /**
//   * 生成token过期时间
//   *
//   * @return
//   */
//  private Date generateExpirationDate() {
//    return new Date(System.currentTimeMillis() + expiration * 1000);
//  }
//
//  /**
//   * 从token中获取用户
//   *
//   * @param token
//   * @return
//   */
//  public String getUsernameFromToken(String token) {
//    String username = null;
//    try {
//      Claims claims = getClaimsFromToken(token);
//      username = claims.getSubject();
//    } catch (Exception e) {
//      username = null;
//    }
//    return username;
//  }
//
//  /**
//   * 从token中获取荷载
//   *
//   * @param token
//   * @return
//   */
//  private Claims getClaimsFromToken(String token) {
//    Claims claims = null;
//    try {
//      claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
//    } catch (Exception e) {
//      claims = null;
//    }
//    return claims;
//  }
//
//  /**
//   * 验证token是否有效
//   *
//   * @param token
//   * @param userDetails
//   * @return
//   */
//  public boolean validateToken(String token, UserDetails userDetails) {
//    String username = getUsernameFromToken(token);
//    return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
//  }
//
//  /**
//   * 判断token是否有效
//   *
//   * @param token
//   * @return
//   */
//  private boolean isTokenExpired(String token) {
//    Date expiredDate = getExpiredDateFromToken(token);
//    return expiredDate.before(new Date());
//  }
//
//  /**
//   * 从token中获取过期时间
//   *
//   * @param token
//   * @return
//   */
//  private Date getExpiredDateFromToken(String token) {
//    Claims claims = getClaimsFromToken(token);
//    return claims.getExpiration();
//  }
//
//  /**
//   * 判断token是否可以刷新
//   *
//   * @param token
//   * @return
//   */
//  public boolean canRefresh(String token) {
//    return !isTokenExpired(token);
//  }
//
//  /**
//   * 重新刷新token
//   *
//   * @param userDetails
//   * @return
//   */
//  public String refreshToken(UserDetails userDetails) {
//    Map<String, Object> claims = new HashMap<>();
//    claims.put(CLAIM_KEY_CREATED, new Date());
//    return generateToken(claims);
//  }
//}


@Component
public class JwtTokenUtil {

  private static final String CLAIM_KEY_USERNAME = "sub";
  private static final String CLAIM_KEY_CREATED = "created";
  @Value("${jwt.secret}")
  private String secret;
  @Value("${jwt.expiration}")
  private long expiration;

  /**
   * @return java.lang.String
   * @Author lizongzai
   * @Description //TODO 根据用户信息生成token
   * @Date 2023/1/4 21:13
   * @Param [userDetails]
   **/
  public String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>(16);
    claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
    claims.put(CLAIM_KEY_CREATED, new Date());
    return generateToken(claims);
  }

  /**
   * @return java.lang.String
   * @Author lizongzai
   * @Description //TODO  根据负载生成JWT Token
   @Date 2023/1/4 21:13
    * @Param [claims]
   **/
  private String generateToken(Map<String, Object> claims) {
    return Jwts.builder()
        .setClaims(claims)
        .setExpiration(generateExpirationDate())
        .signWith(SignatureAlgorithm.HS512, secret)
        .compact();
  }

  /**
   * @return java.util.Date
   * @Author lizongzai
   * @Description //TODO 生成token过期时间
   * @Date 2023/1/4 21:13
   * @Param []
   **/
  private Date generateExpirationDate() {
    return new Date(System.currentTimeMillis() + expiration * 1000);
  }

  /**
   * @return java.lang.String
   * @Author lizongzai
   * @Description //TODO 从token中获取登录用户名
   * @Date 2023/1/4 21:13
   * @Param [token]
   **/
  public String getUsernameFromToken(String token) {
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
   * @return io.jsonwebtoken.Claims
   * @Author lizongzai
   * @Description //TODO 从token中获取JWT中的负载
   * @Date 2023/1/4 21:13
   * @Param [token]
   **/
  private Claims getClaimsFromToken(String token) {
    Claims claims = null;
    try {
      claims = Jwts.parser()
          .setSigningKey(secret)
          .parseClaimsJws(token)
          .getBody();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return claims;
  }

  /**
   * @return java.lang.boolean
   * @Author lizongzai
   * @Description //TODO 验证 token 是否有效
   * @Date 2023/1/4 21:13
   * @Param [userDetails, token]
   **/
  public boolean validateToken(String token, UserDetails userDetails) {
    String userName = getUsernameFromToken(token);
    return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
  }

  /**
   * @return java.lang.boolean
   * @Author lizongzai
   * @Description //TODO 判断token是否失效
   * @Date 2023/1/4 21:13
   * @Param [token]
   **/
  private boolean isTokenExpired(String token) {
    Date expiredDate = getExpiredDateFromToken(token);
    return expiredDate.before(new Date());
  }

  /**
   * @return java.util.Date
   * @Author lizongzai
   * @Description //TODO 从token中获取过期时间
   * @Date 2023/1/4 21:13
   * @Param [token]
   **/
  private Date getExpiredDateFromToken(String token) {
    Claims claims = getClaimsFromToken(token);
    return claims.getExpiration();
  }

  /**
   * @return java.lang.boolean
   * @Author lizongzai
   * @Description //TODO 判断token是否可以被刷新
   * @Date 2023/1/4 21:13
   * @Param [token]
   **/
  public boolean canRefresh(String token) {
    return !isTokenExpired(token);
  }

  /**
   * @return java.lang.String
   * @Author lizongzai
   * @Description //TODO 刷新token
   * @Date 2023/1/4 21:13
   * @Param [token]
   **/
  public String refreshToken(String token) {
    Claims claims = getClaimsFromToken(token);
    claims.put(CLAIM_KEY_CREATED, new Date());
    return generateToken(claims);
  }

}

