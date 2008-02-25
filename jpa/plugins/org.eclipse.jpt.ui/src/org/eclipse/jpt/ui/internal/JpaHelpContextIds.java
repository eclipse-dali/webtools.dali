/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal;

import org.eclipse.jpt.ui.JptUiPlugin;


/**
 * Help context ids for the Dali JPA UI.
 * <p>
 * This interface contains constants only; it is not intended to be
 * implemented.
 * </p>
 *  
 */
public interface JpaHelpContextIds {


	//ContextID prefix
	public static final String PREFIX = JptUiPlugin.PLUGIN_ID + ".";  //$NON-NLS-1$
	
	//Persistent Type composites
	public static final String ENTITY_ACCESS_TYPE = PREFIX + "entity_accessType"; //$NON-NLS-1$
	public static final String ENTITY_ATTRIBUTE_OVERRIDES = PREFIX + "entity_attributeOverrides"; //$NON-NLS-1$
	public static final String ENTITY_ATTRIBUTE_OVERRIDES_COLUMN = PREFIX + "entity_attributeOverridesColumn"; //$NON-NLS-1$
	public static final String ENTITY_CATALOG = PREFIX + "entity_catalog"; //$NON-NLS-1$
	public static final String ENTITY_INHERITANCE_STRATEGY = PREFIX + "entity_inheritanceStrategy"; //$NON-NLS-1$
	public static final String ENTITY_INHERITANCE_DISCRIMINATOR_TYPE = PREFIX + "entity_inheritanceDiscriminatorType"; //$NON-NLS-1$
	public static final String ENTITY_INHERITANCE_DISCRIMINATOR_COLUMN = PREFIX + "entity_inheritanceDiscriminatorColumn"; //$NON-NLS-1$
	public static final String ENTITY_INHERITANCE_DISCRIMINATOR_VALUE = PREFIX + "entity_inheritanceDiscriminatorValue"; //$NON-NLS-1$
	public static final String ENTITY_NAME = PREFIX + "entity_name"; //$NON-NLS-1$
	public static final String ENTITY_ORM_PACKAGE = PREFIX + "orm_package"; //$NON-NLS-1$
	public static final String ENTITY_ORM_SCHEMA = PREFIX + "orm_schema"; //$NON-NLS-1$
	public static final String ENTITY_ORM_CATALOG = PREFIX + "orm_catalog"; //$NON-NLS-1$
	public static final String ENTITY_ORM_ACCESS = PREFIX + "orm_access"; //$NON-NLS-1$
	public static final String ENTITY_ORM_CASCADE = PREFIX + "orm_cascade"; //$NON-NLS-1$
	public static final String ENTITY_ORM_XML = PREFIX + "orm_xml"; //$NON-NLS-1$
	public static final String ENTITY_SCHEMA = PREFIX + "entity_schema"; //$NON-NLS-1$
	public static final String ENTITY_TABLE = PREFIX + "entity_table"; //$NON-NLS-1$

	
	//Persistent Attribute composites
	public static final String MAPPING_CASCADE_TYPE = PREFIX + "mapping_cascadeType"; //$NON-NLS-1$
	public static final String MAPPING_COLUMN = PREFIX + "mapping_column"; //$NON-NLS-1$
	public static final String MAPPING_COLUMN_INSERTABLE = PREFIX + "mapping_columnInsertable"; //$NON-NLS-1$
	public static final String MAPPING_COLUMN_TABLE = PREFIX + "mapping_columnTable"; //$NON-NLS-1$
	public static final String MAPPING_COLUMN_UPDATABLE= PREFIX + "mapping_columnUpdatable"; //$NON-NLS-1$
	public static final String MAPPING_EMBEDDED_ATTRIBUTE_OVERRIDES = PREFIX + "mapping_embeddedAttributeOverrides";//$NON-NLS-1$
	public static final String MAPPING_EMBEDDED_ATTRIBUTE_OVERRIDES_COLUMN = PREFIX + "mapping_embeddedAttributeOverridesColumn";//$NON-NLS-1$
	public static final String MAPPING_ENUMERATED = PREFIX + "mapping_enumerated"; //$NON-NLS-1$
	public static final String MAPPING_FETCH_TYPE = PREFIX + "mapping_fetchType"; //$NON-NLS-1$
	public static final String MAPPING_GENERATED_VALUE_STRATEGY = PREFIX + "mapping_generatedValueStrategy"; //$NON-NLS-1$
	public static final String MAPPING_GENERATED_VALUE_GENERATOR_NAME = PREFIX + "mapping_generatedValueGeneratorName"; //$NON-NLS-1$
	public static final String MAPPING_JOIN_COLUMN_NAME = PREFIX + "mapping_joinColumnName"; //$NON-NLS-1$
	public static final String MAPPING_JOIN_REFERENCED_COLUMN = PREFIX + "mapping_joinReferencedColumn"; //$NON-NLS-1$
	public static final String MAPPING_JOIN_TABLE_NAME = PREFIX + "mapping_joinTableName"; //$NON-NLS-1$
	public static final String MAPPING_JOIN_TABLE_COLUMNS = PREFIX + "mapping_joinTableJoinColumns"; //$NON-NLS-1$
	public static final String MAPPING_JOIN_TABLE_INVERSE_JOIN_COLUMNS = PREFIX + "mapping_joinTableInverseJoinColumns"; //$NON-NLS-1$
	public static final String MAPPING_LOB = PREFIX + "mapping_lob"; //$NON-NLS-1$
	public static final String MAPPING_MAP_AS = PREFIX + "mapping_mapAs"; //$NON-NLS-1$
	public static final String MAPPING_MAPPED_BY = PREFIX + "mapping_mappedBy"; //$NON-NLS-1$
	public static final String MAPPING_OPTIONAL = PREFIX + "mapping_optional"; //$NON-NLS-1$
	public static final String MAPPING_ORDER_BY = PREFIX + "mapping_orderBy"; //$NON-NLS-1$
	public static final String MAPPING_ORDER_BY_NO_ORDERING = PREFIX + "mapping_orderByNoOrdering"; //$NON-NLS-1$
	public static final String MAPPING_ORDER_BY_PRIMARY_KEY_ORDERING = PREFIX + "mapping_orderByPrimaryKeyOrdering"; //$NON-NLS-1$
	public static final String MAPPING_ORDER_BY_CUSTOM_ORDERING = PREFIX + "mapping_orderByCustomOrdering"; //$NON-NLS-1$
	public static final String MAPPING_PRIMARY_KEY_GENERATION = PREFIX + "mapping_primaryKeyGeneration"; //$NON-NLS-1$
	public static final String MAPPING_SEQUENCE_GENERATOR = PREFIX + "mapping_sequenceGenerator"; //$NON-NLS-1$
	public static final String MAPPING_SEQUENCE_GENERATOR_NAME = PREFIX + "mapping_sequenceGeneratorName"; //$NON-NLS-1$ 
	public static final String MAPPING_SEQUENCE_GENERATOR_SEQUENCE = PREFIX + "mapping_sequenceGeneratorSequence"; //$NON-NLS-1$ 
	public static final String MAPPING_TABLE_GENERATOR = PREFIX + "mapping_tableGenerator"; //$NON-NLS-1$
	public static final String MAPPING_TABLE_GENERATOR_NAME = PREFIX + "mapping_tableGeneratorName"; //$NON-NLS-1$
	public static final String MAPPING_TABLE_GENERATOR_PRIMARY_KEY_COLUMN = PREFIX + "mapping_tableGeneratorPrimaryKeyColumn"; //$NON-NLS-1$
	public static final String MAPPING_TABLE_GENERATOR_PRIMARY_KEY_COLUMN_VALUE = PREFIX + "mapping_tableGeneratorPrimaryKeyColumnValue"; //$NON-NLS-1$
	public static final String MAPPING_TABLE_GENERATOR_TABLE= PREFIX + "mapping_tableGeneratorTable"; //$NON-NLS-1$
	public static final String MAPPING_TABLE_GENERATOR_VALUE_COLUMN = PREFIX + "mapping_tableGeneratorValueColumn"; //$NON-NLS-1$
	public static final String MAPPING_TARGET_ENTITY = PREFIX + "mapping_targetEntity"; //$NON-NLS-1$
	public static final String MAPPING_TEMPORAL = PREFIX + "mapping_temporal"; //$NON-NLS-1$

