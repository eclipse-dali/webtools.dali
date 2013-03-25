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
 * Transform a <code>null</code> string into a client-configured
 * {@link Boolean}.
 * @see Boolean#valueOf(String)
 */
public class StringToBooleanTransformer
	implements Transformer<String, Boolean>, Serializable
{
	private final Boolean nullBoolean;

	private static final long serialVersionUID = 1L;


	public StringToBooleanTransformer(Boolean nullBoolean) {
		super();
		this.nullBoolean = nullBoolean;
	}

	/**
	 * @see Boolean#valueOf(String)
	 */
	public Boolean transform(String string) {
		return (string == null) ? this.nullBoolean : Boolean.valueOf(string);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
