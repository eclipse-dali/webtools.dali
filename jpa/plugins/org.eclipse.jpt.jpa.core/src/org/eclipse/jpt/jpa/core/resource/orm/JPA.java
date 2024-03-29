/*******************************************************************************
 * Copyright (c) 2006, 2011 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License 2.0 which accompanies this distribution, and is
 * available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.resource.orm;

@SuppressWarnings("nls")
public interface JPA
{
	String SCHEMA_NAMESPACE = "http://java.sun.com/xml/ns/persistence/orm";
	String SCHEMA_LOCATION = "http://java.sun.com/xml/ns/persistence/orm_1_0.xsd";
	String SCHEMA_VERSION = "1.0";
	
	String ACCESS = "access";
	String ALLOCATION_SIZE = "allocation-size";
	String ASSOCIATION_OVERRIDE = "association-override";
	String ATTRIBUTE_OVERRIDE = "attribute-override";
	String ATTRIBUTES = "attributes";
	String BASIC = "basic";
	String CASCADE = "cascade";
	String CASCADE_ALL = "cascade-all";
	String CASCADE_DETACH = "cascade-detach";	
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
}
