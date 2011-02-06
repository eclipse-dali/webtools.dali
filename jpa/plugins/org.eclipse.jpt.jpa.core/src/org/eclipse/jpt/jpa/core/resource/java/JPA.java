/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.resource.java;

/**
 * JPA Java-related stuff (annotations etc.)
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.0
 */
@SuppressWarnings("nls")
public interface JPA {

	// JPA package
	String PACKAGE = "javax.persistence";
	String PACKAGE_ = PACKAGE + '.';


	// ********** API **********

	// JPA annotations
	String ASSOCIATION_OVERRIDE = PACKAGE_ + "AssociationOverride";
		String ASSOCIATION_OVERRIDE__NAME = "name";
		String ASSOCIATION_OVERRIDE__JOIN_COLUMNS = "joinColumns";
	String ASSOCIATION_OVERRIDES = PACKAGE_ + "AssociationOverrides";
		String ASSOCIATION_OVERRIDES__VALUE = "value";
	String ATTRIBUTE_OVERRIDE = PACKAGE_ + "AttributeOverride";
		String ATTRIBUTE_OVERRIDE__NAME = "name";
		String ATTRIBUTE_OVERRIDE__COLUMN = "column";
	String ATTRIBUTE_OVERRIDES = PACKAGE_ + "AttributeOverrides";
		String ATTRIBUTE_OVERRIDES__VALUE = "value";
	String BASIC = PACKAGE_ + "Basic";
		String BASIC__FETCH = "fetch";
		String BASIC__OPTIONAL = "optional";
	String COLUMN = PACKAGE_ + "Column";
		String COLUMN__NAME = "name";
		String COLUMN__UNIQUE = "unique";
		String COLUMN__NULLABLE = "nullable";
		String COLUMN__INSERTABLE = "insertable";
		String COLUMN__UPDATABLE = "updatable";
		String COLUMN__COLUMN_DEFINITION = "columnDefinition";
		String COLUMN__TABLE = "table";
		String COLUMN__LENGTH = "length";
		String COLUMN__PRECISION = "precision";
		String COLUMN__SCALE = "scale";
	String COLUMN_RESULT = PACKAGE_ + "ColumnResult";
		String COLUMN_RESULT__NAME = "name";
	String DISCRIMINATOR_COLUMN = PACKAGE_ + "DiscriminatorColumn";
		String DISCRIMINATOR_COLUMN__NAME = "name";
		String DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE = "discriminatorType";
		String DISCRIMINATOR_COLUMN__COLUMN_DEFINITION = "columnDefinition";
		String DISCRIMINATOR_COLUMN__LENGTH = "length";
	String DISCRIMINATOR_VALUE = PACKAGE_ + "DiscriminatorValue";
		String DISCRIMINATOR_VALUE__VALUE = "value";
	String EMBEDDABLE = PACKAGE_ + "Embeddable";
	String EMBEDDED = PACKAGE_ + "Embedded";
	String EMBEDDED_ID = PACKAGE_ + "EmbeddedId";
	String ENTITY = PACKAGE_ + "Entity";
		String ENTITY__NAME = "name";
	String ENTITY_LISTENERS = PACKAGE_ + "EntityListeners";
		String ENTITY_LISTENERS__VALUE = "value";
	String ENTITY_RESULT = PACKAGE_ + "EntityResult";
		String ENTITY_RESULT__ENTITY_CLASS = "entityClass";
		String ENTITY_RESULT__FIELDS = "fields";
		String ENTITY_RESULT__DISCRIMINATOR_COLUMN = "discriminatorColumn";
	String ENUMERATED = PACKAGE_ + "Enumerated";
		String ENUMERATED__VALUE = "value";
	String EXCLUDE_DEFAULT_LISTENERS = PACKAGE_ + "ExcludeDefaultListeners";
	String EXCLUDE_SUPERCLASS_LISTENERS = PACKAGE_ + "ExcludeSuperclassListeners";
	String FIELD_RESULT = PACKAGE_ + "FieldResult";
		String FIELD_RESULT__NAME = "name";
		String FIELD_RESULT__COLUMN = "column";
	String FLUSH_MODE = PACKAGE_ + "FlushMode";
		String FLUSH_MODE__VALUE = "value";
	String GENERATED_VALUE = PACKAGE_ + "GeneratedValue";
		String GENERATED_VALUE__STRATEGY = "strategy";
		String GENERATED_VALUE__GENERATOR = "generator";
	String ID = PACKAGE_ + "Id";
	String ID_CLASS = PACKAGE_ + "IdClass";
		String ID_CLASS__VALUE = "value";
	String INHERITANCE = PACKAGE_ + "Inheritance";
		String INHERITANCE__STRATEGY = "strategy";
	String JOIN_COLUMN = PACKAGE_ + "JoinColumn";
		String JOIN_COLUMN__NAME = "name";
		String JOIN_COLUMN__REFERENCED_COLUMN_NAME = "referencedColumnName";
		String JOIN_COLUMN__UNIQUE = "unique";
		String JOIN_COLUMN__NULLABLE = "nullable";
		String JOIN_COLUMN__INSERTABLE = "insertable";
		String JOIN_COLUMN__UPDATABLE = "updatable";
		String JOIN_COLUMN__COLUMN_DEFINITION = "columnDefinition";
		String JOIN_COLUMN__TABLE = "table";
	String JOIN_COLUMNS = PACKAGE_ + "JoinColumns";
		String JOIN_COLUMNS__VALUE = "value";
	String JOIN_TABLE = PACKAGE_ + "JoinTable";
		String JOIN_TABLE__NAME = "name";
		String JOIN_TABLE__CATALOG = "catalog";
		String JOIN_TABLE__SCHEMA = "schema";
		String JOIN_TABLE__JOIN_COLUMNS = "joinColumns";
		String JOIN_TABLE__INVERSE_JOIN_COLUMNS = "inverseJoinColumns";
		String JOIN_TABLE__UNIQUE_CONSTRAINTS = "uniqueConstraints";
	String LOB = PACKAGE_ + "Lob";
	String MANY_TO_MANY = PACKAGE_ + "ManyToMany";
		String MANY_TO_MANY__TARGET_ENTITY = "targetEntity";
		String MANY_TO_MANY__CASCADE = "cascade";
		String MANY_TO_MANY__FETCH = "fetch";
		String MANY_TO_MANY__MAPPED_BY = "mappedBy";
	String MANY_TO_ONE = PACKAGE_ + "ManyToOne";
		String MANY_TO_ONE__TARGET_ENTITY = "targetEntity";
		String MANY_TO_ONE__CASCADE = "cascade";
		String MANY_TO_ONE__FETCH = "fetch";
		String MANY_TO_ONE__OPTIONAL = "optional";
	String MAP_KEY = PACKAGE_ + "MapKey";
		String MAP_KEY__NAME = "name";
	String MAPPED_SUPERCLASS = PACKAGE_ + "MappedSuperclass";
	String NAMED_NATIVE_QUERIES = PACKAGE_ + "NamedNativeQueries";
		String NAMED_NATIVE_QUERIES__VALUE = "value";
	String NAMED_NATIVE_QUERY = PACKAGE_ + "NamedNativeQuery";
		String NAMED_NATIVE_QUERY__NAME = "name";
		String NAMED_NATIVE_QUERY__QUERY = "query";
		String NAMED_NATIVE_QUERY__HINTS = "hints";
		String NAMED_NATIVE_QUERY__RESULT_CLASS = "resultClass";
		String NAMED_NATIVE_QUERY__RESULT_SET_MAPPING = "resultSetMapping";
	String NAMED_QUERIES = PACKAGE_ + "NamedQueries";
		String NAMED_QUERIES__VALUE = "value";
	String NAMED_QUERY = PACKAGE_ + "NamedQuery";
		String NAMED_QUERY__NAME = "name";
		String NAMED_QUERY__QUERY = "query";
		String NAMED_QUERY__HINTS = "hints";
	String ONE_TO_MANY = PACKAGE_ + "OneToMany";
		String ONE_TO_MANY__TARGET_ENTITY = "targetEntity";
		String ONE_TO_MANY__CASCADE = "cascade";
		String ONE_TO_MANY__FETCH = "fetch";
		String ONE_TO_MANY__MAPPED_BY = "mappedBy";
	String ONE_TO_ONE = PACKAGE_ + "OneToOne";
		String ONE_TO_ONE__TARGET_ENTITY = "targetEntity";
		String ONE_TO_ONE__CASCADE = "cascade";
		String ONE_TO_ONE__FETCH = "fetch";
		String ONE_TO_ONE__OPTIONAL = "optional";
		String ONE_TO_ONE__MAPPED_BY = "mappedBy";
	String ORDER_BY = PACKAGE_ + "OrderBy";
		String ORDER_BY__VALUE = "value";
	String PERSISTENCE_CONTEXT = PACKAGE_ + "PersistenceContext";
		String PERSISTENCE_CONTEXT__NAME = "name";
		String PERSISTENCE_CONTEXT__UNIT_NAME = "unitName";
		String PERSISTENCE_CONTEXT__TYPE = "type";
	String PERSISTENCE_CONTEXTS = PACKAGE_ + "PersistenceContexts";
		String PERSISTENCE_CONTEXTS__VALUE = "value";
	String PERSISTENCE_UNIT = PACKAGE_ + "XmlPersistenceUnit";
		String PERSISTENCE_UNIT__NAME = "name";
		String PERSISTENCE_UNIT__UNIT_NAME = "unitName";
	String PERSISTENCE_UNITS = PACKAGE_ + "PersistenceUnits";
		String PERSISTENCE_UNITS__VALUE = "value";
	String POST_LOAD = PACKAGE_ + "PostLoad";
	String POST_PERSIST = PACKAGE_ + "PostPersist";
	String POST_REMOVE = PACKAGE_ + "PostRemove";
	String POST_UPDATE = PACKAGE_ + "PostUpdate";
	String PRE_PERSIST = PACKAGE_ + "PrePersist";
	String PRE_REMOVE = PACKAGE_ + "PreRemove";
	String PRE_UPDATE = PACKAGE_ + "PreUpdate";
	String PRIMARY_KEY_JOIN_COLUMN = PACKAGE_ + "PrimaryKeyJoinColumn";
		String PRIMARY_KEY_JOIN_COLUMN__NAME = "name";
		String PRIMARY_KEY_JOIN_COLUMN__REFERENCED_COLUMN_NAME = "referencedColumnName";
		String PRIMARY_KEY_JOIN_COLUMN__COLUMN_DEFINITION = "columnDefinition";
	String PRIMARY_KEY_JOIN_COLUMNS = PACKAGE_ + "PrimaryKeyJoinColumns";
		String PRIMARY_KEY_JOIN_COLUMNS__VALUE = "value";
	String QUERY_HINT = PACKAGE_ + "QueryHint";
		String QUERY_HINT__NAME = "name";
		String QUERY_HINT__VALUE = "value";
	String SECONDARY_TABLE = PACKAGE_ + "SecondaryTable";
		String SECONDARY_TABLE__NAME = "name";
		String SECONDARY_TABLE__CATALOG = "catalog";
		String SECONDARY_TABLE__SCHEMA = "schema";
		String SECONDARY_TABLE__PK_JOIN_COLUMNS = "pkJoinColumns";
		String SECONDARY_TABLE__UNIQUE_CONSTRAINTS = "uniqueConstraints";
	String SECONDARY_TABLES = PACKAGE_ + "SecondaryTables";
		String SECONDARY_TABLES__VALUE = "value";
	String SEQUENCE_GENERATOR = PACKAGE_ + "SequenceGenerator";
		String SEQUENCE_GENERATOR__NAME = "name";
		String SEQUENCE_GENERATOR__SEQUENCE_NAME = "sequenceName";
		String SEQUENCE_GENERATOR__INITIAL_VALUE = "initialValue";
		String SEQUENCE_GENERATOR__ALLOCATION_SIZE = "allocationSize";
	String SQL_RESULT_SET_MAPPING = PACKAGE_ + "SqlResultSetMapping";
		String SQL_RESULT_SET_MAPPING__NAME = "name";
		String SQL_RESULT_SET_MAPPING__ENTITIES = "entities";
		String SQL_RESULT_SET_MAPPING__COLUMNS = "columns";
	String TABLE = PACKAGE_ + "Table";
		String TABLE__NAME = "name";
		String TABLE__CATALOG = "catalog";
		String TABLE__SCHEMA = "schema";
		String TABLE__UNIQUE_CONSTRAINTS = "uniqueConstraints";
	String TABLE_GENERATOR = PACKAGE_ + "TableGenerator";
		String TABLE_GENERATOR__NAME = "name";
		String TABLE_GENERATOR__TABLE = "table";
		String TABLE_GENERATOR__CATALOG = "catalog";
		String TABLE_GENERATOR__SCHEMA = "schema";
		String TABLE_GENERATOR__PK_COLUMN_NAME = "pkColumnName";
		String TABLE_GENERATOR__VALUE_COLUMN_NAME = "valueColumnName";
		String TABLE_GENERATOR__PK_COLUMN_VALUE = "pkColumnValue";
		String TABLE_GENERATOR__INITIAL_VALUE = "initialValue";
		String TABLE_GENERATOR__ALLOCATION_SIZE = "allocationSize";
		String TABLE_GENERATOR__UNIQUE_CONSTRAINTS = "uniqueConstraints";
	String TEMPORAL = PACKAGE_ + "Temporal";
		String TEMPORAL__VALUE = "value";
	String TRANSIENT = PACKAGE_ + "Transient";
	String UNIQUE_CONSTRAINT = PACKAGE_ + "UniqueConstraint";
		String UNIQUE_CONSTRAINT__COLUMN_NAMES = "columnNames";
	String VERSION = PACKAGE_ + "Version";

