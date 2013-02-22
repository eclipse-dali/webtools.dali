/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.validation;

import org.eclipse.core.resources.IProject;
import org.eclipse.jpt.common.core.internal.utility.ValidationMessageLoader;
import org.eclipse.jpt.common.core.utility.ValidationMessage;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jaxb.core.JaxbProject;

/**
 * Localized validation messages used by Dali EclipseLink JAXB core.
 * <b>
 * <strong>NB:</strong> These are not loaded like "normal" message
 * (i.e. like messages loaded by {@link org.eclipse.osgi.util.NLS});
 * instead they are loaded as {@link ValidationMessage}s.
 * @see ValidationMessageLoader
 */
public class JptJaxbEclipseLinkCoreValidationMessages {
	
	// validation on project
	public static ValidationMessage PROJECT_MISSING_ECLIPSELINK_JAXB_CONTEXT_FACTORY;
	
	// oxm file
	public static ValidationMessage OXM_FILE__NO_PACKAGE_SPECIFIED;
	public static ValidationMessage OXM_FILE__NO_SUCH_PACKAGE;
	
	// oxm java type
	public static ValidationMessage OXM_JAVA_TYPE__NAME_NOT_SPECIFIED;
	public static ValidationMessage OXM_JAVA_TYPE__PACKAGE_NAME_NOT_UNIFORM;
	
	// xml discriminator node
	public static ValidationMessage XML_DISCRIMINATOR_NODE__NOT_SPECIFIED;
	
	// xml discriminator value
	public static ValidationMessage XML_DISCRIMINATOR_VALUE__NOT_SPECIFIED;
	
	// xml element decl
	public static ValidationMessage XML_ELEMENT_DECL__INVALID_METHOD_SIGNATURE_RETURN_TYPE;
	
	// xml inverse reference
	public static ValidationMessage XML_INVERSE_REFERENCE__MAPPED_BY_NOT_SPECIFIED;
	public static ValidationMessage XML_INVERSE_REFERENCE__MAPPED_BY_NOT_RESOLVED;
	public static ValidationMessage XML_INVERSE_REFERENCE__MAPPED_BY_ILLEGAL_MAPPING_TYPE;
	
	// xml join node
	public static ValidationMessage XML_JOIN_NODE__XML_PATH_NOT_SPECIFIED;
	public static ValidationMessage XML_JOIN_NODE__REFERENCED_XML_PATH_NOT_SPECIFIED;
	public static ValidationMessage XML_JOIN_NODE__REFERENCED_XML_PATH_NOT_IN_REFERENCED_CLASS_KEYS;
	
	// xml join nodes
	public static ValidationMessage XML_JOIN_NODES__INVALID_REFERENCED_CLASS;
	public static ValidationMessage XML_JOIN_NODES__DUPLICATE_XML_PATH;
	public static ValidationMessage XML_JOIN_NODES__DUPLICATE_REFERENCED_XML_PATH;
	
	// xml path
	public static ValidationMessage XML_PATH__NOT_SPECIFIED;
	
		// used on XmlElements mapping
	public static ValidationMessage XML_PATH__INSUFFICIENT_XML_PATHS_FOR_XML_ELEMENTS;
	public static ValidationMessage XML_PATH__INSUFFICIENT_XML_ELEMENTS_FOR_XML_PATHS;
	
	// for all XPath usage
	public static ValidationMessage XPATH__INVALID_FORM_ILLEGAL_SEGMENT;
	public static ValidationMessage XPATH__ROOT_NOT_SUPPORTED;
	public static ValidationMessage XPATH__SELF_SEGMENT_MUST_BE_FIRST_SEGMENT;
	public static ValidationMessage XPATH__TEXT_SEGMENT_MUST_BE_LAST_SEGMENT;
	public static ValidationMessage XPATH__ATTRIBUTE_SEGMENT_MUST_BE_LAST_SEGMENT;
	public static ValidationMessage XPATH__INVALID_NS_PREFIX;
	public static ValidationMessage XPATH__UNRESOLVED_ELEMENT;
	public static ValidationMessage XPATH__UNRESOLVED_ATTRIBUTE;


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
			// return JaxbEclipseLinkPreferences.getValidationMessageSeverity(project, messageID, defaultSeverity);
		}
		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}
	}

	private static final String MESSAGE_BUNDLE_NAME = "jpt_jaxb_eclipselink_core_validation"; //$NON-NLS-1$
	private static final String DESCRIPTION_BUNDLE_NAME = "jpt_jaxb_eclipselink_core_validation_description"; //$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = JptJaxbEclipseLinkCoreValidationMessages.class;
	static {
		ValidationMessageLoader.load(BUNDLE_CLASS, MESSAGE_BUNDLE_NAME, DESCRIPTION_BUNDLE_NAME, JaxbProject.MARKER_TYPE, PREFERENCES_ADAPTER);
	}
}
