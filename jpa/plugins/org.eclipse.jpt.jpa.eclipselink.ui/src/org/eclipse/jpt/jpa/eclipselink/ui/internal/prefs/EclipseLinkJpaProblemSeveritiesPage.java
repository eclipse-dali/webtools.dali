/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.prefs;

import org.eclipse.core.resources.IProject;
import org.eclipse.jpt.common.ui.internal.JptUIPlugin;
import org.eclipse.jpt.common.ui.internal.prefs.JptProblemSeveritiesPage;
import org.eclipse.jpt.jpa.eclipselink.core.EclipseLinkJpaPreferences;
import org.eclipse.jpt.jpa.eclipselink.core.validation.JptJpaEclipseLinkCoreValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.ui.JptJpaEclipseLinkUiMessages;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.plugin.JptJpaEclipseLinkUiPlugin;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;
import org.eclipse.jpt.jpa.ui.prefs.validation.JptJpaUiPreferencesValidationMessages;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;

public class EclipseLinkJpaProblemSeveritiesPage
	extends JptProblemSeveritiesPage
{
	/**
	 * The unique identifier for this page when it is shown in the workspace
	 * preferences dialog.
	 */
	private static final String PREFERENCE_PAGE_ID = "org.eclipse.jpt.jpa.eclipselink.ui.jpaProblemSeveritiesPreferences"; //$NON-NLS-1$

	/**
	 * The unique identifier for this page when it is shown in the project
	 * properties dialog.
	 */
	private static final String PROPERTY_PAGE_ID = "org.eclipse.jpt.jpa.eclipselink.ui.jpaProblemSeveritiesProperties"; //$NON-NLS-1$


	public EclipseLinkJpaProblemSeveritiesPage() {
		super();
	}

	@Override
	protected JptUIPlugin getUIPlugin() {
		return JptJpaEclipseLinkUiPlugin.instance();
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
		this.setDescription(JptJpaUiMessages.JPA_PROBLEM_SEVERITIES_PAGE_DESCRIPTION);
	}

	@Override
	protected void addCombos(Composite parent) {
		this.addPersistenceUnitCategory(parent);
		this.addTypeCategory(parent);
		this.addAttributeCategory(parent);
		this.addQueriesGeneratorsCategory(parent);
		this.addMultitenancyCategory(parent);
		this.addVirtualCategory(parent);
	}

	private void addPersistenceUnitCategory(Composite parent) {
		parent = this.addExpandableSection(parent, JptJpaUiPreferencesValidationMessages.PERSISTENCE_UNIT_LEVEL_CATEGORY);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.EXCEPTION_HANDLER_CLASS_NOT_SPECIFIED);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.EXCEPTION_HANDLER_CLASS_NOT_EXIST);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.EXCEPTION_HANDLER_CLASS_NOT_VALID);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.EXCEPTION_HANDLER_CLASS_IMPLEMENTS_EXCEPTION_HANDLER);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.PERSISTENCE_UNIT_LEGACY_DESCRIPTOR_CUSTOMIZER);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.PERSISTENCE_UNIT_LEGACY_ENTITY_CACHING);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.PERSISTENCE_UNIT_CACHING_PROPERTY_IGNORED);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.SESSION_CUSTOMIZER_CLASS_NOT_SPECIFIED);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.SESSION_CUSTOMIZER_CLASS_NOT_EXIST);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.SESSION_CUSTOMIZER_CLASS_NOT_VALID);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.SESSION_CUSTOMIZER_CLASS_IMPLEMENTS_SESSION_CUSTOMIZER);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.SESSION_LOGGER_CLASS_NOT_SPECIFIED);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.SESSION_LOGGER_CLASS_NOT_EXIST);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.SESSION_LOGGER_CLASS_IMPLEMENTS_SESSION_LOG);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.SESSION_PROFILER_CLASS_NOT_SPECIFIED);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.SESSION_PROFILER_CLASS_NOT_EXIST);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.SESSION_PROFILER_CLASS_NOT_VALID);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.SESSION_PROFILER_CLASS_IMPLEMENTS_SESSION_PROFILER);
	}

	private void addTypeCategory(Composite parent) {
		parent = this.addExpandableSection(parent, JptJpaUiPreferencesValidationMessages.TYPE_LEVEL_CATEGORY);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.CACHE_EXPIRY_AND_EXPIRY_TIME_OF_DAY_BOTH_SPECIFIED);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.DESCRIPTOR_CUSTOMIZER_CLASS_NOT_SPECIFIED);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.DESCRIPTOR_CUSTOMIZER_CLASS_NOT_EXIST);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.DESCRIPTOR_CUSTOMIZER_CLASS_NOT_VALID);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.DESCRIPTOR_CUSTOMIZER_CLASS_IMPLEMENTS_DESCRIPTOR_CUSTOMIZER);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.TYPE_MAPPING_MEMBER_CLASS_NOT_STATIC);
	}

	private void addAttributeCategory(Composite parent) {
		parent = this.addExpandableSection(parent, JptJpaUiPreferencesValidationMessages.ATTRIBUTE_LEVEL_CATEGORY);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.CONVERTER_CLASS_IMPLEMENTS_CONVERTER);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.STRUCT_CONVERTER_CLASS_IMPLEMENTS_STRUCT_CONVERTER);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.CONVERTER_DUPLICATE_NAME);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.CONVERTER_NAME_UNDEFINED);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.RESERVED_CONVERTER_NAME);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.CONVERTER_CLASS_EXISTS);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.CONVERTER_CLASS_DEFINED);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.ID_MAPPING_UNRESOLVED_CONVERTER_NAME);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.MULTIPLE_OBJECT_VALUES_FOR_DATA_VALUE);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.PERSISTENT_ATTRIBUTE_INVALID_VERSION_MAPPING_TYPE);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.BASIC_COLLECTION_MAPPING_DEPRECATED);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.BASIC_MAP_MAPPING_DEPRECATED);
	}

	private void addQueriesGeneratorsCategory(Composite parent) {
		parent = this.addExpandableSection(parent, JptJpaUiPreferencesValidationMessages.QUERIES_GENERATORS_CATEGORY);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.GENERATOR_EQUIVALENT);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.QUERY_EQUIVALENT);
	}

	private void addMultitenancyCategory(Composite parent) {
		parent = this.addExpandableSection(parent, JptJpaEclipseLinkUiMessages.MULTITENANCY_CATEGORY);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.TENANT_DISCRIMINATOR_COLUMN_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.VIRTUAL_TENANT_DISCRIMINATOR_COLUMN_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.VIRTUAL_ATTRIBUTE_TENANT_DISCRIMINATOR_COLUMN_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.TENANT_DISCRIMINATOR_COLUMN_TABLE_NOT_VALID);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.VIRTUAL_TENANT_DISCRIMINATOR_COLUMN_TABLE_NOT_VALID);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.VIRTUAL_ATTRIBUTE_TENANT_DISCRIMINATOR_COLUMN_TABLE_NOT_VALID);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.MULTITENANT_NOT_SPECIFIED_WITH_TENANT_DISCRIMINATOR_COLUMNS);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.MULTITENANT_METADATA_CANNOT_BE_SPECIFIED_ON_NON_ROOT_ENTITY);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.MULTITENANT_TABLE_PER_TENANT_NOT_SUPPORTED);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.MULTITENANT_VPD_MIGHT_NOT_BE_NOT_SUPPORTED);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.MULTITENANT_VPD_NOT_SUPPORTED_ON_NON_ORACLE_DATABASE_PLATFORM);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.MULTITENANT_VPD_INCLUDE_CRITERIA_WILL_BE_IGNORED);
	}

	private void addVirtualCategory(Composite parent) {
		parent = this.addExpandableSection(parent, JptJpaEclipseLinkUiMessages.VIRTUAL_CATEGORY);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.VIRTUAL_ATTRIBUTE_NO_ATTRIBUTE_TYPE_SPECIFIED);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.VIRTUAL_ATTRIBUTE_ATTRIBUTE_TYPE_DOES_NOT_EXIST);
		this.addLabeledCombo(parent, JptJpaEclipseLinkCoreValidationMessages.VIRTUAL_TYPE_PARENT_CLASS_DOES_NOT_EXIST);
	}


	// ********** plug-in preferences **********

	@Override
	protected boolean getWorkspaceValidationPreferencesOverridden(IProject project) {
		return EclipseLinkJpaPreferences.getWorkspaceValidationOverridden(project);
	}

	@Override
	protected void setWorkspaceValidationPreferencesOverridden(IProject project, boolean value) {
		EclipseLinkJpaPreferences.setWorkspaceValidationOverridden(project, value);
	}

	@Override
	protected int getValidationMessageSeverityPreference(String prefKey) {
		return EclipseLinkJpaPreferences.getValidationMessageSeverity(prefKey);
	}

	@Override
	protected void setValidationMessageSeverityPreference(String prefKey, int value) {
		EclipseLinkJpaPreferences.setValidationMessageSeverity(prefKey, value);
	}

	@Override
	protected int getValidationMessageSeverityPreference(IProject project, String prefKey) {
		return EclipseLinkJpaPreferences.getValidationMessageSeverity(project, prefKey);
	}

	@Override
	protected void setValidationMessageSeverityPreference(IProject project, String prefKey, int value) {
		EclipseLinkJpaPreferences.setValidationMessageSeverity(project, prefKey, value);
	}
}
