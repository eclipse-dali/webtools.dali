/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.persistence;

import java.util.Iterator;
import java.util.ListIterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.Embeddable;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.Generator;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.jpt.jpa.core.context.XmlContextNode;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlProperty;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.ReplaceEdit;

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
 * @version 3.0
 * @since 2.0
 */
public interface PersistenceUnit
	extends XmlContextNode, JpaStructureNode, PersistentTypeContainer
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

	/**
	 * Set the persistence unit's name.
	 */
	void setName(String name);


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
	ListIterator<MappingFileRef> mappingFileRefs();

	/**
	 * Return the size of the persistence unit's list of mapping file refs,
	 * both specified and implied.
	 */
	int mappingFileRefsSize();

	/**
	 * Return all the mapping file refs, both specified and implied,
	 * containing the specified type.
	 */
	Iterator<MappingFileRef> mappingFileRefsContaining(String typeName);


	// ********** specified mapping file refs **********

	/**
	 * String constant associated with changes to the persistence unit's
	 * specified mapping file refs.
	 */
	String SPECIFIED_MAPPING_FILE_REFS_LIST = "specifiedMappingFileRefs"; //$NON-NLS-1$

	/**
	 * Return the persistence unit's list of specified mapping file refs.
	 */
	ListIterator<MappingFileRef> specifiedMappingFileRefs();

	/**
	 * Return the size of the persistence unit's list of specified mapping file refs.
	 */
	int specifiedMappingFileRefsSize();

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
	ListIterator<JarFileRef> jarFileRefs();

	/**
	 * Return the size of the persistence unit's list of JAR file refs.
	 */
	int jarFileRefsSize();

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
	Iterator<ClassRef> classRefs();

	/**
	 * Return the size of the persistence unit's list of class refs,
	 * both specified and implied.
	 */
	int classRefsSize();


	// ********** specified class refs **********

	/**
	 * String constant associated with changes to the persistence unit's
	 * specified class refs.
	 */
	String SPECIFIED_CLASS_REFS_LIST = "specifiedClassRefs"; //$NON-NLS-1$

	/**
	 * Return the persistence unit's list of specified class refs.
	 */
	ListIterator<ClassRef> specifiedClassRefs();

	/**
	 * Return the size of the persistence unit's list of specified mapping file refs.
	 */
	int specifiedClassRefsSize();

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
	Iterator<ClassRef> impliedClassRefs();

	/**
	 * Return the size of the persistence unit's list of implied class refs.
	 */
	int impliedClassRefsSize();


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
	ListIterator<Property> properties();

	/**
	 * Return the size of the persistence unit's list of properties.
	 */
	int propertiesSize();

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
	Iterator<Property> propertiesWithNamePrefix(String propertyNamePrefix);

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
		extends XmlContextNode
	{
		PersistenceUnit getParent();

		@SuppressWarnings("hiding")
		String NAME_PROPERTY = "name"; //$NON-NLS-1$
		String getName();
		void setName(String name);

		String VALUE_PROPERTY = "value"; //$NON-NLS-1$
		String getValue();
		void setValue(String value);

		XmlProperty getXmlProperty();

		/**
		 * Create ReplaceEdits for renaming the property value to the newName.
		 * The originalType has not yet been renamed, the newName is the new short name.
		 * If this value does not match the original type, then return an empty Iterable.
		 */
		Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName);

		/**
		 * Create ReplaceEdits for moving any references to the originalType to the newPackage.
		 * The originalType has not yet been moved.
		 */
		Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage);

		/**
		 * Create ReplaceEdits for renaming the property value package to the newName.
		 * The originalPackage has not yet been renamed.
		 * If this value is not in the originalPackage, then return an empty Iterable.
		 */
		Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName);
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
	 * NB: There are no granular collection change notifications;
	 * only a "collection changed" notification when the collection
	 * is rebuilt at the finish of the persistence unit's "update".
	 */
	String GENERATORS_COLLECTION = "generators"; //$NON-NLS-1$

	/**
	 * Return the generators defined within the persistence unit's scope,
	 * including generators with duplicate names.
	 */
	Iterator<Generator> generators();

	/**
	 * Return the size of the list of generators defined within the persistence unit's scope,
	 * including generators with duplicate names.
	 */
	int generatorsSize();

	/**
	 * Add the specified generator (that is defined elsewhere) to the
	 * list of generators defined within the persistence unit's scope.
	 * NB: This is to be called by every generator during "update".
	 * This method does not directly generate a change notification.
	 * The change notification is fired at the end of the persistence unit's
	 * "update", once all the generators have added themselves.
	 */
	void addGenerator(Generator generator);

	/**
	 * Return the names of the generators defined in the persistence
	 * unit's scope, with duplicates removed.
	 */
	Iterable<String> getUniqueGeneratorNames();


	// ********** queries **********

	/**
	 * String constant associated with changes to the persistence unit's
	 * collection of "global" queries.
	 * NB: There are no granular collection change notifications;
	 * only a "collection changed" notification when the collection is
	 * rebuilt at the finish of the persistence unit's "update".
	 */
	String QUERIES_COLLECTION = "queries"; //$NON-NLS-1$

	/**
	 * Return the queries defined within the persistence unit's scope,
	 * including queries with duplicate names.
	 */
	Iterator<Query> queries();

	/**
	 * Return the number of queries defined within the persistence unit's scope,
	 * including queries with duplicate names.
	 */
	int queriesSize();

	/**
	 * Add the specified query (that is defined elsewhere) to the
	 * list of queries defined within the persistence unit's scope.
	 * NB: This is to be called by every query during "update".
	 * This method does not directly generate a change notification.
	 * The change notification is fired at the end of the persistence unit's
	 * "update", once all the queries have added themselves.
	 */
	void addQuery(Query query);


	// ********** misc **********

	/**
	 * Return the XML resource model corresponding to the
	 * persistence unit.
	 */
	XmlPersistenceUnit getXmlPersistenceUnit();

	/**
	 * Return the persistent type specified in the persistence unit with the
	 * specified name.
	 */
	PersistentType getPersistentType(String typeName);

	/**
	 * Return whether the persistence unit specifies a persistent type with the
	 * specified name (i.e. the type is listed either in the persistence unit's
	 * list of specified classes or in one of the persistent unit's mapping files).
	 */
	boolean specifiesPersistentType(String typeName);

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
	 * Return whether the text representation of this persistence unit contains
	 * the given text offset
	 */
	boolean containsOffset(int textOffset);

	/**
	 * Return all the entities defined in both the implied and specified mapping files
	 * of a persistence unit
	 */
	 Iterable<Entity> getOrmEntities();
	
	/**
	 * Return the entity names of all the entities defined in both the implied and specified mapping files
	 * of a persistence unit 
	 */
	 Iterator<String> ormEntityNames();
	 
	/**
	 * Return all the entities defined with both the implied and specified Java classes
	 * of a persistence unit 
	 */
	 Iterable<Entity> getJavaEntities();
	
	/**
	 * Return the entity names of all the entities defined with both the implied and specified Java classes
	 * of a persistence unit 
	 */
	Iterator<String> javaEntityNames();
	
	/**
	 * Return the entity names of entities only defined with mapped Java classes of a persistence unit.
	 * The names of Java entities overridden by entities defined in the mapping files are excluded.
	 */
	Iterator<String> javaEntityNamesExclOverridden();
	

	// ********** validation **********

	/**
	 * Return whether the persistence unit validates agains database metadata.
	 * (For instance, if the connection is not active, then it should not.)
	 */
	boolean validatesAgainstDatabase();

	
	// ********** refactoring **********

	/**
	 * Create DeleteEdits for deleting any references to the deleted type.
	 * Return an EmptyIterable if there are not any references.
	 */
	Iterable<DeleteEdit> createDeleteTypeEdits(IType type);

	/**
	 * Create DeleteEdits for deleting any references to the deleted file
	 * Return an EmptyIterable if there are not any references.
	 */
	Iterable<DeleteEdit> createDeleteMappingFileEdits(IFile file);

	/**
	 * Create ReplaceEdits for renaming any references to the originalType to the newName.
	 * The originalType has not yet been renamed, the newName is the new short name.
	 */
	Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName);
	
	/**
	 * Create ReplaceEdits for moving any references to the originalType to the newPackage.
	 * The originalType has not yet been moved.
	 */
	Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage);

	/**
	 * Create ReplaceEdits for renaming any references to the originalPackage to the newName.
	 * The originalPackage has not yet been renamed.
	 */
	Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName);

	/**
	 * Create ReplaceEdits for renaming any references to the originalFolder to the newName.
	 * The originalFolder has not yet been renamed.
	 */
	Iterable<ReplaceEdit> createRenameFolderEdits(IFolder originalFolder, String newName);

	/**
	 * Create ReplaceEdits for renaming any references to the originalFile to the newName.
	 * Return an EmptyIterable if there are not any references.	
	 * The originalFile has not yet been renamed, the newName is the new short name.
	 */
	Iterable<ReplaceEdit> createRenameMappingFileEdits(IFile originalFile, String newName);

	/**
	 * Create ReplaceEdits for moving any references to the originalFile to the destination.
	 * Return an EmptyIterable if there are not any references.	
	 * The originalFile has not been moved yet.
	 */
	Iterable<ReplaceEdit> createMoveMappingFileEdits(IFile originalFile, IPath runtineDestination);

	/**
	 * Create ReplaceEdits for moving any references to the originalFolder to the runtimeDestination.
	 * The runtimeDestination already includes the original folder name.
	 */
	Iterable<ReplaceEdit> createMoveFolderEdits(IFolder originalFolder, IPath runtimeDestination);

	/**
	 * Return a location relative to the beginning of the persistence.xml for 
	 * inserting a new mapping-file element. If there are existing mapping files, 
	 * the location should be after those. If no existing mapping files then make
	 * sure the location does not violate the persistence.xml schema.
	 */
	int findInsertLocationForMappingFileRef();

}
