package org.musteat.fragment.validator.engine;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.musteat.fragment.validator.ValidationResult;
import org.musteat.fragment.validator.rule.logic.CustomValidationLogic;
import org.musteat.fragment.validator.rule.logic.MaxLengthLogic;
import org.musteat.fragment.validator.rule.logic.OptionalLogic;
import org.musteat.fragment.validator.rule.logic.RequiredLogic;

/**
 * This class maps a single {@link Field} to an associated Validation described via annotation.
 * Typically these configurations are cached and reused.
 * Implementations must be thread safe.  Calls to {@link #validate(Object)} will be made across multiple threads at the same time.
 *
 * The following implementations can be used as examples of how to write a new ValidationRule 
 * @see RequiredLogic
 * @see MaxLengthLogic
 */
public abstract class ValidationRule implements Comparable<ValidationRule>
{
	protected Field fieldToValidate;
	
	
	/** 
	 * @param validationAnnotation The annotation 
	 * @param fieldToValidate  The Class field that this validation is attached to
	 */
	public ValidationRule(Annotation validationAnnotation,Field fieldToValidate)
	{
		this.fieldToValidate = fieldToValidate;
	
	}
	
	/**
	 * 
	 * @param obj Object with the field to validate.  This can be null and must be handled.
	 * @return Returning null is considered {@link ResultStatus#Success}
	 */
	public abstract ValidationResult validate(Object obj) throws Exception;
	
	
	/**
	 * Priority indicates the sort order of when to perform a validation.
	 * Rules with a higher priority get run before rules with a lower priority. 
	 * While there is no hard limit on how high or low priority can be, the top priority are {@link OptionalLogic} and {@link RequiredLogic} at 10.
	 * The lowest priority is {@link CustomValidationLogic} at 0.
	 * 
	 * The fastest and broadest validations should be higher a higher priority than slower or more specific validation checks.  
	 * Failing out of the validation process as quickly as possible is desirable.
	 * 
	 * 
	 * @return
	 */
	public abstract int getPriority();
	
	
	@Override
	public int compareTo(ValidationRule o)
	{
		if(o == null) throw new NullPointerException();
		return getPriority() - o.getPriority();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof ValidationRule)
			return getPriority() == ((ValidationRule) obj).getPriority();
		else
			return super.equals(obj);
	}
	
	
}
