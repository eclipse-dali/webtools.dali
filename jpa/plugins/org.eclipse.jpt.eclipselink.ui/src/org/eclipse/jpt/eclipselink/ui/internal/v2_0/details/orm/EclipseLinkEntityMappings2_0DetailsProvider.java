/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.v2_0.details.orm;

import org.eclipse.jpt.core.JpaResourceType;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.eclipselink.core.internal.JptEclipseLinkCorePlugin;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaDetailsPage;
import org.eclipse.jpt.ui.details.JpaDetailsProvider;
import org.eclipse.jpt.ui.internal.details.AbstractEntityMappingsDetailsProvider;
import org.eclipse.swt.widgets.Composite;

/**
 * This provider is responsible for creating the {@link JpaDetailsPage}
 * when the information comes from the XML file (either from the persistence
 * configuration or from the Mappings Descriptor).
 *
 * @version 2.2
 * @since 2.2
 */
public class EclipseLinkEntityMappings2_0DetailsProvider
	extends AbstractEntityMappingsDetailsProvider
{
	// singleton
	private static final JpaDetailsProvider INSTANCE = new EclipseLinkEntityMappings2_0DetailsProvider();
	
	
	/**
	 * Return the singleton
	 */
	public static JpaDetailsProvider instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private EclipseLinkEntityMappings2_0DetailsProvider() {
		super();
	}
	
	
	@Override
	protected boolean providesDetails(JpaResourceType resourceType) {
		return resourceType.equals(JptEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_2_0_RESOURCE_TYPE) ||
			resourceType.equals(JptEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_2_1_RESOURCE_TYPE) ||
			resourceType.equals(JptEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_2_2_RESOURCE_TYPE);
	}
	
	public JpaDetailsPage<? extends JpaStructureNode> buildDetailsPage(
		Composite parent,
		WidgetFactory widgetFactory) {

		return new EclipseLinkEntityMappings2_0DetailsPage(parent, widgetFactory);
	}
}
