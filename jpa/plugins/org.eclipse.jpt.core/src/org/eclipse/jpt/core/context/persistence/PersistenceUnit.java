/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.persistence;

import java.util.ListIterator;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.Generator;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.Query;
import org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface PersistenceUnit extends PersistenceJpaContextNode, JpaStructureNode
{
	// **************** parent *************************************************
	
	Persistence parent();
	
	
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
	 * Return an iterator on the list of mapping file refs, whether specified or
	 * implied.
	 * This will not be null.
	 */
	ListIterator<MappingFileRef> mappingFileRefs();
	
	/**
	 * Return of mapping file refs, specified and implied.
	 */
	int mappingFileRefsSize();
	
	// **************** specified mapping file refs ****************************
	
	/**
	 * String constant associated with changes to the specified mapping file refs list
	 */
	final static String SPECIFIED_MAPPING_FILE_REF_LIST = "specifiedMappingFileRefs";
	
	/**
	 * Return an iterator on the list of specified mapping file refs.
	 * This will not be null.
	 */
	ListIterator<MappingFileRef> specifiedMappingFileRefs();
	
	/**
	 * Return of specified mapping file refs.
	 */
	int specifiedMappingFileRefsSize();

	/**
	 * Add a specified mapping file ref to the persistence unit and return the object 
	 * representing it.
	 */
	MappingFileRef addSpecifiedMappingFileRef();
	
	/**
	 * Add a specified mapping file ref to the persistence unit at the specified index and 
	 * return the object representing it.
	 */
	MappingFileRef addSpecifiedMappingFileRef(int index);
	
	/**
	 * Remove the specified mapping file ref from the persistence unit.
	 */
	void removeSpecifiedMappingFileRef(MappingFileRef mappingFileRef);
	
	/**
	 * Remove the specified mapping file ref at the specified index from the persistence unit.
	 */
	void removeSpecifiedMappingFileRef(int index);
	
	
	// **************** implied mapping file ref *******************************
	
	/**
	 * String constant associated with changes to the implied mapping file ref
	 */
	final static String IMPLIED_MAPPING_FILE_REF_PROPERTY = "impliedMappingFileRef";
	
	/**
	 * Return the current implied mapping file ref.
	 * This may be null.
	 */
	MappingFileRef getImpliedMappingFileRef();
	
	/**
	 * Adds the implied mapping file ref
	 */
	MappingFileRef setImpliedMappingFileRef();
	
	/**
	 * Removes the implied mapping file ref
	 */
	void unsetImpliedMappingFileRef();
	
	
	// **************** class refs *********************************************
	
	/**
	 * Return an iterator on the list of class refs, whether specified or implied.
	 * This will not be null.
	 */
	ListIterator<ClassRef> classRefs();
	
	/**
	 * Return the number of specified and implied class refs.
	 */
	int classRefsSize();
	
	// **************** specified class refs ***********************************
	
	/**
	 * String constant associated with changes to the specified class refs list
	 */
	final static String SPECIFIED_CLASS_REF_LIST = "specifiedClassRefs";
	
	/**
	 * Return an iterator on the list of specified class refs.
	 * This will not be null.
	 */
	ListIterator<ClassRef> specifiedClassRefs();
	
	/**
	 * Return the number of specified class refs.
	 */
	int specifiedClassRefsSize();
	
	/**
	 * Add a specified class ref to the persistence unit and return the object 
	 * representing it.
	 */
	ClassRef addSpecifiedClassRef();
	
	/**
	 * Add a specified class ref to the persistence unit at the specified index and 
	 * return the object representing it.
	 */
	ClassRef addSpecifiedClassRef(int index);
	
	/**
	 * Remove the specified class ref from the persistence unit.
	 */
	void removeSpecifiedClassRef(ClassRef classRef);
	
	/**
	 * Remove the specified class ref at the specified index from the persistence unit.
	 */
	void removeSpecifiedClassRef(int index);
	
	
	// **************** implied class refs *************************************
	
	/**
	 * String constant associated with changes to the implied class refs list
	 */
	final static String IMPLIED_CLASS_REF_LIST = "impliedClassRefs";
	
	/**
	 * Return an iterator on the list of implied class refs.
	 * This will not be null.
	 */
	ListIterator<ClassRef> impliedClassRefs();
	
	/**
	 * Return the number of implied class refs.
	 */
	int impliedClassRefsSize();
	
	/**
	 * Add an implied class ref to the persistence unit and return the object 
	 * representing it.
	 */
	ClassRef addImpliedClassRef(String className);
	
	/**
	 * Add an implied class ref to the persistence unit at the specified index and 
	 * return the object representing it.
	 */
	ClassRef addImpliedClassRef(int index, String className);
	
	/**
	 * Remove the implied class ref from the persistence unit.
	 */
	void removeImpliedClassRef(ClassRef classRef);
	
	/**
	 * Remove the implied class ref at the specified index from the persistence unit.
	 */
	void removeImpliedClassRef(int index);
	
	
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
	ListIterator<Property> properties();
	
	int propertiesSize();
	
	/**
	 * Add a property to the persistence unit and return the object 
	 * representing it.
	 */
	Property addProperty();
	
	Property getProperty(String key);
	
	Property getProperty(String key, String value);

	ListIterator<Property> propertiesWithPrefix(String keyPrefix);
	
	void putProperty(String key, String value, boolean allowDuplicates);
	
	void replacePropertyValue(String key, String oldValue, String newValue);
	
	boolean containsProperty(String key);

	/**
	 * Remove the property from the persistence unit.
	 */
	void removeProperty(Property property);
	
	/**
	 * Remove the property with the given key from the persistence unit.
	 */
	void removeProperty(String key);
	
	/**
	 * Remove the property with the given key and valuefrom the persistence unit.
	 */
	void removeProperty(String key, String value);
	
	
	// **************** PersistenceUnitDefaults ********************************
	
	String getDefaultSchema();
		String DEFAULT_SCHEMA_PROPERTY = "defaultSchema";
		
	String getDefaultCatalog();
		String DEFAULT_CATALOG_PROPERTY = "defaultCatalog";
	
	AccessType getDefaultAccess();
		String DEFAULT_ACCESS_PROPERTY = "defaultAccess";
	
	boolean getDefaultCascadePersist();
		String DEFAULT_CASCADE_PERSIST_PROPERTY = "defaultCascadePersist";
	
	
	// **************** global generator and query support *********************
	
	/**
	 * Identifier for changes to the list of global generators.
	 * Note that there are no granular changes to this list.  There is only
	 * notification that the entire list has changed.
	 */
	String GENERATORS_LIST = "generators";
	
	/**
	 * Add the generator (defined elsewhere) to the list of generators defined
	 * within this persistence unit.
	 * Note that this should only be called during the process of updating the
	 * local generator definition.
	 * No change notification accompanies this action specifically.
	 */
	void addGenerator(Generator generator);
	
	/**
	 * Return an iterator on all generators defined within this persistence unit,
	 * included duplicately named generators.
	 */
	ListIterator<Generator> allGenerators();
	
	/**
	 * Identifier for changes to the list of global queries.
	 * Note that there are no granular changes to this list.  There is only
	 * notification that the entire list has changed.
	 */
	String QUERIES_LIST = "queries";
	
	/**
	 * Add the query (defined elsewhere) to the list of queries defined 
	 * within this persistence unit.
	 * Note that this should only be called during the process of updating the
	 * local query definition.
	 * No change notification accompanies this action specifically.
	 */
	void addQuery(Query query);
	
	/**
	 * Return an iterator on all queries defined within this persistence unit,
	 * included duplicately named queries.
	 */
	ListIterator<Query> allQueries();
	
	
	// **************** updating ***********************************************
	
	void update(XmlPersistenceUnit persistenceUnit);
	
	
	// *************************************************************************
	
	/**
	 * Return the IPersistentType specified in this PersistenceUnit with the given
	 * fully qualified type name
	 */
	PersistentType persistentType(String fullyQualifiedTypeName);
	
	/**
	 * Return whether the text representation of this persistence unit contains
	 * the given text offset
	 */
	boolean containsOffset(int textOffset);

}
