/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
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

/**
 * Property tester for {@link ReadOnlyPersistentAttribute}.
 * See <code>org.eclipse.jpt.jpa.core/plugin.xml</code>
 */
public class PersistentAttributePropertyTester
	extends PropertyTester 
{
	public static final String IS_MAPPED = "isMapped"; //$NON-NLS-1$
	public static final String IS_NOT_MAPPED = "isNotMapped"; //$NON-NLS-1$
	public static final String IS_VIRTUAL = "isVirtual"; //$NON-NLS-1$
	public static final String IS_NOT_VIRTUAL = "isNotVirtual"; //$NON-NLS-1$


	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (receiver instanceof ReadOnlyPersistentAttribute) {
			return this.test((ReadOnlyPersistentAttribute) receiver, property, expectedValue);
		}
		return false;
	}

	protected boolean test(ReadOnlyPersistentAttribute attribute, String property, Object expectedValue) {
		if (property.equals(IS_NOT_MAPPED)) {
			return ! this.test(attribute, IS_MAPPED, expectedValue);
		}
		if (property.equals(IS_MAPPED)) {
			boolean expected = (expectedValue == null) ? true : ((Boolean) expectedValue).booleanValue();
			boolean actual = (attribute.getMapping().getKey() != null);
			return actual == expected;
		}

		if (property.equals(IS_NOT_VIRTUAL)) {
			return ! this.test(attribute, IS_VIRTUAL, expectedValue);
		}
		if (property.equals(IS_VIRTUAL)) {
			boolean expected = (expectedValue == null) ? true : ((Boolean) expectedValue).booleanValue();
			boolean actual = attribute.isVirtual();
			return actual == expected;
		}

		return false;
	}
}
