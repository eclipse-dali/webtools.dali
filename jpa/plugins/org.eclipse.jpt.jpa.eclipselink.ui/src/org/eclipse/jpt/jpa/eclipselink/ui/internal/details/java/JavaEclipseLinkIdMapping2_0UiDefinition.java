/*******************************************************************************
 *  Copyright (c) 2010, 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.java;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.ui.details.MappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.IdMappingUiDefinition;

public class JavaEclipseLinkIdMapping2_0UiDefinition
	extends IdMappingUiDefinition
{
	// singleton
	private static final JavaEclipseLinkIdMapping2_0UiDefinition INSTANCE = 
			new JavaEclipseLinkIdMapping2_0UiDefinition();
	
	
	/**
	 * Return the singleton
	 */
	public static MappingUiDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private JavaEclipseLinkIdMapping2_0UiDefinition() {
		super();
	}
	
	
	@Override
	public boolean isEnabledFor(JpaContextModel node) {
		// a default eclipselink 1-1 mapping can not be mapped as an id, as mapping it
		// that way will actually result in a default 1-1 with an id derived identity
		return ObjectTools.notEquals(
				((PersistentAttribute) node).getDefaultMappingKey(), 
				MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
	}
}