	// JPA enums
	String CASCADE_TYPE = PACKAGE_ + "CascadeType";
		String CASCADE_TYPE_ = CASCADE_TYPE + '.';
		String CASCADE_TYPE__ALL = CASCADE_TYPE_ + "ALL";
		String CASCADE_TYPE__MERGE = CASCADE_TYPE_ + "MERGE";
		String CASCADE_TYPE__PERSIST = CASCADE_TYPE_ + "PERSIST";
		String CASCADE_TYPE__REFRESH = CASCADE_TYPE_ + "REFRESH";
		String CASCADE_TYPE__REMOVE = CASCADE_TYPE_ + "REMOVE";

	String DISCRIMINATOR_TYPE = PACKAGE_ + "DiscriminatorType";
		String DISCRIMINATOR_TYPE_ = DISCRIMINATOR_TYPE + '.';
		String DISCRIMINATOR_TYPE__CHAR = DISCRIMINATOR_TYPE_ + "CHAR";
		String DISCRIMINATOR_TYPE__INTEGER = DISCRIMINATOR_TYPE_ + "INTEGER";
		String DISCRIMINATOR_TYPE__STRING = DISCRIMINATOR_TYPE_ + "STRING";

	String ENUM_TYPE = PACKAGE_ + "EnumType";
		String ENUM_TYPE_ = ENUM_TYPE + '.';
		String ENUM_TYPE__ORDINAL = ENUM_TYPE_ + "ORDINAL";
		String ENUM_TYPE__STRING = ENUM_TYPE_ + "STRING";

