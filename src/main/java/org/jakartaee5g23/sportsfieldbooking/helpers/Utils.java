package org.jakartaee5g23.sportsfieldbooking.helpers;

import org.springframework.context.NoSuchMessageException;
import org.springframework.web.multipart.MultipartFile;

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

}
