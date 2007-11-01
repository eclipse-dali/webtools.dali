/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.IJpaAnnotationProvider;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationEditFormatter;
import org.eclipse.jpt.core.internal.jdtutility.JDTTools;
import org.eclipse.jpt.utility.internal.CommandExecutorProvider;
import org.eclipse.jpt.utility.internal.node.Node;

public class JpaCompilationUnitResource extends AbstractResource implements JavaResource
{
	protected final IJpaAnnotationProvider annotationProvider;
	
	protected final CommandExecutorProvider modifySharedDocumentCommandExecutorProvider;
	
	protected final AnnotationEditFormatter annotationEditFormatter;
	
	protected final ICompilationUnit compilationUnit;
	
	/**
	 * The primary type of the CompilationUnit. Not going to handle
	 * multiple Types defined in a compilation unit.  Entities must have
	 * a public/protected no-arg constructor and there is no way to access
	 * it in a non-public/protected class.
	 */
	protected JavaPersistentTypeResource persistentType;	
		public static final String PERSISTENT_TYPE_PROPERTY = "persistentTypeProperty";
	

	public JpaCompilationUnitResource(
			IFile file, 
			IJpaAnnotationProvider annotationProvider, 
			CommandExecutorProvider modifySharedDocumentCommandExecutorProvider,
			AnnotationEditFormatter annotationEditFormatter) {
		// The jpa compilation unit is the root of its sub-tree
		super(null);
		this.annotationProvider = annotationProvider;
		this.modifySharedDocumentCommandExecutorProvider = modifySharedDocumentCommandExecutorProvider;
		this.annotationEditFormatter = annotationEditFormatter;
		this.compilationUnit = compilationUnitFrom(file);
		this.initialize(JDTTools.buildASTRoot(this.compilationUnit));
	}
	
	protected ICompilationUnit compilationUnitFrom(IFile file) {
		ICompilationUnit compilationUnit = JavaCore.createCompilationUnitFrom(file);
		try {
			compilationUnit.open(null);
		}
		catch (JavaModelException jme) {
			// do nothing - we just won't have a primary type in this case
		}
		return compilationUnit;
	}
	
	public void initialize(CompilationUnit astRoot) {
		IType iType = this.compilationUnit.findPrimaryType();
		if (iType != null) {
			this.persistentType = createJavaPersistentType(iType, astRoot);
		}
	}
	
	// **************** overrides **********************************************
	
	
	@Override
	public Validator validator() {
		return Node.NULL_VALIDATOR;
	}

	@Override
	protected void checkParent(Node parentNode) {
		if (parentNode != null) {
			throw new IllegalArgumentException("The parent node must be null");
		}
	}
	
	@Override
	public IJpaAnnotationProvider annotationProvider() {
		return this.annotationProvider;
	}
	
	@Override
	public CommandExecutorProvider modifySharedDocumentCommandExecutorProvider() {
		return this.modifySharedDocumentCommandExecutorProvider;
	}
	
	@Override
	public AnnotationEditFormatter annotationEditFormatter()  {
		return this.annotationEditFormatter;
	}
	
	// *************************************************************************
	
	public ICompilationUnit getCompilationUnit() {
		return this.compilationUnit;
	}
	
	public JavaPersistentTypeResource javaPersistentTypeResource(String fullyQualifiedTypeName) {
		return getPersistentType().javaPersistentTypeResource(fullyQualifiedTypeName);
	}

	/**
	 * The persistentType resource for the compilation unit's primary type.
	 * Will be null if the primary type is null.
	 */
	public JavaPersistentTypeResource getPersistentType() {	
		return this.persistentType;
		//TODO should i only be returning this if it returns true to isPersistable?
		//that's how we handle nestedTypes on JavaPersistentTypeResource

//		if (this.persistentType.isPersistable()) {
//		return this.persistentType;
//	}
//	return null;
	}
	
	protected void setPersistentType(JavaPersistentTypeResource newPersistentType) {
		JavaPersistentTypeResource oldPersistentType = this.persistentType;
		this.persistentType = newPersistentType;
		firePropertyChanged(PERSISTENT_TYPE_PROPERTY, oldPersistentType, newPersistentType);
	}
	
	private JavaPersistentTypeResource createJavaPersistentType(IType iType, CompilationUnit astRoot) {
		return 
		JavaPersistentTypeResourceImpl.createJavaPersistentType(this, 
			iType, 
			modifySharedDocumentCommandExecutorProvider(), 
			annotationEditFormatter(), 
			astRoot);
	}
	
	public void updateFromJava(CompilationUnit astRoot) {
		IType iType = this.compilationUnit.findPrimaryType();
		if (iType == null) {
			setPersistentType(null);
		}
		else {
			if (getPersistentType() == null) {
				setPersistentType(createJavaPersistentType(iType, astRoot));
			}
			else {
				getPersistentType().updateFromJava(astRoot);
			}
		}
	}
	
	
	public ITextRange textRange(CompilationUnit astRoot) {
		return null;//this.selectionTextRange();
	}

//	/**
//	 * Return null for selection textRange.  Entire java file will appear selected when
//	 * switching files otherwise
//	 */
//	public ITextRange selectionTextRange() {
//		return null;
//	}
	
//	void resourceChanged(String aspectName) {
//		fireStateChanged();
//	}

}
