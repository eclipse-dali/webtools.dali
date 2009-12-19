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

import org.eclipse.jpt.core.JpaNode;
import org.eclipse.jpt.db.SchemaContainer;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * This combo-box displays a schema container's schemata.
 */
public abstract class SchemaCombo<T extends JpaNode>
	extends DatabaseObjectCombo<T>
{
	public SchemaCombo(Pane<? extends T> parentPane, Composite parent) {
		super(parentPane, parent);
	}

	public SchemaCombo(
						Pane<?> parentPane,
						PropertyValueModel<? extends T> subjectHolder,
						Composite parent
	) {
		super(parentPane, subjectHolder, parent);
	}

	public SchemaCombo(
						PropertyValueModel<? extends T> subjectHolder,
						Composite parent,
						WidgetFactory widgetFactory
	) {
		super(subjectHolder, parent, widgetFactory);
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
