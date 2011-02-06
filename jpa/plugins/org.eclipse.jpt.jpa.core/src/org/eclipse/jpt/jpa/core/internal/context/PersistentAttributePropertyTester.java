/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;

public class PersistentAttributePropertyTester
	extends PropertyTester 
{
	public static final String IS_MAPPED = "isMapped"; //$NON-NLS-1$

	public static final String IS_VIRTUAL = "isVirtual"; //$NON-NLS-1$


	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (property == null) {
			return false;
		}
		if ( ! (expectedValue instanceof Boolean)) {
			return false;
		}
		return this.test_(receiver, property, ((Boolean) expectedValue).booleanValue());
	}

	/**
	 * pre-condition: property is not <code>null</code>
	 */
	protected boolean test_(Object receiver, String property, boolean expected) {
		if (property.equals(IS_MAPPED)) {
			boolean actual = ((ReadOnlyPersistentAttribute) receiver).getMapping().getKey() != null;
			return actual == expected;
		}
		if (property.equals(IS_VIRTUAL)) {
			boolean actual = ((ReadOnlyPersistentAttribute) receiver).isVirtual();
			return actual == expected;
		}
		return false;
	}
}
