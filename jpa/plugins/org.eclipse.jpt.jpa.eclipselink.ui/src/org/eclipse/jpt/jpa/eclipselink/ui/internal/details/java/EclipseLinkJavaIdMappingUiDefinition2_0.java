/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.java;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.ui.details.MappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.IdMappingUiDefinition;

public class EclipseLinkJavaIdMappingUiDefinition2_0
	extends IdMappingUiDefinition
{
	// singleton
	private static final EclipseLinkJavaIdMappingUiDefinition2_0 INSTANCE = 
			new EclipseLinkJavaIdMappingUiDefinition2_0();
	
	
	/**
	 * Return the singleton
	 */
	public static MappingUiDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private EclipseLinkJavaIdMappingUiDefinition2_0() {
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
