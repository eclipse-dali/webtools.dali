/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.context.JpaContextNode;
import org.eclipse.jpt.ui.JpaPlatformUi;
import org.eclipse.jpt.ui.JpaUiFactory;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaDetailsPage;
import org.eclipse.jpt.ui.internal.platform.JpaPlatformUiRegistry;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.ScrolledForm;

/**
 * The base class for the details view.
 *
 * @see JpaContextNode
 *
 * @version 2.0
 * @since 1.0
 */
public abstract class AbstractJpaDetailsPage<T extends JpaStructureNode>
	extends FormPane<T>
	implements JpaDetailsPage<T>
{
	/**
	 * Creates a new <code>BaseJpaDetailsPage</code>.
	 *
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	protected AbstractJpaDetailsPage(Composite parent,
	                                 WidgetFactory widgetFactory) {

		super(new SimplePropertyValueModel<T>(), parent, widgetFactory);
	}

	protected JpaPlatformUi getJpaPlatformUi() {
		String platformId = getSubject().getJpaProject().getJpaPlatform().getId();
		return JpaPlatformUiRegistry.instance().getJpaPlatformUi(platformId);
	}

	protected JpaUiFactory getJpaUiFactory() {
		return getJpaPlatformUi().getJpaUiFactory();
	}

	/**
	 * There is an issue with <code>ScrolledForm</code>, it doesn't repaint the
	 * entire content, this will retrieve it by going up the hierarchy of the
	 * given <code>Composite</code> and force a reflow on it.
	 *
	 * @param container The container used to find the <code>ScrolledForm</code>
	 */
	protected final void repaintDetailsView(Composite container) {

		while (container != null &&
		     !(container instanceof ScrolledForm))
		{
			container = container.getParent();
		}

		if (container != null) {
			ScrolledForm scrolledForm = (ScrolledForm) container;
			scrolledForm.reflow(true);
		}
	}

	/*
	 * (non-Javadoc)
	 */
	public final void setSubject(T subject) {

		SimplePropertyValueModel<T> subjectHolder = (SimplePropertyValueModel<T>) getSubjectHolder();

		// Populate this page with the new subject
		if (subject != null) {
			subjectHolder.setValue(subject);
			populate();
		}
		// Dispose this page
		else {
			disengageListeners();
			subjectHolder.setValue(null);
		}
	}
}