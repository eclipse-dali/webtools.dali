/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal;

import org.eclipse.jpt.jpa.core.jpql.JpaJpqlQueryHelper;

import org.eclipse.persistence.jpa.jpql.parser.EclipseLinkJPQLGrammar1;

/**
 * The EclipseLink 1.0 implementation of {@link JpaJpqlQueryHelper}.
 *
 * @version 2.4
 * @since 2.4
 * @author Pascal Filion
 */
public class EclipseLinkJpaJpqlQueryHelper extends AbstractEclipseLinkJpaJpqlQueryHelper {

	/**
	 * The singleton instance of this helper.
	 */
	private static final JpaJpqlQueryHelper INSTANCE = new EclipseLinkJpaJpqlQueryHelper();

	/**
	 * Creates a new <code>EclipseLinkJpaJpqlQueryHelper</code>.
	 */
	private EclipseLinkJpaJpqlQueryHelper() {
		super(EclipseLinkJPQLGrammar1.instance());
	}

	/**
	 * Returns the singleton instance of this helper.
	 *
	 * @return The singleton instance of this helper
	 */
	public static JpaJpqlQueryHelper instance() {
		return INSTANCE;
	}
}