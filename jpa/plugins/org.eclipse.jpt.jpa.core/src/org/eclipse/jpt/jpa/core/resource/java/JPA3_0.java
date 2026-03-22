/*******************************************************************************
 * Copyright (c) 2026 Lakshminarayana Nekkanti. All rights reserved.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Lakshminarayana Nekkanti - initial API and implementation
 *
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.resource.java;

/**
 * Jakarta Persistence 3.0+ Java-related constants.
 * <p>
 * Every annotation name constant in this interface uses the
 * <code>jakarta.persistence</code> package prefix and shadows the
 * corresponding constant inherited from {@link JPA} (which uses
 * <code>javax.persistence</code>).
 * <p>
 * This interface is the single source of truth for annotation FQNs used in
 * JPA 3.x platform code (annotation definitions, annotation definition
 * providers, validators, etc.).  It covers all annotations introduced across
 * JPA 1.0, 2.0, 2.1, 2.2, 3.0, and 3.1.
 * <p>
 * <b>Why explicit re-declaration?</b>
 * The JPA 2.0 / 2.1 constant interfaces ({@code JPA2_0}, {@code JPA2_1})
 * compute their annotation FQN constants using {@code JPA.PACKAGE_}
 * (<code>javax.persistence.</code>), so those values are fixed at class-load
 * time.  Simply extending those interfaces would still yield javax-based FQNs.
 * Therefore every annotation FQN constant must be explicitly declared here
 * with the jakarta prefix.
 * <p>
 * Element-name string constants (e.g. {@code NAME = "name"}) do not contain
 * the package prefix and are inherited from {@link JPA} unchanged.
 * <p>
 * For context-aware package resolution at runtime see
 * {@link org.eclipse.jpt.jpa.core.internal.utility.JpaPackageHelper}.
 *
 * @since 3.0
 */
@SuppressWarnings("nls")
public interface JPA3_0 extends JPA {

	// ---- package -------------------------------------------------------

	/** Jakarta Persistence package prefix (<code>jakarta.persistence</code>). */
	String PACKAGE = JAKARTA_PACKAGE;
	String PACKAGE_ = PACKAGE + '.';


	// ====================================================================
	// JPA 1.0 annotations  (shadows JPA.*)
	// ====================================================================

