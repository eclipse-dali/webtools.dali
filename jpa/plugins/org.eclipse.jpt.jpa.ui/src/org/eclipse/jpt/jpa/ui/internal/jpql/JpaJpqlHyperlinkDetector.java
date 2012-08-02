/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
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
package org.eclipse.jpt.jpa.ui.internal.jpql;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.hyperlink.AbstractHyperlinkDetector;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.jpt.jpa.core.context.NamedQuery;
import org.eclipse.jpt.jpa.core.jpql.JpaJpqlQueryHelper;
import org.eclipse.persistence.jpa.jpql.parser.QueryPosition;

/**
 * The abstract implementation of a hyperlink detector that is used to retrieve hyperlinks that are
 * supported for a JPQL query.
 * <p>
 * The detector is registered with the JPQL query editor's hyperlink detector target, which is
 * <code>org.eclipse.jpt.jpa.ui.jpql</code>.
 *
 * @see JpaJpqlContentProposalProvider#HYPERLINK_DETECTOR_ID
 *
 * @version 3.3
 * @since 3.3
 * @author Pascal Filion
 */
public abstract class JpaJpqlHyperlinkDetector extends AbstractHyperlinkDetector {

	/**
	 * Creates a new <code>JpaJpqlHyperlinkDetector</code>.
	 */
	protected JpaJpqlHyperlinkDetector() {
		super();
	}

	/**
	 * Creates a new {@link HyperlinkBuilder}, which will visit the {@link
	 * org.eclipse.persistence.jpa.jpql.parser.Expression Expression} which is the parsed object of
	 * the region where to detect hyperlinks.
	 *
	 * @param queryHelper This helper provides functionality related to JPQL queries
	 * @param namedQuery The model object representing the JPQL query
	 * @param queryPosition This object determines the position of the cursor within the parsed tree
	 * @return A new concrete {@link HyperlinkBuilder}
	 */
	protected abstract JpaJpqlHyperlinkBuilder buildHyperlinkBuilder(JpaJpqlQueryHelper queryHelper,
	                                                                 NamedQuery namedQuery,
	                                                                 QueryPosition queryPosition);

	/**
	 * {@inheritDoc}
	 */
	public final IHyperlink[] detectHyperlinks(ITextViewer textViewer,
	                                           IRegion region,
	                                           boolean canShowMultipleHyperlinks) {


		NamedQuery namedQuery = (NamedQuery) getAdapter(NamedQuery.class);
		JpaJpqlQueryHelper queryHelper = namedQuery.getPersistenceUnit().createJpqlQueryHelper();

		try {
			String jpqlQuery = textViewer.getTextWidget().getText();

			// Populate the helper
			queryHelper.setQuery(namedQuery, jpqlQuery);

			// Build the position within the parsed tree
			QueryPosition queryPosition = queryHelper.getJPQLExpression().buildPosition(
				jpqlQuery,
				region.getOffset()
			);

			// Gather the possible hyperlinks by visiting the Expression
			JpaJpqlHyperlinkBuilder builder = buildHyperlinkBuilder(
				queryHelper,
				namedQuery,
				queryPosition
			);

			queryPosition.getExpression().accept(builder);
			return builder.hyperlinks();
		}
		finally {
			queryHelper.dispose();
		}
	}
}