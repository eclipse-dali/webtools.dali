/*******************************************************************************
 *  Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.node.Node;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * @deprecated Extend directly BaseJpaController, this pane will go away the
 *             moment I have a sec. ~PF
 */
@Deprecated
public abstract class BaseJpaComposite<T extends Node> extends BaseJpaController<T>
{
	/**
	 * Creates a new <code>BaseJpaComposite</code>.
	 *
	 * @param parentController The parent container of this one
	 * @param parent The parent container
	 */
	protected BaseJpaComposite(BaseJpaController<? extends T> parentController,
	                           Composite parent) {

		super(parentController, parent);
	}

	/**
	 * Creates a new <code>BaseJpaComposite</code>.
	 *
	 * @param parentController The parent container of this one
	 * @param parent The parent container
	 * @param automaticallyAlignWidgets <code>true</code> to make the widgets
	 * this pane aligned with the widgets of the given parent controller;
	 * <code>false</code> to not align them
	 */
	protected BaseJpaComposite(BaseJpaController<? extends T> parentController,
	                           Composite parent,
	                           boolean automaticallyAlignWidgets) {

		super(parentController, parent, automaticallyAlignWidgets);
	}

	/**
	 * Creates a new <code>BaseJpaComposite</code>.
	 *
	 * @param parentController The parent container of this one
	 * @param subjectHolder The holder of the subject
	 * @param parent The parent container
	 */
	protected BaseJpaComposite(BaseJpaController<?> parentController,
	                           PropertyValueModel<? extends T> subjectHolder,
	                           Composite parent) {

		super(parentController, subjectHolder, parent);
	}

	/**
	 * Creates a new <code>BaseJpaComposite</code>.
	 *
	 * @param parentController The parent container of this one
	 * @param subjectHolder The holder of the subject
	 * @param parent The parent container
	 * @param automaticallyAlignWidgets <code>true</code> to make the widgets
	 * this pane aligned with the widgets of the given parent controller;
	 * <code>false</code> to not align them
	 */
	protected BaseJpaComposite(BaseJpaController<?> parentController,
	                           PropertyValueModel<? extends T> subjectHolder,
	                           Composite parent,
	                           boolean automaticallyAlignWidgets) {

		super(parentController, subjectHolder, parent, automaticallyAlignWidgets);
	}

	/**
	 * Creates a new <code>BaseJpaComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	protected BaseJpaComposite(PropertyValueModel<? extends T> subjectHolder,
	                           Composite parent,
	                           TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}
}