package com.blurryworks.fragment.validator;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blurryworks.fragment.validator.engine.ClassValidationPlan;

/**
 * By default, as new classes are validated, a cache is built of validation steps to perform for that class.
 * This cache can be warmed (for example before a server starts accepting requests) by calling {@link #cacheClassValidation(Class)}
 *  
 * This class is thread safe and for effective caching, should be used as a shared instance. 
 */
public class ValidationProcessor
{
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	boolean classValidationCaching = true;

	Map<Class<?>,ClassValidationPlan> validaitonPlan = new HashMap<>();
	
	/**
	 * Enables or disables Class Validation Caching
	 * When disabled, the existing cache is cleared.  See {@link #clearClassValidationCache()}
	 *  
	 */
	public void setClassValidationCaching(boolean enable)
	{
		this.classValidationCaching = enable;
		clearClassValidationCache();
	}
	
	public void clearClassValidationCache()
	{
		validaitonPlan.clear();
	}
	
	/**
	 * 
	 * @param c Class to scan for annotations and add to the cache
	 */
	public void cacheClassValidation(Class<?> c) throws Exception
	{
		generateValidationPlan(c);
	}
	
	
	/**
	 * 
	 * @param c Can be null, Class to add to validation cache
	 * @throws Exception
	 */
	private void generateValidationPlan(Class<?> c) throws Exception
	{
		if(c == null)
			return;
		Queue<Class<?>> classesToGeneratePlans = new LinkedList<>();
		
		classesToGeneratePlans.add(c);
		
		while(!classesToGeneratePlans.isEmpty())
		{
			Class<?> clazz = classesToGeneratePlans.poll();
			
			if(!validaitonPlan.containsKey(clazz))
			{
				ClassValidationPlan cvp = new ClassValidationPlan();
				classesToGeneratePlans.addAll(cvp.setupPlan(clazz));
				validaitonPlan.put(clazz, cvp);
			}
		}
		
	}
	
	public ValidationResults validate(Object objectToValidate) throws Exception
	{
		ValidationResults results = new ValidationResults();
		
		Queue<Object> objectsToValidate = new LinkedList<>();
		
		objectsToValidate.add(objectToValidate);
		
		
		while(!objectsToValidate.isEmpty())
		{
			Object underValidation = objectsToValidate.poll();
			ClassValidationPlan cvp = validaitonPlan.get(underValidation.getClass());
			if(cvp != null)
			{
				cvp.validate(results, underValidation);
				for(Field f : underValidation.getClass().getFields())
				{
					if(validaitonPlan.containsKey(f.getType()))
					{
						Object o = f.get(underValidation);
						if(o != null)
							objectsToValidate.add(o);
					}
				}
			}
		}
		
		return results;
	}
	

}
