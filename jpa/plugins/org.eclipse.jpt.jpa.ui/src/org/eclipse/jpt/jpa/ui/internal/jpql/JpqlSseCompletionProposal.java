/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
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
import org.eclipse.persistence.jpa.jpql.tools.ContentAssistProposals;
import org.eclipse.persistence.jpa.jpql.tools.ResultQuery;
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

		return proposals.buildXmlQuery(
			actualJpqlQuery,
			proposal,
			actualPosition,
			isCompletionInserts() ^ toggleCompletion
		);
	}
}