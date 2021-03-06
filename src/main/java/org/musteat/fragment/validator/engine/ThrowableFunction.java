package org.musteat.fragment.validator.engine;

@FunctionalInterface
public interface ThrowableFunction<T, R>
{
	R apply(T t) throws Exception;
}
