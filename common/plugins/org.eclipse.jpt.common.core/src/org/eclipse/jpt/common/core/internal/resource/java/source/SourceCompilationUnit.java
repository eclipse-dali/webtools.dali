/*******************************************************************************
 * Copyright (c) 2007, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.resource.java.source;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.AnnotationProvider;
import org.eclipse.jpt.common.core.JptResourceModelListener;
import org.eclipse.jpt.common.core.internal.plugin.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.internal.utility.jdt.ASTTools;
import org.eclipse.jpt.common.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationEditFormatter;
import org.eclipse.jpt.common.utility.command.CommandContext;
import org.eclipse.jpt.common.utility.internal.ListenerList;
import org.eclipse.jpt.common.utility.internal.model.ModelTools;

/**
 * Java compilation unit (source file)
 */
public abstract class SourceCompilationUnit
	extends SourceModel
	implements JavaResourceCompilationUnit
{
	/** JDT compilation unit */
	final ICompilationUnit compilationUnit;

	/** pluggable annotation provider */
	private final AnnotationProvider annotationProvider;

	/** improved annotation formatting */
	private final AnnotationEditFormatter annotationEditFormatter;

	/** pluggable context that allows the document to be modified on another thread */
	private final CommandContext modifySharedDocumentCommandContext;

	/** listeners notified whenever the resource model changes */
	private final ListenerList<JptResourceModelListener> resourceModelListenerList = ModelTools.listenerList();


	// ********** construction **********

	protected SourceCompilationUnit(
			ICompilationUnit compilationUnit,
			AnnotationProvider annotationProvider, 
			AnnotationEditFormatter annotationEditFormatter,
			CommandContext modifySharedDocumentCommandContext) {
		super(null);  // the JPA compilation unit is the root of its sub-tree
		this.compilationUnit = compilationUnit;
		this.annotationProvider = annotationProvider;
		this.annotationEditFormatter = annotationEditFormatter;
		this.modifySharedDocumentCommandContext = modifySharedDocumentCommandContext;
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
	public AnnotationProvider getAnnotationProvider() {
		return this.annotationProvider;
	}
	

	// ********** JavaResourceNode implementation **********

	public TextRange getTextRange() {
		return null;
	}


	// ********** JavaResourceNode.Root implementation **********

	public void resourceModelChanged() {
		for (JptResourceModelListener listener : this.resourceModelListenerList) {
			listener.resourceModelChanged(this);
		}
	}


	// ********** JavaResourceCompilationUnit implementation **********

	public ICompilationUnit getCompilationUnit() {
		return this.compilationUnit;
	}

	public CommandContext getModifySharedDocumentCommandContext() {
		return this.modifySharedDocumentCommandContext;
	}
	
	public AnnotationEditFormatter getAnnotationEditFormatter()  {
		return this.annotationEditFormatter;
	}
	
	public CompilationUnit buildASTRoot() {
		return ASTTools.buildASTRoot(this.compilationUnit);
	}


	// ********** JpaResourceModel implementation **********

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

	public void synchronizeWithJavaSource(CompilationUnit astRoot) {
		this.synchronizeWith(astRoot);
	}

	public void synchronizeWithJavaSourceIfNecessary() {
		try {
			if (this.compilationUnit.isConsistent()) {
				return;
			}
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.instance().logError(ex);
			// fall through and sync anyway?
		}
		this.synchronizeWithJavaSource();
	}

	protected abstract void synchronizeWith(CompilationUnit astRoot);


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
