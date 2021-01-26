package de.hsrm.mi.swtpro.pflamoehus.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import de.hsrm.mi.swtpro.pflamoehus.security.SecurityConfig.UserDetailServiceImpl;
import de.hsrm.mi.swtpro.pflamoehus.user.userservice.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import static de.hsrm.mi.swtpro.pflamoehus.security.SecurityContents.SECRET;
import java.util.Date;



/**
 * Function 1: generate a JWT from username, secret
 * Function 2: get email from JWT
 * Function 3: validate a JWT
 * 
 * @author Ann-Cathrin Fabian
 * @version 2
 */
@Component
public class JwtUtils {

    private static final Logger logger= LoggerFactory.getLogger(JwtUtils.class);

    @Value(SECRET)  
    private String jwtSecret;

    @Autowired
    UserService us;

    @Autowired
    UserDetailServiceImpl uds;

    // @Value(EXPIRATION_TIME)
    // private int jwtExpirationMs;

    /** 
     * Generates a JWT-token with the user and a secret key.
     * 
     * @param authentication represents the token for an authentication request
     * @return String
     */
    public String generateJwtToken(Authentication email){

    
        UserDetails userPrincipal = (UserDetails) email.getPrincipal();

        return Jwts.builder()
            .setSubject((userPrincipal.getUsername()))
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + 84600000))
            .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()), SignatureAlgorithm.HS512)
            .compact();
    }

    
    /** 
     * Gets the email of a user out of the Jwt token.
     * 
     * @param token given token
     * @return String
     */
    public String getUserNameFromJwtToken(String token){
        return Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(token).getBody().getSubject();
    }

    
    /** 
     * Validates a Jwt Token. Fields: invalid, unsupported, empty
     * 
     * @param authToken the given token
     * @return boolean
     */
    public boolean validateJwtToken(String authToken){
        try{
            Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(authToken);
            return true;
        }catch(MalformedJwtException e){
            logger.error("Invalid JWT token: " + e.getMessage());
        }catch(UnsupportedJwtException e){
            logger.error("JWT token is unsupported: " + e.getMessage());
        }catch(IllegalArgumentException e){
            logger.error("JWT claims string is empty: " + e.getMessage());
        }

        return false;
    }
    
}
