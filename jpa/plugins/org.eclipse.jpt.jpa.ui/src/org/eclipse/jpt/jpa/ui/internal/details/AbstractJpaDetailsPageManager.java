/*******************************************************************************
 * Copyright (c) 2007, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jface.resource.ResourceManager;
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
 * The base class for the JPA details view page manager.
 *
 * @see JpaStructureNode
 */
public abstract class AbstractJpaDetailsPageManager<T extends JpaStructureNode>
	extends Pane<T>
	implements JpaDetailsPageManager
{
	protected AbstractJpaDetailsPageManager(Composite parent, WidgetFactory widgetFactory, ResourceManager resourceManager) {
		super(new SimplePropertyValueModel<T>(), parent, widgetFactory, resourceManager);
	}

	public Control getPage() {
		return this.getControl();
	}

	protected JpaPlatformUi getJpaPlatformUi() {
		JpaStructureNode node = this.getSubject();
        return (node == null) ? null : node.getJpaPlatform().getAdapter(JpaPlatformUi.class);
	}

	@SuppressWarnings("unchecked")
	public final void setSubject(JpaStructureNode subject) {
		ModifiablePropertyValueModel<T> subjectHolder = (ModifiablePropertyValueModel<T>) getSubjectHolder();
		subjectHolder.setValue((T) subject);
	}

	@Override
	protected void controlDisposed() {
		super.controlDisposed();
	}
}
