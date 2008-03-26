/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.actions;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.ui.IActionFilter;

public class PersistentAttributeActionFilter 
	implements IActionFilter
{
	public static final String IS_MAPPED = "isMapped";
	
	
	public boolean testAttribute(Object target, String name, String value) {
		if (! IS_MAPPED.equals(name)) {
			return false;
		}
		
		Boolean booleanValue;
		if ("true".equals(value)) {
			booleanValue = true;
		}
		else if ("false".equals(value)) {
			booleanValue = false;
		}
		else {
			return false;
		}
		
		boolean mapped = ((PersistentAttribute) target).getMappingKey() != MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY;
		return mapped == booleanValue;
	}
	
	
	public static final class Factory
		implements IAdapterFactory
	{
		private static final Class[] ADAPTER_TYPES = { IActionFilter.class };
		
		public Object getAdapter(final Object adaptable, final Class adapterType ) {
			if( adapterType == IActionFilter.class ) {
				return new PersistentAttributeActionFilter();
			}
			return null;
		}
	    
		public Class[] getAdapterList() {
			return ADAPTER_TYPES;
		}
	}
}
