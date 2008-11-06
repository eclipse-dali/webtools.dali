/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.persistence;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.Property;
import org.eclipse.jpt.core.internal.context.persistence.AbstractPersistenceUnit;
import org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.caching.Caching;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.caching.EclipseLinkCaching;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.connection.Connection;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.connection.EclipseLinkConnection;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.customization.Customization;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.customization.EclipseLinkCustomization;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.general.EclipseLinkGeneralProperties;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.general.GeneralProperties;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.logging.EclipseLinkLogging;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.logging.Logging;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.options.EclipseLinkOptions;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.options.Options;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.schema.generation.EclipseLinkSchemaGeneration;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.schema.generation.SchemaGeneration;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.jpt.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;

/**
 * EclipseLinkPersistenceUnit
 */
public class EclipseLinkPersistenceUnit extends AbstractPersistenceUnit
{
	
	private final GeneralProperties generalProperties;
	private final Connection connection;
	private final Customization customization;
	private final Caching caching;
	private final Logging logging;
	private final Options options;
	private final SchemaGeneration schemaGeneration;
	
	private final ListValueModel<Property> propertiesAdapter;
	private final ListValueModel<Property> propertyListAdapter;
	
	/* global converter definitions, defined elsewhere in model */
	protected final List<EclipseLinkConverter> converters = new ArrayList<EclipseLinkConverter>();
	
	
	public EclipseLinkPersistenceUnit(Persistence parent, XmlPersistenceUnit persistenceUnit) {
		super(parent);
		this.initialize(persistenceUnit);
		PropertyValueModel<PersistenceUnit> persistenceUnitHolder = 
			new SimplePropertyValueModel<PersistenceUnit>(this);
		
		this.propertiesAdapter = this.buildPropertiesAdapter(persistenceUnitHolder);
		this.propertyListAdapter = this.buildPropertyListAdapter(this.propertiesAdapter);		
		this.generalProperties = this.buildGeneralProperties();
		this.connection = this.buildConnection();
		this.customization = this.buildCustomization();
		this.caching = this.buildCaching();
		this.logging = this.buildLogging();
		this.options = this.buildOptions();
		this.schemaGeneration = this.buildSchemaGeneration();
	}

	// ********** internal methods **********

	private ListValueModel<Property> buildPropertyListAdapter(ListValueModel<Property> propertiesAdapter) {
		return new ItemPropertyListValueModelAdapter<Property>(propertiesAdapter, Property.VALUE_PROPERTY);
	}

	private ListValueModel<Property> buildPropertiesAdapter(PropertyValueModel<PersistenceUnit> subjectHolder) {
		return new ListAspectAdapter<PersistenceUnit, Property>(subjectHolder, PersistenceUnit.PROPERTIES_LIST) {
			@Override
			protected ListIterator<Property> listIterator_() {
				return this.subject.properties();
			}

			@Override
			protected int size_() {
				return this.subject.propertiesSize();
			}
		};
	}
	
	private GeneralProperties buildGeneralProperties() {
		return new EclipseLinkGeneralProperties(this, this.propertyListAdapter);
	}
	
	private Connection buildConnection() {
		return new EclipseLinkConnection(this, this.propertyListAdapter);
	}

	private Customization buildCustomization() {
		return new EclipseLinkCustomization(this, this.propertyListAdapter);
	}
	
	private Caching buildCaching() {
		return new EclipseLinkCaching(this, this.propertyListAdapter);
	}

	private Logging buildLogging() {
		return new EclipseLinkLogging(this, this.propertyListAdapter);
	}

	private Options buildOptions() {
		return new EclipseLinkOptions(this, this.propertyListAdapter);
	}

	private SchemaGeneration buildSchemaGeneration() {
		return new EclipseLinkSchemaGeneration(this, this.propertyListAdapter);
	}
	
	@Override
	protected void addNonUpdateAspectNamesTo(Set<String> nonUpdateAspectNames) {
		super.addNonUpdateAspectNamesTo(nonUpdateAspectNames);
		nonUpdateAspectNames.add(CONVERTERS_LIST);
	}
	
	
	// **************** properties *********************************************
	
	public GeneralProperties getGeneralProperties() {
		return this.generalProperties;
	}

	public Connection getConnection() {
		return this.connection;
	}

	public Customization getCustomization() {
		return this.customization;
	}

	public Caching getCaching() {
		return this.caching;
	}
	
	public Logging getLogging() {
		return this.logging;
	}
	
	public Options getOptions() {
		return this.options;
	}

	public SchemaGeneration getSchemaGeneration() {
		return this.schemaGeneration;
	}
	

	public ListValueModel<Property> getPropertiesAdapter() {
		return this.propertiesAdapter;
	}

	public ListValueModel<Property> getPropertyListAdapter() {
		return this.propertyListAdapter;
	}

	
	// **************** converters *********************************************
	
	/**
	 * Identifier for changes to the list of global converter definitions.
	 * Note that there are no granular changes to this list.  There is only
	 * notification that the entire list has changed.
	 */
	public static final String CONVERTERS_LIST = "converters"; //$NON-NLS-1$
	
	/**
	 * Add the converter definition (defined elsewhere) to the list of converters
	 * defined within this persistence unit.
	 * Note that this should only be called during the process of updating the
	 * local converter definition.
	 * No change notification accompanies this action specifically.
	 */
	public void addConverter(EclipseLinkConverter converter) {
		this.converters.add(converter);
	}
	
	/**
	 * Return an iterator on all converters defined within this persistence unit,
	 * included duplicately named converters definitions.
	 */
	public ListIterator<EclipseLinkConverter> allConverters() {
		return new CloneListIterator<EclipseLinkConverter>(this.converters);
	}
	
	/**
	 * Return an array of the names of the converters defined in the persistence
	 * unit, with duplicates removed.
	 */
	public String[] uniqueConverterNames() {
		HashSet<String> names = CollectionTools.set(this.allNonNullConverterNames());
		return names.toArray(new String[names.size()]);
	}
	
	protected Iterator<String> allNonNullConverterNames() {
		return new FilteringIterator<String, String>(this.allConverterNames()) {
			@Override
			protected boolean accept(String converterName) {
				return converterName != null;
			}
		};
	}
	
	protected Iterator<String> allConverterNames() {
		return new TransformationIterator<EclipseLinkConverter, String>(this.allConverters()) {
			@Override
			protected String transform(EclipseLinkConverter converter) {
				return converter.getName();
			}
		};
	}
	
	
	// **************** updating ***********************************************
	
	@Override
	public void update(XmlPersistenceUnit persistenceUnit) {
		this.converters.clear();
		super.update(persistenceUnit);
		convertersUpdated();
	}
	
	// This is called after the persistence unit has been updated to ensure
	// we catch all added converters
	protected void convertersUpdated() {
		fireListChanged(CONVERTERS_LIST);
	}
}