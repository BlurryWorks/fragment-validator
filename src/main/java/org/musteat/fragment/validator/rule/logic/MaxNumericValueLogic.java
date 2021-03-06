package org.musteat.fragment.validator.rule.logic;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.function.Function;

import org.musteat.fragment.validator.ValidationResult;
import org.musteat.fragment.validator.engine.ResultStatus;
import org.musteat.fragment.validator.engine.ThrowableFunction;
import org.musteat.fragment.validator.engine.ValidationRule;
import org.musteat.fragment.validator.rule.MaxDoubleValue;
import org.musteat.fragment.validator.rule.MaxLongValue;
import org.musteat.fragment.validator.rule.MaxNumericValue;

public class MaxNumericValueLogic extends ValidationRule
{

	ThrowableFunction<Object, ValidationResult> validationMethod;
	Annotation annotation;

	Function<Object, ValidationResult> typeBaseValidation;

	Double maxDoubleValue;
	Long maxLongValue;


	public MaxNumericValueLogic(Annotation validationAnnotation, Field fieldToValidate) throws Exception
	{
		super(validationAnnotation, fieldToValidate);
		this.annotation = validationAnnotation;

		Class<?> t = fieldToValidate.getType();

		if (t.isPrimitive())
		{
			if (t.isAssignableFrom(int.class) || t.isAssignableFrom(long.class))
			{
				configureLongValidation();
			} else if (t.isAssignableFrom(double.class) || t.isAssignableFrom(long.class))
			{
				configureDoubleValidation();
			}
		} else if (Long.class.isAssignableFrom(t)
				|| Integer.class.isAssignableFrom(t))
		{
			configureLongValidation();
		} else if (Double.class.isAssignableFrom(t)
				|| Float.class.isAssignableFrom(t))
		{
			configureDoubleValidation();
		}
		else
		{
			throw new Exception("In Class: " + fieldToValidate.getDeclaringClass().getSimpleName() + "Type: " + fieldToValidate.getType().toString() + " validation is not supported by " + validationAnnotation.annotationType().getName());
		}

	}

	private void configureLongValidation() throws Exception
	{
		validationMethod = this::validateLong;
		if (annotation instanceof MaxLongValue)
			this.maxLongValue = ((MaxLongValue) annotation).value();
		else if (annotation instanceof MaxNumericValue)
			this.maxLongValue = Long.valueOf(((MaxNumericValue) annotation).value());
		else
			throw new Exception("Type: " + fieldToValidate.getType().toString() + " validation is not supported by "
					+ annotation.annotationType().getName());

	}

	private void configureDoubleValidation() throws Exception
	{
		validationMethod = this::validateDouble;
		if (annotation instanceof MaxDoubleValue)
			this.maxDoubleValue = ((MaxDoubleValue) annotation).value();
		else if (annotation instanceof MaxNumericValue)
			this.maxDoubleValue = Double.valueOf(((MaxNumericValue) annotation).value());
		else
			throw new Exception("Type: " + fieldToValidate.getType().toString() + " validation is not supported by "
					+ annotation.annotationType().getName());
	}

	@Override
	public int getPriority()
	{
		return 8;
	}

	public ValidationResult validateLong(Object obj) throws IllegalArgumentException, IllegalAccessException
	{
		Number n = (Number) obj;

		if (n.longValue() > maxLongValue)
			return new ValidationResult(n + " is greater than " + maxLongValue, ResultStatus.Failure);
		return null;
	}

	public ValidationResult validateDouble(Object obj) throws IllegalArgumentException, IllegalAccessException
	{
		Number n = (Number) obj;
		if (n.doubleValue() > maxDoubleValue)
			return new ValidationResult(n + " is greater than " + maxDoubleValue, ResultStatus.Failure);
		return null;
	}

	@Override
	public ValidationResult validate(Object obj) throws Exception
	{
		if (obj == null)
			return null;
		return validationMethod.apply(obj);
	}

}