	String ASSOCIATION_OVERRIDE = PACKAGE_ + "AssociationOverride";
	String ASSOCIATION_OVERRIDES = PACKAGE_ + "AssociationOverrides";
	String ATTRIBUTE_OVERRIDE = PACKAGE_ + "AttributeOverride";
	String ATTRIBUTE_OVERRIDES = PACKAGE_ + "AttributeOverrides";
	String BASIC = PACKAGE_ + "Basic";
	String COLUMN = PACKAGE_ + "Column";
	String COLUMN_RESULT = PACKAGE_ + "ColumnResult";
	String DISCRIMINATOR_COLUMN = PACKAGE_ + "DiscriminatorColumn";
	String DISCRIMINATOR_VALUE = PACKAGE_ + "DiscriminatorValue";
	String EMBEDDABLE = PACKAGE_ + "Embeddable";
	String EMBEDDED = PACKAGE_ + "Embedded";
	String EMBEDDED_ID = PACKAGE_ + "EmbeddedId";
	String ENTITY = PACKAGE_ + "Entity";
	String ENTITY_LISTENERS = PACKAGE_ + "EntityListeners";
	String ENTITY_RESULT = PACKAGE_ + "EntityResult";
	String ENUMERATED = PACKAGE_ + "Enumerated";
	String EXCLUDE_DEFAULT_LISTENERS = PACKAGE_ + "ExcludeDefaultListeners";
	String EXCLUDE_SUPERCLASS_LISTENERS = PACKAGE_ + "ExcludeSuperclassListeners";
	String FIELD_RESULT = PACKAGE_ + "FieldResult";
	String FLUSH_MODE = PACKAGE_ + "FlushMode";
	String GENERATED_VALUE = PACKAGE_ + "GeneratedValue";
	String ID = PACKAGE_ + "Id";
	String ID_CLASS = PACKAGE_ + "IdClass";
	String INHERITANCE = PACKAGE_ + "Inheritance";
	String JOIN_COLUMN = PACKAGE_ + "JoinColumn";
	String JOIN_COLUMNS = PACKAGE_ + "JoinColumns";
	String JOIN_TABLE = PACKAGE_ + "JoinTable";
	String LOB = PACKAGE_ + "Lob";
	String MANY_TO_MANY = PACKAGE_ + "ManyToMany";
	String MANY_TO_ONE = PACKAGE_ + "ManyToOne";
	String MAP_KEY = PACKAGE_ + "MapKey";
	String MAPPED_SUPERCLASS = PACKAGE_ + "MappedSuperclass";
	String NAMED_NATIVE_QUERIES = PACKAGE_ + "NamedNativeQueries";
	String NAMED_NATIVE_QUERY = PACKAGE_ + "NamedNativeQuery";
	String NAMED_QUERIES = PACKAGE_ + "NamedQueries";
	String NAMED_QUERY = PACKAGE_ + "NamedQuery";
	String ONE_TO_MANY = PACKAGE_ + "OneToMany";
	String ONE_TO_ONE = PACKAGE_ + "OneToOne";
	String ORDER_BY = PACKAGE_ + "OrderBy";
	String PERSISTENCE_CONTEXT = PACKAGE_ + "PersistenceContext";
	String PERSISTENCE_CONTEXTS = PACKAGE_ + "PersistenceContexts";
	String PERSISTENCE_UNIT = PACKAGE_ + "XmlPersistenceUnit";
	String PERSISTENCE_UNITS = PACKAGE_ + "PersistenceUnits";
	String POST_LOAD = PACKAGE_ + "PostLoad";
	String POST_PERSIST = PACKAGE_ + "PostPersist";
	String POST_REMOVE = PACKAGE_ + "PostRemove";
	String POST_UPDATE = PACKAGE_ + "PostUpdate";
	String PRE_PERSIST = PACKAGE_ + "PrePersist";
	String PRE_REMOVE = PACKAGE_ + "PreRemove";
	String PRE_UPDATE = PACKAGE_ + "PreUpdate";
	String PRIMARY_KEY_JOIN_COLUMN = PACKAGE_ + "PrimaryKeyJoinColumn";
	String PRIMARY_KEY_JOIN_COLUMNS = PACKAGE_ + "PrimaryKeyJoinColumns";
	String QUERY_HINT = PACKAGE_ + "QueryHint";
	String SECONDARY_TABLE = PACKAGE_ + "SecondaryTable";
	String SECONDARY_TABLES = PACKAGE_ + "SecondaryTables";
	String SEQUENCE_GENERATOR = PACKAGE_ + "SequenceGenerator";
	String SQL_RESULT_SET_MAPPING = PACKAGE_ + "SqlResultSetMapping";
	String TABLE = PACKAGE_ + "Table";
	String TABLE_GENERATOR = PACKAGE_ + "TableGenerator";
	String TEMPORAL = PACKAGE_ + "Temporal";
	String TRANSIENT = PACKAGE_ + "Transient";
	String UNIQUE_CONSTRAINT = PACKAGE_ + "UniqueConstraint";
	String VERSION = PACKAGE_ + "Version";

	// JPA 1.0 enums
	String CASCADE_TYPE = PACKAGE_ + "CascadeType";
		String CASCADE_TYPE_ = CASCADE_TYPE + '.';
		String CASCADE_TYPE__ALL    = CASCADE_TYPE_ + "ALL";
		String CASCADE_TYPE__MERGE   = CASCADE_TYPE_ + "MERGE";
		String CASCADE_TYPE__PERSIST = CASCADE_TYPE_ + "PERSIST";
		String CASCADE_TYPE__REFRESH = CASCADE_TYPE_ + "REFRESH";
		String CASCADE_TYPE__REMOVE  = CASCADE_TYPE_ + "REMOVE";

