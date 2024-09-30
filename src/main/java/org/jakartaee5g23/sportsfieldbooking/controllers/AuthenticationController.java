package org.jakartaee5g23.sportsfieldbooking.controllers;

import com.nimbusds.jose.JOSEException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jakartaee5g23.sportsfieldbooking.annotations.RateLimit;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.authentication.TokensResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.authentication.*;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.authentication.*;
import org.jakartaee5g23.sportsfieldbooking.entities.User;
import org.jakartaee5g23.sportsfieldbooking.exceptions.authentication.AuthenticationException;
import org.jakartaee5g23.sportsfieldbooking.mappers.UserMapper;
import org.jakartaee5g23.sportsfieldbooking.services.AuthenticationService;
import org.jakartaee5g23.sportsfieldbooking.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static org.jakartaee5g23.sportsfieldbooking.components.Translator.getLocalizedMessage;
import static org.jakartaee5g23.sportsfieldbooking.enums.RateLimitKeyType.BY_IP;
import static org.jakartaee5g23.sportsfieldbooking.enums.RateLimitKeyType.BY_TOKEN;
import static org.jakartaee5g23.sportsfieldbooking.exceptions.authentication.AuthenticationErrorCode.INVALID_SIGNATURE;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("${api.prefix}/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "Authentication APIs")
public class AuthenticationController {

    AuthenticationService authenticationService;

    UserService userService;

    UserMapper userMapper = UserMapper.INSTANCE;

    @Operation(summary = "Test field owner role", description = "Test field owner")
    @PreAuthorize("hasRole('FIELD_OWNER')")
    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }

    @Operation(summary = "Test admin role", description = "Test admin")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/hello1")
    public String hello1() {
        return "Hello";
    }

    @Operation(summary = "Test customer role", description = "Test customer")
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/hello2")
    public String hello2() {
        return "Hello";
    }

    @Operation(summary = "Sign up", description = "Create new user")
    @PostMapping("/sign-up")
    @ResponseStatus(CREATED)
    ResponseEntity<SignUpResponse> signUp(@RequestBody @Valid SignUpRequest request) {
        User user = userMapper.toUser(request);

        authenticationService.signUp(user, request.passwordConfirmation(), request.acceptTerms());

        return ResponseEntity.status(CREATED).body(
                new SignUpResponse(getLocalizedMessage("sign_up_success")));
    }

    @Operation(summary = "Send email verification", description = "Send email verification")
    @PostMapping("/send-email-verification")
    @ResponseStatus(OK)
    ResponseEntity<SendEmailVerificationResponse> sendEmailVerification(
            @RequestBody @Valid SendEmailVerificationRequest request) {
        authenticationService.sendEmailVerification(request.email(), request.type());

        return ResponseEntity.status(OK).body(
                new SendEmailVerificationResponse(getLocalizedMessage("resend_verification_email_success")));
    }

    @Operation(summary = "Verify email by code", description = "Verify email by code")
    @PostMapping("/verify-email-by-code")
    @ResponseStatus(OK)
    ResponseEntity<VerifyEmailResponse> verifyEmail(@RequestBody @Valid VerifyEmailByCodeRequest request) {
        User user = userService.findByEmail(request.email());

        authenticationService.verifyEmail(user, request.code(), null);

        return ResponseEntity.status(OK).body(
                new VerifyEmailResponse(getLocalizedMessage("verify_email_success")));
    }

    @Operation(summary = "Verify email by token", description = "Verify email by token")
    @GetMapping("/verify-email-by-token")
    @ResponseStatus(OK)
    ResponseEntity<VerifyEmailResponse> verifyEmail(@RequestParam(name = "token") String token) {
        authenticationService.verifyEmail(null, null, token);

        return ResponseEntity.status(OK).body(
                new VerifyEmailResponse(getLocalizedMessage("verify_email_success")));
    }

    @Operation(summary = "Sign in", description = "Authenticate user and return token")
    @PostMapping("/sign-in")
    @ResponseStatus(OK)
    @RateLimit(limitKeyTypes = { BY_IP })
    ResponseEntity<SignInResponse> signIn(@RequestBody @Valid SignInRequest request) {
        User signInUser = authenticationService.signIn(request.email(), request.password());

        String accessToken = authenticationService.generateToken(signInUser, false);

        String refreshToken = authenticationService.generateToken(signInUser, true);

        return ResponseEntity.status(OK).body(
                SignInResponse.builder()
                        .tokensResponse(new TokensResponse(accessToken, refreshToken))
                        .user(userMapper.toUserResponse(signInUser)).build()
        );
    }

    @Operation(summary = "Refresh", description = "Refresh token")
    @PostMapping("/refresh")
    @ResponseStatus(OK)
    @RateLimit(limitKeyTypes = { BY_TOKEN })
    ResponseEntity<RefreshResponse> refresh(@RequestBody @Valid RefreshRequest request,
                                            HttpServletRequest httpServletRequest) {
        User user;
        try {
            user = authenticationService.refresh(request.refreshToken(), httpServletRequest);

        } catch (ParseException | JOSEException e) {
            throw new AuthenticationException(INVALID_SIGNATURE, UNPROCESSABLE_ENTITY);
        }

        String newAccessToken = authenticationService.generateToken(user, false);

        return ResponseEntity.status(OK).body(new RefreshResponse(
                getLocalizedMessage("refresh_token_success"),
                newAccessToken
        ));
    }

    @Operation(summary = "Sign out", description = "Sign out user")
    @PostMapping("/sign-out")
    @ResponseStatus(OK)
    void signOut(@RequestBody @Valid SignOutRequest request) {
        try {
            authenticationService.signOut(request.accessToken(), request.refreshToken());

        } catch (ParseException | JOSEException e) {
            throw new AuthenticationException(INVALID_SIGNATURE, UNPROCESSABLE_ENTITY);
        }
    }

    @Operation(summary = "Send email forgot password", description = "Send email forgot password")
    @PostMapping("/send-forgot-password")
    @ResponseStatus(OK)
    ResponseEntity<SendEmailForgotPasswordResponse> sendEmailForgotPassword(
            @RequestBody @Valid SendEmailForgotPasswordRequest request) {
        authenticationService.sendEmailForgotPassword(request.email());

        return ResponseEntity.status(OK).body(new SendEmailForgotPasswordResponse(
                getLocalizedMessage("send_forgot_password_email_success"),
                Date.from(Instant.now().plus(1, ChronoUnit.MINUTES))
        ));
    }

    @Operation(summary = "Verify forgot password code", description = "Verify forgot password code")
    @PostMapping("/forgot-password")
    @ResponseStatus(OK)
    ResponseEntity<ForgotPasswordResponse> forgotPassword(@RequestBody @Valid ForgotPasswordRequest request) {
        User user = userService.findByEmail(request.email());
        String forgotPasswordToken = authenticationService.forgotPassword(user, request.code());

        return ResponseEntity.status(OK).body(new ForgotPasswordResponse(
                getLocalizedMessage("verify_forgot_password_code_success"),
                forgotPasswordToken
        ));
    }

    @Operation(summary = "Reset password", description = "Reset password")
    @PostMapping("/reset-password")
    @ResponseStatus(OK)
    ResponseEntity<ResetPasswordResponse> resetPassword(@RequestBody @Valid ResetPasswordRequest request) {
        authenticationService.resetPassword(request.token(), request.password(), request.passwordConfirmation());

        return ResponseEntity.status(OK).body(
                new ResetPasswordResponse(getLocalizedMessage("reset_password_success")));
    }

    @Operation(summary = "Introspect", description = "Introspect provided token")
    @PostMapping("/introspect")
    @ResponseStatus(OK)
    ResponseEntity<IntrospectResponse> introspect(@RequestBody @Valid IntrospectRequest request)
            throws ParseException, JOSEException {
        boolean isValid = authenticationService.introspect(request.token());

        return ResponseEntity.status(OK).body(new IntrospectResponse(isValid));
    }

}