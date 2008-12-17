/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.persistencexml.details;

import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.context.persistence.PersistenceStructureNodes;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaDetailsPage;
import org.eclipse.jpt.ui.details.JpaDetailsProvider;
import org.eclipse.swt.widgets.Composite;

/**
 * PersistenceDetailsProvider
 */
public class PersistenceDetailsProvider
	implements JpaDetailsProvider
{
	// singleton
	private static final JpaDetailsProvider INSTANCE = new PersistenceDetailsProvider();

	/**
	 * Return the singleton.
	 */
	public static JpaDetailsProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private PersistenceDetailsProvider() {
		super();
	}
	
	public JpaDetailsPage<? extends JpaStructureNode> buildDetailsPage(
								Composite parent,
								JpaStructureNode structureNode,
								WidgetFactory widgetFactory) {
		
		if (structureNode.getId() == PersistenceStructureNodes.PERSISTENCE_ID) {
			return new PersistenceXmlDetailsPage(parent, widgetFactory);
		}
		
		if (structureNode.getId() == PersistenceStructureNodes.PERSISTENCE_UNIT_ID) {
			return new PersistenceXmlDetailsPage(parent, widgetFactory);
		}

		return null;
	}

}
