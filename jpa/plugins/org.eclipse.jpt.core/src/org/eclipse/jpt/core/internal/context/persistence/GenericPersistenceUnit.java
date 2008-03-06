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

import static org.eclipse.jpt.core.context.persistence.PersistenceUnitTransactionType.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Set;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.Generator;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.Query;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.PersistenceUnitDefaults;
import org.eclipse.jpt.core.context.persistence.ClassRef;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.context.persistence.PersistenceStructureNodes;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.PersistenceUnitTransactionType;
import org.eclipse.jpt.core.context.persistence.Property;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.orm.OrmArtifactEdit;
import org.eclipse.jpt.core.resource.orm.OrmResource;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlJavaClassRef;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnitTransactionType;
import org.eclipse.jpt.core.resource.persistence.XmlProperties;
import org.eclipse.jpt.core.resource.persistence.XmlProperty;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.HashBag;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.ReadOnlyCompositeListIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class GenericPersistenceUnit extends AbstractPersistenceJpaContextNode
	implements PersistenceUnit
{
	protected XmlPersistenceUnit xmlPersistenceUnit;
	
	/* global generator definitions, defined elsewhere in model */
	protected final GenericGeneratorRepository generatorRepository;
	
	/* global query definitions, defined elsewhere in model */
	protected final GenericQueryRepository queryRepository;
	
	protected String name;
	
	protected PersistenceUnitTransactionType transactionType;
	
	protected PersistenceUnitTransactionType defaultTransactionType = DEFAULT;
	
	protected String description;
	
	protected String provider;
	
	protected String jtaDataSource;
	
	protected String nonJtaDataSource;
	
	protected final List<MappingFileRef> specifiedMappingFileRefs;
	
	protected MappingFileRef impliedMappingFileRef;
	
	protected final List<ClassRef> specifiedClassRefs;
	
	protected final List<ClassRef> impliedClassRefs;
	
	protected Boolean specifiedExcludeUnlistedClasses;
	
	protected boolean defaultExcludeUnlistedClasses = false;
	
	protected final List<Property> properties;
	
	
	protected String defaultSchema;
	protected String defaultCatalog;
	protected AccessType defaultAccess;
	protected boolean defaultCascadePersist;
	
	public GenericPersistenceUnit(Persistence parent, XmlPersistenceUnit persistenceUnit) {
		super(parent);
		this.generatorRepository = new GenericGeneratorRepository(this);
		this.queryRepository = new GenericQueryRepository(this);
		this.transactionType = PersistenceUnitTransactionType.DEFAULT;
		this.specifiedMappingFileRefs = new ArrayList<MappingFileRef>();
		this.specifiedClassRefs = new ArrayList<ClassRef>();
		this.impliedClassRefs = new ArrayList<ClassRef>();
		this.properties = new ArrayList<Property>();
		this.initialize(persistenceUnit);
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
	public PersistenceUnit persistenceUnit() {
		return this;
	}
	
	
	// **************** parent *************************************************
	
	@Override
	public Persistence parent() {
		return (Persistence) super.parent();
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
			getDefaultTransactionType() : this.transactionType;
	}
	
	public void setTransactionType(PersistenceUnitTransactionType newTransactionType) {
		if (newTransactionType == null) {
			throw new IllegalArgumentException("null");
		}
		PersistenceUnitTransactionType oldTransactionType = this.transactionType;
		this.transactionType = newTransactionType;
		
		if (this.transactionType == JTA) {
			this.xmlPersistenceUnit.setTransactionType(XmlPersistenceUnitTransactionType.JTA);
		}
		else if (this.transactionType == RESOURCE_LOCAL) {
			this.xmlPersistenceUnit.setTransactionType(XmlPersistenceUnitTransactionType.RESOURCE_LOCAL);
		}
		else if (this.transactionType == DEFAULT) {
			this.xmlPersistenceUnit.unsetTransactionType();
		}
		else {
			throw new IllegalArgumentException();
		}
		
		firePropertyChanged(TRANSACTION_TYPE_PROPERTY, oldTransactionType, newTransactionType);
	}
	
	public boolean isTransactionTypeDefault() {
		return this.transactionType == DEFAULT;
	}
	
	public void setTransactionTypeToDefault() {
		setTransactionType(DEFAULT);
	}
	
	public PersistenceUnitTransactionType getDefaultTransactionType() {
		// TODO - calculate default
		//  From the JPA spec: "In a Java EE environment, if this element is not 
		//  specified, the default is JTA. In a Java SE environment, if this element 
		// is not specified, a default of RESOURCE_LOCAL may be assumed."
		return this.defaultTransactionType;
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
		firePropertyChanged(DESCRIPTION_PROPERTY, oldProvider, newProvider);
	}
	
	
	// **************** jta data source ****************************************
	
	public String getJtaDataSource() {
		return this.jtaDataSource;
	}
	
	public void setJtaDataSource(String newJtaDataSource) {
		String oldJtaDataSource = this.jtaDataSource;
		this.jtaDataSource = newJtaDataSource;
		this.xmlPersistenceUnit.setJtaDataSource(newJtaDataSource);
		firePropertyChanged(DESCRIPTION_PROPERTY, oldJtaDataSource, newJtaDataSource);
	}
	
	
	// **************** non-jta data source ************************************
	
	public String getNonJtaDataSource() {
		return this.nonJtaDataSource;
	}
	
	public void setNonJtaDataSource(String newNonJtaDataSource) {
		String oldNonJtaDataSource = this.nonJtaDataSource;
		this.nonJtaDataSource = newNonJtaDataSource;
		this.xmlPersistenceUnit.setNonJtaDataSource(newNonJtaDataSource);
		firePropertyChanged(DESCRIPTION_PROPERTY, oldNonJtaDataSource, newNonJtaDataSource);
	}
	
	
	// **************** mapping file refs **************************************
	
	public ListIterator<MappingFileRef> mappingFileRefs() {
		if (impliedMappingFileRef == null) {
			return specifiedMappingFileRefs();
		}
		else {
			return new ReadOnlyCompositeListIterator<MappingFileRef>(
				specifiedMappingFileRefs(), impliedMappingFileRef);
		}
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
		this.xmlPersistenceUnit.getMappingFiles().add(xmlMappingFileRef);
		fireItemAdded(SPECIFIED_MAPPING_FILE_REF_LIST, index, mappingFileRef);
		return mappingFileRef;
	}
	
	public void removeSpecifiedMappingFileRef(MappingFileRef mappingFileRef) {
		removeSpecifiedMappingFileRef(specifiedMappingFileRefs.indexOf(mappingFileRef));
	}
	
	public void removeSpecifiedMappingFileRef(int index) {
		MappingFileRef mappingFileRefRemoved = specifiedMappingFileRefs.remove(index);
		this.xmlPersistenceUnit.getMappingFiles().remove(index);
		fireItemRemoved(SPECIFIED_MAPPING_FILE_REF_LIST, index, mappingFileRefRemoved);
	}
	
	protected void addSpecifiedMappingFileRef_(MappingFileRef mappingFileRef) {
		addSpecifiedMappingFileRef_(specifiedMappingFileRefs.size(), mappingFileRef);
	}
	
	protected void addSpecifiedMappingFileRef_(int index, MappingFileRef mappingFileRef) {
		addItemToList(index, mappingFileRef, specifiedMappingFileRefs, SPECIFIED_MAPPING_FILE_REF_LIST);
	}
	
	protected void removeSpecifiedMappingFileRef_(MappingFileRef mappingFileRef) {
		removeSpecifiedMappingFileRef_(specifiedMappingFileRefs.indexOf(mappingFileRef));
	}
	
	protected void removeSpecifiedMappingFileRef_(int index) {
		removeItemFromList(index, specifiedMappingFileRefs, SPECIFIED_MAPPING_FILE_REF_LIST);
	}
	
	
	// **************** implied mapping file ref *******************************
	
	public MappingFileRef getImpliedMappingFileRef() {
		return impliedMappingFileRef;
	}
	
	public MappingFileRef setImpliedMappingFileRef() {
		if (impliedMappingFileRef != null) {
			throw new IllegalStateException("The implied mapping file ref is already set.");
		}
		MappingFileRef mappingFileRef = buildMappingFileRef(null);
		impliedMappingFileRef = mappingFileRef;
		firePropertyChanged(IMPLIED_MAPPING_FILE_REF_PROPERTY, null, mappingFileRef);
		return mappingFileRef;
	}
	
	public void unsetImpliedMappingFileRef() {
		if (impliedMappingFileRef == null) {
			throw new IllegalStateException("The implie mapping file ref is already unset.");
		}
		MappingFileRef mappingFileRef = impliedMappingFileRef;
		impliedMappingFileRef = null;
		firePropertyChanged(IMPLIED_MAPPING_FILE_REF_PROPERTY, mappingFileRef, null);
	}
	
	
	// **************** class refs *********************************************
	
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
		return addSpecifiedClassRef(this.specifiedClassRefs.size());
	}
	
	public ClassRef addSpecifiedClassRef(int index) {
		XmlJavaClassRef xmlClassRef = PersistenceFactory.eINSTANCE.createXmlJavaClassRef();
		ClassRef classRef = buildClassRef(xmlClassRef);
		this.specifiedClassRefs.add(index, classRef);
		this.xmlPersistenceUnit.getClasses().add(xmlClassRef);
		fireItemAdded(SPECIFIED_CLASS_REF_LIST, index, classRef);
		return classRef;
	}
	
	public void removeSpecifiedClassRef(ClassRef classRef) {
		removeSpecifiedClassRef(this.specifiedClassRefs.indexOf(classRef));
	}
	
	public void removeSpecifiedClassRef(int index) {
		ClassRef classRefRemoved = this.specifiedClassRefs.remove(index);
		this.xmlPersistenceUnit.getClasses().remove(index);
		fireItemRemoved(SPECIFIED_CLASS_REF_LIST, index, classRefRemoved);
	}
	
	protected void addSpecifiedClassRef_(ClassRef classRef) {
		addSpecifiedClassRef_(this.specifiedClassRefs.size(), classRef);
	}
	
	protected void addSpecifiedClassRef_(int index, ClassRef classRef) {
		addItemToList(index, classRef, this.specifiedClassRefs, SPECIFIED_CLASS_REF_LIST);
	}
	
	protected void removeSpecifiedClassRef_(ClassRef classRef) {
		removeSpecifiedClassRef_(this.specifiedClassRefs.indexOf(classRef));
	}
	
	protected void removeSpecifiedClassRef_(int index) {
		removeItemFromList(index, this.specifiedClassRefs, SPECIFIED_CLASS_REF_LIST);
	}
	
	
	// **************** implied class refs *************************************
	
	public ListIterator<ClassRef> impliedClassRefs() {
		return new CloneListIterator<ClassRef>(impliedClassRefs);
	}
	
	public int impliedClassRefsSize() {
		return impliedClassRefs.size();
	}
	
	public ClassRef addImpliedClassRef(String className) {
		return addImpliedClassRef(impliedClassRefs.size(), className);
	}
	
	public ClassRef addImpliedClassRef(int index, String className) {
		ClassRef classRef = buildClassRef(className);
		addItemToList(classRef, impliedClassRefs, IMPLIED_CLASS_REF_LIST);
		return classRef;
	}
	
	public void removeImpliedClassRef(ClassRef classRef) {
		removeImpliedClassRef(impliedClassRefs.indexOf(classRef));
	}
	
	public void removeImpliedClassRef(int index) {
		removeItemFromList(index, impliedClassRefs, IMPLIED_CLASS_REF_LIST);
	}
	
	protected void addImpliedClassRef_(ClassRef classRef) {
		addImpliedClassRef_(impliedClassRefs.size(), classRef);
	}
	
	protected void addImpliedClassRef_(int index, ClassRef classRef) {
		addItemToList(index, classRef, impliedClassRefs, IMPLIED_CLASS_REF_LIST);
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
		
		firePropertyChanged(SPECIFIED_EXCLUDE_UNLISTED_CLASSED_PROPERTY, oldExcludeUnlistedClasses, newExcludeUnlistedClasses);
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
		for(Property property : this.properties) {
			if(property.getName().equals(key)) {
				return property;
			}
		}
		return null;
	}

	public ListIterator<Property> propertiesWithPrefix(String keyPrefix) {
		List<Property> properties = new ArrayList<Property>();
		
		for(Property property : this.properties) {
			if(property.getName().startsWith(keyPrefix)) {
				properties.add( property);
			}
		}
		return properties.listIterator();
	}
	
	public Property getProperty(String key, String value) {
		for(Property property : this.properties) {
			if(property.getName().equals(key) && property.getValue().equals(value)) {
				return property;
			}
		}
		return null;
	}
	
	protected Property getProperty(int index) {
		return this.properties.get(index);
	}

	protected XmlProperty getXmlProperty(String name, String value) {
		if (this.xmlPersistenceUnit.getProperties() == null) {
			XmlProperties xmlProperties = PersistenceFactory.eINSTANCE.createXmlProperties();
			this.xmlPersistenceUnit.setProperties(xmlProperties);
		}
		for(XmlProperty xmlProperty : this.xmlPersistenceUnit.getProperties().getProperties()) {
			if(name.equals(xmlProperty.getName()) && value.equals(xmlProperty.getValue())) {
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
			
			this.addXmlProperty(xmlProperty);
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
			throw new NoSuchElementException("Missing Property name: " + key + ", value: " + oldValue);
		}
		xmlProperty.setValue(value);
	}	
	
	public boolean containsProperty(String key) {
		return (this.getProperty(key) != null);
	}

	public Property addProperty() {
		XmlProperty xmlProperty = PersistenceFactory.eINSTANCE.createXmlProperty();
		
		return this.addXmlProperty(xmlProperty);
	}
	
	protected Property addXmlProperty(XmlProperty xmlProperty) {

		Property property = buildProperty(xmlProperty);
		int index = this.properties.size();
		this.properties.add(index, property);
		
		if (this.xmlPersistenceUnit.getProperties() == null) {
			XmlProperties xmlProperties = PersistenceFactory.eINSTANCE.createXmlProperties();
			this.xmlPersistenceUnit.setProperties(xmlProperties);
		}
		
		this.xmlPersistenceUnit.getProperties().getProperties().add(xmlProperty);
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
		removeProperty_(this.properties.indexOf(property));
	}
	
	protected void removeProperty_(int index) {
		removeItemFromList(index, this.properties, PROPERTIES_LIST);
	}
	
	
	// **************** Persistence Unit Defaults *********************************************
	
	//TODO validation for multiple persistenceUnitDefaults.
	
	//Take the first PersistenceUnitDefaults found in an orm.xml file and use
	//this for the defaults of the PersistenceUnit.
	protected PersistenceUnitDefaults persistenceUnitDefaults() {
		for (MappingFileRef mappingFileRef : CollectionTools.iterable(mappingFileRefs())) {
			PersistenceUnitDefaults persistenceUnitDefaults = mappingFileRef.persistenceUnitDefaults();
			if (persistenceUnitDefaults != null) {
				return persistenceUnitDefaults;
			}
		}
		return null;
	}

	public String getDefaultSchema() {
		return this.defaultSchema;
	}
	
	protected void setDefaultSchema(String newDefaultSchema) {
		String oldDefaultSchema = this.defaultSchema;
		this.defaultSchema = newDefaultSchema;
		firePropertyChanged(DEFAULT_SCHEMA_PROPERTY, oldDefaultSchema, newDefaultSchema);
	}
	
	public String getDefaultCatalog() {
		return this.defaultCatalog;
	}
	
	protected void setDefaultCatalog(String newDefaultCatalog) {
		String oldDefaultCatalog = this.defaultCatalog;
		this.defaultCatalog = newDefaultCatalog;
		firePropertyChanged(DEFAULT_CATALOG_PROPERTY, oldDefaultCatalog, newDefaultCatalog);
	}
	
	public AccessType getDefaultAccess() {
		return this.defaultAccess;
	}
	
	protected void setDefaultAccess(AccessType newDefaultAccess) {
		AccessType oldDefaultAccess = this.defaultAccess;
		this.defaultAccess = newDefaultAccess;
		firePropertyChanged(DEFAULT_ACCESS_PROPERTY, oldDefaultAccess, newDefaultAccess);
	}	
	
	public boolean getDefaultCascadePersist() {
		return this.defaultCascadePersist;
	}
	
	protected void setDefaultCascadePersist(boolean newDefaultCascadePersist) {
		boolean oldDefaultCascadePersist = this.defaultCascadePersist;
		this.defaultCascadePersist = newDefaultCascadePersist;
		firePropertyChanged(DEFAULT_CASCADE_PERSIST_PROPERTY, oldDefaultCascadePersist, newDefaultCascadePersist);
	}
	
	
	// **************** global generator and query support *********************
	
	public void addGenerator(Generator generator) {
		this.generatorRepository.add(generator);
	}
	
	public ListIterator<Generator> allGenerators() {
		return this.generatorRepository.allGenerators();
	}
	
	public void addQuery(Query query) {
		this.queryRepository.add(query);
	}
	
	public ListIterator<Query> allQueries() {
		return this.queryRepository.allQueries();
	}
	

	// **************** updating ***********************************************
	
	protected void initialize(XmlPersistenceUnit xmlPersistenceUnit) {
		this.xmlPersistenceUnit = xmlPersistenceUnit;
		this.name = xmlPersistenceUnit.getName();
		initializeMappingFileRefs(xmlPersistenceUnit);
		initializeClassRefs(xmlPersistenceUnit);
		initializeProperties(xmlPersistenceUnit);
		this.specifiedExcludeUnlistedClasses = xmlPersistenceUnit.getExcludeUnlistedClasses();
		//TODO more things to initialize
	}
	
	protected void initializeMappingFileRefs(XmlPersistenceUnit xmlPersistenceUnit) {
		for (XmlMappingFileRef xmlMappingFileRef : xmlPersistenceUnit.getMappingFiles()) {
			specifiedMappingFileRefs.add(buildMappingFileRef(xmlMappingFileRef));
		}
		if (! impliedMappingFileIsSpecified() && impliedMappingFileExists()) {
			impliedMappingFileRef = buildMappingFileRef(null);
		}
	}
	
	protected void initializeClassRefs(XmlPersistenceUnit xmlPersistenceUnit) {
		for (XmlJavaClassRef xmlJavaClassRef : xmlPersistenceUnit.getClasses()) {
			specifiedClassRefs.add(buildClassRef(xmlJavaClassRef));
		}
		
		if (jpaProject().discoversAnnotatedClasses() && !isExcludeUnlistedClasses()) {
			for (IType type : CollectionTools.iterable(jpaProject().annotatedClasses())) {
				if (! classIsSpecified(type.getFullyQualifiedName())) {
					impliedClassRefs.add(buildClassRef(type.getFullyQualifiedName()));
				}
			}
		}
	}
	
	protected void initializeProperties(XmlPersistenceUnit xmlPersistenceUnit) {
		XmlProperties xmlProperties = xmlPersistenceUnit.getProperties();
		if (xmlProperties == null) {
			return;
		}
		for (XmlProperty xmlProperty : xmlProperties.getProperties()) {
			this.properties.add(buildProperty(xmlProperty));
		}
	}
	
	protected void initializePersistenceUnitDefaults() {
		PersistenceUnitDefaults persistenceUnitDefaults = persistenceUnitDefaults();
		if (persistenceUnitDefaults != null) {
			this.defaultSchema = persistenceUnitDefaults.getSchema();
			this.defaultCatalog = persistenceUnitDefaults.getCatalog();
			this.defaultAccess = persistenceUnitDefaults.getAccess();
			this.defaultCascadePersist = persistenceUnitDefaults.isCascadePersist();
		}
		else {
			this.defaultSchema = null;
			this.defaultCatalog = null;
			this.defaultAccess = null;
			this.defaultCascadePersist = false;		
		}
	}

	public void update(XmlPersistenceUnit persistenceUnit) {
		this.xmlPersistenceUnit = persistenceUnit;
		this.generatorRepository.clear();
		this.queryRepository.clear();
		updateName(persistenceUnit);
		updateTransactionType(persistenceUnit);
		updateDescription(persistenceUnit);
		updateProvider(persistenceUnit);
		updateJtaDataSource(persistenceUnit);
		updateNonJtaDataSource(persistenceUnit);
		updateMappingFileRefs(persistenceUnit);
		updateClassRefs(persistenceUnit);
		updateExcludeUnlistedClasses(persistenceUnit);
		updateProperties(persistenceUnit);
		updatePersistenceUnitDefaults();
		generatorRepositoryUpdated();
		queryRepositoryUpdated();
	}
	
	protected void updateName(XmlPersistenceUnit persistenceUnit) {
		setName(persistenceUnit.getName());
	}
	
	protected void updateTransactionType(XmlPersistenceUnit persistenceUnit) {
		if (! persistenceUnit.isSetTransactionType()) {
			setTransactionType(DEFAULT);
		}
		else if (persistenceUnit.getTransactionType() == XmlPersistenceUnitTransactionType.JTA) {
			setTransactionType(JTA);
		}
		else if (persistenceUnit.getTransactionType() == XmlPersistenceUnitTransactionType.RESOURCE_LOCAL) {
			setTransactionType(RESOURCE_LOCAL);
		}
		else {
			throw new IllegalStateException();
		}
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
		Iterator<XmlMappingFileRef> stream2 = persistenceUnit.getMappingFiles().iterator();
		
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
		OrmArtifactEdit oae = OrmArtifactEdit.getArtifactEditForRead(jpaProject().project());
		OrmResource or = oae.getResource(JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH);
		boolean exists =  or != null && or.exists();
		oae.dispose();
		return exists;
	}
	
	protected MappingFileRef buildMappingFileRef(XmlMappingFileRef xmlMappingFileRef) {
		return jpaFactory().buildMappingFileRef(this, xmlMappingFileRef);
	}
	
	protected void updateClassRefs(XmlPersistenceUnit persistenceUnit) {
		Iterator<ClassRef> stream = specifiedClassRefs();
		Iterator<XmlJavaClassRef> stream2 = new CloneIterator<XmlJavaClassRef>(persistenceUnit.getClasses());
		
		while (stream.hasNext()) {
			ClassRef classRef = stream.next();
			if (stream2.hasNext()) {
				classRef.update(stream2.next());
			}
			else {
				removeSpecifiedClassRef_(classRef);
			}
		}
		
		while (stream2.hasNext()) {
			addSpecifiedClassRef_(buildClassRef(stream2.next()));
		}
		
		Iterator<ClassRef> impliedRefs = impliedClassRefs();
		Iterator<IType> annotatedClasses = jpaProject().annotatedClasses();
		
		
		if (jpaProject().discoversAnnotatedClasses() && !isExcludeUnlistedClasses()) {
			while (impliedRefs.hasNext()) {
				ClassRef classRef = impliedRefs.next();
				boolean updated = false;
				while (! updated && annotatedClasses.hasNext()) {
					IType annotatedClass = annotatedClasses.next();
					if (! classIsSpecified(annotatedClass.getFullyQualifiedName())) {
						classRef.update(annotatedClass.getFullyQualifiedName());
						updated = true;
					}
				}
				if (! annotatedClasses.hasNext() && ! updated) {
					removeImpliedClassRef(classRef);
				}
			}
			
			while (annotatedClasses.hasNext()) {
				IType annotatedClass = annotatedClasses.next();
				if (! classIsSpecified(annotatedClass.getFullyQualifiedName())) {
					addImpliedClassRef(annotatedClass.getFullyQualifiedName());
				}
			}
		}
		else {
			for (ClassRef classRef : CollectionTools.iterable(impliedClassRefs())) {
				removeImpliedClassRef(classRef);
			}
		}
	}
	
	protected ClassRef buildClassRef(XmlJavaClassRef xmlClassRef) {
		return jpaFactory().buildClassRef(this, xmlClassRef);
	}
	
	protected ClassRef buildClassRef(String className) {
		return jpaFactory().buildClassRef(this, className);
	}
	
	protected boolean classIsSpecified(String className) {
		for (ClassRef specifiedClassRef : CollectionTools.iterable(specifiedClassRefs())) {
			if (className.equals(specifiedClassRef.getClassName())) {
				return true;
			}
		}
		return false;
	}
	
	protected void updateExcludeUnlistedClasses(XmlPersistenceUnit persistenceUnit) {
		setSpecifiedExcludeUnlistedClasses(persistenceUnit.getExcludeUnlistedClasses());
	}
	
	protected void updateProperties(XmlPersistenceUnit persistenceUnit) {
		XmlProperties xmlProperties = persistenceUnit.getProperties();
		
		Iterator<Property> stream = properties();
		Iterator<XmlProperty> stream2;
		
		if (xmlProperties == null) {
			stream2 = EmptyIterator.instance();
		}
		else {
			stream2 = xmlProperties.getProperties().iterator();
		}
		
		while (stream.hasNext()) {
			Property property = stream.next();
			if (stream2.hasNext()) {
				property.update(stream2.next());
			}
			else {
				removeProperty_(property);
			}
		}
		
		while (stream2.hasNext()) {
			addProperty_(buildProperty(stream2.next()));
		}
	}
	
	protected Property buildProperty(XmlProperty xmlProperty) {
		return jpaFactory().buildProperty(this, xmlProperty);
	}
		
	protected void updatePersistenceUnitDefaults() {
		PersistenceUnitDefaults persistenceUnitDefaults = persistenceUnitDefaults();
		this.setDefaultSchema(this.schema(persistenceUnitDefaults));
		this.setDefaultCatalog(this.catalog(persistenceUnitDefaults));
		this.setDefaultAccess(this.access(persistenceUnitDefaults));
		this.setDefaultCascadePersist(this.cascadePersist(persistenceUnitDefaults));
	}

	protected String schema(PersistenceUnitDefaults persistenceUnitDefaults) {
		if (persistenceUnitDefaults != null) {
			if (persistenceUnitDefaults.getSchema() != null) {
				return persistenceUnitDefaults.getSchema();
			}
		}
		Schema projectDefaultSchema = projectDefaultSchema();
		return projectDefaultSchema == null ? null : projectDefaultSchema.getName();
	}
	
	protected Schema projectDefaultSchema() {
		return jpaProject().defaultSchema();
	}

	protected String catalog(PersistenceUnitDefaults persistenceUnitDefaults) {
		if (persistenceUnitDefaults != null) {
			if (persistenceUnitDefaults.getCatalog() != null) {
				return persistenceUnitDefaults.getCatalog();
			}
		}
		String catalog = projectDefaultCatalog();
		//the context model uses nulls for defaults that don't exist. currently
		//the DB model is using "" to represent no catalog, changing this here
		if (catalog == "") {
			catalog = null;
		}
		return catalog;
	}

	protected String projectDefaultCatalog() {
		return jpaProject().connectionProfile().getCatalogName();
	}
	
	protected AccessType access(PersistenceUnitDefaults persistenceUnitDefaults) {
		return persistenceUnitDefaults == null ? null : persistenceUnitDefaults.getAccess();
	}
	
	protected boolean cascadePersist(PersistenceUnitDefaults persistenceUnitDefaults) {
		return persistenceUnitDefaults == null ? false : persistenceUnitDefaults.isCascadePersist();
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
	public void addToMessages(List<IMessage> messages) {
		super.addToMessages(messages);
		addMappingFileMessages(messages);	
		addClassMessages(messages);
		addGeneratorMessages(messages);
		addQueryMessages(messages);
	}
	
	protected void addMappingFileMessages(List<IMessage> messages) {
		addMultipleMetadataMessages(messages);
		addDuplicateMappingFileMessages(messages);
		
		for (Iterator<MappingFileRef> stream =  mappingFileRefs(); stream.hasNext();) {
			stream.next().addToMessages(messages);
		}
	}
	
	protected void addMultipleMetadataMessages(List<IMessage> messages) {
		Collection<PersistenceUnitDefaults> puDefaultsCollection = persistenceUnitDefaultsForValidation();
		if (puDefaultsCollection.size() > 1) {
			for (PersistenceUnitDefaults puDefaults : puDefaultsCollection) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.ENTITY_MAPPINGS_MULTIPLE_METADATA,
						new String[] {this.getName()},
						puDefaults)
				);
			}
		}
	}
	
	protected void addDuplicateMappingFileMessages(List<IMessage> messages) {
		HashBag<String> fileBag = new HashBag<String>(
				CollectionTools.collection(
						new TransformationIterator<MappingFileRef, String>(this.mappingFileRefs()) {
							@Override
							protected String transform(MappingFileRef mappingFileRef) {
								return mappingFileRef.getFileName();
							}
						}
				)
		);
		for (MappingFileRef mappingFileRef : CollectionTools.collection(this.mappingFileRefs())) {
			if (fileBag.count(mappingFileRef.getFileName()) > 1) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.PERSISTENCE_UNIT_DUPLICATE_MAPPING_FILE,
						new String[] {mappingFileRef.getFileName()}, 
						mappingFileRef/*, 
						mappingFileRef.validationTextRange()*/)
				);
			}
		}
	}
		
	protected void addClassMessages(List<IMessage> messages) {
//		addInvalidOrRedundantClassMessages(messages);
		addDuplicateClassMessages(messages);
		
		for (ClassRef classRef : CollectionTools.collection(classRefs())) {
				classRef.addToMessages(messages);
		}
	}
	
