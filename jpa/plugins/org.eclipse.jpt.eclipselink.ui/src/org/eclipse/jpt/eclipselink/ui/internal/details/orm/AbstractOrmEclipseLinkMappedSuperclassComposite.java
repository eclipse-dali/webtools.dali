/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.details.orm;

import org.eclipse.jpt.core.context.AccessHolder;
import org.eclipse.jpt.core.context.IdClassReference;
import org.eclipse.jpt.core.context.MappedSuperclass;
import org.eclipse.jpt.eclipselink.core.context.orm.EclipseLinkConverterHolder;
import org.eclipse.jpt.eclipselink.core.context.orm.OrmEclipseLinkCaching;
import org.eclipse.jpt.eclipselink.core.context.orm.OrmEclipseLinkMappedSuperclass;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkMappedSuperclassAdvancedComposite;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkUiDetailsMessages;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.details.AccessTypeComposite;
import org.eclipse.jpt.ui.internal.details.IdClassComposite;
import org.eclipse.jpt.ui.internal.details.orm.MetadataCompleteComposite;
import org.eclipse.jpt.ui.internal.details.orm.OrmJavaClassChooser;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

public abstract class AbstractOrmEclipseLinkMappedSuperclassComposite<T extends OrmEclipseLinkMappedSuperclass> 
	extends Pane<T> 
	implements JpaComposite
{
	protected AbstractOrmEclipseLinkMappedSuperclassComposite(
			PropertyValueModel<? extends T> subjectHolder,
			Composite parent, WidgetFactory widgetFactory) {
		
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
		new OrmJavaClassChooser(this, getSubjectHolder(), container);
		new AccessTypeComposite(this, buildAccessHolder(), container);
		new IdClassComposite(this, buildIdClassReferenceHolder(), container);
		new MetadataCompleteComposite(this, getSubjectHolder(), container);
	}
	
	protected PropertyValueModel<AccessHolder> buildAccessHolder() {
		return new PropertyAspectAdapter<T, AccessHolder>(getSubjectHolder()) {
			@Override
			protected AccessHolder buildValue_() {
				return this.subject.getPersistentType();
			}
		};
	}
	
	protected PropertyValueModel<IdClassReference> buildIdClassReferenceHolder() {
		return new PropertyAspectAdapter<MappedSuperclass, IdClassReference>(getSubjectHolder()) {
			@Override
			protected IdClassReference buildValue_() {
				return this.subject.getIdClassReference();
			}
		};
	}
	
	protected void initializeCachingPane(Composite container) {
		container = addCollapsibleSection(
				addSubPane(container, 5),
				EclipseLinkUiDetailsMessages.EclipseLinkTypeMappingComposite_caching);
		addCachingComposite(container, buildCachingHolder());
	}
	
	protected void addCachingComposite(Composite container, PropertyValueModel<OrmEclipseLinkCaching> cachingHolder) {
		new OrmEclipseLinkCachingComposite(this, cachingHolder, container);
	}
	
	private PropertyAspectAdapter<T, OrmEclipseLinkCaching> buildCachingHolder() {
		return new PropertyAspectAdapter<T, OrmEclipseLinkCaching>(getSubjectHolder()) {
			@Override
			protected OrmEclipseLinkCaching buildValue_() {
				return this.subject.getCaching();
			}
		};
	}
	
	protected void initializeConvertersPane(Composite container) {
		container = addCollapsibleSection(
				container,
				EclipseLinkUiDetailsMessages.EclipseLinkConvertersComposite_Label);
		new OrmEclipseLinkConvertersComposite(this, buildConverterHolder(), container);
	}
	
	private PropertyValueModel<EclipseLinkConverterHolder> buildConverterHolder() {
		return new PropertyAspectAdapter<T, EclipseLinkConverterHolder>(getSubjectHolder()) {
			@Override
			protected EclipseLinkConverterHolder buildValue_() {
				return this.subject.getConverterHolder();
			}
		};
	}
	
	protected void initializeAdvancedPane(Composite container) {
		new EclipseLinkMappedSuperclassAdvancedComposite(this, container);
	}
}
