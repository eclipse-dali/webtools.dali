/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm;

import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXml2_2Definition;
import org.eclipse.jpt.jpa.ui.ResourceUiDefinition;

public class EclipseLinkOrmXml2_2UiDefinition
	extends EclipseLinkOrmXml2_1UiDefinition
{
	// singleton
	private static final ResourceUiDefinition INSTANCE = new EclipseLinkOrmXml2_2UiDefinition();
	
	
	/**
	 * Return the singleton
	 */
	public static ResourceUiDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private EclipseLinkOrmXml2_2UiDefinition() {
		super();
	}
	
	
	@Override
	public boolean providesUi(JptResourceType resourceType) {
		return resourceType.equals(EclipseLinkOrmXml2_2Definition.instance().getResourceType());
	}
}
