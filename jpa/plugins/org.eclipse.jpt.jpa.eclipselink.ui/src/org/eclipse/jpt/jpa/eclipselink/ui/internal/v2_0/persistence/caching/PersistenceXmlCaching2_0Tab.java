/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.v2_0.persistence.caching;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.caching.Caching;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.caching.PersistenceXmlCachingTab;
import org.eclipse.swt.widgets.Composite;

/**
 *  PersistenceXmlCaching2_0Tab
 */
public class PersistenceXmlCaching2_0Tab extends PersistenceXmlCachingTab<Caching>
{
	public PersistenceXmlCaching2_0Tab(
			PropertyValueModel<Caching> subjectHolder,
			Composite parent,
            WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	protected void initializeLayout(Composite container) {
		
		new EclipseLinkCaching2_0Composite(this, container);
	}
}
