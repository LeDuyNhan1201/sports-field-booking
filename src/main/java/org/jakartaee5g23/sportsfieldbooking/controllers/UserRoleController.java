package org.jakartaee5g23.sportsfieldbooking.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.userRole.UserRoleRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.userRole.UserRoleResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.Role;
import org.jakartaee5g23.sportsfieldbooking.entities.User;
import org.jakartaee5g23.sportsfieldbooking.entities.UserRole;
import org.jakartaee5g23.sportsfieldbooking.mappers.UserRoleMapper;
import org.jakartaee5g23.sportsfieldbooking.services.RoleService;
import org.jakartaee5g23.sportsfieldbooking.services.UserRoleService;
import org.jakartaee5g23.sportsfieldbooking.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/userRole")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "User Role APIs")
public class UserRoleController {

    UserRoleService userRoleService;

    UserService userService;

    RoleService roleService;

    UserRoleMapper userRoleMapper = UserRoleMapper.INSTANCE;

    @Operation(summary = "Create User Role", description = "Assign a role to a user")
    @PostMapping
    public ResponseEntity<UserRoleResponse> createUserRole(@RequestBody @Valid UserRoleRequest userRoleRequest) {
        User user = userService.findById(userRoleRequest.userId());
        Role role = roleService.findById(userRoleRequest.roleId());

        UserRole userRole = UserRole.builder()
                .user(user)
                .role(role)
                .build();

        UserRoleResponse response = userRoleMapper.toUserRole(userRoleService.create(userRole));
        return ResponseEntity.ok(response);
    }
}
