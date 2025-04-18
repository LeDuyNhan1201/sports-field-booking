package org.jakartaee5g23.sportsfieldbooking.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.http.HttpServletRequest;
import org.jakartaee5g23.sportsfieldbooking.entities.User;
import org.jakartaee5g23.sportsfieldbooking.enums.ProviderType;
import org.jakartaee5g23.sportsfieldbooking.enums.VerificationType;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Map;

@Service
public interface AuthenticationService {

    boolean introspect(String token) throws JOSEException, ParseException;

    void signUp(User user, String confirmationPassword, boolean acceptTerms, boolean isFieldOwner);

    void sendEmailVerification(String email, VerificationType verificationType);

    void verifyEmail(User user, String code, String token);

    User signIn(String email, String password);

    String generateSocialAuthUrl(ProviderType providerType);

    Map<String, Object> fetchSocialUser(String code, ProviderType providerType) throws Exception;

    String generateToken(User user, boolean isRefresh);

    User refresh(String refreshToken, HttpServletRequest servletRequest) throws ParseException, JOSEException;

    void sendEmailForgotPassword(String email);

    String forgotPassword(User user, String code);

    void resetPassword(String token, String password, String confirmationPassword);

    void signOut(String accessToken, String refreshToken) throws ParseException, JOSEException;

}