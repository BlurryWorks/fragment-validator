package com.blurryworks.fragment.validator;

import java.util.LinkedList;

import com.blurryworks.fragment.validator.engine.ResultStatus;

public class ValidationResults extends LinkedList<ValidationResult>
{

	private static final long serialVersionUID = -7661274570226743109L;
	
	ResultStatus status = ResultStatus.Success;
	

	public ResultStatus addResult(ValidationResult result)
	{
		if (result != null)
		{
			this.add(result);
			if (status.compareTo(result.getStatus()) < 0)
			{
				status = result.getStatus();
			}
		}
		return status;
	}

	public ResultStatus getStatus()
	{
		return status;
	}

	public boolean isHalt()
	{
		return status.isHalt();

	}
	
}
