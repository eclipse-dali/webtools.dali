/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.eclipselink.core;

import org.eclipse.osgi.util.NLS;

/**
 * Localized messages used by Dali common EclipseLink core.
 */
public class JptCommonEclipseLinkCoreMessages {

	private static final String BUNDLE_NAME = "jpt_common_eclipselink_core"; //$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = JptCommonEclipseLinkCoreMessages.class;
	static {
		NLS.initializeMessages(BUNDLE_NAME, BUNDLE_CLASS);
	}

	public static String ECLIPSELINK_LIBRARY_VALIDATOR__NO_ECLIPSELINK_VERSION;
	public static String ECLIPSELINK_LIBRARY_VALIDATOR__MULTIPLE_ECLIPSELINK_VERSIONS;
	public static String ECLIPSELINK_LIBRARY_VALIDATOR__IMPROPER_ECLIPSELINK_VERSION;

	private JptCommonEclipseLinkCoreMessages() {
		throw new UnsupportedOperationException();
	}
}
