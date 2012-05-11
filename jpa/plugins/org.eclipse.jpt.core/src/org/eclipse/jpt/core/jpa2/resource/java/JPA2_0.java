/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.jpa2.resource.java;

import org.eclipse.jpt.core.resource.java.JPA;

/**
 * JPA 2.0 Java-related stuff (annotations etc.)
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.3
 */
@SuppressWarnings("nls")
public interface JPA2_0 
{
	// JPA package
	String PACKAGE = "javax.persistence";
	String PACKAGE_ = PACKAGE + '.';


	// ********** API **********

	// JPA 2.0 annotations
	String ACCESS = PACKAGE_ + "Access";
		String ACCESS__VALUE = "value";

	// ASSOCIATION_OVERRIDE
		String ASSOCIATION_OVERRIDE__JOIN_TABLE = "joinTable";

	String CACHEABLE = PACKAGE_ + "Cacheable";
		String CACHEABLE__VALUE = "value";
		
	String CASCADE_TYPE__DETACH = JPA.CASCADE_TYPE_ + "DETACH";

	String COLLECTION_TABLE = PACKAGE_ + "CollectionTable";
		String COLLECTION_TABLE__NAME = "name";
		String COLLECTION_TABLE__SCHEMA = "schema";
		String COLLECTION_TABLE__CATALOG = "catalog";
		String COLLECTION_TABLE__JOIN_COLUMNS = "joinColumns";
		String COLLECTION_TABLE__UNIQUE_CONSTRAINTS = "uniqueConstraints";
	
	String ELEMENT_COLLECTION = PACKAGE_ + "ElementCollection";
		String ELEMENT_COLLECTION__FETCH = "fetch";
		String ELEMENT_COLLECTION__TARGET_CLASS = "targetClass";

	String MAP_KEY_CLASS = PACKAGE_ + "MapKeyClass";
		String MAP_KEY_CLASS__VALUE = "value";
	
	String MAP_KEY_COLUMN = PACKAGE_ + "MapKeyColumn";
		String MAP_KEY_COLUMN__NAME = "name";
		String MAP_KEY_COLUMN__UNIQUE = "unique";
		String MAP_KEY_COLUMN__NULLABLE = "nullable";
		String MAP_KEY_COLUMN__INSERTABLE = "insertable";
		String MAP_KEY_COLUMN__UPDATABLE = "updatable";
		String MAP_KEY_COLUMN__COLUMN_DEFINITION = "columnDefinition";
		String MAP_KEY_COLUMN__TABLE = "table";
		String MAP_KEY_COLUMN__LENGTH = "length";
		String MAP_KEY_COLUMN__PRECISION = "precision";
		String MAP_KEY_COLUMN__SCALE = "scale";

	String MAP_KEY_ENUMERATED = PACKAGE_ + "MapKeyEnumerated";
		String MAP_KEY_ENUMERATED__VALUE = "value";
		
	String MAP_KEY_JOIN_COLUMN = PACKAGE_ + "MapKeyJoinColumn";
		String MAP_KEY_JOIN_COLUMN__NAME = "name";
		String MAP_KEY_JOIN_COLUMN__REFERENCED_COLUMN_NAME = "referencedColumnName";
		String MAP_KEY_JOIN_COLUMN__UNIQUE = "unique";
		String MAP_KEY_JOIN_COLUMN__NULLABLE = "nullable";
		String MAP_KEY_JOIN_COLUMN__INSERTABLE = "insertable";
		String MAP_KEY_JOIN_COLUMN__UPDATABLE = "updatable";
		String MAP_KEY_JOIN_COLUMN__COLUMN_DEFINITION = "columnDefinition";
		String MAP_KEY_JOIN_COLUMN__TABLE = "table";
		
	String MAP_KEY_JOIN_COLUMNS = PACKAGE_ + "MapKeyJoinColumns";
		String MAP_KEY_JOIN_COLUMNS__VALUE = "value";

