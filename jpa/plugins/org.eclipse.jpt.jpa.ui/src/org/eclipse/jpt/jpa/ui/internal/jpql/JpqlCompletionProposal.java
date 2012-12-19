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
 * The abstract implementation of {@link org.eclipse.jface.text.contentassist.ICompletionProposal
 * ICompletionProposal} which adds relevance and toggling the completion insertion property behavior.
 *
 * @version 3.3
 * @since 3.0
 * @author Pascal Filion
 */
abstract class JpqlCompletionProposal implements ICompletionProposal {

	private String additionalInfo;
	private int cursorOffset;
	private String displayString;
	private Image image;
	String jpqlQuery;
	private NamedQuery namedQuery;
	int position;
	String proposal;
	ContentAssistProposals proposals;
	ResultQuery result;
	boolean toggleCompletion;
	private int tokenEnd;
	private int tokenStart;

	JpqlCompletionProposal(ContentAssistProposals proposals,
	                       String proposal,
	                       String displayString,
	                       String additionalInfo,
	                       Image image,
	                       NamedQuery namedQuery,
	                       String jpqlQuery,
	                       int tokenStart,
	                       int tokenEnd,
	                       int position,
	                       int cursorOffset) {

		super();

		this.image            = image;
		this.tokenStart       = tokenStart;
		this.tokenEnd         = tokenEnd;
		this.position         = position;
		this.proposal         = proposal;
		this.jpqlQuery        = jpqlQuery;
		this.proposals        = proposals;
		this.namedQuery       = namedQuery;
		this.cursorOffset     = cursorOffset;
		this.displayString    = displayString;
		this.additionalInfo   = additionalInfo;
	}

	/**
	 * {@inheritDoc}
	 */
	public void apply(IDocument document) {
		try {
			document.replace(tokenStart, tokenEnd - tokenStart, getResult().getQuery());
		}
		catch (BadLocationException e) {
			// Ignore
		}
	}

	/**
	 * Creates
	 *
	 * @return
	 */
	abstract ResultQuery buildResult();

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
	 * Returns
	 *
	 * @return
	 */
	final ResultQuery getResult() {
		if (result == null) {
			result = buildResult();
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public Point getSelection(IDocument document) {
		return new Point(tokenStart + getResult().getPosition() + cursorOffset, 0);
	}

	final boolean isCompletionInserts() {
		IJavaProject javaProject = namedQuery.getJpaProject().getJavaProject();
		String value = PreferenceConstants.getPreference(PreferenceConstants.CODEASSIST_INSERT_COMPLETION, javaProject);
		return Boolean.valueOf(value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return proposal;
	}
}