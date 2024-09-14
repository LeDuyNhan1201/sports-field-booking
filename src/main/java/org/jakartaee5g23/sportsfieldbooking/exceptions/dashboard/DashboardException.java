package org.jakartaee5g23.sportsfieldbooking.exceptions.dashboard;

import lombok.Getter;
import org.jakartaee5g23.sportsfieldbooking.exceptions.AppException;
import org.jakartaee5g23.sportsfieldbooking.exceptions.filestorage.FileStorageErrorCode;
import org.springframework.http.HttpStatus;

@Getter
public class DashboardException extends AppException {

    public DashboardException(DashboardErrorCode dashboardErrorCode, HttpStatus httpStatus) {
        super(dashboardErrorCode.getMessage(), httpStatus);
        this.dashboardErrorCode = dashboardErrorCode;
    }

    private final DashboardErrorCode dashboardErrorCode;

}