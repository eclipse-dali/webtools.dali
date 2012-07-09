/*******************************************************************************
 *  Copyright (c) 2009, 2012 Oracle. 
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
import org.eclipse.jpt.jpa.core.context.orm.OrmMappedSuperclass;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class OrmEclipseLinkMappedSuperclass2_0Composite
	extends AbstractOrmEclipseLinkMappedSuperclassComposite
{
	public OrmEclipseLinkMappedSuperclass2_0Composite(
			PropertyValueModel<? extends OrmMappedSuperclass> subjectHolder,
			Composite parent, WidgetFactory widgetFactory) {
		
		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected Control initializeCachingSection(Composite container) {
		return new OrmEclipseLinkCaching2_0Composite(this, this.buildCachingHolder(), container).getControl();
	}
}
