/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.orm.details;

import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.eclipselink.core.context.Caching;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.ConverterHolder;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmEntity;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.EclipseLinkUiMappingsMessages;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.EclipseLinkEntityAdvancedComposite;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.mappings.details.AbstractEntityComposite;
import org.eclipse.jpt.ui.internal.orm.details.OrmInheritanceComposite;
import org.eclipse.jpt.ui.internal.orm.details.OrmSecondaryTablesComposite;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

public class EclipseLinkOrmEntityComposite extends AbstractEntityComposite<OrmEntity>
{
	public EclipseLinkOrmEntityComposite(
			PropertyValueModel<? extends OrmEntity> subjectHolder,
			Composite parent, WidgetFactory widgetFactory) {
		super(subjectHolder, parent, widgetFactory);
	}

	
	@Override
	protected void initializeLayout(Composite container) {
		initializeGeneralPane(container);
		initializeCachingPane(container);
		initializeQueriesPane(container);
		initializeInheritancePane(container);
		initializeAttributeOverridesPane(container);
		initializeGeneratorsPane(container);
		initializeConvertersPane(container);
		initializeSecondaryTablesPane(container);
		initializeAdvancedPane(container);
	}
	
	protected void initializeCachingPane(Composite container) {
		container = addCollapsableSection(
			addSubPane(container, 5),
			EclipseLinkUiMappingsMessages.EclipseLinkTypeMappingComposite_caching);
		new OrmCachingComposite(this, buildCachingHolder(), container);
	}
	
	private PropertyAspectAdapter<OrmEntity, Caching> buildCachingHolder() {
		return new PropertyAspectAdapter<OrmEntity, Caching>(getSubjectHolder()) {
			@Override
			protected Caching buildValue_() {
				return ((EclipseLinkOrmEntity) this.subject).getCaching();
			}
		};
	}
	
	@Override
	protected void addInheritanceComposite(Composite container) {
		new OrmInheritanceComposite(this, container);
	}
	
	@Override
	protected void addSecondaryTablesComposite(Composite container) {
		new OrmSecondaryTablesComposite(this, container);
	}

	protected void initializeConvertersPane(Composite container) {

		container = addCollapsableSection(
			container,
			EclipseLinkUiMappingsMessages.ConvertersComposite_Label
		);

		new ConvertersComposite(this, buildConverterHolder(), container);
	}
	
	private PropertyValueModel<ConverterHolder> buildConverterHolder() {
		return new PropertyAspectAdapter<OrmEntity, ConverterHolder>(getSubjectHolder()) {
			@Override
			protected ConverterHolder buildValue_() {
				return ((EclipseLinkOrmEntity) this.subject).getConverterHolder();
			}
		};
	}
	
	protected void initializeAdvancedPane(Composite container) {
		new EclipseLinkEntityAdvancedComposite(this, container);
	}
}
