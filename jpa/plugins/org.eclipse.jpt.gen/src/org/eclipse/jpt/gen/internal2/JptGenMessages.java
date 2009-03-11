/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.gen.internal2;

import org.eclipse.osgi.util.NLS;

/**
 * Localized messages used by Dali entity generation.
 */
class JptGenMessages {

	public static String PackageGenerator_taskName;
	public static String GenScope_taskName;
	public static String EntityGenerator_taskName;
	public static String Templates_notFound;
	public static String Error_Generating_Entities;

	private static final String BUNDLE_NAME = "jpt_gen"; //$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = JptGenMessages.class;
	static {
		NLS.initializeMessages(BUNDLE_NAME, BUNDLE_CLASS);
	}
	
	private JptGenMessages() {
		throw new UnsupportedOperationException();
	}

}
