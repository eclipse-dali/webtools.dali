/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2_1.resource.java;

import org.eclipse.jpt.jpa.core.resource.java.JPA;

/**
 * JPA 2.1 Java-related stuff (annotations etc.)
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 3.3
 */
@SuppressWarnings("nls")
public interface JPA2_1 
{
	// JPA package
	String PACKAGE = JPA.PACKAGE;
	String PACKAGE_ = JPA.PACKAGE_;


	// ********** API **********

	// JPA 2.1 annotations
	String CONVERTER = PACKAGE_ + "Converter";
		String CONVERTER__AUTO_APPLY = "autoApply";

	// @Index (added in JPA 2.1 — used in @Table, @SecondaryTable, @JoinTable, @CollectionTable)
	String INDEX = PACKAGE_ + "Index";
		String INDEX__NAME        = "name";
		String INDEX__COLUMN_LIST = "columnList";
		String INDEX__UNIQUE      = "unique";

	// @ConstructorResult (added in JPA 2.1 — used in @SqlResultSetMapping)
	String CONSTRUCTOR_RESULT = PACKAGE_ + "ConstructorResult";
		String CONSTRUCTOR_RESULT__TARGET_CLASS = "targetClass";
		String CONSTRUCTOR_RESULT__COLUMNS      = "columns";

	// @ForeignKey (added in JPA 2.1 — used in join column and table annotations)
	String FOREIGN_KEY = PACKAGE_ + "ForeignKey";
		String FOREIGN_KEY__NAME                   = "name";
		String FOREIGN_KEY__VALUE                  = "value";
		String FOREIGN_KEY__FOREIGN_KEY_DEFINITION = "foreignKeyDefinition";

	// @Convert
	String CONVERT = PACKAGE_ + "Convert";
		String CONVERT__CONVERTER          = "converter";
		String CONVERT__ATTRIBUTE_NAME     = "attributeName";
		String CONVERT__DISABLE_CONVERSION = "disableConversion";

	// @Converts (container for repeatable @Convert)
	String CONVERTS = PACKAGE_ + "Converts";
		String CONVERTS__VALUE = "value";

	// @NamedEntityGraph / @NamedEntityGraphs
	String NAMED_ENTITY_GRAPH = PACKAGE_ + "NamedEntityGraph";
		String NAMED_ENTITY_GRAPH__NAME                   = "name";
		String NAMED_ENTITY_GRAPH__ATTRIBUTE_NODES        = "attributeNodes";
		String NAMED_ENTITY_GRAPH__INCLUDE_ALL_ATTRIBUTES = "includeAllAttributes";
		String NAMED_ENTITY_GRAPH__SUBGRAPHS              = "subgraphs";
		String NAMED_ENTITY_GRAPH__SUBCLASS_SUBGRAPHS     = "subclassSubgraphs";
	String NAMED_ENTITY_GRAPHS = PACKAGE_ + "NamedEntityGraphs";
		String NAMED_ENTITY_GRAPHS__VALUE = "value";

	// @NamedAttributeNode
	String NAMED_ATTRIBUTE_NODE = PACKAGE_ + "NamedAttributeNode";
		String NAMED_ATTRIBUTE_NODE__VALUE        = "value";
		String NAMED_ATTRIBUTE_NODE__SUBGRAPH     = "subgraph";
		String NAMED_ATTRIBUTE_NODE__KEY_SUBGRAPH = "keySubgraph";

	// @NamedSubgraph
	String NAMED_SUBGRAPH = PACKAGE_ + "NamedSubgraph";
		String NAMED_SUBGRAPH__NAME            = "name";
		String NAMED_SUBGRAPH__TYPE            = "type";
		String NAMED_SUBGRAPH__ATTRIBUTE_NODES = "attributeNodes";

	// JPA 2.1 additions to pre-existing annotations (element names only)
	// @SqlResultSetMapping gained classes= in JPA 2.1
	String SQL_RESULT_SET_MAPPING__CLASSES = "classes";
	// @Table / @SecondaryTable gained indexes= in JPA 2.1
	String TABLE__INDEXES           = "indexes";
	String SECONDARY_TABLE__INDEXES = "indexes";
	// @JoinTable gained indexes= in JPA 2.1
	String JOIN_TABLE__INDEXES      = "indexes";
	// @CollectionTable gained indexes= in JPA 2.1
	String COLLECTION_TABLE__INDEXES = "indexes";
	// @TableGenerator gained indexes= in JPA 2.1
	String TABLE_GENERATOR__INDEXES  = "indexes";
	// @UniqueConstraint gained name= in JPA 2.1
	String UNIQUE_CONSTRAINT__NAME  = "name";
	// @ColumnResult gained type= in JPA 2.1
	String COLUMN_RESULT__TYPE      = "type";

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

	// JPA 2.1 enums
	String PARAMETER_MODE = PACKAGE_ + "ParameterMode";
		String PARAMETER_MODE_ = PARAMETER_MODE + '.';
		String PARAMETER_MODE__IN         = PARAMETER_MODE_ + "IN";
		String PARAMETER_MODE__INOUT      = PARAMETER_MODE_ + "INOUT";
		String PARAMETER_MODE__OUT        = PARAMETER_MODE_ + "OUT";
		String PARAMETER_MODE__REF_CURSOR = PARAMETER_MODE_ + "REF_CURSOR";

	String CONSTRAINT_MODE = PACKAGE_ + "ConstraintMode";
		String CONSTRAINT_MODE_ = CONSTRAINT_MODE + '.';
		String CONSTRAINT_MODE__CONSTRAINT       = CONSTRAINT_MODE_ + "CONSTRAINT";
		String CONSTRAINT_MODE__NO_CONSTRAINT    = CONSTRAINT_MODE_ + "NO_CONSTRAINT";
		String CONSTRAINT_MODE__PROVIDER_DEFAULT = CONSTRAINT_MODE_ + "PROVIDER_DEFAULT";

}
