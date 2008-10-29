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
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.orm.OrmStructureNodes;
import org.eclipse.jpt.eclipselink.core.EclipseLinkJpaFile;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaDetailsPage;
import org.eclipse.jpt.ui.details.JpaDetailsProvider;
import org.eclipse.jpt.ui.internal.orm.details.OrmPersistentAttributeDetailsPage;
import org.eclipse.jpt.ui.internal.orm.details.OrmPersistentTypeDetailsPage;
import org.eclipse.swt.widgets.Composite;

/**
 * This provider is responsible for creating the {@link JpaDetailsPage}
 * when the information comes from the XML file (either from the persistence
 * configuration or from the Mappings Descriptor).
 *
 * @version 2.1
 * @since 2.1
 */
public class OrmDetailsProvider
	implements JpaDetailsProvider
{
	public OrmDetailsProvider() {
		super();
	}

	public JpaDetailsPage<? extends JpaStructureNode> buildDetailsPage(
		Composite parent,
		JpaStructureNode structureNode,
		WidgetFactory widgetFactory) {

		if (structureNode.getId() == OrmStructureNodes.ENTITY_MAPPINGS_ID) {
			//TODO JpaPlatformUi really needs a complete overhaul, this is not a good solution
			if ((((XmlContextNode) structureNode).getEResource()).getType() == EclipseLinkJpaFile.ECLIPSELINK_ORM_RESOURCE_TYPE) {
				return new EntityMappingsDetailsPage(parent, widgetFactory);
			}
			return new org.eclipse.jpt.ui.internal.orm.details.EntityMappingsDetailsPage(parent, widgetFactory);
		}

		if (structureNode.getId() == OrmStructureNodes.PERSISTENT_TYPE_ID) {
			return new OrmPersistentTypeDetailsPage(parent, widgetFactory);
		}

		if (structureNode.getId() == OrmStructureNodes.PERSISTENT_ATTRIBUTE_ID) {
			return new OrmPersistentAttributeDetailsPage(parent, widgetFactory);
		}

		return null;
	}
}