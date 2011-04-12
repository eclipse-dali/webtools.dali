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
package org.eclipse.jpt.jpa.ui.internal.jpql;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.ui.PreferenceConstants;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jpt.jpa.core.context.NamedQuery;
import org.eclipse.persistence.jpa.jpql.ContentAssistProposals;
import org.eclipse.persistence.jpa.jpql.ResultQuery;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

/**
 * The concrete implementation of a {@link org.eclipse.jface.text.contentassist.ICompletionProposal
 * ICompletionProposal} that adds relevance and toggling the completion insertion property behavior.
 *
 * @version 3.0
 * @since 3.0
 * @author Pascal Filion
 */
final class JpqlCompletionProposal implements ICompletionProposal {

	private String actualQuery;
	private String additionalInfo;
	private ContentAssistProposals proposals;
	private int cursorOffset;
	private String displayString;
	private boolean escapeCharacters;
	private Image image;
	private String jpqlQuery;
	private NamedQuery namedQuery;
	private int offset;
	private int position;
	private String proposal;
	private ResultQuery result;
	private boolean toggleCompletion;

	JpqlCompletionProposal(ContentAssistProposals proposals,
	                       String proposal,
	                       String displayString,
	                       String additionalInfo,
	                       Image image,
	                       NamedQuery namedQuery,
	                       String actualQuery,
	                       String jpqlQuery,
	                       int offset,
	                       int position,
	                       int cursorOffset,
	                       boolean escapeCharacters) {

		super();

		this.image            = image;
		this.offset           = offset;
		this.position         = position;
		this.proposal         = proposal;
		this.jpqlQuery        = jpqlQuery;
		this.proposals        = proposals;
		this.namedQuery       = namedQuery;
		this.actualQuery      = actualQuery;
		this.cursorOffset     = cursorOffset;
		this.displayString    = displayString;
		this.additionalInfo   = additionalInfo;
		this.escapeCharacters = escapeCharacters;
	}

	/**
	 * {@inheritDoc}
	 */
	public void apply(IDocument document) {
		try {
			ResultQuery result = buildResult();
			document.replace(offset, actualQuery.length(), result.getQuery());
		}
		catch (BadLocationException e) {
			// Ignore
		}
	}

	private ResultQuery buildResult() {
		if (result == null) {
			if (escapeCharacters) {
				result = proposals.buildEscapedQuery(
					jpqlQuery,
					proposal,
					position,
					isCompletionInserts() ^ toggleCompletion
				);
			}
			else {
				result = proposals.buildQuery(
					jpqlQuery,
					proposal,
					position,
					isCompletionInserts() ^ toggleCompletion
				);
			}
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getAdditionalProposalInfo() {
		return additionalInfo;
	}

	/**
	 * {@inheritDoc}
	 */
	public IContextInformation getContextInformation() {
		return null; // Not needed, this is legacy API
	}

	/**
	 * {@inheritDoc}
	 */
	public String getDisplayString() {
		return displayString;
	}

	/**
	 * {@inheritDoc}
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * {@inheritDoc}
	 */
	public Point getSelection(IDocument document) {
		ResultQuery result = buildResult();
		return new Point(offset + result.getPosition() + cursorOffset, 0);
	}

	private boolean isCompletionInserts() {
		IJavaProject javaProject = namedQuery.getJpaProject().getJavaProject();
		String value = PreferenceConstants.getPreference(PreferenceConstants.CODEASSIST_INSERT_COMPLETION, javaProject);
		return Boolean.valueOf(value);
	}
}