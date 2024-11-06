package org.jakartaee5g23.sportsfieldbooking.controllers;

import com.nimbusds.jose.JOSEException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.authentication.SignOutRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.user.UserUpdateRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.other.PaginateResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.other.Pagination;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.user.UserResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.User;
import org.jakartaee5g23.sportsfieldbooking.exceptions.authentication.AuthenticationException;
import org.jakartaee5g23.sportsfieldbooking.mappers.UserMapper;
import org.jakartaee5g23.sportsfieldbooking.services.AuthenticationService;
import org.jakartaee5g23.sportsfieldbooking.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "User APIs")
public class UserController {

    UserService userService;

    AuthenticationService authenticationService;

    UserMapper userMapper = UserMapper.INSTANCE;

    @Operation(summary = "Get user profile", description = "Get user profile", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/{userId}")
    // @PostAuthorize("returnObject.b ody.email == authentication.name")
    ResponseEntity<UserResponse> getProfile(@PathVariable String userId) {
        return ResponseEntity.status(OK).body(userMapper.toUserResponse(userService.findById(userId)));
    }

    @Operation(summary = "Update user profile", description = "Update user profile", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateProfile(@PathVariable String userId, @RequestBody UserUpdateRequest userUpdateRequest) {
        User existingUser = userService.findById(userId);

        if (existingUser == null) {
            return ResponseEntity.status(NOT_FOUND).build();
        }

        existingUser.setFirstName(userUpdateRequest.getFirstName());
        existingUser.setLastName(userUpdateRequest.getLastName());
        existingUser.setEmail(userUpdateRequest.getEmail());
        existingUser.setMobileNumber(userUpdateRequest.getMobileNumber());
        existingUser.setBirthdate(userUpdateRequest.getBirthdate());
        existingUser.setGender(userUpdateRequest.getGender());
//        existingUser.setAvatar(userUpdateRequest.getAvatar());

        userService.updateUser(existingUser);

        return ResponseEntity.status(HttpStatus.OK)
                .body(userMapper.toUserResponse(existingUser));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId, @RequestBody @Valid SignOutRequest request) {
        try {
            userService.deleteUser(userId);
            authenticationService.signOut(request.accessToken(), request.refreshToken());
            return ResponseEntity.noContent().build();
        } catch (AuthenticationException | ParseException | JOSEException e) {
            return ResponseEntity.status(NOT_FOUND).build();
        }
    }


    @Operation(summary = "Get all users", description = "Get all users")
    @GetMapping
    public ResponseEntity<PaginateResponse<UserResponse>> findAll(@RequestParam(defaultValue = "0") String offset,
                                                                         @RequestParam(defaultValue = "100") String limit) {
        Page<User> users = userService.findAll(Integer.parseInt(offset), Integer.parseInt(limit));
        return ResponseEntity.status(HttpStatus.OK)
                .body(PaginateResponse.<UserResponse>builder()
                        .items(users.stream().map(userMapper::toUserResponse).toList())
                        .pagination(new Pagination(Integer.parseInt(offset), Integer.parseInt(limit), users.getTotalElements()))
                        .build());
    }

/*    @Operation(summary = "Upload avatar", description = "Upload avatar", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/avatar")
    @ResponseStatus(OK)
    ResponseEntity<CommonResponse<?, ?>> uploadAvatar(@RequestPart MultipartFile avatar) {
        SecurityContext context = SecurityContextHolder.getContext();
        User user = userService.findByEmail(context.getAuthentication().getName());

        if (user.getAvatar() != null) fileService.deleteFile(user.getAvatar().getId());

        if (avatar != null) fileService.uploadFiles(user, List.of(avatar));
        else throw new FileException(CAN_NOT_STORE_FILE, BAD_REQUEST);

        return ResponseEntity.status(OK).body(CommonResponse.builder()
                .message(getLocalizedMessage("upload_avatar_success", user.getId())).build());
    }*/

}