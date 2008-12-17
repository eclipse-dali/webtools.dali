/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.orm.details;

import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.orm.OrmStructureNode;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmResource;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaDetailsPage;
import org.eclipse.jpt.ui.details.JpaDetailsProvider;
import org.eclipse.swt.widgets.Composite;

/**
 * This provider is responsible for creating the {@link JpaDetailsPage}
 * when the information comes from the XML file (either from the persistence
 * configuration or from the Mappings Descriptor).
 *
 * @version 2.1
 * @since 2.1
 */
public class EclipseLinkOrmDetailsProvider
	implements JpaDetailsProvider
{
	// singleton
	private static final JpaDetailsProvider INSTANCE = new EclipseLinkOrmDetailsProvider();

	/**
	 * Return the singleton.
	 */
	public static JpaDetailsProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private EclipseLinkOrmDetailsProvider() {
		super();
	}

	public JpaDetailsPage<? extends JpaStructureNode> buildDetailsPage(
		Composite parent,
		JpaStructureNode structureNode,
		WidgetFactory widgetFactory) {

		if (structureNode.getId() == OrmStructureNode.ENTITY_MAPPINGS_ID) {
			//TODO JpaPlatformUi really needs a complete overhaul, this is not a good solution
			if (((EntityMappings) structureNode).getOrmType() == EclipseLinkOrmResource.TYPE) {
				return new EntityMappingsDetailsPage(parent, widgetFactory);
			}
			return new org.eclipse.jpt.ui.internal.orm.details.EntityMappingsDetailsPage(parent, widgetFactory);
		}

		if (structureNode.getId() == OrmStructureNode.PERSISTENT_TYPE_ID) {
			return new EclipseLinkOrmPersistentTypeDetailsPage(parent, widgetFactory);
		}

		if (structureNode.getId() == OrmStructureNode.PERSISTENT_ATTRIBUTE_ID) {
			return new EclipseLinkOrmPersistentAttributeDetailsPage(parent, widgetFactory);
		}

		return null;
	}

}
