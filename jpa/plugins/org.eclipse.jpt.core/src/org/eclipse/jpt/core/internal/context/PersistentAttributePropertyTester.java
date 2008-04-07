/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;

public class PersistentAttributePropertyTester extends PropertyTester 
{
	public static final String IS_MAPPED = "isMapped";
	
	public static final String IS_VIRTUAL = "isVirtual";
	
	
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (IS_MAPPED.equals(property)) {
			Boolean expectedIsMapped;
			
			try {
				expectedIsMapped = (Boolean) expectedValue;
			}
			catch (ClassCastException cce) {
				return false;
			}
			
			Boolean actualIsMapped = ((PersistentAttribute) receiver).getMappingKey() != MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY;
			
			return actualIsMapped == expectedIsMapped;
		}
		else if (IS_VIRTUAL.equals(property)) {
			Boolean expectedIsVirtual;
			
			try {
				expectedIsVirtual = (Boolean) expectedValue;
			}
			catch (ClassCastException cce) {
				return false;
			}
			
			Boolean actualIsVirtual = false;
			if (receiver instanceof OrmPersistentAttribute) {
				actualIsVirtual = ((OrmPersistentAttribute) receiver).isVirtual();
			}
			
			return actualIsVirtual == expectedIsVirtual;
		}
		
		return false;
	}
}