	String DISCRIMINATOR_TYPE = PACKAGE_ + "DiscriminatorType";
		String DISCRIMINATOR_TYPE_ = DISCRIMINATOR_TYPE + '.';
		String DISCRIMINATOR_TYPE__CHAR    = DISCRIMINATOR_TYPE_ + "CHAR";
		String DISCRIMINATOR_TYPE__INTEGER = DISCRIMINATOR_TYPE_ + "INTEGER";
		String DISCRIMINATOR_TYPE__STRING  = DISCRIMINATOR_TYPE_ + "STRING";

	String ENUM_TYPE = PACKAGE_ + "EnumType";
		String ENUM_TYPE_ = ENUM_TYPE + '.';
		String ENUM_TYPE__ORDINAL = ENUM_TYPE_ + "ORDINAL";
		String ENUM_TYPE__STRING  = ENUM_TYPE_ + "STRING";

	String FETCH_TYPE = PACKAGE_ + "FetchType";
		String FETCH_TYPE_ = FETCH_TYPE + '.';
		String FETCH_TYPE__EAGER = FETCH_TYPE_ + "EAGER";
		String FETCH_TYPE__LAZY  = FETCH_TYPE_ + "LAZY";

	String FLUSH_MODE_TYPE = PACKAGE_ + "FlushModeType";
		String FLUSH_MODE_TYPE_ = FLUSH_MODE_TYPE + '.';
		String FLUSH_MODE_TYPE__AUTO   = FLUSH_MODE_TYPE_ + "AUTO";
		String FLUSH_MODE_TYPE__COMMIT = FLUSH_MODE_TYPE_ + "COMMIT";

	String GENERATION_TYPE = PACKAGE_ + "GenerationType";
		String GENERATION_TYPE_ = GENERATION_TYPE + '.';
		String GENERATION_TYPE__AUTO     = GENERATION_TYPE_ + "AUTO";
		String GENERATION_TYPE__IDENTITY = GENERATION_TYPE_ + "IDENTITY";
		String GENERATION_TYPE__SEQUENCE = GENERATION_TYPE_ + "SEQUENCE";
		String GENERATION_TYPE__TABLE    = GENERATION_TYPE_ + "TABLE";
		/** Added in JPA 3.1 ({@code GenerationType.UUID}). */
		String GENERATION_TYPE__UUID     = GENERATION_TYPE_ + "UUID";

	String INHERITANCE_TYPE = PACKAGE_ + "InheritanceType";
		String INHERITANCE_TYPE_ = INHERITANCE_TYPE + '.';
		String INHERITANCE_TYPE__JOINED       = INHERITANCE_TYPE_ + "JOINED";
		String INHERITANCE_TYPE__SINGLE_TABLE = INHERITANCE_TYPE_ + "SINGLE_TABLE";
		String INHERITANCE_TYPE__TABLE_PER_CLASS = INHERITANCE_TYPE_ + "TABLE_PER_CLASS";

	String PERSISTENCE_CONTEXT_TYPE = PACKAGE_ + "PersistenceContextType";
		String PERSISTENCE_CONTEXT_TYPE_ = PERSISTENCE_CONTEXT_TYPE + '.';
		String PERSISTENCE_CONTEXT_TYPE__EXTENDED    = PERSISTENCE_CONTEXT_TYPE_ + "EXTENDED";
		String PERSISTENCE_CONTEXT_TYPE__TRANSACTION = PERSISTENCE_CONTEXT_TYPE_ + "TRANSACTION";

	String TEMPORAL_TYPE = PACKAGE_ + "TemporalType";
		String TEMPORAL_TYPE_ = TEMPORAL_TYPE + '.';
		String TEMPORAL_TYPE__DATE      = TEMPORAL_TYPE_ + "DATE";
		String TEMPORAL_TYPE__TIME      = TEMPORAL_TYPE_ + "TIME";
		String TEMPORAL_TYPE__TIMESTAMP = TEMPORAL_TYPE_ + "TIMESTAMP";

