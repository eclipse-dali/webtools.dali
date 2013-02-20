/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.validation;

import org.eclipse.core.resources.IProject;
import org.eclipse.jpt.common.core.internal.utility.ValidationMessageLoader;
import org.eclipse.jpt.common.core.utility.ValidationMessage;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jaxb.core.JaxbProject;

/**
 * Localized validation messages used by Dali JAXB core.
 * <b>
 * <strong>NB:</strong> These are not loaded like "normal" message
 * (i.e. messages loaded by {@link org.eclipse.osgi.util.NLS});
 * instead they are loaded as {@link ValidationMessage}s.
 * @see ValidationMessageLoader
 */
public class JptJaxbCoreValidationMessages {

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
			// return JaxbPreferences.getValidationMessageSeverity(project, messageID, defaultSeverity);
		}
		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}
	}

	private static final String MESSAGE_BUNDLE_NAME = "jpt_jaxb_core_validation"; //$NON-NLS-1$
	private static final String DESCRIPTION_BUNDLE_NAME = "jpt_jaxb_core_validation_description"; //$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = JptJaxbCoreValidationMessages.class;
	static {
		ValidationMessageLoader.load(BUNDLE_CLASS, MESSAGE_BUNDLE_NAME, DESCRIPTION_BUNDLE_NAME, JaxbProject.MARKER_TYPE, PREFERENCES_ADAPTER);
	}

	// validation on project
	
	public static ValidationMessage NO_JAXB_PROJECT; 
	public static ValidationMessage JAXB_VALIDATION_FAILED; 
	public static ValidationMessage PROJECT_INVALID_LIBRARY_PROVIDER;
	public static ValidationMessage PROJECT__UNRESOLVED_SCHEMA;
	public static ValidationMessage PROJECT__DUPLICATE_NAMESPACE;
	
	
	// validation on package
	
	public static ValidationMessage PACKAGE_NO_SCHEMA_FOR_NAMESPACE;
	public static ValidationMessage PACKAGE_XML_JAVA_TYPE_ADAPTER_TYPE_NOT_SPECIFIED;
	
	public static ValidationMessage XML_SCHEMA__MISMATCHED_ATTRIBUTE_FORM_DEFAULT;
	public static ValidationMessage XML_SCHEMA__MISMATCHED_ELEMENT_FORM_DEFAULT;
	
	
	// validation on type
	
	public static ValidationMessage XML_ENUM__NON_SIMPLE_SCHEMA_TYPE;
	
	public static ValidationMessage XML_TYPE__UNMATCHING_NAMESPACE_FOR_ANONYMOUS_TYPE;
	public static ValidationMessage XML_TYPE__UNSPECIFIED_FACTORY_METHOD;
	public static ValidationMessage XML_TYPE__NO_PUBLIC_OR_PROTECTED_CONSTRUCTOR;
	public static ValidationMessage XML_TYPE__DUPLICATE_PROP;
	public static ValidationMessage XML_TYPE__MISSING_PROP;
	public static ValidationMessage XML_TYPE__NONEXISTENT_PROP;
	public static ValidationMessage XML_TYPE__TRANSIENT_PROP;
	public static ValidationMessage XML_TYPE__FACTORY_CLASS_IGNORED_FOR_ENUM;
	public static ValidationMessage XML_TYPE__FACTORY_METHOD_IGNORED_FOR_ENUM;
	public static ValidationMessage XML_TYPE__PROP_ORDER_IGNORED_FOR_ENUM;
	
	public static ValidationMessage XML_ROOT_ELEMENT_TYPE_CONFLICTS_WITH_XML_TYPE;
	
	
	// validation on enum constant 
	
	public static ValidationMessage XML_ENUM_VALUE__INVALID_LEXICAL_VALUE;
	
	
	// validation on attribute
	
	public static ValidationMessage ATTRIBUTE_MAPPING__UNSUPPORTED_ANNOTATION;
	public static ValidationMessage ATTRIBUTE_MAPPING_XML_JAVA_TYPE_ADAPTER_TYPE_NOT_DEFINED;
	public static ValidationMessage XML_ELEMENT_WRAPPER_DEFINED_ON_NON_ARRAY_NON_COLLECTION;
	
	public static ValidationMessage XML_ANY_ATTRIBUTE__MULTIPLE_MAPPINGS_DEFINED;
	public static ValidationMessage XML_ANY_ATTRIBUTE__NON_MAP_TYPE;
	
	public static ValidationMessage XML_ANY_ELEMENT__MULTIPLE_MAPPINGS_DEFINED;
	
	public static ValidationMessage XML_ATTRIBUTE__INVALID_SCHEMA_TYPE;
	
	public static ValidationMessage XML_ELEMENT__UNSPECIFIED_TYPE;
	public static ValidationMessage XML_ELEMENT__ILLEGAL_TYPE;
	public static ValidationMessage XML_ELEMENT__INVALID_SCHEMA_TYPE;
	
	public static ValidationMessage XML_ELEMENT_DECL__INVALID_METHOD_SIGNATURE_RETURN_TYPE;
	public static ValidationMessage XML_ELEMENT_DECL__INVALID_METHOD_SIGNATURE_PARAM;
	public static ValidationMessage XML_ELEMENT_DECL__SUBST_HEAD_NAME_EQUALS_NAME;
	public static ValidationMessage XML_ELEMENT_DECL__SUBST_HEAD_NO_MATCHING_ELEMENT_DECL;
	
	public static ValidationMessage XML_ELEMENT_REF__UNSPECIFIED_TYPE;
	public static ValidationMessage XML_ELEMENT_REF__ILLEGAL_TYPE;
	public static ValidationMessage XML_ELEMENT_REF__NO_ROOT_ELEMENT;
	public static ValidationMessage XML_ELEMENT_REF__NO_REGISTRY;
	public static ValidationMessage XML_ELEMENT_REF__NO_MATCHING_ELEMENT_DECL;
	
	public static ValidationMessage XML_ELEMENT_REFS__DUPLICATE_XML_ELEMENT_TYPE;
	public static ValidationMessage XML_ELEMENT_REFS__DUPLICATE_XML_ELEMENT_QNAME;
	
	public static ValidationMessage XML_ELEMENTS__DUPLICATE_XML_ELEMENT_TYPE;
	public static ValidationMessage XML_ELEMENTS__DUPLICATE_XML_ELEMENT_QNAME;
	
	public static ValidationMessage XML_ID__MULTIPLE_MAPPINGS_DEFINED;
	public static ValidationMessage XML_ID__ATTRIBUTE_TYPE_NOT_STRING;
	public static ValidationMessage XML_ID__SCHEMA_TYPE_NOT_ID;
	
	public static ValidationMessage XML_ID_REF__TYPE_DOES_NOT_CONTAIN_XML_ID;
	public static ValidationMessage XML_ID_REF__SCHEMA_TYPE_NOT_IDREF;
	
	public static ValidationMessage XML_LIST__ATTRIBUTE_NOT_COLLECTION_TYPE;
	public static ValidationMessage XML_LIST__ITEM_TYPE_NOT_MAPPED_TO_VALID_SCHEMA_TYPE;
	
	public static ValidationMessage XML_REGISTRY__MULTIPLE_XML_REGISTRIES_FOR_PACKAGE;
	public static ValidationMessage XML_REGISTRY__DUPLICATE_XML_ELEMENT_QNAME;
	
	public static ValidationMessage XML_VALUE__MULTIPLE_MAPPINGS_DEFINED;
	public static ValidationMessage XML_VALUE__NO_TEXT_CONTENT;
	public static ValidationMessage XML_VALUE__INVALID_SCHEMA_TYPE;
	
	public static ValidationMessage XML_VALUE_MAPPING_WITH_NON_XML_ATTRIBUTE_MAPPING_DEFINED;
	
	
	// general validation
	
	public static ValidationMessage XML_SCHEMA_TYPE__NON_SIMPLE_TYPE;
	public static ValidationMessage XML_SCHEMA_TYPE__TYPE_NOT_SPECIFIED_ON_PACKAGE;
	public static ValidationMessage XML_SCHEMA_TYPE__TYPE_SPECIFIED_ON_ATTRIBUTE;
	
	public static ValidationMessage QNAME__MISSING_NAME;
	public static ValidationMessage QNAME__UNRESOLVED_COMPONENT;
}
