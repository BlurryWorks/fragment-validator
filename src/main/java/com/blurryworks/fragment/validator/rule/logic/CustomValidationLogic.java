package com.blurryworks.fragment.validator.rule.logic;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.blurryworks.fragment.validator.ValidationResult;
import com.blurryworks.fragment.validator.engine.ValidationRule;
import com.blurryworks.fragment.validator.rule.CustomValidation;

/**
 * This is a special validation logic that triggers a phase of validation 
 * that is  
 */
public class CustomValidationLogic extends ValidationRule
{

	public CustomValidationLogic(Annotation validationAnnotation, Field fieldToValidate)
	{
		super(validationAnnotation, fieldToValidate);
	}

	@Override
	public ValidationResult validate(Object obj) throws Exception
	{
		CustomValidation validationInterface = (CustomValidation)obj;
		return validationInterface.customValidation();
	}
	
	
	@Override
	public int getPriority()
	{
		return 0;
	}
	
	

}
