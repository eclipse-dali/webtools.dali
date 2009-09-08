/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.details.orm;

import org.eclipse.core.runtime.content.IContentType;
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
public class EclipseLinkEntityMappingsDetailsProvider
	extends AbstractEntityMappingsDetailsProvider
{
	// singleton
	private static final JpaDetailsProvider INSTANCE = new EclipseLinkEntityMappingsDetailsProvider();

	/**
	 * Return the singleton.
	 */
	public static JpaDetailsProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private EclipseLinkEntityMappingsDetailsProvider() {
		super();
	}
	
	public IContentType getContentType() {
		return JptEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_CONTENT_TYPE;
	}
	
	public JpaDetailsPage<? extends JpaStructureNode> buildDetailsPage(
		Composite parent,
		WidgetFactory widgetFactory) {

		return new EclipseLinkEntityMappingsDetailsPage(parent, widgetFactory);
	}

}
