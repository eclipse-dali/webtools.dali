/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details.db;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.JpaNode;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.swt.widgets.Composite;

/**
 * This combo-box displays a table's columns.
 */
public abstract class ColumnCombo<T extends JpaNode>
	extends DatabaseObjectCombo<T>
{
	protected ColumnCombo(
			Pane<? extends T> parentPane, 
			Composite parent) {
		
		super(parentPane, parent);
	}
	
	protected ColumnCombo(
			Pane<?> parentPane,
			PropertyValueModel<? extends T> subjectHolder,
			Composite parent) {
		
		super(parentPane, subjectHolder, parent);
	}
	
	protected ColumnCombo(
			Pane<?> parentPane,
			PropertyValueModel<? extends T> subjectHolder,
			PropertyValueModel<Boolean> enabledModel,
			Composite parent) {

		super(parentPane, subjectHolder, enabledModel, parent);
	}

	
	@Override
	protected Iterable<String> getValues_() {
		Table dbTable = this.getDbTable();
		return (dbTable != null) ? dbTable.getSortedColumnIdentifiers() : EmptyIterable.<String>instance();
	}
	
	protected Table getDbTable() {
		return (this.getSubject() == null) ? null : this.getDbTable_();
	}
	
	/**
	 * Assume the subject is not null.
	 */
	protected abstract Table getDbTable_();
	
	@Override
	protected void tableChanged_(Table table) {
		super.tableChanged_(table);
		if (this.getDbTable() == table) {
			this.doPopulate();
		}
	}
}
