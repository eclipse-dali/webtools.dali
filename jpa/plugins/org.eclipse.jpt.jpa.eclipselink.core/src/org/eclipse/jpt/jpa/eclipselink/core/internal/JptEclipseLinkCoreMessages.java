/*******************************************************************************
 *  Copyright (c) 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal;

import org.eclipse.osgi.util.NLS;

/**
 * Localized messages used by Dali eclipselink core.
 */
public class JptEclipseLinkCoreMessages
{
	public static String EclipseLinkLibraryValidator_noEclipseLinkVersion;
	public static String EclipseLinkLibraryValidator_multipleEclipseLinkVersions;
	public static String EclipseLinkLibraryValidator_improperEclipseLinkVersion;
	
	
	private static final String BUNDLE_NAME = "jpt_eclipselink_core"; //$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = JptEclipseLinkCoreMessages.class;
	static {
		NLS.initializeMessages(BUNDLE_NAME, BUNDLE_CLASS);
	}
	
	private JptEclipseLinkCoreMessages() {
		throw new UnsupportedOperationException();
	}
}
