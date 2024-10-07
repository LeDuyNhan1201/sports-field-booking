package org.jakartaee5g23.sportsfieldbooking.mappers;

import org.jakartaee5g23.sportsfieldbooking.dtos.requests.authentication.SignUpRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.user.UserResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toUser(SignUpRequest request);

    UserResponse toUserResponse(User user);
    @AfterMapping
    default void customizeDto(User entity, @MappingTarget UserResponse dto) {
        dto.setMRoles(entity.getRoles().stream().map(role -> role.getRole().getName()).collect(Collectors.toList()));
    }

}