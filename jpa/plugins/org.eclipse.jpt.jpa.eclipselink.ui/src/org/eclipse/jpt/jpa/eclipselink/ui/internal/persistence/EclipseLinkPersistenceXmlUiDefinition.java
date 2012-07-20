/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence;

import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.ui.internal.jface.SimpleItemTreeStateProviderFactoryProvider;
import org.eclipse.jpt.common.ui.jface.ItemTreeStateProviderFactoryProvider;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.persistence.GenericPersistenceXmlDefinition;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.structure.EclipseLinkPersistenceStructureItemContentProviderFactory;
import org.eclipse.jpt.jpa.ui.ResourceUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.persistence.AbstractPersistenceXmlResourceUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.persistence.PersistenceXmlUiFactory;
import org.eclipse.jpt.jpa.ui.internal.structure.PersistenceStructureItemLabelProviderFactory;

public class EclipseLinkPersistenceXmlUiDefinition
	extends AbstractPersistenceXmlResourceUiDefinition
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
		return resourceType.equals(GenericPersistenceXmlDefinition.instance().getResourceType());
	}
	
	public ItemTreeStateProviderFactoryProvider getStructureViewFactoryProvider() {
		return STRUCTURE_VIEW_FACTORY_PROVIDER;
	}
	
	public static final ItemTreeStateProviderFactoryProvider STRUCTURE_VIEW_FACTORY_PROVIDER =
			new SimpleItemTreeStateProviderFactoryProvider(
					EclipseLinkPersistenceStructureItemContentProviderFactory.instance(),
					PersistenceStructureItemLabelProviderFactory.instance()
				);
}
