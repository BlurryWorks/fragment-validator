package com.blurryworks.fragment.validator.engine;

/**
 * Order of the Enum status fields is important, 
 * as Java's {@link Enum} {@link Comparable} functionality is flagged as final.
 * Instead the {@link Comparable#compareTo(Object)} is implemented based on the {@link #ordinal()} field.
 * 
 * The last element should be the override and overriding case to every status above.
 * As such, success is overridden by Skip and Failure 
 *
 */
public enum ResultStatus
{
	Success,
	/**
	 * Successful validation, but skip lower priority validation on the same field
	 */
	Skip(true),
	Failure,
	FailureHalt(true);

	
	private boolean halt = false;
	private ResultStatus()
	{
	}
	
	private ResultStatus(boolean halt)
	{
		this.halt = halt;
	}
	
	public boolean isHalt()
	{
		return halt;
	}
	
	public boolean isSuccessful()
	{
		return this == Success;
	}
	
	public boolean isFailed()
	{
		return this == Failure || this == FailureHalt;
	}
	
	
}
