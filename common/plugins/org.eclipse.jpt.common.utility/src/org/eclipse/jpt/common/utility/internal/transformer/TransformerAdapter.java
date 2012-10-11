/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.transformer;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * Convenience transformer that returns <code>null</code> for every
 * transformation.
 */
public class TransformerAdapter<T1, T2>
	implements Transformer<T1, T2>
{
	public T2 transform(T1 o) {
		return null;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}
