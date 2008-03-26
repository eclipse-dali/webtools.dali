/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.JpaAnnotationProvider;
import org.eclipse.jpt.core.internal.utility.jdt.JDTTools;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.JpaCompilationUnit;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationEditFormatter;
import org.eclipse.jpt.utility.CommandExecutorProvider;

public class JpaCompilationUnitImpl
	extends AbstractJavaResourceNode
	implements JpaCompilationUnit
{
	protected final JpaAnnotationProvider annotationProvider;
	
	protected final CommandExecutorProvider modifySharedDocumentCommandExecutorProvider;
	
	protected final AnnotationEditFormatter annotationEditFormatter;
	
	protected final ICompilationUnit compilationUnit;
	
	/**
	 * The primary type of the CompilationUnit. Not going to handle
	 * multiple Types defined in a compilation unit.  Entities must have
	 * a public/protected no-arg constructor and there is no way to access
	 * it in a non-public/protected class.
	 */
	protected JavaResourcePersistentType persistentType;	
		public static final String PERSISTENT_TYPE_PROPERTY = "persistentTypeProperty";
	
	protected final JavaResourceModel javaResourceModel;
	
	public JpaCompilationUnitImpl(
			IFile file, 
			JpaAnnotationProvider annotationProvider, 
			CommandExecutorProvider modifySharedDocumentCommandExecutorProvider,
			AnnotationEditFormatter annotationEditFormatter,
			JavaResourceModel javaResourceModel) {
		// The jpa compilation unit is the root of its sub-tree
		super(null);
		this.annotationProvider = annotationProvider;
		this.modifySharedDocumentCommandExecutorProvider = modifySharedDocumentCommandExecutorProvider;
		this.annotationEditFormatter = annotationEditFormatter;
		this.javaResourceModel = javaResourceModel;
		this.compilationUnit = compilationUnitFrom(file);
		this.initialize(JDTTools.buildASTRoot(this.compilationUnit));
	}
	
	protected ICompilationUnit compilationUnitFrom(IFile file) {
		ICompilationUnit cu = JavaCore.createCompilationUnitFrom(file);
		try {
			cu.open(null);
		}
		catch (JavaModelException jme) {
			// do nothing - we just won't have a primary type in this case
		}
		return cu;
	}
	
	public void initialize(CompilationUnit astRoot) {
		IType iType = this.compilationUnit.findPrimaryType();
		if (iType != null) {
			this.persistentType = buildJavaResourcePersistentType(iType, astRoot);
		}
	}
	
	// **************** overrides **********************************************
	
	
	@Override
	protected boolean requiresParent() {
		return false;
	}

	@Override
	public JpaCompilationUnit getJpaCompilationUnit() {
		return this;
	}

	@Override
	public JpaAnnotationProvider annotationProvider() {
		return this.annotationProvider;
	}
	
	@Override
	public CommandExecutorProvider modifySharedDocumentCommandExecutorProvider() {
		return this.modifySharedDocumentCommandExecutorProvider;
	}
	
	@Override
	public AnnotationEditFormatter getAnnotationEditFormatter()  {
		return this.annotationEditFormatter;
	}
	
	@Override
	public JavaResourceModel resourceModel() {
		return this.javaResourceModel;
	}
	
	
	// *************************************************************************
	
	public ICompilationUnit getCompilationUnit() {
		return this.compilationUnit;
	}
	
	public JavaResourcePersistentType getJavaPersistentTypeResource(String fullyQualifiedTypeName) {
		if (getPersistentType() != null) {
			return getPersistentType().javaPersistentTypeResource(fullyQualifiedTypeName);
		} 
		return null;
	}

	/**
	 * The persistentType resource for the compilation unit's primary type.
	 * Will be null if the primary type is null.
	 */
	public JavaResourcePersistentType getPersistentType() {	
		return this.persistentType;
		//TODO should i only be returning this if it returns true to isPersistable?
		//that's how we handle nestedTypes on JavaPersistentTypeResource

//		if (this.persistentType.isPersistable()) {
//		return this.persistentType;
//	}
//	return null;
	}
	
	protected void setPersistentType(JavaResourcePersistentType newPersistentType) {
		JavaResourcePersistentType oldPersistentType = this.persistentType;
		this.persistentType = newPersistentType;
		firePropertyChanged(PERSISTENT_TYPE_PROPERTY, oldPersistentType, newPersistentType);
	}
	
	private JavaResourcePersistentType buildJavaResourcePersistentType(IType iType, CompilationUnit astRoot) {
		//TODO put this hook in to avoid the NPE that we get trying to initialize a persistentTypeResource
		//from an annotation type.  Entered bug #    to handle the bigger issue of when we need to 
		//not build a persistent type(annotation types) and when we need to have validation instead(final types)
		try {
			if (iType.isAnnotation() || iType.isEnum()) {
				return null;
			}
		}
		catch (JavaModelException e) {
			throw new RuntimeException(e);
		}

		return 
			JavaResourcePersistentTypeImpl.buildJavaResourcePersistentType(this, 
				iType, 
				modifySharedDocumentCommandExecutorProvider(), 
				getAnnotationEditFormatter(), 
				astRoot);
	}
	
	public void updateFromJava(CompilationUnit astRoot) {
		IType iType = this.compilationUnit.findPrimaryType();
		if (iType == null) {
			setPersistentType(null);
		}
		else {
			if (getPersistentType() == null) {
				setPersistentType(buildJavaResourcePersistentType(iType, astRoot));
			}
			else {
				getPersistentType().updateFromJava(astRoot);
			}
		}
	}
	
	
	public TextRange textRange(CompilationUnit astRoot) {
		return null;//this.selectionTextRange();
	}

//	/**
//	 * Return null for selection textRange.  Entire java file will appear selected when
//	 * switching files otherwise
//	 */
//	public ITextRange selectionTextRange() {
//		return null;
//	}
	
	public void resourceChanged() {
		this.javaResourceModel.resourceChanged();
	}

	@Override
	public void resolveTypes(CompilationUnit astRoot) {
		if (getPersistentType() != null) {
			getPersistentType().resolveTypes(astRoot);
		}
	}
}
