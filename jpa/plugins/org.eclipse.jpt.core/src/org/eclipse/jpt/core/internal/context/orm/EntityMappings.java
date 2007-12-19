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
package org.eclipse.jpt.core.internal.context.orm;

import java.util.ListIterator;
import org.eclipse.jpt.core.internal.context.base.AccessType;
import org.eclipse.jpt.core.internal.context.base.IJpaContextNode;
import org.eclipse.jpt.core.internal.context.base.INamedNativeQuery;
import org.eclipse.jpt.core.internal.context.base.INamedQuery;
import org.eclipse.jpt.core.internal.context.base.ISequenceGenerator;
import org.eclipse.jpt.core.internal.context.base.ITableGenerator;
import org.eclipse.jpt.core.internal.resource.orm.TypeMapping;

public interface EntityMappings extends IJpaContextNode
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
	 * Return the specifiedAccess if not null, otherwise return the defaultCatalog.
	 */
	AccessType getAccess();
	AccessType getDefaultAccess();
		String DEFAULT_ACCESS_PROPERTY = "defaultAccessProperty";
	AccessType getSpecifiedAccess();
	void setSpecifiedAccess(AccessType newSpecifiedAccess);
		String SPECIFIED_ACCESS_PROPERTY = "specifiedAccessProperty";

		
	PersistenceUnitMetadata getPersistenceUnitMetadata();
	
	
	ListIterator<XmlPersistentType> xmlPersistentTypes();
	int xmlPersistentTypesSize();
	XmlPersistentType addXmlPersistentType(String mappingKey, String className);
	void removeXmlPersistentType(int index);
	//void moveXmlPersistentType(int targetIndex, int sourceIndex);
		String PERSISTENT_TYPES_LIST = "persistentTypes";
	

	<T extends ISequenceGenerator> ListIterator<T> sequenceGenerators();
	int sequenceGeneratorsSize();
	ISequenceGenerator addSequenceGenerator(int index);
	void removeSequenceGenerator(int index);
	void moveSequenceGenerator(int targetIndex, int sourceIndex);
		String SEQUENCE_GENERATORS_LIST = "sequenceGeneratorsList";

	<T extends ITableGenerator> ListIterator<T> tableGenerators();
	int tableGeneratorsSize();
	ITableGenerator addTableGenerator(int index);
	void removeTableGenerator(int index);
	void moveTableGenerator(int targetIndex, int sourceIndex);
		String TABLE_GENERATORS_LIST = "tableGeneratorsList";

	<T extends INamedQuery> ListIterator<T> namedQueries();
	int namedQueriesSize();
	INamedQuery addNamedQuery(int index);
	void removeNamedQuery(int index);
	void moveNamedQuery(int targetIndex, int sourceIndex);
		String NAMED_QUERIES_LIST = "namedQueriesList";

	<T extends INamedNativeQuery> ListIterator<T> namedNativeQueries();
	int namedNativeQueriesSize();
	INamedNativeQuery addNamedNativeQuery(int index);
	void removeNamedNativeQuery(int index);
	void moveNamedNativeQuery(int targetIndex, int sourceIndex);
		String NAMED_NATIVE_QUERIES_LIST = "namedNativeQueriesList";

		
	
	PersistenceUnitDefaults persistenceUnitDefaults();
	
	/**
	 * Return the XmlPersistentType listed in this mapping file
	 * with the given fullyQualifiedTypeName.  Return null if non exists.
	 */
	XmlPersistentType persistentTypeFor(String fullyQualifiedTypeName);
	
	void changeMapping(XmlPersistentType xmlPersistentType, XmlTypeMapping<? extends TypeMapping> oldMapping, XmlTypeMapping<? extends TypeMapping> newMapping);
	
	// **************** updating ***********************************************
	
	void initialize(org.eclipse.jpt.core.internal.resource.orm.EntityMappings entityMappings);
	
	void update(org.eclipse.jpt.core.internal.resource.orm.EntityMappings entityMappings);
}
