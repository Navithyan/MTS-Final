package com.fd.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ProductNameValidator
    implements ConstraintValidator<ValidProductName, String> {

@Override
public boolean isValid(String value,
                       ConstraintValidatorContext context) {
    if (value == null) {
        return true;
    }
    return !value.equalsIgnoreCase("test")
        && !value.equalsIgnoreCase("dummy");
}
}

