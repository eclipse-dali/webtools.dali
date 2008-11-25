/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.context.persistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Set;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.Generator;
import org.eclipse.jpt.core.context.MappingFile;
import org.eclipse.jpt.core.context.MappingFilePersistenceUnitDefaults;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.Query;
import org.eclipse.jpt.core.context.persistence.ClassRef;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.context.persistence.PersistenceStructureNodes;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.PersistenceUnitTransactionType;
import org.eclipse.jpt.core.context.persistence.Property;
import org.eclipse.jpt.core.internal.context.AbstractXmlContextNode;
import org.eclipse.jpt.core.internal.resource.orm.OrmResourceModelProvider;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.orm.OrmResource;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlJavaClassRef;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.core.resource.persistence.XmlProperties;
import org.eclipse.jpt.core.resource.persistence.XmlProperty;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.HashBag;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.ReadOnlyCompositeListIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class AbstractPersistenceUnit extends AbstractXmlContextNode
	implements PersistenceUnit
{
	protected XmlPersistenceUnit xmlPersistenceUnit;
	
	protected String name;
	
	protected PersistenceUnitTransactionType specifiedTransactionType;
	
	protected PersistenceUnitTransactionType defaultTransactionType;
	
	protected String description;
	
	protected String provider;
	
	protected String jtaDataSource;
	
	protected String nonJtaDataSource;
	
	protected final List<MappingFileRef> specifiedMappingFileRefs = new ArrayList<MappingFileRef>();
	
	protected MappingFileRef impliedMappingFileRef;
	
	protected final List<ClassRef> specifiedClassRefs = new ArrayList<ClassRef>();
	
	protected final List<ClassRef> impliedClassRefs = new ArrayList<ClassRef>();
	
	protected Boolean specifiedExcludeUnlistedClasses;
	
	protected boolean defaultExcludeUnlistedClasses = false;
	
	protected final List<Property> properties = new ArrayList<Property>();
	
	/* global generator definitions, defined elsewhere in model */
	protected final List<Generator> generators = new ArrayList<Generator>();
	
	/* global query definitions, defined elsewhere in model */
	protected final List<Query> queries = new ArrayList<Query>();
	
	
	protected AccessType defaultAccess;
	protected String defaultCatalog;
	protected String defaultSchema;
	protected boolean defaultCascadePersist;
	
	public AbstractPersistenceUnit(Persistence parent) {
		super(parent);
	}
	
	@Override
	protected void addNonUpdateAspectNamesTo(Set<String> nonUpdateAspectNames) {
		super.addNonUpdateAspectNamesTo(nonUpdateAspectNames);
		nonUpdateAspectNames.add(GENERATORS_LIST);
		nonUpdateAspectNames.add(QUERIES_LIST);
	}
	
	public String getId() {
		return PersistenceStructureNodes.PERSISTENCE_UNIT_ID;
	}

	@Override
	public PersistenceUnit getPersistenceUnit() {
		return this;
	}
	
	
	// **************** parent *************************************************
	
	@Override
	public Persistence getParent() {
		return (Persistence) super.getParent();
	}
	
	
	// **************** name ***************************************************
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		this.xmlPersistenceUnit.setName(newName);
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}
	
	
	// **************** transaction type ***************************************
	
	public PersistenceUnitTransactionType getTransactionType() {
		return (isTransactionTypeDefault()) ?
			getDefaultTransactionType() : getSpecifiedTransactionType();
	}
	
	public PersistenceUnitTransactionType getSpecifiedTransactionType() {
		return this.specifiedTransactionType;
	}
	
	public void setSpecifiedTransactionType(PersistenceUnitTransactionType newTransactionType) {
		PersistenceUnitTransactionType oldTransactionType = this.specifiedTransactionType;
		this.specifiedTransactionType = newTransactionType;		
		this.xmlPersistenceUnit.setTransactionType(PersistenceUnitTransactionType.toXmlResourceModel(newTransactionType));		
		firePropertyChanged(SPECIFIED_TRANSACTION_TYPE_PROPERTY, oldTransactionType, newTransactionType);
	}
	
	public boolean isTransactionTypeDefault() {
		return this.specifiedTransactionType == null;
	}
	
	public PersistenceUnitTransactionType getDefaultTransactionType() {
		return this.defaultTransactionType;
	}
	
	protected void setDefaultTransactionType(PersistenceUnitTransactionType newTransactionType) {
		PersistenceUnitTransactionType oldTransactionType = this.defaultTransactionType;
		this.defaultTransactionType = newTransactionType;		
		firePropertyChanged(DEFAULT_TRANSACTION_TYPE_PROPERTY, oldTransactionType, newTransactionType);
	}
	
	// **************** description ********************************************
	
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String newDescription) {
		String oldDescription = this.description;
		this.description = newDescription;
		this.xmlPersistenceUnit.setDescription(newDescription);
		firePropertyChanged(DESCRIPTION_PROPERTY, oldDescription, newDescription);
	}
	
	
	// **************** provider ***********************************************
	
	public String getProvider() {
		return this.provider;
	}
	
	public void setProvider(String newProvider) {
		String oldProvider = this.provider;
		this.provider = newProvider;
		this.xmlPersistenceUnit.setProvider(newProvider);
		firePropertyChanged(PROVIDER_PROPERTY, oldProvider, newProvider);
	}
	
	
	// **************** jta data source ****************************************
	
	public String getJtaDataSource() {
		return this.jtaDataSource;
	}
	
	public void setJtaDataSource(String newJtaDataSource) {
		String oldJtaDataSource = this.jtaDataSource;
		this.jtaDataSource = newJtaDataSource;
		this.xmlPersistenceUnit.setJtaDataSource(newJtaDataSource);
		firePropertyChanged(JTA_DATA_SOURCE_PROPERTY, oldJtaDataSource, newJtaDataSource);
	}
	
	
	// **************** non-jta data source ************************************
	
	public String getNonJtaDataSource() {
		return this.nonJtaDataSource;
	}
	
	public void setNonJtaDataSource(String newNonJtaDataSource) {
		String oldNonJtaDataSource = this.nonJtaDataSource;
		this.nonJtaDataSource = newNonJtaDataSource;
		this.xmlPersistenceUnit.setNonJtaDataSource(newNonJtaDataSource);
		firePropertyChanged(NON_JTA_DATA_SOURCE_PROPERTY, oldNonJtaDataSource, newNonJtaDataSource);
	}
	
	
	// **************** mapping file refs **************************************
	
	public ListIterator<MappingFileRef> mappingFileRefs() {
		if (impliedMappingFileRef == null) {
			return specifiedMappingFileRefs();
		}
		return new ReadOnlyCompositeListIterator<MappingFileRef>(
			specifiedMappingFileRefs(), impliedMappingFileRef);
	}
	
	public int mappingFileRefsSize() {
		if (impliedMappingFileRef == null) {
			return specifiedMappingFileRefsSize();
		}
		return 1 + specifiedMappingFileRefsSize();
	}

	// **************** specified mapping file refs ****************************
	
	public ListIterator<MappingFileRef> specifiedMappingFileRefs() {
		return new CloneListIterator<MappingFileRef>(specifiedMappingFileRefs);
	}
	
	public int specifiedMappingFileRefsSize() {
		return specifiedMappingFileRefs.size();
	}
	
	public MappingFileRef addSpecifiedMappingFileRef() {
		return addSpecifiedMappingFileRef(specifiedMappingFileRefs.size());
	}
	
	public MappingFileRef addSpecifiedMappingFileRef(int index) {
		XmlMappingFileRef xmlMappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		MappingFileRef mappingFileRef = buildMappingFileRef(xmlMappingFileRef);
		specifiedMappingFileRefs.add(index, mappingFileRef);
		this.xmlPersistenceUnit.getMappingFiles().add(index, xmlMappingFileRef);
		fireItemAdded(SPECIFIED_MAPPING_FILE_REFS_LIST, index, mappingFileRef);
		return mappingFileRef;
	}
	
	public void removeSpecifiedMappingFileRef(MappingFileRef mappingFileRef) {
		removeSpecifiedMappingFileRef(specifiedMappingFileRefs.indexOf(mappingFileRef));
	}
	
	public void removeSpecifiedMappingFileRef(int index) {
		MappingFileRef mappingFileRefRemoved = specifiedMappingFileRefs.remove(index);
		mappingFileRefRemoved.dispose();
		this.xmlPersistenceUnit.getMappingFiles().remove(index);
		fireItemRemoved(SPECIFIED_MAPPING_FILE_REFS_LIST, index, mappingFileRefRemoved);
	}
	
	protected void addSpecifiedMappingFileRef_(MappingFileRef mappingFileRef) {
		addSpecifiedMappingFileRef_(specifiedMappingFileRefs.size(), mappingFileRef);
	}
	
	protected void addSpecifiedMappingFileRef_(int index, MappingFileRef mappingFileRef) {
		addItemToList(index, mappingFileRef, specifiedMappingFileRefs, SPECIFIED_MAPPING_FILE_REFS_LIST);
	}
	
	protected void removeSpecifiedMappingFileRef_(MappingFileRef mappingFileRef) {
		mappingFileRef.dispose();
		this.removeItemFromList(mappingFileRef, this.specifiedMappingFileRefs, SPECIFIED_MAPPING_FILE_REFS_LIST);
	}
	
	
	// **************** implied mapping file ref *******************************
	
	public MappingFileRef getImpliedMappingFileRef() {
		return impliedMappingFileRef;
	}
	
	protected MappingFileRef setImpliedMappingFileRef() {
		if (impliedMappingFileRef != null) {
			throw new IllegalStateException("The implied mapping file ref is already set."); //$NON-NLS-1$
		}
		MappingFileRef mappingFileRef = buildMappingFileRef(null);
		impliedMappingFileRef = mappingFileRef;
		firePropertyChanged(IMPLIED_MAPPING_FILE_REF_PROPERTY, null, mappingFileRef);
		return mappingFileRef;
	}
	
	protected void unsetImpliedMappingFileRef() {
		if (impliedMappingFileRef == null) {
			throw new IllegalStateException("The implied mapping file ref is already unset."); //$NON-NLS-1$
		}
		MappingFileRef mappingFileRef = impliedMappingFileRef;
		impliedMappingFileRef.dispose();
		impliedMappingFileRef = null;
		firePropertyChanged(IMPLIED_MAPPING_FILE_REF_PROPERTY, mappingFileRef, null);
	}
	
	
	// **************** class refs *********************************************
	
	@SuppressWarnings("unchecked")
	public ListIterator<ClassRef> classRefs() {
		return new ReadOnlyCompositeListIterator<ClassRef>(
			specifiedClassRefs(), impliedClassRefs());
	}
	
	public int classRefsSize() {
		return specifiedClassRefsSize() + impliedClassRefsSize();
	}
	
	// **************** specified class refs ***********************************
	
	public ListIterator<ClassRef> specifiedClassRefs() {
		return new CloneListIterator<ClassRef>(this.specifiedClassRefs);
	}
	
	public int specifiedClassRefsSize() {
		return specifiedClassRefs.size();
	}
	
	public ClassRef addSpecifiedClassRef() {
		return addSpecifiedClassRef(this.specifiedClassRefsSize());
	}
	
	public ClassRef addSpecifiedClassRef(int index) {
		XmlJavaClassRef xmlClassRef = PersistenceFactory.eINSTANCE.createXmlJavaClassRef();
		ClassRef classRef = buildClassRef(xmlClassRef);
		this.specifiedClassRefs.add(index, classRef);
		this.xmlPersistenceUnit.getClasses().add(index, xmlClassRef);
		fireItemAdded(SPECIFIED_CLASS_REFS_LIST, index, classRef);
		return classRef;
	}

	public void removeSpecifiedClassRef(ClassRef classRef) {
		removeSpecifiedClassRef(this.specifiedClassRefs.indexOf(classRef));
	}
	
	public void removeSpecifiedClassRef(int index) {
		ClassRef classRefRemoved = this.specifiedClassRefs.remove(index);
		classRefRemoved.dispose();
		this.xmlPersistenceUnit.getClasses().remove(index);
		fireItemRemoved(SPECIFIED_CLASS_REFS_LIST, index, classRefRemoved);
	}
	
	protected void addSpecifiedClassRef_(ClassRef classRef) {
		addSpecifiedClassRef_(this.specifiedClassRefs.size(), classRef);
	}
	
	protected void addSpecifiedClassRef_(int index, ClassRef classRef) {
		addItemToList(index, classRef, this.specifiedClassRefs, SPECIFIED_CLASS_REFS_LIST);
	}
	
	protected void removeSpecifiedClassRef_(ClassRef classRef) {
		classRef.dispose();
		this.removeItemFromList(classRef, this.specifiedClassRefs, SPECIFIED_CLASS_REFS_LIST);
	}
	
	
	// **************** implied class refs *************************************
	
	public ListIterator<ClassRef> impliedClassRefs() {
		return new CloneListIterator<ClassRef>(impliedClassRefs);
	}
	
	public int impliedClassRefsSize() {
		return impliedClassRefs.size();
	}
	
	protected ClassRef addImpliedClassRef(String className) {
		return this.addImpliedClassRef(this.impliedClassRefs.size(), className);
	}
	
	protected ClassRef addImpliedClassRef(int index, String className) {
		ClassRef classRef = buildClassRef(className);
		addItemToList(index, classRef, this.impliedClassRefs, IMPLIED_CLASS_REFS_LIST);
		return classRef;
	}
	
	protected void removeImpliedClassRef(ClassRef classRef) {
		classRef.dispose();
		this.removeItemFromList(classRef, this.impliedClassRefs, IMPLIED_CLASS_REFS_LIST);
	}
	
	
	// **************** exclude unlisted classes *******************************
	
	public boolean isExcludeUnlistedClasses() {
		return getSpecifiedExcludeUnlistedClasses() == null ? getDefaultExcludeUnlistedClasses() : getSpecifiedExcludeUnlistedClasses().booleanValue();
	}
	
	public Boolean getSpecifiedExcludeUnlistedClasses() {
		return this.specifiedExcludeUnlistedClasses;
	}
	
	public void setSpecifiedExcludeUnlistedClasses(Boolean newExcludeUnlistedClasses) {
		Boolean oldExcludeUnlistedClasses = this.specifiedExcludeUnlistedClasses;
		this.specifiedExcludeUnlistedClasses = newExcludeUnlistedClasses;
		
		this.xmlPersistenceUnit.setExcludeUnlistedClasses(this.specifiedExcludeUnlistedClasses);
		
		firePropertyChanged(SPECIFIED_EXCLUDE_UNLISTED_CLASSES_PROPERTY, oldExcludeUnlistedClasses, newExcludeUnlistedClasses);
	}

	public boolean getDefaultExcludeUnlistedClasses() {
		// TODO - calculate default
		//  This is determined from the project
		return this.defaultExcludeUnlistedClasses;
	}


	// **************** properties *********************************************
	
	public ListIterator<Property> properties() {
		return new CloneListIterator<Property>(this.properties);
	}

	public int propertiesSize() {
		return this.properties.size();
	}

	public Property getProperty(String key) {
		if (key == null) {
			throw new IllegalStateException("Cannot getProperty: key is null."); //$NON-NLS-1$
		}
		for(Property property : this.properties) {
			if(key.equals(property.getName())) {
				return property;
			}
		}
		return null;
	}

	public ListIterator<Property> propertiesWithPrefix(String keyPrefix) {
		if (keyPrefix == null) {
			throw new IllegalStateException("Cannot find propertiesWithPrefix: keyPrefix is null."); //$NON-NLS-1$
		}
		List<Property> result = new ArrayList<Property>();
		
		for(Property property : this.properties) {
			if(property.getName() != null && property.getName().startsWith(keyPrefix)) {
				result.add( property);
			}
		}
		return result.listIterator();
	}
	
	public Property getProperty(String key, String value) {
		if (key == null || value == null) {
			throw new IllegalStateException("Cannot getProperty: key or value is null."); //$NON-NLS-1$
		}
		for(Property property : this.properties) {
			if(key.equals(property.getName())) {
				if(value.equals(property.getValue())) {
						return property;
				}
			}
		}
		return null;
	}
	
	protected Property getProperty(int index) {
		return this.properties.get(index);
	}

	protected XmlProperty getXmlProperty(String propertyName, String value) {
		if (this.xmlPersistenceUnit.getProperties() == null) {
			XmlProperties xmlProperties = PersistenceFactory.eINSTANCE.createXmlProperties();
			this.xmlPersistenceUnit.setProperties(xmlProperties);
		}
		for(XmlProperty xmlProperty : this.xmlPersistenceUnit.getProperties().getProperties()) {
			if(propertyName.equals(xmlProperty.getName()) && value.equals(xmlProperty.getValue())) {
				return xmlProperty;
			}
		}
		return null;
	}

	/**
	 * Adds or Changes Property with the given key and value.
	 */
	public void putProperty(String key, String value, boolean allowDuplicates) {
		if( ! allowDuplicates && this.containsProperty(key)) {
			this.putXmlProperty(key, value, this.getProperty(key).getValue());
			return;
		}
		if( value != null) {
			XmlProperty xmlProperty = PersistenceFactory.eINSTANCE.createXmlProperty();
			xmlProperty.setName(key);
			xmlProperty.setValue(value);
			
			this.addXmlProperty(xmlProperty, propertiesSize());
			return;
		}
	}
	
	public void replacePropertyValue(String key, String oldValue, String newValue) {
		
		this.putXmlProperty(key, newValue, oldValue);
	}
	
	protected void putXmlProperty(String key, String value, String oldValue) {
		if( value == null) {
			this.removeProperty(key);
			return;
		}

		XmlProperty xmlProperty = this.getXmlProperty(key, oldValue);
		if(xmlProperty == null) {
			throw new NoSuchElementException("Missing Property name: " + key + ", value: " + oldValue); //$NON-NLS-1$ //$NON-NLS-2$
		}
		xmlProperty.setValue(value);
	}	
	
	public boolean containsProperty(String key) {
		return (this.getProperty(key) != null);
	}

	public Property addProperty() {
		return addProperty(propertiesSize());
	}
	
	public Property addProperty(int index) {
		return this.addXmlProperty(PersistenceFactory.eINSTANCE.createXmlProperty(), index);
	}
	
	protected Property addXmlProperty(XmlProperty xmlProperty, int index) {

		Property property = buildProperty(xmlProperty);
		
		if (this.xmlPersistenceUnit.getProperties() == null) {
			XmlProperties xmlProperties = PersistenceFactory.eINSTANCE.createXmlProperties();
			this.xmlPersistenceUnit.setProperties(xmlProperties);
		}
		
		this.properties.add(index, property);
		this.xmlPersistenceUnit.getProperties().getProperties().add(index, xmlProperty);
		this.fireItemAdded(PROPERTIES_LIST, index, property);
		return property;
	}
	
	public void removeProperty(String key) {
		this.removeProperty(this.getProperty(key));
	}
	
	public void removeProperty(String key, String value) {
		this.removeProperty(this.getProperty(key, value));
	}
	
	public void removeProperty(Property property) {
		if (property != null) {
			this.removeProperty(this.properties.indexOf(property));
		}
	}
	
	protected void removeProperty(int index) {
		Property propertyRemoved = this.properties.remove(index);
		this.xmlPersistenceUnit.getProperties().getProperties().remove(index);
		
		if (this.xmlPersistenceUnit.getProperties().getProperties().isEmpty()) {
			this.xmlPersistenceUnit.setProperties(null);
		}
		
		fireItemRemoved(PROPERTIES_LIST, index, propertyRemoved);
	}
	
	protected void addProperty_(Property property) {
		addProperty_(this.properties.size(), property);
	}
	
	protected void addProperty_(int index, Property property) {
		addItemToList(index, property, this.properties, PROPERTIES_LIST);
	}
	
	protected void removeProperty_(Property property) {
		this.removeItemFromList(property, this.properties, PROPERTIES_LIST);
	}
	
	
	// **************** Persistence Unit Defaults *********************************************
	
	//TODO validation for multiple persistenceUnitDefaults.
	
	//Take the first MappingFilePersistenceUnitDefaults found in a mapping file and use
	//this for the defaults of the PersistenceUnit.
	protected MappingFilePersistenceUnitDefaults getPersistenceUnitDefaults() {
		for (Iterator<MappingFileRef> stream= this.mappingFileRefs(); stream.hasNext(); ) {
			MappingFilePersistenceUnitDefaults defaults = stream.next().getPersistenceUnitDefaults();
			if (defaults != null) {
				return defaults;
			}
		}
		return null;
	}

	public AccessType getDefaultAccess() {
		return this.defaultAccess;
	}
	
	protected void setDefaultAccess(AccessType access) {
		AccessType old = this.defaultAccess;
		this.defaultAccess = access;
		this.firePropertyChanged(DEFAULT_ACCESS_PROPERTY, old, access);
	}	
	
	public String getDefaultCatalog() {
		return this.defaultCatalog;
	}
	
	protected void setDefaultCatalog(String catalog) {
		String old = this.defaultCatalog;
		this.defaultCatalog = catalog;
		this.firePropertyChanged(DEFAULT_CATALOG_PROPERTY, old, catalog);
	}
	
	public String getDefaultSchema() {
		return this.defaultSchema;
	}
	
	protected void setDefaultSchema(String schema) {
		String old = this.defaultSchema;
		this.defaultSchema = schema;
		this.firePropertyChanged(DEFAULT_SCHEMA_PROPERTY, old, schema);
	}
	
	public boolean getDefaultCascadePersist() {
		return this.defaultCascadePersist;
	}
	
	protected void setDefaultCascadePersist(boolean cascadePersist) {
		boolean old = this.defaultCascadePersist;
		this.defaultCascadePersist = cascadePersist;
		this.firePropertyChanged(DEFAULT_CASCADE_PERSIST_PROPERTY, old, cascadePersist);
	}
	
	
	// **************** generators *********************
	
	public void addGenerator(Generator generator) {
		this.generators.add(generator);
	}
	
	public ListIterator<Generator> allGenerators() {
		return new CloneListIterator<Generator>(this.generators);
	}

	protected Iterator<String> allGeneratorNames() {
		return new TransformationIterator<Generator, String>(this.allGenerators()) {
			@Override
			protected String transform(Generator generator) {
				return generator.getName();
			}
		};
	}
	
	protected Iterator<String> allNonNullGeneratorNames() {
		return new FilteringIterator<String, String>(this.allGeneratorNames()) {
			@Override
			protected boolean accept(String generatorName) {
				return generatorName != null;
			}
		};
	}
	
	public String[] uniqueGeneratorNames() {
		HashSet<String> names = CollectionTools.set(this.allNonNullGeneratorNames());
		return names.toArray(new String[names.size()]);
	}

	
	// **************** queries *********************
	
	public void addQuery(Query query) {
		this.queries.add(query);
	}
	
	public ListIterator<Query> allQueries() {
		return new CloneListIterator<Query>(this.queries);
	}
	
	
	// **************** updating ***********************************************
	
	/**
	 * Concrete subclass must call this in the constructor, not called here
	 */
	protected void initialize(XmlPersistenceUnit xpu) {
		this.xmlPersistenceUnit = xpu;
		this.name = xpu.getName();
		
		initializeProperties(xpu);
		//initialize specified classRefs before mappingFileRefs because of 
		//JpaFile rootStructureNode, we want the mapping file to "win",
		//as it would in a Jpa runtime implementation
		initializeSpecifiedClassRefs(xpu);
		initializeMappingFileRefs(xpu);
		//initialize implied classRefs last since they depend on both
		//specified classRefs and mappingFileRefs
		initializeImpliedClassRefs(xpu);
		initializePersistenceUnitDefaults();
		this.specifiedExcludeUnlistedClasses = xpu.getExcludeUnlistedClasses();
		this.specifiedTransactionType = specifiedTransactionType(xpu);
		this.defaultTransactionType = defaultTransacationType();
		this.description = xpu.getDescription();
		this.provider = xpu.getProvider();
		this.jtaDataSource = xpu.getJtaDataSource();
		this.nonJtaDataSource = xpu.getNonJtaDataSource();
		this.specifiedExcludeUnlistedClasses = xpu.getExcludeUnlistedClasses();
	}
	
	protected void initializeMappingFileRefs(XmlPersistenceUnit xpu) {
		for (XmlMappingFileRef xmlMappingFileRef : xpu.getMappingFiles()) {
			specifiedMappingFileRefs.add(buildMappingFileRef(xmlMappingFileRef));
		}
		if (! impliedMappingFileIsSpecified() && impliedMappingFileExists()) {
			impliedMappingFileRef = buildMappingFileRef(null);
		}
	}
	
	protected void initializeSpecifiedClassRefs(XmlPersistenceUnit xpu) {
		for (XmlJavaClassRef xmlJavaClassRef : xpu.getClasses()) {
			specifiedClassRefs.add(buildClassRef(xmlJavaClassRef));
		}
	}
	
	protected void initializeImpliedClassRefs(XmlPersistenceUnit xpu) {
		if (getJpaProject().discoversAnnotatedClasses() && ! isExcludeUnlistedClasses()) {
			for (String typeName : CollectionTools.iterable(this.getJpaProject().annotatedClassNames())) {
				if ( ! classIsSpecified(typeName)) {
					impliedClassRefs.add(buildClassRef(typeName));
				}
			}
		}
	}
	
	protected void initializeProperties(XmlPersistenceUnit xpu) {
		XmlProperties xmlProperties = xpu.getProperties();
		if (xmlProperties == null) {
			return;
		}
		for (XmlProperty xmlProperty : xmlProperties.getProperties()) {
			this.properties.add(buildProperty(xmlProperty));
		}
	}
	
	protected void initializePersistenceUnitDefaults() {
		MappingFilePersistenceUnitDefaults defaults = this.getPersistenceUnitDefaults();
		this.defaultAccess = this.buildDefaultAccess(defaults);
		this.defaultCatalog = this.buildDefaultCatalog(defaults);
		this.defaultSchema = this.buildDefaultSchema(defaults);
		this.defaultCascadePersist = this.buildDefaultCascadePersist(defaults);
	}

	public void update(XmlPersistenceUnit persistenceUnit) {
		this.xmlPersistenceUnit = persistenceUnit;
		this.generators.clear();
		this.queries.clear();
		updateName(persistenceUnit);
		updateSpecifiedTransactionType(persistenceUnit);
		updateDefaultTransactionType();
		updateDescription(persistenceUnit);
		updateProvider(persistenceUnit);
		updateJtaDataSource(persistenceUnit);
		updateNonJtaDataSource(persistenceUnit);
		//update specified classRefs before mappingFileRefs because of 
		//JpaFile rootStructureNode, we want the mapping file to "win",
		//as it would in a Jpa runtime implementation
		updateSpecifiedClassRefs(persistenceUnit);
		updateMappingFileRefs(persistenceUnit);
		//update implied classRefs last since they depend on both
		//specified classRefs and mappingFileRefs
		updateImpliedClassRefs();
		updateExcludeUnlistedClasses(persistenceUnit);
		updateProperties(persistenceUnit);
		updatePersistenceUnitDefaults();
		generatorRepositoryUpdated();
		queryRepositoryUpdated();
	}
	
	protected void updateName(XmlPersistenceUnit persistenceUnit) {
		setName(persistenceUnit.getName());
	}
	
	protected void updateSpecifiedTransactionType(XmlPersistenceUnit persistenceUnit) {
		setSpecifiedTransactionType(specifiedTransactionType(persistenceUnit));
	}
	
	protected PersistenceUnitTransactionType specifiedTransactionType(XmlPersistenceUnit persistenceUnit) {
		return PersistenceUnitTransactionType.fromXmlResourceModel(persistenceUnit.getTransactionType());
	}
	
	protected void updateDefaultTransactionType() {
		setDefaultTransactionType(defaultTransacationType());
	}
	
	protected PersistenceUnitTransactionType defaultTransacationType() {
		// TODO - calculate default
		//  From the JPA spec: "In a Java EE environment, if this element is not 
		//  specified, the default is JTA. In a Java SE environment, if this element 
		// is not specified, a default of RESOURCE_LOCAL may be assumed."
		return null;
	}
	
	protected void updateDescription(XmlPersistenceUnit persistenceUnit) {
		setDescription(persistenceUnit.getDescription());
	}
	
	protected void updateProvider(XmlPersistenceUnit persistenceUnit) {
		setProvider(persistenceUnit.getProvider());
	}
	
	protected void updateJtaDataSource(XmlPersistenceUnit persistenceUnit) {
		setJtaDataSource(persistenceUnit.getJtaDataSource());
	}
	
	protected void updateNonJtaDataSource(XmlPersistenceUnit persistenceUnit) {
		setNonJtaDataSource(persistenceUnit.getNonJtaDataSource());
	}
	
	protected void updateMappingFileRefs(XmlPersistenceUnit persistenceUnit) {
		Iterator<MappingFileRef> stream = specifiedMappingFileRefs();
		Iterator<XmlMappingFileRef> stream2 = new CloneIterator<XmlMappingFileRef>(persistenceUnit.getMappingFiles());//prevent ConcurrentModificiationException
		
		while (stream.hasNext()) {
			MappingFileRef mappingFileRef = stream.next();
			if (stream2.hasNext()) {
				mappingFileRef.update(stream2.next());
			}
			else {
				removeSpecifiedMappingFileRef_(mappingFileRef);
			}
		}
		
		while (stream2.hasNext()) {
			addSpecifiedMappingFileRef_(buildMappingFileRef(stream2.next()));
		}
		
		if (impliedMappingFileIsSpecified()) {
			if (impliedMappingFileRef != null) {
				unsetImpliedMappingFileRef();
			}
		}
		else {
			if (impliedMappingFileExists()) {
				if (impliedMappingFileRef == null) {
					setImpliedMappingFileRef();
				}
				getImpliedMappingFileRef().update(null);
			}
			else {
				if (impliedMappingFileRef != null) {
					unsetImpliedMappingFileRef();
				}
			}
		}
	}
	
	protected boolean impliedMappingFileIsSpecified() {
		String impliedMappingFile = JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH;
		for (MappingFileRef each : specifiedMappingFileRefs) {
			if (impliedMappingFile.equals(each.getFileName())) {
				return true;
			}
		}
		return false;
	}
	
	protected boolean impliedMappingFileExists() {
		OrmResourceModelProvider modelProvider = 
			OrmResourceModelProvider.getDefaultModelProvider(getJpaProject().getProject());
		OrmResource resource = modelProvider.getResource();
		return resource != null && resource.exists();
	}
	
	protected MappingFileRef buildMappingFileRef(XmlMappingFileRef xmlMappingFileRef) {
		return getJpaFactory().buildMappingFileRef(this, xmlMappingFileRef);
	}
	
	//this is not being changed to match updateImpliedClassRefs.  In the xml,
	//changing the class name does not imply that a new object needs to be created.
	//If anything we should be matching ClassRefs with the corresponding XmlJavaClassRef
	//and if there is not one the ClassRef would be removed.  We would not want to 
	//do a name match as is done for the implied class refs.
	protected void updateSpecifiedClassRefs(XmlPersistenceUnit persistenceUnit) {
		Iterator<ClassRef> contextClassRefs = specifiedClassRefs();
		Iterator<XmlJavaClassRef> resourceClassRefs = new CloneIterator<XmlJavaClassRef>(persistenceUnit.getClasses());//prevent ConcurrentModificiationException
		
		while (contextClassRefs.hasNext()) {
			ClassRef contextClassRef = contextClassRefs.next();
			if (resourceClassRefs.hasNext()) {
				contextClassRef.update(resourceClassRefs.next());
			}
			else {
				removeSpecifiedClassRef_(contextClassRef);
			}
		}
		
		while (resourceClassRefs.hasNext()) {
			addSpecifiedClassRef_(buildClassRef(resourceClassRefs.next()));
		}
	}
	
	protected void updateImpliedClassRefs() {
		if (isExcludeUnlistedClasses()) {
			for (ClassRef classRef : CollectionTools.iterable(impliedClassRefs())) {
				removeImpliedClassRef(classRef);
			}
			return;
		}
		
		Iterator<String> annotatedClassNames = getJpaProject().annotatedClassNames();
		Collection<ClassRef> impliedRefsToRemove = CollectionTools.collection(impliedClassRefs());
		Collection<ClassRef> impliedRefsToUpdate = new ArrayList<ClassRef>();
		
		while (annotatedClassNames.hasNext()) {
			String annotatedClassName = annotatedClassNames.next();
			boolean impliedRefFound = false;
			if (!classIsSpecified(annotatedClassName)) {
				for (ClassRef classRef : impliedRefsToRemove) {
					if (annotatedClassName.equals(classRef.getClassName())) {
						impliedRefsToRemove.remove(classRef);
						impliedRefsToUpdate.add(classRef);
						impliedRefFound = true;
						break;
					}
				}
				if (!impliedRefFound) {
					addImpliedClassRef(annotatedClassName);
				}
			}
		}
		for (ClassRef classRef : impliedRefsToRemove) {
			removeImpliedClassRef(classRef);
		}
		//first handle adding/removing of the implied class refs, then update the others last, 
		//this causes less churn in the update process
		for (ClassRef classRef : impliedRefsToUpdate) {
			classRef.update(classRef.getClassName());
		}
	}
	
	protected ClassRef buildClassRef(XmlJavaClassRef xmlClassRef) {
		return getJpaFactory().buildClassRef(this, xmlClassRef);
	}
	
	protected ClassRef buildClassRef(String className) {
		return getJpaFactory().buildClassRef(this, className);
	}
	
	/**
	 * Return true if the class is specified either as a class
	 * or listed in a mapping file
	 */
	protected boolean classIsSpecified(String className) {
		for (ClassRef specifiedClassRef : CollectionTools.iterable(specifiedClassRefs())) {
			if (className.equals(specifiedClassRef.getClassName())) {
				return true;
			}
		}
		for (Iterator<MappingFileRef> stream = this.mappingFileRefs(); stream.hasNext(); ) {
			if (stream.next().getPersistentType(className) != null) {
				return true;
			}
		}
		return false;
	}
	
	protected void updateExcludeUnlistedClasses(XmlPersistenceUnit persistenceUnit) {
		setSpecifiedExcludeUnlistedClasses(persistenceUnit.getExcludeUnlistedClasses());
	}

	protected void updateProperties(XmlPersistenceUnit persistenceUnit) {
		List<XmlProperty> xmlProperties = 	this.buildXmlProperties(persistenceUnit);
		
		Iterator<Property> stream = this.properties();
		while (stream.hasNext()) {
			Property property = stream.next();
			XmlProperty xmlProperty = this.getXmlPropertyNamed(property.getName(), xmlProperties);
			if (xmlProperty != null) {
				property.update(xmlProperty);
				
				xmlProperties.remove(xmlProperty);
			}
			else {
				this.removeProperty_(property);
			}
		}

		for (XmlProperty xmlProperty : xmlProperties) {
			this.addProperty_(this.buildProperty(xmlProperty));
		}
	}
	
	protected Property buildProperty(XmlProperty xmlProperty) {
		return getJpaFactory().buildProperty(this, xmlProperty);
	}

	protected List<XmlProperty> buildXmlProperties(XmlPersistenceUnit persistenceUnit) {
		if (persistenceUnit.getProperties() == null) {
			return new ArrayList<XmlProperty>();
		}
		return CollectionTools.list(persistenceUnit.getProperties().getProperties());
	}
	
	protected XmlProperty getXmlPropertyNamed(String name, List<XmlProperty> xmlProperties) {
		for(XmlProperty xmlProperty : xmlProperties) {
			String xmlPropertyName = xmlProperty.getName();
			if(xmlPropertyName == name || xmlPropertyName.equals(name)) {
				return xmlProperty;
			};
		}
		return null;
	}
		
	protected void updatePersistenceUnitDefaults() {
		MappingFilePersistenceUnitDefaults defaults = getPersistenceUnitDefaults();
		this.setDefaultAccess(this.buildDefaultAccess(defaults));
		this.setDefaultCatalog(this.buildDefaultCatalog(defaults));
		this.setDefaultSchema(this.buildDefaultSchema(defaults));
		this.setDefaultCascadePersist(this.buildDefaultCascadePersist(defaults));
	}

	protected AccessType buildDefaultAccess(MappingFilePersistenceUnitDefaults defaults) {
		return (defaults == null) ? null : defaults.getAccess();
	}
	
	protected String buildDefaultCatalog(MappingFilePersistenceUnitDefaults defaults) {
		String catalog = (defaults == null) ? null : defaults.getCatalog();
		return (catalog != null) ? catalog : this.getJpaProject().getDefaultCatalog();
	}

	protected String buildDefaultSchema(MappingFilePersistenceUnitDefaults defaults) {
		String schema = (defaults == null) ? null : defaults.getSchema();
		return (schema != null) ? schema : this.getJpaProject().getDefaultSchema();
	}
	
	protected boolean buildDefaultCascadePersist(MappingFilePersistenceUnitDefaults defaults) {
		return (defaults == null) ? false : defaults.isCascadePersist();
	}
	
	// This is called after the persistence unit has been updated to ensure
	// we catch all added generators
	protected void generatorRepositoryUpdated() {
		fireListChanged(GENERATORS_LIST);
	}
	
	// This is called after the persistence unit has been updated to ensure
	// we catch all added queries
	protected void queryRepositoryUpdated() {
		fireListChanged(QUERIES_LIST);
	}
	
	
	// ********** Validation ***********************************************
	
	@Override
	public void validate(List<IMessage> messages) {
		super.validate(messages);
		this.validateMappingFiles(messages);	
		this.validateClassRefs(messages);
	}
	
	protected void validateMappingFiles(List<IMessage> messages) {
		this.checkForMultiplePersistenceUnitDefaults(messages);
		this.checkForDuplicateMappingFiles(messages);
		for (Iterator<MappingFileRef> stream = this.mappingFileRefs(); stream.hasNext();) {
			stream.next().validate(messages);
		}
	}
	
	protected void checkForMultiplePersistenceUnitDefaults(List<IMessage> messages) {
		boolean foundDefaultsToUse = false;
		Iterator<MappingFileRef> stream = mappingFileRefs();
		while (stream.hasNext() && ! foundDefaultsToUse) {
			MappingFileRef mappingFileRef = stream.next();
			MappingFile mappingFile = mappingFileRef.getMappingFile();
			if (mappingFile != null 
					&& mappingFile.getRoot() != null 
					&& mappingFile.getRoot().getPersistenceUnitDefaults() != null
					&& mappingFile.getRoot().getPersistenceUnitDefaults().resourceExists()) {
				foundDefaultsToUse = true;
			}
		}
		while (stream.hasNext()) {
			MappingFileRef mappingFileRef = stream.next();
			MappingFile mappingFile = mappingFileRef.getMappingFile();
			if (mappingFile != null 
				&& mappingFile.getRoot() != null 
				&& mappingFile.getRoot().getPersistenceUnitDefaults() != null
				&& mappingFile.getRoot().getPersistenceUnitDefaults().resourceExists()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.NORMAL_SEVERITY,
						JpaValidationMessages.MAPPING_FILE_EXTRANEOUS_PERSISTENCE_UNIT_DEFAULTS,
						new String[] {mappingFileRef.getFileName()},
						mappingFileRef
					)
				);
			}
		}
	}
	
	protected void checkForDuplicateMappingFiles(List<IMessage> messages) {
		HashBag<String> fileNames = new HashBag<String>();
		CollectionTools.addAll(fileNames, this.mappingFileRefNames());
		for (MappingFileRef mappingFileRef : CollectionTools.iterable(this.mappingFileRefs())) {
			String fileName = mappingFileRef.getFileName();
			if (fileNames.count(fileName) > 1) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.PERSISTENCE_UNIT_DUPLICATE_MAPPING_FILE,
						new String[] {fileName}, 
						mappingFileRef, 
						mappingFileRef.getValidationTextRange()
					)
				);
			}
		}
	}
		
	protected Iterator<String> mappingFileRefNames() {
		return new TransformationIterator<MappingFileRef, String>(this.mappingFileRefs()) {
			@Override
			protected String transform(MappingFileRef mappingFileRef) {
				return mappingFileRef.getFileName();
			}
		};
	}
	
	protected void validateClassRefs(List<IMessage> messages) {
		this.checkForDuplicateClasses(messages);
		for (Iterator<ClassRef> stream = this.classRefs(); stream.hasNext(); ) {
			stream.next().validate(messages);
		}
	}

	protected void checkForDuplicateClasses(List<IMessage> messages) {
		HashBag<String> classNames = new HashBag<String>();
		CollectionTools.addAll(classNames, this.classRefNames());
		for (ClassRef classRef : CollectionTools.iterable(this.classRefs())) {
			String className = classRef.getClassName();
			if ((className != null) && (classNames.count(className) > 1)) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.PERSISTENCE_UNIT_DUPLICATE_CLASS,
						new String[] {className}, 
						classRef, 
						classRef.getValidationTextRange()
					)
				);
			}
		}
	}
	
	protected Iterator<String> classRefNames() {
		return new TransformationIterator<ClassRef, String>(this.classRefs()) {
			@Override
			protected String transform(ClassRef classRef) {
				return classRef.getClassName();
			}
		};
	}

	protected Collection<MappingFile> buildMappingFiles() {
		ArrayList<MappingFile> result = new ArrayList<MappingFile>();
		for (Iterator<MappingFileRef> stream = this.mappingFileRefs(); stream.hasNext(); ) {
			MappingFile mappingFile = stream.next().getMappingFile();
			if (mappingFile != null) {
				result.add(mappingFile);
			}
		}
		return result;
	}
	
	//*************************************
	
	public PersistentType getPersistentType(String fullyQualifiedTypeName) {
		for (Iterator<MappingFileRef> stream = this.mappingFileRefs(); stream.hasNext(); ) {
			PersistentType persistentType = stream.next().getPersistentType(fullyQualifiedTypeName);
			if (persistentType != null) {
				return persistentType;
			}
		}
		for (ClassRef classRef : CollectionTools.iterable(classRefs())) {
			if (classRef.isFor(fullyQualifiedTypeName)) {
				return classRef.getJavaPersistentType();
			}
		}
		return null;
	}
	
	public Entity getEntity(String fullyQualifiedTypeName) {
		PersistentType persistentType = this.getPersistentType(fullyQualifiedTypeName);
		if (persistentType == null) {
			return null;
		}
		if (persistentType.getMappingKey() == MappingKeys.ENTITY_TYPE_MAPPING_KEY) {
			return (Entity) persistentType.getMapping();
		}
		return null;
	}
	
	public JpaStructureNode getStructureNode(int textOffset) {
		for (MappingFileRef mappingFileRef : CollectionTools.iterable(mappingFileRefs())) {
			if (mappingFileRef.containsOffset(textOffset)) {
				return mappingFileRef;
			}
		}
		for (ClassRef classRef : CollectionTools.iterable(classRefs())) {
			if (classRef.containsOffset(textOffset)) {
				return classRef;
			}
		}
		return this;
	}
	
	public boolean containsOffset(int textOffset) {
		if (xmlPersistenceUnit == null) {
			return false;
		}
		return xmlPersistenceUnit.containsOffset(textOffset);
	}
	
	public TextRange getSelectionTextRange() {
		return xmlPersistenceUnit.getSelectionTextRange();
	}
	
	public TextRange getValidationTextRange() {
		return xmlPersistenceUnit.getValidationTextRange();
	}
	
	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(getName());
	}
	
	public void dispose() {
		for (Iterator<ClassRef> stream = this.classRefs(); stream.hasNext(); ) {
			stream.next().dispose();
		}
		for (Iterator<MappingFileRef> stream = this.mappingFileRefs(); stream.hasNext(); ) {
			stream.next().dispose();
		}
	}
}