	String FETCH_TYPE = PACKAGE_ + "FetchType";
		String FETCH_TYPE_ = FETCH_TYPE + '.';
		String FETCH_TYPE__EAGER = FETCH_TYPE_ + "EAGER";
		String FETCH_TYPE__LAZY = FETCH_TYPE_ + "LAZY";

	String FLUSH_MODE_TYPE = PACKAGE_ + "FlushModeType";
		String FLUSH_MODE_TYPE_ = FLUSH_MODE_TYPE + '.';
		String FLUSH_MODE_TYPE__AUTO = FLUSH_MODE_TYPE_ + "AUTO";
		String FLUSH_MODE_TYPE__COMMIT = FLUSH_MODE_TYPE_ + "COMMIT";

	String GENERATION_TYPE = PACKAGE_ + "GenerationType";
		String GENERATION_TYPE_ = GENERATION_TYPE + '.';
		String GENERATION_TYPE__AUTO = GENERATION_TYPE_ + "AUTO";
		String GENERATION_TYPE__IDENTITY = GENERATION_TYPE_ + "IDENTITY";
		String GENERATION_TYPE__SEQUENCE = GENERATION_TYPE_ + "SEQUENCE";
		String GENERATION_TYPE__TABLE = GENERATION_TYPE_ + "TABLE";

