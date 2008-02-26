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
package org.eclipse.jpt.core.context.orm;

import java.util.ListIterator;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.SequenceGenerator;
import org.eclipse.jpt.core.context.TableGenerator;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;

public interface EntityMappings extends OrmJpaContextNode, JpaStructureNode
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
	

	<T extends SequenceGenerator> ListIterator<T> sequenceGenerators();
	int sequenceGeneratorsSize();
	SequenceGenerator addSequenceGenerator(int index);
	void removeSequenceGenerator(int index);
	void removeSequenceGenerator(SequenceGenerator sequenceGenerator);
	void moveSequenceGenerator(int targetIndex, int sourceIndex);
		String SEQUENCE_GENERATORS_LIST = "sequenceGeneratorsList";

	<T extends TableGenerator> ListIterator<T> tableGenerators();
	int tableGeneratorsSize();
	TableGenerator addTableGenerator(int index);
	void removeTableGenerator(int index);
	void removeTableGenerator(TableGenerator tableGenerator);
	void moveTableGenerator(int targetIndex, int sourceIndex);
		String TABLE_GENERATORS_LIST = "tableGeneratorsList";

	ListIterator<OrmNamedQuery> namedQueries();
	int namedQueriesSize();
	OrmNamedQuery addNamedQuery(int index);
	void removeNamedQuery(int index);
	void removeNamedQuery(OrmNamedQuery namedQuery);
	void moveNamedQuery(int targetIndex, int sourceIndex);
		String NAMED_QUERIES_LIST = "namedQueriesList";

	ListIterator<OrmNamedNativeQuery> namedNativeQueries();
	int namedNativeQueriesSize();
	OrmNamedNativeQuery addNamedNativeQuery(int index);
	void removeNamedNativeQuery(int index);
	void removeNamedNativeQuery(OrmNamedNativeQuery namedNativeQuery);
	void moveNamedNativeQuery(int targetIndex, int sourceIndex);
		String NAMED_NATIVE_QUERIES_LIST = "namedNativeQueriesList";

		
	
	PersistenceUnitDefaults persistenceUnitDefaults();
	
	/**
	 * Return the {@link OrmPersistentType) listed in this mapping file
	 * with the given fullyQualifiedTypeName.  Return null if none exists.
	 */
	OrmPersistentType persistentTypeFor(String fullyQualifiedTypeName);
	
	void changeMapping(OrmPersistentType ormPersistentType, OrmTypeMapping oldMapping, OrmTypeMapping newMapping);
	
	// **************** updating ***********************************************
	
	void initialize(XmlEntityMappings entityMappings);
	
	void update(XmlEntityMappings entityMappings);
	
	// *************************************************************************
	
	boolean containsOffset(int textOffset);
}
