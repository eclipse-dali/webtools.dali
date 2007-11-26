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
	/**
	 * Return the IPersistentType specified in this PersistenceUnit with the given
	 * fully qualified type name
	 */
	IPersistentType persistentType(String fullyQualifiedTypeName);

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
	 * (corresponds to empty tag in persistence.xml)
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
	
	final static String DESCRIPTION_PROPERTY = "description";
	
	String getDescription();
	
	void setDescription(String description);
	
	
	// **************** provider ********************************************
	
	final static String PROVIDER_PROPERTY = "provider";
	
	String getProvider();
	
	void setProvider(String provider);
	
	
	// **************** jta data source ****************************************
	
	final static String JTA_DATA_SOURCE_PROPERTY = "jtaDataSource";
	
	String getJtaDataSource();
	
	void setJtaDataSource(String jtaDataSource);
	
	
	// **************** non-jta data source ************************************
	
	final static String NON_JTA_DATA_SOURCE_PROPERTY = "nonJtaDataSource";
	
	String getNonJtaDataSource();
	
	void setNonJtaDataSource(String nonJtaDataSource);
	
	
	// **************** mapping file refs **************************************
	
	final static String MAPPING_FILE_REF_LIST = "mappingFileRefs";
	
	ListIterator<IMappingFileRef> mappingFileRefs();
	
	void addMappingFileRef(IMappingFileRef mappingFileRef);
	
	void addMappingFileRef(int index, IMappingFileRef mappingFileRef);
	
	void removeMappingFileRef(IMappingFileRef mappingFileRef);
	
	void removeMappingFileRef(int index);
	
	
	// **************** class refs *********************************************
	
	final static String CLASS_REF_LIST = "classRefs";
	
	ListIterator<IClassRef> classRefs();
	
	void addClassRef(IClassRef classRef);
	
	void addClassRef(int index, IClassRef classRef);
	
	void removeClassRef(IClassRef classRef);
	
	void removeClassRef(int index);
	
	
	// **************** exclude unlisted classes *******************************
	
	final static String EXCLUDE_UNLISTED_CLASSED_PROPERTY = "excludeUnlistedClasses";
	
	final static String DEFAULT_EXCLUDE_UNLISTED_CLASSED_PROPERTY = "defaultExcludeUnlistedClasses";
	
	boolean getExcludeUnlistedClasses();
	
	void setExcludeUnlistedClasses(boolean excludeUnlistedClasses);
	
	/** Return true if exclude unlisted classes is default rather than specified */
	boolean isExcludeUnlistedClassesDefault();
	
	/** Return the default exclude unlisted classes */
	boolean getDefaultExcludeUnlistedClasses();
	
	/** Sets exclude unlisted classes to the default */
	void setExcludeUnlistedClassesToDefault();
	
	
	// **************** properties *********************************************
	
	final static String PROPERTIES_LIST = "properties";
	
	ListIterator<IProperty> properties();
	
	void addProperty(IProperty property);
	
	void addProperty(int index, IProperty property);
	
	void removeProperty(IProperty property);
	
	void removeProperty(int index);
	
	
	// **************** updating ***********************************************
	
	void initialize(XmlPersistenceUnit persistenceUnit);
	
	void update(XmlPersistenceUnit persistenceUnit);
}
