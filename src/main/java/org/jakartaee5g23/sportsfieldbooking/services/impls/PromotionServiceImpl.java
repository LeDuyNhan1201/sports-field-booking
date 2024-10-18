package org.jakartaee5g23.sportsfieldbooking.services.impls;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.text.*;
import java.util.*;
import java.time.*;

import org.jakartaee5g23.sportsfieldbooking.entities.Promotion;
import org.jakartaee5g23.sportsfieldbooking.enums.PromotionStatus;
import org.jakartaee5g23.sportsfieldbooking.exceptions.AppException;
import org.jakartaee5g23.sportsfieldbooking.exceptions.CommonErrorCode;
import org.jakartaee5g23.sportsfieldbooking.exceptions.promotion.PromotionErrorCode;
import org.jakartaee5g23.sportsfieldbooking.exceptions.promotion.PromotionException;
import org.jakartaee5g23.sportsfieldbooking.exceptions.sportsfield.SportsFieldErrorCode;
import org.jakartaee5g23.sportsfieldbooking.exceptions.sportsfield.SportsFieldException;
import org.jakartaee5g23.sportsfieldbooking.repositories.PromotionRepository;
import org.jakartaee5g23.sportsfieldbooking.services.PromotionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PromotionServiceImpl implements PromotionService {
    PromotionRepository promotionRepository;

    public boolean isTimeValid(Date startDate, Date endDate) {
        LocalTime openingLocalTime = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
        LocalTime closingLocalTime = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
        return closingLocalTime.isAfter(openingLocalTime);
    }

    @Override
    public Promotion create(Promotion request, Boolean isConfirmed) {
        if (!isConfirmed) throw new PromotionException(PromotionErrorCode.CREATE_FAILED, HttpStatus.UNPROCESSABLE_ENTITY);
        Date startDate = request.getStartDate();
        Date endDate = request.getEndDate();
        
        if(isTimeValid(startDate, endDate))
            throw new PromotionException(PromotionErrorCode.INVALID_START_END_TIME, HttpStatus.UNPROCESSABLE_ENTITY);
        
        Promotion promotion = Promotion.builder()
            .name(request.getName())
            .description(request.getDescription())
            .discountPercentage(request.getDiscountPercentage())
            .startDate(startDate)
            .endDate(endDate)
            .status(request.getStatus())
            .build();
        
        return promotionRepository.save(promotion);
    }

    @Override
    public Page<Promotion> findAll(int offset, int limit) {
        return promotionRepository.findAll(PageRequest.of(offset, limit, Sort.by("created_at").descending())); 
    }

    @Override
    public Promotion findById(Integer id) {
        return promotionRepository.findById(id).orElseThrow(
                () -> new AppException(CommonErrorCode.OBJECT_NOT_FOUND, HttpStatus.NOT_FOUND, "Promotion"));
    }

    @Override
    public Promotion update(Promotion request, Boolean isConfirmed) {
        if (!isConfirmed) throw new PromotionException(PromotionErrorCode.CREATE_FAILED, HttpStatus.UNPROCESSABLE_ENTITY);
        Date startDate = request.getStartDate();
        Date endDate = request.getEndDate();
        
        if(isTimeValid(startDate, endDate))
            throw new PromotionException(PromotionErrorCode.INVALID_START_END_TIME, HttpStatus.UNPROCESSABLE_ENTITY);
        
        Promotion promotion = findById(request.getId());
        promotion.setName(request.getName());
        promotion.setDescription(request.getDescription());
        promotion.setDiscountPercentage(request.getDiscountPercentage());
        promotion.setStartDate(request.getStartDate());
        promotion.setEndDate(request.getEndDate());
        
        return promotionRepository.save(promotion);
    }

    @Override
    public Promotion updateStatus(Integer id, PromotionStatus status) {
        Promotion promotion = findById(id);
        promotion.setStatus(status);
        return promotionRepository.save(promotion);    
    }
    
}
