/*******************************************************************************
 * Copyright (c) 2008, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal;

import org.eclipse.jpt.common.ui.JptCommonUiMessages;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.osgi.util.NLS;

/**
 * Transform a {@link Boolean} value into a UI string.
 * If the value is non-<code>null</code>, bind it to the supplied string.
 * If the value is <code>null</code>, convert it into the supplied null string,
 * which can, itself, be <code>null</code>.
 */
public class BooleanStringTransformer
	extends TransformerAdapter<Boolean, String>
{
	private final String string;
	private final String nullString;

	public BooleanStringTransformer(String string, String nullString) {
		super();
		if (string == null) {
			throw new NullPointerException();
		}
		this.string = string;
		this.nullString = nullString;
	}

	@Override
	public String transform(Boolean b) {
		return (b == null) ? this.nullString : this.transform(b.booleanValue());
	}

	private String transform(boolean b) {
		String defaultStringValue = b ? JptCommonUiMessages.BOOLEAN_TRUE : JptCommonUiMessages.BOOLEAN_FALSE;
		return NLS.bind(this.string, defaultStringValue);
	}
}
