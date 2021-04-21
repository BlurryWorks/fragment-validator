package com.blurryworks.fragment.validator.rule.logic;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Objects;

import com.blurryworks.fragment.validator.ValidationResult;
import com.blurryworks.fragment.validator.engine.ResultStatus;
import com.blurryworks.fragment.validator.engine.ValidationRule;
import com.blurryworks.fragment.validator.rule.Required;

public class OptionalLogic extends ValidationRule
{

	public OptionalLogic(Annotation validationAnnotation, Field fieldToValidate) throws Exception
	{
		super(validationAnnotation, fieldToValidate);		
		if(fieldToValidate.getAnnotation(Required.class) != null)
			throw new Exception("Optional and Required annotations can not exist on the same field");
	}

	@Override
	public ValidationResult validate(Object obj) throws Exception
	{		
		if(Objects.isNull(obj))
			return new ValidationResult("Field is null, skipping the rest of validaiton", ResultStatus.Skip);
		return null;
	}

	@Override
	public int getPriority()
	{
		return 10;
	}

}
