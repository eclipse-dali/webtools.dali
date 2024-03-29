/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.bundleexclude;

import java.io.InputStream;
import java.io.Reader;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.content.IContentDescription;
import org.eclipse.core.runtime.content.ITextContentDescriber;

/**
 * This describer returns {@link #INDETERMINATE} for any content.
 * <p>
 * <strong>NB:</strong>
 * This class is in a package that is excluded from bundle
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
