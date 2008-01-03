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

import java.util.ListIterator;
import org.eclipse.jpt.core.internal.resource.persistence.XmlPersistenceUnit;

public interface IPersistenceUnit extends IJpaContextNode
{
	// **************** parent *************************************************
	
	IPersistence persistence();
	
	
	// **************** name ***************************************************
	
	/**
	 * String constant associated with changes to the persistence unit's name
	 */
	final static String NAME_PROPERTY = "name";
	
	/**
	 * Return the name of the persistence unit.
	 */
	String getName();
	
	/**
	 * Set the name of the persistence unit.
	 */
	void setName(String name);
	
	
	// **************** transaction type ***************************************
	
	/**
	 * String constant associated with changes to the persistence unit's 
	 * transaction type
	 */
	final static String TRANSACTION_TYPE_PROPERTY = "transactionType";
	
	/** 
	 * Return the transaction type of the persistence unit, one of the values of 
	 * {@link PersistenceUnitTransactionType} 
	 */
	PersistenceUnitTransactionType getTransactionType();
	
	/** 
	 * Set the transaction type of the persistence unit, one of the values of 
	 * {@link PersistenceUnitTransactionType} 
	 */
	void setTransactionType(PersistenceUnitTransactionType transactionType);
	
	/** 
	 * Return true if the transaction type is default rather than overridden
	 * (corresponds to missing tag in persistence.xml)
	 */
	boolean isTransactionTypeDefault();
	
	/** 
	 * Set the transaction type of the persistence unit to the default 
	 */
	void setTransactionTypeToDefault();
	
	/**
	 * String constant associated with changes to the persistence unit's 
	 * default transaction type (not typically changed)
	 */
	final static String DEFAULT_TRANSACTION_TYPE_PROPERTY = "defaultTransactionType";
	
	/** 
	 * Return the default transaction type 
	 */
	PersistenceUnitTransactionType getDefaultTransactionType();
	
	
	
	// **************** description ********************************************
	
	/**
	 * String constant associated with changes to the persistence unit's description
	 */
	final static String DESCRIPTION_PROPERTY = "description";
	
	/**
	 * Return the description of the persistence unit.
	 */
	String getDescription();
	
	/**
	 * Set the description of the persistence unit.
	 */
	void setDescription(String description);
	
	
	// **************** provider ********************************************
	
	/**
	 * String constant associated with changes to the persistence unit's provider
	 */
	final static String PROVIDER_PROPERTY = "provider";
	
	/**
	 * Return the provider of the persistence unit.
	 */
	String getProvider();
	
	/**
	 * Set the provider of the persistence unit.
	 */
	void setProvider(String provider);
	
	
	// **************** jta data source ****************************************
	
	/**
	 * String constant associated with changes to the persistence unit's JTA data source
	 */
	final static String JTA_DATA_SOURCE_PROPERTY = "jtaDataSource";
	
	/**
	 * Return the JTA data source of the persistence unit.
	 */
	String getJtaDataSource();
	
	/**
	 * Set the JTA data source of the persistence unit.
	 */
	void setJtaDataSource(String jtaDataSource);
	
	
	// **************** non-jta data source ************************************
	
	/**
	 * String constant associated with changes to the persistence unit's non-JTA data source
	 */
	final static String NON_JTA_DATA_SOURCE_PROPERTY = "nonJtaDataSource";
	
	/**
	 * Return the non-JTA data source of the persistence unit.
	 */
	String getNonJtaDataSource();
	
	/**
	 * Set the non-JTA data source of the persistence unit.
	 */
	void setNonJtaDataSource(String nonJtaDataSource);
	
	
	// **************** mapping file refs **************************************
	
	/**
	 * String constant associated with changes to the mapping file refs list
	 */
	final static String MAPPING_FILE_REF_LIST = "mappingFileRefs";
	
	/**
	 * Return an iterator on the list of mapping file refs.
	 * This will not be null.
	 */
	ListIterator<IMappingFileRef> mappingFileRefs();
	
	/**
	 * Add a mapping file ref to the persistence unit and return the object 
	 * representing it.
	 */
	IMappingFileRef addMappingFileRef();
	
	/**
	 * Add a mapping file ref to the persistence unit at the specified index and 
	 * return the object representing it.
	 */
	IMappingFileRef addMappingFileRef(int index);
	
