/*******************************************************************************
 *  Copyright (c) 2008, 2010 Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.details.orm;

import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.eclipselink.core.context.orm.EclipseLinkConverterHolder;
import org.eclipse.jpt.eclipselink.core.context.orm.OrmEclipseLinkCaching;
import org.eclipse.jpt.eclipselink.core.context.orm.OrmEclipseLinkEntity;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkEntityAdvancedComposite;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkUiDetailsMessages;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.details.orm.AbstractOrmEntityComposite;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

public abstract class AbstractOrmEclipseLinkEntityComposite extends AbstractOrmEntityComposite
{
	protected AbstractOrmEclipseLinkEntityComposite(
			PropertyValueModel<? extends OrmEntity> subjectHolder,
			Composite parent, WidgetFactory widgetFactory) {
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
				addSubPane(container, 5),
				EclipseLinkUiDetailsMessages.EclipseLinkTypeMappingComposite_caching);
		initializeCachingSection(container, buildCachingHolder());
	}
	
	protected void initializeCachingSection(Composite container, PropertyValueModel<OrmEclipseLinkCaching> cachingHolder) {
		new OrmEclipseLinkCachingComposite(this, cachingHolder, container);
	}

	
	private PropertyAspectAdapter<OrmEntity, OrmEclipseLinkCaching> buildCachingHolder() {
		return new PropertyAspectAdapter<OrmEntity, OrmEclipseLinkCaching>(getSubjectHolder()) {
			@Override
			protected OrmEclipseLinkCaching buildValue_() {
				return ((OrmEclipseLinkEntity) this.subject).getCaching();
			}
		};
	}
	
	protected void initializeConvertersCollapsibleSection(Composite container) {
		container = addCollapsibleSection(
				container,
				EclipseLinkUiDetailsMessages.EclipseLinkTypeMappingComposite_converters);
		this.initializeConvertersSection(container, buildConverterHolder());
	}
	
	protected void initializeConvertersSection(Composite container, PropertyValueModel<EclipseLinkConverterHolder> converterHolder) {
		new OrmEclipseLinkConvertersComposite(this, converterHolder, container);
	}
	
	private PropertyValueModel<EclipseLinkConverterHolder> buildConverterHolder() {
		return new PropertyAspectAdapter<OrmEntity, EclipseLinkConverterHolder>(getSubjectHolder()) {
			@Override
			protected EclipseLinkConverterHolder buildValue_() {
				return ((OrmEclipseLinkEntity) this.subject).getConverterHolder();
			}
		};
	}
	
	protected void initializeAdvancedCollapsibleSection(Composite container) {
		new EclipseLinkEntityAdvancedComposite(this, container);
	}
}
