/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.v1_2.details.java;

import org.eclipse.jpt.core.context.AccessHolder;
import org.eclipse.jpt.core.context.java.JavaEmbeddable;
import org.eclipse.jpt.eclipselink.ui.internal.details.java.AbstractJavaEclipseLinkEmbeddableComposite;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.details.AccessTypeComposite;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
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
public class JavaEclipseLinkEmbeddable1_2Composite extends AbstractJavaEclipseLinkEmbeddableComposite
{
	/**
	 * Creates a new <code>EmbeddableComposite</code>.
	 *
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public JavaEclipseLinkEmbeddable1_2Composite(PropertyValueModel<? extends JavaEmbeddable> subjectHolder,
	                           Composite parent,
	                           WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite container) {
		this.initializeEmbeddableCollapsibleSection(container);
		this.initializeConvertersCollapsibleSection(container);
		this.initializeAdvancedCollapsibleSection(container);
	}

	@Override
	protected void initializeEmbeddableSection(Composite container) {
		new AccessTypeComposite(this, buildAccessHolder(), container);	
	}

	protected PropertyValueModel<AccessHolder> buildAccessHolder() {
		return new PropertyAspectAdapter<JavaEmbeddable, AccessHolder>(
			getSubjectHolder())
		{
			@Override
			protected AccessHolder buildValue_() {
				return this.subject.getPersistentType();
			}
		};
	}
}