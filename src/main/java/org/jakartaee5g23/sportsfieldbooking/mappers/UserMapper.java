package org.jakartaee5g23.sportsfieldbooking.mappers;

import org.jakartaee5g23.sportsfieldbooking.dtos.requests.authentication.SignUpRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.UserInfoResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.sql.Timestamp;
import java.text.ParseException;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toUser(SignUpRequest request);

    UserInfoResponse toUserInfoResponse(User user);
    @AfterMapping
    default void customize(@MappingTarget UserInfoResponse dto, User entity) {
        dto.setStrBirthdate(entity.getBirthdate().toString());
    }

}