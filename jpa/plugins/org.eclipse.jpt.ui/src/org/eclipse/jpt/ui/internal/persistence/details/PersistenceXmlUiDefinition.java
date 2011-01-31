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
package org.eclipse.jpt.ui.internal.persistence.details;

import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.ui.ResourceUiDefinition;
import org.eclipse.jpt.ui.internal.structure.PersistenceResourceModelStructureProvider;
import org.eclipse.jpt.ui.structure.JpaStructureProvider;

public class PersistenceXmlUiDefinition extends AbstractPersistenceXmlResourceUiDefinition
{
	// singleton
	private static final ResourceUiDefinition INSTANCE = new PersistenceXmlUiDefinition();
	
	
	/**
	 * Return the singleton
	 */
	public static ResourceUiDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private PersistenceXmlUiDefinition() {
		super();
	}
	
	
	@Override
	protected PersistenceXmlUiFactory buildPersistenceXmlUiFactory() {
		return new GenericPersistenceXmlUiFactory();
	}
	
	public boolean providesUi(JptResourceType resourceType) {
		return resourceType.equals(JptCorePlugin.PERSISTENCE_XML_1_0_RESOURCE_TYPE);
	}
	
	public JpaStructureProvider getStructureProvider() {
		return PersistenceResourceModelStructureProvider.instance();
	}
}
