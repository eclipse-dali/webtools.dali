/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.transformer;

import java.io.Serializable;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * Transform a {@link String} into an {@link Integer} if possible.
 * @see Integer#valueOf(String)
 */
public final class StringToIntegerTransformer
	implements Transformer<String, Integer>, Serializable
{
	public static final Transformer<String, Integer> INSTANCE = new StringToIntegerTransformer();

	public static Transformer<String, Integer> instance() {
		return INSTANCE;
	}

	// ensure single instance
	private StringToIntegerTransformer() {
		super();
	}

	/**
	 * @see Integer#valueOf(String)
	 */
	public Integer transform(String string) {
		return Integer.valueOf(string);
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
