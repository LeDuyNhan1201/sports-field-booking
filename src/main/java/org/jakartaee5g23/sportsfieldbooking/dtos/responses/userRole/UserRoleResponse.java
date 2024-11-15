package org.jakartaee5g23.sportsfieldbooking.dtos.responses.userRole;

public record UserRoleResponse(
        String id,
        String userId,
        String roleId,
        String createdAt
) {}
