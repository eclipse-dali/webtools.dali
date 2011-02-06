/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.v2_0.details.java;

import org.eclipse.jpt.common.utility.internal.Tools;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaIdMapping;
import org.eclipse.jpt.jpa.ui.details.java.JavaAttributeMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.java.JavaIdMappingUiDefinition;

public class JavaEclipseLinkIdMapping2_0UiDefinition
	extends JavaIdMappingUiDefinition
{
	// singleton
	private static final JavaEclipseLinkIdMapping2_0UiDefinition INSTANCE = 
			new JavaEclipseLinkIdMapping2_0UiDefinition();
	
	
	/**
	 * Return the singleton
	 */
	public static JavaAttributeMappingUiDefinition<JavaIdMapping> instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private JavaEclipseLinkIdMapping2_0UiDefinition() {
		super();
	}
	
	
	@Override
	public boolean isEnabledFor(ReadOnlyPersistentAttribute mappableObject) {
		// a default eclipselink 1-1 mapping can not be mapped as an id, as mapping it
		// that way will actually result in a default 1-1 with an id derived identity
		return Tools.valuesAreDifferent(
				mappableObject.getDefaultMappingKey(), 
				MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
	}
}
