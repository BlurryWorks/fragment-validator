package com.blurryworks.fragment.validator.engine;

@FunctionalInterface
public interface ThrowableFunction<T, R>
{
	R apply(T t) throws Exception;
}
