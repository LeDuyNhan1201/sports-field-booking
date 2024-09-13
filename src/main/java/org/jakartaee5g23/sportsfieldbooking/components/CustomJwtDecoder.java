package org.jakartaee5g23.sportsfieldbooking.components;

import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.jakartaee5g23.sportsfieldbooking.exceptions.authentication.AuthenticationException;
import org.jakartaee5g23.sportsfieldbooking.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.util.Objects;

import static org.jakartaee5g23.sportsfieldbooking.exceptions.authentication.AuthenticationErrorCode.INVALID_TOKEN;
import static org.jakartaee5g23.sportsfieldbooking.helpers.Constants.ACCESS_TOKEN_SIGNATURE_ALGORITHM;
import static org.springframework.http.HttpStatus.BAD_GATEWAY;

@Component
@RequiredArgsConstructor
public class CustomJwtDecoder implements JwtDecoder {

    @Value("${jwt.accessSignerKey}")
    private String ACCESS_SIGNER_KEY;

    private final AuthenticationService authenticationService;

    private NimbusJwtDecoder nimbusJwtDecoder = null;

    @Override
    public Jwt decode(String token) throws JwtException {

        try {
            if (!authenticationService.introspect(token)) throw new AuthenticationException(INVALID_TOKEN, BAD_GATEWAY);

        } catch (JOSEException | ParseException e) {
            throw new JwtException(e.getMessage());
        }

        if (Objects.isNull(nimbusJwtDecoder)) {
            SecretKeySpec secretKeySpec = new SecretKeySpec(ACCESS_SIGNER_KEY.getBytes(), ACCESS_TOKEN_SIGNATURE_ALGORITHM.getName());
            nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(secretKeySpec)
                    .macAlgorithm(MacAlgorithm.from(ACCESS_TOKEN_SIGNATURE_ALGORITHM.getName()))
                    .build();
        }

        return nimbusJwtDecoder.decode(token);
    }

}