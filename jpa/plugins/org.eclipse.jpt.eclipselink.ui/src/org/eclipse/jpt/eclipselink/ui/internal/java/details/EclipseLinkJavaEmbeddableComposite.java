/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.java.details;

import org.eclipse.jpt.core.context.java.JavaEmbeddable;
import org.eclipse.jpt.eclipselink.core.context.ChangeTracking;
import org.eclipse.jpt.eclipselink.core.context.Customizer;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkEmbeddable;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaEmbeddable;
import org.eclipse.jpt.eclipselink.core.context.java.JavaConverterHolder;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.EclipseLinkUiMappingsMessages;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.ChangeTrackingComposite;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.CustomizerComposite;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * This pane does not have any widgets.
 *
 * @see Embeddable
 * @see BaseJpaUiFactory - The factory creating this pane
 * @see EmbeddableUiProvider
 *
 * @version 2.1
 * @since 2.1
 */
public class EclipseLinkJavaEmbeddableComposite extends FormPane<JavaEmbeddable>
                                 implements JpaComposite
{
	/**
	 * Creates a new <code>EmbeddableComposite</code>.
	 *
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public EclipseLinkJavaEmbeddableComposite(PropertyValueModel<? extends JavaEmbeddable> subjectHolder,
	                           Composite parent,
	                           WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite container) {
		initializeConvertersPane(container);
		initializeAdvancedPane(container);
	}

	protected void initializeConvertersPane(Composite container) {

		container = addCollapsableSection(
			addSubPane(container, 5),
			EclipseLinkUiMappingsMessages.EclipseLinkJavaEmbeddableComposite_converters
		);

		new ConvertersComposite(this, buildConverterHolderValueModel(), container);
	}

	protected PropertyValueModel<JavaConverterHolder> buildConverterHolderValueModel() {
		return new PropertyAspectAdapter<JavaEmbeddable, JavaConverterHolder>(getSubjectHolder()) {
			@Override
			protected JavaConverterHolder buildValue_() {
				return ((EclipseLinkJavaEmbeddable) this.subject).getConverterHolder();
			}	
		};
	}
	
	
	protected void initializeAdvancedPane(Composite container) {
		container = addCollapsableSection(
			container,
			EclipseLinkUiMappingsMessages.EclipseLinkJavaEntityComposite_advanced
		);
		
		new CustomizerComposite(this, buildCustomizerHolder(), container);
		new ChangeTrackingComposite(this, buildChangeTrackingHolder(), container);
	}
	
	private PropertyValueModel<Customizer> buildCustomizerHolder() {
		return new PropertyAspectAdapter<JavaEmbeddable, Customizer>(getSubjectHolder()) {
			@Override
			protected Customizer buildValue_() {
				return ((EclipseLinkEmbeddable) this.subject).getCustomizer();
			}
		};
	}
	
	private PropertyValueModel<ChangeTracking> buildChangeTrackingHolder() {
		return new PropertyAspectAdapter<JavaEmbeddable, ChangeTracking>(getSubjectHolder()) {
			@Override
			protected ChangeTracking buildValue_() {
				return ((EclipseLinkEmbeddable) this.subject).getChangeTracking();
			}
		};
	}
}