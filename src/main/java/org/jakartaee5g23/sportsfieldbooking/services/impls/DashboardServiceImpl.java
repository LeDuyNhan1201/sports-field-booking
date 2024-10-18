package org.jakartaee5g23.sportsfieldbooking.services.impls;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.other.Pagination;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.other.PaginateResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.dashboard.RevenueReportResponse;
import org.jakartaee5g23.sportsfieldbooking.dtos.responses.booking.BookingResponse;
import org.jakartaee5g23.sportsfieldbooking.entities.Booking;
import org.jakartaee5g23.sportsfieldbooking.entities.SportsField;
import org.jakartaee5g23.sportsfieldbooking.enums.PaymentStatus;
import org.jakartaee5g23.sportsfieldbooking.repositories.BookingRepository;
import org.jakartaee5g23.sportsfieldbooking.services.DashboardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class DashboardServiceImpl implements DashboardService {

    BookingRepository bookingRepository;

    @Override
    public RevenueReportResponse revenueReport(SportsField sportsField, Date beginDate, Date endDate, int offset, int limit) {
//        double total = 0.0;
//        Page<Booking> bookings = bookingRepository.findBySportFieldIdAndStartTimeBetween(sportsField, beginDate, endDate, PageRequest.of(offset, limit, Sort.by("createdAt").descending()));
//        for (Booking booking : bookings.stream().toList())
//            if (booking.getPayment().getStatus() == PaymentStatus.COMPLETED) total += booking.getPayment().getPrice();
//
//        PaginateResponse<BookingResponse> paginateResponse = PaginateResponse.<BookingResponse>builder()
//                .items(bookings.stream().map(BookingMapper.INSTANCE::toBookingResponse).toList())
//                .pagination(new Pagination(offset, limit, bookings.getTotalElements()))
//                .build();
//        return RevenueReportResponse.builder().owner(sportsField.getUser().getId()).total(total).data(paginateResponse).build();
        return null;
    }

}
