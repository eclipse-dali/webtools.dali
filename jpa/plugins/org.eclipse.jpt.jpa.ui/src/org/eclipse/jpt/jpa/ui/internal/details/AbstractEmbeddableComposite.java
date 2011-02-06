/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.Embeddable;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.internal.details.JptUiDetailsMessages;
import org.eclipse.swt.widgets.Composite;

/**
 * This pane does not have any widgets.
 *
 * @see Embeddable
 * @see JavaUiFactory - The factory creating this pane
 * @see EmbeddableUiProvider
 *
 * @version 2.3
 * @since 2.1
 */
public abstract class AbstractEmbeddableComposite<T extends Embeddable> 
	extends Pane<T>
	implements JpaComposite
{
	/**
	 * Creates a new <code>EmbeddableComposite</code>.
	 *
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	protected AbstractEmbeddableComposite(PropertyValueModel<? extends T> subjectHolder,
	                           Composite parent,
	                           WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite container) {
	}
	
	protected void initializeEmbeddableCollapsibleSection(Composite container) {
		container = addCollapsibleSection(
			container,
			JptUiDetailsMessages.EmbeddableSection_title,
			new SimplePropertyValueModel<Boolean>(Boolean.TRUE)
		);

		this.initializeEmbeddableSection(container);
	}
	
	protected void initializeEmbeddableSection(Composite container) {
		
	}
}