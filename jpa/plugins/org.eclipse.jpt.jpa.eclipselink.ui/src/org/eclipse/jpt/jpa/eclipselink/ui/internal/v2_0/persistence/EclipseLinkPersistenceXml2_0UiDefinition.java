/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.v2_0.persistence;

import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.ui.jface.ItemTreeStateProviderFactoryProvider;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.persistence.GenericPersistenceXml2_0Definition;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.EclipseLinkPersistenceXmlUiDefinition;
import org.eclipse.jpt.jpa.ui.ResourceUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.persistence.AbstractPersistenceXmlResourceUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.persistence.PersistenceXmlUiFactory;

public class EclipseLinkPersistenceXml2_0UiDefinition
	extends AbstractPersistenceXmlResourceUiDefinition
{
	// singleton
	private static final ResourceUiDefinition INSTANCE = new EclipseLinkPersistenceXml2_0UiDefinition();

	/**
	 * Return the singleton
	 */
	public static ResourceUiDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private EclipseLinkPersistenceXml2_0UiDefinition() {
		super();
	}
	
	@Override
	protected PersistenceXmlUiFactory buildPersistenceXmlUiFactory() {
		return new EclipseLink2_0PersistenceXmlUiFactory();
	}
	
	public boolean providesUi(JptResourceType resourceType) {
		return resourceType.equals(GenericPersistenceXml2_0Definition.instance().getResourceType());
	}
	
	public ItemTreeStateProviderFactoryProvider getStructureViewFactoryProvider() {
		return EclipseLinkPersistenceXmlUiDefinition.STRUCTURE_VIEW_FACTORY_PROVIDER;
	}
}
