package com.blurryworks.fragment.validator.rule.logic;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.blurryworks.fragment.validator.ValidationResult;
import com.blurryworks.fragment.validator.engine.ResultStatus;
import com.blurryworks.fragment.validator.engine.ValidationRule;
import com.blurryworks.fragment.validator.rule.RegularExpression;

public class RegularExpressionLogic extends ValidationRule
{	
	Pattern pattern;

	public RegularExpressionLogic(Annotation validationAnnotation, Field fieldToValidate)
	{
		super(validationAnnotation, fieldToValidate);
		
		pattern = Pattern.compile(((RegularExpression) validationAnnotation).value());		
	}

	@Override
	public ValidationResult validate(Object obj) throws Exception
	{
		if(obj == null)
			return null;
		
		String s = obj.toString();
		Matcher m = pattern.matcher(s);
		
		if(!m.matches())
			return new ValidationResult(s + " did not match " + pattern.pattern(), ResultStatus.Failure);
		return null;
	}
	
	
	@Override
	public int getPriority()
	{
		return 6;
	}

}
