package org.musteat.fragment.validator.rule.logic;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;


import java.lang.annotation.Retention;

import java.lang.annotation.Target;

import org.musteat.fragment.validator.engine.ValidationRule;

/**
 * Used to map a specific annotation to the class providing the logic.  
 * See {@link ValidationRule}
 */
@Retention(RUNTIME)
@Target(ANNOTATION_TYPE)
public @interface ValidationClassMapping
{
	Class<? extends ValidationRule> value();
}
