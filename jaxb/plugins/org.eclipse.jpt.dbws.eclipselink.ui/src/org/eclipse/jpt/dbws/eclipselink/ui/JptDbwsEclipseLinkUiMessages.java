/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.dbws.eclipselink.ui;

import org.eclipse.osgi.util.NLS;

/**
 * Localized messages used by Dali DBWS EclipseLink UI.
 */
public class JptDbwsEclipseLinkUiMessages {

	private static final String BUNDLE_NAME = "jpt_dbws_eclipselink_ui"; //$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = JptDbwsEclipseLinkUiMessages.class;
	static {
		NLS.initializeMessages(BUNDLE_NAME, BUNDLE_CLASS);
	}

	public static String DBWS_GENERATOR_WIZARD__TITLE;
	public static String DBWS_GENERATOR_WIZARD__GENERATING_DBWS;

	public static String WEB_DYNAMIC_PROJECT_WIZARD_PAGE__TITLE;
	public static String WEB_DYNAMIC_PROJECT_WIZARD_PAGE__DESC;
	public static String WEB_DYNAMIC_PROJECT_WIZARD_PAGE__DESTINATION_PROJECT;

	public static String BUILDER_XML_WIZARD_PAGE__TITLE;
	public static String BUILDER_XML_WIZARD_PAGE__DESC;
	public static String BUILDER_XML_WIZARD_PAGE__ERROR_URI_CANNOT_BE_LOCATED;

	public static String JDBC_DRIVER_WIZARD_PAGE__TITLE;
	public static String JDBC_DRIVER_WIZARD_PAGE__DESC;
	public static String JDBC_DRIVER_WIZARD_PAGE__DRIVER_FILES;
	public static String JDBC_DRIVER_WIZARD_PAGE__ADD_BUTTON;
	public static String JDBC_DRIVER_WIZARD_PAGE__REMOVE_BUTTON;
	public static String JDBC_DRIVER_WIZARD_PAGE__CHOOSE_A_DRIVER_FILE;

	public static String DBWS_GENERATOR_UI__RUNNING_DBWS_WARNING_TITLE;
	public static String DBWS_GENERATOR_UI__RUNNING_DBWS_WARNING_MESSAGE;
	public static String DBWS_GENERATOR_UI__DBWS_NOT_ON_CLASSPATH_MESSAGE;
	public static String DBWS_GENERATOR_UI__NOT_JAVA_PROJECT;
	public static String DBWS_GENERATOR_UI__NOT_WEB_DYNAMIC_PROJECT;
	public static String DBWS_GENERATOR_UI__GENERATING_DBWS;

	public static String BUILDER_XML_WIZARD_PAGE__XML_CATALOG_TABLE_TITLE;
	public static String BUILDER_XML_WIZARD_PAGE__XML_CATALOG_KEY_COLUMN;
	public static String BUILDER_XML_WIZARD_PAGE__XML_CATALOG_URI_COLUMN;

	private JptDbwsEclipseLinkUiMessages() {
		throw new UnsupportedOperationException();
	}
}
