package org.musteat.fragment.validator.rule.logic;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;

import org.musteat.fragment.validator.ValidationResult;
import org.musteat.fragment.validator.engine.ResultStatus;
import org.musteat.fragment.validator.engine.ThrowableFunction;
import org.musteat.fragment.validator.engine.ValidationRule;
import org.musteat.fragment.validator.rule.MaxLength;;

public class MaxLengthLogic extends ValidationRule
{

	ThrowableFunction<Object,ValidationResult> validationMethod;
	MaxLength maxLengthAnnotation;


	public MaxLengthLogic(Annotation validationAnnotation, Field fieldToValidate) throws Exception
	{
		super(validationAnnotation, fieldToValidate);	
		maxLengthAnnotation = (MaxLength) validationAnnotation;		
		
		
		if(fieldToValidate.getType().isArray())
		{
			validationMethod = this::validateArray;			
		}
		else if(Collection.class.isAssignableFrom(fieldToValidate.getType()))
		{
			validationMethod = this::validateCollection;			
		}
		else if(String.class.isAssignableFrom(fieldToValidate.getType()))
		{
			validationMethod = this::validateString;
		}
		else
		{
			throw new Exception("In Class: " + fieldToValidate.getDeclaringClass().getSimpleName() + "Type: " + fieldToValidate.getType().toString() + " validation is not supported by " + validationAnnotation.annotationType().getName());
		}
	}

	@Override
	public ValidationResult validate(Object obj) throws Exception
	{
		if(obj == null)
			return null;
		return validationMethod.apply(obj);
	}
	
	public ValidationResult validateArray(Object obj) throws Exception
	{
		Object arrayObject = obj;		
		if(Array.getLength(arrayObject) > maxLengthAnnotation.value() )
			return new ValidationResult(ResultStatus.Failure);
		return null;
	}	
	
	public ValidationResult validateString(Object obj) throws Exception
	{
		String stringObject = (String) obj;		
		if(stringObject.length() > maxLengthAnnotation.value())
			return new ValidationResult(ResultStatus.Failure);
		return null;
	}
	
	public ValidationResult validateCollection(Object obj) throws Exception
	{
		Collection<?> setObject = (Collection<?>) obj;
		if(setObject.size() > maxLengthAnnotation.value())
			return new ValidationResult(ResultStatus.Failure);
		return null;
	}
	
	
	@Override
	public int getPriority()
	{
		return 9;
	}

}
