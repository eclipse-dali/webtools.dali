/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm;

import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXmlDefinition2_2;
import org.eclipse.jpt.jpa.ui.ResourceUiDefinition;

public class EclipseLinkOrmXmlUiDefinition2_2
	extends EclipseLinkOrmXmlUiDefinition2_1
{
	// singleton
	private static final ResourceUiDefinition INSTANCE = new EclipseLinkOrmXmlUiDefinition2_2();
	
	
	/**
	 * Return the singleton
	 */
	public static ResourceUiDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private EclipseLinkOrmXmlUiDefinition2_2() {
		super();
	}
	
	
	@Override
	public boolean providesUi(JptResourceType resourceType) {
		return resourceType.equals(EclipseLinkOrmXmlDefinition2_2.instance().getResourceType());
	}
}
