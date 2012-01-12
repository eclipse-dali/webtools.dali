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
package org.eclipse.jpt.jpa.core.internal.jpa2;

import org.eclipse.jpt.jpa.core.jpql.JpaJpqlQueryHelper;

import org.eclipse.jpt.jpa.core.internal.AbstractJpaJpqlQueryHelper;
import org.eclipse.persistence.jpa.jpql.parser.JPQLGrammar2_0;

/**
 * The default implementation of {@link JpaJpqlQueryHelper} that provides support based on the Java
 * Persistence functional specification version 2.0.
 *
 * @version 3.1
 * @since 3.1
 * @author Pascal Filion
 */
public class Generic2_0JpaJpqlQueryHelper extends AbstractJpaJpqlQueryHelper {

	/**
	 * The singleton instance of this helper.
	 */
	private static final JpaJpqlQueryHelper INSTANCE = new Generic2_0JpaJpqlQueryHelper();

	/**
	 * Creates a new <code>Generic2_0JpaJpqlQueryHelper</code>.
	 */
	private Generic2_0JpaJpqlQueryHelper() {
		super(JPQLGrammar2_0.instance());
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