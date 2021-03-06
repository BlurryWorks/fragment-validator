package org.musteat.fragment.validator.rule.logic;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.musteat.fragment.validator.ValidationResults;
import org.musteat.fragment.validator.engine.ClassValidationPlan;
import org.musteat.fragment.validator.rule.Required;

class RequiredLogicTest
{

	public class TestObj
	{
		@Required
		public String foo;
	}
	
	ClassValidationPlan validation;
	
	
	
	@BeforeEach
	void setup() throws InvocationTargetException, InstantiationException, IllegalAccessException, IllegalArgumentException, NoSuchMethodException, SecurityException
	{
		validation = new ClassValidationPlan();
		validation.setupPlan(TestObj.class);
	}
	
	
	@Test
	void testValidateFailure() throws Exception
	{
		TestObj test = new TestObj();
		ValidationResults vr = new ValidationResults();
		validation.validate(vr,test);
		
		assertFalse(vr.getStatus().isSuccessful());
	
	}
	
	@Test
	void testValidateSuccess() throws Exception
	{
		TestObj test = new TestObj();
		
		test.foo = "";
		ValidationResults vr = new ValidationResults();
		validation.validate(vr,test);
		
		assertTrue(vr.getStatus().isSuccessful());
	}

}
