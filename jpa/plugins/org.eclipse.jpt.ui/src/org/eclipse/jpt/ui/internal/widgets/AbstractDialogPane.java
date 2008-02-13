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

import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.node.Node;
import org.eclipse.swt.widgets.Composite;

/**
 * The abstract pane to use when the pane is shown in an <code>AbstractDialog</code>.
 *
 * @see AbstractDialog
 *
 * @version 2.0
 * @since 2.0
 */
public abstract class AbstractDialogPane<T extends Node> extends AbstractPane<T> {

	/**
	 * Creates a new <code>AbstractDialog</code>.
	 *
	 * @param parentPane The parent controller of this one
	 * @param parent The parent container
	 *
	 * @category Constructor
	 */
	protected AbstractDialogPane(AbstractDialogPane<? extends T> parentPane,
	                             Composite parent) {

		super(parentPane, parent);
	}

	/**
	 * Creates a new <code>AbstractDialogPane</code>.
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
	protected AbstractDialogPane(AbstractDialogPane<? extends T> parentPane,
	                             Composite parent,
	                             boolean automaticallyAlignWidgets) {

		super(parentPane, parent, automaticallyAlignWidgets);
	}

	/**
	 * Creates a new <code>AbstractDialogPane</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 *
	 * @category Constructor
	 */
	protected AbstractDialogPane(AbstractDialogPane<?> parentPane,
	                             PropertyValueModel<? extends T> subjectHolder,
	                             Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	/**
	 * Creates a new <code>AbstractDialogPane</code>.
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
	protected AbstractDialogPane(AbstractDialogPane<?> parentPane,
	                             PropertyValueModel<? extends T> subjectHolder,
	                             Composite parent,
	                             boolean automaticallyAlignWidgets) {

		super(parentPane, subjectHolder, parent, automaticallyAlignWidgets);
	}

	/**
	 * Creates a new <code>AbstractDialogPane</code>.
	 *
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 *
	 * @category Constructor
	 */
	protected AbstractDialogPane(PropertyValueModel<? extends T> subjectHolder,
	                             Composite parent) {

		super(subjectHolder, parent, DefaultWidgetFactory.instance());
	}
}