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

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.Generator;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.Query;
import org.eclipse.jpt.core.context.java.JavaGeneratedValue;
import org.eclipse.jpt.core.context.java.JavaGenerator;
import org.eclipse.jpt.core.context.java.JavaQuery;
import org.eclipse.jpt.core.context.orm.OrmGeneratedValue;
import org.eclipse.jpt.core.context.orm.OrmGenerator;
import org.eclipse.jpt.core.context.orm.OrmQuery;
import org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

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
	
	Persistence getParent();
	
	
	// **************** name ***************************************************
	
	/**
	 * String constant associated with changes to the persistence unit's name
	 */
	final static String NAME_PROPERTY = "name"; //$NON-NLS-1$
	
	/**
	 * Return the name of the persistence unit.
	 */
	String getName();
	
	/**
	 * Set the name of the persistence unit.
	 */
	void setName(String name);
	
	
	// **************** transaction type ***************************************
	
	PersistenceUnitTransactionType getTransactionType();

	/**
	 * String constant associated with changes to the persistence unit's 
	 * specified transaction type
	 */
	String SPECIFIED_TRANSACTION_TYPE_PROPERTY = "specifiedTransactionType"; //$NON-NLS-1$
	
	/** 
	 * Return the transaction type of the persistence unit, one of the values of 
	 * {@link PersistenceUnitTransactionType} 
	 */
	PersistenceUnitTransactionType getSpecifiedTransactionType();
	
	/** 
	 * Set the transaction type of the persistence unit, one of the values of 
	 * {@link PersistenceUnitTransactionType} 
	 */
	void setSpecifiedTransactionType(PersistenceUnitTransactionType transactionType);
	
	/**
	 * String constant associated with changes to the persistence unit's 
	 * default transaction type (not typically changed)
	 */
	final static String DEFAULT_TRANSACTION_TYPE_PROPERTY = "defaultTransactionType"; //$NON-NLS-1$
	
	/** 
	 * Return the default transaction type 
	 */
	PersistenceUnitTransactionType getDefaultTransactionType();
	
	
	
	// **************** description ********************************************
	
	/**
	 * String constant associated with changes to the persistence unit's description
	 */
	final static String DESCRIPTION_PROPERTY = "description"; //$NON-NLS-1$
	
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
	final static String PROVIDER_PROPERTY = "provider"; //$NON-NLS-1$
	
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
	final static String JTA_DATA_SOURCE_PROPERTY = "jtaDataSource"; //$NON-NLS-1$
	
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
	final static String NON_JTA_DATA_SOURCE_PROPERTY = "nonJtaDataSource"; //$NON-NLS-1$
	
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
	final static String SPECIFIED_MAPPING_FILE_REFS_LIST = "specifiedMappingFileRefs"; //$NON-NLS-1$
	
	/**
	 * Return an iterator on the list of specified mapping file refs.
	 * This will not be null.
	 */
	ListIterator<MappingFileRef> specifiedMappingFileRefs();
	
	/**
	 * Return size of specified mapping file refs.
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
	final static String IMPLIED_MAPPING_FILE_REF_PROPERTY = "impliedMappingFileRef"; //$NON-NLS-1$
	
	/**
	 * Return the current implied mapping file ref.
	 * This may be null.
	 */
	MappingFileRef getImpliedMappingFileRef();
	
	
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
	final static String SPECIFIED_CLASS_REFS_LIST = "specifiedClassRefs"; //$NON-NLS-1$
	
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
	final static String IMPLIED_CLASS_REFS_LIST = "impliedClassRefs"; //$NON-NLS-1$
	
	/**
	 * Return an iterator on the list of implied class refs.
	 * This will not be null.
	 */
	ListIterator<ClassRef> impliedClassRefs();
	
	/**
	 * Return the number of implied class refs.
	 */
	int impliedClassRefsSize();
	
	
	// **************** exclude unlisted classes *******************************
	
	/** 
	 * Return the "exclude unlisted classes" setting of the persistence unit.
	 */
	boolean isExcludeUnlistedClasses();

	/**
	 * String constant associated with changes to the persistence unit's 
	 * "exclude unlisted classes" setting
	 */
	final static String SPECIFIED_EXCLUDE_UNLISTED_CLASSES_PROPERTY = "specifiedExcludeUnlistedClasses"; //$NON-NLS-1$
	
	/** 
	 * Return the "exclude unlisted classes" setting of the persistence unit.
	 */
	Boolean getSpecifiedExcludeUnlistedClasses();
	
	/** 
	 * Set the "exclude unlisted classes" setting of the persistence unit.
	 */
	void setSpecifiedExcludeUnlistedClasses(Boolean excludeUnlistedClasses);
		
	/**
	 * String constant associated with changes to the persistence unit's 
	 * default "exclude unlisted classes" setting (not typically changed)
	 */
	final static String DEFAULT_EXCLUDE_UNLISTED_CLASSES_PROPERTY = "defaultExcludeUnlistedClasses"; //$NON-NLS-1$
	
	/** 
	 * Return the default "exclude unlisted classes" setting
	 */
	boolean getDefaultExcludeUnlistedClasses();
	
	
	// **************** properties *********************************************
	
	/**
	 * String constant associated with changes to the properties list
	 */
	final static String PROPERTIES_LIST = "properties"; //$NON-NLS-1$
	
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
	
	Property addProperty(int index);

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
	
	AccessType getDefaultAccess();
		String DEFAULT_ACCESS_PROPERTY = "defaultAccess"; //$NON-NLS-1$
	
	String getDefaultCatalog();
		String DEFAULT_CATALOG_PROPERTY = "defaultCatalog"; //$NON-NLS-1$
	
	String getDefaultSchema();
		String DEFAULT_SCHEMA_PROPERTY = "defaultSchema"; //$NON-NLS-1$
		
	boolean getDefaultCascadePersist();
		String DEFAULT_CASCADE_PERSIST_PROPERTY = "defaultCascadePersist"; //$NON-NLS-1$
	
	
	// **************** generators *********************
	
	/**
	 * Identifier for changes to the list of global generators.
	 * Note that there are no granular changes to this list.  There is only
	 * notification that the entire list has changed.
	 */
	String GENERATORS_LIST = "generators"; //$NON-NLS-1$
	
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
	 * Return an array of the names of the generators defined in the persistence
	 * unit, with duplicates removed.
	 */
	String[] uniqueGeneratorNames();

	/**
	 * Validate the ORM generators held by the specified generator holder.
	 */
	void validateGenerators(OrmGeneratorHolder generatorHolder, List<IMessage> messages);

	/**
	 * Validate the Java generators held by the specified generator holder.
	 */
	void validateGenerators(JavaGeneratorHolder generatorHolder, List<IMessage> messages, CompilationUnit astRoot);

	/**
	 * Validate whether the specified ORM generated value matches a
	 * generator defined in the persistence unit.
	 */
	void validateGeneratedValue(OrmGeneratedValueHolder generatedValueHolder, List<IMessage> messages);

	/**
	 * Validate whether the specified Java generated value matches a
	 * generator defined in the persistence unit.
	 */
	void validateGeneratedValue(JavaGeneratedValueHolder generatedValueHolder, List<IMessage> messages, CompilationUnit astRoot);
	
	
	// **************** queries *********************
	
	/**
	 * Identifier for changes to the list of global queries.
	 * Note that there are no granular changes to this list.  There is only
	 * notification that the entire list has changed.
	 */
	String QUERIES_LIST = "queries"; //$NON-NLS-1$
	
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
	
	/**
	 * Validate the ORM queries held by the specified query holder.
	 */
	void validateQueries(OrmQueryHolder queryHolder, List<IMessage> messages);

	/**
	 * Validate the Java queries held by the specified query holder.
	 */
	void validateQueries(JavaQueryHolder queryHolder, List<IMessage> messages, CompilationUnit astRoot);

	
	// **************** updating ***********************************************
	
	/**
	 * Update the PersistenceUnit context model object to match the XmlPersistenceUnit 
	 * resource model object. see {@link org.eclipse.jpt.core.JpaProject#update()}
	 */
	void update(XmlPersistenceUnit persistenceUnit);
	
	
	// *************************************************************************
	
	/**
	 * Return the PersistentType specified in this PersistenceUnit with the given
	 * fully qualified type name
	 */
	PersistentType getPersistentType(String fullyQualifiedTypeName);
	
	/**
	 * Return whether the text representation of this persistence unit contains
	 * the given text offset
	 */
	boolean containsOffset(int textOffset);

	
	// ********** validation callbacks **********
	
	interface OrmGeneratorHolder {
		Iterator<OrmGenerator> generators();
	}

	interface JavaGeneratorHolder {
		Iterator<JavaGenerator> generators();
	}

	interface OrmGeneratedValueHolder {
		OrmGeneratedValue getGeneratedValue();
	}

	interface JavaGeneratedValueHolder {
		JavaGeneratedValue getGeneratedValue();
	}

	interface OrmQueryHolder {
		Iterator<OrmQuery> queries();
	}

	interface JavaQueryHolder {
		Iterator<JavaQuery> queries();
	}

}
