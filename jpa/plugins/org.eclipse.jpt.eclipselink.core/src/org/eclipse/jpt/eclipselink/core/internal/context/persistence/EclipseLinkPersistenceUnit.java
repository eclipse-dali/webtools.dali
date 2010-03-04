/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.internal.context.persistence.AbstractPersistenceUnit;
import org.eclipse.jpt.core.internal.jpa1.context.persistence.ImpliedMappingFileRef;
import org.eclipse.jpt.core.jpa2.context.persistence.options.SharedCacheMode;
import org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.eclipselink.core.EclipseLinkJpaProject;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.eclipselink.core.context.persistence.EclipseLinkPersistenceXmlContextNodeFactory;
import org.eclipse.jpt.eclipselink.core.context.persistence.caching.Caching;
import org.eclipse.jpt.eclipselink.core.context.persistence.connection.Connection;
import org.eclipse.jpt.eclipselink.core.context.persistence.customization.Customization;
import org.eclipse.jpt.eclipselink.core.context.persistence.general.GeneralProperties;
import org.eclipse.jpt.eclipselink.core.context.persistence.logging.Logging;
import org.eclipse.jpt.eclipselink.core.context.persistence.options.Options;
import org.eclipse.jpt.eclipselink.core.context.persistence.schema.generation.SchemaGeneration;
import org.eclipse.jpt.eclipselink.core.internal.DefaultEclipseLinkJpaValidationMessages;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaValidationMessages;
import org.eclipse.jpt.eclipselink.core.internal.JptEclipseLinkCorePlugin;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.caching.EclipseLinkCaching;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.customization.EclipseLinkCustomization;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.general.EclipseLinkGeneralProperties;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.schema.generation.EclipseLinkSchemaGeneration;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterables.CompositeListIterable;
import org.eclipse.jpt.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * EclipseLink persistence unit
 */
