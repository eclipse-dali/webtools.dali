/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpql;

import org.eclipse.jpt.jpa.core.context.NamedQuery;
import org.eclipse.jpt.jpa.core.jpql.JpaJpqlQueryHelper;
import org.eclipse.persistence.jpa.jpql.parser.QueryPosition;

/**
 * The generic implementation of a hyperlink detector used in the JPQL query text editor.
 *
 * @version 3.3
 * @since 3.3
 * @author Pascal Filion
 */
public class GenericJpaJpqlHyperlinkDetector extends JpaJpqlHyperlinkDetector {

	/**
	 * Creates a new <code>GenericJpaJpqlHyperlinkDetector</code>.
	 */
	public GenericJpaJpqlHyperlinkDetector() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected JpaJpqlHyperlinkBuilder buildHyperlinkBuilder(JpaJpqlQueryHelper queryHelper,
	                                                        NamedQuery namedQuery,
	                                                        QueryPosition queryPosition) {

		return new GenericJpaJpqlHyperlinkBuilder(queryHelper, namedQuery, queryPosition);
	}
}