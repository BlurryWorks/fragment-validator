package com.blurryworks.fragment.validator.rule;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.blurryworks.fragment.validator.rule.logic.OptionalLogic;
import com.blurryworks.fragment.validator.rule.logic.ValidationClassMapping;

/**
 * A field that is nullable
 * If a field is marked as {@link Optional} and {@link Required} then a validation error will be triggered.
 * The default assumption is that all fields are {@link Required}.
 * If a field is {@link Optional} and is null, then other validations assigned to that field will not be performed.
 * If the field is not null, then those additional validations will be performed
 * 
 */
@Documented
@Retention(RUNTIME)
@Target(FIELD)
@ValidationClassMapping(OptionalLogic.class)
public @interface Optional
{

}
