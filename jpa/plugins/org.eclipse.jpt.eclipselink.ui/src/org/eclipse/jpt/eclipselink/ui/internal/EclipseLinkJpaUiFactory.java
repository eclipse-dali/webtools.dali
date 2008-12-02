/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal;

import java.util.ArrayList;
import java.util.ListIterator;
import org.eclipse.jpt.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.core.context.java.JavaEmbeddable;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.context.java.JavaIdMapping;
import org.eclipse.jpt.core.context.java.JavaManyToManyMapping;
import org.eclipse.jpt.core.context.java.JavaManyToOneMapping;
import org.eclipse.jpt.core.context.java.JavaMappedSuperclass;
import org.eclipse.jpt.core.context.java.JavaOneToManyMapping;
import org.eclipse.jpt.core.context.java.JavaOneToOneMapping;
import org.eclipse.jpt.core.context.java.JavaVersionMapping;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.caching.Caching;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.connection.Connection;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.customization.Customization;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.logging.Logging;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.options.Options;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.schema.generation.SchemaGeneration;
import org.eclipse.jpt.eclipselink.ui.internal.java.details.EclipseLinkJavaEmbeddableComposite;
import org.eclipse.jpt.eclipselink.ui.internal.java.details.EclipseLinkJavaEntityComposite;
import org.eclipse.jpt.eclipselink.ui.internal.java.details.EclipseLinkJavaMappedSuperclassComposite;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.EclipseLinkBasicMappingComposite;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.EclipseLinkIdMappingComposite;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.EclipseLinkManyToManyMappingComposite;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.EclipseLinkManyToOneMappingComposite;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.EclipseLinkVersionMappingComposite;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.EclipselinkOneToManyMappingComposite;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.EclipselinkOneToOneMappingComposite;
import org.eclipse.jpt.eclipselink.ui.internal.persistence.caching.PersistenceXmlCachingTab;
import org.eclipse.jpt.eclipselink.ui.internal.persistence.connection.PersistenceXmlConnectionTab;
import org.eclipse.jpt.eclipselink.ui.internal.persistence.customization.PersistenceXmlCustomizationTab;
import org.eclipse.jpt.eclipselink.ui.internal.persistence.general.EclipseLinkPersistenceUnitGeneralComposite;
import org.eclipse.jpt.eclipselink.ui.internal.persistence.logging.PersistenceXmlLoggingTab;
import org.eclipse.jpt.eclipselink.ui.internal.persistence.options.PersistenceXmlOptionsTab;
import org.eclipse.jpt.eclipselink.ui.internal.persistence.schema.generation.PersistenceXmlSchemaGenerationTab;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.details.JpaPageComposite;
import org.eclipse.jpt.ui.internal.BaseJpaUiFactory;
import org.eclipse.jpt.ui.internal.persistence.details.PersistenceUnitPropertiesComposite;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

public class EclipseLinkJpaUiFactory extends BaseJpaUiFactory
{
	public EclipseLinkJpaUiFactory() {
		super();
	}
	
	
	// **************** persistence unit composites ****************************
	
	@Override
	public ListIterator<JpaPageComposite> createPersistenceUnitComposites(
						PropertyValueModel<PersistenceUnit> subjectHolder,
						Composite parent,
						WidgetFactory widgetFactory) {
		
		PropertyValueModel<EclipseLinkPersistenceUnit> eclipseLinkPersistenceUnitHolder = 
			this.buildEclipseLinkPersistenceUnitHolder(subjectHolder);
		ArrayList<JpaPageComposite> pages = 
			new ArrayList<JpaPageComposite>(8);

		pages.add(new EclipseLinkPersistenceUnitGeneralComposite(eclipseLinkPersistenceUnitHolder, parent, widgetFactory));
		
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
				PropertyValueModel<EclipseLinkPersistenceUnit> subjectHolder) {
		return new TransformationPropertyValueModel<EclipseLinkPersistenceUnit, Connection>(subjectHolder) {
			@Override
			protected Connection transform_(EclipseLinkPersistenceUnit value) {
				return value.getConnection();
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
				PropertyValueModel<EclipseLinkPersistenceUnit> subjectHolder) {
		return new TransformationPropertyValueModel<EclipseLinkPersistenceUnit, Options>(subjectHolder) {
			@Override
			protected Options transform_(EclipseLinkPersistenceUnit value) {
				return value.getOptions();
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
	
	
	// **************** java type mapping composites ***************************
	
	@Override
	public JpaComposite createJavaMappedSuperclassComposite(
			PropertyValueModel<JavaMappedSuperclass> subjectHolder,
			Composite parent, WidgetFactory widgetFactory) {
		return new EclipseLinkJavaMappedSuperclassComposite(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	public JpaComposite createJavaEntityComposite(
			PropertyValueModel<JavaEntity> subjectHolder,
			Composite parent, WidgetFactory widgetFactory) {
		return new EclipseLinkJavaEntityComposite(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	public JpaComposite createJavaEmbeddableComposite(
			PropertyValueModel<JavaEmbeddable> subjectHolder,
			Composite parent, WidgetFactory widgetFactory) {
		return new EclipseLinkJavaEmbeddableComposite(subjectHolder, parent, widgetFactory);
	}
	
	
	// **************** java attribute mapping composites **********************
	
	@Override
	public JpaComposite createJavaIdMappingComposite(
			PropertyValueModel<JavaIdMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new EclipseLinkIdMappingComposite(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	public JpaComposite createJavaBasicMappingComposite(
			PropertyValueModel<JavaBasicMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new EclipseLinkBasicMappingComposite(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	public JpaComposite createJavaVersionMappingComposite(
			PropertyValueModel<JavaVersionMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new EclipseLinkVersionMappingComposite(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	public JpaComposite createJavaManyToOneMappingComposite(
			PropertyValueModel<JavaManyToOneMapping> subjectHolder, 
			Composite parent, 
			WidgetFactory widgetFactory) {
		return new EclipseLinkManyToOneMappingComposite(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	public JpaComposite createJavaOneToManyMappingComposite(
			PropertyValueModel<JavaOneToManyMapping> subjectHolder, 
			Composite parent, 
			WidgetFactory widgetFactory) {
		return new EclipselinkOneToManyMappingComposite(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	public JpaComposite createJavaOneToOneMappingComposite(
			PropertyValueModel<JavaOneToOneMapping> subjectHolder, 
			Composite parent, 
			WidgetFactory widgetFactory) {
		return new EclipselinkOneToOneMappingComposite(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	public JpaComposite createJavaManyToManyMappingComposite(
			PropertyValueModel<JavaManyToManyMapping> subjectHolder, 
			Composite parent, 
			WidgetFactory widgetFactory) {
		return new EclipseLinkManyToManyMappingComposite(subjectHolder, parent, widgetFactory);
	}	
}