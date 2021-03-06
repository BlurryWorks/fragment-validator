package org.musteat.fragment.validator.rule.logic;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.function.Function;

import org.musteat.fragment.validator.ValidationResult;
import org.musteat.fragment.validator.engine.ResultStatus;
import org.musteat.fragment.validator.engine.ThrowableFunction;
import org.musteat.fragment.validator.engine.ValidationRule;
import org.musteat.fragment.validator.rule.MinimumDoubleValue;
import org.musteat.fragment.validator.rule.MinimumLongValue;
import org.musteat.fragment.validator.rule.MinimumNumericValue;

public class MinimumNumericValueLogic extends ValidationRule
{

	ThrowableFunction<Object, ValidationResult> validationMethod;
	Annotation annotation;

	Function<Object, ValidationResult> typeBaseValidation;

	Double minDoubleValue;
	Long minLongValue;

	public MinimumNumericValueLogic(Annotation validationAnnotation, Field fieldToValidate) throws Exception
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
		} else if (Long.class.isAssignableFrom(t) || Integer.class.isAssignableFrom(t))
		{
			configureLongValidation();
		} else if (Double.class.isAssignableFrom(t) || Float.class.isAssignableFrom(t))
		{
			configureDoubleValidation();
		} else
		{
			throw new Exception("In Class: " + fieldToValidate.getDeclaringClass().getSimpleName() + "Type: " + fieldToValidate.getType().toString() + " validation is not supported by " + validationAnnotation.annotationType().getName());
		}

	}

	private void configureLongValidation() throws Exception
	{
		validationMethod = this::validateLong;
		if (annotation instanceof MinimumLongValue)
			this.minLongValue = ((MinimumLongValue) annotation).value();
		else if (annotation instanceof MinimumNumericValue)
			this.minLongValue = Long.valueOf(((MinimumNumericValue) annotation).value());
		else
			throw new Exception("Type: " + fieldToValidate.getType().toString() + " validation is not supported by "
					+ annotation.annotationType().getName());

	}

	private void configureDoubleValidation() throws Exception
	{
		validationMethod = this::validateDouble;
		if (annotation instanceof MinimumDoubleValue)
			this.minDoubleValue = ((MinimumDoubleValue) annotation).value();
		else if (annotation instanceof MinimumNumericValue)
			this.minDoubleValue = Double.valueOf(((MinimumNumericValue) annotation).value());
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

		if (n.longValue() < minLongValue)
			return new ValidationResult(n + " is less than " + minLongValue, ResultStatus.Failure);
		return null;
	}

	public ValidationResult validateDouble(Object obj) throws IllegalArgumentException, IllegalAccessException
	{
		Number n = (Number) obj;
		if (n.doubleValue() < minDoubleValue)
			return new ValidationResult(n + " is less than " + minDoubleValue, ResultStatus.Failure);
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
