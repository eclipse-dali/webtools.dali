/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.filebuffers.ITextFileBuffer;
import org.eclipse.core.filebuffers.ITextFileBufferManager;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.XmlFile;
import org.eclipse.jpt.jpa.ui.JpaWorkbench;
import org.eclipse.jpt.jpa.ui.JptJpaUiImages;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.PlatformUI;
import org.eclipse.wst.sse.ui.contentassist.CompletionProposalInvocationContext;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMDocument;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMElement;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMNode;
import org.eclipse.wst.xml.ui.internal.XMLUIMessages;
import org.eclipse.wst.xml.ui.internal.contentassist.ContentAssistRequest;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * This computer adds content assist support for a mapping file (ORM Configuration).
 *
 * @version 2.3
 * @since 2.3
 */
public class JpaXmlCompletionProposalComputer extends DefaultJpaXmlCompletionProposalComputer {

	public JpaXmlCompletionProposalComputer() {
	}

	@Override
	public List<ICompletionProposal> computeCompletionProposals(
			CompletionProposalInvocationContext context,
			IProgressMonitor monitor) {
		try {
			return super.computeCompletionProposals(context, monitor);
		} catch (RuntimeException ex) {
			// When we run into any unexpected exception, we will log the exception
			// and then return an empty list to prevent code completion process from 
			// crashing. We need to determine if runtime exceptions should be 
			// expected. If so, we could remove the log(ex) in the future.
			JptJpaUiPlugin.instance().logError(ex);
			return Collections.emptyList();
		}
	}

