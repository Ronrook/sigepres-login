package com.sigepres.servicioweb.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    /**
     * Llave para cifrar el jwt, recibe su valor de application.properties
     */
    @Value("${jwt.secret.key}")
    private static String SECRET_KEY;

    public String getToken(UserDetails user) {
        return getToken(new HashMap<>(), user);
    }

    /**
     * Crea un token JWT con reclamaciones adicionales, nombre de usuario, fecha de emisión
     * y fecha de vencimiento, y lo firma con una clave.
     */
    private String getToken(Map<String,Object> extraClaims, UserDetails user) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Obtiene y devuelve una clave a partir de la clave secreta codificada en base64.
     */
    private Key getKey() {
        byte[] keyBytes= Decoders.BASE64.decode(SECRET_KEY = "mmmmmmmmmm");
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Obtiene y devuelve el nombre de usuario contenido en un token JWT verificando las
     * reclamaciones (claims) del token.
     */
    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    /**
     * Verifica si un token es válido comparando el nombre de usuario del token con el
     * nombre de usuario del objeto UserDetails y asegurándose de que el token no haya
     * expirado.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username=getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
    }

    /**
     * Obtiene y devuelve todas las reclamaciones (claims) contenidas en un token JWT
     * después de verificar la firma del token con la clave correspondiente.
     */
    private Claims getAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Obtiene y devuelve todas las reclamaciones (claims) contenidas en un token JWT
     * después de verificar la firma del token con la clave correspondiente.
     */
    public <T> T getClaim(String token, Function<Claims,T> claimsResolver) {
        final Claims claims=getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Obtiene y devuelve la fecha de vencimiento de un token JWT verificando las
     * reclamaciones (claims) del token.
     */
    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    /**
     * Verifica si un token JWT ha expirado comparando su fecha de vencimiento con la
     * fecha actual.
     */
    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }

}
