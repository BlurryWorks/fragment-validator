package org.musteat.fragment.validator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.musteat.fragment.validator.engine.ResultStatus;
import org.musteat.fragment.validator.rule.Required;

class ValidationProcessorTest
{

	public class NestedTest1
	{
		public NestedTest2 nullable;
		@Required
		public NestedTest2 required;
	}
	
	public class NestedTest2
	{
		@Required
		public String hello;
	}
	
	ValidationProcessor vp = null;
	
	@BeforeEach
	public void setup()
	{
		vp = new ValidationProcessor();		
		
	}
	
	@Test
	void nullNestedClassValidation() throws Exception
	{
		vp.setClassValidationCaching(true);
		vp.cacheClassValidation(NestedTest1.class);
		vp.cacheClassValidation(NestedTest2.class);
		
		
		NestedTest1 underTest = new NestedTest1();
		
		underTest.nullable = null;
		underTest.required = new NestedTest2();
		underTest.required.hello = "Hi";
		
		ValidationResults vrs = vp.validate(underTest);
		
		assertEquals(ResultStatus.Success, vrs.getStatus());		
	}
	
	@Test
	void failureTest() throws Exception
	{
		vp.setClassValidationCaching(true);
		vp.cacheClassValidation(NestedTest1.class);
		vp.cacheClassValidation(NestedTest2.class);
		
		NestedTest1 underTest = new NestedTest1();
		
		underTest.nullable = new NestedTest2();
		underTest.nullable.hello = null;
		underTest.required = null;
		
		ValidationResults vrs = vp.validate(underTest);
		
		assertEquals(ResultStatus.Failure, vrs.getStatus());	
		
	}

}