	// JPA 1.0 interfaces / classes / exceptions
	String ENTITY_MANAGER         = PACKAGE_ + "EntityManager";
	String ENTITY_MANAGER_FACTORY = PACKAGE_ + "EntityManagerFactory";
	String ENTITY_TRANSACTION      = PACKAGE_ + "EntityTransaction";
	String INSTRUMENTABLE_CLASS_LOADER = PACKAGE_ + "InstrumentableClassLoader";
	String QUERY                  = PACKAGE_ + "Query";
	String PERSISTENCE            = PACKAGE_ + "XmlPersistence";
	String NON_UNIQUE_RESULT_EXCEPTION = PACKAGE_ + "NonUniqueResultException";
	String OBJECT_NOT_FOUND_EXCEPTION  = PACKAGE_ + "ObjectNotFoundException";
	String PERSISTENCE_EXCEPTION  = PACKAGE_ + "PersistenceException";

	// JPA 1.0 SPI
	String SPI_PACKAGE  = PACKAGE_ + "spi";
	String SPI_PACKAGE_ = SPI_PACKAGE + '.';
	String ENTITY_MANAGER_FACTORY_PROVIDER = SPI_PACKAGE_ + "EntityManagerFactoryProvider";
	String PERSISTENCE_INFO     = SPI_PACKAGE_ + "PersistenceInfo";
	String PERSISTENCE_PROVIDER = SPI_PACKAGE_ + "PersistenceProvider";


	// ====================================================================
	// JPA 2.0 additions  (equivalent of JPA2_0 using jakarta prefix)
	// ====================================================================

	// @Access
	String ACCESS = PACKAGE_ + "Access";
		String ACCESS__VALUE = "value";

	// @AssociationOverride additions (element name only – no package prefix)
	String ASSOCIATION_OVERRIDE__JOIN_TABLE = "joinTable";

	// CascadeType.DETACH added in JPA 2.0
	String CASCADE_TYPE__DETACH = CASCADE_TYPE_ + "DETACH";

	// @Cacheable
	String CACHEABLE = PACKAGE_ + "Cacheable";
		String CACHEABLE__VALUE = "value";

	// @CollectionTable
	String COLLECTION_TABLE = PACKAGE_ + "CollectionTable";
		String COLLECTION_TABLE__NAME               = "name";
		String COLLECTION_TABLE__SCHEMA             = "schema";
		String COLLECTION_TABLE__CATALOG            = "catalog";
		String COLLECTION_TABLE__JOIN_COLUMNS       = "joinColumns";
		String COLLECTION_TABLE__UNIQUE_CONSTRAINTS = "uniqueConstraints";

	// @ElementCollection
	String ELEMENT_COLLECTION = PACKAGE_ + "ElementCollection";
		String ELEMENT_COLLECTION__FETCH        = "fetch";
		String ELEMENT_COLLECTION__TARGET_CLASS = "targetClass";

	// @MapKeyClass
	String MAP_KEY_CLASS = PACKAGE_ + "MapKeyClass";
		String MAP_KEY_CLASS__VALUE = "value";

	// @MapKeyColumn
	String MAP_KEY_COLUMN = PACKAGE_ + "MapKeyColumn";
		String MAP_KEY_COLUMN__NAME              = "name";
		String MAP_KEY_COLUMN__UNIQUE            = "unique";
		String MAP_KEY_COLUMN__NULLABLE          = "nullable";
		String MAP_KEY_COLUMN__INSERTABLE        = "insertable";
		String MAP_KEY_COLUMN__UPDATABLE         = "updatable";
		String MAP_KEY_COLUMN__COLUMN_DEFINITION = "columnDefinition";
		String MAP_KEY_COLUMN__TABLE             = "table";
		String MAP_KEY_COLUMN__LENGTH            = "length";
		String MAP_KEY_COLUMN__PRECISION         = "precision";
		String MAP_KEY_COLUMN__SCALE             = "scale";

