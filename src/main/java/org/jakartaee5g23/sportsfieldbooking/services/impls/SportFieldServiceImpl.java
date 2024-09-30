package org.jakartaee5g23.sportsfieldbooking.services.impls;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.text.*;
import java.util.*;
import java.time.*;

import org.jakartaee5g23.sportsfieldbooking.entities.*;
import org.jakartaee5g23.sportsfieldbooking.enums.SportFieldStatus;
import org.jakartaee5g23.sportsfieldbooking.exceptions.AppException;
import org.jakartaee5g23.sportsfieldbooking.exceptions.CommonErrorCode;
import org.jakartaee5g23.sportsfieldbooking.exceptions.sportfield.SportFieldErrorCode;
import org.jakartaee5g23.sportsfieldbooking.exceptions.sportfield.SportFieldException;
import org.jakartaee5g23.sportsfieldbooking.repositories.SportFieldRepository;
import org.jakartaee5g23.sportsfieldbooking.repositories.CategoryRepository;
import org.jakartaee5g23.sportsfieldbooking.services.SportFieldService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class SportFieldServiceImpl implements SportFieldService {

    SportFieldRepository sportFieldRepository;

    CategoryRepository categoryRepository;

    public Optional<Date> parseTime(String time) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        try {
            return Optional.of(formatter.parse(time));

        } catch (ParseException e) {
            log.error("Error parsing time", e);
            return Optional.empty();
        }
    }

    public boolean isTimeValid(Date openingTime, Date closingTime) {
        LocalTime openingLocalTime = openingTime.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
        LocalTime closingLocalTime = closingTime.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
        return closingLocalTime.isAfter(openingLocalTime);
    }

    @Override
    public SportField findById(String id) {
        return sportFieldRepository.findById(id).orElseThrow(
                () -> new AppException(CommonErrorCode.OBJECT_NOT_FOUND, HttpStatus.NOT_FOUND, "Field Sport"));
    }

    @Override
    public Page<SportField> findAll(int offset, int limit) {
        return sportFieldRepository.findAll(PageRequest.of(offset, limit, Sort.by("createdAt").descending()));
    }

    @Override
    @Transactional
    public SportField update(SportField request, Boolean isConfirmed) {
        if (!isConfirmed) throw new SportFieldException(SportFieldErrorCode.UPDATE_FAILED, HttpStatus.UNPROCESSABLE_ENTITY);

        Date openingTime = request.getOpeningTime();
        Date closingTime = request.getClosingTime();
        if(isTimeValid(openingTime, closingTime))
            throw new SportFieldException(SportFieldErrorCode.INVALID_OPENING_CLOSING_TIME, HttpStatus.UNPROCESSABLE_ENTITY);

        SportField sportField = findById(request.getId());
        sportField.setOpacity(request.getOpacity());
        sportField.setPricePerHour(request.getPricePerHour());
        sportField.setClosingTime(closingTime);
        sportField.setOpeningTime(openingTime);
        sportField.setLocation(request.getLocation());
        sportField.setName(request.getName());
        return sportFieldRepository.save(sportField);
    }

    @Override
    public SportField updateStatus(String id, SportFieldStatus status) {
        SportField sportField = findById(id);
        sportField.setStatus(status);
        return sportFieldRepository.save(sportField);
    }

    @Override
    public SportField create(SportField request, Boolean isConfirmed) {
        if (!isConfirmed) throw new SportFieldException(SportFieldErrorCode.CREATE_FAILED, HttpStatus.UNPROCESSABLE_ENTITY);

        Date openingTime = request.getOpeningTime();
        Date closingTime = request.getClosingTime();

        if (isTimeValid(openingTime, closingTime))
            throw new SportFieldException(SportFieldErrorCode.INVALID_OPENING_CLOSING_TIME, HttpStatus.UNPROCESSABLE_ENTITY);

        Category category = categoryRepository.findById(request.getCategory().getId()).orElseThrow(
                () -> new AppException(CommonErrorCode.OBJECT_NOT_FOUND, HttpStatus.NOT_FOUND, "Category"));

        SportField createField = SportField.builder()
                .name(request.getName())
                .location(request.getLocation())
                .pricePerHour(request.getPricePerHour())
                .opacity(request.getOpacity())
                .openingTime(openingTime)
                .closingTime(closingTime)
                .status(SportFieldStatus.NONE)
                .category(category)
                .user(request.getUser())
                .build();
        return sportFieldRepository.save(createField);
    }

}
