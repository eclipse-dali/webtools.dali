/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm.resource;

public interface OrmXmlMapper
{
	String XML_NS = "xmlns";  //$NON-NLS-1$
	String XML_NS_XSI = "xmlns:xsi";  //$NON-NLS-1$
	String XSI_SCHEMA_LOCATION = "xsi:schemaLocation";  //$NON-NLS-1$
	String XSI_NS_URL = "http://www.w3.org/2001/XMLSchema-instance";  //$NON-NLS-1$
	String PERSISTENCE_NS_URL = "http://java.sun.com/xml/ns/persistence/orm";  //$NON-NLS-1$
	String ORM_SCHEMA_LOC_1_0 = "http://java.sun.com/xml/ns/persistence/orm_1_0.xsd";  //$NON-NLS-1$
	
	String FOO="foo";  //$NON-NLS-1$
	String BAR="bar";  //$NON-NLS-1$
	
	String ACCESS = "access";  //$NON-NLS-1$
	String ATTRIBUTES = "attributes";  //$NON-NLS-1$
	String ATTRIBUTE_OVERRIDE_NAME = "name";  //$NON-NLS-1$
	String ATTRIBUTE_OVERRIDE_COLUMN = "column";  //$NON-NLS-1$
	String ASSOCIATION_OVERRIDE_NAME = "name";  //$NON-NLS-1$
	String ASSOCIATION_OVERRIDE_JOIN_COLUMN = "join-column";  //$NON-NLS-1$
	String BASIC = "basic";  //$NON-NLS-1$
	String BASIC__ENUMERATED = "enumerated";  //$NON-NLS-1$
	
	String CASCADE = "cascade";  //$NON-NLS-1$
	String CASCADE_PERSIST = "cascade-persist";  //$NON-NLS-1$
	String CATALOG = "catalog";  //$NON-NLS-1$
	String CLASS = "class";  //$NON-NLS-1$
	String COLUMN = "column";  //$NON-NLS-1$
	String COLUMN__LENGTH = "length";  //$NON-NLS-1$
	String COLUMN__PRECISION= "precision";  //$NON-NLS-1$
	String COLUMN__SCALE= "scale";  //$NON-NLS-1$
	String COLUMN__COLUMN_DEFINITION= "column-definition";  //$NON-NLS-1$
	String COLUMN__NULLABLE = "nullable";  //$NON-NLS-1$
	String COLUMN__UNIQUE = "unique";  //$NON-NLS-1$
	String COLUMN__UPDATABLE = "updatable";  //$NON-NLS-1$
	String COLUMN__INSERTABLE = "insertable";  //$NON-NLS-1$
	String COLUMN__TABLE = "table";  //$NON-NLS-1$
	String COLUMN__NAME = "name";  //$NON-NLS-1$

	String DISCRIMINATOR_COLUMN__NAME = "name";  //$NON-NLS-1$
	String DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE = "discriminator-type";  //$NON-NLS-1$
	String DISCRIMINATOR_COLUMN__COLUMN_DEFINITION = "column-definition";  //$NON-NLS-1$
	String DISCRIMINATOR_COLUMN__LENGTH = "length";  //$NON-NLS-1$
	String EMBEDDABLE = "embeddable";  //$NON-NLS-1$
	String EMBEDDABLE__DESCRIPTION = "description";  //$NON-NLS-1$
	
	String EMBEDDED = "embedded";  //$NON-NLS-1$
	String EMBEDDED__ATTRIBUTE_OVERRIDE = "attribute-override";  //$NON-NLS-1$
	String EMBEDDED_ID = "embedded-id";  //$NON-NLS-1$
	String EMBEDDED_ID__ATTRIBUTE_OVERRIDE = "attribute-override";  //$NON-NLS-1$
	