	// @MapKeyEnumerated
	String MAP_KEY_ENUMERATED = PACKAGE_ + "MapKeyEnumerated";
		String MAP_KEY_ENUMERATED__VALUE = "value";

	// @MapKeyJoinColumn
	String MAP_KEY_JOIN_COLUMN = PACKAGE_ + "MapKeyJoinColumn";
		String MAP_KEY_JOIN_COLUMN__NAME                   = "name";
		String MAP_KEY_JOIN_COLUMN__REFERENCED_COLUMN_NAME = "referencedColumnName";
		String MAP_KEY_JOIN_COLUMN__UNIQUE                 = "unique";
		String MAP_KEY_JOIN_COLUMN__NULLABLE               = "nullable";
		String MAP_KEY_JOIN_COLUMN__INSERTABLE             = "insertable";
		String MAP_KEY_JOIN_COLUMN__UPDATABLE              = "updatable";
		String MAP_KEY_JOIN_COLUMN__COLUMN_DEFINITION      = "columnDefinition";
		String MAP_KEY_JOIN_COLUMN__TABLE                  = "table";

	// @MapKeyJoinColumns
	String MAP_KEY_JOIN_COLUMNS = PACKAGE_ + "MapKeyJoinColumns";
		String MAP_KEY_JOIN_COLUMNS__VALUE = "value";

	// @MapKeyTemporal
	String MAP_KEY_TEMPORAL = PACKAGE_ + "MapKeyTemporal";
		String MAP_KEY_TEMPORAL__VALUE = "value";

	// @MapsId
	String MAPS_ID = PACKAGE_ + "MapsId";
		String MAPS_ID__VALUE = "value";

	// @NamedQuery additions
	String NAMED_QUERY__LOCK_MODE = "lockMode";

	// @OneToMany / @OneToOne orphanRemoval element (name only)
	String ONE_TO_MANY__ORPHAN_REMOVAL = "orphanRemoval";
	String ONE_TO_ONE__ORPHAN_REMOVAL  = "orphanRemoval";

	// @OrderColumn
	String ORDER_COLUMN = PACKAGE_ + "OrderColumn";
		String ORDER_COLUMN__NAME              = "name";
		String ORDER_COLUMN__NULLABLE          = "nullable";
		String ORDER_COLUMN__INSERTABLE        = "insertable";
		String ORDER_COLUMN__UPDATABLE         = "updatable";
		String ORDER_COLUMN__COLUMN_DEFINITION = "columnDefinition";

	// @SequenceGenerator additions (element names only)
	String SEQUENCE_GENERATOR__CATALOG = "catalog";
	String SEQUENCE_GENERATOR__SCHEMA  = "schema";

	// JPA 2.0 enums
	String ACCESS_TYPE = PACKAGE_ + "AccessType";
		String ACCESS_TYPE_ = ACCESS_TYPE + '.';
		String ACCESS_TYPE__FIELD    = ACCESS_TYPE_ + "FIELD";
		String ACCESS_TYPE__PROPERTY = ACCESS_TYPE_ + "PROPERTY";

