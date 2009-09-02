/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.v2_0.resource.orm;

@SuppressWarnings("nls")
public interface JPA
{
	
	String NAMESPACE_URL = "http://www.eclipse.org/eclipselink/xsds/persistence/orm";
	String SCHEMA_LOCATION_1_1 = "http://www.eclipse.org/eclipselink/xsds/eclipselink_orm_1_1.xsd";
	String VERSION_1_1	= "1.1";
	
	String ACCESS = "access";
	String ALLOCATION_SIZE = "allocation-size";
	String ASSOCIATION_OVERRIDE = "association-override";
	String ATTRIBUTE_OVERRIDE = "attribute-override";
	String ATTRIBUTES = "attributes";
	String BASIC = "basic";
	String CASCADE = "cascade";
	String CASCADE_ALL = "cascade-all";
	String CASCADE_MERGE = "cascade-merge";
	String CASCADE_PERSIST = "cascade-persist";
	String CASCADE_REFRESH = "cascade-refresh";	
	String CASCADE_REMOVE = "cascade-remove";	
	String CATALOG = "catalog";
	String CLASS = "class";
	String COLUMN = "column";
	String COLUMN_DEFINITION= "column-definition";
	String COLUMN_NAME = "column-name";
	String COLUMN_RESULT = "column-result";
	String DESCRIPTION = "description";
	String DISCRIMINATOR_COLUMN = "discriminator-column";
	String DISCRIMINATOR_TYPE = "discriminator-type";
	String DISCRIMINATOR_VALUE = "discriminator-value";
	String EMBEDDABLE = "embeddable";
	String EMBEDDED = "embedded";
	String EMBEDDED_ID = "embedded-id";
	String ENTITY = "entity";
	String ENTITY_CLASS = "entity-class";
	String ENTITY_LISTENER = "entity-listener";
	String ENTITY_LISTENERS = "entity-listeners";
	String ENTITY_MAPPINGS = "entity-mappings";
		String ENTITY_MAPPINGS__VERSION = "version";
		
	String ENTITY_RESULT = "entity-result";
	String ENUMERATED = "enumerated";
	String EXCLUDE_DEFAULT_LISTENERS = "exclude-default-listeners";
	String EXCLUDE_SUPERCLASS_LISTENERS = "exclude-superclass-listeners";
	String FETCH = "fetch";
	String FIELD_RESULT = "field-result";
	String GENERATED_VALUE = "generated-value";
	String GENERATOR = "generator";
	String HINT = "hint";
	String ID = "id";
	String ID_CLASS = "id-class";
	String INHERITANCE = "inheritance";
	String INITIAL_VALUE = "initial-value";
	String INSERTABLE = "insertable";
	String INVERSE_JOIN_COLUMN = "inverse-join-column";
	String JOIN_COLUMN = "join-column";
	String JOIN_TABLE = "join-table";
	String LENGTH = "length";
	String LOB = "lob";
	String MANY_TO_MANY = "many-to-many";
	String MANY_TO_ONE = "many-to-one";
	String MAP_KEY = "map-key";
	String MAPPED_BY = "mapped-by";
	String MAPPED_SUPERCLASS = "mapped-superclass";
	String METADATA_COMPLETE = "metadata-complete";
	String METHOD_NAME = "method-name";
	String NAME = "name";
	String NAMED_NATIVE_QUERY = "named-native-query";
	String NAMED_QUERY = "named-query";
	String NULLABLE = "nullable";
	String ONE_TO_MANY = "one-to-many";
	String ONE_TO_ONE = "one-to-one";
	String OPTIONAL = "optional";
	String ORDER_BY = "order-by";
	String PACKAGE = "package";
	String PERSISTENCE_UNIT_DEFAULTS = "persistence-unit-defaults";
	String PERSISTENCE_UNIT_METADATA = "persistence-unit-metadata";
	String PK_COLUMN_NAME = "pk-column-name";
	String PK_COLUMN_VALUE = "pk-column-value";
	String POST_LOAD = "post-load";
	String POST_PERSIST = "post-persist";
	String POST_REMOVE = "post-remove";
	String POST_UPDATE = "post-update";
	String PRE_PERSIST = "pre-persist";
	String PRE_REMOVE = "pre-remove";
	String PRE_UPDATE = "pre-update";
	String PRECISION= "precision";
	String PRIMARY_KEY_JOIN_COLUMN = "primary-key-join-column";
	String QUERY = "query";
	String REFERENCED_COLUMN_NAME = "referenced-column-name";
	String RESULT_CLASS = "result-class";
	String RESULT_SET_MAPPING = "result-set-mapping";
	String SCALE= "scale";
	String SCHEMA = "schema";
	String SECONDARY_TABLE = "secondary-table";
	String SEQUENCE_GENERATOR = "sequence-generator";
	String SEQUENCE_NAME = "sequence-name";
	String SQL_RESULT_SET_MAPPING = "sql-result-set-mapping";
	String STRATEGY = "strategy";
	String TABLE = "table";
	String TABLE_GENERATOR = "table-generator";
	String TARGET_ENTITY = "target-entity";
	String TEMPORAL = "temporal";
	String TRANSIENT = "transient";
	String UNIQUE = "unique";
	String UNIQUE_CONSTRAINT = "unique-constraint";
	String UPDATABLE = "updatable";
	String VALUE = "value";
	String VALUE_COLUMN_NAME = "value-column-name";
	String VERSION = "version";
	String XML_MAPPING_METADATA_COMPLETE = "xml-mapping-metadata-complete";

	
	// ********** EclipseLink 1.0 strings ***********
	
