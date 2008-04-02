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

import java.util.ListIterator;

import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.Property;
import org.eclipse.jpt.eclipselink.core.internal.context.caching.Caching;
import org.eclipse.jpt.eclipselink.core.internal.context.caching.EclipseLinkCaching;
import org.eclipse.jpt.eclipselink.core.internal.context.customization.Customization;
import org.eclipse.jpt.eclipselink.core.internal.context.customization.EclipseLinkCustomization;
import org.eclipse.jpt.eclipselink.core.internal.context.schema.generation.EclipseLinkSchemaGeneration;
import org.eclipse.jpt.eclipselink.core.internal.context.schema.generation.SchemaGeneration;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;

/**
 * EclipseLinkJpaProperties
 */
public class EclipseLinkJpaProperties extends AbstractModel
	implements EclipseLinkProperties
{
	private PersistenceUnit persistenceUnit;
	
	private SchemaGeneration schemaGeneration;
	private Caching caching;
	private Customization customization;

	// private Logging logging;
	private ListValueModel<Property> propertiesAdapter;
	private ListValueModel<Property> propertyListAdapter;

	// ********** constructors/initialization **********
	public EclipseLinkJpaProperties(PersistenceUnit parent) {
		super();
		this.initialize(parent);
	}

	protected void initialize(PersistenceUnit parent) {
		this.persistenceUnit = parent;
		PropertyValueModel<PersistenceUnit> persistenceUnitHolder = 
			new SimplePropertyValueModel<PersistenceUnit>(this.persistenceUnit);
		
		this.propertiesAdapter = this.buildPropertiesAdapter(persistenceUnitHolder);
		this.propertyListAdapter = this.buildPropertyListAdapter(this.propertiesAdapter);
		
		this.schemaGeneration = this.buildSchemaGeneration();
		this.caching = this.buildCaching();
		this.customization = this.buildCustomization();
		// TODO
		// this.logging = this.buildLogging();
	}

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

	private SchemaGeneration buildSchemaGeneration() {
		return new EclipseLinkSchemaGeneration(this.persistenceUnit(), this.propertyListAdapter());
	}

	private Caching buildCaching() {
		return new EclipseLinkCaching(this.persistenceUnit(), this.propertyListAdapter());
	}

	private Customization buildCustomization() {
		return new EclipseLinkCustomization(this.persistenceUnit(), this.propertyListAdapter());
	}

	// TODO
	// private Logging buildLogging() {
	// return new EclipseLinkLogging( this.persistenceUnit());
	// }
	
	// ******** Behavior *********
	public SchemaGeneration getSchemaGeneration() {
		return this.schemaGeneration;
	}

	public Caching getCaching() {
		return this.caching;
	}

	public Customization getCustomization() {
		return this.customization;
	}

	// TODO
	// public Logging getLogging() {
	// return this.logging;
	// }
	
	public EclipseLinkProperties eclipseLinkProperties() {
		return this;
	}

	public ListValueModel<Property> propertiesAdapter() {
		return this.propertiesAdapter;
	}

	public ListValueModel<Property> propertyListAdapter() {
		return this.propertyListAdapter;
	}

	public PersistenceUnit persistenceUnit() {
		return this.persistenceUnit;
	}

	public boolean itemIsProperty(Property item) {
		throw new UnsupportedOperationException();
	}

	public void propertyChanged(PropertyChangeEvent event) {
		throw new UnsupportedOperationException();
	}

	public String propertyIdFor(Property property) {
		throw new UnsupportedOperationException();
	}
}