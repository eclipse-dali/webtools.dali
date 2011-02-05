/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jdt.core.CompletionContext;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.ui.text.java.ContentAssistInvocationContext;
import org.eclipse.jdt.ui.text.java.IJavaCompletionProposalComputer;
import org.eclipse.jdt.ui.text.java.JavaContentAssistInvocationContext;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jpt.common.core.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jpt.common.core.internal.utility.jdt.ASTTools;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.JptJaxbCorePlugin;
import org.eclipse.jpt.jaxb.core.context.java.JavaContextNode;
import org.eclipse.jpt.jaxb.ui.JptJaxbUiPlugin;


public class JaxbJavaCompletionProposalComputer
		implements IJavaCompletionProposalComputer {

	public JaxbJavaCompletionProposalComputer() {
		super();
	}
	
	
	public void sessionStarted() {
		// do nothing
	}
	
	@SuppressWarnings("unchecked")
	public List computeCompletionProposals(ContentAssistInvocationContext context, IProgressMonitor monitor) {
		return (context instanceof JavaContentAssistInvocationContext) ?
				computeCompletionProposals((JavaContentAssistInvocationContext) context)
				: Collections.emptyList();
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
			return computeCompletionProposals_(context);
		} 
		catch (Exception ex) {
			// JptJaxbCorePlugin.log(ex);  // don't log "expected" exceptions (?)
			return Collections.emptyList();
		}
	}
	
	private List<ICompletionProposal> computeCompletionProposals_(JavaContentAssistInvocationContext context) {
		ICompilationUnit cu = context.getCompilationUnit();
		IFile file = (cu != null) ? getCorrespondingResource(cu) : null;
		IContentType contentType = (file != null) ? PlatformTools.getContentType(file) : null;
		
		if (contentType == null  || ! contentType.isKindOf(JptCommonCorePlugin.JAVA_SOURCE_CONTENT_TYPE)) {
			return Collections.emptyList();
		}
		
		JaxbProject jaxbProject = JptJaxbCorePlugin.getJaxbProject(file.getProject());
		if (jaxbProject == null) {
			return Collections.emptyList();
		}
		
		Iterable<? extends JavaContextNode> javaNodes = jaxbProject.getPrimaryJavaNodes(cu);
		if (CollectionTools.isEmpty(javaNodes)) {
			return Collections.emptyList();
		}

		CompletionContext cc = context.getCoreContext();

		// the context's "token" is really a sort of "prefix" - it does NOT
		// correspond to the "start" and "end" we get below... 
		char[] prefix = cc.getToken();
		Filter<String> filter = ((prefix == null) ? Filter.Null.<String>instance() : new IgnoreCasePrefixFilter(prefix));
		// the token "start" is the offset of the token's first character
		int tokenStart = cc.getTokenStart();
		// the token "end" is the offset of the token's last character (yuk)
		int tokenEnd = cc.getTokenEnd();
		if (tokenStart == -1) {  // not sure why this happens - see bug 242286
			return Collections.emptyList();
		}
		
//		System.out.println("prefix: " + ((prefix == null) ? "[null]" : new String(prefix)));
//		System.out.println("token start: " + tokenStart);
//		System.out.println("token end: " + tokenEnd);
//		String source = cu.getSource();
//		String token = source.substring(Math.max(0, tokenStart), Math.min(source.length(), tokenEnd + 1));
//		System.out.println("token: =>" + token + "<=");
//		String snippet = source.substring(Math.max(0, tokenStart - 20), Math.min(source.length(), tokenEnd + 21));
//		System.out.println("surrounding snippet: =>" + snippet + "<=");

		// TODO move this parser call into the model...
		CompilationUnit astRoot = ASTTools.buildASTRoot(cu);
		List<ICompletionProposal> proposals = new ArrayList<ICompletionProposal>();
		for (JavaContextNode javaNode : javaNodes) {
			for (String proposal : javaNode.getJavaCompletionProposals(context.getInvocationOffset(), filter, astRoot)) {
				proposals.add(new CompletionProposal(proposal, tokenStart, tokenEnd - tokenStart + 1, proposal.length()));
			}
		}
		return proposals;
	}

	private IFile getCorrespondingResource(ICompilationUnit cu) {
		try {
			return (IFile) cu.getCorrespondingResource();
		} catch (JavaModelException ex) {
			JptJaxbUiPlugin.log(ex);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List computeContextInformation(ContentAssistInvocationContext context, IProgressMonitor monitor) {
		return Collections.emptyList();
	}

	public String getErrorMessage() {
		return null;
	}

	public void sessionEnded() {
		// do nothing
	}

	private static class IgnoreCasePrefixFilter implements Filter<String> {
		private final char[] prefix;
		IgnoreCasePrefixFilter(char[] prefix) {
			super();
			this.prefix = prefix;
		}
		public boolean accept(String s) {
			return StringTools.stringStartsWithIgnoreCase(s.toCharArray(), this.prefix);
		}
	}

}
