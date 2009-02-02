/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.context.persistence.AbstractPersistenceUnit;
import org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.eclipselink.core.internal.JptEclipseLinkCorePlugin;
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
import org.eclipse.jpt.eclipselink.core.internal.resource.orm.EclipseLinkOrmXmlResourceProvider;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmXmlResource;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.ReadOnlyCompositeListIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.jpt.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.model.value.ListValueModel;

/**
 * EclipseLink persistence unit
 */
public class EclipseLinkPersistenceUnit
	extends AbstractPersistenceUnit
{
	protected MappingFileRef impliedEclipseLinkMappingFileRef;

	private GeneralProperties generalProperties;
	private Connection connection;
	private Customization customization;
	private Caching caching;
	private Logging logging;
	private Options options;
	private SchemaGeneration schemaGeneration;

	/* global converter definitions, defined elsewhere in model */
	protected final List<EclipseLinkConverter> converters = new ArrayList<EclipseLinkConverter>();


	public EclipseLinkPersistenceUnit(Persistence parent) {
		super(parent);
	}

	@Override
	protected void initializeProperties() {
		super.initializeProperties();
		ListValueModel<Property> propertyListAdapter = this.buildPropertyListAdapter();

		this.generalProperties = new EclipseLinkGeneralProperties(this, propertyListAdapter);
		this.connection = new EclipseLinkConnection(this, propertyListAdapter);
		this.customization = new EclipseLinkCustomization(this, propertyListAdapter);
		this.caching = new EclipseLinkCaching(this, propertyListAdapter);
		this.logging = new EclipseLinkLogging(this, propertyListAdapter);
		this.options = new EclipseLinkOptions(this, propertyListAdapter);
		this.schemaGeneration = new EclipseLinkSchemaGeneration(this, propertyListAdapter);
	}

	private ListValueModel<Property> buildPropertyListAdapter() {
		return new ItemPropertyListValueModelAdapter<Property>(this.buildPropertiesAdapter(), Property.VALUE_PROPERTY);
	}

	private ListValueModel<Property> buildPropertiesAdapter() {
		return new ListAspectAdapter<PersistenceUnit, Property>(PROPERTIES_LIST, this) {
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

	@Override
	protected void addNonUpdateAspectNamesTo(Set<String> nonUpdateAspectNames) {
		super.addNonUpdateAspectNamesTo(nonUpdateAspectNames);
		nonUpdateAspectNames.add(CONVERTERS_LIST);
	}


	// **************** mapping file refs **************************************

	@Override
	public ListIterator<MappingFileRef> mappingFileRefs() {
		if (impliedEclipseLinkMappingFileRef == null) {
			return super.mappingFileRefs();
		}
		return new ReadOnlyCompositeListIterator<MappingFileRef>(
			super.mappingFileRefs(), impliedEclipseLinkMappingFileRef);
	}

	@Override
	public int mappingFileRefsSize() {
		if (impliedEclipseLinkMappingFileRef == null) {
			return super.mappingFileRefsSize();
		}
		return 1 + super.mappingFileRefsSize();
	}


	// **************** implied eclipselink mapping file ref *******************

	/**
	 * String constant associated with changes to the implied eclipselink mapping file ref
	 */
	public final static String IMPLIED_ECLIPSELINK_MAPPING_FILE_REF_PROPERTY = "impliedEclipseLinkMappingFileRef"; //$NON-NLS-1$


	public MappingFileRef getImpliedEclipseLinkMappingFileRef() {
		return impliedEclipseLinkMappingFileRef;
	}

	protected MappingFileRef setImpliedEclipseLinkMappingFileRef() {
		if (impliedEclipseLinkMappingFileRef != null) {
			throw new IllegalStateException("The implied eclipselink mapping file ref is already set."); //$NON-NLS-1$
		}
		MappingFileRef mappingFileRef = new EclipseLinkImpliedMappingFileRef(this);
		impliedEclipseLinkMappingFileRef = mappingFileRef;
		firePropertyChanged(IMPLIED_ECLIPSELINK_MAPPING_FILE_REF_PROPERTY, null, mappingFileRef);
		return mappingFileRef;
	}

	protected void unsetImpliedEclipseLinkMappingFileRef() {
		if (impliedEclipseLinkMappingFileRef == null) {
			throw new IllegalStateException("The implied eclipselink mapping file ref is already unset."); //$NON-NLS-1$
		}
		MappingFileRef mappingFileRef = impliedEclipseLinkMappingFileRef;
		impliedEclipseLinkMappingFileRef.dispose();
		impliedEclipseLinkMappingFileRef = null;
		firePropertyChanged(IMPLIED_ECLIPSELINK_MAPPING_FILE_REF_PROPERTY, mappingFileRef, null);
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

	@Override
	protected void initializeMappingFileRefs() {
		super.initializeMappingFileRefs();

		// use implied mapping file if 
		// a) properties does not exclude it
		// b) it isn't otherwise specified
		// c) the file actually exists
		if (! impliedEclipseLinkMappingFileIsExcluded()
				&& ! impliedEclipseLinkMappingFileIsSpecified()
				&& impliedEclipseLinkMappingFileExists()) {

			impliedEclipseLinkMappingFileRef = new EclipseLinkImpliedMappingFileRef(this);
		}
	}

	@Override
	protected void updateMappingFileRefs() {
		super.updateMappingFileRefs();

		// use implied mapping file if 
		// a) properties does not exclude it
		// b) it isn't otherwise specified
		// c) the file actually exists
		if (! impliedEclipseLinkMappingFileIsExcluded()
				&& ! impliedEclipseLinkMappingFileIsSpecified()
				&& impliedEclipseLinkMappingFileExists()) {

			if (impliedEclipseLinkMappingFileRef == null) {
				setImpliedEclipseLinkMappingFileRef();
			}
			getImpliedEclipseLinkMappingFileRef().update(null);
		}
		else if (impliedEclipseLinkMappingFileRef != null) {
			unsetImpliedEclipseLinkMappingFileRef();
		}
	}

	protected boolean impliedEclipseLinkMappingFileIsExcluded() {
		return getGeneralProperties().getExcludeEclipselinkOrm() == Boolean.TRUE;
	}

	protected boolean impliedEclipseLinkMappingFileIsSpecified() {
		String impliedMappingFile = JptEclipseLinkCorePlugin.DEFAULT_ECLIPSELINK_ORM_XML_FILE_PATH;
		for (Iterator<MappingFileRef> stream = specifiedMappingFileRefs(); stream.hasNext(); ) {
			if (impliedMappingFile.equals(stream.next().getFileName())) {
				return true;
			}
		}
		return false;
	}

	protected boolean impliedEclipseLinkMappingFileExists() {
		EclipseLinkOrmXmlResourceProvider modelProvider = 
			EclipseLinkOrmXmlResourceProvider.getDefaultXmlResourceProvider(getJpaProject().getProject());
		EclipseLinkOrmXmlResource resource = modelProvider.getXmlResource();
		return resource != null && resource.exists();
	}

	// This is called after the persistence unit has been updated to ensure
	// we catch all added converters
	protected void convertersUpdated() {
		fireListChanged(CONVERTERS_LIST);
	}

}
