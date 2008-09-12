/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.db;

import java.util.Iterator;

import org.eclipse.jpt.core.JpaNode;
import org.eclipse.jpt.db.Database;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * This combo-box displays the database's catalogs.
 */
public abstract class CatalogCombo<T extends JpaNode>
	extends DatabaseObjectCombo<T>
{
	public CatalogCombo(Pane<? extends T> parentPane, Composite parent) {
		super(parentPane, parent);
	}

	public CatalogCombo(
						Pane<?> parentPane,
						PropertyValueModel<? extends T> subjectHolder,
						Composite parent
	) {
		super(parentPane, subjectHolder, parent);
	}

	public CatalogCombo(
						PropertyValueModel<? extends T> subjectHolder,
						Composite parent,
						WidgetFactory widgetFactory
	) {
		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected Iterator<String> values() {
		Database db = this.getDatabase();
		return (db == null) ? EmptyIterator.<String>instance() : db.sortedCatalogIdentifiers();
	}

}
