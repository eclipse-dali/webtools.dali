/*******************************************************************************
 * Copyright (c) 2006, 2025 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.resource.java;

import java.util.Optional;
import java.util.function.Function;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.IProjectFacet;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;

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
	String DEFAULT_PACKAGE = "javax.persistence";
	String JAKARTA_PACKAGE = "jakarta.persistence";
	// Maps a JPA facet version to its corresponding package name
	Function<IProjectFacetVersion, String> mapVersionToPackage = version -> Optional.ofNullable(version)
			.map(v -> v.getVersionString().startsWith("3") ? "jakarta.persistence" : DEFAULT_PACKAGE)
			.orElse(DEFAULT_PACKAGE);

	// Retrieves the installed JPA facet version from a project
	Function<IProject, Optional<IProjectFacetVersion>> getInstalledJpaVersion = project -> {
		try {
			IFacetedProject facetedProject = ProjectFacetsManager.create(project);
			if (facetedProject != null) {
				IProjectFacet jpaFacet = ProjectFacetsManager.getProjectFacet("jpt.jpa");
				return Optional.ofNullable(facetedProject.getInstalledVersion(jpaFacet));
			}
		} catch (CoreException e) {
			// Optionally log the exception
		}
		return Optional.empty();
	};

	// Resolves the appropriate JPA package name for a given project
	Function<IProject, String> PACKAGE = project -> getInstalledJpaVersion.apply(project).map(mapVersionToPackage)
			.orElse(DEFAULT_PACKAGE);

	Function<IProject, String> PACKAGE_ = project -> PACKAGE.apply(project) + '.';

	// ********** API **********

	// JPA annotations
	Function<IProject, String> ASSOCIATION_OVERRIDE = project -> PACKAGE_.apply(project) + "AssociationOverride";
		String ASSOCIATION_OVERRIDE__NAME = "name";
		String ASSOCIATION_OVERRIDE__JOIN_COLUMNS = "joinColumns";
	Function<IProject, String> ASSOCIATION_OVERRIDES =  project -> PACKAGE_.apply(project) + "AssociationOverrides";
		String ASSOCIATION_OVERRIDES__VALUE = "value";
	Function<IProject, String> ATTRIBUTE_OVERRIDE =  project -> PACKAGE_.apply(project) + "AttributeOverride";
		String ATTRIBUTE_OVERRIDE__NAME = "name";
		String ATTRIBUTE_OVERRIDE__COLUMN = "column";
	Function<IProject, String> ATTRIBUTE_OVERRIDES = project -> PACKAGE_.apply(project) + "AttributeOverrides";
		String ATTRIBUTE_OVERRIDES__VALUE = "value";
	Function<IProject, String> BASIC = project -> PACKAGE_.apply(project) + "Basic";
		String BASIC__FETCH = "fetch";
		String BASIC__OPTIONAL = "optional";
	Function<IProject, String> COLUMN = project -> PACKAGE_.apply(project) + "Column";
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
	Function<IProject, String> COLUMN_RESULT = project -> PACKAGE_.apply(project) + "ColumnResult";
		String COLUMN_RESULT__NAME = "name";
	Function<IProject, String> DISCRIMINATOR_COLUMN = project -> PACKAGE_.apply(project) + "DiscriminatorColumn";
		String DISCRIMINATOR_COLUMN__NAME = "name";
		String DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE = "discriminatorType";
		String DISCRIMINATOR_COLUMN__COLUMN_DEFINITION = "columnDefinition";
		String DISCRIMINATOR_COLUMN__LENGTH = "length";
	Function<IProject, String> DISCRIMINATOR_VALUE = project -> PACKAGE_.apply(project) + "DiscriminatorValue";
		String DISCRIMINATOR_VALUE__VALUE = "value";
	Function<IProject, String> EMBEDDABLE = project -> PACKAGE_.apply(project) + "Embeddable";
	Function<IProject, String> EMBEDDED = project -> PACKAGE_.apply(project) + "Embedded";
	Function<IProject, String> EMBEDDED_ID = project -> PACKAGE_.apply(project) + "EmbeddedId";
	Function<IProject, String> ENTITY = project -> PACKAGE_.apply(project) + "Entity";
		String ENTITY__NAME = "name";
	Function<IProject, String> ENTITY_LISTENERS = project -> PACKAGE_.apply(project) + "EntityListeners";
		String ENTITY_LISTENERS__VALUE = "value";
	Function<IProject, String> ENTITY_RESULT = project -> PACKAGE_.apply(project) + "EntityResult";
		String ENTITY_RESULT__ENTITY_CLASS = "entityClass";
		String ENTITY_RESULT__FIELDS = "fields";
		String ENTITY_RESULT__DISCRIMINATOR_COLUMN = "discriminatorColumn";
	Function<IProject, String> ENUMERATED = project -> PACKAGE_.apply(project) + "Enumerated";
		String ENUMERATED__VALUE = "value";
	Function<IProject, String> EXCLUDE_DEFAULT_LISTENERS = project -> PACKAGE_.apply(project) + "ExcludeDefaultListeners";
	Function<IProject, String> EXCLUDE_SUPERCLASS_LISTENERS = project -> PACKAGE_.apply(project) + "ExcludeSuperclassListeners";
	Function<IProject, String> FIELD_RESULT = project -> PACKAGE_.apply(project) + "FieldResult";
		String FIELD_RESULT__NAME = "name";
		String FIELD_RESULT__COLUMN = "column";
	Function<IProject, String> FLUSH_MODE = project -> PACKAGE_.apply(project) + "FlushMode";
		String FLUSH_MODE__VALUE = "value";
	Function<IProject, String> GENERATED_VALUE = project -> PACKAGE_.apply(project) + "GeneratedValue";
		String GENERATED_VALUE__STRATEGY = "strategy";
		String GENERATED_VALUE__GENERATOR = "generator";
	Function<IProject, String> ID = project -> PACKAGE_.apply(project) + "Id";
	Function<IProject, String> ID_CLASS = project -> PACKAGE_.apply(project) + "IdClass";
		String ID_CLASS__VALUE = "value";
	Function<IProject, String> INHERITANCE = project -> PACKAGE_.apply(project) + "Inheritance";
		String INHERITANCE__STRATEGY = "strategy";
	Function<IProject, String> JOIN_COLUMN = project -> PACKAGE_.apply(project) + "JoinColumn";
		String JOIN_COLUMN__NAME = "name";
		String JOIN_COLUMN__REFERENCED_COLUMN_NAME = "referencedColumnName";
		String JOIN_COLUMN__UNIQUE = "unique";
		String JOIN_COLUMN__NULLABLE = "nullable";
		String JOIN_COLUMN__INSERTABLE = "insertable";
		String JOIN_COLUMN__UPDATABLE = "updatable";
		String JOIN_COLUMN__COLUMN_DEFINITION = "columnDefinition";
		String JOIN_COLUMN__TABLE = "table";
	Function<IProject, String> JOIN_COLUMNS = project -> PACKAGE_.apply(project) + "JoinColumns";
		String JOIN_COLUMNS__VALUE = "value";
	Function<IProject, String> JOIN_TABLE = project -> PACKAGE_.apply(project) + "JoinTable";
		String JOIN_TABLE__NAME = "name";
		String JOIN_TABLE__CATALOG = "catalog";
		String JOIN_TABLE__SCHEMA = "schema";
		String JOIN_TABLE__JOIN_COLUMNS = "joinColumns";
		String JOIN_TABLE__INVERSE_JOIN_COLUMNS = "inverseJoinColumns";
		String JOIN_TABLE__UNIQUE_CONSTRAINTS = "uniqueConstraints";
	Function<IProject, String> LOB = project -> PACKAGE_.apply(project) + "Lob";
	Function<IProject, String> MANY_TO_MANY = project -> PACKAGE_.apply(project) + "ManyToMany";
		String MANY_TO_MANY__TARGET_ENTITY = "targetEntity";
		String MANY_TO_MANY__CASCADE = "cascade";
		String MANY_TO_MANY__FETCH = "fetch";
		String MANY_TO_MANY__MAPPED_BY = "mappedBy";
	Function<IProject, String> MANY_TO_ONE = project -> PACKAGE_.apply(project) + "ManyToOne";
		String MANY_TO_ONE__TARGET_ENTITY = "targetEntity";
		String MANY_TO_ONE__CASCADE = "cascade";
		String MANY_TO_ONE__FETCH = "fetch";
		String MANY_TO_ONE__OPTIONAL = "optional";
	Function<IProject, String> MAP_KEY = project -> PACKAGE_.apply(project) + "MapKey";
		String MAP_KEY__NAME = "name";
	Function<IProject, String> MAPPED_SUPERCLASS = project -> PACKAGE_.apply(project) + "MappedSuperclass";
	Function<IProject, String> NAMED_NATIVE_QUERIES = project -> PACKAGE_.apply(project) + "NamedNativeQueries";
		String NAMED_NATIVE_QUERIES__VALUE = "value";
	Function<IProject, String> NAMED_NATIVE_QUERY = project -> PACKAGE_.apply(project) + "NamedNativeQuery";
		String NAMED_NATIVE_QUERY__NAME = "name";
		String NAMED_NATIVE_QUERY__QUERY = "query";
		String NAMED_NATIVE_QUERY__HINTS = "hints";
		String NAMED_NATIVE_QUERY__RESULT_CLASS = "resultClass";
		String NAMED_NATIVE_QUERY__RESULT_SET_MAPPING = "resultSetMapping";
	Function<IProject, String> NAMED_QUERIES = project -> PACKAGE_.apply(project) + "NamedQueries";
		String NAMED_QUERIES__VALUE = "value";
	Function<IProject, String> NAMED_QUERY = project -> PACKAGE_.apply(project) + "NamedQuery";
		String NAMED_QUERY__NAME = "name";
		String NAMED_QUERY__QUERY = "query";
		String NAMED_QUERY__HINTS = "hints";
	Function<IProject, String> ONE_TO_MANY = project -> PACKAGE_.apply(project) + "OneToMany";
		String ONE_TO_MANY__TARGET_ENTITY = "targetEntity";
		String ONE_TO_MANY__CASCADE = "cascade";
		String ONE_TO_MANY__FETCH = "fetch";
		String ONE_TO_MANY__MAPPED_BY = "mappedBy";
	Function<IProject, String> ONE_TO_ONE = project -> PACKAGE_.apply(project) + "OneToOne";
		String ONE_TO_ONE__TARGET_ENTITY = "targetEntity";
		String ONE_TO_ONE__CASCADE = "cascade";
		String ONE_TO_ONE__FETCH = "fetch";
		String ONE_TO_ONE__OPTIONAL = "optional";
		String ONE_TO_ONE__MAPPED_BY = "mappedBy";
	Function<IProject, String> ORDER_BY = project -> PACKAGE_.apply(project) + "OrderBy";
		String ORDER_BY__VALUE = "value";
	Function<IProject, String> PERSISTENCE_CONTEXT = project -> PACKAGE_.apply(project) + "PersistenceContext";
		String PERSISTENCE_CONTEXT__NAME = "name";
		String PERSISTENCE_CONTEXT__UNIT_NAME = "unitName";
		String PERSISTENCE_CONTEXT__TYPE = "type";
	Function<IProject, String> PERSISTENCE_CONTEXTS = project -> PACKAGE_.apply(project) + "PersistenceContexts";
		String PERSISTENCE_CONTEXTS__VALUE = "value";
	Function<IProject, String> PERSISTENCE_UNIT = project -> PACKAGE_.apply(project) + "XmlPersistenceUnit";
		String PERSISTENCE_UNIT__NAME = "name";
		String PERSISTENCE_UNIT__UNIT_NAME = "unitName";
	Function<IProject, String> PERSISTENCE_UNITS = project -> PACKAGE_.apply(project) + "PersistenceUnits";
		String PERSISTENCE_UNITS__VALUE = "value";
	Function<IProject, String> POST_LOAD = project -> PACKAGE_.apply(project) + "PostLoad";
	Function<IProject, String> POST_PERSIST = project -> PACKAGE_.apply(project) + "PostPersist";
	Function<IProject, String> POST_REMOVE = project -> PACKAGE_.apply(project) + "PostRemove";
	Function<IProject, String> POST_UPDATE = project -> PACKAGE_.apply(project) + "PostUpdate";
	Function<IProject, String> PRE_PERSIST = project -> PACKAGE_.apply(project) + "PrePersist";
	Function<IProject, String> PRE_REMOVE = project -> PACKAGE_.apply(project) + "PreRemove";
	Function<IProject, String> PRE_UPDATE = project -> PACKAGE_.apply(project) + "PreUpdate";
	Function<IProject, String> PRIMARY_KEY_JOIN_COLUMN = project -> PACKAGE_.apply(project) + "PrimaryKeyJoinColumn";
		String PRIMARY_KEY_JOIN_COLUMN__NAME = "name";
		String PRIMARY_KEY_JOIN_COLUMN__REFERENCED_COLUMN_NAME = "referencedColumnName";
		String PRIMARY_KEY_JOIN_COLUMN__COLUMN_DEFINITION = "columnDefinition";
	Function<IProject, String> PRIMARY_KEY_JOIN_COLUMNS = project -> PACKAGE_.apply(project) + "PrimaryKeyJoinColumns";
		String PRIMARY_KEY_JOIN_COLUMNS__VALUE = "value";
	Function<IProject, String> QUERY_HINT = project -> PACKAGE_.apply(project) + "QueryHint";
		String QUERY_HINT__NAME = "name";
		String QUERY_HINT__VALUE = "value";
	Function<IProject, String> SECONDARY_TABLE = project -> PACKAGE_.apply(project) + "SecondaryTable";
		String SECONDARY_TABLE__NAME = "name";
		String SECONDARY_TABLE__CATALOG = "catalog";
		String SECONDARY_TABLE__SCHEMA = "schema";
		String SECONDARY_TABLE__PK_JOIN_COLUMNS = "pkJoinColumns";
		String SECONDARY_TABLE__UNIQUE_CONSTRAINTS = "uniqueConstraints";
	Function<IProject, String> SECONDARY_TABLES = project -> PACKAGE_.apply(project) + "SecondaryTables";
		String SECONDARY_TABLES__VALUE = "value";
	Function<IProject, String> SEQUENCE_GENERATOR = project -> PACKAGE_.apply(project) + "SequenceGenerator";
		String SEQUENCE_GENERATOR__NAME = "name";
		String SEQUENCE_GENERATOR__SEQUENCE_NAME = "sequenceName";
		String SEQUENCE_GENERATOR__INITIAL_VALUE = "initialValue";
		String SEQUENCE_GENERATOR__ALLOCATION_SIZE = "allocationSize";
	Function<IProject, String> SQL_RESULT_SET_MAPPING = project -> PACKAGE_.apply(project) + "SqlResultSetMapping";
		String SQL_RESULT_SET_MAPPING__NAME = "name";
		String SQL_RESULT_SET_MAPPING__ENTITIES = "entities";
		String SQL_RESULT_SET_MAPPING__COLUMNS = "columns";
	Function<IProject, String> TABLE = project -> PACKAGE_.apply(project) + "Table";
		String TABLE__NAME = "name";
		String TABLE__CATALOG = "catalog";
		String TABLE__SCHEMA = "schema";
		String TABLE__UNIQUE_CONSTRAINTS = "uniqueConstraints";
	Function<IProject, String> TABLE_GENERATOR = project -> PACKAGE_.apply(project) + "TableGenerator";
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
	Function<IProject, String> TEMPORAL = project -> PACKAGE_.apply(project) + "Temporal";
		String TEMPORAL__VALUE = "value";
	Function<IProject, String> TRANSIENT = project -> PACKAGE_.apply(project) + "Transient";
	Function<IProject, String> UNIQUE_CONSTRAINT = project -> PACKAGE_.apply(project) + "UniqueConstraint";
		String UNIQUE_CONSTRAINT__COLUMN_NAMES = "columnNames";
	Function<IProject, String> VERSION = project -> PACKAGE_.apply(project) + "Version";

	// JPA enums
	Function<IProject, String> CASCADE_TYPE = project -> PACKAGE_.apply(project) + "CascadeType";
		Function<IProject, String> CASCADE_TYPE_ = project -> CASCADE_TYPE.apply(project) + '.';
		Function<IProject, String> CASCADE_TYPE__ALL = project -> CASCADE_TYPE_.apply(project) + "ALL";
		Function<IProject, String> CASCADE_TYPE__MERGE = project -> CASCADE_TYPE_.apply(project) + "MERGE";
		Function<IProject, String> CASCADE_TYPE__PERSIST = project -> CASCADE_TYPE_.apply(project) + "PERSIST";
		Function<IProject, String> CASCADE_TYPE__REFRESH = project -> CASCADE_TYPE_.apply(project) + "REFRESH";
		Function<IProject, String> CASCADE_TYPE__REMOVE = project -> CASCADE_TYPE_.apply(project) + "REMOVE";

	Function<IProject, String> DISCRIMINATOR_TYPE = project -> PACKAGE_.apply(project) + "DiscriminatorType";
		Function<IProject, String> DISCRIMINATOR_TYPE_ = project -> DISCRIMINATOR_TYPE.apply(project) + '.';
		Function<IProject, String> DISCRIMINATOR_TYPE__CHAR = project -> DISCRIMINATOR_TYPE_.apply(project) + "CHAR";
		Function<IProject, String> DISCRIMINATOR_TYPE__INTEGER = project -> DISCRIMINATOR_TYPE_.apply(project) + "INTEGER";
		Function<IProject, String> DISCRIMINATOR_TYPE__STRING = project -> DISCRIMINATOR_TYPE_.apply(project) + "STRING";

	Function<IProject, String> ENUM_TYPE = project -> PACKAGE_.apply(project) + "EnumType";
		Function<IProject, String> ENUM_TYPE_ = project -> ENUM_TYPE.apply(project) + '.';
		Function<IProject, String> ENUM_TYPE__ORDINAL = project -> ENUM_TYPE_.apply(project) + "ORDINAL";
		Function<IProject, String> ENUM_TYPE__STRING = project -> ENUM_TYPE_.apply(project) + "STRING";

	Function<IProject, String> FETCH_TYPE = project -> PACKAGE_.apply(project) + "FetchTypes";
		Function<IProject, String> FETCH_TYPE_ = project -> FETCH_TYPE.apply(project) + '.';
		Function<IProject, String> FETCH_TYPE__EAGER = project -> FETCH_TYPE_.apply(project) + "EAGER";
		Function<IProject, String> FETCH_TYPE__LAZY = project -> FETCH_TYPE_.apply(project) + "LAZY";

	Function<IProject, String> FLUSH_MODE_TYPE = project -> PACKAGE_.apply(project) + "FlushModeType";
		Function<IProject, String> FLUSH_MODE_TYPE_ = project -> FLUSH_MODE_TYPE.apply(project) + '.';
		Function<IProject, String> FLUSH_MODE_TYPE__AUTO = project -> FLUSH_MODE_TYPE_.apply(project) + "AUTO";
		Function<IProject, String> FLUSH_MODE_TYPE__COMMIT = project -> FLUSH_MODE_TYPE_.apply(project) + "COMMIT";

	Function<IProject, String> GENERATION_TYPE = project -> PACKAGE_.apply(project) + "GenerationType";
		Function<IProject, String> GENERATION_TYPE_ = project -> GENERATION_TYPE.apply(project) + '.';
		Function<IProject, String> GENERATION_TYPE__AUTO = project -> GENERATION_TYPE_.apply(project) + "AUTO";
		Function<IProject, String> GENERATION_TYPE__IDENTITY = project -> GENERATION_TYPE_.apply(project) + "IDENTITY";
		Function<IProject, String> GENERATION_TYPE__SEQUENCE = project -> GENERATION_TYPE_.apply(project) + "SEQUENCE";
		Function<IProject, String> GENERATION_TYPE__TABLE = project -> GENERATION_TYPE_.apply(project) + "TABLE";

	Function<IProject, String> INHERITANCE_TYPE = project -> PACKAGE_.apply(project) + "InheritanceType";
		Function<IProject, String> INHERITANCE_TYPE_ = project -> INHERITANCE_TYPE.apply(project) + '.';
		Function<IProject, String> INHERITANCE_TYPE__JOINED = project -> INHERITANCE_TYPE_.apply(project) + "JOINED";
		Function<IProject, String> INHERITANCE_TYPE__SINGLE_TABLE = project -> INHERITANCE_TYPE_.apply(project) + "SINGLE_TABLE";
		Function<IProject, String> INHERITANCE_TYPE__TABLE_PER_CLASS = project -> INHERITANCE_TYPE_.apply(project) + "TABLE_PER_CLASS";

	Function<IProject, String> PERSISTENCE_CONTEXT_TYPE = project -> PACKAGE_.apply(project) + "PersistenceContextType";
		Function<IProject, String> PERSISTENCE_CONTEXT_TYPE_ = project -> PERSISTENCE_CONTEXT_TYPE.apply(project) + '.';
		Function<IProject, String> PERSISTENCE_CONTEXT_TYPE__EXTENDED = project -> PERSISTENCE_CONTEXT_TYPE_.apply(project)	+ "EXTENDED";
		Function<IProject, String> PERSISTENCE_CONTEXT_TYPE__TRANSACTION = project -> PERSISTENCE_CONTEXT_TYPE_.apply(project) + "TRANSACTION";

	Function<IProject, String> TEMPORAL_TYPE = project -> PACKAGE_.apply(project) + "TemporalType";
		Function<IProject, String> TEMPORAL_TYPE_ = project -> TEMPORAL_TYPE.apply(project) + '.';
		Function<IProject, String> TEMPORAL_TYPE__DATE = project -> TEMPORAL_TYPE_.apply(project) + "DATE";
		Function<IProject, String> TEMPORAL_TYPE__TIME = project -> TEMPORAL_TYPE_.apply(project) + "TIME";
		Function<IProject, String> TEMPORAL_TYPE__TIMESTAMP = project -> TEMPORAL_TYPE_.apply(project) + "TIMESTAMP";

	// JPA interfaces
	Function<IProject, String> ENTITY_MANAGER = project -> PACKAGE_.apply(project) + "EntityManager";
	Function<IProject, String> ENTITY_MANAGER_FACTORY = project -> PACKAGE_.apply(project) + "EntityManagerFactory";
	Function<IProject, String> ENTITY_TRANSACTION = project -> PACKAGE_.apply(project) + "EntityTransaction";
	Function<IProject, String> INSTRUMENTABLE_CLASS_LOADER = project -> PACKAGE_.apply(project) + "InstrumentableClassLoader";
	Function<IProject, String> QUERY = project -> PACKAGE_.apply(project) + "Query";

	// JPA classes
	Function<IProject, String> PERSISTENCE = project -> PACKAGE_.apply(project) + "XmlPersistence";

	// JPA exceptions
	Function<IProject, String> NON_UNIQUE_RESULT_EXCEPTION = project -> PACKAGE_.apply(project) + "NonUniqueResultException";
	Function<IProject, String> OBJECT_NOT_FOUND_EXCEPTION = project -> PACKAGE_.apply(project) + "ObjectNotFoundException";
	Function<IProject, String> PERSISTENCE_EXCEPTION = project -> PACKAGE_.apply(project) + "PersistenceException";


	// ********** SPI **********

	// JPA SPI package
	Function<IProject, String> SPI_PACKAGE = project -> PACKAGE_.apply(project) + "spi";
	Function<IProject, String> SPI_PACKAGE_ = project -> SPI_PACKAGE.apply(project)+ '.';

	// JPA SPI interfaces
	String ENTITY_MANAGER_FACTORY_PROVIDER = SPI_PACKAGE_ + "EntityManagerFactoryProvider";
	String PERSISTENCE_INFO = SPI_PACKAGE_ + "PersistenceInfo";
	String PERSISTENCE_PROVIDER = SPI_PACKAGE_ + "PersistenceProvider";
}
