/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.AccessHolder;
import org.eclipse.jpt.jpa.core.context.orm.OrmMappedSuperclass;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmEclipseLinkCaching;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmEclipseLinkConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmEclipseLinkMappedSuperclass;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkConvertersComposite;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkMappedSuperclassAdvancedComposite;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractMappedSuperclassComposite;
import org.eclipse.jpt.jpa.ui.internal.details.AccessTypeComposite;
import org.eclipse.jpt.jpa.ui.internal.details.IdClassComposite;
import org.eclipse.jpt.jpa.ui.internal.details.orm.MetadataCompleteComposite;
import org.eclipse.jpt.jpa.ui.internal.details.orm.OrmJavaClassChooser;
import org.eclipse.swt.widgets.Composite;

public abstract class AbstractOrmEclipseLinkMappedSuperclassComposite
	extends AbstractMappedSuperclassComposite<OrmMappedSuperclass> 
	implements JpaComposite
{
	protected AbstractOrmEclipseLinkMappedSuperclassComposite(
			PropertyValueModel<? extends OrmMappedSuperclass> subjectHolder,
			Composite parent, WidgetFactory widgetFactory) {
		
		super(subjectHolder, parent, widgetFactory);
	}
	
	
	@Override
	protected void initializeLayout(Composite container) {
		this.initializeMappedSuperclassCollapsibleSection(container);
		this.initializeCachingCollapsibleSection(container);
		this.initializeConvertersCollapsibleSection(container);
		this.initializeAdvancedCollapsibleSection(container);
	}
	
	@Override
	protected void initializeMappedSuperclassSection(Composite container) {
		new OrmJavaClassChooser(this, getSubjectHolder(), container);
		new AccessTypeComposite(this, buildAccessHolder(), container);
		new IdClassComposite(this, buildIdClassReferenceHolder(), container);
		new MetadataCompleteComposite(this, getSubjectHolder(), container);
	}

	protected void initializeCachingCollapsibleSection(Composite container) {
		container = addCollapsibleSection(
				container,
				EclipseLinkUiDetailsMessages.EclipseLinkTypeMappingComposite_caching);
		this.initializeCachingSection(container, buildCachingHolder());
	}
	
	protected void initializeCachingSection(Composite container, PropertyValueModel<OrmEclipseLinkCaching> cachingHolder) {
		new OrmEclipseLinkCachingComposite(this, cachingHolder, container);
	}
	
	protected PropertyValueModel<AccessHolder> buildAccessHolder() {
		return new PropertyAspectAdapter<OrmMappedSuperclass, AccessHolder>(getSubjectHolder()) {
			@Override
			protected AccessHolder buildValue_() {
				return this.subject.getPersistentType();
			}
		};
	}
	
	private PropertyAspectAdapter<OrmMappedSuperclass, OrmEclipseLinkCaching> buildCachingHolder() {
		return new PropertyAspectAdapter<OrmMappedSuperclass, OrmEclipseLinkCaching>(getSubjectHolder()) {
			@Override
			protected OrmEclipseLinkCaching buildValue_() {
				return ((OrmEclipseLinkMappedSuperclass) this.subject).getCaching();
			}
		};
	}
	
	protected void initializeConvertersCollapsibleSection(Composite container) {
		container = addCollapsibleSection(
				container,
				EclipseLinkUiDetailsMessages.EclipseLinkTypeMappingComposite_converters);
		this.initializeConvertersSection(container, buildConverterContainerModel());
	}
	
	protected void initializeConvertersSection(Composite container, PropertyValueModel<OrmEclipseLinkConverterContainer> converterHolder) {
		new EclipseLinkConvertersComposite(this, converterHolder, container);
	}
	
	private PropertyValueModel<OrmEclipseLinkConverterContainer> buildConverterContainerModel() {
		return new PropertyAspectAdapter<OrmMappedSuperclass, OrmEclipseLinkConverterContainer>(getSubjectHolder()) {
			@Override
			protected OrmEclipseLinkConverterContainer buildValue_() {
				return ((OrmEclipseLinkMappedSuperclass) this.subject).getConverterContainer();
			}
		};
	}
	
	protected void initializeAdvancedCollapsibleSection(Composite container) {
		new EclipseLinkMappedSuperclassAdvancedComposite(this, container);
	}
}
