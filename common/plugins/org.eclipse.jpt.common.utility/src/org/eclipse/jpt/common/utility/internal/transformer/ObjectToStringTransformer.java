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
 * Transform an object into the string returned by its {@link Object#toString()}
 * method. A <code>null</code> is transformed into a client-configured
 * non-<code>null</code> string.
 * 
 * @param <I> input: the type of the object passed to the transformer
 */
public class ObjectToStringTransformer<I>
	implements Transformer<I, String>, Serializable
{
	private final String nullString;

	private static final long serialVersionUID = 1L;


	public ObjectToStringTransformer(String nullString) {
		super();
		this.nullString = nullString;
	}

	public String transform(I input) {
		return (input == null) ? this.nullString : input.toString();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.nullString);
	}
}
