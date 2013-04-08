/*******************************************************************************
 *  Copyright (c) 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.ui;

import org.eclipse.osgi.util.NLS;

public class JptJaxbEclipseLinkUiMessages {

	private static final String BUNDLE_NAME = "jpt_jaxb_eclipselink_ui"; //$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = JptJaxbEclipseLinkUiMessages.class;
	static {
		NLS.initializeMessages(BUNDLE_NAME, BUNDLE_CLASS);
	}
	
	
	public static String OXM_FILE_WIZARD__TITLE;
	public static String OXM_FILE_WIZARD__NEW_FILE_PAGE__TITLE;
	public static String OXM_FILE_WIZARD__NEW_FILE_PAGE__DESC;
	public static String OXM_FILE_WIZARD__FILE_OPTIONS_PAGE__TITLE;
	public static String OXM_FILE_WIZARD__FILE_OPTIONS_PAGE__DESC;
	public static String OXM_FILE_WIZARD__FILE_OPTIONS_PAGE__PACKAGE_NAME_LABEL;
	
	
	private JptJaxbEclipseLinkUiMessages() {
		throw new UnsupportedOperationException();
	}
}
