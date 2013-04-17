/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm;

import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXmlDefinition2_2;
import org.eclipse.jpt.jpa.ui.ResourceUiDefinition;

public class EclipseLinkOrmXmlUiDefinition2_2
	extends EclipseLinkOrmXml2_1UiDefinition
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
