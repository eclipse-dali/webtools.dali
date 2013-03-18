/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.transformer;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * A transformer that uses Java reflection to transform an object into the
 * value returned of one of its zero-argument methods.
 * 
 * @param <I> input: the type of objects passed to the transformer
 * @param <O> output: the type of objects returned by the transformer
 * 
 * @see FieldTransformer
 */
public class MethodTransformer<I, O>
	implements Transformer<I, O>
{
	private final String methodName;

	public MethodTransformer(String methodName) {
		super();
		this.methodName = methodName;
	}

	@SuppressWarnings("unchecked")
	public O transform(I input) {
		return (O) ObjectTools.execute(input, this.methodName);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.methodName);
	}
}