public class EclipseLinkPersistenceUnit
	extends AbstractPersistenceUnit
{
	protected MappingFileRef impliedEclipseLinkMappingFileRef;

	private/*final*/ GeneralProperties generalProperties;
	private Customization customization;
	private Caching caching;
	private Logging logging;
	private SchemaGeneration schemaGeneration;

	/* global converter definitions, defined elsewhere in model */
	protected final List<EclipseLinkConverter> converters = new ArrayList<EclipseLinkConverter>();


	// ********** constructors/initialization **********
	public EclipseLinkPersistenceUnit(Persistence parent, XmlPersistenceUnit xmlPersistenceUnit) {
		super(parent, xmlPersistenceUnit);
	}

	@Override
	public EclipseLinkPersistenceXmlContextNodeFactory getContextNodeFactory() {
		return (EclipseLinkPersistenceXmlContextNodeFactory) super.getContextNodeFactory();
	}

	@Override
	protected void initializeProperties() {
		super.initializeProperties();

		this.generalProperties = this.buildEclipseLinkGeneralProperties();
		this.customization = this.buildEclipseLinkCustomization();
		this.caching = this.buildEclipseLinkCaching();
		this.logging = this.buildEclipseLinkLogging();
		this.schemaGeneration = this.buildEclipseLinkSchemaGeneration();
	}

	@Override
	protected void addNonUpdateAspectNamesTo(Set<String> nonUpdateAspectNames) {
		super.addNonUpdateAspectNamesTo(nonUpdateAspectNames);
		nonUpdateAspectNames.add(CONVERTERS_LIST);
	}
	
	@Override
	public void propertyValueChanged(String propertyName, String newValue) {
		super.propertyValueChanged(propertyName, newValue);
		this.generalProperties.propertyValueChanged(propertyName, newValue);
		this.customization.propertyValueChanged(propertyName, newValue);
		this.caching.propertyValueChanged(propertyName, newValue);
		this.logging.propertyValueChanged(propertyName, newValue);
		this.schemaGeneration.propertyValueChanged(propertyName, newValue);
	}
	
	@Override
	public void propertyRemoved(String propertyName) {
		super.propertyRemoved(propertyName);
		this.generalProperties.propertyRemoved(propertyName);
		this.customization.propertyRemoved(propertyName);
		this.caching.propertyRemoved(propertyName);
		this.logging.propertyRemoved(propertyName);
		this.schemaGeneration.propertyRemoved(propertyName);
	}
	
	@Override
	protected SharedCacheMode buildDefaultSharedCacheMode() {
		return SharedCacheMode.DISABLE_SELECTIVE;
	}
	
	@Override
	public boolean calculateDefaultCacheable() {
		switch (getSharedCacheMode()) {
			case NONE:
			case ENABLE_SELECTIVE:
				return false;
			case ALL:
			case DISABLE_SELECTIVE:
			case UNSPECIFIED:
				return true;
		}
		return true;//null
	}

	// **************** mapping file refs **************************************

	@Override
	protected ListIterable<MappingFileRef> getMappingFileRefs() {
		return (this.impliedEclipseLinkMappingFileRef == null) ? super.getMappingFileRefs() : this.getCombinedEclipseLinkMappingFileRefs();
	}

	protected ListIterable<MappingFileRef> getCombinedEclipseLinkMappingFileRefs() {
		return new CompositeListIterable<MappingFileRef>(super.getMappingFileRefs(), this.impliedEclipseLinkMappingFileRef);
	}

	@Override
	public int mappingFileRefsSize() {
		return this.impliedEclipseLinkMappingFileRef == null ? super.mappingFileRefsSize() : combinedEclipseLinkMappingFileRefsSize(); 
	}

	protected int combinedEclipseLinkMappingFileRefsSize() {
		return super.mappingFileRefsSize() + 1;
	}


	// **************** implied eclipselink mapping file ref *******************

	/**
	 * String constant associated with changes to the implied eclipselink mapping file ref
	 */
	public final static String IMPLIED_ECLIPSELINK_MAPPING_FILE_REF_PROPERTY = "impliedEclipseLinkMappingFileRef"; //$NON-NLS-1$


	public MappingFileRef getImpliedEclipseLinkMappingFileRef() {
		return this.impliedEclipseLinkMappingFileRef;
	}

	protected MappingFileRef setImpliedEclipseLinkMappingFileRef() {
		if (this.impliedEclipseLinkMappingFileRef != null) {
			throw new IllegalStateException("The implied eclipselink mapping file ref is already set."); //$NON-NLS-1$
		}
		MappingFileRef mappingFileRef = buildEclipseLinkImpliedMappingFileRef();
		this.impliedEclipseLinkMappingFileRef = mappingFileRef;
		this.firePropertyChanged(IMPLIED_ECLIPSELINK_MAPPING_FILE_REF_PROPERTY, null, mappingFileRef);
		return mappingFileRef;
	}

	protected void unsetImpliedEclipseLinkMappingFileRef() {
		if (this.impliedEclipseLinkMappingFileRef == null) {
			throw new IllegalStateException("The implied eclipselink mapping file ref is already unset."); //$NON-NLS-1$
		}
		MappingFileRef mappingFileRef = this.impliedEclipseLinkMappingFileRef;
		this.impliedEclipseLinkMappingFileRef.dispose();
		this.impliedEclipseLinkMappingFileRef = null;
		this.firePropertyChanged(IMPLIED_ECLIPSELINK_MAPPING_FILE_REF_PROPERTY, mappingFileRef, null);
	}


	// **************** factory methods *********************************************
	
	protected GeneralProperties buildEclipseLinkGeneralProperties() {
		return new EclipseLinkGeneralProperties(this);
	}
	
	protected Connection buildEclipseLinkConnection() {
		return (Connection) this.getContextNodeFactory().buildConnection(this);
	}
	
	protected Customization buildEclipseLinkCustomization() {
		return new EclipseLinkCustomization(this);
	}
	
	protected Caching buildEclipseLinkCaching() {
		return new EclipseLinkCaching(this);
	}
	
	protected Logging buildEclipseLinkLogging() {
		return (Logging) this.getContextNodeFactory().buildLogging(this);
	}
	
	protected Options buildEclipseLinkOptions() {
		return (Options) this.getContextNodeFactory().buildOptions(this);
	}
	
	protected SchemaGeneration buildEclipseLinkSchemaGeneration() {
		return new EclipseLinkSchemaGeneration(this);
	}
	

	// **************** properties *********************************************

	public GeneralProperties getGeneralProperties() {
		return this.generalProperties;
	}

	@Override
	public Connection getConnection() {
		return (Connection) super.getConnection();
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

	@Override
	public Options getOptions() {
		return (Options) super.getOptions();
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

	public int convertersSize() {
		return this.converters.size();
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
		return new FilteringIterator<String>(this.allConverterNames()) {
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
	public void postUpdate() {
		super.postUpdate();
		if (this.impliedEclipseLinkMappingFileRef != null) {
			this.impliedEclipseLinkMappingFileRef.postUpdate();
		}
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

			this.impliedEclipseLinkMappingFileRef = buildEclipseLinkImpliedMappingFileRef();
		}
	}

	private ImpliedMappingFileRef buildEclipseLinkImpliedMappingFileRef() {
		return new ImpliedMappingFileRef(this, JptEclipseLinkCorePlugin.DEFAULT_ECLIPSELINK_ORM_XML_FILE_PATH);
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

			if (this.impliedEclipseLinkMappingFileRef == null) {
				setImpliedEclipseLinkMappingFileRef();
			}
			getImpliedEclipseLinkMappingFileRef().update(null);
		}
		else if (this.impliedEclipseLinkMappingFileRef != null) {
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

	@Override
	public EclipseLinkJpaProject getJpaProject() {
		return (EclipseLinkJpaProject) super.getJpaProject();
	}
	
	protected boolean impliedEclipseLinkMappingFileExists() {
		return getJpaProject().getDefaultEclipseLinkOrmXmlResource() != null;
	}

	// This is called after the persistence unit has been updated to ensure
	// we catch all added converters
	protected void convertersUpdated() {
		fireListChanged(CONVERTERS_LIST, this.converters);
	}
	
	// ********** validation **********

	@Override
	protected void validateProperties(List<IMessage> messages, IReporter reporter) {

		if(this.isJpa2_0Compatible()) {
			Iterator<Property> properties = this.propertiesWithNamePrefix("eclipselink.cache.type."); //$NON-NLS-1$
			
			if(properties.hasNext()) {
				Property property = properties.next();
				messages.add(
					DefaultEclipseLinkJpaValidationMessages.buildMessage(
						IMessage.NORMAL_SEVERITY,
						EclipseLinkJpaValidationMessages.PERSISTENCE_UNIT_LEGACY_ENTITY_CACHING,
						new String[] {property.getName()},
						this.getPersistenceUnit()
					)
				);
			}
		}
	}

}