	String LOCK_MODE_TYPE = PACKAGE_ + "LockModeType";
		String LOCK_MODE_TYPE_ = LOCK_MODE_TYPE + '.';
		String LOCK_MODE_TYPE__READ                       = LOCK_MODE_TYPE_ + "READ";
		String LOCK_MODE_TYPE__WRITE                      = LOCK_MODE_TYPE_ + "WRITE";
		String LOCK_MODE_TYPE__OPTIMISTIC                 = LOCK_MODE_TYPE_ + "OPTIMISTIC";
		String LOCK_MODE_TYPE__OPTIMISTIC_FORCE_INCREMENT = LOCK_MODE_TYPE_ + "OPTIMISTIC_FORCE_INCREMENT";
		String LOCK_MODE_TYPE__PESSIMISTIC_READ           = LOCK_MODE_TYPE_ + "PESSIMISTIC_READ";
		String LOCK_MODE_TYPE__PESSIMISTIC_WRITE          = LOCK_MODE_TYPE_ + "PESSIMISTIC_WRITE";
		String LOCK_MODE_TYPE__PESSIMISTIC_FORCE_INCREMENT = LOCK_MODE_TYPE_ + "PESSIMISTIC_FORCE_INCREMENT";
		String LOCK_MODE_TYPE__NONE                       = LOCK_MODE_TYPE_ + "NONE";

	// JPA 2.0 metamodel
	String METAMODEL_PACKAGE  = PACKAGE_ + "metamodel";
	String METAMODEL_PACKAGE_ = METAMODEL_PACKAGE + '.';

	String STATIC_METAMODEL = METAMODEL_PACKAGE_ + "StaticMetamodel";
		String STATIC_METAMODEL__VALUE = "value";

	String SINGULAR_ATTRIBUTE   = METAMODEL_PACKAGE_ + "SingularAttribute";
	String COLLECTION_ATTRIBUTE = METAMODEL_PACKAGE_ + "CollectionAttribute";
	String LIST_ATTRIBUTE        = METAMODEL_PACKAGE_ + "ListAttribute";
	String MAP_ATTRIBUTE         = METAMODEL_PACKAGE_ + "MapAttribute";
	String SET_ATTRIBUTE         = METAMODEL_PACKAGE_ + "SetAttribute";


	// ====================================================================
	// JPA 2.1 additions  (equivalent of JPA2_1 using jakarta prefix)
	// ====================================================================

	// @Convert
	String CONVERT = PACKAGE_ + "Convert";
		String CONVERT__CONVERTER           = "converter";
		String CONVERT__ATTRIBUTE_NAME      = "attributeName";
		String CONVERT__DISABLE_CONVERSION  = "disableConversion";

	// @Converts (container for repeatable @Convert)
	String CONVERTS = PACKAGE_ + "Converts";
		String CONVERTS__VALUE = "value";

	// @Converter
	String CONVERTER = PACKAGE_ + "Converter";
		String CONVERTER__AUTO_APPLY = "autoApply";

	// @NamedEntityGraph / @NamedEntityGraphs
	String NAMED_ENTITY_GRAPH = PACKAGE_ + "NamedEntityGraph";
		String NAMED_ENTITY_GRAPH__NAME                     = "name";
		String NAMED_ENTITY_GRAPH__ATTRIBUTE_NODES          = "attributeNodes";
		String NAMED_ENTITY_GRAPH__INCLUDE_ALL_ATTRIBUTES   = "includeAllAttributes";
		String NAMED_ENTITY_GRAPH__SUBGRAPHS                = "subgraphs";
		String NAMED_ENTITY_GRAPH__SUBCLASS_SUBGRAPHS       = "subclassSubgraphs";
	String NAMED_ENTITY_GRAPHS = PACKAGE_ + "NamedEntityGraphs";
		String NAMED_ENTITY_GRAPHS__VALUE = "value";

	// @NamedAttributeNode
	String NAMED_ATTRIBUTE_NODE = PACKAGE_ + "NamedAttributeNode";
		String NAMED_ATTRIBUTE_NODE__VALUE     = "value";
		String NAMED_ATTRIBUTE_NODE__SUBGRAPH  = "subgraph";
		String NAMED_ATTRIBUTE_NODE__KEY_SUBGRAPH = "keySubgraph";

	// @NamedSubgraph
	String NAMED_SUBGRAPH = PACKAGE_ + "NamedSubgraph";
		String NAMED_SUBGRAPH__NAME            = "name";
		String NAMED_SUBGRAPH__TYPE            = "type";
		String NAMED_SUBGRAPH__ATTRIBUTE_NODES = "attributeNodes";

