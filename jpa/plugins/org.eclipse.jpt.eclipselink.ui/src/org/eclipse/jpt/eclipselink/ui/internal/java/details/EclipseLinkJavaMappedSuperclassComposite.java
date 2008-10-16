/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.java.details;

import org.eclipse.jpt.core.context.java.JavaMappedSuperclass;
import org.eclipse.jpt.eclipselink.core.context.ChangeTracking;
import org.eclipse.jpt.eclipselink.core.context.Customizer;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkMappedSuperclass;
import org.eclipse.jpt.eclipselink.core.context.ReadOnly;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaMappedSuperclass;
import org.eclipse.jpt.eclipselink.core.context.java.JavaConverterHolder;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.EclipseLinkUiMappingsMessages;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.ChangeTrackingComposite;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.CustomizerComposite;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.EclipseLinkMappedSuperclassComposite;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.ReadOnlyComposite;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.mappings.details.IdClassComposite;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * The pane used for an EclipseLink Java Mapped Superclass.
 *
 * @see EclipseLinkMappedSuperclass
 * @see EclipselinkJpaUiFactory - The factory creating this pane
 *
 * @version 2.1
 * @since 2.1
 */
public class EclipseLinkJavaMappedSuperclassComposite extends EclipseLinkMappedSuperclassComposite<JavaMappedSuperclass>
                                       implements JpaComposite
{
	/**
	 * Creates a new <code>MappedSuperclassComposite</code>.
	 *
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public EclipseLinkJavaMappedSuperclassComposite(PropertyValueModel<? extends JavaMappedSuperclass> subjectHolder,
	                                 Composite parent,
	                                 WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite container) {
		new IdClassComposite(this, container);
		
		initializeCachingPane(container);
		initializeConvertersPane(container);
		initializeAdvancedPane(container);
	}
	
	protected void initializeConvertersPane(Composite container) {
		container = addCollapsableSection(
			container,
			EclipseLinkUiMappingsMessages.EclipseLinkTypeMappingComposite_converters
		);

		new ConvertersComposite(this, buildConverterHolderValueModel(), container);
	}

	protected PropertyValueModel<JavaConverterHolder> buildConverterHolderValueModel() {
		return new PropertyAspectAdapter<JavaMappedSuperclass, JavaConverterHolder>(getSubjectHolder()) {
			@Override
			protected JavaConverterHolder buildValue_() {
				return ((EclipseLinkJavaMappedSuperclass) this.subject).getConverterHolder();
			}	
		};
	}
	
	protected void initializeAdvancedPane(Composite container) {

		container = addCollapsableSection(
			container,
			EclipseLinkUiMappingsMessages.EclipseLinkTypeMappingComposite_advanced
		);

		
		new ReadOnlyComposite(this, buildReadOnlyHolder(), container);
		new CustomizerComposite(this, buildCustomizerHolder(), container);
		new ChangeTrackingComposite(this, buildChangeTrackingHolder(), container);
	}
	
	private PropertyValueModel<ReadOnly> buildReadOnlyHolder() {
		return new PropertyAspectAdapter<JavaMappedSuperclass, ReadOnly>(getSubjectHolder()) {
			@Override
			protected ReadOnly buildValue_() {
				return ((EclipseLinkMappedSuperclass) this.subject).getReadOnly();
			}
		};
	}
	
	private PropertyValueModel<Customizer> buildCustomizerHolder() {
		return new PropertyAspectAdapter<JavaMappedSuperclass, Customizer>(getSubjectHolder()) {
			@Override
			protected Customizer buildValue_() {
				return ((EclipseLinkMappedSuperclass) this.subject).getCustomizer();
			}
		};
	}
	
	private PropertyValueModel<ChangeTracking> buildChangeTrackingHolder() {
		return new PropertyAspectAdapter<JavaMappedSuperclass, ChangeTracking>(getSubjectHolder()) {
			@Override
			protected ChangeTracking buildValue_() {
				return ((EclipseLinkMappedSuperclass) this.subject).getChangeTracking();
			}
		};
	}
}