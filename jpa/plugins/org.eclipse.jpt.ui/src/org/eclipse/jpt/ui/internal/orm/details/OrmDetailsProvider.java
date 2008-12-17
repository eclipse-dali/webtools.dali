/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.orm.details;

import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.context.orm.OrmStructureNode;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaDetailsPage;
import org.eclipse.jpt.ui.details.JpaDetailsProvider;
import org.eclipse.swt.widgets.Composite;

/**
 * This provider is responsible for creating the {@link JpaDetailsPage}
 * when the information comes from the XML file (either from the persistence
 * configuration or from the Mappings Descriptor).
 */
public class OrmDetailsProvider
	implements JpaDetailsProvider
{
	// singleton
	private static final JpaDetailsProvider INSTANCE = new OrmDetailsProvider();

	/**
	 * Return the singleton.
	 */
	public static JpaDetailsProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private OrmDetailsProvider() {
		super();
	}

	public JpaDetailsPage<? extends JpaStructureNode> buildDetailsPage(
		Composite parent,
		JpaStructureNode structureNode,
		WidgetFactory widgetFactory) {

		if (structureNode.getId() == OrmStructureNode.ENTITY_MAPPINGS_ID) {
			return new EntityMappingsDetailsPage(parent, widgetFactory);
		}

		if (structureNode.getId() == OrmStructureNode.PERSISTENT_TYPE_ID) {
			return new OrmPersistentTypeDetailsPage(parent, widgetFactory);
		}

		if (structureNode.getId() == OrmStructureNode.PERSISTENT_ATTRIBUTE_ID) {
			return new OrmPersistentAttributeDetailsPage(parent, widgetFactory);
		}

		return null;
	}

}
