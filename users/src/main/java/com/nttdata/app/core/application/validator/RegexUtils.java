package com.nttdata.app.core.application.validator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class RegexUtils {

    @Value("${regex.only_numbers}")
    private String REGEX_ONLY_NUMBERS;

    @Value("${regex.only_numbers.error}")
    private String REGEX_ONLY_NUMBERS_ERROR;

    @Value("${regex.email}")
    private String REGEX_EMAIL;

    @Value("${regex.email_error}")
    private String REGEX_EMAIL_ERROR;

    @Value("${regex.phone_length}")
    private String REGEX_PHONE_LENGTH;

    @Value("${regex.phone_length_error}")
    private String REGEX_PHONE_LENGTH_ERROR;

    @Value("${regex.phone_format}")
    private String REGEX_PHONE_FORMAT;

    @Value("${regex.phone_format_error}")
    private String REGEX_PHONE_FORMAT_ERROR;

    @Value("${regex.password_format}")
    private String REGEX_PASSWORD_FORMAT;

    @Value("${regex.password_format_error}")
    private String REGEX_PASSWORD_FORMAT_ERROR;

    @Value("${regex.nombre_format}")
    private String REGEX_NOMBRE_FORMAT;

    @Value("${regex.nombre_format_error}")
    private String REGEX_NOMBRE_FORMAT_ERROR;

    public void validateRegex(RegexTypes regex, String value, String fieldName) {

        String regularExpression = "";
        String message = "invalid format";

        switch (regex) {
            case ONLY_NUMBERS:
                regularExpression = REGEX_ONLY_NUMBERS;
                message = REGEX_ONLY_NUMBERS_ERROR;
                break;
            case EMAIL:
                regularExpression = REGEX_EMAIL;
                message = REGEX_EMAIL_ERROR;
                break;
            case PHONE_LENGTH:
                regularExpression = REGEX_PHONE_LENGTH;
                message = REGEX_PHONE_LENGTH_ERROR;
                break;
            case PHONE_FORMAT:
                regularExpression = REGEX_PHONE_FORMAT;
                message = REGEX_PHONE_FORMAT_ERROR;
                break;
            case PASSWORD_FORMAT:
                regularExpression = REGEX_PASSWORD_FORMAT;
                message = REGEX_PASSWORD_FORMAT_ERROR;
                break;
            case NOMBRE_FORMAT:
                regularExpression = REGEX_NOMBRE_FORMAT;
                message = REGEX_NOMBRE_FORMAT_ERROR;
                break;
            default:

        }

        var pattern = Pattern.compile(regularExpression);
        var matcher = pattern.matcher(value);
        boolean match = false;
        while (matcher.find()) {
            match = true;
        }

        if (!match) {
            throw new IllegalArgumentException(message);
        }
    }

}
