/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.caching.Caching;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.connection.Connection;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.customization.Customization;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.logging.Logging;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.options.Options;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.schema.generation.SchemaGeneration;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.caching.PersistenceXmlCachingTab;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.connection.PersistenceXmlConnectionTab;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.customization.PersistenceXmlCustomizationTab;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.general.EclipseLinkPersistenceUnitGeneralComposite;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.logging.PersistenceXmlLoggingTab;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.options.PersistenceXmlOptionsTab;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.schema.generation.PersistenceXmlSchemaGenerationTab;
import org.eclipse.jpt.jpa.ui.details.JpaPageComposite;
import org.eclipse.jpt.jpa.ui.internal.persistence.details.PersistenceUnitPropertiesComposite;
import org.eclipse.jpt.jpa.ui.internal.persistence.details.PersistenceXmlUiFactory;
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

		PropertyValueModel<EclipseLinkPersistenceUnit> eclipseLinkPersistenceUnitHolder = 
			this.buildEclipseLinkPersistenceUnitHolder(subjectHolder);

		pages.add(this.buildGeneralTab(subjectHolder, parent, widgetFactory));
		pages.add(this.buildConnectionTab(subjectHolder, parent, widgetFactory));
		pages.add(this.buildCustomizationTab(eclipseLinkPersistenceUnitHolder, parent, widgetFactory));
		pages.add(this.buildCachingTab(eclipseLinkPersistenceUnitHolder, parent, widgetFactory));
		pages.add(this.buildLoggingTab(eclipseLinkPersistenceUnitHolder, parent, widgetFactory));
		pages.add(this.buildOptionsTab(subjectHolder, parent, widgetFactory));
		pages.add(this.buildSchemaGenerationTab(eclipseLinkPersistenceUnitHolder, parent, widgetFactory));
		pages.add(this.buildPropertiesTab(subjectHolder, parent, widgetFactory));

		return pages.listIterator();
	}

	// ********** persistence unit tabs **********
	
	protected EclipseLinkPersistenceUnitGeneralComposite buildGeneralTab(
				PropertyValueModel<PersistenceUnit> subjectHolder,
				Composite parent,
				WidgetFactory widgetFactory) {

		return new EclipseLinkPersistenceUnitGeneralComposite(subjectHolder, parent, widgetFactory);
	}
	
	protected PersistenceXmlConnectionTab<? extends Connection> buildConnectionTab(
				PropertyValueModel<PersistenceUnit> subjectHolder,
				Composite parent,
				WidgetFactory widgetFactory) {
		PropertyValueModel<Connection> connectionHolder = this.buildConnectionHolder(subjectHolder);

		return new PersistenceXmlConnectionTab<Connection>(connectionHolder, parent, widgetFactory);
	}
	
	protected PersistenceXmlCustomizationTab<? extends Customization> buildCustomizationTab(
				PropertyValueModel<EclipseLinkPersistenceUnit> subjectHolder,
				Composite parent,
				WidgetFactory widgetFactory) {
		PropertyValueModel<Customization> customizationHolder = this.buildCustomizationHolder(subjectHolder);

		return new PersistenceXmlCustomizationTab<Customization>(customizationHolder, parent, widgetFactory);
	}
	
	protected PersistenceXmlCachingTab<? extends Caching> buildCachingTab(
				PropertyValueModel<EclipseLinkPersistenceUnit> subjectHolder,
				Composite parent,
				WidgetFactory widgetFactory) {
		PropertyValueModel<Caching> cachingHolder = this.buildCachingHolder(subjectHolder);

		return new PersistenceXmlCachingTab<Caching>(cachingHolder, parent, widgetFactory);
	}
	
	protected PersistenceXmlLoggingTab<? extends Logging> buildLoggingTab(
				PropertyValueModel<EclipseLinkPersistenceUnit> subjectHolder,
				Composite parent,
				WidgetFactory widgetFactory) {
		PropertyValueModel<Logging> loggingHolder = this.buildLoggingHolder(subjectHolder);

		return new PersistenceXmlLoggingTab<Logging>(loggingHolder, parent, widgetFactory);
	}
	
	protected PersistenceXmlOptionsTab<? extends Options> buildOptionsTab(
				PropertyValueModel<PersistenceUnit> subjectHolder,
				Composite parent,
				WidgetFactory widgetFactory) {
		PropertyValueModel<Options> optionsHolder = this.buildOptionsHolder(subjectHolder);

		return new PersistenceXmlOptionsTab<Options>(optionsHolder, parent, widgetFactory);
	}
	
	protected PersistenceXmlSchemaGenerationTab buildSchemaGenerationTab(
				PropertyValueModel<EclipseLinkPersistenceUnit> subjectHolder,
				Composite parent,
				WidgetFactory widgetFactory) {
		PropertyValueModel<SchemaGeneration> schemaGenHolder = this.buildSchemaGenerationHolder(subjectHolder);

		return new PersistenceXmlSchemaGenerationTab(schemaGenHolder, parent, widgetFactory);
	}
	
	protected PersistenceUnitPropertiesComposite buildPropertiesTab(
				PropertyValueModel<PersistenceUnit> subjectHolder,
				Composite parent,
				WidgetFactory widgetFactory) {

		return new PersistenceUnitPropertiesComposite(subjectHolder, parent, widgetFactory);
	}

	// ********** private methods **********
	
	private PropertyValueModel<EclipseLinkPersistenceUnit> buildEclipseLinkPersistenceUnitHolder(
				PropertyValueModel<PersistenceUnit> subjectHolder) {
		return new TransformationPropertyValueModel<PersistenceUnit, EclipseLinkPersistenceUnit>(subjectHolder) {
			@Override
			protected EclipseLinkPersistenceUnit transform_(PersistenceUnit value) {
				return (EclipseLinkPersistenceUnit) value;
			}
		};
	}
	
	private PropertyValueModel<Connection> buildConnectionHolder(
				PropertyValueModel<PersistenceUnit> subjectHolder) {
		return new TransformationPropertyValueModel<PersistenceUnit, Connection>(subjectHolder) {
			@Override
			protected Connection transform_(PersistenceUnit value) {

				return ((EclipseLinkPersistenceUnit)value).getConnection();
			}
		};
	}
	
	protected PropertyValueModel<Customization> buildCustomizationHolder(
				PropertyValueModel<EclipseLinkPersistenceUnit> subjectHolder) {
		return new TransformationPropertyValueModel<EclipseLinkPersistenceUnit, Customization>(subjectHolder) {
			@Override
			protected Customization transform_(EclipseLinkPersistenceUnit value) {
				return value.getCustomization();
			}
		};
	}
	
	protected PropertyValueModel<Caching> buildCachingHolder(
				PropertyValueModel<EclipseLinkPersistenceUnit> subjectHolder) {
		return new TransformationPropertyValueModel<EclipseLinkPersistenceUnit, Caching>(subjectHolder) {
			@Override
			protected Caching transform_(EclipseLinkPersistenceUnit value) {
				return value.getCaching();
			}
		};
	}
	
	private PropertyValueModel<Logging> buildLoggingHolder(
				PropertyValueModel<EclipseLinkPersistenceUnit> subjectHolder) {
		return new TransformationPropertyValueModel<EclipseLinkPersistenceUnit, Logging>(subjectHolder) {
			@Override
			protected Logging transform_(EclipseLinkPersistenceUnit value) {
				return value.getLogging();
			}
		};
	}
	
	private PropertyValueModel<Options> buildOptionsHolder(
				PropertyValueModel<PersistenceUnit> subjectHolder) {
		return new TransformationPropertyValueModel<PersistenceUnit, Options>(subjectHolder) {
			@Override
			protected Options transform_(PersistenceUnit value) {

				return ((EclipseLinkPersistenceUnit)value).getOptions();
			}
		};
	}
	
	private PropertyValueModel<SchemaGeneration> buildSchemaGenerationHolder(
				PropertyValueModel<EclipseLinkPersistenceUnit> subjectHolder) {
		return new TransformationPropertyValueModel<EclipseLinkPersistenceUnit, SchemaGeneration>(subjectHolder) {
			@Override
			protected SchemaGeneration transform_(EclipseLinkPersistenceUnit value) {
				return value.getSchemaGeneration();
			}
		};
	}
}