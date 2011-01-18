/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.details.java;

import org.eclipse.jpt.core.context.java.JavaEmbeddable;
import org.eclipse.jpt.eclipselink.core.context.java.JavaEclipseLinkConverterContainer;
import org.eclipse.jpt.eclipselink.core.context.java.JavaEclipseLinkEmbeddable;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkEmbeddableAdvancedComposite;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkUiDetailsMessages;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.details.AbstractEmbeddableComposite;
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
public abstract class AbstractJavaEclipseLinkEmbeddableComposite extends AbstractEmbeddableComposite<JavaEmbeddable>
                                 implements JpaComposite
{
	/**
	 * Creates a new <code>EmbeddableComposite</code>.
	 *
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	protected AbstractJavaEclipseLinkEmbeddableComposite(PropertyValueModel<? extends JavaEmbeddable> subjectHolder,
	                           Composite parent,
	                           WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite container) {
		initializeConvertersCollapsibleSection(container);
		initializeAdvancedCollapsibleSection(container);
	}

	protected void initializeConvertersCollapsibleSection(Composite container) {

		container = addCollapsibleSection(
			container,
			EclipseLinkUiDetailsMessages.EclipseLinkTypeMappingComposite_converters
		);
		initializeConvertersSection(container, this.buildConverterHolderValueModel());
	}

	protected void initializeConvertersSection(Composite container, PropertyValueModel<JavaEclipseLinkConverterContainer> converterHolder) {
		new JavaEclipseLinkConvertersComposite(this, converterHolder, container);
	}

	protected PropertyValueModel<JavaEclipseLinkConverterContainer> buildConverterHolderValueModel() {
		return new PropertyAspectAdapter<JavaEmbeddable, JavaEclipseLinkConverterContainer>(getSubjectHolder()) {
			@Override
			protected JavaEclipseLinkConverterContainer buildValue_() {
				return ((JavaEclipseLinkEmbeddable) this.subject).getConverterContainer();
			}	
		};
	}
	
	protected void initializeAdvancedCollapsibleSection(Composite container) {
		new EclipseLinkEmbeddableAdvancedComposite(this, container);
	}
}