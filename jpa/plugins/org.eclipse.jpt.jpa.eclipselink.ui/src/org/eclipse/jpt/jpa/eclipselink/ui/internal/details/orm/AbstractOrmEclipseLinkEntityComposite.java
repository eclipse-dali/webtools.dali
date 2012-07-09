/*******************************************************************************
 *  Copyright (c) 2008, 2011 Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.orm.OrmEntity;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmEclipseLinkCaching;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmEclipseLinkConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmEclipseLinkEntity;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkConvertersComposite;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkEntityAdvancedComposite;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.details.orm.AbstractOrmEntityComposite;
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
				container,
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
		this.initializeConvertersSection(container, buildConverterContainerModel());
	}
	
	protected void initializeConvertersSection(Composite container, PropertyValueModel<OrmEclipseLinkConverterContainer> converterHolder) {
		new EclipseLinkConvertersComposite(this, converterHolder, container);
	}
	
	private PropertyValueModel<OrmEclipseLinkConverterContainer> buildConverterContainerModel() {
		return new PropertyAspectAdapter<OrmEntity, OrmEclipseLinkConverterContainer>(getSubjectHolder()) {
			@Override
			protected OrmEclipseLinkConverterContainer buildValue_() {
				return ((OrmEclipseLinkEntity) this.subject).getConverterContainer();
			}
		};
	}
	
	protected void initializeAdvancedCollapsibleSection(Composite container) {
		new EclipseLinkEntityAdvancedComposite(this, container);
	}
}