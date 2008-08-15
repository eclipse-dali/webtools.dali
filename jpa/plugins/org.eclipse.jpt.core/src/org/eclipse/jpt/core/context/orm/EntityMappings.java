/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.orm;

import java.util.ListIterator;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.QueryHolder;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface EntityMappings extends OrmJpaContextNode, JpaStructureNode, QueryHolder
{
	
	String getVersion();
		
	String getDescription();
	void setDescription(String newDescription);
		String DESCRIPTION_PROPERTY = "descriptionProperty";

	String getPackage();
	void setPackage(String newPackage);
		String PACKAGE_PROPERTY = "packageProperty";

	/**
	 * Return the specifiedSchema if not null, otherwise return the defaultSchema.
	 */
	String getSchema();
	String getDefaultSchema();
		String DEFAULT_SCHEMA_PROPERTY = "defaultSchemaProperty";
	String getSpecifiedSchema();
	void setSpecifiedSchema(String newSpecifiedSchema);
		String SPECIFIED_SCHEMA_PROPERTY = "specifiedSchemaProperty";

	/**
	 * Return the specifiedCatalog if not null, otherwise return the defaultCatalog.
	 */
	String getCatalog();
	String getDefaultCatalog();
		String DEFAULT_CATALOG_PROPERTY = "defaultCatalogProperty";
	String getSpecifiedCatalog();
	void setSpecifiedCatalog(String newSpecifiedCatalog);
		String SPECIFIED_CATALOG_PROPERTY = "specifiedCatalogProperty";

	/**
	 * Return the specifiedAccess if not null, otherwise return the defaultAccess.
	 */
	AccessType getAccess();
	AccessType getDefaultAccess();
		String DEFAULT_ACCESS_PROPERTY = "defaultAccessProperty";
	AccessType getSpecifiedAccess();
	void setSpecifiedAccess(AccessType newSpecifiedAccess);
		String SPECIFIED_ACCESS_PROPERTY = "specifiedAccessProperty";

		
	PersistenceUnitMetadata getPersistenceUnitMetadata();
	
	
	ListIterator<OrmPersistentType> ormPersistentTypes();
	int ormPersistentTypesSize();
	OrmPersistentType addOrmPersistentType(String mappingKey, String className);
	void removeOrmPersistentType(int index);
	void removeOrmPersistentType(OrmPersistentType ormPersistentType);
	//void moveOrmPersistentType(int targetIndex, int sourceIndex);
	boolean containsPersistentType(String className);
		String PERSISTENT_TYPES_LIST = "persistentTypes";
	

	ListIterator<OrmSequenceGenerator> sequenceGenerators();
	int sequenceGeneratorsSize();
	OrmSequenceGenerator addSequenceGenerator(int index);
	void removeSequenceGenerator(int index);
	void removeSequenceGenerator(OrmSequenceGenerator sequenceGenerator);
	void moveSequenceGenerator(int targetIndex, int sourceIndex);
		String SEQUENCE_GENERATORS_LIST = "sequenceGeneratorsList";

	ListIterator<OrmTableGenerator> tableGenerators();
	int tableGeneratorsSize();
	OrmTableGenerator addTableGenerator(int index);
	void removeTableGenerator(int index);
	void removeTableGenerator(OrmTableGenerator tableGenerator);
	void moveTableGenerator(int targetIndex, int sourceIndex);
		String TABLE_GENERATORS_LIST = "tableGeneratorsList";

	@SuppressWarnings("unchecked")
	ListIterator<OrmNamedQuery> namedQueries();
	int namedQueriesSize();
	OrmNamedQuery addNamedQuery(int index);
	void removeNamedQuery(int index);
	void moveNamedQuery(int targetIndex, int sourceIndex);

	@SuppressWarnings("unchecked")
	ListIterator<OrmNamedNativeQuery> namedNativeQueries();
	int namedNativeQueriesSize();
	OrmNamedNativeQuery addNamedNativeQuery(int index);
	void removeNamedNativeQuery(int index);
	void moveNamedNativeQuery(int targetIndex, int sourceIndex);

		
	
	PersistenceUnitDefaults getPersistenceUnitDefaults();
	
	/**
	 * Return the {@link OrmPersistentType) listed in this mapping file
	 * with the given fullyQualifiedTypeName.  Return null if none exists.
	 */
	OrmPersistentType getPersistentType(String fullyQualifiedTypeName);
	
	void changeMapping(OrmPersistentType ormPersistentType, OrmTypeMapping oldMapping, OrmTypeMapping newMapping);
	
	// **************** updating ***********************************************
		
	/**
	 * Update the EntityMappings context model object to match the XmlEntityMappings 
	 * resource model object. see {@link org.eclipse.jpt.core.JpaProject#update()}
	 */
	void update(XmlEntityMappings entityMappings);
	
	// *************************************************************************
	
	boolean containsOffset(int textOffset);
}
