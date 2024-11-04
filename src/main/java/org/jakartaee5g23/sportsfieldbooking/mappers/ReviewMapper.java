package org.jakartaee5g23.sportsfieldbooking.mappers;

import org.jakartaee5g23.sportsfieldbooking.dtos.requests.review.ReviewRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.reviews.ReviewResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.Review;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);

    Review toReview(ReviewRequest request);

    @Mapping(target = "parentReview", source = "parentReview")
    ReviewResponse toReviewResponse(Review entity);
    @AfterMapping
    default void customizeDto(Review entity, @MappingTarget ReviewResponse dto) {
        dto.setMUser(UserMapper.INSTANCE.toUserResponse(entity.getUser()));
        dto.setMSportField(SportsFieldMapper.INSTANCE.toSportsFieldResponse(entity.getSportsField()));
    }

}