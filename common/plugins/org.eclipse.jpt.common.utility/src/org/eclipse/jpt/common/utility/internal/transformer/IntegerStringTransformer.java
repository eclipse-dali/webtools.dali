/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.transformer;

import java.io.Serializable;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * Transform a {@link String} into an {@link Integer} if possible.
 * Transform a <code>null</code> string into a <code>null</code> {@link Integer}.
 * @see Integer#valueOf(String)
 */
public final class IntegerStringTransformer
	extends AbstractTransformer<String, Integer>
	implements Serializable
{
	public static final Transformer<String, Integer> INSTANCE = new IntegerStringTransformer();

	public static Transformer<String, Integer> instance() {
		return INSTANCE;
	}

	// ensure single instance
	private IntegerStringTransformer() {
		super();
	}

	/**
	 * @see Integer#valueOf(String)
	 */
	@Override
	protected Integer transform_(String string) {
		return Integer.valueOf(string);
	}

	private static final long serialVersionUID = 1L;
	private Object readResolve() {
		// replace this object with the singleton
		return INSTANCE;
	}
}
