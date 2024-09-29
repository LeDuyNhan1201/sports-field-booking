package org.jakartaee5g23.sportsfieldbooking.helpers;

import org.jakartaee5g23.sportsfieldbooking.entities.User;
import org.springframework.context.NoSuchMessageException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import java.util.Random;

import static org.jakartaee5g23.sportsfieldbooking.components.Translator.getLocalizedMessage;
import static org.jakartaee5g23.sportsfieldbooking.helpers.Constants.ALLOWED_MEDIA_TYPES;

public class Utils {

    public static boolean isMedia(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && ALLOWED_MEDIA_TYPES.contains(contentType);
    }

    public static String getMessageForValidationException(String messageKey, Object... args) {
        try {
            return getLocalizedMessage(messageKey, args);

        } catch (NoSuchMessageException exception) {
            return exception.getMessage();
        }
    }

    public static <T extends Enum<?>> T getRandomEnum(Class<T> enumClass) {
        Random random = new Random();
        int x = random.nextInt(enumClass.getEnumConstants().length);
        return enumClass.getEnumConstants()[x];
    }

    public static String getUserIdFromContext() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
