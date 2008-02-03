/*******************************************************************************
 *  Copyright (c) 2006, 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import org.eclipse.jpt.core.internal.context.base.IJpaContextNode;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;
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
	extends AbstractFormPane<T>
	implements IJpaDetailsPage<T>
{
	/**
	 * Creates a new <code>BaseJpaDetailsPage</code>.
	 *
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	protected BaseJpaDetailsPage(Composite parent,
	                             TabbedPropertySheetWidgetFactory widgetFactory) {

		super(new SimplePropertyValueModel<T>(), parent, widgetFactory);
	}

	/*
	 * (non-Javadoc)
	 */
	public final void setSubject(T subject) {
		WritablePropertyValueModel<T> subjectHolder = (WritablePropertyValueModel<T>) getSubjectHolder();
		subjectHolder.setValue(subject);

		if (subject != null) {
			populate();
		}
		else {
			dispose();
		}
	}
}