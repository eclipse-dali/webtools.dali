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

/**
 * Convenience transformer that returns <code>null</code> if the original
 * object is <code>null</code>; otherwise it calls {@link #transform_(Object)},
 * which is to be implemented by subclasses.
 */
public abstract class AbstractTransformer<T1, T2>
	extends TransformerAdapter<T1, T2>
{
	@Override
	public final T2 transform(T1 o) {
		return (o == null) ? null : this.transform_(o);
	}

	/**
	 * Transform the specified object; its value is guaranteed to be not
	 * <code>null</code>.
	 */
	protected abstract T2 transform_(T1 o);
}
