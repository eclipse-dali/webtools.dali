/*******************************************************************************
* Copyright (c) 2009, 2012 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.details.orm;

import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.ui.details.JpaDetailsPageManager;
import org.eclipse.jpt.jpa.ui.details.JpaDetailsProvider;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractEntityMappingsDetailsProvider;
import org.eclipse.swt.widgets.Composite;

/**
 *  EntityMappings2_0DetailsProvider
 */
public class EntityMappings2_0DetailsProvider
	extends AbstractEntityMappingsDetailsProvider
{
	// singleton
	private static final JpaDetailsProvider INSTANCE = new EntityMappings2_0DetailsProvider();
	
	
	/**
	 * Return the singleton
	 */
	public static JpaDetailsProvider instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private EntityMappings2_0DetailsProvider() {
		super();
	}
	
	
	@Override
	protected boolean providesDetails(JptResourceType resourceType) {
		return resourceType.equals(JptJpaCorePlugin.ORM_XML_2_0_RESOURCE_TYPE);
	}
	
	public JpaDetailsPageManager<EntityMappings> buildDetailsPageManager(
			Composite parent,
			WidgetFactory widgetFactory) {
		
		return new EntityMappings2_0DetailsPage(parent, widgetFactory);
	}
}
