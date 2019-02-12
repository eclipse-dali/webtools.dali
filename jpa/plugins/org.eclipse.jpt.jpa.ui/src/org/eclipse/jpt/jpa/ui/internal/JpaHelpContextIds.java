/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License 2.0, which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal;

import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;

/**
 * Help context ids for the Dali JPA UI.
 * <p>
 * This interface contains constants only; it is not intended to be
 * implemented.
 * </p>
 */
@SuppressWarnings("nls")
public interface JpaHelpContextIds {

	//ContextID prefix
	public static final String PREFIX = JptJpaUiPlugin.instance().getPluginID() + ".";

	//Persistent Type composites
	public static final String ENTITY_ACCESS_TYPE = PREFIX + "entity_accessType";
	public static final String ENTITY_ATTRIBUTE_OVERRIDES = PREFIX + "entity_attributeOverrides";
	public static final String ENTITY_ATTRIBUTE_OVERRIDES_COLUMN = PREFIX + "entity_attributeOverridesColumn";
	public static final String ENTITY_CATALOG = PREFIX + "entity_catalog";
	public static final String ENTITY_INHERITANCE_STRATEGY = PREFIX + "entity_inheritanceStrategy";
	public static final String ENTITY_INHERITANCE_DISCRIMINATOR_TYPE = PREFIX + "entity_inheritanceDiscriminatorType";
	public static final String ENTITY_INHERITANCE_DISCRIMINATOR_COLUMN = PREFIX + "entity_inheritanceDiscriminatorColumn";
	public static final String ENTITY_INHERITANCE_DISCRIMINATOR_VALUE = PREFIX + "entity_inheritanceDiscriminatorValue";
	public static final String ENTITY_NAME = PREFIX + "entity_name";
	public static final String ENTITY_ORM_PACKAGE = PREFIX + "orm_package";
	public static final String ENTITY_ORM_SCHEMA = PREFIX + "orm_schema";
	public static final String ENTITY_ORM_CATALOG = PREFIX + "orm_catalog";
	public static final String ENTITY_ORM_DELIMITED_IDENTIFIERS = PREFIX + "orm_delimited_identifiers";
	public static final String ENTITY_ORM_CASCADE = PREFIX + "orm_cascade";
	public static final String ENTITY_ORM_XML = PREFIX + "orm_xml";
	public static final String ENTITY_SCHEMA = PREFIX + "entity_schema";
	public static final String ENTITY_TABLE = PREFIX + "entity_table";
	public static final String ENTITY_CACHEABLE = PREFIX + "entity_cacheable";

