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
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.JpaModel;
import org.eclipse.swt.widgets.Composite;

/**
 * This combo-box displays the database's catalogs.
 */
public abstract class CatalogCombo<T extends JpaModel>
	extends DatabaseObjectCombo<T>
{
	protected CatalogCombo(
			Pane<? extends T> parentPane,
			Composite parent) {
		
		super(parentPane, parent);
	}
	
	protected CatalogCombo(
			Pane<?> parentPane,
			PropertyValueModel<? extends T> subjectHolder,
			Composite parent) {
		
		super(parentPane, subjectHolder, parent);
	}
	
	protected CatalogCombo(
			Pane<?> parentPane,
			PropertyValueModel<? extends T> subjectHolder,
			PropertyValueModel<Boolean> enabledModel,
			Composite parent) {

		super(parentPane, subjectHolder, enabledModel, parent);
	}

	
	@Override
	protected Iterable<String> getValues_() {
		return this.getDatabase().getSortedCatalogIdentifiers();
	}

}
