package org.jakartaee5g23.sportsfieldbooking.services.impls;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.text.*;
import java.util.*;
import java.time.*;
import java.util.stream.Collectors;

import org.jakartaee5g23.sportsfieldbooking.entities.*;
import org.jakartaee5g23.sportsfieldbooking.enums.SportsFieldStatus;
import org.jakartaee5g23.sportsfieldbooking.exceptions.AppException;
import org.jakartaee5g23.sportsfieldbooking.exceptions.CommonErrorCode;
import org.jakartaee5g23.sportsfieldbooking.exceptions.sportsfield.SportsFieldErrorCode;
import org.jakartaee5g23.sportsfieldbooking.exceptions.sportsfield.SportsFieldException;
import org.jakartaee5g23.sportsfieldbooking.repositories.SportsFieldRepository;
import org.jakartaee5g23.sportsfieldbooking.repositories.CategoryRepository;
import org.jakartaee5g23.sportsfieldbooking.services.SportsFieldService;
import org.jakartaee5g23.sportsfieldbooking.specifications.Filter;
import org.jakartaee5g23.sportsfieldbooking.specifications.SportsFieldSpecification;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class SportsFieldServiceImpl implements SportsFieldService {

    SportsFieldRepository sportsFieldRepository;

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
    public SportsField findById(String id) {
        return sportsFieldRepository.findById(id).orElseThrow(
                () -> new AppException(CommonErrorCode.OBJECT_NOT_FOUND, HttpStatus.NOT_FOUND, "Field Sport"));
    }

    @Override
    public Page<SportsField> findAll(int offset, int limit, String colSort, int sortDirection) {
        Sort sort = (sortDirection == 1)
                ? Sort.by(colSort).ascending() // Sắp xếp từ A đến Z
                : Sort.by(colSort).descending(); // Sắp xếp từ Z đến A
        return sportsFieldRepository.findAll(PageRequest.of(offset, limit, sort));
    }

    @Override
    @Transactional
    public SportsField update(SportsField request, Boolean isConfirmed) {
        if (!isConfirmed)
            throw new SportsFieldException(SportsFieldErrorCode.CREATE_FAILED, HttpStatus.UNPROCESSABLE_ENTITY);

//            if (isTimeValid(openingTime, closingTime))
//                throw new SportsFieldException(SportsFieldErrorCode.INVALID_OPENING_CLOSING_TIME,
//                        HttpStatus.UNPROCESSABLE_ENTITY);

            Category category = categoryRepository.findById(request.getCategory().getId()).orElseThrow(
                    () -> new AppException(CommonErrorCode.OBJECT_NOT_FOUND, HttpStatus.NOT_FOUND, "Category"));
            
            SportsField currentSportsField = sportsFieldRepository.findById(request.getId()).orElseThrow(
                () -> new AppException(CommonErrorCode.OBJECT_NOT_FOUND, HttpStatus.NOT_FOUND, "Sports Field"));

            currentSportsField.setName(request.getName());
            currentSportsField.setLocation(request.getLocation());
            currentSportsField.setOpacity(request.getOpacity());
            currentSportsField.setCategory(category);

            return sportsFieldRepository.save(currentSportsField);
    }

    @Override
    public SportsField updateStatus(String id, SportsFieldStatus status) {
        SportsField sportsField = findById(id);
        sportsField.setStatus(status);
        return sportsFieldRepository.save(sportsField);
    }

    @Override
    public SportsField create(SportsField request, Boolean isConfirmed) {
        if (!isConfirmed)
            throw new SportsFieldException(SportsFieldErrorCode.CREATE_FAILED, HttpStatus.UNPROCESSABLE_ENTITY);

            Date openingTime = request.getOpeningTime();
            Date closingTime = request.getClosingTime();

            Category category = categoryRepository.findById(request.getCategory().getId()).orElseThrow(
                    () -> new AppException(CommonErrorCode.OBJECT_NOT_FOUND, HttpStatus.NOT_FOUND, "Category"));

            SportsField createField = SportsField.builder()
                    .name(request.getName())
                    .location(request.getLocation())
                    .opacity(request.getOpacity())
                    .openingTime(openingTime)
                    .closingTime(closingTime)
                    .rating(request.getRating())
                    .status(SportsFieldStatus.PENDING)
                    .category(category)
                    .user(request.getUser())
                    .build();
            return sportsFieldRepository.save(createField);
    }

    @Override
    public Page<SportsField> searchSportsField(String userId,String text,
                                                        Double maxPrice, Double minPrice, Integer categoryId, int offset, int limit , String colSort, int sortDirection) {
        Sort sort = (sortDirection == 1)
                ? Sort.by(colSort).ascending() // Sắp xếp từ A đến Z
                : Sort.by(colSort).descending(); // Sắp xếp từ Z đến A

        Pageable pageable = PageRequest.of(0, 1000, sort);
        Page<SportsField> allFields = sportsFieldRepository.searchSportsFields(userId,text,categoryId,maxPrice,minPrice, pageable);

        if (allFields.isEmpty()) {
            return Page.empty(PageRequest.of(offset, limit, sort));
        }

        List<SportsField> allFieldList = allFields.getContent();

        int fromIndex = Math.min(offset * limit, allFieldList.size());
        int toIndex = Math.min(fromIndex + limit, allFieldList.size());

        List<SportsField> subList = allFieldList.subList(fromIndex, toIndex);

        Pageable resultPageable = PageRequest.of(offset, limit, sort);

        return new PageImpl<>(subList, resultPageable, allFieldList.size());

    }

    @Override
    public Page<SportsField> findByUser(User user, int offset, int limit, String colSort, int sortDirection) {
        Sort sort = (sortDirection == 1)
                ? Sort.by(colSort).ascending() // Sắp xếp từ A đến Z
                : Sort.by(colSort).descending(); // Sắp xếp từ Z đến A

        Pageable pageable = PageRequest.of(offset, limit, sort);
        return sportsFieldRepository.findByUser(user, PageRequest.of(offset, limit, sort));
    }


    @Override
    public Page<SportsField> findSportsFieldsByCategoryLocationPrice(String categoryId, String location,
            double minPrice, double maxPrice, int offset, int limit, String colSort, int sortDirection) {
        Sort sort = (sortDirection == 1)
                ? Sort.by(colSort).ascending()
                : Sort.by(colSort).descending();
        Pageable pageable = PageRequest.of(offset, limit, sort);
        return sportsFieldRepository.findSportsFieldsByCategoryLocationPrice(categoryId, location, minPrice, maxPrice,
                pageable);
    }

    @Override
    public Page<SportsField> searchSportsFields(
            List<Filter> filters, Integer categoryId, String userId, Double maxPrice, Double minPrice,
            List<Sort.Order> orders, int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit, Sort.by(orders));
        Specification<SportsField> specification = new SportsFieldSpecification(filters, categoryId, userId, maxPrice, minPrice, orders);
        return sportsFieldRepository.findAll(specification, pageable);
    }

}
