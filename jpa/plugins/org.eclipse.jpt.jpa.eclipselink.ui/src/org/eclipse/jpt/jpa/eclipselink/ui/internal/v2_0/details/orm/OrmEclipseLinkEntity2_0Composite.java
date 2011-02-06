/*******************************************************************************
 *  Copyright (c) 2009, 2010 Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.v2_0.details.orm;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.GeneratorContainer;
import org.eclipse.jpt.jpa.core.context.QueryContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmEntity;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmEclipseLinkCaching;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm.AbstractOrmEclipseLinkEntityComposite;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.Entity2_0OverridesComposite;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.Generation2_0Composite;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.Queries2_0Composite;
import org.eclipse.swt.widgets.Composite;

public class OrmEclipseLinkEntity2_0Composite extends AbstractOrmEclipseLinkEntityComposite
{
	public OrmEclipseLinkEntity2_0Composite(
			PropertyValueModel<? extends OrmEntity> subjectHolder,
			Composite parent, WidgetFactory widgetFactory) {
		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeAttributeOverridesSection(Composite container) {
		new Entity2_0OverridesComposite(this, container);
	}

	@Override
	protected void initializeGeneratorsSection(Composite container, PropertyValueModel<GeneratorContainer> generatorContainerHolder) {
		new Generation2_0Composite(this, generatorContainerHolder, container);
	}

	@Override
	protected void initializeCachingSection(Composite container, PropertyValueModel<OrmEclipseLinkCaching> cachingHolder) {
		new OrmEclipseLinkCaching2_0Composite(this, cachingHolder, container);
	}

	@Override
	protected void initializeQueriesSection(Composite container, PropertyValueModel<QueryContainer> queryContainerHolder) {
		new Queries2_0Composite(this, queryContainerHolder, container);
	}

}
