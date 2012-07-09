/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence;

import java.util.ArrayList;
import java.util.ListIterator;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.Caching;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.Connection;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.Customization;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.caching.PersistenceXmlCachingTab;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.connection.PersistenceXmlConnectionTab;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.customization.PersistenceXmlCustomizationTab;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.general.EclipseLinkPersistenceUnitGeneralTab;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.options.PersistenceXmlOptionsTab;
import org.eclipse.jpt.jpa.ui.editors.JpaPageComposite;
import org.eclipse.jpt.jpa.ui.internal.persistence.PersistenceUnitPropertiesTab;
import org.eclipse.jpt.jpa.ui.internal.persistence.PersistenceXmlUiFactory;
import org.eclipse.swt.widgets.Composite;

public class EclipseLinkPersistenceXmlUiFactory implements PersistenceXmlUiFactory
{
	// ********** constructors **********
	
	public EclipseLinkPersistenceXmlUiFactory() {
		super();
	}

	// **************** persistence unit composites ****************************
	
	public ListIterator<JpaPageComposite> createPersistenceUnitComposites(
						PropertyValueModel<PersistenceUnit> subjectHolder,
						Composite parent,
						WidgetFactory widgetFactory) {

		ArrayList<JpaPageComposite> pages = new ArrayList<JpaPageComposite>(8);

		pages.add(this.buildGeneralTab(subjectHolder, parent, widgetFactory));
		pages.add(this.buildConnectionTab(subjectHolder, parent, widgetFactory));
		pages.add(this.buildCustomizationTab(subjectHolder, parent, widgetFactory));
		pages.add(this.buildCachingTab(subjectHolder, parent, widgetFactory));
		pages.add(this.buildOptionsTab(subjectHolder, parent, widgetFactory));
		pages.add(this.buildPropertiesTab(subjectHolder, parent, widgetFactory));

		return pages.listIterator();
	}

	// ********** persistence unit tabs **********
	
	protected EclipseLinkPersistenceUnitGeneralTab buildGeneralTab(
				PropertyValueModel<PersistenceUnit> subjectHolder,
				Composite parent,
				WidgetFactory widgetFactory) {

		return new EclipseLinkPersistenceUnitGeneralTab(subjectHolder, parent, widgetFactory);
	}
	
	protected PersistenceXmlConnectionTab<? extends Connection> buildConnectionTab(
				PropertyValueModel<PersistenceUnit> subjectHolder,
				Composite parent,
				WidgetFactory widgetFactory) {

		return new PersistenceXmlConnectionTab<Connection>( this.buildConnectionHolder(subjectHolder), parent, widgetFactory);
	}
	
	protected PersistenceXmlCustomizationTab<? extends Customization> buildCustomizationTab(
				PropertyValueModel<PersistenceUnit> subjectHolder,
				Composite parent,
				WidgetFactory widgetFactory) {

		return new PersistenceXmlCustomizationTab<Customization>(this.buildCustomizationHolder(subjectHolder), parent, widgetFactory);
	}
	
	protected PersistenceXmlCachingTab<? extends Caching> buildCachingTab(
				PropertyValueModel<PersistenceUnit> subjectHolder,
				Composite parent,
				WidgetFactory widgetFactory) {
		PropertyValueModel<Caching> cachingHolder = this.buildCachingHolder(subjectHolder);

		return new PersistenceXmlCachingTab<Caching>(cachingHolder, parent, widgetFactory);
	}
	
	protected PersistenceXmlOptionsTab<? extends PersistenceUnit> buildOptionsTab(
				PropertyValueModel<PersistenceUnit> subjectHolder,
				Composite parent,
				WidgetFactory widgetFactory) {

		return new PersistenceXmlOptionsTab<PersistenceUnit>(subjectHolder, parent, widgetFactory);
	}
	
	protected PersistenceUnitPropertiesTab buildPropertiesTab(
				PropertyValueModel<PersistenceUnit> subjectHolder,
				Composite parent,
				WidgetFactory widgetFactory) {

		return new PersistenceUnitPropertiesTab(subjectHolder, parent, widgetFactory);
	}

	// ********** private methods **********
	
	private PropertyValueModel<Connection> buildConnectionHolder(
				PropertyValueModel<PersistenceUnit> subjectHolder) {
		return new TransformationPropertyValueModel<PersistenceUnit, Connection>(subjectHolder) {
			@Override
			protected Connection transform_(PersistenceUnit value) {

				return ((EclipseLinkPersistenceUnit) value).getConnection();
			}
		};
	}
	
	protected PropertyValueModel<Customization> buildCustomizationHolder(
				PropertyValueModel<PersistenceUnit> subjectHolder) {
		return new TransformationPropertyValueModel<PersistenceUnit, Customization>(subjectHolder) {
			@Override
			protected Customization transform_(PersistenceUnit value) {
				return ((EclipseLinkPersistenceUnit) value).getCustomization();
			}
		};
	}
	
	protected PropertyValueModel<Caching> buildCachingHolder(
				PropertyValueModel<PersistenceUnit> subjectHolder) {
		return new TransformationPropertyValueModel<PersistenceUnit, Caching>(subjectHolder) {
			@Override
			protected Caching transform_(PersistenceUnit value) {
				return ((EclipseLinkPersistenceUnit) value).getCaching();
			}
		};
	}
}