/*******************************************************************************
 * Copyright (c) 2007, 2015 Oracle. All rights reserved.
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
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.CompletionContext;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.text.java.ContentAssistInvocationContext;
import org.eclipse.jdt.ui.text.java.IJavaCompletionProposalComputer;
import org.eclipse.jdt.ui.text.java.JavaContentAssistInvocationContext;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;

/**
 * JPA Java code-completion proposal computer
 */
public class JpaJavaCompletionProposalComputer
	implements IJavaCompletionProposalComputer
{
	public JpaJavaCompletionProposalComputer() {
		super();
	}

	public void sessionStarted() {
		// do nothing
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List computeCompletionProposals(ContentAssistInvocationContext context, IProgressMonitor monitor) {
		return (context instanceof JavaContentAssistInvocationContext) ?
					this.computeCompletionProposals((JavaContentAssistInvocationContext) context) :
					Collections.emptyList();
	}

	/**
	 * We fail silently here because (it seems) <em>expected</em> exceptions occur
	 * more frequently than intermittent <em>unexpected</em> exceptions that might
	 * merit investigation (and a logged stacktrace might be the only hint as
	 * to what happened).
	 * <p>
	 * We will get an <em>expected</em> exception (typically a {@link NullPointerException NPE})
	 * here if the user:<ol>
	 * <li>modifies the Java source file in a way that puts it drastically out
	 *     of sync with the Dali context model (e.g. deleting a field or
	 *     annotation)
	 * <li>immediately invokes Content Assist (typically <code>Ctrl+Space</code>)
	 * </ol>
	 * The AST we build here will be based on the just-modified Java source; but,
	 * since the user moved quickly and we will not have yet received any Java
	 * change notification (since we only get a Java change notification when
	 * the user has paused typing for at least 0.5 seconds), the context model
	 * will still be based on the unmodified Java source. As the new AST is
	 * passed down through the context model to the resource model all the code
	 * expects to find the AST in sync with the model. When this is not the
	 * case (e.g. a field in the resource model is no longer present in the AST
	 * because the user has deleted it or modified the code in such a way that
	 * the parser can no longer detect the field) the model will probably choke
	 * when it cannot find the corresponding AST node.
	 * <p>
	 * It seems reasonable, in these situations, to simply return no completion
	 * proposals. If the user simply waits a moment and tries again, we will be
	 * able to successfully calculate some proposals.
	 * <p>
	 * ~bjv
	 */
	private List<ICompletionProposal> computeCompletionProposals(JavaContentAssistInvocationContext context) {
		try {
			return this.computeCompletionProposals_(context);
		} catch (RuntimeException ex) {
			// JptJpaUiPlugin.log(ex);  // don't log "expected" exceptions (?)
			return Collections.emptyList();
		}
	}

	private List<ICompletionProposal> computeCompletionProposals_(JavaContentAssistInvocationContext context) {
		ICompilationUnit cu = context.getCompilationUnit();
		if (cu == null) {
			return Collections.emptyList();
		}

		IFile file = this.getCorrespondingResource(cu);
		if (file == null) {
			return Collections.emptyList();
		}

		JpaFile jpaFile = this.getJpaFile(file);
		if (jpaFile == null) {
			return Collections.emptyList();
		}
		
		Collection<JpaStructureNode> rootStructureNodes = CollectionTools.hashBag(jpaFile.getRootStructureNodes());
		if (rootStructureNodes.isEmpty()) {
			return Collections.emptyList();
		}

		CompletionContext cc = context.getCoreContext();

		// the context's "token" is really a sort of "prefix" - it does NOT
		// correspond to the "start" and "end" we get below... 
		char[] prefix = cc.getToken();
		Predicate<String> filter = this.buildPrefixFilter(prefix);
		// the token "kind" tells us if we are in a String literal already - CompletionContext.TOKEN_KIND_STRING_LITERAL
		int tokenKind = cc.getTokenKind();
		// the token "start" is the offset of the token's first character
		int tokenStart = cc.getTokenStart();
		// the token "end" is the offset of the token's last character (yuk)
		int tokenEnd = cc.getTokenEnd();
		if (tokenStart == -1) {  // not sure why this happens - see bug 242286
			return Collections.emptyList();
		}

//		System.out.println("token start: " + tokenStart);
//		System.out.println("token end: " + tokenEnd);
//		System.out.println("token kind: " + tokenKind);
//		String source = cu.getSource();
//		String token = source.substring(Math.max(0, tokenStart), Math.min(source.length(), tokenEnd + 1));
//		System.out.println("token: =>" + token + "<=");
//		String snippet = source.substring(Math.max(0, tokenStart - 20), Math.min(source.length(), tokenEnd + 21));
//		System.out.println("surrounding snippet: =>" + snippet + "<=");

		List<ICompletionProposal> proposals = new ArrayList<ICompletionProposal>();
		for (JpaStructureNode structureNode : rootStructureNodes) {
			for (String s : this.buildCompletionProposals((JavaPersistentType) structureNode, context.getInvocationOffset(), filter)) {
				if (tokenKind == CompletionContext.TOKEN_KIND_STRING_LITERAL) {//already quoted
					proposals.add(new CompletionProposal(s , tokenStart + 1, tokenEnd - tokenStart - 1, s.length()));					
				}
				else {//add the quotes
					proposals.add(new CompletionProposal("\"" + s + "\"", tokenStart, tokenEnd - tokenStart + 1, s.length() + 2)); //$NON-NLS-1$ //$NON-NLS-2$
				}
			}
		}
		return proposals;
	}

	private Iterable<String> buildCompletionProposals(JavaPersistentType structureNode, int pos, Predicate<String> filter) {
		return IterableTools.filter(structureNode.getCompletionProposals(pos), filter);
	}

	private IFile getCorrespondingResource(ICompilationUnit cu) {
		try {
			return (IFile) cu.getCorrespondingResource();
		} catch (JavaModelException ex) {
			JptJpaUiPlugin.instance().logError(ex);
			return null;
		}
	}

	private JpaFile getJpaFile(IFile file) {
		return (JpaFile) file.getAdapter(JpaFile.class);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List computeContextInformation(ContentAssistInvocationContext context, IProgressMonitor monitor) {
		return Collections.emptyList();
	}

	public String getErrorMessage() {
		return null;
	}

	public void sessionEnded() {
		// do nothing
	}

	private Predicate<String> buildPrefixFilter(char[] prefix) {
		return (prefix == null) ?
				PredicateTools.<String>true_() :
				new StringTools.StartsWithIgnoreCase(new String(prefix));
	}
}
