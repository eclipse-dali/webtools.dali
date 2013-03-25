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

import java.io.Serializable;
import org.eclipse.jpt.common.utility.internal.ClassTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * Transform a {@link Class} into an instance by calling
 * {@link Class#newInstance()}. Checked exceptions are converted to
 * {@link RuntimeException}s.
 * 
 * @param <O> output: the type of the object returned by the transformer (and
 *   the class, or superclass of the class, passed to the transformer)
 * 
 * @see Class#newInstance()
 */
public final class InstantiationTransformer<O>
	implements Transformer<Class<? extends O>, O>, Serializable
{
	@SuppressWarnings("rawtypes")
	public static final Transformer INSTANCE = new InstantiationTransformer();

	@SuppressWarnings("unchecked")
	public static <O> Transformer<Class<? extends O>, O> instance() {
		return INSTANCE;
	}

	// ensure single instance
	private InstantiationTransformer() {
		super();
	}

	public O transform(Class<? extends O> input) {
		return ClassTools.newInstance(input);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

	private static final long serialVersionUID = 1L;
	private Object readResolve() {
		// replace this object with the singleton
		return INSTANCE;
	}
}