	//Persistent Attribute composites
	public static final String MAPPING_CASCADE_TYPE = PREFIX + "mapping_cascadeType";
	public static final String MAPPING_COLUMN = PREFIX + "mapping_column";
	public static final String MAPPING_COLUMN_INSERTABLE = PREFIX + "mapping_columnInsertable";
	public static final String MAPPING_COLUMN_LENGTH = PREFIX + "mapping_columnLength";
	public static final String MAPPING_COLUMN_NULLABLE = PREFIX + "mapping_columnNullable";
	public static final String MAPPING_COLUMN_PRECISION = PREFIX + "mapping_columnPrecision";
	public static final String MAPPING_COLUMN_SCALE = PREFIX + "mapping_columnScale";
	public static final String MAPPING_COLUMN_TABLE = PREFIX + "mapping_columnTable";
	public static final String MAPPING_COLUMN_UNIQUE = PREFIX + "mapping_columnUnique";
	public static final String MAPPING_COLUMN_UPDATABLE= PREFIX + "mapping_columnUpdatable";
	public static final String MAPPING_EMBEDDED_ATTRIBUTE_OVERRIDES = PREFIX + "mapping_embeddedAttributeOverrides";
	public static final String MAPPING_EMBEDDED_ATTRIBUTE_OVERRIDES_COLUMN = PREFIX + "mapping_embeddedAttributeOverridesColumn";
	public static final String MAPPING_ENUMERATED = PREFIX + "mapping_enumerated";
	public static final String MAPPING_FETCH_TYPE = PREFIX + "mapping_fetchType";
	public static final String MAPPING_GENERATED_VALUE_STRATEGY = PREFIX + "mapping_generatedValueStrategy";
	public static final String MAPPING_GENERATED_VALUE_GENERATOR_NAME = PREFIX + "mapping_generatedValueGeneratorName";
	public static final String MAPPING_JOIN_COLUMN_NAME = PREFIX + "mapping_joinColumnName";
	public static final String MAPPING_JOIN_REFERENCED_COLUMN = PREFIX + "mapping_joinReferencedColumn";
	public static final String MAPPING_JOIN_TABLE_NAME = PREFIX + "mapping_joinTableName";
	public static final String MAPPING_JOIN_TABLE_SCHEMA = PREFIX + "mapping_joinTableSchema";
	public static final String MAPPING_JOIN_TABLE_CATALOG = PREFIX + "mapping_joinTableCatalog";
	public static final String MAPPING_JOIN_TABLE_COLUMNS = PREFIX + "mapping_joinTableJoinColumns";
	public static final String MAPPING_JOIN_TABLE_INVERSE_JOIN_COLUMNS = PREFIX + "mapping_joinTableInverseJoinColumns";
	public static final String MAPPING_LOB = PREFIX + "mapping_lob";
	public static final String MAPPING_MAP_AS = PREFIX + "mapping_mapAs";
	public static final String MAPPING_MAPPED_BY = PREFIX + "mapping_mappedBy";
	public static final String MAPPING_NAMED_NATIVE_QUERIES = "named_native_queries";
	public static final String MAPPING_NAMED_QUERIES = PREFIX + "named_queries";
	public static final String MAPPING_OPTIONAL = PREFIX + "mapping_optional";
	public static final String MAPPING_ORDER_BY = PREFIX + "mapping_orderBy";
	public static final String MAPPING_ORDER_BY_NO_ORDERING = PREFIX + "mapping_orderByNoOrdering";
	public static final String MAPPING_ORDER_BY_ORDERING = PREFIX + "mapping_orderByOrdering";
	public static final String MAPPING_ORDER_COLUMN_ORDERING = PREFIX + "mapping_orderColumnOrdering";
	public static final String MAPPING_ORDER_COLUMN_ORDERING_COLUMN = PREFIX + "mapping_orderColumnOrderingColumn";
	public static final String MAPPING_PRIMARY_KEY_GENERATION = PREFIX + "mapping_primaryKeyGeneration";
	public static final String MAPPING_SEQUENCE_GENERATOR = PREFIX + "mapping_sequenceGenerator";
	public static final String MAPPING_SEQUENCE_GENERATOR_NAME = PREFIX + "mapping_sequenceGeneratorName";
	public static final String MAPPING_SEQUENCE_GENERATOR_SEQUENCE = PREFIX + "mapping_sequenceGeneratorSequence";
	public static final String MAPPING_TABLE_GENERATOR = PREFIX + "mapping_tableGenerator";
	public static final String MAPPING_TABLE_GENERATOR_CATALOG = PREFIX + "mapping_tableGeneratorCatalog";
	public static final String MAPPING_TABLE_GENERATOR_NAME = PREFIX + "mapping_tableGeneratorName";
	public static final String MAPPING_TABLE_GENERATOR_PRIMARY_KEY_COLUMN = PREFIX + "mapping_tableGeneratorPrimaryKeyColumn";
	public static final String MAPPING_TABLE_GENERATOR_PRIMARY_KEY_COLUMN_VALUE = PREFIX + "mapping_tableGeneratorPrimaryKeyColumnValue";
	public static final String MAPPING_TABLE_GENERATOR_SCHEMA = PREFIX + "mapping_tableGeneratorSchema";
	public static final String MAPPING_TABLE_GENERATOR_TABLE= PREFIX + "mapping_tableGeneratorTable";
	public static final String MAPPING_TABLE_GENERATOR_VALUE_COLUMN = PREFIX + "mapping_tableGeneratorValueColumn";
	public static final String MAPPING_TARGET_ENTITY = PREFIX + "mapping_targetEntity";
	public static final String MAPPING_TEMPORAL = PREFIX + "mapping_temporal";

	//Project properties
	public static final String PROPERTIES_JAVA_PERSISTENCE = PREFIX + "properties_javaPersistence";
	public static final String PROPERTIES_JAVA_PERSISTENCE_CONNECTION = PREFIX + "properties_javaPersistenceConnection";
	public static final String PROPERTIES_JAVA_PERSISTENCE_SCHEMA = PREFIX + "properties_javaPersistenceSchema";
	public static final String PROPERTIES_JAVA_PERSISTENCE_METAMODEL = PREFIX + "properties_canonicalMetamodel";
	
