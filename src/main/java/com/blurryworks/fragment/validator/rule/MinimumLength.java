package com.blurryworks.fragment.validator.rule;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Collection;

import com.blurryworks.fragment.validator.rule.logic.MinimumLengthLogic;
import com.blurryworks.fragment.validator.rule.logic.ValidationClassMapping;


/**
 * Validates the minimum length a String or data structure. 
 * For this validation to pass, the structure must contain at least the number of elements specified.
 * This check is inclusive.
 * 
 * It supports the following structures and interfaces and their subclasses.
 *
 * <ul>
 * 	<li>Array primitive</li>
 *  <li>{@link Collection}</li>
 * 	<li>{@link CharSequence}</li>
 * </ul>
 * 
 * Importantly, "length" of String/CharSequences is based on Java's concept of a character.  This means individual characters and not their combined glyph representation.
 * 🐱‍👤 for example is treated as more than one character.
 * 
 * 
 * @see MaxLength
 * @see Optional
 */
@Documented
@Retention(RUNTIME)
@Target(FIELD)
@ValidationClassMapping(MinimumLengthLogic.class)
public @interface MinimumLength
{
	int value();

}
