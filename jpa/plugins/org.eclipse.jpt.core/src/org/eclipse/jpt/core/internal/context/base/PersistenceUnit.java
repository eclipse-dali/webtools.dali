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

import static org.eclipse.jpt.core.internal.context.base.PersistenceUnitTransactionType.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.context.orm.PersistenceUnitDefaults;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentType;
import org.eclipse.jpt.core.internal.resource.orm.OrmArtifactEdit;
import org.eclipse.jpt.core.internal.resource.orm.OrmResource;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.XmlJavaClassRef;
import org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.internal.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.core.internal.resource.persistence.XmlPersistenceUnitTransactionType;
import org.eclipse.jpt.core.internal.resource.persistence.XmlProperties;
import org.eclipse.jpt.core.internal.resource.persistence.XmlProperty;
import org.eclipse.jpt.core.internal.validation.IJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.HashBag;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.ReadOnlyCompositeListIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

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
	
	protected final List<IMappingFileRef> specifiedMappingFileRefs;
	
	protected IMappingFileRef impliedMappingFileRef;
	
	protected final List<IClassRef> specifiedClassRefs;
	
	protected final List<IClassRef> impliedClassRefs;
	
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
		this.specifiedMappingFileRefs = new ArrayList<IMappingFileRef>();
		this.specifiedClassRefs = new ArrayList<IClassRef>();
		this.impliedClassRefs = new ArrayList<IClassRef>();
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
		if (impliedMappingFileRef == null) {
			return specifiedMappingFileRefs();
		}
		else {
			return new ReadOnlyCompositeListIterator<IMappingFileRef>(
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
	
	public ListIterator<IMappingFileRef> specifiedMappingFileRefs() {
		return new CloneListIterator<IMappingFileRef>(specifiedMappingFileRefs);
	}
	
	public int specifiedMappingFileRefsSize() {
		return specifiedMappingFileRefs.size();
	}
	
	public IMappingFileRef addSpecifiedMappingFileRef() {
		return addSpecifiedMappingFileRef(specifiedMappingFileRefs.size());
	}
	
	public IMappingFileRef addSpecifiedMappingFileRef(int index) {
		XmlMappingFileRef xmlMappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		IMappingFileRef mappingFileRef = createMappingFileRef(xmlMappingFileRef);
		specifiedMappingFileRefs.add(index, mappingFileRef);
		this.xmlPersistenceUnit.getMappingFiles().add(xmlMappingFileRef);
		fireItemAdded(SPECIFIED_MAPPING_FILE_REF_LIST, index, mappingFileRef);
		return mappingFileRef;
	}
	
	public void removeSpecifiedMappingFileRef(IMappingFileRef mappingFileRef) {
		removeSpecifiedMappingFileRef(specifiedMappingFileRefs.indexOf(mappingFileRef));
	}
	
	public void removeSpecifiedMappingFileRef(int index) {
		IMappingFileRef mappingFileRefRemoved = specifiedMappingFileRefs.remove(index);
		this.xmlPersistenceUnit.getMappingFiles().remove(index);
		fireItemRemoved(SPECIFIED_MAPPING_FILE_REF_LIST, index, mappingFileRefRemoved);
	}
	
	protected void addSpecifiedMappingFileRef_(IMappingFileRef mappingFileRef) {
		addSpecifiedMappingFileRef_(specifiedMappingFileRefs.size(), mappingFileRef);
	}
	
	protected void addSpecifiedMappingFileRef_(int index, IMappingFileRef mappingFileRef) {
		addItemToList(index, mappingFileRef, specifiedMappingFileRefs, SPECIFIED_MAPPING_FILE_REF_LIST);
	}
	
	protected void removeSpecifiedMappingFileRef_(IMappingFileRef mappingFileRef) {
		removeSpecifiedMappingFileRef_(specifiedMappingFileRefs.indexOf(mappingFileRef));
	}
	
	protected void removeSpecifiedMappingFileRef_(int index) {
		removeItemFromList(index, specifiedMappingFileRefs, SPECIFIED_MAPPING_FILE_REF_LIST);
	}
	
	
	// **************** implied mapping file ref *******************************
	
	public IMappingFileRef getImpliedMappingFileRef() {
		return impliedMappingFileRef;
	}
	
	public IMappingFileRef setImpliedMappingFileRef() {
		if (impliedMappingFileRef != null) {
			throw new IllegalStateException("The implied mapping file ref is already set.");
		}
		IMappingFileRef mappingFileRef = createMappingFileRef(null);
		impliedMappingFileRef = mappingFileRef;
		firePropertyChanged(IMPLIED_MAPPING_FILE_REF_PROPERTY, null, mappingFileRef);
		return mappingFileRef;
	}
	
	public void unsetImpliedMappingFileRef() {
		if (impliedMappingFileRef == null) {
			throw new IllegalStateException("The implie mapping file ref is already unset.");
		}
		IMappingFileRef mappingFileRef = impliedMappingFileRef;
		impliedMappingFileRef = null;
		firePropertyChanged(IMPLIED_MAPPING_FILE_REF_PROPERTY, mappingFileRef, null);
	}
	
	
	// **************** class refs *********************************************
	
	public ListIterator<IClassRef> classRefs() {
		return new ReadOnlyCompositeListIterator<IClassRef>(
			specifiedClassRefs(), impliedClassRefs());
	}
	
	public int classRefsSize() {
		return specifiedClassRefsSize() + impliedClassRefsSize();
	}
	
	// **************** specified class refs ***********************************
	
	public ListIterator<IClassRef> specifiedClassRefs() {
		return new CloneListIterator<IClassRef>(this.specifiedClassRefs);
	}
	
	public int specifiedClassRefsSize() {
		return specifiedClassRefs.size();
	}
	
	public IClassRef addSpecifiedClassRef() {
		return addSpecifiedClassRef(this.specifiedClassRefs.size());
	}
	
	public IClassRef addSpecifiedClassRef(int index) {
		XmlJavaClassRef xmlClassRef = PersistenceFactory.eINSTANCE.createXmlJavaClassRef();
		IClassRef classRef = createClassRef(xmlClassRef);
		this.specifiedClassRefs.add(index, classRef);
		this.xmlPersistenceUnit.getClasses().add(xmlClassRef);
		fireItemAdded(SPECIFIED_CLASS_REF_LIST, index, classRef);
		return classRef;
	}
	
	public void removeSpecifiedClassRef(IClassRef classRef) {
		removeSpecifiedClassRef(this.specifiedClassRefs.indexOf(classRef));
	}
	
	public void removeSpecifiedClassRef(int index) {
		IClassRef classRefRemoved = this.specifiedClassRefs.remove(index);
		this.xmlPersistenceUnit.getClasses().remove(index);
		fireItemRemoved(SPECIFIED_CLASS_REF_LIST, index, classRefRemoved);
	}
	
	protected void addSpecifiedClassRef_(IClassRef classRef) {
		addSpecifiedClassRef_(this.specifiedClassRefs.size(), classRef);
	}
	
	protected void addSpecifiedClassRef_(int index, IClassRef classRef) {
		addItemToList(index, classRef, this.specifiedClassRefs, SPECIFIED_CLASS_REF_LIST);
	}
	
	protected void removeSpecifiedClassRef_(IClassRef classRef) {
		removeSpecifiedClassRef_(this.specifiedClassRefs.indexOf(classRef));
	}
	
	protected void removeSpecifiedClassRef_(int index) {
		removeItemFromList(index, this.specifiedClassRefs, SPECIFIED_CLASS_REF_LIST);
	}
	
	
	// **************** implied class refs *************************************
	
	public ListIterator<IClassRef> impliedClassRefs() {
		return new CloneListIterator<IClassRef>(impliedClassRefs);
	}
	
	public int impliedClassRefsSize() {
		return impliedClassRefs.size();
	}
	
	public IClassRef addImpliedClassRef(String className) {
		return addImpliedClassRef(impliedClassRefs.size(), className);
	}
	
	public IClassRef addImpliedClassRef(int index, String className) {
		IClassRef classRef = createClassRef(className);
		addItemToList(classRef, impliedClassRefs, IMPLIED_CLASS_REF_LIST);
		return classRef;
	}
	
	public void removeImpliedClassRef(IClassRef classRef) {
		removeImpliedClassRef(impliedClassRefs.indexOf(classRef));
	}
	
	public void removeImpliedClassRef(int index) {
		removeItemFromList(index, impliedClassRefs, IMPLIED_CLASS_REF_LIST);
	}
	
	protected void addImpliedClassRef_(IClassRef classRef) {
		addImpliedClassRef_(impliedClassRefs.size(), classRef);
	}
	
	protected void addImpliedClassRef_(int index, IClassRef classRef) {
		addItemToList(index, classRef, impliedClassRefs, IMPLIED_CLASS_REF_LIST);
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
		if (property != null) {
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
			specifiedMappingFileRefs.add(createMappingFileRef(xmlMappingFileRef));
		}
		if (! impliedMappingFileIsSpecified() && impliedMappingFileExists()) {
			impliedMappingFileRef = createMappingFileRef(null);
		}
	}
	
	protected void initializeClassRefs(XmlPersistenceUnit xmlPersistenceUnit) {
		for (XmlJavaClassRef xmlJavaClassRef : xmlPersistenceUnit.getClasses()) {
			specifiedClassRefs.add(createClassRef(xmlJavaClassRef));
		}
		
		if (jpaProject().discoversAnnotatedClasses() && ! getExcludeUnlistedClasses()) {
			for (IType type : CollectionTools.iterable(jpaProject().annotatedClasses())) {
				if (! classIsSpecified(type.getFullyQualifiedName())) {
					impliedClassRefs.add(createClassRef(type.getFullyQualifiedName()));
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
		Iterator<IMappingFileRef> stream = specifiedMappingFileRefs();
		Iterator<XmlMappingFileRef> stream2 = persistenceUnit.getMappingFiles().iterator();
		
		while (stream.hasNext()) {
			IMappingFileRef mappingFileRef = stream.next();
			if (stream2.hasNext()) {
				mappingFileRef.update(stream2.next());
			}
			else {
				removeSpecifiedMappingFileRef_(mappingFileRef);
			}
		}
		
		while (stream2.hasNext()) {
			addSpecifiedMappingFileRef_(createMappingFileRef(stream2.next()));
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
		for (IMappingFileRef each : specifiedMappingFileRefs) {
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
	
	protected IMappingFileRef createMappingFileRef(XmlMappingFileRef xmlMappingFileRef) {
		IMappingFileRef mappingFileRef = jpaFactory().createMappingFileRef(this);
		mappingFileRef.initialize(xmlMappingFileRef);
		return mappingFileRef;
	}
	
	protected void updateClassRefs(XmlPersistenceUnit persistenceUnit) {
		Iterator<IClassRef> stream = specifiedClassRefs();
		Iterator<XmlJavaClassRef> stream2 = persistenceUnit.getClasses().iterator();
		
		while (stream.hasNext()) {
			IClassRef classRef = stream.next();
			if (stream2.hasNext()) {
				classRef.update(stream2.next());
			}
			else {
				removeSpecifiedClassRef_(classRef);
			}
		}
		
		while (stream2.hasNext()) {
			addSpecifiedClassRef_(createClassRef(stream2.next()));
		}
		
		Iterator<IClassRef> impliedRefs = impliedClassRefs();
		Iterator<IType> annotatedClasses = jpaProject().annotatedClasses();
		
		
		if (jpaProject().discoversAnnotatedClasses() && ! getExcludeUnlistedClasses()) {
			while (impliedRefs.hasNext()) {
				IClassRef classRef = impliedRefs.next();
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
			for (IClassRef classRef : CollectionTools.iterable(impliedClassRefs())) {
				removeImpliedClassRef(classRef);
			}
		}
	}
	
	protected IClassRef createClassRef(XmlJavaClassRef xmlClassRef) {
		IClassRef classRef = jpaFactory().createClassRef(this);
		classRef.initialize(xmlClassRef);
		return classRef;
	}
	
	protected IClassRef createClassRef(String className) {
		IClassRef classRef = jpaFactory().createClassRef(this);
		classRef.initialize(className);
		return classRef;
	}
	
	protected boolean classIsSpecified(String className) {
		for (IClassRef specifiedClassRef : CollectionTools.iterable(specifiedClassRefs())) {
			if (className.equals(specifiedClassRef.getClassName())) {
				return true;
			}
		}
		return false;
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
	
	
	// ********** Validation ***********************************************
	
	@Override
	public void addToMessages(List<IMessage> messages, CompilationUnit astRoot) {
		super.addToMessages(messages, astRoot);
		
		addMappingFileMessages(messages, astRoot);	
		addClassMessages(messages, astRoot);	
	}
	
	protected void addMappingFileMessages(List<IMessage> messages, CompilationUnit astRoot) {
		addMultipleMetadataMessages(messages);
		addUnspecifiedMappingFileMessages(messages);
		addUnresolvedMappingFileMessages(messages);
		addInvalidMappingFileContentMessage(messages);
		addDuplicateMappingFileMessages(messages);
		
		for (Iterator<IMappingFileRef> stream =  mappingFileRefs(); stream.hasNext();) {
			stream.next().addToMessages(messages, astRoot);
		}
	}
	
	protected void addMultipleMetadataMessages(List<IMessage> messages) {
		Collection<PersistenceUnitDefaults> puDefaultsCollection = persistenceUnitDefaultsForValidation();
		if (puDefaultsCollection.size() > 1) {
			for (PersistenceUnitDefaults puDefaults : puDefaultsCollection) {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.ENTITY_MAPPINGS_MULTIPLE_METADATA,
						new String[] {this.getName()},
						puDefaults)
				);
			}
		}
	}
	
	protected void addDuplicateMappingFileMessages(List<IMessage> messages) {
		HashBag<String> fileBag = new HashBag<String>(
				CollectionTools.collection(
						new TransformationIterator(this.mappingFileRefs()) {
							@Override
							protected Object transform(Object next) {
								return ((IMappingFileRef) next).getFileName();
							}
						}
				)
		);
		for (IMappingFileRef mappingFileRef : CollectionTools.collection(this.mappingFileRefs())) {
			if (fileBag.count(mappingFileRef.getFileName()) > 1) {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.PERSISTENCE_UNIT_DUPLICATE_MAPPING_FILE,
						new String[] {mappingFileRef.getFileName()}, 
						mappingFileRef) //, mappingFileRef.validationTextRange())
				);
			}
		}
	}
	
	protected void addUnspecifiedMappingFileMessages(List<IMessage> messages) {
		for (IMappingFileRef mappingFileRef : CollectionTools.collection(this.mappingFileRefs())) {
			if (mappingFileRef.getFileName() == null || mappingFileRef.getFileName().equals("")) {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.PERSISTENCE_UNIT_UNSPECIFIED_MAPPING_FILE,
						mappingFileRef) //, mappingFileRef.validationTextRange())
				);
			}
		}
	}
	
	protected void addUnresolvedMappingFileMessages(List<IMessage> messages) {
		for (Iterator<IMappingFileRef> stream = this.mappingFileRefs(); stream.hasNext(); ) {
			IMappingFileRef mappingFileRef = stream.next();
			if (! (mappingFileRef.getFileName() == null || mappingFileRef.getFileName().equals(""))
					&& mappingFileRef.getOrmXml() == null) {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.PERSISTENCE_UNIT_NONEXISTENT_MAPPING_FILE,
						new String[] {mappingFileRef.getFileName()}, 
						mappingFileRef) //, mappingFileRef.validationTextRange()) 
				);
			}
		}
	}
	
	protected void addInvalidMappingFileContentMessage(List<IMessage> messages) {
		for (Iterator<IMappingFileRef> stream = this.mappingFileRefs(); stream.hasNext(); ) {
			IMappingFileRef mappingFileRef = (IMappingFileRef) stream.next();
			if (mappingFileRef.getOrmXml() != null 
					&& mappingFileRef.getOrmXml().getEntityMappings() == null) {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.PERSISTENCE_UNIT_INVALID_MAPPING_FILE,
						new String[] {mappingFileRef.getFileName()}, 
						mappingFileRef) //, mappingFileRef.validationTextRange())
				);
			}
		} 
	}
	
	
	protected void addClassMessages(List<IMessage> messages, CompilationUnit astRoot) {
		addUnspecifiedClassMessages(messages);
		addUnresolvedClassMessages(messages);
//		addInvalidOrRedundantClassMessages(messages);
		addDuplicateClassMessages(messages);
		
		for (IClassRef classRef : CollectionTools.collection(classRefs())) {
				classRef.addToMessages(messages, astRoot);
		}
	}
	
	protected void addUnspecifiedClassMessages(List<IMessage> messages) {
		for (IClassRef javaClassRef : CollectionTools.collection(this.classRefs())) {
			if (javaClassRef.getJavaPersistentType() == null) {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.PERSISTENCE_UNIT_UNSPECIFIED_CLASS,
						javaClassRef, javaClassRef.validationTextRange())
				);
			}
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
	
	protected void addUnresolvedClassMessages(List<IMessage> messages) {
		for (IClassRef javaClassRef : specifiedClassRefs) {
			String javaClass = javaClassRef.getClassName();
			if (! StringTools.stringIsEmpty(javaClass) && javaClassRef.getJavaPersistentType() == null) {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.PERSISTENCE_UNIT_NONEXISTENT_CLASS,
						new String[] {javaClass}, 
						javaClassRef, javaClassRef.validationTextRange())
				);
			}
		}
	}
	
	protected void addDuplicateClassMessages(List<IMessage> messages) {
		HashBag<String> classNameBag = new HashBag(
				CollectionTools.collection(
						new TransformationIterator(this.classRefs()) {
							@Override
							protected Object transform(Object next) {
								return ((IClassRef) next).getClassName();
							}
						}
				)
		);
		for (IClassRef javaClassRef : CollectionTools.collection(this.classRefs())) {
			if (javaClassRef.getClassName() != null
					&& classNameBag.count(javaClassRef.getClassName()) > 1) {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.PERSISTENCE_UNIT_DUPLICATE_CLASS,
						new String[] {javaClassRef.getClassName()}, 
						javaClassRef, javaClassRef.validationTextRange())
				);
			}
		}
	}
	
	
	private Collection<PersistenceUnitDefaults> persistenceUnitDefaultsForValidation() {
		ArrayList<PersistenceUnitDefaults> puDefaults = new ArrayList<PersistenceUnitDefaults>();
		for (IMappingFileRef mappingFileRef : CollectionTools.iterable(mappingFileRefs())) {
			PersistenceUnitDefaults persistenceUnitDefaults = mappingFileRef.persistenceUnitDefaults();
			if (persistenceUnitDefaults != null) {
				puDefaults.add(persistenceUnitDefaults);
			}
		}
		return puDefaults;
	}
	
	//*************************************
	
	public IPersistentType persistentType(String fullyQualifiedTypeName) {
		for (IMappingFileRef mappingFileRef : CollectionTools.iterable(mappingFileRefs())) {
			XmlPersistentType xmlPersistentType = mappingFileRef.persistentTypeFor(fullyQualifiedTypeName);
			if (xmlPersistentType != null) {
				return xmlPersistentType;
			}
		}
		for (IClassRef classRef : CollectionTools.iterable(specifiedClassRefs())) {
			if (classRef.isFor(fullyQualifiedTypeName)) {
				return classRef.getJavaPersistentType();
			}
		}
		return null;
	}
	
	@Override
	public IJpaStructureNode structureNode(int textOffset) {
		if (! containsOffset(textOffset)) {
			return null;
		}
		for (IMappingFileRef mappingFileRef : CollectionTools.iterable(mappingFileRefs())) {
			if (mappingFileRef.containsOffset(textOffset)) {
				return mappingFileRef;
			}
		}
		for (IClassRef classRef : CollectionTools.iterable(classRefs())) {
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
	
	public ITextRange selectionTextRange() {
		return xmlPersistenceUnit.selectionTextRange();
	}
	
	public ITextRange validationTextRange() {
		return xmlPersistenceUnit.validationTextRange();
	}
	
	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(getName());
	}
}
