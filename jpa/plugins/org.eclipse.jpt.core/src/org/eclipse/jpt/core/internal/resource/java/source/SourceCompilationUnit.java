/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java.source;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.JptResourceModelListener;
import org.eclipse.jpt.common.core.internal.utility.jdt.ASTTools;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationEditFormatter;
import org.eclipse.jpt.core.JpaAnnotationProvider;
import org.eclipse.jpt.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.utility.CommandExecutor;
import org.eclipse.jpt.utility.internal.ListenerList;

/**
 * Java compilation unit (source file)
 */
public abstract class SourceCompilationUnit
	extends SourceNode
	implements JavaResourceCompilationUnit
{
	/** JDT compilation unit */
	final ICompilationUnit compilationUnit;

	/** pluggable annotation provider */
	private final JpaAnnotationProvider annotationProvider;

	/** improved annotation formatting */
	private final AnnotationEditFormatter annotationEditFormatter;

	/** pluggable executor that allows the document to be modified on another thread */
	private final CommandExecutor modifySharedDocumentCommandExecutor;

	/** listeners notified whenever the resource model changes */
	private final ListenerList<JptResourceModelListener> resourceModelListenerList;


	// ********** construction **********

	protected SourceCompilationUnit(
			ICompilationUnit compilationUnit,
			JpaAnnotationProvider annotationProvider, 
			AnnotationEditFormatter annotationEditFormatter,
			CommandExecutor modifySharedDocumentCommandExecutor) {
		super(null);  // the JPA compilation unit is the root of its sub-tree
		this.compilationUnit = compilationUnit;
		this.annotationProvider = annotationProvider;
		this.annotationEditFormatter = annotationEditFormatter;
		this.modifySharedDocumentCommandExecutor = modifySharedDocumentCommandExecutor;
		this.resourceModelListenerList = new ListenerList<JptResourceModelListener>(JptResourceModelListener.class);
	}

	public void initialize(CompilationUnit astRoot) {
		// never called?
	}

	void openCompilationUnit() {
		try {
			this.compilationUnit.open(null);
		} catch (JavaModelException ex) {
			// do nothing - we just won't have a primary type in this case
		}
	}

	void closeCompilationUnit() {
		try {
			this.compilationUnit.close();
		} catch (JavaModelException ex) {
			// hmmm
		}
	}


	// ********** AbstractJavaResourceNode overrides **********

	@Override
	protected boolean requiresParent() {
		return false;
	}

	@Override
	public JavaResourceCompilationUnit getRoot() {
		return this;
	}

	@Override
	public IFile getFile() {
		return (IFile) this.compilationUnit.getResource();
	}
	
	@Override
	public JpaAnnotationProvider getAnnotationProvider() {
		return this.annotationProvider;
	}
	

	// ********** JavaResourceNode implementation **********

	public TextRange getTextRange(CompilationUnit astRoot) {
		return null;
	}


	// ********** JavaResourceNode.Root implementation **********

	public void resourceModelChanged() {
		for (JptResourceModelListener listener : this.resourceModelListenerList.getListeners()) {
			listener.resourceModelChanged(this);
		}
	}


	// ********** JavaResourceCompilationUnit implementation **********

	public ICompilationUnit getCompilationUnit() {
		return this.compilationUnit;
	}

	public CommandExecutor getModifySharedDocumentCommandExecutor() {
		return this.modifySharedDocumentCommandExecutor;
	}
	
	public AnnotationEditFormatter getAnnotationEditFormatter()  {
		return this.annotationEditFormatter;
	}
	
	@Override
	public CompilationUnit buildASTRoot() {
		return ASTTools.buildASTRoot(this.compilationUnit);
	}


	// ********** JptResourceModel implementation **********

	public void addResourceModelListener(JptResourceModelListener listener) {
		this.resourceModelListenerList.add(listener);
	}

	public void removeResourceModelListener(JptResourceModelListener listener) {
		this.resourceModelListenerList.remove(listener);
	}


	// ********** Java changes **********

	public void synchronizeWithJavaSource() {
		this.synchronizeWith(this.buildASTRoot());
	}


	// ********** internal **********

	String getCompilationUnitName() {
		return this.removeJavaExtension(this.compilationUnit.getElementName());
	}

	private String removeJavaExtension(String fileName) {
		int index = fileName.lastIndexOf(".java"); //$NON-NLS-1$
		return (index == -1) ? fileName : fileName.substring(0, index);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getCompilationUnitName());
	}

}
