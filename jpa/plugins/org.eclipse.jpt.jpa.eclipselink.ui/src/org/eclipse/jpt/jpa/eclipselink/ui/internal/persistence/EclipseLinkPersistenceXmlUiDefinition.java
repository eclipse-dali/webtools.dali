/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence;

import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.structure.EclipseLinkPersistenceResourceModelStructureProvider;
import org.eclipse.jpt.jpa.ui.ResourceUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.persistence.details.AbstractPersistenceXmlResourceUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.persistence.details.PersistenceXmlUiFactory;
import org.eclipse.jpt.jpa.ui.structure.JpaStructureProvider;

public class EclipseLinkPersistenceXmlUiDefinition extends AbstractPersistenceXmlResourceUiDefinition
{
	// singleton
	private static final ResourceUiDefinition INSTANCE = new EclipseLinkPersistenceXmlUiDefinition();
	
	
	/**
	 * Return the singleton
	 */
	public static ResourceUiDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private EclipseLinkPersistenceXmlUiDefinition() {
		super();
	}
	
	
	@Override
	protected PersistenceXmlUiFactory buildPersistenceXmlUiFactory() {
		return new EclipseLinkPersistenceXmlUiFactory();
	}
	
	public boolean providesUi(JptResourceType resourceType) {
		return resourceType.equals(JptJpaCorePlugin.PERSISTENCE_XML_1_0_RESOURCE_TYPE);
	}
	
	public JpaStructureProvider getStructureProvider() {
		return EclipseLinkPersistenceResourceModelStructureProvider.instance();
	}
}