	String INHERITANCE_TYPE = PACKAGE_ + "InheritanceType";
		String INHERITANCE_TYPE_ = INHERITANCE_TYPE + '.';
		String INHERITANCE_TYPE__JOINED = INHERITANCE_TYPE_ + "JOINED";
		String INHERITANCE_TYPE__SINGLE_TABLE = INHERITANCE_TYPE_ + "SINGLE_TABLE";
		String INHERITANCE_TYPE__TABLE_PER_CLASS = INHERITANCE_TYPE_ + "TABLE_PER_CLASS";

	String PERSISTENCE_CONTEXT_TYPE = PACKAGE_ + "PersistenceContextType";
		String PERSISTENCE_CONTEXT_TYPE_ = PERSISTENCE_CONTEXT_TYPE + '.';
		String PERSISTENCE_CONTEXT_TYPE__EXTENDED = PERSISTENCE_CONTEXT_TYPE_ + "EXTENDED";
		String PERSISTENCE_CONTEXT_TYPE__TRANSACTION = PERSISTENCE_CONTEXT_TYPE_ + "TRANSACTION";

	String TEMPORAL_TYPE = PACKAGE_ + "TemporalType";
		String TEMPORAL_TYPE_ = TEMPORAL_TYPE + '.';
		String TEMPORAL_TYPE__DATE = TEMPORAL_TYPE_ + "DATE";
		String TEMPORAL_TYPE__TIME = TEMPORAL_TYPE_ + "TIME";
		String TEMPORAL_TYPE__TIMESTAMP = TEMPORAL_TYPE_ + "TIMESTAMP";

