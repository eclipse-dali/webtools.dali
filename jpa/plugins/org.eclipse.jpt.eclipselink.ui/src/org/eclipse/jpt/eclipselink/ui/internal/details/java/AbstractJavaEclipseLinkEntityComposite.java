/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.details.java;

import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.eclipselink.core.context.java.JavaEclipseLinkCaching;
import org.eclipse.jpt.eclipselink.core.context.java.JavaEclipseLinkConverterHolder;
import org.eclipse.jpt.eclipselink.core.context.java.JavaEclipseLinkEntity;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkEntityAdvancedComposite;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkUiDetailsMessages;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.details.AbstractEntityComposite;
import org.eclipse.jpt.ui.internal.details.java.JavaInheritanceComposite;
import org.eclipse.jpt.ui.internal.details.java.JavaSecondaryTablesComposite;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * The pane used for an EclipseLink Java entity.
 *
 * @see JavaEclipseLinkEntity
 * @see EclipselinkJpaUiFactory - The factory creating this pane
 *
 * @version 2.3
 * @since 2.1
 */
public abstract class AbstractJavaEclipseLinkEntityComposite<T extends JavaEntity>
	extends AbstractEntityComposite<T>
{
	/**
	 * Creates a new <code>EclipseLinkJavaEntityComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>JavaEntity</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	protected AbstractJavaEclipseLinkEntityComposite(
			PropertyValueModel<? extends T> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		
		super(subjectHolder, parent, widgetFactory);
	}
	
	
	@Override
	protected void initializeLayout(Composite container) {
		this.initializeEntityCollapsibleSection(container);
		this.initializeCachingCollapsibleSectionPane(container);
		this.initializeQueriesCollapsibleSection(container);
		this.initializeInheritanceCollapsibleSection(container);
		this.initializeAttributeOverridesCollapsibleSection(container);
		this.initializeGeneratorsCollapsibleSection(container);
		this.initializeConvertersCollapsibleSection(container);
		this.initializeSecondaryTablesCollapsibleSection(container);
		this.initializeAdvancedCollapsibleSection(container);
	}
	
	protected void initializeCachingCollapsibleSectionPane(Composite container) {
		container = addCollapsibleSection(
				container,
				EclipseLinkUiDetailsMessages.EclipseLinkTypeMappingComposite_caching);
		initializeCachingSection(container, buildCachingHolder());
	}
	
	protected void initializeCachingSection(Composite container, PropertyValueModel<JavaEclipseLinkCaching> cachingHolder) {
		new JavaEclipseLinkCachingComposite(this, cachingHolder, container);
	}
	
	private PropertyAspectAdapter<JavaEntity, JavaEclipseLinkCaching> buildCachingHolder() {
		return new PropertyAspectAdapter<JavaEntity, JavaEclipseLinkCaching>(getSubjectHolder()) {
			@Override
			protected JavaEclipseLinkCaching buildValue_() {
				return ((JavaEclipseLinkEntity) this.subject).getCaching();
			}
		};
	}
	
	protected void initializeConvertersCollapsibleSection(Composite container) {
		container = addCollapsibleSection(
				container,
				EclipseLinkUiDetailsMessages.EclipseLinkTypeMappingComposite_converters);
		this.initializeConvertersSection(container, buildConverterHolderValueModel());
	}
	
	protected void initializeConvertersSection(Composite container, PropertyValueModel<JavaEclipseLinkConverterHolder> converterHolder) {
		new JavaEclipseLinkConvertersComposite(this, converterHolder, container);
	}
	
	private PropertyValueModel<JavaEclipseLinkConverterHolder> buildConverterHolderValueModel() {
		return new PropertyAspectAdapter<JavaEntity, JavaEclipseLinkConverterHolder>(getSubjectHolder()) {
			@Override
			protected JavaEclipseLinkConverterHolder buildValue_() {
				return ((JavaEclipseLinkEntity) this.subject).getConverterHolder();
			}	
		};
	}

	@Override
	protected void initializeSecondaryTablesSection(Composite container) {
		new JavaSecondaryTablesComposite(this, container);
	}

	@Override
	protected void initializeInheritanceSection(Composite container) {
		new JavaInheritanceComposite(this, container);
	}

	protected void initializeAdvancedCollapsibleSection(Composite container) {
		new EclipseLinkEntityAdvancedComposite(this, container);
	}
}
