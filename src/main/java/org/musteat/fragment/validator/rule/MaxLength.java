package org.musteat.fragment.validator.rule;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Collection;

import org.musteat.fragment.validator.rule.logic.MaxLengthLogic;
import org.musteat.fragment.validator.rule.logic.ValidationClassMapping;

/**
 * Validates the maximum length of a String or data structure.  
 * For this validation to pass, the structure must contain no more than number of elements specified
 * This check is inclusive. 
 * 
 * It supports the following structures and interfaces and their subclasses.  
 *
 * <ul>
 * 	<li>Array primitive</li>
 *  <li>{@link Collection}</li>
 * 	<li>{@link String}</li>
 * </ul>
 * 
 * @see MinimumLength
 * 
 */
@Documented
@Retention(RUNTIME)
@Target(FIELD)
@ValidationClassMapping(MaxLengthLogic.class)
public @interface MaxLength
{
	int value();

}
