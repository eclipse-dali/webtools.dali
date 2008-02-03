/*******************************************************************************
 *  Copyright (c) 2006, 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.utility.internal.model.Model;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * The abstract class used to create a pane. (TODO)
 *
 * @deprecated Remove this pane and extend directly AbstractFormPane.
 *
 * @version 2.0
 * @since 1.0
 */
@Deprecated
public abstract class BaseJpaController<T extends Model> extends AbstractFormPane<T>
                                                         implements IJpaComposite<T>
{
	/**
	 * Creates a new <code>BaseJpaController</code>.
	 *
	 * @param parentController The parent controller of this one
	 * @param parent The parent container
	 *
	 * @category Constructor
	 */
	protected BaseJpaController(BaseJpaController<? extends T> parentController,
	                            Composite parent) {

		super(parentController, parent);
	}

	/**
	 * Creates a new <code>BaseJpaController</code>.
	 *
	 * @param parentController The parent container of this one
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various widgets
	 * @param automaticallyAlignWidgets <code>true</code> to make the widgets
	 * this pane aligned with the widgets of the given parent controller;
	 * <code>false</code> to not align them
	 *
	 * @category Constructor
	 */
	protected BaseJpaController(BaseJpaController<? extends T> parentController,
	                            Composite parent,
	                            boolean automaticallyAlignWidgets) {

		super(parentController, parent, automaticallyAlignWidgets);
	}

	/**
	 * Creates a new <code>BaseJpaController</code>.
	 *
	 * @param parentController The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 *
	 * @category Constructor
	 */
	protected BaseJpaController(BaseJpaController<?> parentController,
	                            PropertyValueModel<? extends T> subjectHolder,
	                            Composite parent) {

		super(parentController, subjectHolder, parent);
	}

	/**
	 * Creates a new <code>BaseJpaController</code>.
	 *
	 * @param parentController The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various widgets
	 * @param automaticallyAlignWidgets <code>true</code> to make the widgets
	 * this pane aligned with the widgets of the given parent controller;
	 * <code>false</code> to not align them
	 *
	 * @category Constructor
	 */
	protected BaseJpaController(BaseJpaController<?> parentController,
	                            PropertyValueModel<? extends T> subjectHolder,
	                            Composite parent,
	                            boolean automaticallyAlignWidgets) {

		super(parentController, subjectHolder, parent, automaticallyAlignWidgets);
	}

	/**
	 * Creates a new <code>BaseJpaController</code>.
	 *
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 *
	 * @category Constructor
	 */
	protected BaseJpaController(PropertyValueModel<? extends T> subjectHolder,
	                            Composite parent,
	                            TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}
}