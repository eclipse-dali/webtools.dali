/*******************************************************************************
 * Copyright (c) 2012 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     
 *******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal;

import java.util.Properties;
import java.util.Vector;

import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.ui.contentassist.CompletionProposalInvocationContext;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMNode;
import org.eclipse.wst.xml.ui.internal.contentassist.ContentAssistRequest;

/**
 * <p>Default implementation of the {@link AbstractJpaXmlCompletionProposalComputer}, defaults are to do nothing</p>
 *
 * The code of this class was copied from DefaultXMLCompletionProposalComputer and modified according to
 * the needs of the XML content assist of Dali.
 * 
 * The existence of this class is to isolate WTP code that will be removed after bug 371939 is addressed.
 * When it can be removed, we should try to eliminate the subclassing of this "default" implementation.
 */

public class DefaultJpaXmlCompletionProposalComputer extends
		AbstractJpaXmlCompletionProposalComputer {

	/**
	 * Default behavior is do to nothing.
	 * 
	 * @see org.eclipse.wst.sse.ui.contentassist.ICompletionProposalComputer#sessionEnded()
	 */
	public void sessionEnded() {
		//default behavior is to do nothing
	}

	/**
	 * Default behavior is do to nothing.
	 * 
	 * @see org.eclipse.wst.sse.ui.contentassist.ICompletionProposalComputer#sessionStarted()
	 */
	public void sessionStarted() {
		//default behavior is to do nothing
	}
	
	/**
	 * Default behavior is do to nothing.
	 * 
	 * @see org.eclipse.wst.xml.ui.internal.contentassist.AbstractXMLCompletionProposalComputer#addAttributeNameProposals(org.eclipse.wst.xml.ui.internal.contentassist.ContentAssistRequest, org.eclipse.wst.sse.ui.contentassist.CompletionProposalInvocationContext)
	 */
	@Override
	protected void addAttributeNameProposals(
			ContentAssistRequest contentAssistRequest,
			CompletionProposalInvocationContext context) {
		//default behavior is to do nothing
	}

	/**
	 * Default behavior is do to nothing.
	 * 
	 * @see org.eclipse.wst.xml.ui.internal.contentassist.AbstractXMLCompletionProposalComputer#addAttributeValueProposals(org.eclipse.wst.xml.ui.internal.contentassist.ContentAssistRequest, org.eclipse.wst.sse.ui.contentassist.CompletionProposalInvocationContext)
	 */
	@Override
	protected void addAttributeValueProposals(
			ContentAssistRequest contentAssistRequest,
			CompletionProposalInvocationContext context) {
		//default behavior is to do nothing
	}

	/**
	 * Default behavior is do to nothing.
	 * 
	 * @see org.eclipse.wst.xml.ui.internal.contentassist.AbstractXMLCompletionProposalComputer#addCommentProposal(org.eclipse.wst.xml.ui.internal.contentassist.ContentAssistRequest, org.eclipse.wst.sse.ui.contentassist.CompletionProposalInvocationContext)
	 */
	@Override
	protected void addCommentProposal(
			ContentAssistRequest contentAssistRequest,
			CompletionProposalInvocationContext context) {
		//default behavior is to do nothing
	}

	/**
	 * Default behavior is do to nothing.
	 * 
	 * @see org.eclipse.wst.xml.ui.internal.contentassist.AbstractXMLCompletionProposalComputer#addDocTypeProposal(org.eclipse.wst.xml.ui.internal.contentassist.ContentAssistRequest, org.eclipse.wst.sse.ui.contentassist.CompletionProposalInvocationContext)
	 */
	protected void addDocTypeProposal(
			ContentAssistRequest contentAssistRequest,
			CompletionProposalInvocationContext context) {
		//default behavior is to do nothing
	}

	/**
	 * Default behavior is do to nothing.
	 * 
	 * @see org.eclipse.wst.xml.ui.internal.contentassist.AbstractXMLCompletionProposalComputer#addEmptyDocumentProposals(org.eclipse.wst.xml.ui.internal.contentassist.ContentAssistRequest, org.eclipse.wst.sse.ui.contentassist.CompletionProposalInvocationContext)
	 */
	@Override
	protected void addEmptyDocumentProposals(
			ContentAssistRequest contentAssistRequest,
			CompletionProposalInvocationContext context) {
		//default behavior is to do nothing
	}

	/**
	 * Default behavior is do to nothing.
	 * 
	 * @see org.eclipse.wst.xml.ui.internal.contentassist.AbstractXMLCompletionProposalComputer#addEndTagNameProposals(org.eclipse.wst.xml.ui.internal.contentassist.ContentAssistRequest, org.eclipse.wst.sse.ui.contentassist.CompletionProposalInvocationContext)
	 */
	@Override
	protected void addEndTagNameProposals(
			ContentAssistRequest contentAssistRequest,
			CompletionProposalInvocationContext context) {
		//default behavior is to do nothing
	}

	/**
	 * Default behavior is do to nothing.
	 * 
	 * @see org.eclipse.wst.xml.ui.internal.contentassist.AbstractXMLCompletionProposalComputer#addEndTagProposals(org.eclipse.wst.xml.ui.internal.contentassist.ContentAssistRequest, org.eclipse.wst.sse.ui.contentassist.CompletionProposalInvocationContext)
	 */
	@Override
	protected void addEndTagProposals(
			ContentAssistRequest contentAssistRequest,
			CompletionProposalInvocationContext context) {
		//default behavior is to do nothing
	}

	/**
	 * Default behavior is do to nothing.
	 * 
	 * @see org.eclipse.wst.xml.ui.internal.contentassist.AbstractXMLCompletionProposalComputer#addEntityProposals(org.eclipse.wst.xml.ui.internal.contentassist.ContentAssistRequest, org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion, org.eclipse.wst.xml.core.internal.provisional.document.IDOMNode, org.eclipse.wst.sse.ui.contentassist.CompletionProposalInvocationContext)
	 */
	@Override
	protected void addEntityProposals(
			ContentAssistRequest contentAssistRequest,
			ITextRegion completionRegion, IDOMNode treeNode,
			CompletionProposalInvocationContext context) {
		//default behavior is to do nothing
	}

	/**
	 * Default behavior is do to nothing.
	 * 
	 * @see org.eclipse.wst.xml.ui.internal.contentassist.AbstractXMLCompletionProposalComputer#addEntityProposals(java.util.Vector, java.util.Properties, java.lang.String, int, org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion, org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion, org.eclipse.wst.sse.ui.contentassist.CompletionProposalInvocationContext)
	 */
	@Override
	protected void addEntityProposals(Vector<ICompletionProposal> proposals, Properties map,
			String key, int nodeOffset, IStructuredDocumentRegion sdRegion,
			ITextRegion completionRegion,
			CompletionProposalInvocationContext context) {
		//default behavior is to do nothing
	}

	/**
	 * Default behavior is do to nothing.
	 * 
	 * @see org.eclipse.wst.xml.ui.internal.contentassist.AbstractXMLCompletionProposalComputer#addPCDATAProposal(java.lang.String, org.eclipse.wst.xml.ui.internal.contentassist.ContentAssistRequest, org.eclipse.wst.sse.ui.contentassist.CompletionProposalInvocationContext)
	 */
	@Override
	protected void addPCDATAProposal(String nodeName,
			ContentAssistRequest contentAssistRequest,
			CompletionProposalInvocationContext context) {
		//default behavior is to do nothing
	}

	/**
	 * Default behavior is do to nothing.
	 * 
	 * @see org.eclipse.wst.xml.ui.internal.contentassist.AbstractXMLCompletionProposalComputer#addStartDocumentProposals(org.eclipse.wst.xml.ui.internal.contentassist.ContentAssistRequest, org.eclipse.wst.sse.ui.contentassist.CompletionProposalInvocationContext)
	 */
	@Override
	protected void addStartDocumentProposals(
			ContentAssistRequest contentAssistRequest,
			CompletionProposalInvocationContext context) {
		//default behavior is to do nothing
	}

	/**
	 * Default behavior is do to nothing.
	 * 
	 * @see org.eclipse.wst.xml.ui.internal.contentassist.AbstractXMLCompletionProposalComputer#addTagCloseProposals(org.eclipse.wst.xml.ui.internal.contentassist.ContentAssistRequest, org.eclipse.wst.sse.ui.contentassist.CompletionProposalInvocationContext)
	 */
	@Override
	protected void addTagCloseProposals(
			ContentAssistRequest contentAssistRequest,
			CompletionProposalInvocationContext context) {
		//default behavior is to do nothing
	}

	/**
	 * Default behavior is do to nothing.
	 * 
	 * @see org.eclipse.wst.xml.ui.internal.contentassist.AbstractXMLCompletionProposalComputer#addTagInsertionProposals(org.eclipse.wst.xml.ui.internal.contentassist.ContentAssistRequest, int, org.eclipse.wst.sse.ui.contentassist.CompletionProposalInvocationContext)
	 */
	@Override
	protected void addTagInsertionProposals(
			ContentAssistRequest contentAssistRequest, int childPosition,
			CompletionProposalInvocationContext context) {
		//default behavior is to do nothing
	}

	/**
	 * Default behavior is do to nothing.
	 * 
	 * @see org.eclipse.wst.xml.ui.internal.contentassist.AbstractXMLCompletionProposalComputer#addTagNameProposals(org.eclipse.wst.xml.ui.internal.contentassist.ContentAssistRequest, int, org.eclipse.wst.sse.ui.contentassist.CompletionProposalInvocationContext)
	 */
	@Override
	protected void addTagNameProposals(
			ContentAssistRequest contentAssistRequest, int childPosition,
			CompletionProposalInvocationContext context) {
		//default behavior is to do nothing
	}
}