//	protected void addInvalidOrRedundantClassMessages(List<IMessage> messages) {
//		for (IClassRef javaClassRef : CollectionTools.collection(this.classRefs())) {
//			IJavaPersistentType javaPersistentType = javaClassRef.getJavaPersistentType();
//			//*****leaving this test out for now********
//			//first test for a redundant entry in any of the mapping files
////			if (mappingFilesContainPersistentTypeFor(jdtType)){
////			//Uncomment below code to add info message about redundant entry.
////			/*	messages.add(JpaValidationMessages.buildMessage(
////						IMessage.LOW_SEVERITY,
////						IJpaValidationMessages.PERSISTENCE_UNIT_REDUNDANT_CLASS,
////						new String[] { javaClassRef.getJavaClass() },
////						javaClassRef, javaClassRef.validationTextRange())); */
////			//if not redundant, check to see if it is an invalid entry
////			} else if(!StringTools.stringIsEmpty(javaClass)
//			} if (!StringTools.stringIsEmpty(javaClass)
//					&& jdtType != null
//					&& (javaPersistentTypeFor(javaClassRef) == null || javaPersistentTypeFor(
//							javaClassRef).getMappingKey() == IMappingKeys.NULL_TYPE_MAPPING_KEY)){
//								messages.add(JpaValidationMessages.buildMessage(
//									IMessage.HIGH_SEVERITY,
//									IJpaValidationMessages.PERSISTENCE_UNIT_INVALID_CLASS,
//									new String[] { javaClassRef.getJavaClass() }, javaClassRef,
//									javaClassRef.validationTextRange()));
//			}
//		}
//	}
	
	
	protected void addDuplicateClassMessages(List<IMessage> messages) {
		HashBag<String> classNameBag = new HashBag<String>(
				CollectionTools.collection(
						new TransformationIterator<ClassRef, String>(this.classRefs()) {
							@Override
							protected String transform(ClassRef classRef) {
								return classRef.getClassName();
							}
						}
				)
		);
		for (ClassRef javaClassRef : CollectionTools.collection(this.classRefs())) {
			if (javaClassRef.getClassName() != null
					&& classNameBag.count(javaClassRef.getClassName()) > 1) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.PERSISTENCE_UNIT_DUPLICATE_CLASS,
						new String[] {javaClassRef.getClassName()}, 
						javaClassRef, 
						javaClassRef.validationTextRange())
				);
			}
		}
	}
	
	protected void addGeneratorMessages(List<IMessage> messages) {
		
	}
	
	protected void addQueryMessages(List<IMessage> messages) {
		
	}
	
	private Collection<PersistenceUnitDefaults> persistenceUnitDefaultsForValidation() {
		ArrayList<PersistenceUnitDefaults> puDefaults = new ArrayList<PersistenceUnitDefaults>();
		for (MappingFileRef mappingFileRef : CollectionTools.iterable(mappingFileRefs())) {
			PersistenceUnitDefaults persistenceUnitDefaults = mappingFileRef.persistenceUnitDefaults();
			if (persistenceUnitDefaults != null) {
				puDefaults.add(persistenceUnitDefaults);
			}
		}
		return puDefaults;
	}
	
	//*************************************
	
	public PersistentType persistentType(String fullyQualifiedTypeName) {
		for (MappingFileRef mappingFileRef : CollectionTools.iterable(mappingFileRefs())) {
			OrmPersistentType ormPersistentType = mappingFileRef.persistentTypeFor(fullyQualifiedTypeName);
			if (ormPersistentType != null) {
				return ormPersistentType;
			}
		}
		for (ClassRef classRef : CollectionTools.iterable(classRefs())) {
			if (classRef.isFor(fullyQualifiedTypeName)) {
				return classRef.getJavaPersistentType();
			}
		}
		return null;
	}
	
	public JpaStructureNode structureNode(int textOffset) {
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
	
	public TextRange selectionTextRange() {
		return xmlPersistenceUnit.selectionTextRange();
	}
	
	public TextRange validationTextRange() {
		return xmlPersistenceUnit.validationTextRange();
	}
	
	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(getName());
	}
}