	String ACCESS_METHODS = "access-methods"; //$NON-NLS-1$
	String ACCESS_METHODS__GET_METHOD = "get-method"; //$NON-NLS-1$
	String ACCESS_METHODS__SET_METHOD = "set-method"; //$NON-NLS-1$
	
	String BASIC_COLLECTION = "basic-collection";  //$NON-NLS-1$
	
	String BASIC_MAP = "basic-map";  //$NON-NLS-1$
	
	String CACHE = "cache";  //$NON-NLS-1$
	String CACHE__EXPIRY = "expiry";  //$NON-NLS-1$
	String CACHE__SIZE = "size";  //$NON-NLS-1$
	String CACHE__SHARED = "shared";  //$NON-NLS-1$
	String CACHE__TYPE = "type";  //$NON-NLS-1$
	String CACHE__ALWAYS_REFRESH = "always-refresh";  //$NON-NLS-1$
	String CACHE__REFRESH_ONLY_IF_NEWER = "refresh-only-if-newer";  //$NON-NLS-1$
	String CACHE__DISABLE_HITS = "disable-hits";  //$NON-NLS-1$
	String CACHE__COORDINATION_TYPE = "coordination-type";  //$NON-NLS-1$
	
	String CHANGE_TRACKING = "change-tracking";  //$NON-NLS-1$
		
	String CONVERSION_VALUE = "conversion-value"; //$NON-NLS-1$
	String CONVERSION_VALUE__DATA_VALUE = "data-value"; //$NON-NLS-1$
	String CONVERSION_VALUE__OBJECT_VALUE = "object-value"; //$NON-NLS-1$

	String CONVERT = "convert"; //$NON-NLS-1$
	
	String CONVERTER = "converter"; //$NON-NLS-1$
	String CONVERTER__CLASS = "class"; //$NON-NLS-1$
	String CONVERTER__NAME = "name"; //$NON-NLS-1$
	
	String COPY_POLICY = "copy-policy"; //$NON-NLS-1$
		String COPY_POLICY__CLASS = "class"; //$NON-NLS-1$
	
	String CLONE_COPY_POLICY = "clone-copy-policy"; //$NON-NLS-1$
		String CLONE_COPY_POLICY__METHOD = "method"; //$NON-NLS-1$
		String CLONE_COPY_POLICY__WORKING_COPY_METHOD = "working-copy-method"; //$NON-NLS-1$
	
	String CUSTOMIZER = "customizer";  //$NON-NLS-1$

	String EXCLUDE_DEFAULT_MAPPINGS = "exclude-default-mappings";  //$NON-NLS-1$

	String EXISTENCE_CHECKING = "existence-checking";  //$NON-NLS-1$
	
	String EXPIRY_TIME_OF_DAY = "expiry-time-of-day";  //$NON-NLS-1$
	String EXPIRY_TIME_OF_DAY__HOUR = "hour";  //$NON-NLS-1$
	String EXPIRY_TIME_OF_DAY__MINUTE = "minute";  //$NON-NLS-1$
	String EXPIRY_TIME_OF_DAY__SECOND = "second";  //$NON-NLS-1$
	String EXPIRY_TIME_OF_DAY__MILLISECOND = "millisecond";  //$NON-NLS-1$
	
	String INSTANTIATION_COPY_POLICY = "instantiation-copy-policy"; //$NON-NLS-1$

	String JOIN_FETCH = "join-fetch";  //$NON-NLS-1$
	
	String MUTABLE = "mutable";  //$NON-NLS-1$
	
