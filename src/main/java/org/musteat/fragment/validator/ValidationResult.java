package org.musteat.fragment.validator;

import java.lang.reflect.Field;

import org.musteat.fragment.validator.engine.ResultStatus;

public class ValidationResult {
	
	String message;
	ResultStatus status;
	String context;
	
	public ValidationResult(ResultStatus status)
	{
		this.status = status;
	}
	
	public ValidationResult(String message, ResultStatus status)
	{		
		this.message = message;
		this.status = status;
	}
	
	public String getMessage()
	{
		return message;
	}
	
	public ResultStatus getStatus()
	{
		return this.status;
	}
	
	public String getContext()
	{
		if(context != null)
			return context;
		else
			return "";
	}
	
	public void setContext(String context)
	{
		this.context = context;
	}
	
	public void setContext(Class<?> clazz, Field field)
	{
		this.context = "Class: " + clazz.getCanonicalName() + " Field: " + field.getName();
	}
	
	
}