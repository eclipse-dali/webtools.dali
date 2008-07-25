/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.platform;

import java.util.ArrayList;
import java.util.ListIterator;

import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.eclipselink.core.internal.context.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.eclipselink.core.internal.context.caching.Caching;
import org.eclipse.jpt.eclipselink.core.internal.context.connection.Connection;
import org.eclipse.jpt.eclipselink.core.internal.context.customization.Customization;
import org.eclipse.jpt.eclipselink.core.internal.context.logging.Logging;
import org.eclipse.jpt.eclipselink.core.internal.context.options.Options;
import org.eclipse.jpt.eclipselink.core.internal.context.schema.generation.SchemaGeneration;
import org.eclipse.jpt.eclipselink.ui.internal.caching.PersistenceXmlCachingTab;
import org.eclipse.jpt.eclipselink.ui.internal.connection.PersistenceXmlConnectionTab;
import org.eclipse.jpt.eclipselink.ui.internal.customization.PersistenceXmlCustomizationTab;
import org.eclipse.jpt.eclipselink.ui.internal.logging.PersistenceXmlLoggingTab;
import org.eclipse.jpt.eclipselink.ui.internal.options.PersistenceXmlOptionsTab;
import org.eclipse.jpt.eclipselink.ui.internal.schema.generation.PersistenceXmlSchemaGenerationTab;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaPageComposite;
import org.eclipse.jpt.ui.internal.BaseJpaUiFactory;
import org.eclipse.jpt.ui.internal.persistence.details.PersistenceUnitGeneralComposite;
import org.eclipse.jpt.ui.internal.persistence.details.PersistenceUnitPropertiesComposite;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * EclipseLinkUiFactory
 */
public class EclipseLinkUiFactory extends BaseJpaUiFactory
{
	public EclipseLinkUiFactory() {
		super();
	}
	
	@Override
	public ListIterator<JpaPageComposite> createPersistenceUnitComposites(
						PropertyValueModel<PersistenceUnit> subjectHolder,
						Composite parent,
						WidgetFactory widgetFactory) {
		
		PropertyValueModel<EclipseLinkPersistenceUnit> eclipseLinkPersistenceUnitHolder = 
			this.buildEclipseLinkPersistenceUnitHolder(subjectHolder);
		ArrayList<JpaPageComposite> pages = 
			new ArrayList<JpaPageComposite>(8);

		pages.add(new PersistenceUnitGeneralComposite(subjectHolder, parent, widgetFactory));
		
		PropertyValueModel<Connection> connectionHolder = 
			this.buildConnectionHolder(eclipseLinkPersistenceUnitHolder);
		pages.add(new PersistenceXmlConnectionTab(connectionHolder, parent, widgetFactory));
		
		PropertyValueModel<Customization> customizationHolder = 
			this.buildCustomizationHolder(eclipseLinkPersistenceUnitHolder);
		pages.add(new PersistenceXmlCustomizationTab(customizationHolder, parent, widgetFactory));
		
		PropertyValueModel<Caching> cachingHolder = 
			this.buildCachingHolder(eclipseLinkPersistenceUnitHolder);
		pages.add(new PersistenceXmlCachingTab(cachingHolder, parent, widgetFactory));
		
		PropertyValueModel<Logging> loggingHolder = 
			this.buildLoggingHolder(eclipseLinkPersistenceUnitHolder);
		pages.add(new PersistenceXmlLoggingTab(loggingHolder, parent, widgetFactory));

		PropertyValueModel<Options> optionsHolder = 
			this.buildOptionsHolder(eclipseLinkPersistenceUnitHolder);
		pages.add(new PersistenceXmlOptionsTab(optionsHolder, parent, widgetFactory));
		
		PropertyValueModel<SchemaGeneration> schemaGenHolder = 
			this.buildSchemaGenerationHolder(eclipseLinkPersistenceUnitHolder);
		pages.add(new PersistenceXmlSchemaGenerationTab(schemaGenHolder, parent, widgetFactory));
		
		pages.add(new PersistenceUnitPropertiesComposite(subjectHolder, parent, widgetFactory));
		
		return pages.listIterator();
	}

	private PropertyValueModel<Connection> buildConnectionHolder(
				PropertyValueModel<EclipseLinkPersistenceUnit> subjectHolder) {
		return new TransformationPropertyValueModel<EclipseLinkPersistenceUnit, Connection>(subjectHolder) {
			@Override
			protected Connection transform_(EclipseLinkPersistenceUnit value) {
				return value.getConnection();
			}
		};
	}

	private PropertyValueModel<Options> buildOptionsHolder(
				PropertyValueModel<EclipseLinkPersistenceUnit> subjectHolder) {
		return new TransformationPropertyValueModel<EclipseLinkPersistenceUnit, Options>(subjectHolder) {
			@Override
			protected Options transform_(EclipseLinkPersistenceUnit value) {
				return value.getOptions();
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

	private PropertyValueModel<Customization> buildCustomizationHolder(
				PropertyValueModel<EclipseLinkPersistenceUnit> subjectHolder) {
		return new TransformationPropertyValueModel<EclipseLinkPersistenceUnit, Customization>(subjectHolder) {
			@Override
			protected Customization transform_(EclipseLinkPersistenceUnit value) {
				return value.getCustomization();
			}
		};
	}

	private PropertyValueModel<Caching> buildCachingHolder(
				PropertyValueModel<EclipseLinkPersistenceUnit> subjectHolder) {
		return new TransformationPropertyValueModel<EclipseLinkPersistenceUnit, Caching>(subjectHolder) {
			@Override
			protected Caching transform_(EclipseLinkPersistenceUnit value) {
				return value.getCaching();
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

	private PropertyValueModel<EclipseLinkPersistenceUnit> buildEclipseLinkPersistenceUnitHolder(
				PropertyValueModel<PersistenceUnit> subjectHolder) {
		return new TransformationPropertyValueModel<PersistenceUnit, EclipseLinkPersistenceUnit>(subjectHolder) {
			@Override
			protected EclipseLinkPersistenceUnit transform_(PersistenceUnit value) {
				return (EclipseLinkPersistenceUnit) value;
			}
		};
	}
}
