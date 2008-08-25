/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.widgets;

import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.utility.model.Model;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * The abstract pane to use when the pane is shown using the form look and feel,
 * which is handled by <code>TabbedPropertySheetWidgetFactory</code>.
 *
 * @see TabbedPropertySheetWidgetFactory
 *
 * @version 2.0
 * @since 2.0
 */
public abstract class FormPane<T extends Model> extends Pane<T>
{
	/**
	 * Creates a new <code>FormPane</code>.
	 *
	 * @param parentPane The parent controller of this one
	 * @param parent The parent container
	 *
	 * @category Constructor
	 */
	protected FormPane(FormPane<? extends T> parentPane,
	                           Composite parent) {

		super(parentPane, parent);
	}

	/**
	 * Creates a new <code>FormPane</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various widgets
	 * @param automaticallyAlignWidgets <code>true</code> to make the widgets
	 * this pane aligned with the widgets of the given parent controller;
	 * <code>false</code> to not align them
	 *
	 * @category Constructor
	 */
	protected FormPane(FormPane<? extends T> parentPane,
	                           Composite parent,
	                           boolean automaticallyAlignWidgets) {

		super(parentPane, parent, automaticallyAlignWidgets);
	}

	/**
	 * Creates a new <code>FormPane</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 *
	 * @category Constructor
	 */
	protected FormPane(FormPane<?> parentPane,
	                           PropertyValueModel<? extends T> subjectHolder,
	                           Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	/**
	 * Creates a new <code>FormPane</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various widgets
	 * @param automaticallyAlignWidgets <code>true</code> to make the widgets
	 * this pane aligned with the widgets of the given parent controller;
	 * <code>false</code> to not align them
	 *
	 * @category Constructor
	 */
	protected FormPane(FormPane<?> parentPane,
	                           PropertyValueModel<? extends T> subjectHolder,
	                           Composite parent,
	                           boolean automaticallyAlignWidgets) {

		super(parentPane, subjectHolder, parent, automaticallyAlignWidgets);
	}
	
	/**
	 * Creates a new <code>FormPane</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various widgets
	 * @param automaticallyAlignWidgets <code>true</code> to make the widgets
	 * this pane aligned with the widgets of the given parent controller;
	 * <code>false</code> to not align them
	 *
	 * @category Constructor
	 */
	protected FormPane(FormPane<?> parentPane,
	                           PropertyValueModel<? extends T> subjectHolder,
	                           Composite parent,
	                           boolean automaticallyAlignWidgets,
	                           boolean parentManagePane) {

		super(parentPane, subjectHolder, parent, automaticallyAlignWidgets, parentManagePane);
	}

	/**
	 * Creates a new <code>FormPane</code>.
	 *
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 *
	 * @category Constructor
	 */
	protected FormPane(PropertyValueModel<? extends T> subjectHolder,
	                           Composite parent,
	                           WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	/**
	 * Creates a new <code>FormPane</code>.
	 *
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 *
	 * @category Constructor
	 */
	protected FormPane(PropertyValueModel<? extends T> subjectHolder,
	                           Composite parent,
	                           FormToolkit widgetFactory) {

		this(subjectHolder, parent, new FormWidgetFactory(widgetFactory));
	}

	/**
	 * Returns the actual widget factory wrapped by the <code>IWidgetFactory</code>.
	 *
	 * @return The factory used to create the widgets with the form style
	 * (flat-style) look and feel
	 */
	protected final FormToolkit getFormWidgetFactory() {
		FormWidgetFactory widgetFactory = (FormWidgetFactory) getWidgetFactory();
		return widgetFactory.getWidgetFactory();
	}
}