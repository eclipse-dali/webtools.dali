/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;

/**
 * Property tester for {@link PersistentAttribute}.
 * See <code>org.eclipse.jpt.jpa.core/plugin.xml:org.eclipse.core.expressions.propertyTesters</code>
 */
public class PersistentAttributePropertyTester
	extends PropertyTester 
{
	public static final String IS_MAPPED = "isMapped"; //$NON-NLS-1$
	public static final String IS_NOT_MAPPED = "isNotMapped"; //$NON-NLS-1$
	public static final String IS_VIRTUAL = "isVirtual"; //$NON-NLS-1$
	public static final String IS_NOT_VIRTUAL = "isNotVirtual"; //$NON-NLS-1$


	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (receiver instanceof PersistentAttribute) {
			return this.test((PersistentAttribute) receiver, property, expectedValue);
		}
		return false;
	}

	protected boolean test(PersistentAttribute attribute, String property, Object expectedValue) {
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
