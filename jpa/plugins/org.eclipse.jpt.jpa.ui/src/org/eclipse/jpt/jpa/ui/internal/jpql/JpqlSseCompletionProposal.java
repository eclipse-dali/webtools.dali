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

import java.lang.reflect.Method;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.context.NamedQuery;
import org.eclipse.jpt.jpa.core.jpql.XmlEscapeCharacterConverter;
import org.eclipse.persistence.jpa.jpql.ContentAssistProposals;
import org.eclipse.persistence.jpa.jpql.DefaultContentAssistProposals;
import org.eclipse.persistence.jpa.jpql.ExpressionTools;
import org.eclipse.persistence.jpa.jpql.ResultQuery;
import org.eclipse.persistence.jpa.jpql.WordParser;
import org.eclipse.swt.graphics.Image;

/**
 * @version 3.3
 * @since 3.3
 * @author Pascal Filion
 */
final class JpqlSseCompletionProposal extends JpqlCompletionProposal {

	private boolean cDATASection;
	private int actualPosition;
	private String actualJpqlQuery;

	JpqlSseCompletionProposal(ContentAssistProposals proposals,
	                          String proposal,
	                          String displayString,
	                          String additionalInfo,
	                          Image image,
	                          NamedQuery namedQuery,
	                          String jpqlQuery,
	                          String actualJpqlQuery,
	                          int tokenStart,
	                          int tokenEnd,
	                          int position,
	                          int actualPosition,
	                          int cursorOffset,
	                          boolean cDATASection) {

		super(proposals,
		      proposal,
		      displayString,
		      additionalInfo,
		      image,
		      namedQuery,
		      jpqlQuery,
		      tokenStart,
		      tokenEnd,
		      position,
		      cursorOffset);

		this.actualPosition = actualPosition;
		this.actualJpqlQuery = actualJpqlQuery;
		this.cDATASection = cDATASection;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	ResultQuery buildResult() {

		// No need to convert the JPQL query into an escaped version (like converting > into &gt;)
		if (cDATASection) {
			return proposals.buildQuery(
				jpqlQuery,
				proposal,
				position,
				isCompletionInserts() ^ toggleCompletion
			);
		}

		// TODO: UPDATE ONCE THE NEXT ECLIPSELINK HERMES 2.5 MILESTONE IS AVAILABLE
		return /*proposals.*/buildXmlQuery(
			actualJpqlQuery,
			proposal,
			actualPosition,
			isCompletionInserts() ^ toggleCompletion
		);
	}

	/**
	 * TODO: TO DELETE ONCE USING THE NEXT ECLIPSELINK HERMES 2.5 MILESTONE
	 */
	@SuppressWarnings("nls")
	private ResultQuery buildXmlQuery(String jpqlQuery, String proposal, int position, boolean insert) {

		// Nothing to replace
		if (ExpressionTools.stringIsEmpty(proposal)) {
			return proposals.buildQuery(jpqlQuery, StringTools.EMPTY_STRING, position, false);
		}

		int[] positions = { position };

		// First convert the escape characters into their unicode characters
		String query = XmlEscapeCharacterConverter.unescape(jpqlQuery, positions);

		// Calculate the start and end positions
		WordParser wordParser = new WordParser(query);
		wordParser.setPosition(positions[0]);

		// int[] proposalPositions = buildPositions(wordParser, proposal, insert);
		int[] proposalPositions;
		try {
			Method buildPositionsMethod = DefaultContentAssistProposals.class.getDeclaredMethod("buildPositions", WordParser.class, String.class, boolean.class);
			buildPositionsMethod.setAccessible(true);
			proposalPositions = (int[]) buildPositionsMethod.invoke(proposals, wordParser, proposal, insert);
		}
		catch (Exception e) {
			// This is temporary
			proposalPositions = new int[2];
		}

		// Escape the proposal
		proposal = XmlEscapeCharacterConverter.escape(proposal, new int[1]);

		// Adjust the positions so it's in the original JPQL query, which may contain escaped characters
		XmlEscapeCharacterConverter.reposition(jpqlQuery, proposalPositions);

		// Create the new JPQL query
		StringBuilder sb = new StringBuilder(jpqlQuery);
		sb.replace(proposalPositions[0], proposalPositions[1], proposal);

		// And simply create a new ResultQuery object
		return proposals.buildQuery(sb.toString(), StringTools.EMPTY_STRING, proposalPositions[0] + proposal.length(), false);
	}
}