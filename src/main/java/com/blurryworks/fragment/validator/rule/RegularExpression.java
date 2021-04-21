package com.blurryworks.fragment.validator.rule;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.blurryworks.fragment.validator.rule.logic.RegularExpressionLogic;
import com.blurryworks.fragment.validator.rule.logic.ValidationClassMapping;

/**
 * This uses {@link Pattern} to perform the regular expression, only calling {@link Matcher#matches()}
 *  
 */
@Documented
@Retention(RUNTIME)
@Target(FIELD)
@ValidationClassMapping(RegularExpressionLogic.class)
public @interface RegularExpression
{
	String value();
}
