/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.details.orm;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.jpa.ui.details.JpaDetailsPageManager;
import org.eclipse.jpt.jpa.ui.details.JpaDetailsProvider;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractEntityMappingsDetailsProvider;
import org.eclipse.swt.widgets.Composite;

/**
 *  EntityMappings2_0DetailsProvider
 */
public class EntityMappingsDetailsProvider2_0
	extends AbstractEntityMappingsDetailsProvider
{
	// singleton
	private static final JpaDetailsProvider INSTANCE = new EntityMappingsDetailsProvider2_0();


	/**
	 * Return the singleton
	 */
	public static JpaDetailsProvider instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private EntityMappingsDetailsProvider2_0() {
		super();
	}


	public JpaDetailsPageManager buildDetailsPageManager(Composite parent, WidgetFactory widgetFactory, ResourceManager resourceManager) {
		return new EntityMappingsDetailsPageManager2_0(parent, widgetFactory, resourceManager);
	}
}
