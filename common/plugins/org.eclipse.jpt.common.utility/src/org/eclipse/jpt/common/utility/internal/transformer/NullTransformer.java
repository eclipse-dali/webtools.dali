/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.transformer;

import java.io.Serializable;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * A transformer that will transform every input into <code>null</code>.
 */
public final class NullTransformer<I, O>
	implements Transformer<I, O>, Serializable
{
	@SuppressWarnings("rawtypes")
	public static final Transformer INSTANCE = new NullTransformer();

	@SuppressWarnings("unchecked")
	public static <R1, R2> Transformer<R1, R2> instance() {
		return INSTANCE;
	}

	// ensure single instance
	private NullTransformer() {
		super();
	}

	// simply return null
	public O transform(I input) {
		return null;
	}

	@Override
	public String toString() {
		return ObjectTools.singletonToString(this);
	}

	private static final long serialVersionUID = 1L;
	private Object readResolve() {
		// replace this object with the singleton
		return INSTANCE;
	}
}
