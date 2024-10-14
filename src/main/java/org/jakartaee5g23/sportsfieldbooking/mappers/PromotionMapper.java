package org.jakartaee5g23.sportsfieldbooking.mappers;

import org.jakartaee5g23.sportsfieldbooking.dtos.requests.promotion.NewPromotionRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.requests.promotion.UpdatePromotionRequest;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.promotion.PromotionResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.sportField.SportsFieldResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.Promotion;
import org.jakartaee5g23.sportsfieldbooking.entities.SportsField;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PromotionMapper {

    PromotionMapper INSTANCE = Mappers.getMapper(PromotionMapper.class);

    Promotion toPromotion(NewPromotionRequest dto);
    Promotion toPromotion(UpdatePromotionRequest dto);

    PromotionResponse toPromotionResponse(Promotion entity);
    
    // @AfterMapping
    // default void customizeDto(Promotion entity, @MappingTarget PromotionResponse dto) {
    //     dto.setCategoryName(entity.getCategory().getName());
    // }
} 