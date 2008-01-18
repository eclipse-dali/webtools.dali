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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public abstract class BaseJpaComposite<T extends Node> extends BaseJpaController<T>
	implements IJpaComposite<T>
{
	/**
	 * The container of this composite.
	 */
	private Composite composite;

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

	protected Composite buildComposite(Composite parent) {
		Composite container = this.buildPane(parent);

		GridLayout layout = new GridLayout(1, false);
		layout.marginHeight = 0;
		layout.marginWidth  = 0;
		layout.marginTop    = 0;
		layout.marginLeft   = 0;
		layout.marginBottom = 0;
		layout.marginRight  = 0;
		container.setLayout(layout);

		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		container.setLayoutData(gridData);

		return container;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected final void buildWidgets(Composite parent) {
		this.composite = buildComposite(parent);
		this.initializeLayout(this.composite);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public Control getControl() {
		return this.composite;
	}

	/**
	 * Initializes the layout of this pane.
	 *
	 * @param container The parent container
	 */
	protected abstract void initializeLayout(Composite container);
}