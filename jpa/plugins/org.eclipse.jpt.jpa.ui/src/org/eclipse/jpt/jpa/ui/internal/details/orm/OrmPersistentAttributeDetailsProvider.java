/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details.orm;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.ui.details.JpaDetailsPageManager;
import org.eclipse.jpt.jpa.ui.details.JpaDetailsProvider;
import org.eclipse.swt.widgets.Composite;

/**
 * This provider is responsible for creating the {@link JpaDetailsPageManager}
 * when the information comes from the XML file (either from the persistence
 * configuration or from the Mappings Descriptor).
 */
public class OrmPersistentAttributeDetailsProvider
	implements JpaDetailsProvider
{
	// singleton
	private static final JpaDetailsProvider INSTANCE = new OrmPersistentAttributeDetailsProvider();
	
	
	/**
	 * Return the singleton
	 */
	public static JpaDetailsProvider instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private OrmPersistentAttributeDetailsProvider() {
		super();
	}

	public Class<OrmPersistentAttribute> getStructureType() {
		return OrmPersistentAttribute.class;
	}

	public JpaDetailsPageManager buildDetailsPageManager(Composite parent, WidgetFactory widgetFactory, ResourceManager resourceManager) {
		return new OrmPersistentAttributeDetailsPageManager(parent, widgetFactory, resourceManager);
	}
}
