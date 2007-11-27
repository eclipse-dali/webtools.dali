/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.context.base;

import static org.eclipse.jpt.core.internal.context.base.PersistenceUnitTransactionType.DEFAULT;
import static org.eclipse.jpt.core.internal.context.base.PersistenceUnitTransactionType.JTA;
import static org.eclipse.jpt.core.internal.context.base.PersistenceUnitTransactionType.RESOURCE_LOCAL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.core.internal.ITextRange;
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
	
	
	public PersistenceUnit(IPersistence parent) {
		super(parent);
		this.transactionType = PersistenceUnitTransactionType.DEFAULT;
		this.mappingFileRefs = new ArrayList<IMappingFileRef>();
		this.classRefs = new ArrayList<IClassRef>();
		this.properties = new ArrayList<IProperty>();
	}
	
	
	public IPersistentType persistentType(String fullyQualifiedTypeName) {
		for (IClassRef classRef : CollectionTools.iterable(classRefs())) {
			if (classRef.isFor(fullyQualifiedTypeName)) {
				return classRef.getJavaPersistentType();
			}
		}
		//TODO check mappingFileRefs (probably check these first??)
		return null;
	}
	
	@Override
	public IPersistenceUnit persistenceUnit() {
		return this;
	}

	// **************** name ***************************************************
	
	public String getName() {
		return name;
	}
	
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		xmlPersistenceUnit.setName(newName);
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}
	
	
	// **************** transaction type ***************************************
	
	public PersistenceUnitTransactionType getTransactionType() {
		return (isTransactionTypeDefault()) ?
			getDefaultTransactionType() : transactionType;
	}
	
	public void setTransactionType(PersistenceUnitTransactionType newTransactionType) {
		if (newTransactionType == null) {
			throw new IllegalArgumentException("null");
		}
		PersistenceUnitTransactionType oldTransactionType = transactionType;
		transactionType = newTransactionType;
		
		if (transactionType == JTA) {
			xmlPersistenceUnit.setTransactionType(XmlPersistenceUnitTransactionType.JTA);
		}
		else if (transactionType == RESOURCE_LOCAL) {
			xmlPersistenceUnit.setTransactionType(XmlPersistenceUnitTransactionType.RESOURCE_LOCAL);
		}
		else if (transactionType == DEFAULT) {
			xmlPersistenceUnit.unsetTransactionType();
		}
		else {
			throw new IllegalArgumentException();
		}
		
		firePropertyChanged(TRANSACTION_TYPE_PROPERTY, oldTransactionType, newTransactionType);
	}
	
	public boolean isTransactionTypeDefault() {
		return transactionType == DEFAULT;
	}
	
	public void setTransactionTypeToDefault() {
		setTransactionType(DEFAULT);
	}
	
	public PersistenceUnitTransactionType getDefaultTransactionType() {
		// TODO - calculate default
		//  From the JPA spec: "In a Java EE environment, if this element is not 
		//  specified, the default is JTA. In a Java SE environment, if this element 
		// is not specified, a default of RESOURCE_LOCAL may be assumed."
		return defaultTransactionType;
	}
	
	
	// **************** description ********************************************
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String newDescription) {
		String oldDescription = description;
		description = newDescription;
		xmlPersistenceUnit.setDescription(newDescription);
		firePropertyChanged(DESCRIPTION_PROPERTY, oldDescription, newDescription);
	}
	
	
	// **************** provider ***********************************************
	
	public String getProvider() {
		return provider;
	}
	
	public void setProvider(String newProvider) {
		String oldProvider = provider;
		provider = newProvider;
		xmlPersistenceUnit.setProvider(newProvider);
		firePropertyChanged(DESCRIPTION_PROPERTY, oldProvider, newProvider);
	}
	
	
	// **************** jta data source ****************************************
	
	public String getJtaDataSource() {
		return jtaDataSource;
	}
	
	public void setJtaDataSource(String newJtaDataSource) {
		String oldJtaDataSource = jtaDataSource;
		jtaDataSource = newJtaDataSource;
		xmlPersistenceUnit.setJtaDataSource(newJtaDataSource);
		firePropertyChanged(DESCRIPTION_PROPERTY, oldJtaDataSource, newJtaDataSource);
	}
	
	
	// **************** non-jta data source ************************************
	
	public String getNonJtaDataSource() {
		return nonJtaDataSource;
	}
	
	public void setNonJtaDataSource(String newNonJtaDataSource) {
		String oldNonJtaDataSource = nonJtaDataSource;
		nonJtaDataSource = newNonJtaDataSource;
		xmlPersistenceUnit.setNonJtaDataSource(newNonJtaDataSource);
		firePropertyChanged(DESCRIPTION_PROPERTY, oldNonJtaDataSource, newNonJtaDataSource);
	}
	
	
	// **************** mapping file refs **************************************
	
	public ListIterator<IMappingFileRef> mappingFileRefs() {
		return new CloneListIterator<IMappingFileRef>(mappingFileRefs);
	}
	
	public IMappingFileRef addMappingFileRef() {
		return addMappingFileRef(mappingFileRefs.size());
	}
	
	public IMappingFileRef addMappingFileRef(int index) {
		XmlMappingFileRef xmlMappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		IMappingFileRef mappingFileRef = createMappingFileRef(xmlMappingFileRef);
		mappingFileRefs.add(index, mappingFileRef);
		xmlPersistenceUnit.getMappingFiles().add(xmlMappingFileRef);
		fireListChanged(MAPPING_FILE_REF_LIST);
		return mappingFileRef;
	}
	
	public void removeMappingFileRef(IMappingFileRef mappingFileRef) {
		removeMappingFileRef(mappingFileRefs.indexOf(mappingFileRef));
	}
	
	public void removeMappingFileRef(int index) {
		mappingFileRefs.remove(index);
		xmlPersistenceUnit.getMappingFiles().remove(index);
		fireListChanged(MAPPING_FILE_REF_LIST);
	}
	
	protected void addMappingFileRef_(IMappingFileRef mappingFileRef) {
		addMappingFileRef_(mappingFileRefs.size(), mappingFileRef);
	}
	
	protected void addMappingFileRef_(int index, IMappingFileRef mappingFileRef) {
		mappingFileRefs.add(index, mappingFileRef);
		fireListChanged(MAPPING_FILE_REF_LIST);
	}
	
	protected void removeMappingFileRef_(IMappingFileRef mappingFileRef) {
		removeMappingFileRef_(mappingFileRefs.indexOf(mappingFileRef));
	}
	
	protected void removeMappingFileRef_(int index) {
		mappingFileRefs.remove(index);
		fireListChanged(MAPPING_FILE_REF_LIST);
	}
	
	
	// **************** class refs *********************************************
	
	public ListIterator<IClassRef> classRefs() {
		return new CloneListIterator<IClassRef>(classRefs);
	}
	
	public IClassRef addClassRef() {
		return addClassRef(classRefs.size());
	}
	
	public IClassRef addClassRef(int index) {
		XmlJavaClassRef xmlClassRef = PersistenceFactory.eINSTANCE.createXmlJavaClassRef();
		IClassRef classRef = createClassRef(xmlClassRef);
		classRefs.add(index, classRef);
		xmlPersistenceUnit.getClasses().add(xmlClassRef);
		fireListChanged(CLASS_REF_LIST);
		return classRef;
	}
	
	public void removeClassRef(IClassRef classRef) {
		removeClassRef(classRefs.indexOf(classRef));
	}
	
	public void removeClassRef(int index) {
		classRefs.remove(index);
		xmlPersistenceUnit.getClasses().remove(index);
		fireListChanged(CLASS_REF_LIST);
	}
	
	protected void addClassRef_(IClassRef classRef) {
		addClassRef_(classRefs.size(), classRef);
	}
	
	protected void addClassRef_(int index, IClassRef classRef) {
		classRefs.add(index, classRef);
		fireListChanged(CLASS_REF_LIST);
	}
	
	protected void removeClassRef_(IClassRef classRef) {
		removeClassRef_(classRefs.indexOf(classRef));
	}
	
	protected void removeClassRef_(int index) {
		classRefs.remove(index);
		fireListChanged(CLASS_REF_LIST);
	}
	
	
	// **************** exclude unlisted classes *******************************
	
	public boolean getExcludeUnlistedClasses() {
		return (isExcludeUnlistedClassesDefault()) ? 
				getDefaultExcludeUnlistedClasses() : excludeUnlistedClasses;
	}
	
	public void setExcludeUnlistedClasses(boolean newExcludeUnlistedClasses) {
		setExcludeUnlistedClasses((Boolean) newExcludeUnlistedClasses);
	}
	
	public boolean isExcludeUnlistedClassesDefault() {
		return excludeUnlistedClasses == null;
	}
	
	public boolean getDefaultExcludeUnlistedClasses() {
		// TODO - calculate default
		//  This is determined from the project
		return defaultExcludeUnlistedClasses;
	}
	
	public void setExcludeUnlistedClassesToDefault() {
		setExcludeUnlistedClasses(null);
	}
	
	protected void setExcludeUnlistedClasses(Boolean newExcludeUnlistedClasses) {
		Boolean oldExcludeUnlistedClasses = excludeUnlistedClasses;
		excludeUnlistedClasses = newExcludeUnlistedClasses;
		
		if (excludeUnlistedClasses != null) {
			xmlPersistenceUnit.setExcludeUnlistedClasses(excludeUnlistedClasses);
		}
		else {
			xmlPersistenceUnit.unsetExcludeUnlistedClasses();
		}
		
		firePropertyChanged(EXCLUDE_UNLISTED_CLASSED_PROPERTY, oldExcludeUnlistedClasses, newExcludeUnlistedClasses);
	}
	
	
	// **************** properties *********************************************
	
	public ListIterator<IProperty> properties() {
		return new CloneListIterator<IProperty>(properties);
	}
	
	public IProperty addProperty() {
		return addProperty(properties.size());
	}
	
	public IProperty addProperty(int index) {
		XmlProperty xmlProperty = PersistenceFactory.eINSTANCE.createXmlProperty();
		IProperty property = createProperty(xmlProperty);
		properties.add(index, property);
		
		if (xmlPersistenceUnit.getProperties() == null) {
			XmlProperties xmlProperties = PersistenceFactory.eINSTANCE.createXmlProperties();
			xmlPersistenceUnit.setProperties(xmlProperties);
		}
		
		xmlPersistenceUnit.getProperties().getProperties().add(xmlProperty);
		fireListChanged(PROPERTIES_LIST);
		return property;
	}
	
	public void removeProperty(IProperty property) {
		removeProperty(properties.indexOf(property));
	}
	
	public void removeProperty(int index) {
		properties.remove(index);
		xmlPersistenceUnit.getProperties().getProperties().remove(index);
		
		if (xmlPersistenceUnit.getProperties().getProperties().isEmpty()) {
			xmlPersistenceUnit.setProperties(null);
		}
		
		fireListChanged(PROPERTIES_LIST);
	}
	
	protected void addProperty_(IProperty property) {
		addProperty_(properties.size(), property);
	}
	
	protected void addProperty_(int index, IProperty property) {
		properties.add(index, property);
		fireListChanged(PROPERTIES_LIST);
	}
	
	protected void removeProperty_(IProperty property) {
		removeProperty_(properties.indexOf(property));
	}
	
	protected void removeProperty_(int index) {
		properties.remove(index);
		fireListChanged(PROPERTIES_LIST);
	}
	
	
	// **************** updating ***********************************************
	
	
	public void initialize(XmlPersistenceUnit xmlPersistenceUnit) {
		this.xmlPersistenceUnit = xmlPersistenceUnit;
		name = xmlPersistenceUnit.getName();
		initializeMappingFileRefs(xmlPersistenceUnit);
		initializeClassRefs(xmlPersistenceUnit);
		initializeProperties(xmlPersistenceUnit);
	}
	
	protected void initializeMappingFileRefs(XmlPersistenceUnit xmlPersistenceUnit) {
		for (XmlMappingFileRef xmlMappingFileRef : xmlPersistenceUnit.getMappingFiles()) {
			mappingFileRefs.add(createMappingFileRef(xmlMappingFileRef));
		}
	}
	
	protected void initializeClassRefs(XmlPersistenceUnit xmlPersistenceUnit) {
		for (XmlJavaClassRef xmlJavaClassRef : xmlPersistenceUnit.getClasses()) {
			classRefs.add(createClassRef(xmlJavaClassRef));
		}
	}
	
	protected void initializeProperties(XmlPersistenceUnit xmlPersistenceUnit) {
		XmlProperties xmlProperties = xmlPersistenceUnit.getProperties();
		if (xmlProperties == null) {
			return;
		}
		for (XmlProperty xmlProperty : xmlProperties.getProperties()) {
			properties.add(createProperty(xmlProperty));
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
	
	
	// *************************************************************************
	
	public ITextRange validationTextRange() {
		return this.xmlPersistenceUnit.validationTextRange();
	}
}
