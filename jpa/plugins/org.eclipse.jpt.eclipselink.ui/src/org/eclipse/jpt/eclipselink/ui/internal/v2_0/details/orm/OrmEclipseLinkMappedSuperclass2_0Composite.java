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
package org.eclipse.jpt.eclipselink.ui.internal.v2_0.details.orm;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.eclipselink.core.context.orm.OrmEclipseLinkCaching;
import org.eclipse.jpt.eclipselink.core.context.orm.OrmEclipseLinkMappedSuperclass;
import org.eclipse.jpt.eclipselink.ui.internal.details.orm.AbstractOrmEclipseLinkMappedSuperclassComposite;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

public class OrmEclipseLinkMappedSuperclass2_0Composite<T extends OrmEclipseLinkMappedSuperclass >
	extends AbstractOrmEclipseLinkMappedSuperclassComposite<T>
{
	public OrmEclipseLinkMappedSuperclass2_0Composite(
			PropertyValueModel<? extends T> subjectHolder,
			Composite parent, WidgetFactory widgetFactory) {
		
		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeCachingSection(Composite container, PropertyValueModel<OrmEclipseLinkCaching> cachingHolder) {
		new OrmEclipseLinkCaching2_0Composite(this, cachingHolder, container);
	}
}
