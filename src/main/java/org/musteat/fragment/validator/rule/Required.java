package org.musteat.fragment.validator.rule;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.musteat.fragment.validator.rule.logic.RequiredLogic;
import org.musteat.fragment.validator.rule.logic.ValidationClassMapping;

/**
 * Ensures that a field is not null.  
 * This does not ensure that a {@link String} is not empty.  
 */
@Documented
@Retention(RUNTIME)
@Target(FIELD)
@ValidationClassMapping(RequiredLogic.class)
public @interface Required
{

}
