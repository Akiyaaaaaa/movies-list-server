package dev.akiyaaa.moviesreview.utils.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;
import java.util.Base64;


public class JwtSecretGenerator {
    private static final Logger logger = LoggerFactory.getLogger(JwtSecretGenerator.class);
    
    public static String generateSecret(){
        byte[] secret = new byte[32];
        
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(secret);
        String jwtSecret = Base64.getEncoder().encodeToString(secret);
        return jwtSecret;
    }
}
