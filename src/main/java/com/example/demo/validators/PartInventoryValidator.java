package com.example.demo.validators;

import com.example.demo.DataObjects.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class PartInventoryValidator implements ConstraintValidator<ValidPartInventory, Part> {
    @Autowired
    private ApplicationContext context;

    @Override
    public void initialize(ValidPartInventory constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Part part, ConstraintValidatorContext constraintValidatorContext) {
        return part.getInv() <= part.getMaximum();
    }
}
