package org.example.gymtrackerdemo.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private static final String HMAC_SHA256 = "HmacSHA256";

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final byte[] secretKey;
    private final long expirationSeconds;

    public JwtUtil(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.expiration-seconds:86400}") long expirationSeconds
    ) {
        this.secretKey = secret.getBytes(StandardCharsets.UTF_8);
        this.expirationSeconds = expirationSeconds;
    }

    public String generateToken(String email) {
        long now = Instant.now().getEpochSecond();

        Map<String, Object> header = Map.of("alg", "HS256", "typ", "JWT");
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("sub", email);
        payload.put("exp", now + expirationSeconds);

        String encodedHeader = encode(header);
        String encodedPayload = encode(payload);
        String unsignedToken = encodedHeader + "." + encodedPayload;

        return unsignedToken + "." + sign(unsignedToken);
    }

    public String extractEmail(String token) {
        return (String) parseClaims(token).get("sub");
    }

    public boolean isValid(String token, String email) {
        Map<String, Object> claims = parseClaims(token);
        long expiration = ((Number) claims.get("exp")).longValue();

        return verify(token)
                && email.equals(claims.get("sub"))
                && Instant.now().getEpochSecond() < expiration;
    }

    private Map<String, Object> parseClaims(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Invalid token");
            }
            byte[] payload = Base64.getUrlDecoder().decode(parts[1]);
            return objectMapper.readValue(payload, new TypeReference<>() {});
        } catch (Exception ex) {
            throw new IllegalArgumentException("Invalid token", ex);
        }
    }

    private boolean verify(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                return false;
            }
            String unsignedToken = parts[0] + "." + parts[1];
            return sign(unsignedToken).equals(parts[2]);
        } catch (Exception ex) {
            return false;
        }
    }

    private String encode(Map<String, Object> content) {
        try {
            return Base64.getUrlEncoder()
                    .withoutPadding()
                    .encodeToString(objectMapper.writeValueAsBytes(content));
        } catch (Exception ex) {
            throw new IllegalStateException("Cannot encode token", ex);
        }
    }

    private String sign(String content) {
        try {
            Mac mac = Mac.getInstance(HMAC_SHA256);
            mac.init(new SecretKeySpec(secretKey, HMAC_SHA256));
            byte[] signature = mac.doFinal(content.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(signature);
        } catch (Exception ex) {
            throw new IllegalStateException("Cannot sign token", ex);
        }
    }
}
