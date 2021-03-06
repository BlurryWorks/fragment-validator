package org.musteat.fragment.validator.rule;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.musteat.fragment.validator.rule.logic.MinimumNumericValueLogic;
import org.musteat.fragment.validator.rule.logic.ValidationClassMapping;

@Documented
@Retention(RUNTIME)
@Target(FIELD)
@ValidationClassMapping(MinimumNumericValueLogic.class)
public @interface MinimumLongValue
{
	long value();
}
