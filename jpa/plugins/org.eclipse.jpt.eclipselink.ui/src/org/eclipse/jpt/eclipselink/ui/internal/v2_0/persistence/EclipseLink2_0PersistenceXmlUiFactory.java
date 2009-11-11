/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.v2_0.persistence;

import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.eclipselink.core.context.persistence.caching.Caching;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.eclipselink.core.v2_0.context.persistence.options.Options2_0;
import org.eclipse.jpt.eclipselink.ui.internal.persistence.EclipseLinkPersistenceXmlUiFactory;
import org.eclipse.jpt.eclipselink.ui.internal.persistence.caching.PersistenceXmlCachingTab;
import org.eclipse.jpt.eclipselink.ui.internal.persistence.options.PersistenceXmlOptionsTab;
import org.eclipse.jpt.eclipselink.ui.internal.v2_0.persistence.caching.PersistenceXmlCaching2_0Tab;
import org.eclipse.jpt.eclipselink.ui.internal.v2_0.persistence.options.PersistenceXmlOptions2_0Tab;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

public class EclipseLink2_0PersistenceXmlUiFactory extends EclipseLinkPersistenceXmlUiFactory
{
	// ********** constructors **********
	
	public EclipseLink2_0PersistenceXmlUiFactory() {
		super();
	}

	// ********** persistence unit tabs **********

	@Override
	protected PersistenceXmlCachingTab<Caching> buildCachingTab(
				PropertyValueModel<EclipseLinkPersistenceUnit> subjectHolder,
				Composite parent,
				WidgetFactory widgetFactory) {
		PropertyValueModel<Caching> cachingHolder = this.buildCachingHolder(subjectHolder);

		return new PersistenceXmlCaching2_0Tab(cachingHolder, parent, widgetFactory);
	}
	
	@Override
	protected PersistenceXmlOptionsTab<Options2_0> buildOptionsTab(
				PropertyValueModel<PersistenceUnit> subjectHolder,
				Composite parent,
				WidgetFactory widgetFactory) {
		PropertyValueModel<Options2_0> options2_0Holder = this.buildOptions2_0Holder(subjectHolder);

		return new PersistenceXmlOptions2_0Tab(options2_0Holder, parent, widgetFactory);
	}

	// ********** private methods **********

	private PropertyValueModel<Options2_0> buildOptions2_0Holder(
				PropertyValueModel<PersistenceUnit> subjectHolder) {
		return new TransformationPropertyValueModel<PersistenceUnit, Options2_0>(subjectHolder) {
			@Override
			protected Options2_0 transform_(PersistenceUnit value) {

				return (Options2_0) ((PersistenceUnit2_0)value).getOptions();
			}
		};
	}
}
