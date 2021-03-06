package org.musteat.fragment.validator.rule;

import org.musteat.fragment.validator.ValidationResult;

/**
 * If a class implements this interface, it will automatically be called during validation
 * This phase of validation is performed after all other field specific validation is performed.
 */
public interface CustomValidation
{
	/**
	 * 
	 * @return The result of the custom validation
	 */
	public ValidationResult customValidation();

}
