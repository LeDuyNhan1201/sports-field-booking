package org.jakartaee5g23.sportsfieldbooking.mappers;


import org.jakartaee5g23.sportsfieldbooking.dtos.responses.userRole.UserRoleResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")

public interface UserRoleMapper {
    UserRoleMapper INSTANCE = Mappers.getMapper(UserRoleMapper.class);

    UserRoleResponse toUserRole(UserRole userRole);
}
