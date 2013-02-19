/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.validation;

import org.eclipse.osgi.util.NLS;

/**
 * Localized validation message arguments used by Dali JPA core.
 * <p>
 * <strong>NB:</strong> These strings are not typical messages; they are used as
 * arguments to be bound to various of the validation messages in
 * {@link JptJpaCoreValidationMessages}.
 */
public class JptJpaCoreValidationArgumentMessages {

	private static final String BUNDLE_NAME = "jpt_jpa_core_validation_argument"; //$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = JptJpaCoreValidationArgumentMessages.class;
	static {
		NLS.initializeMessages(BUNDLE_NAME, BUNDLE_CLASS);
	}

	public static String DOES_NOT_MATCH_JOIN_TABLE;
	public static String DOES_NOT_MATCH_COLLECTION_TABLE;
	public static String NOT_VALID_FOR_THIS_ENTITY;

	public static String ATTRIBUTE_DESC;
	public static String VIRTUAL_ATTRIBUTE_DESC;

	public static String ON_MAPPED_SUPERCLASS;
	public static String ON_EMBEDDABLE;

	private JptJpaCoreValidationArgumentMessages() {
		throw new UnsupportedOperationException();
	}
}
