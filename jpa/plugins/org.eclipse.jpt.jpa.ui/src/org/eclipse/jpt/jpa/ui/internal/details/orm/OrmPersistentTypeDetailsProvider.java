/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details.orm;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.internal.Tools;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.ui.details.JpaDetailsPageManager;
import org.eclipse.jpt.jpa.ui.details.JpaDetailsProvider;
import org.eclipse.jpt.jpa.ui.internal.details.PersistentTypeDetailsPage;
import org.eclipse.swt.widgets.Composite;

/**
 * This provider is responsible for creating the {@link JpaDetailsPageManager}
 * when the information comes from the XML file (either from the persistence
 * configuration or from the Mappings Descriptor).
 */
public class OrmPersistentTypeDetailsProvider
	implements JpaDetailsProvider
{
	// singleton
	private static final JpaDetailsProvider INSTANCE = new OrmPersistentTypeDetailsProvider();
	
	
	/**
	 * Return the singleton
	 */
	public static JpaDetailsProvider instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private OrmPersistentTypeDetailsProvider() {
		super();
	}
	
	
	public boolean providesDetails(JpaStructureNode structureNode) {
		return Tools.valuesAreEqual(structureNode.getType(), OrmPersistentType.class)
				&& structureNode.getResourceType().getContentType().isKindOf(JptJpaCorePlugin.MAPPING_FILE_CONTENT_TYPE);
	}
	
	public JpaDetailsPageManager<PersistentType> buildDetailsPageManager(
			Composite parent,
			WidgetFactory widgetFactory) {
		
		return new PersistentTypeDetailsPage(parent, widgetFactory);
	}
}