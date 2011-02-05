/*******************************************************************************
 * Copyright (c) 2005, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility.jdt;

import java.util.List;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationEditFormatter;
import org.eclipse.jpt.common.core.utility.jdt.Type;
import org.eclipse.jpt.common.utility.CommandExecutor;

/**
 * Adapt and extend a JDT type.
 */
public class JDTType
	extends AbstractJDTType
	implements Type
{

	/**
	 * constructor for the compilation unit's primary type
	 */
	public JDTType(
			TypeDeclaration typeDeclaration,  // exclude annotations and enums
			ICompilationUnit compilationUnit,
			CommandExecutor modifySharedDocumentCommandExecutor) {
		this(typeDeclaration, compilationUnit, modifySharedDocumentCommandExecutor, DefaultAnnotationEditFormatter.instance());
	}

	/**
	 * constructor for the compilation unit's primary type
	 */
	public JDTType(
			TypeDeclaration typeDeclaration,  // exclude annotations and enums
			ICompilationUnit compilationUnit,
			CommandExecutor modifySharedDocumentCommandExecutor,
			AnnotationEditFormatter annotationEditFormatter) {
		this(null, typeDeclaration, 1, compilationUnit, modifySharedDocumentCommandExecutor, annotationEditFormatter);
	}

	/**
	 * constructor for nested types
	 */
	public JDTType(
			Type declaringType,
			TypeDeclaration typeDeclaration,  // exclude annotations and enums
			int occurrence,
			ICompilationUnit compilationUnit,
			CommandExecutor modifySharedDocumentCommandExecutor) {
		this(declaringType, typeDeclaration, occurrence, compilationUnit, modifySharedDocumentCommandExecutor, DefaultAnnotationEditFormatter.instance());
	}

	/**
	 * constructor for nested types
	 */
	public JDTType(
			Type declaringType,
			TypeDeclaration typeDeclaration,  // exclude annotations and enums
			int occurrence,
			ICompilationUnit compilationUnit,
			CommandExecutor modifySharedDocumentCommandExecutor,
			AnnotationEditFormatter annotationEditFormatter) {
		super(declaringType, typeDeclaration, occurrence, compilationUnit, modifySharedDocumentCommandExecutor, annotationEditFormatter);
	}

	/**
	 * constructor for testing
	 */
	public JDTType(Type declaringType, String name, int occurrence, ICompilationUnit compilationUnit) {
		super(declaringType, name, occurrence, compilationUnit);
	}


	// ********** Member/Type implementation **********

	/**
	 * find the type's body declaration in the specified AST
	 */
	@Override
	public TypeDeclaration getBodyDeclaration(CompilationUnit astRoot) {
		return (TypeDeclaration) super.getBodyDeclaration(astRoot);
	}

	public boolean isPersistable(CompilationUnit astRoot) {
		ITypeBinding binding = this.getBinding(astRoot);
		return (binding == null) ? false : JPTTools.typeIsPersistable(new JPTToolsAdapter(binding));
	}

	public TypeDeclaration[] getTypes(CompilationUnit astRoot) {
		return this.getBodyDeclaration(astRoot).getTypes();
	}

	public EnumDeclaration[] getEnums(CompilationUnit astRoot) {
		return getEnums(this.getBodyDeclaration(astRoot));
	}

	public FieldDeclaration[] getFields(CompilationUnit astRoot) {
		return this.getBodyDeclaration(astRoot).getFields();
	}

	public MethodDeclaration[] getMethods(CompilationUnit astRoot) {
		return this.getBodyDeclaration(astRoot).getMethods();
	}


	// ********** internal **********

	/**
	 * return the first top-level type in the specified AST with a matching name
	 */
	@Override
	protected TypeDeclaration getTopLevelTypeDeclaration(CompilationUnit astRoot) {
		return (TypeDeclaration) super.getTopLevelTypeDeclaration(astRoot);
	}

	@Override
	protected TypeDeclaration getTypeDeclaration(List<AbstractTypeDeclaration> typeDeclarations) {
		return (TypeDeclaration) super.getTypeDeclaration(typeDeclarations);
	}

	/**
	 * return the nested type with a matching name and occurrence
	 */
	@Override
	protected TypeDeclaration getNestedTypeDeclaration(TypeDeclaration declaringTypeDeclaration) {
		return this.getTypeDeclaration(declaringTypeDeclaration.getTypes());
	}

	/**
	 * return the type declaration corresponding to the type from the specified
	 * set of type declarations (match name and occurrence)
	 */
	@Override
	protected TypeDeclaration getTypeDeclaration(AbstractTypeDeclaration[] typeDeclarations) {
		return (TypeDeclaration) super.getTypeDeclaration(typeDeclarations);
	}

	@Override
	protected int getASTNodeType() {
		return ASTNode.TYPE_DECLARATION;
	}


	// ********** JPT tools adapter **********

	protected static class JPTToolsAdapter implements JPTTools.TypeAdapter {
		private final ITypeBinding typeBinding;
		protected JPTToolsAdapter(ITypeBinding typeBinding) {
			super();
			if (typeBinding == null) {
				throw new NullPointerException();
			}
			this.typeBinding = typeBinding;
		}

		public int getModifiers() {
			return this.typeBinding.getModifiers();
		}

		public boolean isAnnotation() {
			return this.typeBinding.isAnnotation();
		}

		public boolean isAnonymous() {
			return this.typeBinding.isAnonymous();
		}

		public boolean isArray() {
			return this.typeBinding.isArray();
		}

		public boolean isEnum() {
			return this.typeBinding.isEnum();
		}

		public boolean isInterface() {
			return this.typeBinding.isInterface();
		}

		public boolean isLocal() {
			return this.typeBinding.isLocal();
		}

		public boolean isMember() {
			return this.typeBinding.isMember();
		}

		public boolean isPrimitive() {
			return this.typeBinding.isPrimitive();
		}
	}

}
