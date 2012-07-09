/*******************************************************************************
* Copyright (c) 2009, 2012 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.v2_0.persistence;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.Caching;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.Customization;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.EclipseLinkPersistenceXmlUiFactory;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.caching.PersistenceXmlCachingTab;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.customization.PersistenceXmlCustomizationTab;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.options.PersistenceXmlOptionsTab;
import org.eclipse.swt.widgets.Composite;

public class EclipseLink2_0PersistenceXmlUiFactory extends EclipseLinkPersistenceXmlUiFactory
{
	// ********** constructors **********
	
	public EclipseLink2_0PersistenceXmlUiFactory() {
		super();
	}

	// ********** persistence unit tabs **********

	@Override
	protected PersistenceXmlCustomizationTab<Customization> buildCustomizationTab(
				PropertyValueModel<PersistenceUnit> subjectHolder,
				Composite parent,
				WidgetFactory widgetFactory) {
		return new PersistenceXmlCustomization2_0Tab(this.buildCustomizationHolder(subjectHolder), parent, widgetFactory);
	}

	@Override
	protected PersistenceXmlCachingTab<Caching> buildCachingTab(
				PropertyValueModel<PersistenceUnit> subjectHolder,
				Composite parent,
				WidgetFactory widgetFactory) {
		return new PersistenceXmlCaching2_0Tab(this.buildCachingHolder(subjectHolder), parent, widgetFactory);
	}
	
	@Override
	protected PersistenceXmlOptionsTab<PersistenceUnit> buildOptionsTab(
				PropertyValueModel<PersistenceUnit> subjectHolder,
				Composite parent,
				WidgetFactory widgetFactory) {
		return new PersistenceXmlOptions2_0Tab(subjectHolder, parent, widgetFactory);
	}
}
