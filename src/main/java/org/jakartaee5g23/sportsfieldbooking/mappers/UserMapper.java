package org.jakartaee5g23.sportsfieldbooking.mappers;

import org.jakartaee5g23.sportsfieldbooking.dtos.requests.authentication.SignUpRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.user.UserResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toUser(SignUpRequest request);

    UserResponse toUserResponse(User user);

}