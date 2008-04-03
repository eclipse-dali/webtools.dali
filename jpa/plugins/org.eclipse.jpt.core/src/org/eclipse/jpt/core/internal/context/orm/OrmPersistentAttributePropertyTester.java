/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.internal.context.PersistentAttributePropertyTester;

public class OrmPersistentAttributePropertyTester extends PersistentAttributePropertyTester
{
	public static final String IS_VIRTUAL = "isVirtual";
	
	
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (! IS_VIRTUAL.equals(property)) {
			return super.test(receiver, property, args, expectedValue);
		}
		
		Boolean booleanValue;
		
		try {
			booleanValue = (Boolean) expectedValue;
		}
		catch (ClassCastException cce) {
			return false;
		}
		
		return ((OrmPersistentAttribute) receiver).isVirtual() == booleanValue;
	}
}
