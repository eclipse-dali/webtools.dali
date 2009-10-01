/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.ui.JpaPlatformUi;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaDetailsPage;
import org.eclipse.jpt.ui.internal.platform.JpaPlatformUiRegistry;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * The base class for the details view.
 *
 * @see JpaStructureNode
 *
 * @version 3.0
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
	protected AbstractJpaDetailsPage(Composite parent, WidgetFactory widgetFactory) {
		super(new SimplePropertyValueModel<T>(), parent, widgetFactory);
	}

	protected JpaPlatformUi getJpaPlatformUi() {
		String platformId = getSubject().getJpaProject().getJpaPlatform().getId();
		return JpaPlatformUiRegistry.instance().getJpaPlatformUi(platformId);
	}

	public final void setSubject(T subject) {
		WritablePropertyValueModel<T> subjectHolder = (WritablePropertyValueModel<T>) getSubjectHolder();
		subjectHolder.setValue(subject);
	}
}