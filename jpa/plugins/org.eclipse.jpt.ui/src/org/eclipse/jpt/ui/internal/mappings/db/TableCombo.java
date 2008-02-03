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
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * This database object combo handles showing the database's tables.
 *
 * @version 2.0
 * @since 2.0
 */
public abstract class TableCombo<T extends IJpaNode> extends AbstractDatabaseObjectCombo<T>
{
	/**
	 * Creates a new <code>TableCombo</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public TableCombo(AbstractFormPane<? extends T> parentPane,
	                  Composite parent) {

		super(parentPane, parent);
	}

	/**
	 * Creates a new <code>TableCombo</code>.
	 *
	 * @param subjectHolder The holder of the subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public TableCombo(PropertyValueModel<? extends T> subjectHolder,
	                  Composite parent,
	                  IWidgetFactory widgetFactory)
	{
		super(subjectHolder, parent, widgetFactory);
	}

	protected abstract Table table();

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void tableChanged(Table table) {
		super.tableChanged(table);

		if (table == table()) {
			this.doPopulate();
		}
	}
}