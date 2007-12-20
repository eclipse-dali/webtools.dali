/*******************************************************************************
 *  Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import org.eclipse.jpt.core.internal.context.base.IJpaContextNode;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public abstract class BaseJpaDetailsPage<T extends IJpaContextNode>
	extends BaseJpaComposite<T>
	implements IJpaDetailsPage<T>
{
	private Composite control;

	public BaseJpaDetailsPage(
			Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, widgetFactory);
	}

	public BaseJpaDetailsPage(
			Composite parent, int style, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, style, widgetFactory);
	}

	@Override
	//using this to get a Scroll bar on the JpaDetailsView
	protected Composite createComposite(Composite parent, int style) {
		ScrolledForm scrolledForm = this.widgetFactory.createScrolledForm(parent);
		//Nesting another composite because combos on the ScrolledForm didn't have a border
		scrolledForm.getBody().setLayout(new FillLayout());
		Composite composite = super.createComposite(scrolledForm.getBody(), style);
		this.control = scrolledForm;
		return composite;
	}

	@Override
	public Control getControl() {
		return this.control;
	}
}