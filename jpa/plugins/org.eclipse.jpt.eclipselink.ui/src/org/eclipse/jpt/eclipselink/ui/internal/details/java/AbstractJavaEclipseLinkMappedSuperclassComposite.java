/*******************************************************************************
 * Copyright (c) 2008, 2010, Form Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.details.java;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.core.context.java.JavaMappedSuperclass;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkMappedSuperclass;
import org.eclipse.jpt.eclipselink.core.context.java.JavaEclipseLinkCaching;
import org.eclipse.jpt.eclipselink.core.context.java.JavaEclipseLinkConverterContainer;
import org.eclipse.jpt.eclipselink.core.context.java.JavaEclipseLinkMappedSuperclass;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkMappedSuperclassAdvancedComposite;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkUiDetailsMessages;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.details.AbstractMappedSuperclassComposite;
import org.eclipse.swt.widgets.Composite;

/**
 * The pane used for an EclipseLink Java Mapped Superclass.
 *
 * @see EclipseLinkMappedSuperclass
 * @see EclipselinkJpaUiFactory - The factory creating this pane
 *
 * @version 2.3
 * @since 2.1
 */
public abstract class AbstractJavaEclipseLinkMappedSuperclassComposite
	extends AbstractMappedSuperclassComposite<JavaMappedSuperclass>
    implements JpaComposite
{
	/**
	 * Creates a new <code>MappedSuperclassComposite</code>.
	 *
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	protected AbstractJavaEclipseLinkMappedSuperclassComposite(
		PropertyValueModel<? extends JavaMappedSuperclass> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory) {
		
		super(subjectHolder, parent, widgetFactory);
	}
	
	
	@Override
	protected void initializeLayout(Composite container) {
		this.initializeMappedSuperclassCollapsibleSection(container);		
		this.initializeCachingCollapsibleSection(container);
		this.initializeConvertersCollapsibleSection(container);
		this.initializeAdvancedCollapsibleSection(container);
	}

	
	protected void initializeCachingCollapsibleSection(Composite container) {
		container = addCollapsibleSection(
				container,
				EclipseLinkUiDetailsMessages.EclipseLinkTypeMappingComposite_caching);
		this.initializeCachingSection(container, buildCachingHolder());
	}
	
	protected void initializeCachingSection(Composite container, PropertyValueModel<JavaEclipseLinkCaching> cachingHolder) {
		new JavaEclipseLinkCachingComposite(this, cachingHolder, container);
	}
	
	private PropertyAspectAdapter<JavaMappedSuperclass, JavaEclipseLinkCaching> buildCachingHolder() {
		return new PropertyAspectAdapter<JavaMappedSuperclass, JavaEclipseLinkCaching>(getSubjectHolder()) {
			@Override
			protected JavaEclipseLinkCaching buildValue_() {
				return ((JavaEclipseLinkMappedSuperclass) this.subject).getCaching();
			}
		};
	}
	
	protected void initializeConvertersCollapsibleSection(Composite container) {
		container = addCollapsibleSection(
				container,
				EclipseLinkUiDetailsMessages.EclipseLinkTypeMappingComposite_converters);
		this.initializeConvertersSection(container, buildConverterHolderValueModel());
	}
	
	private PropertyValueModel<JavaEclipseLinkConverterContainer> buildConverterHolderValueModel() {
		return new PropertyAspectAdapter<JavaMappedSuperclass, JavaEclipseLinkConverterContainer>(getSubjectHolder()) {
			@Override
			protected JavaEclipseLinkConverterContainer buildValue_() {
				return ((JavaEclipseLinkMappedSuperclass) this.subject).getConverterContainer();
			}	
		};
	}

	protected void initializeConvertersSection(Composite container, PropertyValueModel<JavaEclipseLinkConverterContainer> converterHolder) {
		new JavaEclipseLinkConvertersComposite(this, converterHolder, container);
	}

	protected void initializeAdvancedCollapsibleSection(Composite container) {
		new EclipseLinkMappedSuperclassAdvancedComposite(this, container);
	}
}
