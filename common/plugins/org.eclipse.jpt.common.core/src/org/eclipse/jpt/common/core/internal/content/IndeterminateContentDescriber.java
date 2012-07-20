/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.content;

import java.io.InputStream;
import java.io.Reader;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.content.IContentDescription;
import org.eclipse.core.runtime.content.ITextContentDescriber;

/**
 * This describer returns {@link #INDETERMINATE} for any content.
 * <p>
 * This is in its own package so it can be excluded from bundle
 * activation in the <code>META-INF/MANIFEST.MF</code> file.
 * To be excluded, content describers must be self-contained and
 * not trigger bundle auto-activation.
 */
public class IndeterminateContentDescriber
	implements ITextContentDescriber
{
	public int describe(InputStream contents, IContentDescription description) {
		return INDETERMINATE;
	}

	public int describe(Reader contents, IContentDescription description) {
		return INDETERMINATE;
	}

	public QualifiedName[] getSupportedOptions() {
		return EMPTY_QUALIFIED_NAME_ARRAY;
	}
	private static final QualifiedName[] EMPTY_QUALIFIED_NAME_ARRAY = new QualifiedName[0];
}
