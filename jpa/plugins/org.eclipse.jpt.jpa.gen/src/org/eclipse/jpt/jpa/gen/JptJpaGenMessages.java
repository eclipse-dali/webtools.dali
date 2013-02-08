/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.gen;

import org.eclipse.osgi.util.NLS;

/**
 * Localized messages used by Dali JPA entity generation.
 */
public class JptJpaGenMessages {

	private static final String BUNDLE_NAME = "jpt_jpa_gen"; //$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = JptJpaGenMessages.class;
	static {
		NLS.initializeMessages(BUNDLE_NAME, BUNDLE_CLASS);
	}
	
	public static String PACKAGE_GENERATOR_TASK_NAME;
	public static String GEN_SCOPE_TASK_NAME;
	public static String ENTITY_GENERATOR_TASK_NAME;
	public static String TEMPLATES_NOT_FOUND;
	public static String ERROR_GENERATING_ENTITIES;
	public static String DELETE_FOLDER_ERROR;
	public static String DELETE_FILE_ERROR;
	public static String FILE_READ_ONLY_ERROR;

	private JptJpaGenMessages() {
		throw new UnsupportedOperationException();
	}
}
