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
 * Transform any object, except <code>null</code>, into a single
 * client-specified object. Any <code>null</code> object will be
 * transformed into <code>null</code>.
 * 
 * @param <T1> the type of the object passed to the transformer
 * @param <T2> the type of the object returned by the transformer
 */
public class StaticTransformer<T1, T2>
	extends AbstractTransformer<T1, T2>
{
	private final T2 object;

	public StaticTransformer() {
		this(null);
	}

	public StaticTransformer(T2 object) {
		super();
		this.object = object;
	}

	@Override
	protected T2 transform_(T1 o) {
		return this.object;
	}
}
