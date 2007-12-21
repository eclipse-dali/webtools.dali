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
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public abstract class BaseJpaComposite<T> extends BaseJpaController<T>
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
	 * @param subjectHolder The holder of the subject <code>T</code>
	 * @param parent The parent container
	 * @param style
	 * @param widgetFactory The factory used to create various common widgets
	 */
	protected BaseJpaComposite(PropertyValueModel<? extends T> subjectHolder,
	                           Composite parent,
	                           int style,
	                           TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent, style, widgetFactory);
	}

	/**
	 * Creates a new <code>BaseJpaComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>T</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	protected BaseJpaComposite(PropertyValueModel<? extends T> subjectHolder,
	                           Composite parent,
	                           TabbedPropertySheetWidgetFactory widgetFactory) {

		this(subjectHolder, parent, SWT.NONE, widgetFactory);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void buildWidget(Composite parent, int style) {
		this.composite = createComposite(parent, style);
		initializeLayout(this.composite);
	}

	protected Composite createComposite(Composite parent, int style) {
		Composite container = this.widgetFactory.createComposite(parent, style);

		GridLayout layout = new GridLayout(1, false);
		layout.marginWidth  = 0;
		layout.marginHeight = 0;
		container.setLayout(layout);

		GridData gridData = new GridData();
		gridData.horizontalAlignment       = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		container.setLayoutData(gridData);

		return container;
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
	 * @param composite The parent container
	 */
	protected abstract void initializeLayout(Composite composite);
}