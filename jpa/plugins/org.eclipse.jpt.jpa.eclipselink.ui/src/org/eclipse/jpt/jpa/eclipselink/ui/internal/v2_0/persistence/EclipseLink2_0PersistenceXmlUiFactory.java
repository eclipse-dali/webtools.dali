/*******************************************************************************
* Copyright (c) 2009, 2011 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.v2_0.persistence;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.caching.Caching;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.customization.Customization;
import org.eclipse.jpt.jpa.eclipselink.core.v2_0.context.persistence.logging.Logging2_0;
import org.eclipse.jpt.jpa.eclipselink.core.v2_0.context.persistence.options.Options2_0;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.EclipseLinkPersistenceXmlUiFactory;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.caching.PersistenceXmlCachingTab;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.customization.PersistenceXmlCustomizationTab;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.logging.PersistenceXmlLoggingTab;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.options.PersistenceXmlOptionsTab;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.v2_0.persistence.caching.PersistenceXmlCaching2_0Tab;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.v2_0.persistence.customization.PersistenceXmlCustomization2_0Tab;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.v2_0.persistence.logging.PersistenceXmlLogging2_0Tab;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.v2_0.persistence.options.PersistenceXmlOptions2_0Tab;
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
				PropertyValueModel<EclipseLinkPersistenceUnit> subjectHolder,
				Composite parent,
				WidgetFactory widgetFactory) {
		PropertyValueModel<Customization> customizationHolder = this.buildCustomizationHolder(subjectHolder);

		return new PersistenceXmlCustomization2_0Tab(customizationHolder, parent, widgetFactory);
	}

	@Override
	protected PersistenceXmlCachingTab<Caching> buildCachingTab(
				PropertyValueModel<EclipseLinkPersistenceUnit> subjectHolder,
				Composite parent,
				WidgetFactory widgetFactory) {
		PropertyValueModel<Caching> cachingHolder = this.buildCachingHolder(subjectHolder);

		return new PersistenceXmlCaching2_0Tab(cachingHolder, parent, widgetFactory);
	}

	@Override
	protected PersistenceXmlLoggingTab<? extends Logging2_0> buildLoggingTab(
				PropertyValueModel<EclipseLinkPersistenceUnit> subjectHolder,
				Composite parent,
				WidgetFactory widgetFactory) {
		PropertyValueModel<Logging2_0> logging2_0Holder = this.buildLogging2_0Holder(subjectHolder);

		return new PersistenceXmlLogging2_0Tab(logging2_0Holder, parent, widgetFactory);
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

	private PropertyValueModel<Logging2_0> buildLogging2_0Holder(
				PropertyValueModel<EclipseLinkPersistenceUnit> subjectHolder) {
		return new TransformationPropertyValueModel<EclipseLinkPersistenceUnit, Logging2_0>(subjectHolder) {
			@Override
			protected Logging2_0 transform_(EclipseLinkPersistenceUnit value) {

				return (Logging2_0) value.getLogging();
			}
		};
	}

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
