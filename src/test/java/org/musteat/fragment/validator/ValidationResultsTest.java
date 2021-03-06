package org.musteat.fragment.validator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.musteat.fragment.validator.engine.ResultStatus;

class ValidationResultsTest
{

	@Test
	void testGetStatus()
	{
		ValidationResults vrs = new ValidationResults();
		
		vrs.addResult(new ValidationResult("1",ResultStatus.Success));
		vrs.addResult(new ValidationResult("2",ResultStatus.Success));
		vrs.addResult(new ValidationResult("3",ResultStatus.Success));
		vrs.addResult(new ValidationResult("FAIL",ResultStatus.Failure));
		vrs.addResult(new ValidationResult("4",ResultStatus.Success));
		vrs.addResult(new ValidationResult("5",ResultStatus.Success));
		vrs.addResult(new ValidationResult("6",ResultStatus.Success));
		
		assertEquals(ResultStatus.Failure, vrs.getStatus());
		
		
		vrs = new ValidationResults();
		
		vrs.addResult(new ValidationResult("1",ResultStatus.Success));
		vrs.addResult(new ValidationResult("2",ResultStatus.Success));
		vrs.addResult(new ValidationResult("3",ResultStatus.Success));
		
		assertEquals(ResultStatus.Success, vrs.getStatus());
		
		
		vrs = new ValidationResults();
		
		vrs.addResult(new ValidationResult("1",ResultStatus.Success));
		vrs.addResult(new ValidationResult("2",ResultStatus.Skip));
		vrs.addResult(new ValidationResult("3",ResultStatus.Failure));
		vrs.addResult(new ValidationResult("4",ResultStatus.FailureHalt));
		
		assertEquals(ResultStatus.FailureHalt, vrs.getStatus());
		
		vrs = new ValidationResults();
		
		vrs.addResult(new ValidationResult("1",ResultStatus.FailureHalt));
		vrs.addResult(new ValidationResult("2",ResultStatus.Failure));
		vrs.addResult(new ValidationResult("3",ResultStatus.Skip));
		vrs.addResult(new ValidationResult("4",ResultStatus.Success));	
			
		assertEquals(ResultStatus.FailureHalt, vrs.getStatus());
		
		
				
	}

	@Test
	void testIsHalt()
	{
		ValidationResults vrs = new ValidationResults();
		
		vrs.addResult(new ValidationResult("1",ResultStatus.Success));
		vrs.addResult(new ValidationResult("2",ResultStatus.Success));
		vrs.addResult(new ValidationResult("3",ResultStatus.Success));
		vrs.addResult(new ValidationResult("FAIL",ResultStatus.Failure));
		vrs.addResult(new ValidationResult("4",ResultStatus.Success));
		vrs.addResult(new ValidationResult("5",ResultStatus.Success));
		vrs.addResult(new ValidationResult("6",ResultStatus.Success));
		
		assertFalse( vrs.isHalt());
		
		vrs = new ValidationResults();
		
		vrs.addResult(new ValidationResult("1",ResultStatus.Skip));
		
		assertTrue( vrs.isHalt());
		
	}

}
