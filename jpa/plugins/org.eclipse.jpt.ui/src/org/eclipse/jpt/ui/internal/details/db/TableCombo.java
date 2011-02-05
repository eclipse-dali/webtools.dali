/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details.db;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.core.JpaNode;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.swt.widgets.Composite;

/**
 * This combo-box displays a schema's tables.
 */
public abstract class TableCombo<T extends JpaNode>
	extends DatabaseObjectCombo<T>
{
	public TableCombo(
			Pane<? extends T> parentPane,
			Composite parent) {
		
		super(parentPane, parent);
	}
	
	public TableCombo(
			Pane<?> parentPane,
			PropertyValueModel<? extends T> subjectHolder,
			Composite parent) {
		
		super(parentPane, subjectHolder, parent);
	}
	
	
	@Override
	protected Iterable<String> getValues_() {
		Schema dbSchema = this.getDbSchema();
		return (dbSchema != null) ? dbSchema.getSortedTableIdentifiers() : EmptyIterable.<String>instance();
	}
	
	protected Schema getDbSchema() {
		return (this.getSubject() == null) ? null : this.getDbSchema_();
	}

	/**
	 * Assume the subject is not null.
	 */
	protected abstract Schema getDbSchema_();

}
