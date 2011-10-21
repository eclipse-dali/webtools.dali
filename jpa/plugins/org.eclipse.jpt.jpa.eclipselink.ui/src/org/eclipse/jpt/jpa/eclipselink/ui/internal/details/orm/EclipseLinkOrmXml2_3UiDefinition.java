/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm;

import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.jpa.eclipselink.core.JptJpaEclipseLinkCorePlugin;
import org.eclipse.jpt.jpa.ui.ResourceUiDefinition;
import org.eclipse.jpt.jpa.ui.details.orm.OrmXmlUiFactory;


public class EclipseLinkOrmXml2_3UiDefinition
		extends EclipseLinkOrmXml2_1UiDefinition {
	
	// singleton
	private static final ResourceUiDefinition INSTANCE = new EclipseLinkOrmXml2_3UiDefinition();


	/**
	 * Return the singleton
	 */
	public static ResourceUiDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private EclipseLinkOrmXml2_3UiDefinition() {
		super();
	}

	@Override
	protected OrmXmlUiFactory buildOrmXmlUiFactory() {
		return new EclipseLinkOrmXml2_3UiFactory();
	}

	@Override
	public boolean providesUi(JptResourceType resourceType) {
		return resourceType.equals(JptJpaEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_2_3_RESOURCE_TYPE);
	}
}