	// JPA interfaces
	String ENTITY_MANAGER = PACKAGE_ + "EntityManager";
	String ENTITY_MANAGER_FACTORY = PACKAGE_ + "EntityManagerFactory";
	String ENTITY_TRANSACTION = PACKAGE_ + "EntityTransaction";
	String INSTRUMENTABLE_CLASS_LOADER = PACKAGE_ + "InstrumentableClassLoader";
	String QUERY = PACKAGE_ + "Query";

	// JPA classes
	String PERSISTENCE = PACKAGE_ + "XmlPersistence";

	// JPA exceptions
	String NON_UNIQUE_RESULT_EXCEPTION = PACKAGE_ + "NonUniqueResultException";
	String OBJECT_NOT_FOUND_EXCEPTION = PACKAGE_ + "ObjectNotFoundException";
	String PERSISTENCE_EXCEPTION = PACKAGE_ + "PersistenceException";


	// ********** SPI **********

	// JPA SPI package
	String SPI_PACKAGE = PACKAGE_ + "spi";
	String SPI_PACKAGE_ = SPI_PACKAGE + '.';

	// JPA SPI interfaces
	String ENTITY_MANAGER_FACTORY_PROVIDER = SPI_PACKAGE_ + "EntityManagerFactoryProvider";
	String PERSISTENCE_INFO = SPI_PACKAGE_ + "PersistenceInfo";
	String PERSISTENCE_PROVIDER = SPI_PACKAGE_ + "PersistenceProvider";

}
