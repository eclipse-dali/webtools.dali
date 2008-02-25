/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.widgets;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jpt.utility.internal.model.Model;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;

/**
 * This <code>EnumComboViewer</code> should be used within a form pane.
 *
 * @version 2.0
 * @since 1.0
 */
public abstract class EnumFormComboViewer<T extends Model, V> extends AbstractEnumComboViewer<T, V>
{
	/**
	 * Creates a new <code>EnumFormComboViewer</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various widgets
	 */
	protected EnumFormComboViewer(AbstractFormPane<? extends T> parentPane,
	                              Composite parent) {

		super(parentPane, parent);
	}

	/**
	 * Creates a new <code>EnumFormComboViewer</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various widgets
	 */
	protected EnumFormComboViewer(AbstractFormPane<?> parentPane,
	                              PropertyValueModel<? extends T> subjectHolder,
	                              Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	/**
	 * Creates a new <code>EnumFormComboViewer</code>.
	 *
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various widgets
	 */
	protected EnumFormComboViewer(PropertyValueModel<? extends T> subjectHolder,
	                              Composite parent,
	                              WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	ComboViewer buildComboViewer(Composite container) {
		return buildCComboViewer(container, buildLabelProvider());
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public void enableWidgets(boolean enabled) {

		super.enableWidgets(enabled);

		CCombo combo = getCombo();

		if (combo.isDisposed()) {
			combo.setEnabled(enabled);
		}
	}

	protected final CCombo getCombo() {
		return this.getComboViewer().getCCombo();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	void removeAll() {
		getCombo().removeAll();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	void updateCursor() {
		getCombo().setSelection(new Point(0, 0));
	}
}
