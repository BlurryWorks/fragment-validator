package org.musteat.fragment.validator.engine;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.musteat.fragment.validator.ValidationResult;
import org.musteat.fragment.validator.ValidationResults;
import org.musteat.fragment.validator.rule.CustomValidation;
import org.musteat.fragment.validator.rule.logic.ValidationClassMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassValidationPlan
{
	Logger log = LoggerFactory.getLogger(this.getClass());
	Class<?> plannedClass;
	Map<Field, List<ValidationRule>> plan = new HashMap<>();
	boolean customValidation = false;

	public Set<Class<?>> setupPlan(Class<?> plannedClass) throws InvocationTargetException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, NoSuchMethodException, SecurityException
	{
		this.plannedClass = plannedClass;
		Set<Class<?>> additionalClasses = new HashSet<>();

		for (Field f : plannedClass.getFields())
		{
			Class<?> fieldClass = f.getType();
			if (!(fieldClass.isPrimitive() || fieldClass == String.class))
			{
				additionalClasses.add(f.getType());
			}

			List<ValidationRule> fieldValidationRules = getValidationsForField(f);
			if (!fieldValidationRules.isEmpty())
			{
				plan.put(f, fieldValidationRules);
			}
		}

		for (Class<?> interfaceClass : plannedClass.getInterfaces())
		{
			if (interfaceClass.equals(CustomValidation.class))
			{
				customValidation = true;
				break;
			}
		}

		return additionalClasses;
	}

	public void validate(ValidationResults results, Object o)
	{
		ValidationResult vr;
		for (Field f : plan.keySet())
		{
			for (ValidationRule rule : plan.get(f))
			{
				try
				{
					vr = rule.validate(f.get(o));
				} catch (Exception validateException)
				{
					vr = new ValidationResult(rule.getClass().getName() + " threw an exception while validating field.",
							ResultStatus.FailureHalt);
				}

				if (vr != null)
				{
					vr.setContext(plannedClass, f);
					results.addResult(vr);
				}

				if (results.isHalt())
					break;

			}
		}
	}

	public boolean isCustomValidation()
	{
		return customValidation;
	}

	public List<ValidationRule> getValidationsForField(Field f)
			throws InvocationTargetException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			NoSuchMethodException, SecurityException
	{
		List<ValidationRule> filteredValidationRules = new LinkedList<>();

		for (Annotation a : f.getAnnotations())
		{
			ValidationClassMapping[] classMapping = a.annotationType()
					.getAnnotationsByType(ValidationClassMapping.class);

			switch (classMapping.length)
			{
				case 0:
					continue;
				case 1:
					break;
				default:
					/*
					 * Since validation setup can be run at any point in processing, but is normally run once during cache creation.
					 * It should be safe to proceed, but only one validation rule logic class (which is not guaranteed) will be used.
					 * Annotation definitions should have exactly one @ValidationClassMapping annotation 
					 */
					log.error(
							"Validation Engine does not support multiple validation logic classes to be attached to a single annotation: "
									+ a.toString() + " Only the first ValidationClassMapping will be used."
									+ "Attached to field " + f.getName() + " in object " + f.getDeclaringClass().toString() );
					break;

			}

			Class<? extends ValidationRule> rule = classMapping[0].value();
			filteredValidationRules.add(rule.getConstructor(Annotation.class, Field.class).newInstance(a, f));
		}

		filteredValidationRules.sort(null);
		return filteredValidationRules;
	}

}
