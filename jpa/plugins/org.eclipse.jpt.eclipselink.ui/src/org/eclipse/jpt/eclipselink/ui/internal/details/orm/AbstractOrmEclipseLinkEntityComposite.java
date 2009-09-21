/*******************************************************************************
 *  Copyright (c) 2008, 2009 Oracle. 
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
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCaching;
import org.eclipse.jpt.eclipselink.core.context.orm.EclipseLinkConverterHolder;
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
			EclipseLinkUiDetailsMessages.EclipseLinkTypeMappingComposite_caching);
		new OrmEclipseLinkCachingComposite(this, buildCachingHolder(), container);
	}
	
	private PropertyAspectAdapter<OrmEntity, EclipseLinkCaching> buildCachingHolder() {
		return new PropertyAspectAdapter<OrmEntity, EclipseLinkCaching>(getSubjectHolder()) {
			@Override
			protected EclipseLinkCaching buildValue_() {
				return ((OrmEclipseLinkEntity) this.subject).getCaching();
			}
		};
	}

	protected void initializeConvertersPane(Composite container) {

		container = addCollapsableSection(
			container,
			EclipseLinkUiDetailsMessages.EclipseLinkConvertersComposite_Label
		);

		new OrmEclipseLinkConvertersComposite(this, buildConverterHolder(), container);
	}
	
	private PropertyValueModel<EclipseLinkConverterHolder> buildConverterHolder() {
		return new PropertyAspectAdapter<OrmEntity, EclipseLinkConverterHolder>(getSubjectHolder()) {
			@Override
			protected EclipseLinkConverterHolder buildValue_() {
				return ((OrmEclipseLinkEntity) this.subject).getConverterHolder();
			}
		};
	}
	
	protected void initializeAdvancedPane(Composite container) {
		new EclipseLinkEntityAdvancedComposite(this, container);
	}
}
