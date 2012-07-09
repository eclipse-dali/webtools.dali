/*******************************************************************************
 *  Copyright (c) 2011, 2012 Oracle. 
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
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.orm.OrmEntity;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.Entity2_0OverridesComposite;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.Generation2_0Composite;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.Queries2_0Composite;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public abstract class AbstractOrmEclipseLinkEntity2_xComposite extends AbstractOrmEclipseLinkEntityComposite
{
	protected AbstractOrmEclipseLinkEntity2_xComposite(
			PropertyValueModel<? extends OrmEntity> subjectHolder,
			Composite parent, WidgetFactory widgetFactory) {
		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected Control initializeAttributeOverridesSection(Composite container) {
		return new Entity2_0OverridesComposite(this, container).getControl();
	}

	@Override
	protected Control initializeGeneratorsSection(Composite container) {
		return new Generation2_0Composite(this, this.buildGeneratorContainerHolder(), container).getControl();
	}

	@Override
	protected Control initializeCachingSection(Composite container) {
		return new OrmEclipseLinkCaching2_0Composite(this, this.buildCachingHolder(), container).getControl();
	}

	@Override
	protected Control initializeQueriesSection(Composite container) {
		return new Queries2_0Composite(this, this.buildQueryContainerHolder(), container).getControl();
	}

}
