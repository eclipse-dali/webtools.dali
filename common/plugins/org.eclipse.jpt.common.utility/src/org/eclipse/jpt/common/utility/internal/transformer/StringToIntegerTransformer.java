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
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * Transform a {@link String} into an {@link Integer} if possible.
 * Transform a <code>null</code> string into a client-configured
 * {@link Integer}.
 * @see Integer#valueOf(String)
 */
public class StringToIntegerTransformer
	implements Transformer<String, Integer>, Serializable
{
	private final Integer nullInteger;

	private static final long serialVersionUID = 1L;

	public StringToIntegerTransformer(Integer nullInteger) {
		super();
		this.nullInteger = nullInteger;
	}

	/**
	 * @see Integer#valueOf(String)
	 */
	public Integer transform(String string) {
		return (string == null) ? this.nullInteger : Integer.valueOf(string);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this. nullInteger);
	}
}
