package org.jakartaee5g23.sportsfieldbooking.exceptions.dashboard;

import lombok.Getter;

@Getter
public enum DashboardErrorCode {

    USERS_NOT_FOUND("dashboard/users-not-found", "dashboard_error")
    ;

    DashboardErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private final String code;
    private final String message;

}