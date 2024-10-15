package com.example.demo.validators;
import com.example.demo.DataObjects.Part;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PartValidator implements ConstraintValidator<ValidPart, Part> {

    @Override
    public boolean isValid(Part part, ConstraintValidatorContext context) {
        boolean isValid = true;
        if (part.getMinimum() > part.getMaximum()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Min cannot be greater than Max.")
                    .addPropertyNode("minimum").addConstraintViolation();
            isValid = false;
        }
        if (part.getInv() < part.getMinimum() || part.getInv() > part.getMaximum()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Inventory must be between Min and Max.")
                    .addPropertyNode("inv").addConstraintViolation();
            isValid = false;
        }
        return isValid;
    }

}