package com.hospitalsearch.service.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DiseaseValidation implements ConstraintValidator<Disease,com.hospitalsearch.util.Disease> {
    @Override
    public void initialize(Disease disease) {
    }

    @Override
    public boolean isValid(com.hospitalsearch.util.Disease disease, ConstraintValidatorContext constraintValidatorContext) {
        if (disease==null){
            return true;
        }
        Pattern p = Pattern.compile("YES|NO");
        Matcher m = p.matcher(disease.toString());
        return m.matches();
    }
    }

