/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.reference;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.reference.ObjectReference;

/**
 * Implement some of the methods in {@link ObjectReference} that can
 * be defined in terms of the other methods.
 * Subclasses need only implement<ul>
 * <li>{@link #getValue()}
 * </ul>
 * 
 * @param <V> the type of the reference's value
 */
public abstract class AbstractObjectReference<V>
	implements ObjectReference<V>
{
	protected AbstractObjectReference() {
		super();
	}

	public boolean valueEquals(Object object) {
		return ObjectTools.equals(this.getValue(), object);
	}

	public boolean valueNotEqual(Object object) {
		return ObjectTools.notEquals(this.getValue(), object);
	}

	public boolean isNull() {
		return this.getValue() == null;
	}

	public boolean isNotNull() {
		return this.getValue() != null;
	}

	@Override
	public String toString() {
		return '[' + String.valueOf(this.getValue()) + ']';
	}
}
