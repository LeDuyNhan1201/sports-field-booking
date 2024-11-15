package org.jakartaee5g23.sportsfieldbooking.dtos.requests.userRole;

import jakarta.validation.constraints.NotNull;

public record UserRoleRequest(
        @NotNull
        String userId,
        @NotNull
        int roleId
) {}
