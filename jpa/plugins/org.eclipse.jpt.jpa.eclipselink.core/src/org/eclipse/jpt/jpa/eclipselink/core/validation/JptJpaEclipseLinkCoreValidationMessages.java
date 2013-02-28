/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.validation;

import org.eclipse.core.resources.IProject;
import org.eclipse.jpt.common.core.internal.utility.ValidationMessageLoader;
import org.eclipse.jpt.common.core.utility.ValidationMessage;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

/**
 * Localized validation messages used by Dali EclipseLink JPA core.
 * <b>
 * <strong>NB:</strong> These are not loaded like "normal" message
 * (i.e. like messages loaded by {@link org.eclipse.osgi.util.NLS});
 * instead they are loaded as {@link ValidationMessage}s.
 * @see ValidationMessageLoader
 */
public class JptJpaEclipseLinkCoreValidationMessages {

	public static ValidationMessage CACHE_EXPIRY_AND_EXPIRY_TIME_OF_DAY_BOTH_SPECIFIED;

	public static ValidationMessage CONVERTER_CLASS_IMPLEMENTS_CONVERTER;
	public static ValidationMessage STRUCT_CONVERTER_CLASS_IMPLEMENTS_STRUCT_CONVERTER;
	public static ValidationMessage CONVERTER_DUPLICATE_NAME;
	public static ValidationMessage CONVERTER_NAME_UNDEFINED;
	public static ValidationMessage RESERVED_CONVERTER_NAME;
	public static ValidationMessage CONVERTER_CLASS_EXISTS;
	public static ValidationMessage CONVERTER_CLASS_DEFINED;

	public static ValidationMessage DESCRIPTOR_CUSTOMIZER_CLASS_NOT_SPECIFIED;
	public static ValidationMessage DESCRIPTOR_CUSTOMIZER_CLASS_NOT_EXIST;
	public static ValidationMessage DESCRIPTOR_CUSTOMIZER_CLASS_NOT_VALID;
	public static ValidationMessage DESCRIPTOR_CUSTOMIZER_CLASS_IMPLEMENTS_DESCRIPTOR_CUSTOMIZER;
	
	public static ValidationMessage EXCEPTION_HANDLER_CLASS_NOT_SPECIFIED;
	public static ValidationMessage EXCEPTION_HANDLER_CLASS_NOT_EXIST;
	public static ValidationMessage EXCEPTION_HANDLER_CLASS_NOT_VALID;
	public static ValidationMessage EXCEPTION_HANDLER_CLASS_IMPLEMENTS_EXCEPTION_HANDLER;
	
	public static ValidationMessage GENERATOR_DUPLICATE_NAME;
	public static ValidationMessage GENERATOR_EQUIVALENT;

	public static ValidationMessage ID_MAPPING_UNRESOLVED_CONVERTER_NAME;

	public static ValidationMessage MULTIPLE_OBJECT_VALUES_FOR_DATA_VALUE;
	
	public static ValidationMessage PERSISTENT_ATTRIBUTE_INVALID_VERSION_MAPPING_TYPE;

	public static ValidationMessage PERSISTENCE_UNIT_LEGACY_DESCRIPTOR_CUSTOMIZER;
	public static ValidationMessage PERSISTENCE_UNIT_LEGACY_ENTITY_CACHING;

	public static ValidationMessage PERSISTENCE_UNIT_CACHING_PROPERTY_IGNORED;

	public static ValidationMessage QUERY_DUPLICATE_NAME;
	public static ValidationMessage QUERY_EQUIVALENT;

	public static ValidationMessage SESSION_CUSTOMIZER_CLASS_NOT_SPECIFIED;
	public static ValidationMessage SESSION_CUSTOMIZER_CLASS_NOT_EXIST;
	public static ValidationMessage SESSION_CUSTOMIZER_CLASS_NOT_VALID;
	public static ValidationMessage SESSION_CUSTOMIZER_CLASS_IMPLEMENTS_SESSION_CUSTOMIZER;

	public static ValidationMessage SESSION_LOGGER_CLASS_NOT_SPECIFIED;
	public static ValidationMessage SESSION_LOGGER_CLASS_NOT_EXIST;
	public static ValidationMessage SESSION_LOGGER_CLASS_IMPLEMENTS_SESSION_LOG;
	public static ValidationMessage SESSION_PROFILER_CLASS_NOT_SPECIFIED;
	
	public static ValidationMessage SESSION_PROFILER_CLASS_NOT_EXIST;
	public static ValidationMessage SESSION_PROFILER_CLASS_NOT_VALID;
	public static ValidationMessage SESSION_PROFILER_CLASS_IMPLEMENTS_SESSION_PROFILER;

	public static ValidationMessage BASIC_COLLECTION_MAPPING_DEPRECATED;
	public static ValidationMessage BASIC_MAP_MAPPING_DEPRECATED;

	public static ValidationMessage TYPE_MAPPING_MEMBER_CLASS_NOT_STATIC;

