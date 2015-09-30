package com.mirasworks.tools.pattern.pipeline;


/**
 * 
 * @author Koda
 *
 * @param <T>
 * @param <R>
 */
public interface IPipe<I, O> {

	/**
	 * apply a process on the I input type and return 
	 * the same type with internal changes
	 * @param I in
	 * @return I modiefied
	 */
	 I process(I in);
	 /**
	  * Apply a process on the I input type and return a different object Type
	  * @param in
	  * @return O modified
	  */
	 O processCast(I in) throws TransformException;
}