	/**
	 * Remove the mapping file ref from the persistence unit.
	 */
	void removeMappingFileRef(IMappingFileRef mappingFileRef);
	
	/**
	 * Remove the mapping file ref at the specified index from the persistence unit.
	 */
	void removeMappingFileRef(int index);
	
	
	// **************** class refs *********************************************
	
	/**
	 * String constant associated with changes to the class refs list
	 */
	final static String CLASS_REF_LIST = "classRefs";
	
	/**
	 * Return an iterator on the list of class refs.
	 * This will not be null.
	 */
	ListIterator<IClassRef> classRefs();
	
	/**
	 * Add a class ref to the persistence unit and return the object 
	 * representing it.
	 */
	IClassRef addClassRef();
	
	/**
	 * Add a class ref to the persistence unit at the specified index and 
	 * return the object representing it.
	 */
	IClassRef addClassRef(int index);
	
	/**
	 * Remove the class ref from the persistence unit.
	 */
	void removeClassRef(IClassRef classRef);
	
	/**
	 * Remove the class ref at the specified index from the persistence unit.
	 */
	void removeClassRef(int index);
	
	
	// **************** exclude unlisted classes *******************************
	
	/**
	 * String constant associated with changes to the persistence unit's 
	 * "exclude unlisted classes" setting
	 */
	final static String EXCLUDE_UNLISTED_CLASSED_PROPERTY = "excludeUnlistedClasses";
	
	/** 
	 * Return the "exclude unlisted classes" setting of the persistence unit.
	 */
	boolean getExcludeUnlistedClasses();
	
	/** 
	 * Set the "exclude unlisted classes" setting of the persistence unit.
	 */
	void setExcludeUnlistedClasses(boolean excludeUnlistedClasses);
	
	/** 
	 * Return true if the "exclude unlisted classes" setting is default rather 
	 * than overridden
	 * (corresponds to missing tag in persistence.xml)
	 */
	boolean isExcludeUnlistedClassesDefault();
	
	/** 
	 * Set the "exclude unlisted classes" setting of the persistence unit to the 
	 * default 
	 */
	void setExcludeUnlistedClassesToDefault();
	
	/**
	 * String constant associated with changes to the persistence unit's 
	 * default "exclude unlisted classes" setting (not typically changed)
	 */
	final static String DEFAULT_EXCLUDE_UNLISTED_CLASSED_PROPERTY = "defaultExcludeUnlistedClasses";
	
	/** 
	 * Return the default "exclude unlisted classes" setting
	 */
	boolean getDefaultExcludeUnlistedClasses();
	
	
	// **************** properties *********************************************
	
	/**
	 * String constant associated with changes to the properties list
	 */
	final static String PROPERTIES_LIST = "properties";
	
	/**
	 * Return an iterator on the list of properties.
	 * This will not be null.
	 */
	ListIterator<IProperty> properties();
	
	/**
	 * Add a property to the persistence unit and return the object 
	 * representing it.
	 */
	IProperty addProperty();
	
	IProperty getProperty(String name);

	void putProperty(String key, String value);
	
	boolean containsProperty(String key);
	
	/**
	 * Remove the property from the persistence unit.
	 */
	void removeProperty(IProperty property);
	
	/**
	 * Remove the property with the given key from the persistence unit.
	 */
	void removeProperty(String key);
	
	/**
	 * Remove the property at the specified index from the persistence unit.
	 */
	void removeProperty(int index);
	
	
	// **************** PersistenceUnitDefaults ********************************
	
	String getDefaultSchema();
		String DEFAULT_SCHEMA_PROPERTY = "defaultSchema";
		
	String getDefaultCatalog();
		String DEFAULT_CATALOG_PROPERTY = "defaultCatalog";
	
	AccessType getDefaultAccess();
		String DEFAULT_ACCESS_PROPERTY = "defaultAccess";
	
	boolean getDefaultCascadePersist();
		String DEFAULT_CASCADE_PERSIST_PROPERTY = "defaultCascadePersist";
	
	
	// **************** updating ***********************************************
	
	void initialize(XmlPersistenceUnit persistenceUnit);
	
	void update(XmlPersistenceUnit persistenceUnit);
	
	
	// *************************************************************************
	
	/**
	 * Return the IPersistentType specified in this PersistenceUnit with the given
	 * fully qualified type name
	 */
	IPersistentType persistentType(String fullyQualifiedTypeName);
	
}
