package dev.marcus.curriculum.config.security;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtKeyProvider {

    private final RSAPrivateKey privateKey;
    private final RSAPublicKey publicKey;

    public JwtKeyProvider(
        @Value("${jwt.private.key}") String privateKeySource,
        @Value("${jwt.public.key}") String publicKeySource,
        @Value("${spring.profiles.active:dev}") String activeProfile
    ) throws Exception {
        if ("prod".equalsIgnoreCase(activeProfile)) {
            this.privateKey = loadPrivateKeyFromString(privateKeySource);
            this.publicKey = loadPublicKeyFromString(publicKeySource);
        } else {
            this.privateKey = loadPrivateKeyFromFile(privateKeySource);
            this.publicKey = loadPublicKeyFromFile(publicKeySource);
        }
    }

    public RSAPrivateKey getPrivateKey() {
        return privateKey;
    }

    public RSAPublicKey getPublicKey() {
        return publicKey;
    }

    private RSAPrivateKey loadPrivateKeyFromString(String key) throws Exception {
        String pem = key.replaceAll("-----\\w+ PRIVATE KEY-----", "").replaceAll("\\s", "");
        byte[] decoded = Base64.getDecoder().decode(pem);
        var spec = new PKCS8EncodedKeySpec(decoded);
        return (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(spec);
    }

    private RSAPublicKey loadPublicKeyFromString(String key) throws Exception {
        String pem = key.replaceAll("-----\\w+ PUBLIC KEY-----", "").replaceAll("\\s", "");
        byte[] decoded = Base64.getDecoder().decode(pem);
        var spec = new X509EncodedKeySpec(decoded);
        return (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(spec);
    }

    private RSAPrivateKey loadPrivateKeyFromFile(String location) throws Exception {
        InputStream in = getClass().getClassLoader().getResourceAsStream(location.replace("classpath:", ""));
        byte[] bytes = in.readAllBytes();
        return loadPrivateKeyFromString(new String(bytes, StandardCharsets.UTF_8));
    }

    private RSAPublicKey loadPublicKeyFromFile(String location) throws Exception {
        InputStream in = getClass().getClassLoader().getResourceAsStream(location.replace("classpath:", ""));
        byte[] bytes = in.readAllBytes();
        return loadPublicKeyFromString(new String(bytes, StandardCharsets.UTF_8));
    }
}
