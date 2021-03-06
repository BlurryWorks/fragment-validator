package org.musteat.fragment.validator.rule;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.musteat.fragment.validator.rule.logic.MaxNumericValueLogic;
import org.musteat.fragment.validator.rule.logic.ValidationClassMapping;

@Documented
@Retention(RUNTIME)
@Target(FIELD)
@ValidationClassMapping(MaxNumericValueLogic.class)
public @interface MaxLongValue
{
	long value();
}
