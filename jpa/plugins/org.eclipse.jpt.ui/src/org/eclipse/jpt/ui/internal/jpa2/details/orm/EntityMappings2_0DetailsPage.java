/*******************************************************************************
* Copyright (c) 2009, 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.ui.internal.jpa2.details.orm;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.details.orm.AbstractEntityMappingsDetailsPage;
import org.eclipse.swt.widgets.Composite;

/**
 *  EntityMappings2_0DetailsPage
 */
public class EntityMappings2_0DetailsPage extends AbstractEntityMappingsDetailsPage
{
	/**
	 * Creates a new <code>EntityMappings2_0DetailsPage</code>.
	 *
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public EntityMappings2_0DetailsPage(Composite parent,
	                                 WidgetFactory widgetFactory) {

		super(parent, widgetFactory);
	}

	@Override
	protected void initializeGeneratorsCollapsibleSection(Composite container) {
		new EntityMappingsGenerators2_0Composite(this, container);
	}

	@Override
	protected void initializeQueriesCollapsibleSection(Composite container) {
		new OrmQueries2_0Composite(this, container);
	}

}
