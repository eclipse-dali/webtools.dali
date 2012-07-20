/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXml2_0Definition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXml2_1Definition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXml2_2Definition;
import org.eclipse.jpt.jpa.ui.details.JpaDetailsPageManager;
import org.eclipse.jpt.jpa.ui.details.JpaDetailsProvider;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractEntityMappingsDetailsProvider;
import org.eclipse.swt.widgets.Composite;

/**
 * This provider is responsible for creating the {@link JpaDetailsPageManager}
 * when the information comes from the XML file (either from the persistence
 * configuration or from the Mappings Descriptor).
 *
 * @version 3.1
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
	protected boolean providesDetails(JptResourceType resourceType) {
		return resourceType.equals(EclipseLinkOrmXml2_0Definition.instance().getResourceType()) ||
			resourceType.equals(EclipseLinkOrmXml2_1Definition.instance().getResourceType()) ||
			resourceType.equals(EclipseLinkOrmXml2_2Definition.instance().getResourceType());
	}
	
	public JpaDetailsPageManager<? extends JpaStructureNode> buildDetailsPageManager(
		Composite parent,
		WidgetFactory widgetFactory) {

		return new EclipseLinkEntityMappings2_0DetailsPage(parent, widgetFactory);
	}
}
