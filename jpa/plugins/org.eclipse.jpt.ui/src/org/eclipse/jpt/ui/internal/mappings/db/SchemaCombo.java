/*******************************************************************************
 *  Copyright (c) 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.db;

import org.eclipse.jpt.core.internal.IJpaNode;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.ui.internal.details.BaseJpaController;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * @version 2.0
 * @since 2.0
 */
public abstract class SchemaCombo extends AbstractDatabaseObjectCombo<IJpaNode>
{
	/**
	 * Creates a new <code>SchemaCombo</code>.
	 *
	 * @param parentController The parent container of this one
	 * @param parent The parent container
	 */
	public SchemaCombo(BaseJpaController<? extends IJpaNode> parentController,
	                   Composite parent)
	{
		super(parentController, parent);
	}

	/**
	 * Creates a new <code>SchemaCombo</code>.
	 *
	 * @param subjectHolder The holder of the subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public SchemaCombo(PropertyValueModel<? extends IJpaNode> subjectHolder,
	                   Composite parent,
	                   TabbedPropertySheetWidgetFactory widgetFactory)
	{
		super(subjectHolder, parent, widgetFactory);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void schemaChanged(Schema schema) {
		connectionProfile().getDatabase().schemaNamed();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void tableChanged(Table table) {
	}
}
