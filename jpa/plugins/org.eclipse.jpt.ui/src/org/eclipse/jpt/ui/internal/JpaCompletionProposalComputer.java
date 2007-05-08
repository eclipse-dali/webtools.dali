/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
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
import org.eclipse.jpt.core.internal.IJpaFile;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.content.java.JpaCompilationUnit;
import org.eclipse.jpt.utility.internal.Filter;
import org.eclipse.jpt.utility.internal.StringTools;

public class JpaCompletionProposalComputer implements IJavaCompletionProposalComputer {

	public JpaCompletionProposalComputer() {
		super();
	}

	public void sessionStarted() {
		// do nothing
	}

	@SuppressWarnings("unchecked")
	public List computeCompletionProposals(ContentAssistInvocationContext context, IProgressMonitor monitor) {
		return (context instanceof JavaContentAssistInvocationContext) ?
			this.computeCompletionProposals((JavaContentAssistInvocationContext) context)
		:
			Collections.emptyList();
	}

	private List<ICompletionProposal> computeCompletionProposals(JavaContentAssistInvocationContext context) {
		try {
			return this.computeCompletionProposals_(context);
		} catch (JavaModelException ex) {
			throw new RuntimeException(ex);
		}
	}

	private List<ICompletionProposal> computeCompletionProposals_(JavaContentAssistInvocationContext context) throws JavaModelException {
		ICompilationUnit cu = context.getCompilationUnit();
		if (cu == null) {
			return Collections.emptyList();
		}

		IJpaFile jpaFile = JptCorePlugin.getJpaFile((IFile) cu.getCorrespondingResource());
		if (jpaFile == null) {
			return Collections.emptyList();
		}

		JpaCompilationUnit jpaCU = (JpaCompilationUnit) jpaFile.getContent();
		CompletionContext cc = context.getCoreContext();

		// the context's "token" is really a sort of "prefix" - it does NOT
		// correspond to the "start" and "end" we get below... 
		char[] prefix = cc.getToken();
		Filter<String> filter = ((prefix == null) ? Filter.Null.<String>instance() : new IgnoreCasePrefixFilter(prefix));
		// the token "start" is the offset of the token's first character
		int tokenStart = cc.getTokenStart();
		// the token "end" is the offset of the token's last character (yuk)
		int tokenEnd = cc.getTokenEnd();

//		System.out.println("prefix: " + new String(prefix));
//		System.out.println("token start: " + tokenStart);
//		System.out.println("token end: " + tokenEnd);
//		String source = cu.getSource();
//		String token = source.substring(tokenStart, tokenEnd + 1);
//		System.out.println("token: =>" + token + "<=");
//		String snippet = source.substring(Math.max(0, tokenStart - 20), Math.min(source.length(), tokenEnd + 21));
//		System.out.println("surrounding snippet: =>" + snippet + "<=");

		List<ICompletionProposal> proposals = new ArrayList<ICompletionProposal>();
		for (Iterator<String> stream = jpaCU.candidateValuesFor(context.getInvocationOffset(), filter); stream.hasNext(); ) {
			String s = stream.next();
			proposals.add(new CompletionProposal(s, tokenStart, tokenEnd - tokenStart + 1, s.length()));
		}
		return proposals;
	}

	@SuppressWarnings("unchecked")
	public List computeContextInformation(ContentAssistInvocationContext context, IProgressMonitor monitor) {
		return Collections.EMPTY_LIST;
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
			return StringTools.stringStartsWithIgnoreCase(s.toCharArray(), prefix);
		}
	}

}
