package com.fluffy.spring.validation.validators;

import org.springframework.core.env.Environment;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public abstract class AbstractFormValidator implements Validator {
    protected final Environment env;

    public AbstractFormValidator(Environment env) {
        this.env = env;
    }

    public enum InputType {
        NAME,
        PASSWORD,
        EMAIL,
        DATE,
        GENDER,
        ENABLEABILITY,
        ANY;

        @Override
        public String toString() {
            switch (this) {
                case NAME:
                    return "^[A-ZА-Я][a-zа-яA-ZА-Я-]{1,48}[a-zа-яA-ZА-Я]$";
                case PASSWORD:
                    return "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$";
                case EMAIL:
                    return "^(?=.{1,64}@)[\\p{L}0-9_-]+(\\.[\\p{L}0-9_-]+)*@[^-][\\p{L}0-9-]+(\\.[\\p{L}0-9-]+)*(\\.[\\p{L}]{2,})$";
                case DATE:
                    return "^(?:(?:(?:0[1-9]|1\\d|2[0-8])\\/(?:0[1-9]|1[0-2])|(?:29|30)\\/(?:0[13-9]|1[0-2])|31\\/(?:0[13578]|1[02]))" +
                            "\\/[1-9]\\d{3}|29\\/02(?:\\/[1-9]\\d(?:0[48]|[2468][048]|[13579][26])|(?:[2468][048]|[13579][26])00))$";
                case GENDER:
                    return "^(male)|(female)$";
                case ENABLEABILITY:
                    return "^(enable)|(disable)$";
                case ANY:
                default:
                    return "^.*$";
            }
        }
    }

    public void validate(InputType inputType, String input, Errors errors, String field, String errorCode) {
        if (!input.matches(inputType.toString())) {
            errors.rejectValue(field, errorCode);
        }
    }

    public void validateNullableStringByLength(String input, int length, Errors errors, String field, String errorCode) {
        if (input != null && input.length() > length) {
            errors.rejectValue(field, errorCode);
        }
    }
}
