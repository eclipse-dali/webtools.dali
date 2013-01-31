/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.filter;

/**
 * This predicate will return <code>true</code> for any object that is
 * non-<code>null</code> and an instance of the specified class.
 */
public class InstanceOfFilter<T>
	extends SimpleFilter<T, Class<? extends T>>
{
	private final Class<?> clazz;
	private static final long serialVersionUID = 1L;

	public InstanceOfFilter(Class<?> clazz){
		super();
		this.clazz = clazz;
	}

	@Override
	public boolean accept(T o) {
		return this.clazz.isInstance(o);
	}
}
