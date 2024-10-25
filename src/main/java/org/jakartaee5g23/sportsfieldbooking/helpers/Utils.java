package org.jakartaee5g23.sportsfieldbooking.helpers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

import static org.jakartaee5g23.sportsfieldbooking.helpers.Constants.ALLOWED_MEDIA_TYPES;

public class Utils {

    public static boolean isMedia(String contentType) {
        return contentType != null && ALLOWED_MEDIA_TYPES.contains(contentType);
    }

    public static String generateFileName(String fileType, String extension) {
        // Lấy thời gian hiện tại với format yyyyMMdd_HHmmss
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String timestamp = LocalDateTime.now().format(dateTimeFormatter);

        // Tạo UUID duy nhất
        String uniqueId = UUID.randomUUID().toString();

        // Kết hợp tên file
        return String.format("%s_%s_%s.%s", fileType, timestamp, uniqueId, extension);
    }

    public static <T extends Enum<?>> T getRandomEnum(Class<T> enumClass) {
        Random random = new Random();
        int x = random.nextInt(enumClass.getEnumConstants().length);
        return enumClass.getEnumConstants()[x];
    }

    public static String getUserIdFromContext() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public static String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return "anonymous";
        }

        return authentication.getName();
    }

}