	// @NamedStoredProcedureQuery / @NamedStoredProcedureQueries
	String NAMED_STORED_PROCEDURE_QUERIES = PACKAGE_ + "NamedStoredProcedureQueries";
		String NAMED_STORED_PROCEDURE_QUERIES__VALUE = "value";
	String NAMED_STORED_PROCEDURE_QUERY = PACKAGE_ + "NamedStoredProcedureQuery";
		String NAMED_STORED_PROCEDURE_QUERY__NAME             = "name";
		String NAMED_STORED_PROCEDURE_QUERY__PROCEDURE_NAME   = "procedureName";
		String NAMED_STORED_PROCEDURE_QUERY__PARAMETERS       = "parameters";
		String NAMED_STORED_PROCEDURE_QUERY__RESULT_CLASSES   = "resultClasses";
		String NAMED_STORED_PROCEDURE_QUERY__RESULT_SET_MAPPINGS = "resultSetMappings";
		String NAMED_STORED_PROCEDURE_QUERY__HINTS            = "hints";
	String NAMED_STORED_PROCEDURE_PARAMETER = PACKAGE_ + "StoredProcedureParameter";
		String NAMED_STORED_PROCEDURE_PARAMETER__NAME = "name";
		String NAMED_STORED_PROCEDURE_PARAMETER__MODE = "mode";
		String NAMED_STORED_PROCEDURE_PARAMETER__TYPE = "type";

	// @ForeignKey (added in JPA 2.1 – used in join column and table annotations)
	String FOREIGN_KEY = PACKAGE_ + "ForeignKey";
		String FOREIGN_KEY__NAME                   = "name";
		String FOREIGN_KEY__VALUE                  = "value";
		String FOREIGN_KEY__FOREIGN_KEY_DEFINITION = "foreignKeyDefinition";

	// @Index (added in JPA 2.1)
	String INDEX = PACKAGE_ + "Index";
		String INDEX__NAME         = "name";
		String INDEX__COLUMN_LIST  = "columnList";
		String INDEX__UNIQUE       = "unique";

	// JPA 2.1 enums
	String PARAMETER_MODE = PACKAGE_ + "ParameterMode";
		String PARAMETER_MODE_ = PARAMETER_MODE + '.';
		String PARAMETER_MODE__IN         = PARAMETER_MODE_ + "IN";
		String PARAMETER_MODE__INOUT      = PARAMETER_MODE_ + "INOUT";
		String PARAMETER_MODE__OUT        = PARAMETER_MODE_ + "OUT";
		String PARAMETER_MODE__REF_CURSOR = PARAMETER_MODE_ + "REF_CURSOR";

	String CONSTRAINT_MODE = PACKAGE_ + "ConstraintMode";
		String CONSTRAINT_MODE_ = CONSTRAINT_MODE + '.';
		String CONSTRAINT_MODE__CONSTRAINT    = CONSTRAINT_MODE_ + "CONSTRAINT";
		String CONSTRAINT_MODE__NO_CONSTRAINT = CONSTRAINT_MODE_ + "NO_CONSTRAINT";
		String CONSTRAINT_MODE__PROVIDER_DEFAULT = CONSTRAINT_MODE_ + "PROVIDER_DEFAULT";


	// ====================================================================
	// JPA 2.2 additions  (equivalent of JPA2_2 using jakarta prefix)
	// ====================================================================
	// JPA 2.2 mainly added @Repeatable support to existing annotations.
	// The container annotations themselves (e.g. @AssociationOverrides) were
	// already present. No fundamentally new annotation types were introduced.


	// ====================================================================
	// JPA 3.1 additions
	// ====================================================================

	/** Added in JPA 3.1: {@code GenerationType.UUID} — see {@link #GENERATION_TYPE__UUID}. */
	// No new annotation types in JPA 3.1 beyond what is already declared above.
}
