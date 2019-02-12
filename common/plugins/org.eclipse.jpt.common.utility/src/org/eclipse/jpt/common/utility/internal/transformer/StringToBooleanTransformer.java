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
 * Transform a {@link String} into a {@link Boolean} (i.e. transform
 * a string equal to <code>"true"</code> (ignoring case) into
 * {@link Boolean#TRUE}; transform all other
 * strings into {@link Boolean#FALSE}).
 * @see Boolean#valueOf(String)
 */
public final class StringToBooleanTransformer
	implements Transformer<String, Boolean>, Serializable
{
	public static final Transformer<String, Boolean> INSTANCE = new StringToBooleanTransformer();

	public static Transformer<String, Boolean> instance() {
		return INSTANCE;
	}

	// ensure single instance
	private StringToBooleanTransformer() {
		super();
	}

	/**
	 * @see Boolean#valueOf(String)
	 */
	public Boolean transform(String string) {
		return Boolean.valueOf(string);
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
