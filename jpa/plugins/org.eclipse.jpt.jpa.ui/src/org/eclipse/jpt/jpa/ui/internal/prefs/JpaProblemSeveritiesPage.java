/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.prefs;

import org.eclipse.core.resources.IProject;
import org.eclipse.jpt.common.ui.internal.JptUIPlugin;
import org.eclipse.jpt.common.ui.internal.prefs.JptProblemSeveritiesPage;
import org.eclipse.jpt.jpa.core.JpaPreferences;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.prefs.validation.JptJpaUiPreferencesValidationMessages;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;

public class JpaProblemSeveritiesPage
	extends JptProblemSeveritiesPage
{
	/**
	 * The unique identifier for this page when it is shown in the workspace
	 * preferences dialog.
	 */
	private static final String PREFERENCE_PAGE_ID = "org.eclipse.jpt.jpa.ui.jpaProblemSeveritiesPreferences"; //$NON-NLS-1$

	/**
	 * The unique identifier for this page when it is shown in the project
	 * properties dialog.
	 */
	private static final String PROPERTY_PAGE_ID = "org.eclipse.jpt.jpa.ui.jpaProblemSeveritiesProperties"; //$NON-NLS-1$


	public JpaProblemSeveritiesPage() {
		super();
	}

	@Override
	protected JptUIPlugin getUIPlugin() {
		return JptJpaUiPlugin.instance();
	}

	@Override
	protected String getPreferencePageID() {
		return PREFERENCE_PAGE_ID;
	}

	@Override
	protected String getPropertyPageID() {
		return PROPERTY_PAGE_ID;
	}

	@Override
	public void init(IWorkbench workbench) {
		this.setDescription(JptJpaUiMessages.JpaProblemSeveritiesPage_Description);
	}

	@Override
	protected void addCombos(Composite parent) {
		this.addProjectCategory(parent);
		this.addPersistenceUnitCategory(parent);
		this.addTypeCategory(parent);
		this.addAttributeCategory(parent);
		this.addDatabaseCategory(parent);
		this.addInheritanceCategory(parent);
		this.addQueriesGeneratorsCategory(parent);
	}

	private void addProjectCategory(Composite parent) {
		parent = this.addExpandableSection(parent, JptJpaUiPreferencesValidationMessages.PROJECT_LEVEL_CATEGORY);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.NO_JPA_PROJECT);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENCE_MULTIPLE_PERSISTENCE_UNITS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENCE_NO_PERSISTENCE_UNIT);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENCE_XML_INVALID_CONTENT);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENCE_XML_UNSUPPORTED_CONTENT); //3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PROJECT_MULTIPLE_PERSISTENCE_XML);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PROJECT_INACTIVE_CONNECTION);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PROJECT_INVALID_CONNECTION);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PROJECT_INVALID_LIBRARY_PROVIDER); //3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PROJECT_NO_CONNECTION);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PROJECT_NO_PERSISTENCE_XML);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.XML_VERSION_NOT_LATEST);
	}

	private void addPersistenceUnitCategory(Composite parent) {
		parent = this.addExpandableSection(parent, JptJpaUiPreferencesValidationMessages.PERSISTENCE_UNIT_LEVEL_CATEGORY);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.MAPPING_FILE_EXTRANEOUS_PERSISTENCE_UNIT_METADATA);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENT_TYPE_DUPLICATE_CLASS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENCE_UNIT_DUPLICATE_CLASS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENCE_UNIT_DUPLICATE_JAR_FILE);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENCE_UNIT_DUPLICATE_MAPPING_FILE);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENCE_UNIT_INVALID_CLASS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENCE_UNIT_INVALID_MAPPING_FILE);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENCE_UNIT_JAR_FILE_DEPLOYMENT_PATH_WARNING);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENCE_UNIT_NONEXISTENT_CLASS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENCE_UNIT_LISTED_CLASS_IS_AN_INTERFACE);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENCE_UNIT_LISTED_CLASS_IS_AN_ENUM);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENCE_UNIT_NONEXISTENT_JAR_FILE);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENCE_UNIT_NONEXISTENT_MAPPING_FILE);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENCE_UNIT_REDUNDANT_CLASS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENCE_UNIT_UNSPECIFIED_CLASS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENCE_UNIT_UNSPECIFIED_JAR_FILE);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENCE_UNIT_UNSPECIFIED_MAPPING_FILE);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENCE_UNIT_UNSUPPORTED_MAPPING_FILE_CONTENT);
	}

	private void addTypeCategory(Composite parent) {
		parent = this.addExpandableSection(parent, JptJpaUiPreferencesValidationMessages.TYPE_LEVEL_CATEGORY);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ENTITY_NAME_DUPLICATED); //3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ENTITY_NAME_MISSING); //3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ENTITY_NO_PK);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ENTITY_NON_ROOT_ID_ATTRIBUTE_SPECIFIED); //3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ENTITY_NON_ROOT_ID_CLASS_SPECIFIED); //3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_ANNOTATED_BUT_NOT_LISTED_IN_PERSISTENCE_XML);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MANAGED_BUT_NOT_LISTED_IN_PERSISTENCE_XML);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.MANAGED_TYPE_UNRESOLVED_CLASS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.MANAGED_TYPE_UNSPECIFIED_CLASS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_CLASS_MISSING_NO_ARG_CONSTRUCTOR);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_CLASS_PRIVATE_NO_ARG_CONSTRUCTOR);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_FINAL_CLASS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_ID_AND_EMBEDDED_ID_BOTH_USED);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_AND_EMBEDDED_ID_BOTH_USED);	//3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_ATTRIBUTE_DOES_NOT_EXIST);	//3.2
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_ATTRIBUTE_MAPPING_DUPLICATE_MATCH);	//3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_ATTRIBUTE_MAPPING_NO_MATCH);	//3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_ATTRIBUTE_NO_MATCH);	//3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_ATTRIBUTE_NOT_PRIMARY_KEY);	//3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_ATTRIBUTE_TYPE_DOES_NOT_AGREE);	//3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_MISSING_EQUALS_METHOD);	//3.2
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_MISSING_HASHCODE_METHOD); //3.2
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_MISSING_NO_ARG_CONSTRUCTOR); //3.2
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_MISSING_PUBLIC_NO_ARG_CONSTRUCTOR); //3.2
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_NAME_EMPTY);	//3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_NOT_EXIST);	//3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_NOT_IMPLEMENT_SERIALIZABLE);	//3.2
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_NOT_PUBLIC);	//3.2
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_PROPERTY_METHOD_NOT_PUBLIC);	//3.2
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_REDEFINED);	//3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_REQUIRED);	//3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_WITH_MAPS_ID);	//3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_MAPS_ID_ATTRIBUTE_TYPE_DOES_NOT_AGREE);	//3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_MEMBER_CLASS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_MULTIPLE_EMBEDDED_ID);	//3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_PK_REDEFINED_ID_ATTRIBUTE);	//3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_PK_REDEFINED_ID_CLASS);	//3.0 M7
	}

	private void addAttributeCategory(Composite parent) {
		parent = this.addExpandableSection(parent, JptJpaUiPreferencesValidationMessages.ATTRIBUTE_LEVEL_CATEGORY);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ELEMENT_COLLECTION_TARGET_CLASS_MUST_BE_EMBEDDABLE_OR_BASIC_TYPE);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ELEMENT_COLLECTION_TARGET_CLASS_DOES_NOT_EXIST); //3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ELEMENT_COLLECTION_TARGET_CLASS_NOT_DEFINED);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ELEMENT_COLLECTION_CONTAINS_EMBEDDABLE_WITH_ELEMENT_COLLECTION_MAPPING);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ELEMENT_COLLECTION_CONTAINS_EMBEDDABLE_WITH_PROHIBITED_RELATIONSHIP_MAPPING);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.EMBEDDED_ID_CLASS_SHOULD_BE_PUBLIC);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.EMBEDDED_ID_CLASS_SHOULD_IMPLEMENT_EQUALS_HASHCODE);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.EMBEDDED_ID_CLASS_SHOULD_IMPLEMENT_NO_ARG_CONSTRUCTOR);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.EMBEDDED_ID_CLASS_SHOULD_IMPLEMENT_SERIALIZABLE);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.EMBEDDED_ID_CLASS_SHOULD_NOT_CONTAIN_RELATIONSHIP_MAPPINGS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.EMBEDDED_ID_MAPPING_MAPPED_BY_RELATIONSHIP_AND_ATTRIBUTE_OVERRIDES_SPECIFIED); //3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ID_MAPPING_MAPPED_BY_RELATIONSHIP_AND_COLUMN_SPECIFIED); 	//3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.MAPS_ID_VALUE_INVALID);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.MAPS_ID_VALUE_NOT_RESOLVED);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.MAPS_ID_VALUE_NOT_SPECIFIED);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.MAPPING_INVALID_MAPPED_BY);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.MAPPING_MAPPED_BY_ON_BOTH_SIDES);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.MAPPING_UNRESOLVED_MAPPED_BY);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ORDER_COLUMN_AND_ORDER_BY_BOTH_SPECIFIED);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENT_ATTRIBUTE_ELEMENT_COLLECTION_INVALID_VALUE_TYPE); //3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENT_ATTRIBUTE_FINAL_FIELD);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENT_ATTRIBUTE_FINAL_GETTER);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENT_ATTRIBUTE_INHERITED_ATTRIBUTES_NOT_SUPPORTED);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENT_ATTRIBUTE_INVALID_MAPPING);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENT_ATTRIBUTE_INVALID_TEMPORAL_MAPPING_TYPE); //3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENT_ATTRIBUTE_INVALID_TEMPORAL_MAP_KEY_TYPE);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENT_ATTRIBUTE_INVALID_VERSION_MAPPING_TYPE); //3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENT_ATTRIBUTE_PUBLIC_FIELD);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENT_ATTRIBUTE_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENT_ATTRIBUTE_UNSPECIFIED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TARGET_ENTITY_IS_NOT_AN_ENTITY);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TARGET_ENTITY_NOT_DEFINED);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TARGET_ENTITY_NOT_EXIST);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TARGET_NOT_AN_EMBEDDABLE); //3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.MAP_KEY_CLASS_NOT_DEFINED);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.MAP_KEY_CLASS_NOT_EXIST);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.MAP_KEY_CLASS_MUST_BE_ENTITY_EMBEDDABLE_OR_BASIC_TYPE);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ATTRIBUTE_TYPE_IS_NOT_SUPPORTED_COLLECTION_TYPE);

		parent = this.addSubExpandableSection(parent, JptJpaUiPreferencesValidationMessages.IMPLIED_ATTRIBUTE_LEVEL_CATEGORY);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ELEMENT_COLLECTION_TARGET_CLASS_DOES_NOT_EXIST);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ELEMENT_COLLECTION_TARGET_CLASS_MUST_BE_EMBEDDABLE_OR_BASIC_TYPE);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ELEMENT_COLLECTION_TARGET_CLASS_NOT_DEFINED);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ELEMENT_COLLECTION_CONTAINS_EMBEDDABLE_WITH_ELEMENT_COLLECTION_MAPPING);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ELEMENT_COLLECTION_CONTAINS_EMBEDDABLE_WITH_PROHIBITED_RELATIONSHIP_MAPPING);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_EMBEDDED_ID_CLASS_SHOULD_BE_PUBLIC);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_EMBEDDED_ID_CLASS_SHOULD_IMPLEMENT_EQUALS_HASHCODE);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_EMBEDDED_ID_CLASS_SHOULD_IMPLEMENT_NO_ARG_CONSTRUCTOR);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_EMBEDDED_ID_CLASS_SHOULD_IMPLEMENT_SERIALIZABLE);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_EMBEDDED_ID_CLASS_SHOULD_NOT_CONTAIN_RELATIONSHIP_MAPPINGS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_EMBEDDED_ID_MAPPING_MAPPED_BY_RELATIONSHIP_AND_ATTRIBUTE_OVERRIDES_SPECIFIED);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_TARGET_ENTITY_IS_NOT_AN_ENTITY);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_TARGET_ENTITY_NOT_DEFINED);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_TARGET_NOT_AN_EMBEDDABLE);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_CLASS_NOT_DEFINED);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_CLASS_MUST_BE_ENTITY_EMBEDDABLE_OR_BASIC_TYPE);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_INVALID_TEMPORAL_MAP_KEY_TYPE);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ELEMENT_COLLECTION_INVALID_VALUE_TYPE);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_INVALID_TEMPORAL_MAPPING_TYPE);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ATTRIBUTE_TYPE_IS_NOT_SUPPORTED_COLLECTION_TYPE);
	}

	private void addDatabaseCategory(Composite parent) {
		parent = this.addExpandableSection(parent, JptJpaUiPreferencesValidationMessages.DATABASE_CATEGORY);
		parent.setLayout(new GridLayout(1, false));
		this.addTableCategory(parent);
		this.addColumnCategory(parent);
		this.addOverridesCategory(parent);
	}

	private void addTableCategory(Composite parent) {
		parent = this.addExpandableSection(parent, JptJpaUiPreferencesValidationMessages.TABLE_CATEGORY);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.COLLECTION_TABLE_UNRESOLVED_CATALOG);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.COLLECTION_TABLE_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.COLLECTION_TABLE_UNRESOLVED_SCHEMA);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.JOIN_TABLE_UNRESOLVED_CATALOG);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.JOIN_TABLE_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.JOIN_TABLE_UNRESOLVED_SCHEMA);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.SECONDARY_TABLE_UNRESOLVED_CATALOG);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.SECONDARY_TABLE_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.SECONDARY_TABLE_UNRESOLVED_SCHEMA);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TABLE_UNRESOLVED_CATALOG);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TABLE_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TABLE_UNRESOLVED_SCHEMA);

		parent = this.addSubExpandableSection(parent, JptJpaUiPreferencesValidationMessages.IMPLIED_ATTRIBUTE_LEVEL_CATEGORY);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_COLLECTION_TABLE_UNRESOLVED_CATALOG);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_COLLECTION_TABLE_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_COLLECTION_TABLE_UNRESOLVED_SCHEMA);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_TABLE_UNRESOLVED_CATALOG);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_TABLE_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_TABLE_UNRESOLVED_SCHEMA);
	}

	private void addColumnCategory(Composite parent) {
		parent = this.addExpandableSection(parent, JptJpaUiPreferencesValidationMessages.COLUMN_CATEGORY);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.COLUMN_TABLE_NOT_VALID);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.COLUMN_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.COLUMN_UNRESOLVED_TABLE);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.INVERSE_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.INVERSE_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.INVERSE_JOIN_COLUMN_TABLE_NOT_VALID);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.INVERSE_JOIN_COLUMN_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.INVERSE_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.JOIN_COLUMN_TABLE_NOT_VALID);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.JOIN_COLUMN_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.MAP_KEY_COLUMN_TABLE_NOT_VALID);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.MAP_KEY_COLUMN_UNRESOLVED_NAME); // 3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ORDER_COLUMN_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PRIMARY_KEY_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PRIMARY_KEY_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PRIMARY_KEY_JOIN_COLUMN_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PRIMARY_KEY_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.MAP_KEY_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.MAP_KEY_JOIN_COLUMN_TABLE_NOT_VALID);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.MAP_KEY_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.MAP_KEY_JOIN_COLUMN_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.MAP_KEY_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME);

		parent = this.addSubExpandableSection(parent, JptJpaUiPreferencesValidationMessages.IMPLIED_ATTRIBUTE_LEVEL_CATEGORY);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_COLUMN_TABLE_NOT_VALID);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_COLUMN_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_INVERSE_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_INVERSE_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_INVERSE_JOIN_COLUMN_TABLE_NOT_VALID);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_INVERSE_JOIN_COLUMN_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_INVERSE_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_COLUMN_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_COLUMN_TABLE_NOT_VALID);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_COLUMN_TABLE_NOT_VALID);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_COLUMN_UNRESOLVED_NAME); // 3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ORDER_COLUMN_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_SECONDARY_TABLE_PRIMARY_KEY_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_SECONDARY_TABLE_PRIMARY_KEY_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_SECONDARY_TABLE_PRIMARY_KEY_JOIN_COLUMN_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_SECONDARY_TABLE_PRIMARY_KEY_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_PRIMARY_KEY_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_PRIMARY_KEY_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_PRIMARY_KEY_JOIN_COLUMN_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_PRIMARY_KEY_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_MAP_KEY_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_MAP_KEY_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_JOIN_COLUMN_TABLE_NOT_VALID);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_JOIN_COLUMN_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME);
	}

	private void addOverridesCategory(Composite parent) {
		parent = this.addExpandableSection(parent, JptJpaUiPreferencesValidationMessages.OVERRIDES_CATEGORY);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ASSOCIATION_OVERRIDE_INVALID_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ATTRIBUTE_OVERRIDE_INVALID_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ATTRIBUTE_OVERRIDE_INVALID_TYPE);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ATTRIBUTE_OVERRIDE_DERIVED_AND_SPECIFIED); //3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_INVALID_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_INVERSE_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_INVERSE_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_INVERSE_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_INVERSE_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_INVERSE_JOIN_COLUMN_TABLE_NOT_VALID);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_INVERSE_JOIN_COLUMN_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_INVERSE_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_TABLE_NOT_VALID);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_TABLE_UNRESOLVED_CATALOG);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_TABLE_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_TABLE_UNRESOLVED_SCHEMA);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_OVERRIDE_COLUMN_TABLE_NOT_VALID);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_OVERRIDE_COLUMN_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_OVERRIDE_INVALID_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_MAP_KEY_ATTRIBUTE_OVERRIDE_COLUMN_TABLE_NOT_VALID);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_MAP_KEY_ATTRIBUTE_OVERRIDE_INVALID_NAME);

		parent = this.addSubExpandableSection(parent, JptJpaUiPreferencesValidationMessages.IMPLIED_ATTRIBUTE_LEVEL_CATEGORY);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_INVALID_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_INVERSE_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_INVERSE_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_INVERSE_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_INVERSE_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_INVERSE_JOIN_COLUMN_REFERENCED_COLUMN_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_INVERSE_JOIN_COLUMN_TABLE_NOT_VALID);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_INVERSE_JOIN_COLUMN_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_JOIN_COLUMN_REFERENCED_COLUMN_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_JOIN_COLUMN_TABLE_NOT_VALID);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_JOIN_COLUMN_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ATTRIBUTE_OVERRIDE_COLUMN_TABLE_NOT_VALID);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ATTRIBUTE_OVERRIDE_COLUMN_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ATTRIBUTE_OVERRIDE_INVALID_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_ATTRIBUTE_OVERRIDE_COLUMN_TABLE_NOT_VALID);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_ATTRIBUTE_OVERRIDE_INVALID_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_ATTRIBUTE_OVERRIDE_COLUMN_UNRESOLVED_NAME); //3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_MAP_KEY_ATTRIBUTE_OVERRIDE_COLUMN_UNRESOLVED_NAME); //3.0 M7
	}

	private void addInheritanceCategory(Composite parent) {
		parent = this.addExpandableSection(parent, JptJpaUiPreferencesValidationMessages.INHERITANCE_CATEGORY);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.DISCRIMINATOR_COLUMN_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ENTITY_ABSTRACT_DISCRIMINATOR_VALUE_DEFINED);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ENTITY_ABSTRACT_TABLE_PER_CLASS_DEFINES_TABLE);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ENTITY_NON_ROOT_DISCRIMINATOR_COLUMN_DEFINED);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ENTITY_SINGLE_TABLE_DESCENDANT_DEFINES_TABLE);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ENTITY_TABLE_PER_CLASS_DISCRIMINATOR_COLUMN_DEFINED);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ENTITY_TABLE_PER_CLASS_DISCRIMINATOR_VALUE_DEFINED);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ENTITY_TABLE_PER_CLASS_NOT_PORTABLE_ON_PLATFORM);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ENTITY_TABLE_PER_CLASS_NOT_SUPPORTED_ON_PLATFORM);
	}

	private void addQueriesGeneratorsCategory(Composite parent) {
		parent = this.addExpandableSection(parent, JptJpaUiPreferencesValidationMessages.QUERIES_GENERATORS_CATEGORY);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.GENERATOR_DUPLICATE_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.GENERATOR_NAME_UNDEFINED); //3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.UNRESOLVED_GENERATOR_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.JPQL_QUERY_VALIDATION);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.QUERY_DUPLICATE_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.QUERY_NAME_UNDEFINED); //3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.QUERY_STATEMENT_UNDEFINED); //3.0 M7
	}


	// ********** plug-in preferences **********

	@Override
	protected boolean getWorkspaceValidationPreferencesOverridden(IProject project) {
		return JpaPreferences.getWorkspaceValidationOverridden(project);
	}

	@Override
	protected void setWorkspaceValidationPreferencesOverridden(IProject project, boolean value) {
		JpaPreferences.setWorkspaceValidationOverridden(project, value);
	}

	@Override
	protected int getValidationMessageSeverityPreference(String prefKey) {
		return JpaPreferences.getValidationMessageSeverity(prefKey);
	}

	@Override
	protected void setValidationMessageSeverityPreference(String prefKey, int value) {
		JpaPreferences.setValidationMessageSeverity(prefKey, value);
	}

	@Override
	protected int getValidationMessageSeverityPreference(IProject project, String prefKey) {
		return JpaPreferences.getValidationMessageSeverity(project, prefKey);
	}

	@Override
	protected void setValidationMessageSeverityPreference(IProject project, String prefKey, int value) {
		JpaPreferences.setValidationMessageSeverity(project, prefKey, value);
	}
}