	String ENTITY = "entity";  //$NON-NLS-1$
	String ENTITY__DESCRIPTION = "description";  //$NON-NLS-1$
	String ENTITY__TABLE = "table";  //$NON-NLS-1$
	String ENTITY__PRIMARY_KEY_JOIN_COLUMN = "primary-key-join-column";  //$NON-NLS-1$
	String ENTITY__INHERITANCE = "inheritance";  //$NON-NLS-1$
	String ENTITY__DISCRIMINATOR_VALUE = "discriminator-value";  //$NON-NLS-1$
	String ENTITY__DISCRIMINATOR_COLUMN = "discriminator-column";  //$NON-NLS-1$
	String ENTITY__SEQUENCE_GENERATOR = "sequence-generator";  //$NON-NLS-1$
	String ENTITY__TABLE_GENERATOR = "table-generator";  //$NON-NLS-1$
	String ENTITY__NAMED_QUERY = "named-query";  //$NON-NLS-1$
	String ENTITY__NAMED_NATIVE_QUERY = "named-native-query";  //$NON-NLS-1$
	String ENTITY__SQL_RESULT_SET_MAPPING = "sql-result-set-mapping";  //$NON-NLS-1$
	String ENTITY__EXCLUDE_DEFAULT_LISTENERS = "exclude-default-listeners";  //$NON-NLS-1$
	String ENTITY__EXCLUDE_SUPERCLASS_LISTENERS = "exclude-superclass-listeners";  //$NON-NLS-1$
	String ENTITY__ENTITY_LISTENERS = "entity-listeners";  //$NON-NLS-1$
	String ENTITY__PRE_PERSIST = "pre-persist";  //$NON-NLS-1$
	String ENTITY__POST_PERSIST = "post-persist";  //$NON-NLS-1$
	String ENTITY__PRE_REMOVE = "pre-remove";  //$NON-NLS-1$
	String ENTITY__POST_REMOVE = "post-remove";  //$NON-NLS-1$
	String ENTITY__PRE_UPDATE = "pre-update";  //$NON-NLS-1$
	String ENTITY__POST_UPDATE = "post-update";  //$NON-NLS-1$
	String ENTITY__POST_LOAD = "post-load";  //$NON-NLS-1$
	String ENTITY__ATTRIBUTE_OVERRIDE = "attribute-override";  //$NON-NLS-1$
	String ENTITY__ASSOCIATION_OVERRIDE = "association-override";  //$NON-NLS-1$

	String ENTITY_MAPPINGS = "entity-mappings";  //$NON-NLS-1$
	String ENTITY_MAPPINGS__DESCRIPTION = "description";  //$NON-NLS-1$
	String ENTITY_MAPPINGS__PERSISTENCE_UNIT_METADATA = "persistence-unit-metadata";  //$NON-NLS-1$
	String ENTITY_MAPPINGS__PACKAGE = "package";  //$NON-NLS-1$
	String ENTITY_MAPPINGS__SCHEMA = "schema";  //$NON-NLS-1$
	String ENTITY_MAPPINGS__CATALOG = "catalog";  //$NON-NLS-1$
	String ENTITY_MAPPINGS__ACCESS = "access";  //$NON-NLS-1$
	String ENTITY_MAPPINGS__SEQUENCE_GENERATOR = "sequence-generator";  //$NON-NLS-1$
	String ENTITY_MAPPINGS__TABLE_GENERATOR = "table-generator";  //$NON-NLS-1$
	String ENTITY_MAPPINGS__NAMED_QUERY = "named-query";  //$NON-NLS-1$
	String ENTITY_MAPPINGS__NAMED_NATIVE_QUERY = "named-native-query";  //$NON-NLS-1$
	String ENTITY_MAPPINGS__SQL_RESULT_SET_MAPPING = "sql-result-set-mapping";  //$NON-NLS-1$

	String FETCH = "fetch";  //$NON-NLS-1$
	String JOIN_COLUMN = "join-column";  //$NON-NLS-1$
	String JOIN_TABLE = "join-table";  //$NON-NLS-1$
	String ID = "id";  //$NON-NLS-1$
	String ID__TEMPORAL = "temporal";  //$NON-NLS-1$
	String ID_CLASS = "id-class";  //$NON-NLS-1$
	String ID_CLASS__CLASS = "class";  //$NON-NLS-1$
	
		String GENERATED_VALUE = "generated-value";  //$NON-NLS-1$
	String GENERATED_VALUE__STRATEGY = "strategy";  //$NON-NLS-1$
	String GENERATED_VALUE__GENERATOR = "generator";  //$NON-NLS-1$
	String GENERATOR__NAME = "name";  //$NON-NLS-1$
	String GENERATOR__INITIAL_VALUE = "initial-value";  //$NON-NLS-1$
	String GENERATOR__ALLOCATION_SIZE = "allocation-size";  //$NON-NLS-1$
	
	String INVERSE_JOIN_COLUMN = "inverse-join-column";  //$NON-NLS-1$
	String LOB = "lob";  //$NON-NLS-1$
	String MANY_TO_MANY = "many-to-many";  //$NON-NLS-1$
	String MANY_TO_ONE = "many-to-one";  //$NON-NLS-1$
	String MAP_KEY = "map-key";  //$NON-NLS-1$
	String MAP_KEY__NAME = "name";  //$NON-NLS-1$
	
