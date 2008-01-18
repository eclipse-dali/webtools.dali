/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.context.base;

import static org.eclipse.jpt.core.internal.context.base.PersistenceUnitTransactionType.DEFAULT;
import static org.eclipse.jpt.core.internal.context.base.PersistenceUnitTransactionType.JTA;
import static org.eclipse.jpt.core.internal.context.base.PersistenceUnitTransactionType.RESOURCE_LOCAL;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.context.orm.PersistenceUnitDefaults;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentType;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.XmlJavaClassRef;
import org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.internal.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.core.internal.resource.persistence.XmlPersistenceUnitTransactionType;
import org.eclipse.jpt.core.internal.resource.persistence.XmlProperties;
import org.eclipse.jpt.core.internal.resource.persistence.XmlProperty;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;

public class PersistenceUnit extends JpaContextNode
	implements IPersistenceUnit
{
	protected XmlPersistenceUnit xmlPersistenceUnit;
	
	protected String name;
	
	protected PersistenceUnitTransactionType transactionType;
	
	protected PersistenceUnitTransactionType defaultTransactionType = DEFAULT;
	
	protected String description;
	
	protected String provider;
	
	protected String jtaDataSource;
	
	protected String nonJtaDataSource;
	
	protected final List<IMappingFileRef> mappingFileRefs;
	
	protected final List<IClassRef> classRefs;
	
	protected Boolean excludeUnlistedClasses;
	
	protected boolean defaultExcludeUnlistedClasses = false;
	
	protected final List<IProperty> properties;
	
	protected String defaultSchema;
	protected String defaultCatalog;
	protected AccessType defaultAccess;
	protected boolean defaultCascadePersist;
	
	public PersistenceUnit(IPersistence parent) {
		super(parent);
		this.transactionType = PersistenceUnitTransactionType.DEFAULT;
		this.mappingFileRefs = new ArrayList<IMappingFileRef>();
		this.classRefs = new ArrayList<IClassRef>();
		this.properties = new ArrayList<IProperty>();
	}
	
	
	@Override
	public IPersistenceUnit persistenceUnit() {
		return this;
	}
	
	
	// **************** parent *************************************************
	
	public IPersistence persistence() {
		return (IPersistence) parent();
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
	
	public ListIterator<IMappingFileRef> mappingFileRefs() {
		return new CloneListIterator<IMappingFileRef>(this.mappingFileRefs);
	}
	
	public IMappingFileRef addMappingFileRef() {
		return addMappingFileRef(this.mappingFileRefs.size());
	}
	
	public IMappingFileRef addMappingFileRef(int index) {
		XmlMappingFileRef xmlMappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		IMappingFileRef mappingFileRef = createMappingFileRef(xmlMappingFileRef);
		this.mappingFileRefs.add(index, mappingFileRef);
		this.xmlPersistenceUnit.getMappingFiles().add(xmlMappingFileRef);
		fireItemAdded(MAPPING_FILE_REF_LIST, index, mappingFileRef);
		return mappingFileRef;
	}
	
	public void removeMappingFileRef(IMappingFileRef mappingFileRef) {
		removeMappingFileRef(this.mappingFileRefs.indexOf(mappingFileRef));
	}
	
	public void removeMappingFileRef(int index) {
		IMappingFileRef mappingFileRefRemoved = this.mappingFileRefs.remove(index);
		this.xmlPersistenceUnit.getMappingFiles().remove(index);
		fireItemRemoved(MAPPING_FILE_REF_LIST, index, mappingFileRefRemoved);
	}
	
	protected void addMappingFileRef_(IMappingFileRef mappingFileRef) {
		addMappingFileRef_(this.mappingFileRefs.size(), mappingFileRef);
	}
	
	protected void addMappingFileRef_(int index, IMappingFileRef mappingFileRef) {
		addItemToList(index, mappingFileRef, this.mappingFileRefs, MAPPING_FILE_REF_LIST);
	}
	
	protected void removeMappingFileRef_(IMappingFileRef mappingFileRef) {
		removeMappingFileRef_(this.mappingFileRefs.indexOf(mappingFileRef));
	}
	
	protected void removeMappingFileRef_(int index) {
		removeItemFromList(index, this.mappingFileRefs, MAPPING_FILE_REF_LIST);
	}
	
	
	// **************** class refs *********************************************
	
	public ListIterator<IClassRef> classRefs() {
		return new CloneListIterator<IClassRef>(this.classRefs);
	}
	
	public IClassRef addClassRef() {
		return addClassRef(this.classRefs.size());
	}
	
	public IClassRef addClassRef(int index) {
		XmlJavaClassRef xmlClassRef = PersistenceFactory.eINSTANCE.createXmlJavaClassRef();
		IClassRef classRef = createClassRef(xmlClassRef);
		this.classRefs.add(index, classRef);
		this.xmlPersistenceUnit.getClasses().add(xmlClassRef);
		fireItemAdded(CLASS_REF_LIST, index, classRef);
		return classRef;
	}
	
	public void removeClassRef(IClassRef classRef) {
		removeClassRef(this.classRefs.indexOf(classRef));
	}
	
	public void removeClassRef(int index) {
		IClassRef classRefRemoved = this.classRefs.remove(index);
		this.xmlPersistenceUnit.getClasses().remove(index);
		fireItemRemoved(CLASS_REF_LIST, index, classRefRemoved);
	}
	
	protected void addClassRef_(IClassRef classRef) {
		addClassRef_(this.classRefs.size(), classRef);
	}
	
	protected void addClassRef_(int index, IClassRef classRef) {
		addItemToList(index, classRef, this.classRefs, CLASS_REF_LIST);
	}
	
	protected void removeClassRef_(IClassRef classRef) {
		removeClassRef_(this.classRefs.indexOf(classRef));
	}
	
	protected void removeClassRef_(int index) {
		removeItemFromList(index, this.classRefs, CLASS_REF_LIST);
	}
	
	
	// **************** exclude unlisted classes *******************************
	
	public boolean getExcludeUnlistedClasses() {
		return (isExcludeUnlistedClassesDefault()) ? 
				getDefaultExcludeUnlistedClasses() : this.excludeUnlistedClasses;
	}
	
	public void setExcludeUnlistedClasses(boolean newExcludeUnlistedClasses) {
		setExcludeUnlistedClasses((Boolean) newExcludeUnlistedClasses);
	}
	
	public boolean isExcludeUnlistedClassesDefault() {
		return this.excludeUnlistedClasses == null;
	}
	
	public boolean getDefaultExcludeUnlistedClasses() {
		// TODO - calculate default
		//  This is determined from the project
		return this.defaultExcludeUnlistedClasses;
	}
	
	public void setExcludeUnlistedClassesToDefault() {
		setExcludeUnlistedClasses(null);
	}
	
	protected void setExcludeUnlistedClasses(Boolean newExcludeUnlistedClasses) {
		Boolean oldExcludeUnlistedClasses = this.excludeUnlistedClasses;
		this.excludeUnlistedClasses = newExcludeUnlistedClasses;
		
		if (this.excludeUnlistedClasses != null) {
			this.xmlPersistenceUnit.setExcludeUnlistedClasses(this.excludeUnlistedClasses);
		}
		else {
			this.xmlPersistenceUnit.unsetExcludeUnlistedClasses();
		}
		
		firePropertyChanged(EXCLUDE_UNLISTED_CLASSED_PROPERTY, oldExcludeUnlistedClasses, newExcludeUnlistedClasses);
	}
	
	
	// **************** properties *********************************************
	
	public ListIterator<IProperty> properties() {
		return new CloneListIterator<IProperty>(this.properties);
	}

	public int propertiesSize() {
		return this.properties.size();
	}

	public IProperty getProperty(String key) {
		for(IProperty property : this.properties) {
			if(property.getName().equals(key)) {
				return property;
			}
		}
		return null;
	}

	public IProperty getProperty(String key, String value) {
		for(IProperty property : this.properties) {
			if(property.getName().equals(key) && property.getValue().equals(value)) {
				return property;
			}
		}
		return null;
	}
	
	protected IProperty getProperty(int index) {
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
		EList<XmlProperty> xmlProperties = this.xmlPersistenceUnit.getProperties().getProperties();

		XmlProperty xmlProperty = this.getXmlProperty(key, oldValue);
		if(xmlProperty == null) {
			throw new NoSuchElementException("Missing Property name: " + key + ", value: " + oldValue);
		}
		xmlProperty.setValue(value);
		this.setItemInList(xmlProperties.indexOf(xmlProperty), xmlProperty, xmlProperties, PROPERTIES_LIST);
	}	
	
	public boolean containsProperty(String key) {
		return (this.getProperty(key) != null);
	}

	public IProperty addProperty() {
		XmlProperty xmlProperty = PersistenceFactory.eINSTANCE.createXmlProperty();
		
		return this.addXmlProperty(xmlProperty);
	}
	
	protected IProperty addXmlProperty(XmlProperty xmlProperty) {

		IProperty property = createProperty(xmlProperty);
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
	
	public void removeProperty(IProperty property) {
		if(property != null) {
			this.removeProperty(this.properties.indexOf(property));
		}
	}
	
	protected void removeProperty(int index) {
		IProperty propertyRemoved = this.properties.remove(index);
		this.xmlPersistenceUnit.getProperties().getProperties().remove(index);
		
		if (this.xmlPersistenceUnit.getProperties().getProperties().isEmpty()) {
			this.xmlPersistenceUnit.setProperties(null);
		}
		
		fireItemRemoved(PROPERTIES_LIST, index, propertyRemoved);
	}
	
	protected void addProperty_(IProperty property) {
		addProperty_(this.properties.size(), property);
	}
	
	protected void addProperty_(int index, IProperty property) {
		addItemToList(index, property, this.properties, PROPERTIES_LIST);
	}
	
	protected void removeProperty_(IProperty property) {
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
		for (IMappingFileRef mappingFileRef : CollectionTools.iterable(mappingFileRefs())) {
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
	
	// **************** updating ***********************************************
	
	
	public void initialize(XmlPersistenceUnit xmlPersistenceUnit) {
		this.xmlPersistenceUnit = xmlPersistenceUnit;
		this.name = xmlPersistenceUnit.getName();
		initializeMappingFileRefs(xmlPersistenceUnit);
		initializeClassRefs(xmlPersistenceUnit);
		initializeProperties(xmlPersistenceUnit);
	}
	
	protected void initializeMappingFileRefs(XmlPersistenceUnit xmlPersistenceUnit) {
		for (XmlMappingFileRef xmlMappingFileRef : xmlPersistenceUnit.getMappingFiles()) {
			this.mappingFileRefs.add(createMappingFileRef(xmlMappingFileRef));
		}
	}
	
	protected void initializeClassRefs(XmlPersistenceUnit xmlPersistenceUnit) {
		for (XmlJavaClassRef xmlJavaClassRef : xmlPersistenceUnit.getClasses()) {
			this.classRefs.add(createClassRef(xmlJavaClassRef));
		}
	}
	
	protected void initializeProperties(XmlPersistenceUnit xmlPersistenceUnit) {
		XmlProperties xmlProperties = xmlPersistenceUnit.getProperties();
		if (xmlProperties == null) {
			return;
		}
		for (XmlProperty xmlProperty : xmlProperties.getProperties()) {
			this.properties.add(createProperty(xmlProperty));
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
		Iterator<IMappingFileRef> stream = mappingFileRefs();
		Iterator<XmlMappingFileRef> stream2 = persistenceUnit.getMappingFiles().iterator();
		
		while (stream.hasNext()) {
			IMappingFileRef mappingFileRef = stream.next();
			if (stream2.hasNext()) {
				mappingFileRef.update(stream2.next());
			}
			else {
				removeMappingFileRef_(mappingFileRef);
			}
		}
		
		while (stream2.hasNext()) {
			addMappingFileRef_(createMappingFileRef(stream2.next()));
		}
	}
	
	protected IMappingFileRef createMappingFileRef(XmlMappingFileRef xmlMappingFileRef) {
		IMappingFileRef mappingFileRef = jpaFactory().createMappingFileRef(this);
		mappingFileRef.initialize(xmlMappingFileRef);
		return mappingFileRef;
	}
	
	protected void updateClassRefs(XmlPersistenceUnit persistenceUnit) {
		Iterator<IClassRef> stream = classRefs();
		Iterator<XmlJavaClassRef> stream2 = persistenceUnit.getClasses().iterator();
		
		while (stream.hasNext()) {
			IClassRef classRef = stream.next();
			if (stream2.hasNext()) {
				classRef.update(stream2.next());
			}
			else {
				removeClassRef_(classRef);
			}
		}
		
		while (stream2.hasNext()) {
			addClassRef_(createClassRef(stream2.next()));
		}
	}
	
	protected IClassRef createClassRef(XmlJavaClassRef xmlClassRef) {
		IClassRef classRef = jpaFactory().createClassRef(this);
		classRef.initialize(xmlClassRef);
		return classRef;
	}
	
	protected void updateExcludeUnlistedClasses(XmlPersistenceUnit persistenceUnit) {
		if (persistenceUnit.isSetExcludeUnlistedClasses()) {
			setExcludeUnlistedClasses(persistenceUnit.isExcludeUnlistedClasses());
		}
		else {
			setExcludeUnlistedClassesToDefault();
		}
	}
	
	protected void updateProperties(XmlPersistenceUnit persistenceUnit) {
		XmlProperties xmlProperties = persistenceUnit.getProperties();
		
		Iterator<IProperty> stream = properties();
		Iterator<XmlProperty> stream2;
		
		if (xmlProperties == null) {
			stream2 = EmptyIterator.instance();
		}
		else {
			stream2 = xmlProperties.getProperties().iterator();
		}
		
		while (stream.hasNext()) {
			IProperty property = stream.next();
			if (stream2.hasNext()) {
				property.update(stream2.next());
			}
			else {
				removeProperty_(property);
			}
		}
		
		while (stream2.hasNext()) {
			addProperty_(createProperty(stream2.next()));
		}
	}
	
	protected IProperty createProperty(XmlProperty xmlProperty) {
		IProperty property = jpaFactory().createProperty(this);
		property.initialize(xmlProperty);
		return property;
	}
		
	protected void updatePersistenceUnitDefaults() {
		PersistenceUnitDefaults persistenceUnitDefaults = persistenceUnitDefaults();
		if (persistenceUnitDefaults != null) {
			setDefaultSchema(persistenceUnitDefaults.getSchema());
			setDefaultCatalog(persistenceUnitDefaults.getCatalog());
			setDefaultAccess(persistenceUnitDefaults.getAccess());
			setDefaultCascadePersist(persistenceUnitDefaults.isCascadePersist());
		}
		else {
			setDefaultSchema(null);
			setDefaultCatalog(null);
			setDefaultAccess(null);
			setDefaultCascadePersist(false);		
		}
	}


	// *************************************************************************
	
	public IPersistentType persistentType(String fullyQualifiedTypeName) {
		for (IMappingFileRef mappingFileRef : CollectionTools.iterable(mappingFileRefs())) {
			XmlPersistentType xmlPersistentType = mappingFileRef.persistentTypeFor(fullyQualifiedTypeName);
			if (xmlPersistentType != null) {
				return xmlPersistentType;
			}
		}
		for (IClassRef classRef : CollectionTools.iterable(classRefs())) {
			if (classRef.isFor(fullyQualifiedTypeName)) {
				return classRef.getJavaPersistentType();
			}
		}
		return null;
	}
	
	public ITextRange validationTextRange() {
		return this.xmlPersistenceUnit.validationTextRange();
	}
	
	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(getName());
	}
}
