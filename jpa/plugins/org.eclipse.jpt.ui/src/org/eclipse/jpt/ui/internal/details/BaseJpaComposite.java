/*******************************************************************************
 *  Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import org.eclipse.swt.SWT;
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

	public BaseJpaComposite(Composite parent, int style, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, style, widgetFactory);
	}

	public BaseJpaComposite(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		this(parent, SWT.NULL, widgetFactory);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void buildWidget(Composite parent, int style) {
		super.buildWidget(parent);
		this.composite = createComposite(parent, style);
		initializeLayout(this.composite);
	}

	protected Composite createComposite(Composite parent, int style) {
		return this.widgetFactory.createComposite(parent, style);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public Control getControl() {
		return this.composite;
	}

	protected abstract void initializeLayout(Composite composite);
}