/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.widgets;

import org.eclipse.jpt.common.utility.model.Model;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;

/**
 * This <code>EnumComboViewer</code> should be used within a form pane.
 *
 * @version 2.3
 * @since 1.0
 */
public abstract class EnumFormComboViewer<T extends Model, V>
	extends EnumComboViewer<T, V>
{
	/**
	 * Creates a new <code>EnumFormComboViewer</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various widgets
	 */
	protected EnumFormComboViewer(Pane<? extends T> parentPane,
	                              Composite parent
	) {
		super(parentPane, parent);
	}

	protected EnumFormComboViewer(Pane<? extends T> parentPane,
									Composite parent,
									PropertyValueModel<Boolean> enabledModel
	) {
		super(parentPane, parent, enabledModel);
	}

	/**
	 * Creates a new <code>EnumFormComboViewer</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various widgets
	 */
	protected EnumFormComboViewer(Pane<?> parentPane,
	                              PropertyValueModel<? extends T> subjectHolder,
	                              Composite parent
	) {
		super(parentPane, subjectHolder, parent);
	}

	protected EnumFormComboViewer(Pane<?> parentPane,
        							PropertyValueModel<? extends T> subjectHolder,
        							PropertyValueModel<Boolean> enabledModel,
        							Composite parent
		) {
		super(parentPane, subjectHolder, enabledModel, parent);
	}

	@Override
	void removeAll() {
		getControl().removeAll();
	}

	@Override
	void updateCursor() {
		getControl().setSelection(new Point(0, 0));
	}
}
