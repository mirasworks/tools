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
	 * apply the pipe and return the output new type in form of new type class
	 * 
	 * @param in
	 * @return
	 * @throws TransformException
	 */

	public LinkedList<O> processTransform(I in) throws TransformException {
		LinkedList<O> transformeds = new LinkedList<O>();
		for (IPipe<I, O> pipe : this) {
			O out = pipe.processTransform(in);
			transformeds.add(out);
		}
		return transformeds;
	}

}
