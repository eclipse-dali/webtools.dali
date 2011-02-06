/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm;

import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.eclipselink.core.JptJpaEclipseLinkCorePlugin;
import org.eclipse.jpt.jpa.ui.details.JpaDetailsPage;
import org.eclipse.jpt.jpa.ui.details.JpaDetailsProvider;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractEntityMappingsDetailsProvider;
import org.eclipse.swt.widgets.Composite;

/**
 * This provider is responsible for creating the {@link JpaDetailsPage}
 * when the information comes from the XML file (either from the persistence
 * configuration or from the Mappings Descriptor).
 *
 * @version 2.2
 * @since 2.2
 */
public class EclipseLinkEntityMappingsDetailsProvider
	extends AbstractEntityMappingsDetailsProvider
{
	// singleton
	private static final JpaDetailsProvider INSTANCE = new EclipseLinkEntityMappingsDetailsProvider();
	
	
	/**
	 * Return the singleton
	 */
	public static JpaDetailsProvider instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private EclipseLinkEntityMappingsDetailsProvider() {
		super();
	}
	
	
	@Override
	protected boolean providesDetails(JptResourceType resourceType) {
		return resourceType.equals(JptJpaEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_1_0_RESOURCE_TYPE)
				|| resourceType.equals(JptJpaEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_1_1_RESOURCE_TYPE)
				|| resourceType.equals(JptJpaEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_1_2_RESOURCE_TYPE);
	}
	
	public JpaDetailsPage<? extends JpaStructureNode> buildDetailsPage(
			Composite parent,
			WidgetFactory widgetFactory) {
		
		return new EclipseLinkEntityMappingsDetailsPage(parent, widgetFactory);
	}
}
