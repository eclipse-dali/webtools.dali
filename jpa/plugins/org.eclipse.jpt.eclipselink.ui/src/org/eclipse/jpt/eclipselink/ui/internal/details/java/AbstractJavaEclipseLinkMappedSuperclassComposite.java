/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.details.java;

import org.eclipse.jpt.core.context.java.JavaMappedSuperclass;
import org.eclipse.jpt.eclipselink.core.context.java.JavaEclipseLinkConverterHolder;
import org.eclipse.jpt.eclipselink.core.context.java.JavaEclipseLinkMappedSuperclass;
import org.eclipse.jpt.eclipselink.core.context.java.JavaEclipseLinkCaching;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkMappedSuperclassAdvancedComposite;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkUiDetailsMessages;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.details.IdClassComposite;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * The pane used for an EclipseLink Java Mapped Superclass.
 *
 * @see EclipseLinkMappedSuperclass
 * @see EclipselinkJpaUiFactory - The factory creating this pane
 *
 * @version 3.0
 * @since 2.1
 */
public abstract class AbstractJavaEclipseLinkMappedSuperclassComposite extends FormPane<JavaMappedSuperclass>
                                       implements JpaComposite
{
	/**
	 * Creates a new <code>MappedSuperclassComposite</code>.
	 *
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	protected AbstractJavaEclipseLinkMappedSuperclassComposite(PropertyValueModel<? extends JavaMappedSuperclass> subjectHolder,
	                                 Composite parent,
	                                 WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite container) {
		initializeGeneralPane(container);		
		initializeCachingPane(container);
		initializeConvertersPane(container);
		initializeAdvancedPane(container);
	}
	
	protected void initializeGeneralPane(Composite container) {
		new IdClassComposite(this, container);
	}
	
	protected void initializeCachingPane(Composite container) {

		container = addCollapsableSection(
			addSubPane(container, 5),
			EclipseLinkUiDetailsMessages.EclipseLinkTypeMappingComposite_caching
		);
		addCachingComposite(container, buildCachingHolder());
	}
	
	protected void addCachingComposite(Composite container, PropertyValueModel<JavaEclipseLinkCaching> cachingHolder) {
		new JavaEclipseLinkCachingComposite(this, cachingHolder, container);
	}

	private PropertyAspectAdapter<JavaMappedSuperclass, JavaEclipseLinkCaching> buildCachingHolder() {
		return new PropertyAspectAdapter<JavaMappedSuperclass, JavaEclipseLinkCaching>(
			getSubjectHolder())
		{
			@Override
			protected JavaEclipseLinkCaching buildValue_() {
				return ((JavaEclipseLinkMappedSuperclass) this.subject).getCaching();
			}
		};
	}
	
	protected void initializeConvertersPane(Composite container) {
		container = addCollapsableSection(
			container,
			EclipseLinkUiDetailsMessages.EclipseLinkTypeMappingComposite_converters
		);

		new JavaEclipseLinkConvertersComposite(this, buildConverterHolderValueModel(), container);
	}

	protected PropertyValueModel<JavaEclipseLinkConverterHolder> buildConverterHolderValueModel() {
		return new PropertyAspectAdapter<JavaMappedSuperclass, JavaEclipseLinkConverterHolder>(getSubjectHolder()) {
			@Override
			protected JavaEclipseLinkConverterHolder buildValue_() {
				return ((JavaEclipseLinkMappedSuperclass) this.subject).getConverterHolder();
			}	
		};
	}
	
	protected void initializeAdvancedPane(Composite container) {
		new EclipseLinkMappedSuperclassAdvancedComposite(this, container);
	}
}