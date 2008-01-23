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
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * The base class for the details view.
 *
 * @see IJpaContextNode
 *
 * @version 2.0
 * @since 1.0
 */
public abstract class BaseJpaDetailsPage<T extends IJpaContextNode>
	extends BaseJpaController<T>
	implements IJpaDetailsPage<T>
{
	private ScrolledForm scrolledForm;

	/**
	 * Creates a new <code>BaseJpaDetailsPage</code>.
	 *
	 * @param subjectHolder The holder of the subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public BaseJpaDetailsPage(PropertyValueModel<? extends T> subjectHolder,
	                          Composite parent,
	                          TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected Composite buildContainer(Composite container) {

		scrolledForm = getWidgetFactory().createScrolledForm(container);
		container = scrolledForm.getBody();

		GridLayout layout = new GridLayout(1, false);
		layout.marginHeight = 0;
		layout.marginWidth  = 0;
		layout.marginTop    = 5;
		layout.marginLeft   = 5;
		layout.marginBottom = 5;
		layout.marginRight  = 5;
		container.setLayout(layout);

		return container;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public Control getControl() {
		return scrolledForm;
	}
}