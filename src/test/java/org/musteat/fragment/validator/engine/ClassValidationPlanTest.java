package org.musteat.fragment.validator.engine;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.musteat.fragment.validator.ValidationResult;
import org.musteat.fragment.validator.ValidationResults;
import org.musteat.fragment.validator.rule.CustomValidation;
import org.musteat.fragment.validator.rule.MaxLongValue;
import org.musteat.fragment.validator.rule.MinimumLongValue;
import org.musteat.fragment.validator.rule.Optional;
import org.musteat.fragment.validator.rule.Required;

class ClassValidationPlanTest
{
	public class SimpleClass {
		@MinimumLongValue(30)
		public int a = 500;
	}
	
	public class NestedClass {
		
		@Required
		public SimpleClass a;
		
		@Optional
		public SimpleClass b;
		
		@MaxLongValue(100)
		public Integer c;
		
		@MaxLongValue(2)
		@MinimumLongValue(1)
		public Integer d = null;
	}
	
	public class CustomValidationClass implements CustomValidation {
		
		
		private ResultStatus customResult;
		
		public CustomValidationClass(ResultStatus customResult)
		{
			this.customResult = customResult;
		}

		@Override
		public ValidationResult customValidation()
		{
			return new ValidationResult("Test Result", customResult);
		}
		
	}

	@Test
	void testSetupPlan() throws InvocationTargetException, InstantiationException, IllegalAccessException, IllegalArgumentException, NoSuchMethodException, SecurityException
	{
		ClassValidationPlan cvp = new ClassValidationPlan();
		
		Set<Class<?>> additionalClasses;
		
		additionalClasses = cvp.setupPlan(SimpleClass.class);
		
		assertEquals(0, additionalClasses.size(),"There should be no additional classes to validate for SimpleClass");
		
		cvp = new ClassValidationPlan();
		
		additionalClasses = cvp.setupPlan(NestedClass.class);
		
		assertEquals(2, additionalClasses.size(),"Should be two results to examine, SimpleClass and Integer");
		
		
		
	}

	@Test
	void testValidate() throws Exception
	{
		ClassValidationPlan cvp = new ClassValidationPlan();
		cvp.setupPlan(SimpleClass.class);
		
		SimpleClass test = new SimpleClass();
		
		ValidationResults vrs = new ValidationResults();
		cvp.validate(vrs, test);
		
		assertEquals(ResultStatus.Success,vrs.getStatus());
		
		assertEquals(0, vrs.size());
		
		test = new SimpleClass();
		test.a = 0;
		vrs = new ValidationResults();
		
		cvp.validate(vrs, test);
		
		assertEquals(ResultStatus.Failure, vrs.getStatus());
		assertEquals(1, vrs.size());
		
		
	}

	@Test
	void testIsCustomValidation() throws Exception
	{
		ClassValidationPlan cvp = new ClassValidationPlan();
		cvp.setupPlan(CustomValidationClass.class);
		
		CustomValidationClass cvc = new CustomValidationClass(ResultStatus.Success);
		ValidationResults vrs = new ValidationResults();
		
		cvp.validate(vrs, cvc);
		
		assertEquals(ResultStatus.Success, vrs.getStatus());
		assertEquals(true, cvp.isCustomValidation());
		
		
		cvp = new ClassValidationPlan();
		cvp.setupPlan(SimpleClass.class);
		vrs = new ValidationResults();
		cvp.validate(vrs, new SimpleClass());
		assertEquals(ResultStatus.Success, vrs.getStatus());
		assertEquals(false, cvp.isCustomValidation());
		
	}

}