	String MAPPED_BY = "mapped-by";  //$NON-NLS-1$
	String MAPPED_SUPERCLASS = "mapped-superclass";  //$NON-NLS-1$
	String METADATA_COMPLETE = "metadata-complete";  //$NON-NLS-1$
	String NAME = "name";  //$NON-NLS-1$
	
	String NAMED_NATIVE_QUERY = "named-native-query";  //$NON-NLS-1$
	String NAMED_NATIVE_QUERY__NAME = "name";  //$NON-NLS-1$
	String NAMED_NATIVE_QUERY__QUERY = "query";  //$NON-NLS-1$
	String NAMED_NATIVE_QUERY__RESULT_CLASS = "result-class";  //$NON-NLS-1$
	String NAMED_NATIVE_QUERY__RESULT_SET_MAPPING = "result-set-mapping";  //$NON-NLS-1$
	String NAMED_NATIVE_QUERY__HINT = "hint";  //$NON-NLS-1$
		
	String NAMED_QUERY = "named-query";  //$NON-NLS-1$
	String NAMED_QUERY__NAME = "name";  //$NON-NLS-1$
	String NAMED_QUERY__QUERY = "query";  //$NON-NLS-1$
	String NAMED_QUERY__HINT = "hint";  //$NON-NLS-1$

	String QUERY_HINT__NAME = "name";  //$NON-NLS-1$
	String QUERY_HINT__VALUE = "value";  //$NON-NLS-1$

	String ONE_TO_MANY = "one-to-many";  //$NON-NLS-1$
	String ONE_TO_ONE = "one-to-one";  //$NON-NLS-1$
	String ONE_TO_ONE__PRIMARY_KEY_JOIN_COLUMNS = "primary-key-join-columns";  //$NON-NLS-1$
	String ONE_TO_ONE__JOIN_TABLE = "join-table";  //$NON-NLS-1$
	
	String OPTIONAL = "optional";  //$NON-NLS-1$
	String ORDER_BY = "order-by";  //$NON-NLS-1$
	String PACKAGE="package";  //$NON-NLS-1$
	String PERSISTENCE_UNIT_DEFAULTS="persistence-unit-defaults";  //$NON-NLS-1$
	String PERSISTENCE_UNIT_DEFAULTS__ENTITY_LISTENERS="entity-listeners";  //$NON-NLS-1$
	
	String PERSISTENCE_UNIT_METADATA="persistence-unit-metadata";  //$NON-NLS-1$
	String REFERENCED_COLUMN_NAME="referenced-column-name";  //$NON-NLS-1$
	String SCHEMA="schema";  //$NON-NLS-1$
	String SECONDARY_TABLE = "secondary-table";  //$NON-NLS-1$
	String SECONDARY_TABLE__PRIMARY_KEY_JOIN_COLUMN = "primary-key-join-column";  //$NON-NLS-1$
	String SEQUENCE_GENERATOR = "sequence-generator";  //$NON-NLS-1$
	String SEQUENCE_GENERATOR__SEQUENCE_NAME = "sequence-name";  //$NON-NLS-1$
	String STRATEGY = "strategy";  //$NON-NLS-1$
	String TABLE_GENERATOR = "table-generator";  //$NON-NLS-1$
	String TABLE_GENERATOR__TABLE = "table";  //$NON-NLS-1$
	String TABLE_GENERATOR__CATALOG = "catalog";  //$NON-NLS-1$
	String TABLE_GENERATOR__SCHEMA = "schema";  //$NON-NLS-1$
	String TABLE_GENERATOR__PK_COLUMN_NAME = "pk-column-name";  //$NON-NLS-1$
	String TABLE_GENERATOR__VALUE_COLUMN_NAME = "value-column-name";  //$NON-NLS-1$
	String TABLE_GENERATOR__PK_COLUMN_VALUE = "pk-column-value";  //$NON-NLS-1$
	String TARGET_ENTITY = "target-entity";  //$NON-NLS-1$
	String TEMPORAL = "temporal";  //$NON-NLS-1$
	String TRANSIENT = "transient";  //$NON-NLS-1$
	String UNIQUE_CONSTRAINT = "unique-constraint";  //$NON-NLS-1$
	String UNIQUE_CONSTRAINT__COLUMN_NAME = "column-name";  //$NON-NLS-1$
	String VERSION = "version";  //$NON-NLS-1$
	String XML_MAPPING_METADATA_COMPLETE = "xml-mapping-metadata-complete";  //$NON-NLS-1$
}
