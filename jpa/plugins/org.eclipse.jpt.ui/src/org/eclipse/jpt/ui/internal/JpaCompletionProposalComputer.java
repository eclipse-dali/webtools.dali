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

import java.util.Collections;
import java.util.List;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.ui.text.java.ContentAssistInvocationContext;
import org.eclipse.jdt.ui.text.java.IJavaCompletionProposalComputer;
import org.eclipse.jdt.ui.text.java.JavaContentAssistInvocationContext;
import org.eclipse.jpt.core.internal.IJpaFile;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.core.internal.JpaCorePlugin;
import org.eclipse.jpt.core.internal.content.java.JavaPersistentType;
import org.eclipse.jpt.core.internal.content.java.JpaCompilationUnit;

public class JpaCompletionProposalComputer implements IJavaCompletionProposalComputer {

	public JpaCompletionProposalComputer() {
		super();
	}

	public void sessionStarted() {
		// do nothing
	}

	@SuppressWarnings("unchecked")
	public List computeCompletionProposals(ContentAssistInvocationContext context, IProgressMonitor monitor) {
//		this.xxx((JavaContentAssistInvocationContext) context);
		return Collections.EMPTY_LIST;
	}

	private void xxx(JavaContentAssistInvocationContext context) {
		try {
			this.xxx_(context);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	private void xxx_(JavaContentAssistInvocationContext context) throws Exception {
		System.out.println(context.getCoreContext());
//		System.out.println("identifier prefix: " + context.computeIdentifierPrefix());
//		System.out.println("select length: " + context.getCompilationUnit().codeSelect(context.getInvocationOffset(), 0).length);

		ICompilationUnit cu = context.getCompilationUnit();
		IResource resource = cu.getCorrespondingResource();
		System.out.println("CU resource: " + resource);
		IFile file = (IFile) resource;

		IJpaFile jpaFile = JpaCorePlugin.getJpaFile(file);
		System.out.println("JPA file: " + jpaFile);
		
		JpaCompilationUnit jpaCU = (JpaCompilationUnit) jpaFile.getContent();
		System.out.println("java persistent type: " + jpaCU.candidateValuesFor(context.getInvocationOffset()));
		
		IJpaProject jpaProject = jpaFile.getJpaProject();
		System.out.println("JPA project: " + jpaProject);
		
		IType iType = cu.findPrimaryType();
		JavaPersistentType pType = jpaProject.findJavaPersistentType(iType);
		System.out.println("java persistent type: " + pType);
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

}
