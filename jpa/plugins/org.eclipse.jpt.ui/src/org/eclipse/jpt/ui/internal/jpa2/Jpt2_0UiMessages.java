/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.ui.internal.jpa2;

import org.eclipse.osgi.util.NLS;

/**
 *  Jpt2_0UiMessages
 */
public class Jpt2_0UiMessages
{
	// Connection
	public static String ConnectionPropertiesComposite_Database_GroupBox;

	public static String DataSourcePropertiesComposite_jtaDataSourceLabel;
	public static String DataSourcePropertiesComposite_nonJtaDataSourceLabel;
	
	public static String GenericPersistenceUnit2_0ConnectionComposite_sectionTitle;
	public static String GenericPersistenceUnit2_0ConnectionComposite_sectionDescription;
	public static String GenericPersistenceUnit2_0ConnectionTab_title;
	
	public static String GenericPersistenceUnit2_0OptionsComposite_miscellaneousSectionTitle;
	public static String GenericPersistenceUnit2_0OptionsComposite_miscellaneousSectionDescription;
	public static String GenericPersistenceUnit2_0OptionsTab_title;

	public static String JdbcConnectionPropertiesComposite_ConnectionDialog_Message;
	public static String JdbcConnectionPropertiesComposite_ConnectionDialog_Title;

	public static String JdbcConnectionPropertiesComposite_driverLabel;
	public static String JdbcConnectionPropertiesComposite_urlLabel;
	public static String JdbcConnectionPropertiesComposite_userLabel;
	public static String JdbcConnectionPropertiesComposite_passwordLabel;

	public static String JdbcPropertiesComposite_JdbcConnectionProperties_GroupBox;

	public static String LockingConfigurationComposite_lockTimeoutLabel;
	public static String QueryConfigurationComposite_queryTimeoutLabel;
	
	public static String TransactionTypeComposite_transactionTypeLabel;

	public static String TransactionTypeComposite_jta;
	public static String TransactionTypeComposite_resource_local;
	
	public static String ValidationModeComposite_validationModeLabel;
	
	public static String ValidationModeComposite_auto;
	public static String ValidationModeComposite_callback;
	public static String ValidationModeComposite_none;

	public static String ValidationConfigurationComposite_groupPrePersistLabel;
	public static String ValidationConfigurationComposite_groupPreUpdateLabel;
	public static String ValidationConfigurationComposite_groupPreRemoveLabel;

	
	private static final String BUNDLE_NAME = "jpt_ui_persistence2_0"; //$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = Jpt2_0UiMessages.class;
	static {
		NLS.initializeMessages(BUNDLE_NAME, BUNDLE_CLASS);
	}

	private Jpt2_0UiMessages() {
		throw new UnsupportedOperationException();
	}

}
