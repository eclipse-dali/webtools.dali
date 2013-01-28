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
 * Transform a {@link String} into a {@link Boolean} (i.e. transform
 * a string equal to <code>"true"</code> (ignoring case) into
 * {@link Boolean#TRUE}; transform all other non-<code>null</code>
 * strings into {@link Boolean#FALSE}).
 * Transform a <code>null</code> string into a <code>null</code> {@link Boolean}.
 * @see Boolean#valueOf(String)
 */
public final class BooleanStringTransformer
	extends AbstractTransformer<String, Boolean>
	implements Serializable
{
	public static final Transformer<String, Boolean> INSTANCE = new BooleanStringTransformer();

	public static Transformer<String, Boolean> instance() {
		return INSTANCE;
	}

	// ensure single instance
	private BooleanStringTransformer() {
		super();
	}

	/**
	 * @see Boolean#valueOf(String)
	 */
	@Override
	protected Boolean transform_(String string) {
		return Boolean.valueOf(string);
	}

	private static final long serialVersionUID = 1L;
	private Object readResolve() {
		// replace this object with the singleton
		return INSTANCE;
	}
}
