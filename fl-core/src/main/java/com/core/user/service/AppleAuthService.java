package com.core.user.service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AppleAuthService {

    @Value("${apple.key-url}")
    private String keyUrl;

    public String getAppleEmail(String identityToken) throws ParseException, JOSEException, IOException {
        JWKSet jwkSet = loadAppleJWKSet();
        JWSObject jwsObject = parseIdentityToken(identityToken);
        JWK jwk = getJwk(jwsObject, jwkSet);
        JWSVerifier verifier = createJWSVerifier(jwk);

        if (!verifyToken(jwsObject, verifier)) {
            throw new JOSEException("Token verification failed");
        }

        return extractEmailFromPayload(jwsObject);
    }

    private JWKSet loadAppleJWKSet() throws IOException, ParseException {
        return JWKSet.load(new URL(keyUrl));
    }

    private JWSObject parseIdentityToken(String identityToken) throws ParseException {
        return JWSObject.parse(identityToken);
    }

    private JWK getJwk(JWSObject jwsObject, JWKSet jwkSet) {
        return jwkSet.getKeyByKeyId(jwsObject.getHeader().getKeyID());
    }

    private JWSVerifier createJWSVerifier(JWK jwk) throws JOSEException {
        RSAKey rsaKey = jwk.toRSAKey();
        return new RSASSAVerifier(rsaKey);
    }

    private boolean verifyToken(JWSObject jwsObject, JWSVerifier verifier) throws JOSEException {
        return jwsObject.verify(verifier);
    }

    private String extractEmailFromPayload(JWSObject jwsObject) {
        Map<String, Object> payload = jwsObject.getPayload().toJSONObject();
        return (String) payload.get("email");
    }
}
