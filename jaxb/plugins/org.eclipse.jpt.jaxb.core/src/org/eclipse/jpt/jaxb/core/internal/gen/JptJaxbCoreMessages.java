/*******************************************************************************
* Copyright (c) 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.gen;

import org.eclipse.osgi.util.NLS;

/**
 * Localized messages used by Dali JAXB Core.
 *
 * @version 3.0
 */
public class JptJaxbCoreMessages {
	
	public static String SchemaGenerator_creatingJAXBPropertiesFileTask;

	public static String ClassesGenerator_generatingClasses;
	public static String ClassesGenerator_generatingClassesTask;


	private static final String BUNDLE_NAME = "jpt_jaxb_core"; //$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = JptJaxbCoreMessages.class;
	static {
		NLS.initializeMessages(BUNDLE_NAME, BUNDLE_CLASS);
	}

	private JptJaxbCoreMessages() {
		throw new UnsupportedOperationException();
	}

}
