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

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jpt.core.JpaAnnotationProvider;
import org.eclipse.jpt.core.internal.utility.jdt.JDTTools;
import org.eclipse.jpt.core.resource.java.JavaResourceModel;
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

	protected final JavaResourceModel javaResourceModel;

	protected final ICompilationUnit compilationUnit;

	/**
	 * The primary type of the AST compilation unit. We are not going to handle
	 * multiple types defined in a single compilation unit. Entities must have
	 * a public/protected no-arg constructor, and there is no way to access
	 * the constructor in a package class (which is what all top-level,
	 * non-primary classes must be).
	 */
	protected JavaResourcePersistentType persistentType;	


	// ********** construction **********

	public JpaCompilationUnitImpl(
			IFile file, 
			JpaAnnotationProvider annotationProvider, 
			CommandExecutorProvider modifySharedDocumentCommandExecutorProvider,
			AnnotationEditFormatter annotationEditFormatter,
			JavaResourceModel javaResourceModel) {
		super(null);  // the JPA compilation unit is the root of its sub-tree
		this.annotationProvider = annotationProvider;
		this.modifySharedDocumentCommandExecutorProvider = modifySharedDocumentCommandExecutorProvider;
		this.annotationEditFormatter = annotationEditFormatter;
		this.javaResourceModel = javaResourceModel;
		this.compilationUnit = JavaCore.createCompilationUnitFrom(file);
		this.openCompilationUnit();
		this.persistentType = this.buildJavaResourcePersistentType();
	}

	protected void openCompilationUnit() {
		try {
			this.compilationUnit.open(null);
		} catch (JavaModelException ex) {
			// do nothing - we just won't have a primary type in this case
		}
	}

	protected JavaResourcePersistentType buildJavaResourcePersistentType() {
		return this.buildJavaResourcePersistentType(this.buildASTRoot());
	}

	protected JavaResourcePersistentType buildJavaResourcePersistentType(CompilationUnit astRoot) {
		TypeDeclaration td = this.getPrimaryType(astRoot);
		return (td == null) ? null : this.buildJavaResourcePersistentType(astRoot, td);
	}

	public void initialize(CompilationUnit astRoot) {
		// never called?
	}


	// ********** overrides **********

	@Override
	protected boolean requiresParent() {
		return false;
	}

	@Override
	public JpaCompilationUnit getJpaCompilationUnit() {
		return this;
	}

	@Override
	public JpaAnnotationProvider getAnnotationProvider() {
		return this.annotationProvider;
	}
	
	@Override
	public CommandExecutorProvider getModifySharedDocumentCommandExecutorProvider() {
		return this.modifySharedDocumentCommandExecutorProvider;
	}
	
	@Override
	public AnnotationEditFormatter getAnnotationEditFormatter()  {
		return this.annotationEditFormatter;
	}
	
	@Override
	public JavaResourceModel getResourceModel() {
		return this.javaResourceModel;
	}
	

	// ********** JpaCompilationUnit implementation **********

	public ICompilationUnit getCompilationUnit() {
		return this.compilationUnit;
	}
	
	public JavaResourcePersistentType getJavaPersistentTypeResource(String fullyQualifiedTypeName) {
		return (this.persistentType == null) ? null : this.persistentType.getJavaPersistentTypeResource(fullyQualifiedTypeName);
	}

	/**
	 * The persistentType resource for the compilation unit's primary type.
	 * Will be null if the primary type is null.
	 */
	public JavaResourcePersistentType getPersistentType() {	
		return this.persistentType;
		//TODO should i only be returning this if it returns true to isPersistable?
		//that's how we handle nestedTypes on JavaPersistentTypeResource
		//return this.persistentType.isPersistable() ? this.persistentType : null;
	}
	
	protected void setPersistentType(JavaResourcePersistentType persistentType) {
		JavaResourcePersistentType old = this.persistentType;
		this.persistentType = persistentType;
		this.firePropertyChanged(PERSISTENT_TYPE_PROPERTY, old, persistentType);
	}

	public void updateFromJava() {
		this.update(this.buildASTRoot());
	}

	public void update(CompilationUnit astRoot) {
		TypeDeclaration td = this.getPrimaryType(astRoot);
		if (td == null) {
			this.setPersistentType(null);
		} else {
			if (this.persistentType == null) {
				this.setPersistentType(this.buildJavaResourcePersistentType(astRoot, td));
			} else {
				this.persistentType.update(astRoot);
			}
		}
	}

	public TextRange getTextRange(CompilationUnit astRoot) {
		return null;
	}

	public void resourceChanged() {
		this.javaResourceModel.resourceChanged();
	}

	public void resolveTypes() {
		if (this.persistentType != null) {
			this.persistentType.resolveTypes(this.buildASTRoot());
		}
	}


	// ********** internal **********

	protected CompilationUnit buildASTRoot() {
		return JDTTools.buildASTRoot(this.compilationUnit);
	}

	// TODO use JPA factory
	protected JavaResourcePersistentType buildJavaResourcePersistentType(CompilationUnit astRoot, TypeDeclaration typeDeclaration) {
		return JavaResourcePersistentTypeImpl.newInstance(this, typeDeclaration, astRoot);
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
				return atd.resolveBinding() != null ? (TypeDeclaration) atd : null;
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
		int index = fileName.lastIndexOf(".java");
		return (index == -1) ? fileName : fileName.substring(0, index);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.persistentType.getName());
	}

}
