/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.core.JpaModel;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.swt.widgets.Composite;

/**
 * This combo-box displays a schema's sequences.
 */
public abstract class SequenceCombo<T extends JpaModel>
	extends DatabaseObjectCombo<T>
{
	public SequenceCombo(
			Pane<?> parentPane,
			PropertyValueModel<? extends T> subjectHolder,
			Composite parent) {
		
		super(parentPane, subjectHolder, parent);
	}
	

	@Override
	protected Iterable<String> getValues_() {
		Schema dbSchema = this.getDbSchema();
		return (dbSchema != null) ? dbSchema.getSortedSequenceIdentifiers() : EmptyIterable.<String>instance();
	}

	protected Schema getDbSchema() {
		return (this.getSubject() == null) ? null : this.getDbSchema_();
	}

	/**
	 * Assume the subject is not null.
	 */
	protected abstract Schema getDbSchema_();

}
