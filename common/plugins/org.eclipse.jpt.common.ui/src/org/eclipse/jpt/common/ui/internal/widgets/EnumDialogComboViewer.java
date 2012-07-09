/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
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
 * This <code>EnumComboViewer</code> should be used within a dialog pane.
 *
 * @version 2.0
 * @since 2.0
 */
public abstract class EnumDialogComboViewer<T extends Model, V>
	extends EnumComboViewer<T, V>
{
	/**
	 * Creates a new <code>EnumDialogComboViewer</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various widgets
	 */
	protected EnumDialogComboViewer(DialogPane<? extends T> parentPane,
	                                Composite parent
	) {
		super(parentPane, parent);
	}

	/**
	 * Creates a new <code>EnumDialogComboViewer</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various widgets
	 */
	protected EnumDialogComboViewer(DialogPane<?> parentPane,
	                                PropertyValueModel<? extends T> subjectHolder,
	                                Composite parent
	) {
		super(parentPane, subjectHolder, parent);
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
