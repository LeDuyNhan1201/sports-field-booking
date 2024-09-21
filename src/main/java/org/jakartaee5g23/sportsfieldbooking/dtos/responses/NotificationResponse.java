package org.jakartaee5g23.sportsfieldbooking.dtos.responses;

import java.util.*;

import org.jakartaee5g23.sportsfieldbooking.entities.Notification;

public record NotificationResponse(List<Notification> notifications) {

}
