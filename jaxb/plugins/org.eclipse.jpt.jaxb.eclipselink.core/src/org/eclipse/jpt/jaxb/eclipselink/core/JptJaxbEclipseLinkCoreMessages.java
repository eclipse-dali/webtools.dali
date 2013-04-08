/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core;

import org.eclipse.osgi.util.NLS;

/**
 * Localized messages used by Dali JAXB EclipseLink core.
 */
public class JptJaxbEclipseLinkCoreMessages {

	private static final String BUNDLE_NAME = "jpt_jaxb_eclipselink_core"; //$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = JptJaxbEclipseLinkCoreMessages.class;
	static {
		NLS.initializeMessages(BUNDLE_NAME, BUNDLE_CLASS);
	}

	public static String EL_JAXB_USER_LIBRARY_VALIDATOR__NO_XJC_CLASSES;
	
	public static String VALIDATE__NEW_OXM_FILE__PROJECT_NOT_JAXB;
	public static String VALIDATE__NEW_OXM_FILE__PROJECT_NOT_ECLIPSELINK;
	public static String VALIDATE__NEW_OXM_FILE__CONTAINER_QUESTIONABLE;
	public static String VALIDATE__NEW_OXM_FILE__PACKAGE_UNSPECIFIED;
	public static String VALIDATE__NEW_OXM_FILE__PACKAGE_DOESNT_EXIST;
	
	
	private JptJaxbEclipseLinkCoreMessages() {
		throw new UnsupportedOperationException();
	}
}
