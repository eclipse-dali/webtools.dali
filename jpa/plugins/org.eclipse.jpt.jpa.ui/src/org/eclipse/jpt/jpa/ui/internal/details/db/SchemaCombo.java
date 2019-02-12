/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details.db;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.JpaModel;
import org.eclipse.jpt.jpa.db.SchemaContainer;
import org.eclipse.swt.widgets.Composite;

/**
 * This combo-box displays a schema container's schemata.
 */
public abstract class SchemaCombo<T extends JpaModel>
	extends DatabaseObjectCombo<T>
{
	protected SchemaCombo(
			Pane<? extends T> parentPane,
			Composite parent) {
		
		super(parentPane, parent);
	}
	
	protected SchemaCombo(
			Pane<?> parentPane,
			PropertyValueModel<? extends T> subjectHolder,
			Composite parent) {
		
		super(parentPane, subjectHolder, parent);
	}
	
	protected SchemaCombo(
			Pane<?> parentPane,
			PropertyValueModel<? extends T> subjectHolder,
			PropertyValueModel<Boolean> enabledModel,
			Composite parent) {

		super(parentPane, subjectHolder, enabledModel, parent);
	}

	
	@Override
	protected Iterable<String> getValues_() {
		SchemaContainer sc = this.getDbSchemaContainer();
		return (sc != null) ? sc.getSortedSchemaIdentifiers() : EmptyIterable.<String>instance();
	}

	protected SchemaContainer getDbSchemaContainer() {
		return (this.getSubject() == null) ? null : this.getDbSchemaContainer_();
	}

	/**
	 * Assume the subject is not null.
	 */
	protected abstract SchemaContainer getDbSchemaContainer_();

}