	@Override
	protected void addAttributeValueProposals(ContentAssistRequest contentAssistRequest,
			CompletionProposalInvocationContext context) {

		List<String> proposedValues = getProposedValues(context);

		if (proposedValues.size() > 0 ) {
			String matchString = contentAssistRequest.getMatchString();
			if (matchString == null) {
				matchString = ""; //$NON-NLS-1$
			}

			// initialize newMatchingString to an empty string to handle the case when user invokes code assist without giving delimiter
			String newMatchString = ""; //$NON-NLS-1$
			if ((matchString.length() > 0) && (matchString.startsWith("\"") || matchString.startsWith("'"))) { //$NON-NLS-1$ //$NON-NLS-2$
				newMatchString = matchString.substring(1);
			}
			//create completion proposals for this attribute value declaration
			int rOffset = contentAssistRequest.getReplacementBeginPosition();
			int rLength = contentAssistRequest.getReplacementLength();
			for (String possibleValue : proposedValues) {
				if ((newMatchString.length() == 0) || StringTools.startsWithIgnoreCase(possibleValue, newMatchString)) {

					// handle values that include special characters like double-quote or apostrophe
					String convertedPossibleValue = null;
					if (matchString.startsWith("\"")) { //$NON-NLS-1$
						convertedPossibleValue = StringTools.convertToDoubleQuotedXmlAttributeValue(possibleValue);
					} else if (matchString.startsWith("'")) { //$NON-NLS-1$
						convertedPossibleValue = StringTools.convertToSingleQuotedXmlAttributeValue(possibleValue);
					} else {
						convertedPossibleValue = StringTools.convertToXmlAttributeValue(possibleValue);
					}

					CompletionProposal proposal = null;
					// give users an additional message if value includes special characters since what is written
					//  to XML is most likely different from what is shown in the proposal list with this case
					if (possibleValue.startsWith("\"")) { //$NON-NLS-1$
						// handle the case when user does ""<invoke code assist here>" trying to get these special values
						// User cannot do ""F<invoke code assist here>" or ""F<invoke code assist here>"" trying to
						// get these values that are special and start with F because XML would regard the F as the 
						// start of another attribute since there are two double-quotes exist before F.
						if (matchString.startsWith("\"") && newMatchString.startsWith("\"")) { //$NON-NLS-1$ //$NON-NLS-2$
							proposal = new CompletionProposal(
									convertedPossibleValue, rOffset, rLength + 1, convertedPossibleValue.length(), null, 
									possibleValue, null, JptJpaUiMessages.JpaXmlCompletionProposalComputer_SpecialNameMsg);
						} else {
							proposal = new CompletionProposal(
									convertedPossibleValue, rOffset, rLength, convertedPossibleValue.length(), null, 
									possibleValue, null, JptJpaUiMessages.JpaXmlCompletionProposalComputer_SpecialNameMsg);
						}
					} else {
						// do not give the addition message with normal values
						proposal = new CompletionProposal(
								convertedPossibleValue, rOffset, rLength, convertedPossibleValue.length(), null, 
								possibleValue, null, null );
					}
					
					contentAssistRequest.addProposal(proposal);
				}
			}
		}
		else {
			setErrorMessage(XMLUIMessages.Content_Assist_not_availab_UI_);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void addTagInsertionProposals(
			ContentAssistRequest contentAssistRequest, int childPosition,
			CompletionProposalInvocationContext context) {

		Node parent = contentAssistRequest.getParent();

		// This is only valid at the document element level
		// and only valid if it's a XML file
		if ((parent != null) && (parent.getNodeType() == Node.DOCUMENT_NODE) &&
				((IDOMDocument) parent).isXMLType() && !isCursorAfterXMLPI(contentAssistRequest)) {
			return;
		}
		// only want completion proposals if cursor is after doctype...
		if (!isCursorAfterDoctype(contentAssistRequest)) {
			return;
		}

		if ((parent != null) && (parent instanceof IDOMNode) && isCommentNode((IDOMNode) parent)) {
			// loop and find non comment node?
			while ((parent != null) && isCommentNode((IDOMNode) parent)) {
				parent = parent.getParentNode();
			}
		}

		List<String> proposedValues = getProposedValues(context);

		if (proposedValues.size() > 0 ) {
			String matchString = contentAssistRequest.getMatchString();
			if (matchString == null) {
				matchString = ""; //$NON-NLS-1$
			}

			int begin = contentAssistRequest.getReplacementBeginPosition();
			int length = contentAssistRequest.getReplacementLength();
			if ((parent instanceof IDOMNode) && (parent.getNodeType() == Node.ELEMENT_NODE)) {
				if (((IDOMNode) parent).getLastStructuredDocumentRegion() != ((IDOMNode) parent).getFirstStructuredDocumentRegion()) {
					begin = ((IDOMNode) parent).getFirstStructuredDocumentRegion().getEndOffset();
					length = ((IDOMNode) parent).getLastStructuredDocumentRegion().getStartOffset() - begin;
				}
			}

			for (String proposedValue : proposedValues) {
				
				if ((matchString.length() == 0) || StringTools.startsWithIgnoreCase(proposedValue, matchString)) {
					String convertedProposedValue = StringTools.convertToXmlElementText(proposedValue);

					CompletionProposal proposal = null;
					if (proposedValue.startsWith("\"")) { //$NON-NLS-1$
						proposal = new CompletionProposal(
								convertedProposedValue, begin, length, convertedProposedValue.length(), 
								this.getImage(context, JptJpaUiImages.JPA_CONTENT), proposedValue, null, 
								JptJpaUiMessages.JpaXmlCompletionProposalComputer_SpecialNameMsg);
					} else {
						proposal = new CompletionProposal(
								convertedProposedValue, begin, length, convertedProposedValue.length(), 
								this.getImage(context, JptJpaUiImages.JPA_CONTENT), proposedValue, null, null);
					}

					contentAssistRequest.addProposal(proposal);
				}
			}
		}
		else {
			setErrorMessage(XMLUIMessages.Content_Assist_not_availab_UI_);
		}
	}

	/**
	 * Retrieves all of the possible valid values for this attribute/element declaration
	 */
	private List<String> getProposedValues(CompletionProposalInvocationContext context) {
		int documentPosition = context.getInvocationOffset();
		if (documentPosition == -1) return Collections.emptyList();

		ITextFileBufferManager manager = FileBuffers.getTextFileBufferManager();
		ITextFileBuffer buffer = manager.getTextFileBuffer(context.getDocument());
		if (buffer == null) return Collections.emptyList();

		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IFile file = root.getFile(buffer.getLocation());

		JpaFile jpaFile = (JpaFile) file.getAdapter(JpaFile.class);
		if (jpaFile == null) return Collections.emptyList();

		Collection<JpaStructureNode> rootStructureNodes = CollectionTools.collection(jpaFile.getRootStructureNodes());
		if (rootStructureNodes.isEmpty()) {
			return Collections.emptyList();
		}

		List<String> list = new ArrayList<String>();
		for (JpaStructureNode node : rootStructureNodes) {
			Iterable<String> proposals = ((XmlFile.Root) node).getCompletionProposals(documentPosition);
			if (proposals != null) {
				CollectionTools.addAll(list, proposals);
			}
		}
		return list;
	}

	/**
	 * This method can check if the cursor is after the XMLPI
	 */
	protected boolean isCursorAfterXMLPI(ContentAssistRequest car) {
		Node aNode = car.getNode();
		boolean xmlpiFound = false;
		Document parent = aNode.getOwnerDocument();
		int xmlpiNodePosition = -1;
		boolean isAfterXMLPI = false;

		if (parent == null) {
			return true; // blank document case
		}

		for (Node child = parent.getFirstChild(); child != null; child = child.getNextSibling()) {
			boolean xmlpi = ((child.getNodeType() == Node.PROCESSING_INSTRUCTION_NODE) && child.getNodeName().equals("xml")); //$NON-NLS-1$
			xmlpiFound = xmlpiFound || xmlpi;
			if (xmlpiFound) {
				if (child instanceof IDOMNode) {
					xmlpiNodePosition = ((IDOMNode) child).getEndOffset();
					isAfterXMLPI = (car.getReplacementBeginPosition() >= xmlpiNodePosition);
				}
				break;
			}
		}
		return isAfterXMLPI;
	}

	private boolean isCursorAfterDoctype(ContentAssistRequest car) {
		Node aNode = car.getNode();
		Document parent = aNode.getOwnerDocument();
		int xmldoctypeNodePosition = -1;
		boolean isAfterDoctype = true;

		if (parent == null) {
			return true; // blank document case
		}

		for (Node child = parent.getFirstChild(); child != null; child = child.getNextSibling()) {
			if (child instanceof IDOMNode) {
				if (child.getNodeType() == Node.DOCUMENT_TYPE_NODE) {
					xmldoctypeNodePosition = ((IDOMNode) child).getEndOffset();
					isAfterDoctype = (car.getReplacementBeginPosition() >= xmldoctypeNodePosition);
					break;
				}
			}
		}
		return isAfterDoctype;
	}

	/**
	 * This is to determine if a tag is a special meta-info comment tag that
	 * shows up as an ELEMENT
	 */
	private boolean isCommentNode(IDOMNode node) {
		return ((node != null) && (node instanceof IDOMElement) && ((IDOMElement) node).isCommentTag());
	}

	private Image getImage(CompletionProposalInvocationContext context, ImageDescriptor descriptor) {
		return this.getImage(context.getViewer().getTextWidget(), descriptor);
	}

	private Image getImage(Control control, ImageDescriptor descriptor) {
		return this.getResourceManager(control).createImage(descriptor);
	}

	private ResourceManager getResourceManager(Control control) {
		return this.getJpaWorkbench().getResourceManager(control);
	}

	private JpaWorkbench getJpaWorkbench() {
		return PlatformTools.getAdapter(PlatformUI.getWorkbench(), JpaWorkbench.class);
	}
}