	//Project properties
	public static final String PROPERTIES_JAVA_PERSISTENCE = PREFIX + "properties_javaPersistence"; //$NON-NLS-1$
	public static final String PROPERTIES_JAVA_PERSISTENCE_CONNECTION = PREFIX + "properties_javaPersistenceConnection"; //$NON-NLS-1$
	public static final String PROPERTIES_JAVA_PERSISTENCE_SCHEMA = PREFIX + "properties_javaPersistenceSchema"; //$NON-NLS-1$
	
	//Dialogs, Wizards
	public static final String DIALOG_CREATE_ORM = PREFIX + "dialog_createORM"; //$NON-NLS-1$
	public static final String DIALOG_EDIT_INVERSE_JOIN_COLUNN = PREFIX + "dialog_editInverseJoinColumn"; //$NON-NLS-1$
	public static final String DIALOG_GENERATE_ENTITIES = PREFIX + "dialog_generateEntities"; //$NON-NLS-1$
	public static final String DIALOG_GENERATE_ENTITIES_SOURCE = PREFIX + "dialog_generateEntities_source"; //$NON-NLS-1$
	public static final String DIALOG_GENERATE_ENTITIES_PACKAGE = PREFIX + "dialog_generateEntities_package"; //$NON-NLS-1$
	public static final String DIALOG_GENERATE_ENTITIES_TABLES = PREFIX + "dialog_generateEntities_tables"; //$NON-NLS-1$
	public static final String DIALOG_JPA_FACET = PREFIX + "dialog_JPAFacet"; //$NON-NLS-1$
	public static final String DIALOG_JPA_PLATFORM = PREFIX + "dialog_JPAPlatform"; //$NON-NLS-1$
	public static final String NEW_JPA_PROJECT = PREFIX + "dialog_newJPAProject"; //$NON-NLS-1$
 	public static final String NEW_JPA_PROJECT_CONTENT_PAGE_CLASSPATH = PREFIX + "dialog_addJavaPersistence_classpath"; //$NON-NLS-1$
 	public static final String NEW_JPA_PROJECT_CONTENT_PAGE_DATABASE = PREFIX + "dialog_addJavaPersistence_database"; //$NON-NLS-1$
 	public static final String NEW_JPA_PROJECT_CONTENT_PAGE_PACKAGING = PREFIX + "dialog_addJavaPersistence_packaging"; //$NON-NLS-1$
 	public static final String NEW_JPA_PROJECT_CREATION_PAGE = PREFIX + "dialog_addJavaPersistence"; //$NON-NLS-1$
	
	//Other
	public static final String PERSISTENCE_OUTLINE = PREFIX + "persistenceOutline"; //$NON-NLS-1$

	
}
