/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java.source;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jpt.core.JpaAnnotationProvider;
import org.eclipse.jpt.core.JpaResourceModelListener;
import org.eclipse.jpt.core.internal.utility.jdt.JDTTools;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationEditFormatter;
import org.eclipse.jpt.utility.CommandExecutor;
import org.eclipse.jpt.utility.internal.ListenerList;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;

/**
 * Java compilation unit (source file)
 */
public final class SourceCompilationUnit
	extends SourceNode
	implements JavaResourceCompilationUnit
{
	/** JDT compilation unit */
	private final ICompilationUnit compilationUnit;

	/** pluggable annotation provider */
	private final JpaAnnotationProvider annotationProvider;

	/** improved annotation formatting */
	private final AnnotationEditFormatter annotationEditFormatter;

	/** pluggable executor that allows the document to be modified on another thread */
	private final CommandExecutor modifySharedDocumentCommandExecutor;

	/** listeners notified whenever the resource model changes */
	private final ListenerList<JpaResourceModelListener> resourceModelListenerList;

	/**
	 * The primary type of the AST compilation unit. We are not going to handle
	 * multiple types defined in a single compilation unit. Entities must have
	 * a public/protected no-arg constructor, and there is no way to access
	 * the constructor in a package class (which is what all top-level,
	 * non-primary classes must be).
	 */
	protected JavaResourcePersistentType persistentType;	


	// ********** construction **********

	public SourceCompilationUnit(
			ICompilationUnit compilationUnit,
			JpaAnnotationProvider annotationProvider, 
			AnnotationEditFormatter annotationEditFormatter,
			CommandExecutor modifySharedDocumentCommandExecutor) {
		super(null);  // the JPA compilation unit is the root of its sub-tree
		this.compilationUnit = compilationUnit;
		this.annotationProvider = annotationProvider;
		this.annotationEditFormatter = annotationEditFormatter;
		this.modifySharedDocumentCommandExecutor = modifySharedDocumentCommandExecutor;
		this.resourceModelListenerList = new ListenerList<JpaResourceModelListener>(JpaResourceModelListener.class);
		this.persistentType = this.buildPersistentType();
	}

	protected JavaResourcePersistentType buildPersistentType() {
		this.openCompilationUnit();
		CompilationUnit astRoot = this.buildASTRoot();
		this.closeCompilationUnit();
		return this.buildPersistentType(astRoot);
	}

	protected void openCompilationUnit() {
		try {
			this.compilationUnit.open(null);
		} catch (JavaModelException ex) {
			// do nothing - we just won't have a primary type in this case
		}
	}

	protected void closeCompilationUnit() {
		try {
			this.compilationUnit.close();
		} catch (JavaModelException ex) {
			// hmmm
		}
	}

	protected JavaResourcePersistentType buildPersistentType(CompilationUnit astRoot) {
		TypeDeclaration td = this.getPrimaryType(astRoot);
		return (td == null) ? null : this.buildPersistentType(astRoot, td);
	}

	@Override
	protected boolean requiresParent() {
		return false;
	}

	public void initialize(CompilationUnit astRoot) {
		// never called?
	}


	// ********** AbstractJavaResourceNode overrides **********

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

	public void update(CompilationUnit astRoot) {
		TypeDeclaration td = this.getPrimaryType(astRoot);
		if (td == null) {
			this.persistentType = null;
		} else {
			if (this.persistentType == null) {
				this.persistentType = this.buildPersistentType(astRoot, td);
			} else {
				this.persistentType.update(astRoot);
			}
		}
	}

	public TextRange getTextRange(CompilationUnit astRoot) {
		return null;
	}


	// ********** JavaResourceNode.Root implementation **********

	/**
	 * NB: return *all* the persistent types since we build them all
	 */
	public Iterator<JavaResourcePersistentType> persistentTypes() {
		return (this.persistentType == null) ?
				EmptyIterator.<JavaResourcePersistentType>instance() :
				this.persistentType.allTypes();
	}

	public void resourceModelChanged() {
		for (JpaResourceModelListener listener : this.resourceModelListenerList.getListeners()) {
			listener.resourceModelChanged();
		}
	}


	// ********** JavaResourceCompilationUnit implementation **********

	public ICompilationUnit getCompilationUnit() {
		return this.compilationUnit;
	}

	public void resolveTypes() {
		if (this.persistentType != null) {
			this.persistentType.resolveTypes(this.buildASTRoot());
		}
	}

	public CommandExecutor getModifySharedDocumentCommandExecutor() {
		return this.modifySharedDocumentCommandExecutor;
	}
	
	public AnnotationEditFormatter getAnnotationEditFormatter()  {
		return this.annotationEditFormatter;
	}
	
	public CompilationUnit buildASTRoot() {
		return JDTTools.buildASTRoot(this.compilationUnit);
	}


	// ********** JpaResourceModel implementation **********

	public void addResourceModelListener(JpaResourceModelListener listener) {
		this.resourceModelListenerList.add(listener);
	}

	public void removeResourceModelListener(JpaResourceModelListener listener) {
		this.resourceModelListenerList.remove(listener);
	}


	// ********** Java changes **********

	public void update() {
		this.update(this.buildASTRoot());
	}


	// ********** internal **********

	protected JavaResourcePersistentType buildPersistentType(CompilationUnit astRoot, TypeDeclaration typeDeclaration) {
		return SourcePersistentType.newInstance(this, typeDeclaration, astRoot);
	}

	/**
	 * i.e. the type with the same name as the compilation unit;
	 * return the first class or interface (ignore annotations and enums) with
	 * the same name as the compilation unit (file);
	 * NB: this type could be in error if there is an annotation or enum
	 * with the same name preceding it in the compilation unit
	 * 
	 * Return null if resolveBinding() on the TypeDeclaration returns null
	 * This can occur if the project JRE is removed (bug 225332)
	 */
	protected TypeDeclaration getPrimaryType(CompilationUnit astRoot) {
		String primaryTypeName = this.getPrimaryTypeName();
		for (AbstractTypeDeclaration atd : types(astRoot)) {
			if ((atd.getNodeType() == ASTNode.TYPE_DECLARATION)
					&& atd.getName().getFullyQualifiedName().equals(primaryTypeName)) {
				return (atd.resolveBinding()) != null ? (TypeDeclaration) atd : null;
			}
		}
		return null;
	}

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	protected static List<AbstractTypeDeclaration> types(CompilationUnit astRoot) {
		return astRoot.types();
	}

	/**
	 * i.e. the name of the compilation unit
	 */
	protected String getPrimaryTypeName() {
		return removeJavaExtension(this.compilationUnit.getElementName());
	}

	protected static String removeJavaExtension(String fileName) {
		int index = fileName.lastIndexOf(".java"); //$NON-NLS-1$
		return (index == -1) ? fileName : fileName.substring(0, index);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.persistentType.getName());
	}

}
