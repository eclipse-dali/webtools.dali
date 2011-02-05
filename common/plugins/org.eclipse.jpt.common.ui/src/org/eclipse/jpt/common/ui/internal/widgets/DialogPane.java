/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.widgets;

import org.eclipse.jpt.common.utility.internal.node.Node;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * The abstract pane to use when the pane is shown in an <code>Dialog</code>.
 *
 * @see Dialog
 *
 * @version 2.0
 * @since 2.0
 */
public abstract class DialogPane<T extends Node> extends Pane<T> {

	/**
	 * Creates a new <code>DialogPane</code>.
	 *
	 * @param parentPane The parent controller of this one
	 * @param parent The parent container
	 *
	 * @category Constructor
	 */
	protected DialogPane(DialogPane<? extends T> parentPane,
	                             Composite parent) {

		super(parentPane, parent);
	}

	/**
	 * Creates a new <code>DialogPane</code>.
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
	protected DialogPane(DialogPane<? extends T> parentPane,
	                             Composite parent,
	                             boolean automaticallyAlignWidgets) {

		super(parentPane, parent, automaticallyAlignWidgets);
	}

	/**
	 * Creates a new <code>DialogPane</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 *
	 * @category Constructor
	 */
	protected DialogPane(DialogPane<?> parentPane,
	                             PropertyValueModel<? extends T> subjectHolder,
	                             Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	/**
	 * Creates a new <code>DialogPane</code>.
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
	protected DialogPane(DialogPane<?> parentPane,
	                             PropertyValueModel<? extends T> subjectHolder,
	                             Composite parent,
	                             boolean automaticallyAlignWidgets) {

		super(parentPane, subjectHolder, parent, automaticallyAlignWidgets);
	}

	/**
	 * Creates a new <code>DialogPane</code>.
	 *
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 *
	 * @category Constructor
	 */
	protected DialogPane(PropertyValueModel<? extends T> subjectHolder,
	                             Composite parent) {

		super(subjectHolder, parent, DefaultWidgetFactory.instance());
	}
}