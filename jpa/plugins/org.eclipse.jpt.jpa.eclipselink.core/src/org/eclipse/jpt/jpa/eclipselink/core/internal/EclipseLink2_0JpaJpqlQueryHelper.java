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

import org.eclipse.persistence.jpa.jpql.parser.EclipseLinkJPQLGrammar2_0;

/**
 * The EclipseLink 2.0 implementation of {@link JpaJpqlQueryHelper}.
 *
 * @version 3.1
 * @since 3.1
 * @author Pascal Filion
 */
public class EclipseLink2_0JpaJpqlQueryHelper extends AbstractEclipseLinkJpaJpqlQueryHelper {

	/**
	 * The singleton instance of this helper.
	 */
	private static final JpaJpqlQueryHelper INSTANCE = new EclipseLink2_0JpaJpqlQueryHelper();

	/**
	 * Creates a new <code>EclipseLink2_0JpaJpqlQueryHelper</code>.
	 */
	private EclipseLink2_0JpaJpqlQueryHelper() {
		super(EclipseLinkJPQLGrammar2_0.instance());
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