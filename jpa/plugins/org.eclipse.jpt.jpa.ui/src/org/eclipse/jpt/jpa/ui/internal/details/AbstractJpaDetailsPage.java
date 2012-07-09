/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.jpt.jpa.ui.details.JpaDetailsPageManager;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * The base class for the details view.
 *
 * @see JpaStructureNode
 *
 * @version 3.0
 * @since 1.0
 */
public abstract class AbstractJpaDetailsPage<T extends JpaStructureNode>
	extends Pane<T>
	implements JpaDetailsPageManager<T>
{

	protected AbstractJpaDetailsPage(Composite parent, WidgetFactory widgetFactory) {
		super(new SimplePropertyValueModel<T>(), parent, widgetFactory);
	}

	public Control getPage() {
		return this.getControl();
	}

	protected JpaPlatformUi getJpaPlatformUi() {
        return (JpaPlatformUi) getSubject().getJpaPlatform().getAdapter(JpaPlatformUi.class);
	}

	@SuppressWarnings("unchecked")
	public final void setSubject(Object subject) {
		ModifiablePropertyValueModel<T> subjectHolder = (ModifiablePropertyValueModel<T>) getSubjectHolder();
		subjectHolder.setValue((T) subject);
	}
}
