/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.resource.orm.translators;

import org.eclipse.jpt.core.internal.JpaConstants;
import org.eclipse.jpt.core.resource.orm.OrmPackage;

public interface OrmXmlMapper
	extends JpaConstants
{
	OrmPackage ORM_PKG = OrmPackage.eINSTANCE;
	
	String ACCESS = "access";  //$NON-NLS-1$
	String ALLOCATION_SIZE = "allocation-size";  //$NON-NLS-1$
	String ASSOCIATION_OVERRIDE = "association-override";  //$NON-NLS-1$
	String ATTRIBUTE_OVERRIDE = "attribute-override";  //$NON-NLS-1$
	String ATTRIBUTES = "attributes";  //$NON-NLS-1$
	String BASIC = "basic";  //$NON-NLS-1$
	String CASCADE = "cascade";  //$NON-NLS-1$
	String CASCADE_ALL = "cascade-all";  //$NON-NLS-1$
	String CASCADE_MERGE = "cascade-merge";  //$NON-NLS-1$
	String CASCADE_PERSIST = "cascade-persist";  //$NON-NLS-1$
	String CASCADE_REFRESH = "cascade-refresh";  //$NON-NLS-1$	
	String CASCADE_REMOVE = "cascade-remove";  //$NON-NLS-1$	
	String CATALOG = "catalog";  //$NON-NLS-1$
	String CLASS = "class";  //$NON-NLS-1$
	String COLUMN = "column";  //$NON-NLS-1$
	String COLUMN_DEFINITION= "column-definition";  //$NON-NLS-1$
	String COLUMN_NAME = "column-name";  //$NON-NLS-1$
	String COLUMN_RESULT = "column-result";  //$NON-NLS-1$
	String DESCRIPTION = "description";  //$NON-NLS-1$
	String DISCRIMINATOR_COLUMN = "discriminator-column";  //$NON-NLS-1$
	String DISCRIMINATOR_TYPE = "discriminator-type";  //$NON-NLS-1$
	String DISCRIMINATOR_VALUE = "discriminator-value";  //$NON-NLS-1$
	String EMBEDDABLE = "embeddable";  //$NON-NLS-1$
	String EMBEDDED = "embedded";  //$NON-NLS-1$
	String EMBEDDED_ID = "embedded-id";  //$NON-NLS-1$
	String ENTITY = "entity";  //$NON-NLS-1$
	String ENTITY_CLASS = "entity-class";  //$NON-NLS-1$
	String ENTITY_LISTENER = "entity-listener";  //$NON-NLS-1$
	String ENTITY_LISTENERS = "entity-listeners";  //$NON-NLS-1$
	String ENTITY_MAPPINGS = "entity-mappings";  //$NON-NLS-1$
	String ENTITY_RESULT = "entity-result";  //$NON-NLS-1$
	String ENUMERATED = "enumerated";  //$NON-NLS-1$
	String EXCLUDE_DEFAULT_LISTENERS = "exclude-default-listeners";  //$NON-NLS-1$
	String EXCLUDE_SUPERCLASS_LISTENERS = "exclude-superclass-listeners";  //$NON-NLS-1$
	String FETCH = "fetch";  //$NON-NLS-1$
	String FIELD_RESULT = "field-result";  //$NON-NLS-1$
	String GENERATED_VALUE = "generated-value";  //$NON-NLS-1$
	String GENERATOR = "generator";  //$NON-NLS-1$
	String HINT = "hint";  //$NON-NLS-1$
	String ID = "id";  //$NON-NLS-1$
	String ID_CLASS = "id-class";  //$NON-NLS-1$
	String INHERITANCE = "inheritance";  //$NON-NLS-1$
	String INITIAL_VALUE = "initial-value";  //$NON-NLS-1$
	String INSERTABLE = "insertable";  //$NON-NLS-1$
	String INVERSE_JOIN_COLUMN = "inverse-join-column";  //$NON-NLS-1$
	String JOIN_COLUMN = "join-column";  //$NON-NLS-1$
	String JOIN_TABLE = "join-table";  //$NON-NLS-1$
	String LENGTH = "length";  //$NON-NLS-1$
	String LOB = "lob";  //$NON-NLS-1$
	String MANY_TO_MANY = "many-to-many";  //$NON-NLS-1$
	String MANY_TO_ONE = "many-to-one";  //$NON-NLS-1$
	String MAP_KEY = "map-key";  //$NON-NLS-1$
	String MAPPED_BY = "mapped-by";  //$NON-NLS-1$
	String MAPPED_SUPERCLASS = "mapped-superclass";  //$NON-NLS-1$
	String METADATA_COMPLETE = "metadata-complete";  //$NON-NLS-1$
	String METHOD_NAME = "method-name";  //$NON-NLS-1$
	String NAME = "name";  //$NON-NLS-1$
	String NAMED_NATIVE_QUERY = "named-native-query";  //$NON-NLS-1$
	String NAMED_QUERY = "named-query";  //$NON-NLS-1$
	String NULLABLE = "nullable";  //$NON-NLS-1$
	String ONE_TO_MANY = "one-to-many";  //$NON-NLS-1$
	String ONE_TO_ONE = "one-to-one";  //$NON-NLS-1$
	String OPTIONAL = "optional";  //$NON-NLS-1$
	String ORDER_BY = "order-by";  //$NON-NLS-1$
	String PACKAGE = "package";  //$NON-NLS-1$
	String PERSISTENCE_UNIT_DEFAULTS = "persistence-unit-defaults";  //$NON-NLS-1$
	String PERSISTENCE_UNIT_METADATA = "persistence-unit-metadata";  //$NON-NLS-1$
	String PK_COLUMN_NAME = "pk-column-name";  //$NON-NLS-1$
	String PK_COLUMN_VALUE = "pk-column-value";  //$NON-NLS-1$
	String POST_LOAD = "post-load";  //$NON-NLS-1$
	String POST_PERSIST = "post-persist";  //$NON-NLS-1$
	String POST_REMOVE = "post-remove";  //$NON-NLS-1$
	String POST_UPDATE = "post-update";  //$NON-NLS-1$
	String PRE_PERSIST = "pre-persist";  //$NON-NLS-1$
	String PRE_REMOVE = "pre-remove";  //$NON-NLS-1$
	String PRE_UPDATE = "pre-update";  //$NON-NLS-1$
	String PRECISION= "precision";  //$NON-NLS-1$
	String PRIMARY_KEY_JOIN_COLUMN = "primary-key-join-column";  //$NON-NLS-1$
	String QUERY = "query";  //$NON-NLS-1$
	String REFERENCED_COLUMN_NAME = "referenced-column-name";  //$NON-NLS-1$
	String RESULT_CLASS = "result-class";  //$NON-NLS-1$
	String RESULT_SET_MAPPING = "result-set-mapping";  //$NON-NLS-1$
	String SCALE= "scale";  //$NON-NLS-1$
	String SCHEMA = "schema";  //$NON-NLS-1$
	String SECONDARY_TABLE = "secondary-table";  //$NON-NLS-1$
	String SEQUENCE_GENERATOR = "sequence-generator";  //$NON-NLS-1$
	String SEQUENCE_NAME = "sequence-name";  //$NON-NLS-1$
	String SQL_RESULT_SET_MAPPING = "sql-result-set-mapping";  //$NON-NLS-1$
	String STRATEGY = "strategy";  //$NON-NLS-1$
	String TABLE = "table";  //$NON-NLS-1$
	String TABLE_GENERATOR = "table-generator";  //$NON-NLS-1$
	String TARGET_ENTITY = "target-entity";  //$NON-NLS-1$
	String TEMPORAL = "temporal";  //$NON-NLS-1$
	String TRANSIENT = "transient";  //$NON-NLS-1$
	String UNIQUE = "unique";  //$NON-NLS-1$
	String UNIQUE_CONSTRAINT = "unique-constraint";  //$NON-NLS-1$
	String UPDATABLE = "updatable";  //$NON-NLS-1$
	String VALUE = "value";  //$NON-NLS-1$
	String VALUE_COLUMN_NAME = "value-column-name";  //$NON-NLS-1$
	String VERSION = "version";  //$NON-NLS-1$
	String XML_MAPPING_METADATA_COMPLETE = "xml-mapping-metadata-complete";  //$NON-NLS-1$
}
