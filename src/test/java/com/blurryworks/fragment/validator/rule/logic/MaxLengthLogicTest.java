package com.blurryworks.fragment.validator.rule.logic;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.blurryworks.fragment.validator.ValidationResults;
import com.blurryworks.fragment.validator.engine.ClassValidationPlan;
import com.blurryworks.fragment.validator.engine.ResultStatus;
import com.blurryworks.fragment.validator.rule.MaxLength;

class MaxLengthLogicTest
{

	public class TestObj
	{
		@MaxLength(5)
		public String string;
		
		@MaxLength(5)
		public List<String> list;
		
		@MaxLength(5)
		public String[] array;
	}
	
	public class TestObjInvalid
	{
		@MaxLength(5)
		public Integer wrong = 3;
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
		test.string = "Hello!";
		
		validation.validate(vr,test);		
		assertEquals(ResultStatus.Failure,vr.getStatus());
		
		
		test = new TestObj();
		vr = new ValidationResults();
		test.list = new LinkedList<>();
		for(int i = 0; i < 6; i++)
		{
			test.list.add("Hello");
		}
		
		validation.validate(vr,test);		
		assertEquals(ResultStatus.Failure,vr.getStatus());
		
		
		test = new TestObj();
		vr = new ValidationResults();
		test.array =  new String[6];
		
		validation.validate(vr,test);		
		assertEquals(ResultStatus.Failure,vr.getStatus());
	}
	
	@Test
	void testValidateSuccess() throws Exception
	{
		TestObj test = new TestObj();
		
		test.string = "Hello";
		test.list = new LinkedList<>();
		test.list.add("Hello");
		test.list.add("World");
		test.list.add("!");
		test.array = new String[3];
		
		ValidationResults vr = new ValidationResults();
		validation.validate(vr,test);
		
		assertTrue(vr.getStatus().isSuccessful());
	}
	
	@Test
	void testInvalidType() throws Exception
	{	
		ClassValidationPlan invalidValidation = new ClassValidationPlan();		
		
		assertThrows(InvocationTargetException.class,()->invalidValidation.setupPlan(TestObjInvalid.class) );
		
	}

}
