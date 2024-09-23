package com.example.demo.validators;
import com.example.demo.DataObjects.Part;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PartValidator implements ConstraintValidator<ValidPart, Part> {

    @Override
    public boolean isValid(Part part, ConstraintValidatorContext context) {
        boolean isValid = true;
        // Validate min is not greater than max
        if (part.getMinimum() > part.getMaximum()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Minimum cannot be greater than Maximum.")
                    .addPropertyNode("minimum").addConstraintViolation();
            isValid = false;
        }
        // Validate inv is between min and max
        if (part.getInv() < part.getMinimum() || part.getInv() > part.getMaximum()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Inventory must be between Minimum and Maximum.")
                    .addPropertyNode("inv").addConstraintViolation();
            isValid = false;
        }
        return isValid;
    }

}