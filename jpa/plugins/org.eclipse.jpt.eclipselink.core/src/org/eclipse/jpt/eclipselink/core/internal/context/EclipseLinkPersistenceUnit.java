/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.internal.context.persistence.GenericPersistenceUnit;
import org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.eclipselink.core.internal.context.caching.Caching;
import org.eclipse.jpt.eclipselink.core.internal.context.connection.Connection;
import org.eclipse.jpt.eclipselink.core.internal.context.customization.Customization;
import org.eclipse.jpt.eclipselink.core.internal.context.general.GeneralProperties;
import org.eclipse.jpt.eclipselink.core.internal.context.logging.Logging;
import org.eclipse.jpt.eclipselink.core.internal.context.options.Options;
import org.eclipse.jpt.eclipselink.core.internal.context.schema.generation.SchemaGeneration;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;

/**
 * EclipseLinkPersistenceUnit
 */
public class EclipseLinkPersistenceUnit extends GenericPersistenceUnit
{
	private final EclipseLinkProperties eclipseLinkProperties;
	
	/* global converter definitions, defined elsewhere in model */
	protected final List<EclipseLinkConverter> converters;
	
	
	public EclipseLinkPersistenceUnit(Persistence parent, XmlPersistenceUnit persistenceUnit) {
		super(parent, persistenceUnit);
		this.eclipseLinkProperties = new EclipseLinkJpaProperties(this);
		this.converters = new ArrayList<EclipseLinkConverter>();
	}
	
	
	@Override
	protected void addNonUpdateAspectNamesTo(Set<String> nonUpdateAspectNames) {
		super.addNonUpdateAspectNamesTo(nonUpdateAspectNames);
		nonUpdateAspectNames.add(CONVERTERS_LIST);
	}
	
	
	// **************** properties *********************************************
	
	public GeneralProperties getGeneralProperties() {
		return this.eclipseLinkProperties.getGeneralProperties();
	}

	public Connection getConnection() {
		return this.eclipseLinkProperties.getConnection();
	}

	public Customization getCustomization() {
		return this.eclipseLinkProperties.getCustomization();
	}

	public Caching getCaching() {
		return this.eclipseLinkProperties.getCaching();
	}
	
	public Logging getLogging() {
		return this.eclipseLinkProperties.getLogging();
	}
	
	public Options getOptions() {
		return this.eclipseLinkProperties.getOptions();
	}

	public SchemaGeneration getSchemaGeneration() {
		return this.eclipseLinkProperties.getSchemaGeneration();
	}
	
	
	// **************** converters *********************************************
	
	/**
	 * Identifier for changes to the list of global converter definitions.
	 * Note that there are no granular changes to this list.  There is only
	 * notification that the entire list has changed.
	 */
	public String CONVERTERS_LIST = "converters"; //$NON-NLS-1$
	
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