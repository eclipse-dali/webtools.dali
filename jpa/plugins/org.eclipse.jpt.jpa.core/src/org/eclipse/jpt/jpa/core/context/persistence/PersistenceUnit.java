/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.persistence;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.DeleteTypeRefactoringParticipant;
import org.eclipse.jpt.jpa.core.context.Embeddable;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.Generator;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.jpt.jpa.core.context.TypeRefactoringParticipant;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.jpql.JpaJpqlQueryHelper;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlProperty;

/**
 * Context model corresponding to the XML resource model {@link XmlPersistenceUnit},
 * which corresponds to the <code>persistence-unit</code> element in the
 * <code>persistence.xml</code> file.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 *
 * @version 3.3
 * @since 2.0
 */
public interface PersistenceUnit
	extends JpaStructureNode, PersistentTypeContainer, MappingFileRefactoringParticipant, DeleteTypeRefactoringParticipant, TypeRefactoringParticipant
{
	/**
	 * Covariant override.
	 */
	Persistence getParent();


	// ********** name **********

	/**
	 * String constant associated with changes to the persistence unit's name.
	 */
	String NAME_PROPERTY = "name"; //$NON-NLS-1$

	/**
	 * Return the persistence unit's name.
	 */
	String getName();
	Transformer<PersistenceUnit, String> NAME_TRANSFORMER = new NameTransformer();
	class NameTransformer
		extends TransformerAdapter<PersistenceUnit, String>
	{
		@Override
		public String transform(PersistenceUnit persistenceUnit) {
			return persistenceUnit.getName();
		}
	}

	/**
	 * Set the persistence unit's name.
	 */
	void setName(String name);


	// ********** transaction type **********

	/**
	 * Creates a new {@link JpaJpqlQueryHelper} that provides functionality related to JPQL queries.
	 */
	JpaJpqlQueryHelper createJpqlQueryHelper();


	// ********** transaction type **********

	/**
	 * Return the persistence unit's transaction type,
	 * whether specified or defaulted.
	 */
	PersistenceUnitTransactionType getTransactionType();

	/**
	 * String constant associated with changes to the persistence unit's
	 * specified transaction type
	 */
	String SPECIFIED_TRANSACTION_TYPE_PROPERTY = "specifiedTransactionType"; //$NON-NLS-1$

	/**
	 * Return the persistence unit's specified transaction type.
	 */
	PersistenceUnitTransactionType getSpecifiedTransactionType();

	/**
	 * Set the persistence unit's specified transaction type.
	 */
	void setSpecifiedTransactionType(PersistenceUnitTransactionType transactionType);

	/**
	 * String constant associated with changes to the persistence unit's
	 * default transaction type (not typically changed).
	 */
	String DEFAULT_TRANSACTION_TYPE_PROPERTY = "defaultTransactionType"; //$NON-NLS-1$

	/**
	 * Return the persistence unit's default transaction type.
	 */
	PersistenceUnitTransactionType getDefaultTransactionType();


	// ********** description **********

	/**
	 * String constant associated with changes to the persistence unit's description.
	 */
	String DESCRIPTION_PROPERTY = "description"; //$NON-NLS-1$

	/**
	 * Return the persistence unit's description.
	 */
	String getDescription();

	/**
	 * Set the persistence unit's description.
	 */
	void setDescription(String description);


	// ********** provider **********

	/**
	 * String constant associated with changes to the persistence unit's provider.
	 */
	String PROVIDER_PROPERTY = "provider"; //$NON-NLS-1$

	/**
	 * Return the persistence unit's provider.
	 */
	String getProvider();

	/**
	 * Set the persistence unit's provider.
	 */
	void setProvider(String provider);


	// ********** JTA data source **********

	/**
	 * String constant associated with changes to the persistence unit's JTA data source
	 */
	String JTA_DATA_SOURCE_PROPERTY = "jtaDataSource"; //$NON-NLS-1$

	/**
	 * Return the persistence unit's JTA data source.
	 */
	String getJtaDataSource();

	/**
	 * Set the persistence unit's JTA data source.
	 */
	void setJtaDataSource(String jtaDataSource);


	// ********** non-JTA data source **********

	/**
	 * String constant associated with changes to the persistence unit's non-JTA data source
	 */
	String NON_JTA_DATA_SOURCE_PROPERTY = "nonJtaDataSource"; //$NON-NLS-1$

	/**
	 * Return the persistence unit's non-JTA data source.
	 */
	String getNonJtaDataSource();

	/**
	 * Set the persistence unit's non-JTA data source.
	 */
	void setNonJtaDataSource(String nonJtaDataSource);


	// ********** mapping file refs **********

	/**
	 * Return the persistence unit's list of mapping file refs,
	 * both specified and implied.
	 */
	ListIterable<MappingFileRef> getMappingFileRefs();

	/**
	 * Return the size of the persistence unit's list of mapping file refs,
	 * both specified and implied.
	 */
	int getMappingFileRefsSize();

	/**
	 * Return all the mapping file refs, both specified and implied,
	 * containing the specified type.
	 */
	Iterable<MappingFileRef> getMappingFileRefsContaining(String typeName);


	// ********** specified mapping file refs **********

	/**
	 * String constant associated with changes to the persistence unit's
	 * specified mapping file refs.
	 */
	String SPECIFIED_MAPPING_FILE_REFS_LIST = "specifiedMappingFileRefs"; //$NON-NLS-1$

	/**
	 * Return the persistence unit's list of specified mapping file refs.
	 */
	ListIterable<MappingFileRef> getSpecifiedMappingFileRefs();

	/**
	 * Return the size of the persistence unit's list of specified mapping file refs.
	 */
	int getSpecifiedMappingFileRefsSize();

	/**
	 * Add a new specified mapping file ref to the persistence unit;
	 * return the newly-created mapping file ref.
	 */
	MappingFileRef addSpecifiedMappingFileRef(String fileName);

	/**
	 * Add a new specified mapping file ref to the persistence unit at the specified index;
	 * return the newly-created mapping file ref.
	 */
	MappingFileRef addSpecifiedMappingFileRef(int index, String fileName);

	/**
	 * Remove the specified mapping file ref from the persistence unit.
	 */
	void removeSpecifiedMappingFileRef(MappingFileRef mappingFileRef);

	/**
	 * Remove the specified mapping file ref at the specified index from the persistence unit.
	 */
	void removeSpecifiedMappingFileRef(int index);


	// ********** implied mapping file ref **********

	/**
	 * String constant associated with changes to the persistence unit's
	 * implied mapping file ref.
	 */
	String IMPLIED_MAPPING_FILE_REF_PROPERTY = "impliedMappingFileRef"; //$NON-NLS-1$

	/**
	 * Return the persistence unit's implied mapping file ref.
	 */
	MappingFileRef getImpliedMappingFileRef();


	// ********** jar file refs **********

	/**
	 * String constant associated with changes to the persistence unit's
	 * JAR file refs.
	 */
	String JAR_FILE_REFS_LIST = "jarFileRefs"; //$NON-NLS-1$

	/**
	 * Return the persistence unit's list of JAR file refs.
	 */
	ListIterable<JarFileRef> getJarFileRefs();

	/**
	 * Return the size of the persistence unit's list of JAR file refs.
	 */
	int getJarFileRefsSize();

	/**
	 * Add a new JAR file ref to the persistence unit;
	 * return the newly-created JAR file ref.
	 */
	JarFileRef addJarFileRef(String fileName);

	/**
	 * Add a new JAR file ref to the persistence unit at the specified index;
	 * return the newly-created JAR file ref.
	 */
	JarFileRef addJarFileRef(int index, String fileName);

	/**
	 * Remove the specified JAR file ref from the persistence unit.
	 */
	void removeJarFileRef(JarFileRef jarFileRef);

	/**
	 * Remove the JAR file ref at the specified index from the persistence unit.
	 */
	void removeJarFileRef(int index);


	// ********** class refs **********

	/**
	 * Return the persistence unit's list of class refs,
	 * both specified and implied.
	 */
	Iterable<ClassRef> getClassRefs();

	/**
	 * Return the size of the persistence unit's list of class refs,
	 * both specified and implied.
	 */
	int getClassRefsSize();


	// ********** specified class refs **********

	/**
	 * String constant associated with changes to the persistence unit's
	 * specified class refs.
	 */
	String SPECIFIED_CLASS_REFS_LIST = "specifiedClassRefs"; //$NON-NLS-1$

	/**
	 * Return the persistence unit's list of specified class refs.
	 */
	ListIterable<ClassRef> getSpecifiedClassRefs();

	/**
	 * Return the size of the persistence unit's list of specified mapping file refs.
	 */
	int getSpecifiedClassRefsSize();

	/**
	 * Add a new specified class ref to the persistence unit;
	 * return the newly-created class ref.
	 */
	ClassRef addSpecifiedClassRef(String className);

	/**
	 * Add a new specified class ref to the persistence unit at the specified index;
	 * return the newly-created class ref.
	 */
	ClassRef addSpecifiedClassRef(int index, String className);

	/**
	 * Remove the specified class ref from the persistence unit.
	 */
	void removeSpecifiedClassRef(ClassRef classRef);

	/**
	 * Remove the specified class refs from the persistence unit.
	 */
	void removeSpecifiedClassRefs(Iterable<ClassRef> classRefs);

	/**
	 * Remove the specified class ref at the specified index from the persistence unit.
	 */
	void removeSpecifiedClassRef(int index);


	// ********** implied class refs **********

	/**
	 * String constant associated with changes to the persistence unit's
	 * implied class refs.
	 */
	String IMPLIED_CLASS_REFS_COLLECTION = "impliedClassRefs"; //$NON-NLS-1$

	/**
	 * Return the persistence unit's list of implied class refs.
	 */
	Iterable<ClassRef> getImpliedClassRefs();

	/**
	 * Return the size of the persistence unit's list of implied class refs.
	 */
	int getImpliedClassRefsSize();


	// ********** exclude unlisted classes **********

	/**
	 * Return whether the persistence unit excludes unlisted classes.
	 */
	boolean excludesUnlistedClasses();

	/**
	 * String constant associated with changes to the persistence unit's
	 * "exclude unlisted classes" flag.
	 */
	String SPECIFIED_EXCLUDE_UNLISTED_CLASSES_PROPERTY = "specifiedExcludeUnlistedClasses"; //$NON-NLS-1$

	/**
	 * Return the persistence unit's specified "exclude unlisted classes" flag.
	 */
	Boolean getSpecifiedExcludeUnlistedClasses();

	/**
	 * Set the persistence unit's specified "exclude unlisted classes" flag.
	 */
	void setSpecifiedExcludeUnlistedClasses(Boolean excludeUnlistedClasses);

	/**
	 * String constant associated with changes to the persistence unit's
	 * default "exclude unlisted classes" flag (not typically changed).
	 */
	String DEFAULT_EXCLUDE_UNLISTED_CLASSES_PROPERTY = "defaultExcludeUnlistedClasses"; //$NON-NLS-1$

	/**
	 * Return whether the persistence unit excludes unlisted classes by default.
	 */
	boolean getDefaultExcludeUnlistedClasses();


	// ********** properties **********

	/**
	 * String constant associated with changes to the persistence unit's
	 * properties.
	 */
	String PROPERTIES_LIST = "properties"; //$NON-NLS-1$

	/**
	 * Return the persistence unit's list of properties.
	 */
	ListIterable<Property> getProperties();

	/**
	 * Return the size of the persistence unit's list of properties.
	 */
	int getPropertiesSize();

	/**
	 * Return the *first* property in the persistence unit's property list with
	 * the specified name. Return null if the list does not contain a property
	 * with the specified name.
	 */
	Property getProperty(String propertyName);

	/**
	 * Return all the properties in the persistence unit with the specified
	 * name. Return an empty Iterable if the persistence unit does not contain
	 * a property with the specified name.
	 */
	Iterable<Property> getPropertiesNamed(String propertyName);

	/**
	 * Return the persistence unit's properties with names beginning with the
	 * specified prefix.
	 */
	Iterable<Property> getPropertiesWithNamePrefix(String propertyNamePrefix);

	/**
	 * Add a new property to the persistence unit;
	 * return the newly-created property.
	 */
	Property addProperty();

	/**
	 * Add a new property to the persistence unit at the specified index;
	 * return the newly-created property.
	 */
	Property addProperty(int index);

	/**
	 * Set the value of the *first* property in the persistence unit's property
	 * list with the specified name to the specified value, creating a new
	 * property if one does not already exist. If a property exists and the
	 * specified value is null, the existing property is removed.
	 */
	void setProperty(String propertyName, String value);

	/**
	 * Set the value of the property with the specified name, creating a new
	 * property if one does not exist or if the specified flag indicates
	 * duplicate property names are allowed. If a property exists and duplicate
	 * values are not allowed and the specified value is null, the existing
	 * property is removed.
	 */
	void setProperty(String propertyName, String value, boolean duplicatePropertyNamesAllowed);

	/**
	 * Remove the specified property from the persistence unit.
	 */
	void removeProperty(Property property);

	/**
	 * Remove the *first* property in the persistence unit's property list
	 * with the specified name.
	 */
	void removeProperty(String propertyName);

	/**
	 * Remove the *first* property in the persistence unit's property list
	 * with the specified name and value, allowing the removal of properties
	 * with duplicate property names.
	 */
	void removeProperty(String propertyName, String value);

	/**
	 * Call back method for Property name changing.
	 */
	void propertyNameChanged(String oldPropertyName, String newPropertyName, String value);

	/**
	 * Call back method for Property value changing.
	 */
	void propertyValueChanged(String propertyName, String newValue);

	/**
	 * Simple property interface.
	 */
	interface Property
		extends JpaContextModel, TypeRefactoringParticipant
	{
		PersistenceUnit getParent();

		String NAME_PROPERTY = "name"; //$NON-NLS-1$
		String getName();
		void setName(String name);
		class NameEquals
			extends Predicate.Adapter<Property>
		{
			private final String propertyName;
			public NameEquals(String propertyName) {
				super();
				this.propertyName = propertyName;
			}
			@Override
			public boolean evaluate(Property property) {
				return ObjectTools.equals(this.propertyName, property.getName());
			}
		}
		class NameStartsWith
			extends Predicate.Adapter<Property>
		{
			private final String prefix;
			public NameStartsWith(String prefix) {
				super();
				this.prefix = prefix;
			}
			@Override
			public boolean evaluate(Property property) {
				String propertyName = property.getName();
				return (propertyName != null) && propertyName.startsWith(this.prefix);
			}
		}

		String VALUE_PROPERTY = "value"; //$NON-NLS-1$
		String getValue();
		void setValue(String value);
		Transformer<Property, String> VALUE_TRANSFORMER = new ValueTransformer();
		class ValueTransformer
			extends TransformerAdapter<Property, String>
		{
			@Override
			public String transform(Property property) {
				return property.getValue();
			}
		}

		XmlProperty getXmlProperty();
	}


	// ********** mapping file (orm.xml) persistence unit metadata **********

	/**
	 * String constant associated with changes to the persistence unit's
	 * "XML mapping metadata complete" flag.
	 */
	String XML_MAPPING_METADATA_COMPLETE_PROPERTY = "xmlMappingMetadataComplete"; //$NON-NLS-1$

	/**
	 * Return the default "XML mapping metadata complete" flag from the
	 * first persistence unit metadata
	 * found in the persistence unit's list of mapping files.
	 */
	boolean isXmlMappingMetadataComplete();

	/**
	 * String constant associated with changes to the persistence unit's
	 * default access type.
	 */
	String DEFAULT_ACCESS_PROPERTY = "defaultAccess"; //$NON-NLS-1$

	/**
	 * Return the default access type from the first persistence unit metadata
	 * found in the persistence unit's list of mapping files.
	 */
	AccessType getDefaultAccess();

	/**
	 * String constant associated with changes to the persistence unit's
	 * default database catalog.
	 */
	String DEFAULT_CATALOG_PROPERTY = "defaultCatalog"; //$NON-NLS-1$

	/**
	 * Return the default database catalog from the first persistence unit metadata
	 * found in the persistence unit's list of mapping files.
	 */
	String getDefaultCatalog();

	/**
	 * String constant associated with changes to the persistence unit's
	 * default database schema.
	 */
	String DEFAULT_SCHEMA_PROPERTY = "defaultSchema"; //$NON-NLS-1$

	/**
	 * Return the default database schema from the first persistence unit metadata
	 * found in the persistence unit's list of mapping files.
	 */
	String getDefaultSchema();

	/**
	 * String constant associated with changes to the persistence unit's
	 * default "cascade persist" flag.
	 */
	String DEFAULT_CASCADE_PERSIST_PROPERTY = "defaultCascadePersist"; //$NON-NLS-1$

	/**
	 * Return the default "cascade persist" flag from the first persistence unit metadata
	 * found in the persistence unit's list of mapping files.
	 */
	boolean getDefaultCascadePersist();


	// ********** generators **********

	/**
	 * String constant associated with changes to the persistence unit's
	 * collection of "global" generators.
	 */
	String GENERATORS_COLLECTION = "generators"; //$NON-NLS-1$

	/**
	 * Return the "active" generators defined within the persistence unit's scope,
	 * including generators with duplicate names. "Active" generators are:<ul>
	 * <li>any generator defined in mapping files
	 * <li>any generator defined via Java annotations that is not "overridden"
	 *     by a mapping file generator with the same name
	 * </ul>
	 * <strong>NB:</strong> A Java generator defined on a class or attribute
	 * that is overridden in a mapping file is <em>not</em>, as a result,
	 * itself overridden. A Java generator can only be overridden by a mapping
	 * file generator with the same name.
	 * <p>
	 * <strong>NB:</strong> A Java generator defined on a class or attribute
	 * whose corresponding mapping file mapping (or mapping file) is marked
	 * "metadata complete" is ignored.
	 */
	Iterable<Generator> getGenerators();

	/**
	 * Return the number of "active" generators defined within the persistence
	 * unit's scope.
	 * @see #getGenerators()
	 */
	int getGeneratorsSize();

	/**
	 * Return the names of the "active" generators defined in the persistence
	 * unit's scope, with duplicates removed.
	 */
	Iterable<String> getUniqueGeneratorNames();

	/**
	 * Return whether the persistence unit contains any "convertible"
	 * generators (i.e. Java generators that are neither overridden nor
	 * [erroneously] duplicated).
	 * @see #convertJavaGenerators(EntityMappings, IProgressMonitor)
	 */
	boolean hasConvertibleJavaGenerators();
	
	/**
	 * Convert all "convertible" Java generators to global-level mapping file
	 * generators.
	 * @see #hasConvertibleJavaGenerators()
	 */
	void convertJavaGenerators(EntityMappings entityMappings, IProgressMonitor monitor);
	

	// ********** queries **********

	/**
	 * String constant associated with changes to the persistence unit's
	 * collection of "global" queries.
	 */
	String QUERIES_COLLECTION = "queries"; //$NON-NLS-1$

	/**
	 * Return the "active" queries defined within the persistence unit's scope,
	 * including queries with duplicate names. These are very similar to
	 * generators.
	 * @see #getGenerators()
	 */
	Iterable<Query> getQueries();

	/**
	 * Return the number of "active" queries defined within the persistence
	 * unit's scope.
	 * @see #getQueries()
	 */
	int getQueriesSize();

	/**
	 * Add the specified query (that is defined elsewhere) to the
	 * list of queries defined within the persistence unit's scope.
	 */
	void addQuery(Query query);
	
	/**
	 * Return whether the persistence unit contains any "convertible"
	 * queries. (i.e. Java queries that are neither overridden nor
	 * [erroneously] duplicated - by default, any Java queries with the same
	 * name are "duplicates").
	 * @see #convertJavaQueries(EntityMappings, IProgressMonitor)
	 */
	boolean hasConvertibleJavaQueries();
	
	/**
	 * Convert all "convertible" Java queries to global-level mapping file
	 * queries.
	 * @see #hasConvertibleJavaQueries()
	 */
	void convertJavaQueries(EntityMappings entityMappings, IProgressMonitor monitor);


	// ********** misc **********

	/**
	 * Return the XML resource model corresponding to the
	 * persistence unit.
	 */
	XmlPersistenceUnit getXmlPersistenceUnit();

	/**
	 * Return whether the persistence unit specifies a managed type with the
	 * specified name (i.e. the type is listed either in the persistence unit's
	 * list of specified classes or in one of the persistent unit's mapping files).
	 */
	boolean specifiesManagedType(String typeName);

	/**
	 * Return the persistence unit's Java persistent types, as specified by
	 * the class refs (both specified and implied) and jar files.
	 * There can be duplicate types, and any of them may be overridden by a
	 * mapping file persistence type.
	 */
	Iterable<PersistentType> getJavaPersistentTypes();

	/**
	 * Return the persistence unit's entities.
	 */
	Iterable<Entity> getEntities();

	/**
	 * Return the entity specified in the persistence unit with the
	 * specified name. Return null if there is no persistent type
	 * with the specified name or if the persistent type is not mapped as an
	 * entity.
	 * @see org.eclipse.jpt.jpa.core.MappingKeys#ENTITY_TYPE_MAPPING_KEY
	 */
	Entity getEntity(String typeName);

	/**
	 * Return the embeddable specified in the persistence unit with the
	 * specified name. Return null if there is no persistent type
	 * with the specified name or if the persistent type is not mapped as an
	 * embeddable.
	 * @see org.eclipse.jpt.jpa.core.MappingKeys#EMBEDDABLE_TYPE_MAPPING_KEY
	 */
	Embeddable getEmbeddable(String typeName);

	/**
	 * Synchronize the persistence unit's list of specified classes with the
	 * types in the project currently annotated.
	 */
	void synchronizeClasses(IProgressMonitor monitor);
	
	/**
	 * Return the names of all the packages valid cross the persistence unit
	 * including the ones from jar files
	 */
	Iterable<String> getPackageNames();

	/**
	 * Return a location relative to the beginning of the persistence.xml for
	 * inserting a new mapping-file element. If there are existing mapping files,
	 * the location should be after those. If no existing mapping files then make
	 * sure the location does not violate the persistence.xml schema.
	 */
	int findInsertLocationForMappingFileRef();
	

	// ********** validation **********

	/**
	 * Return whether the persistence unit validates agains database metadata.
	 * (For instance, if the connection is not active, then it should not.)
	 */
	boolean validatesAgainstDatabase();

	//TODO would like to remove this eventually
	void dispose();

}