	//Dialogs, Wizards
	public static final String DIALOG_CREATE_ORM = PREFIX + "dialog_createORM";
	public static final String DIALOG_EDIT_INVERSE_JOIN_COLUNN = PREFIX + "dialog_editInverseJoinColumn";
	public static final String DIALOG_GENERATE_ENTITIES = PREFIX + "dialog_generateEntities";
	public static final String DIALOG_GENERATE_ENTITIES_SOURCE = PREFIX + "dialog_generateEntities_source";
	public static final String DIALOG_GENERATE_ENTITIES_PACKAGE = PREFIX + "dialog_generateEntities_package";
	public static final String DIALOG_GENERATE_ENTITIES_TABLES = PREFIX + "dialog_generateEntities_tables";
	public static final String DIALOG_JPA_FACET = PREFIX + "dialog_JPAFacet";
	public static final String DIALOG_JPA_PLATFORM = PREFIX + "dialog_JPAPlatform";
	
	//New JPA Project wizard:
	public static final String NEW_JPA_PROJECT = PREFIX + "dialog_newJPAProject";
 	public static final String NEW_JPA_PROJECT_CONTENT_PAGE_CLASSPATH = PREFIX + "dialog_addJavaPersistence_classpath";
 	public static final String NEW_JPA_PROJECT_CONTENT_PAGE_DATABASE = PREFIX + "dialog_addJavaPersistence_database";
 	public static final String NEW_JPA_PROJECT_CONTENT_PAGE_PACKAGING = PREFIX + "dialog_addJavaPersistence_packaging";
 	public static final String NEW_JPA_PROJECT_CREATION_PAGE = PREFIX + "dialog_addJavaPersistence";
	public static final String NEW_JPA_PROJECT_JPA_FACET = PREFIX + "dialog_newJPAProjectFacet";

	//Other
	public static final String PERSISTENCE_OUTLINE = PREFIX + "persistenceOutline";

	//Persistence Xml Editor
	public static final String PERSISTENCE_XML_CONNECTION = PREFIX + "persistence_connection";
	public static final String PERSISTENCE_XML_GENERAL = PREFIX + "persistence_general";
	public static final String PERSISTENCE_XML_PROPERTIES  = PREFIX + "persistence_properties";
	public static final String PERSISTENCE_XML_SOURCE  = PREFIX + "persistence_source";

	//New JPA Entity wizard
	public static final String NEW_JPA_ENTITY_ENTITY_CLASS  = PREFIX + "dialog_entityClassPage";
	public static final String NEW_JPA_ENTITY_ENTITY_PROPERTIES  = PREFIX + "dialog_entityPropertiesPage";
	
	//Generate Entities Wizard
	public static final String GENERATE_ENTITIES_WIZARD_ASSOCIATION_CARDINALITY = PREFIX + "dialog_associationCardinalityPage";
	public static final String GENERATE_ENTITIES_WIZARD_ASSOCIATION_TABLES = PREFIX + "dialog_associationTablesPage";
	public static final String GENERATE_ENTITIES_WIZARD_CUSTOMIZE_DEFAULT_ENTITY_GENERATION = PREFIX + "dialog_customizeDefaultEntityGeneration";
	public static final String GENERATE_ENTITIES_WIZARD_CUSTOMIZE_INDIVIDUAL_ENTITIES = PREFIX + "dialog_customizeIndividualEntities";
	public static final String GENERATE_ENTITIES_WIZARD_JOIN_COLUMNS = PREFIX + "dialog_joinColumnsPage";
	public static final String GENERATE_ENTITIES_WIZARD_SELECT_CASCADE = PREFIX + "dialog_selectCascade";
	public static final String GENERATE_ENTITIES_WIZARD_SELECT_TABLES = PREFIX + "dialog_selectTablesPage";
	public static final String GENERATE_ENTITIES_WIZARD_TABLE_ASSOCIATIONS = PREFIX + "dialog_tableAssociationsPage";

	public static final String MAPPING_COLLECTION_TABLE_NAME = PREFIX + "mapping_collectionTableName";
	public static final String MAPPING_COLLECTION_TABLE_SCHEMA = PREFIX + "mapping_collectionTableSchema";
	public static final String MAPPING_COLLECTION_TABLE_CATALOG = PREFIX + "mapping_collectionTableCatalog";
	
	public static final String MAPPING_ELEMENT_COLLECTION_TARGET_CLASS = PREFIX + "mapping_elementCollectionTargetClass";
}
