package com.blurryworks.fragment.validator.rule.logic;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.blurryworks.fragment.validator.ValidationResults;
import com.blurryworks.fragment.validator.engine.ClassValidationPlan;
import com.blurryworks.fragment.validator.engine.ResultStatus;
import com.blurryworks.fragment.validator.rule.MaxLongValue;
import com.blurryworks.fragment.validator.rule.MaxNumericValue;

class MaxNumericLogicTest
{

	public class TestObj
	{
		@MaxNumericValue("5")
		public int ls1;		
		@MaxNumericValue("5")
		public Integer ls2;
		@MaxNumericValue("5")
		public long ls3;
		@MaxNumericValue("5")
		public Long ls4;
		
		
		@MaxLongValue(5)
		public int ll1;		
		@MaxLongValue(5)
		public Integer ll2;
		@MaxLongValue(5)
		public long ll3;
		@MaxLongValue(5)
		public Long ll4; 
		
	}
	
	public class TestObjInvalid
	{
		@MaxNumericValue("5")
		public String wrong = "3";
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
		TestObj test;
		ValidationResults vr;
		
		test = new TestObj();
		vr = new ValidationResults();
		test.ll1 = 6;
		test.ll2 = 6;
		test.ll3 = 6;
		test.ll4 = (long) 6;
		
		
		test.ls1 = 6;
		test.ls2 = 6;
		test.ls3 = 6;
		test.ls4 = (long) 6;
		
		validation.validate(vr,test);		
		assertEquals(ResultStatus.Failure,vr.getStatus());
		
	
	}
	
	@Test
	void testValidateSuccess() throws Exception
	{
		TestObj test;
		ValidationResults vr;
		
		test = new TestObj();
		vr = new ValidationResults();
		test.ll1 = 1;
		test.ll2 = 2;
		test.ll3 = 3;
		test.ll4 = (long) 4;
		
		
		test.ls1 = 5;
		test.ls2 = 1;
		test.ls3 = 2;
		test.ls4 = (long) -10000;
		
		validation.validate(vr,test);		
		
		assertEquals(ResultStatus.Success, vr.getStatus());
	}
	
	@Test
	void testInvalidType() throws Exception
	{	
		ClassValidationPlan invalidValidation = new ClassValidationPlan();		
		
		assertThrows(InvocationTargetException.class,()->invalidValidation.setupPlan(TestObjInvalid.class) );
		
	}

}