	String NAMED_STORED_PROCEDURE_QUERY = "named-stored-procedure-query"; //$NON-NLS-1$
		String NAMED_STORED_PROCEDURE_QUERY__NAME = "name"; //$NON-NLS-1$
		String NAMED_STORED_PROCEDURE_QUERY__HINT = "hint"; //$NON-NLS-1$
		String NAMED_STORED_PROCEDURE_QUERY__RESULT_CLASS = "result-class"; //$NON-NLS-1$
		String NAMED_STORED_PROCEDURE_QUERY__RESULT_SET_MAPPING = "result-set-mapping"; //$NON-NLS-1$
		String NAMED_STORED_PROCEDURE_QUERY__PROCEDURE_NAME = "procedure-name"; //$NON-NLS-1$
		String NAMED_STORED_PROCEDURE_QUERY__RETURNS_RESULT_SET = "returns-result-set"; //$NON-NLS-1$
	String NAMED_STORED_PROCEDURE_QUERY__PARAMETER = "parameter"; //$NON-NLS-1$
		String PARAMETER__DIRECTION = "direction"; //$NON-NLS-1$
		String PARAMETER__NAME = "name"; //$NON-NLS-1$
		String PARAMETER__QUERY_PARAMETER = "query-parameter"; //$NON-NLS-1$
		String PARAMETER__TYPE = "type"; //$NON-NLS-1$
		String PARAMETER__JDBC_TYPE = "jdbc-type"; //$NON-NLS-1$
		String PARAMETER__JDBC_TYPE_NAME = "jdbc-type-name"; //$NON-NLS-1$
		

	String OBJECT_TYPE_CONVERTER = "object-type-converter"; //$NON-NLS-1$
	String OBJECT_TYPE_CONVERTER__NAME = "name"; //$NON-NLS-1$
	String OBJECT_TYPE_CONVERTER__DATA_TYPE = "data-type"; //$NON-NLS-1$
	String OBJECT_TYPE_CONVERTER__OBJECT_TYPE = "object-type"; //$NON-NLS-1$
	String OBJECT_TYPE_CONVERTER__DEFAULT_OBJECT_VALUE = "default-object-value"; //$NON-NLS-1$

	String OPTIMISTIC_LOCKING = "optimistic-locking"; //$NON-NLS-1$
		String OPTIMISTIC_LOCKING__SELECTED_COLUMN = "selected-column"; //$NON-NLS-1$
		String OPTIMISTIC_LOCKING__TYPE = "type"; //$NON-NLS-1$
		String OPTIMISTIC_LOCKING__CASCADE = "cascade"; //$NON-NLS-1$

	String PRIVATE_OWNED = "private-owned";  //$NON-NLS-1$
	
	String PROPERTY = "property"; //$NON-NLS-1$
	String PROPERTY__NAME = "name"; //$NON-NLS-1$
	String PROPERTY__VALUE = "value"; //$NON-NLS-1$
	String PROPERTY__VALUE_TYPE = "value-type"; //$NON-NLS-1$
	
	String READ_ONLY = "read-only";  //$NON-NLS-1$
	
	
	String STRUCT_CONVERTER = "struct-converter"; //$NON-NLS-1$
	String STRUCT_CONVERTER__NAME = "name"; //$NON-NLS-1$
	String STRUCT_CONVERTER__CONVERTER = "converter"; //$NON-NLS-1$
	
	String TRANSFORMATION = "transformation";  //$NON-NLS-1$

	String TYPE = "type";  //$NON-NLS-1$
	
	String TYPE_CONVERTER = "type-converter"; //$NON-NLS-1$
	String TYPE_CONVERTER__NAME = "name"; //$NON-NLS-1$
	String TYPE_CONVERTER__DATA_TYPE = "data-type"; //$NON-NLS-1$
	String TYPE_CONVERTER__OBJECT_TYPE = "object-type"; //$NON-NLS-1$
		
	String VARIABLE_ONE_TO_ONE = "variable-one-to-one";  //$NON-NLS-1$

	
	// ********** EclipseLink 1.1 strings ***********
	
	String PRIMARY_KEY = "primary-key";  //$NON-NLS-1$

	
	// JPA 2.0 specific elements
	
	String CACHEABLE = "cacheable";
	String COLLECTION_TABLE = "collection-table";
	String DELIMITIED_IDENTIFIERS = "delimited-identifiers";
	String ELEMENT_COLLECTION = "element-collection";
	String MAP_KEY_ATTRIBUTE_OVERRIDE = "map-key-attribute-override";
	String MAP_KEY_CLASS = "map-key-class";
	String MAP_KEY_COLUMN = "map-key-column";
	String MAP_KEY_ENUMERATED = "map-key-enumerated";
	String MAP_KEY_JOIN_COLUMN = "map-key-join-column";
	String MAPPED_BY_ID = "mapped-by-id";
	String NAMED_QUERY__LOCK_MODE = "lock-mode";
	String ORDER_COLUMN = "order-column";
	String ORPHAN_REMOVAL = "orphan-removal";
	String TARGET_CLASS = "target-class";
	String MAP_KEY_TEMPORAL = "map-key-temporal";
	
	// ********** EclipseLink 2.0 strings ***********
	String MAP_KEY_ASSOCIATION_OVERRIDE = "map-key-association-override";
	String MAP_KEY_CONVERT = "map-key-convert";

}