	String MAP_KEY_TEMPORAL = PACKAGE_ + "MapKeyTemporal";
		String MAP_KEY_TEMPORAL__VALUE = "value";

	String MAPS_ID = PACKAGE_ + "MapsId";
		String MAPS_ID__VALUE = "value";

	// NAMED_QUERY
		String NAMED_QUERY__LOCK_MODE = "lockMode";

	// ONE_TO_MANY
		String ONE_TO_MANY__ORPHAN_REMOVAL = "orphanRemoval";

	// ONE_TO_ONE
		String ONE_TO_ONE__ORPHAN_REMOVAL = "orphanRemoval";

	String ORDER_COLUMN = PACKAGE_ + "OrderColumn";
		String ORDER_COLUMN__NAME = "name";
		String ORDER_COLUMN__NULLABLE = "nullable";
		String ORDER_COLUMN__INSERTABLE = "insertable";
		String ORDER_COLUMN__UPDATABLE = "updatable";
		String ORDER_COLUMN__COLUMN_DEFINITION = "columnDefinition";

	// SEQUENCE_GENERATOR
		String SEQUENCE_GENERATOR__CATALOG = "catalog";
		String SEQUENCE_GENERATOR__SCHEMA = "schema";


	// JPA 2.0 enums
	String ACCESS_TYPE = PACKAGE_ + "AccessType";
		String ACCESS_TYPE_ = ACCESS_TYPE + '.';
		String ACCESS_TYPE__FIELD = ACCESS_TYPE_ + "FIELD";
		String ACCESS_TYPE__PROPERTY = ACCESS_TYPE_ + "PROPERTY";

	String LOCK_MODE_TYPE = PACKAGE_ + "LockModeType";
		String LOCK_MODE_TYPE_ = LOCK_MODE_TYPE + '.';
		String LOCK_MODE_TYPE__READ = LOCK_MODE_TYPE_ + "READ";
		String LOCK_MODE_TYPE__WRITE = LOCK_MODE_TYPE_ + "WRITE";
		String LOCK_MODE_TYPE__OPTIMISTIC = LOCK_MODE_TYPE_ + "OPTIMISTIC";
		String LOCK_MODE_TYPE__OPTIMISTIC_FORCE_INCREMENT = LOCK_MODE_TYPE_ + "OPTIMISTIC_FORCE_INCREMENT";
		String LOCK_MODE_TYPE__PESSIMISTIC_READ = LOCK_MODE_TYPE_ + "PESSIMISTIC_READ";
		String LOCK_MODE_TYPE__PESSIMISTIC_WRITE = LOCK_MODE_TYPE_ + "PESSIMISTIC_WRITE";
		String LOCK_MODE_TYPE__PESSIMISTIC_FORCE_INCREMENT = LOCK_MODE_TYPE_ + "PESSIMISTIC_FORCE_INCREMENT";
		String LOCK_MODE_TYPE__NONE = LOCK_MODE_TYPE_ + "NONE";

	// JPA 2.0 metamodel
	String METAMODEL_PACKAGE = PACKAGE_ + "metamodel";
	String METAMODEL_PACKAGE_ = METAMODEL_PACKAGE + '.';

	String STATIC_METAMODEL = METAMODEL_PACKAGE_ + "StaticMetamodel";
		String STATIC_METAMODEL__VALUE = "value";

	String SINGULAR_ATTRIBUTE = METAMODEL_PACKAGE_ + "SingularAttribute";

	String COLLECTION_ATTRIBUTE = METAMODEL_PACKAGE_ + "CollectionAttribute";
	String LIST_ATTRIBUTE = METAMODEL_PACKAGE_ + "ListAttribute";
	String MAP_ATTRIBUTE = METAMODEL_PACKAGE_ + "MapAttribute";
	String SET_ATTRIBUTE = METAMODEL_PACKAGE_ + "SetAttribute";
}
