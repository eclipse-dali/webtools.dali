/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import org.eclipse.osgi.util.NLS;

public class JptCoreMessages extends NLS
{
	private static final String BUNDLE_NAME = "jpa_core"; //$NON-NLS-1$
	
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, JptCoreMessages.class);
	}
	
	public static String VALIDATE_PLATFORM_NOT_SPECIFIED;
	
	public static String VALIDATE_CONNECTION_NOT_SPECIFIED;
	
	public static String VALIDATE_CONNECTION_NOT_CONNECTED;
	
	public static String SYNCHRONIZE_CLASSES_JOB;
	
	public static String SYNCHRONIZING_CLASSES_TASK;
	
	public static String INVALID_PERSISTENCE_XML_CONTENT;
	
	public static String ERROR_WRITING_FILE;
	
	
	private JptCoreMessages() {
		throw new UnsupportedOperationException();
	}
}
