/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core;

import org.eclipse.osgi.util.NLS;

public class JptJpaEclipseLinkCoreMessages {

	private static final String BUNDLE_NAME = "jpt_jpa_eclipselink_core"; //$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = JptJpaEclipseLinkCoreMessages.class;
	static {
		NLS.initializeMessages(BUNDLE_NAME, BUNDLE_CLASS);
	}

	public static String JAVA_METADATA_CONVERSION_CONVERT_CONVERTER;

	public static String ECLIPSELINK_STATIC_WEAVING_BUILDER__STATIC_WEAVING_FAILED;

	private JptJpaEclipseLinkCoreMessages() {
		throw new UnsupportedOperationException();
	}
}