	public static ValidationMessage TENANT_DISCRIMINATOR_COLUMN_UNRESOLVED_NAME;
	public static ValidationMessage VIRTUAL_TENANT_DISCRIMINATOR_COLUMN_UNRESOLVED_NAME;
	public static ValidationMessage VIRTUAL_ATTRIBUTE_TENANT_DISCRIMINATOR_COLUMN_UNRESOLVED_NAME;
	public static ValidationMessage TENANT_DISCRIMINATOR_COLUMN_TABLE_NOT_VALID;
	public static ValidationMessage VIRTUAL_TENANT_DISCRIMINATOR_COLUMN_TABLE_NOT_VALID;
	public static ValidationMessage VIRTUAL_ATTRIBUTE_TENANT_DISCRIMINATOR_COLUMN_TABLE_NOT_VALID;
	public static ValidationMessage MULTITENANT_NOT_SPECIFIED_WITH_TENANT_DISCRIMINATOR_COLUMNS;
	public static ValidationMessage MULTITENANT_METADATA_CANNOT_BE_SPECIFIED_ON_NON_ROOT_ENTITY;
	public static ValidationMessage MULTITENANT_TABLE_PER_TENANT_NOT_SUPPORTED;
	public static ValidationMessage MULTITENANT_VPD_MIGHT_NOT_BE_NOT_SUPPORTED;
	public static ValidationMessage MULTITENANT_VPD_NOT_SUPPORTED_ON_NON_ORACLE_DATABASE_PLATFORM;
	public static ValidationMessage MULTITENANT_VPD_INCLUDE_CRITERIA_WILL_BE_IGNORED;

	public static ValidationMessage VIRTUAL_ATTRIBUTE_NO_ATTRIBUTE_TYPE_SPECIFIED;
	public static ValidationMessage VIRTUAL_ATTRIBUTE_ATTRIBUTE_TYPE_DOES_NOT_EXIST;
	public static ValidationMessage VIRTUAL_TYPE_PARENT_CLASS_DOES_NOT_EXIST;


	// ********** static initialization **********

	private static final ValidationMessageLoader.PreferencesAdapter PREFERENCES_ADAPTER = new PreferencesAdapter();
	private static class PreferencesAdapter
		implements ValidationMessageLoader.PreferencesAdapter
	{
		PreferencesAdapter() {
			super();
		}
		// TODO
		public int getSeverity(IProject project, String messageID, int defaultSeverity) {
			return defaultSeverity;
			// return JpaEclipseLinkPreferences.getValidationMessageSeverity(project, messageID, defaultSeverity);
		}
		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}
	}

	private static final String MESSAGE_BUNDLE_NAME = "jpt_jpa_eclipselink_core_validation"; //$NON-NLS-1$
	private static final String DESCRIPTION_BUNDLE_NAME = "jpt_jpa_eclipselink_core_validation_description"; //$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = JptJpaEclipseLinkCoreValidationMessages.class;
	static {
		ValidationMessageLoader.load(BUNDLE_CLASS, MESSAGE_BUNDLE_NAME, DESCRIPTION_BUNDLE_NAME, JpaProject.MARKER_TYPE, PREFERENCES_ADAPTER);

		// WARNINGs
		BASIC_COLLECTION_MAPPING_DEPRECATED.setDefaultSeverity(IMessage.NORMAL_SEVERITY);
		BASIC_MAP_MAPPING_DEPRECATED.setDefaultSeverity(IMessage.NORMAL_SEVERITY);
		MULTITENANT_METADATA_CANNOT_BE_SPECIFIED_ON_NON_ROOT_ENTITY.setDefaultSeverity(IMessage.NORMAL_SEVERITY);
		MULTITENANT_NOT_SPECIFIED_WITH_TENANT_DISCRIMINATOR_COLUMNS.setDefaultSeverity(IMessage.NORMAL_SEVERITY);
		MULTITENANT_TABLE_PER_TENANT_NOT_SUPPORTED.setDefaultSeverity(IMessage.NORMAL_SEVERITY);
		MULTITENANT_VPD_INCLUDE_CRITERIA_WILL_BE_IGNORED.setDefaultSeverity(IMessage.NORMAL_SEVERITY);
		MULTITENANT_VPD_NOT_SUPPORTED_ON_NON_ORACLE_DATABASE_PLATFORM.setDefaultSeverity(IMessage.NORMAL_SEVERITY);
		PERSISTENCE_UNIT_CACHING_PROPERTY_IGNORED.setDefaultSeverity(IMessage.NORMAL_SEVERITY);
		PERSISTENCE_UNIT_LEGACY_DESCRIPTOR_CUSTOMIZER.setDefaultSeverity(IMessage.NORMAL_SEVERITY);
		PERSISTENCE_UNIT_LEGACY_ENTITY_CACHING.setDefaultSeverity(IMessage.NORMAL_SEVERITY);

		// INFOs
		GENERATOR_EQUIVALENT.setDefaultSeverity(IMessage.LOW_SEVERITY);
		QUERY_EQUIVALENT.setDefaultSeverity(IMessage.LOW_SEVERITY);
		MULTITENANT_VPD_MIGHT_NOT_BE_NOT_SUPPORTED.setDefaultSeverity(IMessage.LOW_SEVERITY);
	}
}
