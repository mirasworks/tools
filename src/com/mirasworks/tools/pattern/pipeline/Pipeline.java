package com.mirasworks.tools.pattern.pipeline;

import java.util.LinkedList;

public class Pipeline<I, O> extends LinkedList<IPipe<I, O>> {

	private static final long serialVersionUID = -316927047880001591L;

	/**
	 * apply a process pipe on the given input
	 * and return a new product
	 * 
	 * @see addPipe
	 * @param in
	 * @return
	 */
	public I process(I in) {

		for (IPipe<I, O> pipe : this) {
			in = pipe.process(in);
		}
		return in;
	}

	
		
	/**
	 * 
	 * @param I in
	 * @return LinkedList<O>
	 * @throws TransformException
	 */
	public LinkedList<O> processTransform(I in) throws TransformException {
		LinkedList<O> transformeds = new LinkedList<O>();
		for (IPipe<I, O> pipe : this) {
			O out = pipe.processCast(in);
			transformeds.add(out);
		}
		return transformeds;
	}

}
